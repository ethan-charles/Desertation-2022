import matplotlib.pyplot as plt
import torch
import torch.nn as nn
import numpy as np
import os
from args import get_parser
import pickle
from model import get_model
from torchvision import transforms
from utils.output_utils import prepare_output
from PIL import Image
import time

import requests
from io import BytesIO
import random
from collections import Counter
import glob
##########################################
## Server import
##########################################
from flask import Flask, render_template, request, jsonify


data_dir = '../data'

use_gpu = True
device = torch.device('cuda' if torch.cuda.is_available() and use_gpu else 'cpu')
map_loc = None if torch.cuda.is_available() and use_gpu else 'cpu'

ingrs_vocab = pickle.load(open(os.path.join(data_dir, 'ingr_vocab.pkl'), 'rb'))
vocab = pickle.load(open(os.path.join(data_dir, 'instr_vocab.pkl'), 'rb'))

ingr_vocab_size = len(ingrs_vocab)
instrs_vocab_size = len(vocab)
output_dim = instrs_vocab_size


##########################################
## Load Model
##########################################
t = time.time()
import sys;

sys.argv = [''];
del sys
args = get_parser()
args.maxseqlen = 15
args.ingrs_only = False
model = get_model(args, ingr_vocab_size, instrs_vocab_size)
# Load the trained model parameters
model_path = os.path.join(data_dir, 'modelbest.ckpt')
model.load_state_dict(torch.load(model_path, map_location=map_loc))
model.to(device)
model.eval()
model.ingrs_only = False
model.recipe_only = False
print('loaded model')
print("Elapsed time:", time.time() - t)

transf_list_batch = []
transf_list_batch.append(transforms.ToTensor())
transf_list_batch.append(transforms.Normalize((0.485, 0.456, 0.406),
                                              (0.229, 0.224, 0.225)))
to_input_transf = transforms.Compose(transf_list_batch)

greedy = [True, False, False, False]
beam = [-1, -1, -1, -1]
temperature = 1.0
numgens = len(greedy)


##########################################
## Image Preprocessing
##########################################
def remove_transparency(im, bg_colour=(255, 255, 255)):
    # Only process if image has transparency (http://stackoverflow.com/a/1963146)
    # if im.mode in ('RGBA', 'LA') or (im.mode == 'P' and 'transparency' in im.info):
    if im.mode in ('RGBA', 'LA'):

        # Need to convert to RGBA if LA format due to a bug in PIL (http://stackoverflow.com/a/1963146)
        alpha = im.convert('RGBA').split()[-1]

        # Create a new background image of our matt color.
        # Must be RGBA because paste requires both images have the same format
        # (http://stackoverflow.com/a/8720632  and  http://stackoverflow.com/a/9459208)
        bg = Image.new("RGBA", im.size, bg_colour + (255,))
        bg.paste(im, mask=alpha)
        return bg

    else:
        return im


def get_ingr_ids(ingr_string_list, ingr_vocab_list):
    ingr_ids = []
    for ingr_name in ingr_string_list:
        try:
            ingr_id = np.where(np.array(ingr_vocab_list) == ingr_name)[0][0]
            ingr_ids.append(ingr_id)
        except:
            print("'" + ingr_name + "' is not in the ingredient list. Make sure it matches exactly.")
    return ingr_ids


##########################################
##Validity Data
##########################################
fingr = open(os.path.join(data_dir, 'ingr_vocab.pkl'), 'rb')
finstr = open(os.path.join(data_dir, 'instr_vocab.pkl'), 'rb')
datainstr = pickle.load(finstr)
dataingr = pickle.load(fingr)

print("ingredient number:", len(dataingr))
##print(dataingr)
print("instruction number:", len(datainstr))
##print(datainstr)

model_pathtest = os.path.join(data_dir, 'modelbest.ckpt')
modeltest = torch.load(model_path, map_location=map_loc)

for param_tensor in modeltest:
    print(param_tensor, "\t", modeltest[param_tensor].size())

##########################################
##Add Recipe Images Below
##########################################
use_urls = True  # set to True to load images from demo_urls instead of those in /content/inversecooking/data/demo_imgs folder
show_anyways = True  # if True, it will show the recipe even if it's not valid. More fun if True.

demo_urls = ['https://post.healthline.com/wp-content/uploads/2018/04/steak-meat-1296x728-header.jpg',
             'https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&webp=true&resize=375,341',
             'https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg',
             'https://cdn.diffords.com/contrib/stock-images/2016/7/14/20160a9ce3df170d814455d86fd6fededd03.jpg',
             'https://i.pinimg.com/originals/ab/67/53/ab6753ec1cef75f1cc2052487b1f4059.jpg',
             'http://static.oprah.com/images/201302/orig/201302-orig-beautiful-chicken-600x411.jpg',]

# Incredients must be exactly from this list: https://pastebin.com/iZb0UrTd
add_my_ingredients = True  # Add own ingredients to the final recipe, in ADDITION to what the AI thinks are the real ingredients
use_only_my_ingredients = False  # Use ONLY own ingredients in final recipe. It tends to ignore really crazy stuff like bleach. The picture of the food still has a big effect on the final recipe!
## custom_ingredient_list = ['bacon','jelly','salsa','lemon']
custom_ingredient_list = []
show_ingredient_probabilities = False  # If True, show extra information about the ingredient predictions

override_sequence_length = 0
# Default is 0 which uses the predicted recipe length. High values don't do much, I think because the recipes are generated one step at a time
# So adding length to the last step like ENJOY or a smile, just adds endless 'end of recipe' tokens after that get ignored.
# That said, it will sometimes make a very long recipe with it, that would never have finished on the default setting.
# Short values just cut the recipe off early.
# length is in tokens, a word is a token, so is a space or a piece of puntucation.
# generally long ingredient lists have longer recipes, higher top_k values are longer, and temperature > 1.0 recipes are longer but also kind of crazy
temperature = 1.0  # default is 1.0.
top_k = 10  # default is 10
# both temperature and top_k values only effect recipes 2-4 by default, the first recipe defaults to GREEDY sampling, though you can change that below
greedy = [True, False, False, False]

image_folder = os.path.join(data_dir, 'demo_imgs')

if not use_urls:
    # demo_imgs = os.listdir(image_folder)
    demo_imgs = [os.path.basename(f) for f in glob.glob(image_folder + "/*.jpg")]
    demo_imgs = demo_imgs + [os.path.basename(f) for f in glob.glob(image_folder + "/*.png")]
    random.shuffle(demo_imgs)

demo_files = demo_urls if use_urls else demo_imgs

print("Images to be Reciped: " + str(demo_files))

if (use_only_my_ingredients or add_my_ingredients):
    if use_only_my_ingredients:
        print("Custom ingredients:")
    elif add_my_ingredients:
        print("Add ingredients to predicted:")
    print(custom_ingredient_list)
    custom_ingrs = get_ingr_ids(custom_ingredient_list, ingrs_vocab)
    tensor_ingrs = torch.LongTensor([custom_ingrs]).to(device)


##########################################
## Server setting + Recipe Generation
##########################################
#Create Flask object 'app' and initialize
app = Flask(__name__)

#The route address of 'app' is "/submit", which is the URL address defined in AJAX, and both POST and GET methods can be used to submit.
@app.route("/submit",methods=["GET", "POST"])
#Define specific functions here, and the return value is in JSON format.
def submit():
    img = request.files.get('file')

    image = Image.open(img)
    if image.mode != 'RGB':
        # If you just convert to RGB, sometimes the background ends up black, though I'm not sure if it effects the model
        image = remove_transparency(image)
        image = image.convert('RGB')
    transf_list = []
    transf_list.append(transforms.Resize(256))
    transf_list.append(transforms.CenterCrop(224))
    transform = transforms.Compose(transf_list)
    image_transf = transform(image)
    image_tensor = to_input_transf(image_transf).unsqueeze(0).to(device)
    ##plt.imshow(image_transf)
    ##plt.axis('off')
    ##plt.show()
    ##plt.close()
    printed_probs = 0
    num_valid = 1
    i = 0
    print("Start To Generate")
    with torch.no_grad():
        if use_only_my_ingredients and len(custom_ingrs) > 0:
            outputs = model.sample(image_tensor, greedy=greedy[i],
                                   temperature=temperature, beam=beam[i], true_ingrs=tensor_ingrs, add_ingrs=None,
                                   override_sequence_length=override_sequence_length, top_k=top_k)
        elif add_my_ingredients and len(custom_ingrs) > 0:
            outputs = model.sample(image_tensor, greedy=greedy[i],
                                   temperature=temperature, beam=beam[i], true_ingrs=None, add_ingrs=tensor_ingrs,
                                   override_sequence_length=override_sequence_length, top_k=top_k)
        else:
            outputs = model.sample(image_tensor, greedy=greedy[i],
                                   temperature=temperature, beam=beam[i], true_ingrs=None,
                                   override_sequence_length=override_sequence_length, top_k=top_k)
    ingr_ids = outputs['ingr_ids'].cpu().numpy()
    recipe_ids = outputs['recipe_ids'].cpu().numpy()
    # Ingredient probababilites.
    # These don't seem to be used in generating the final recipe, but might be interesting to see.
    if show_ingredient_probabilities and printed_probs == 0:
        sm = torch.nn.Softmax()
        ingr_probabilities = outputs['ingr_probs']
        print("Predicted Ingredients: ", end='')
        for ingr_index in range(0, len(ingr_probabilities[0])):
            ingr, ingrp = ingr_probabilities[0][ingr_index].max(0)
            if ingrp == 0:
                break
            print(ingrs_vocab[ingrp], end=': ')
            ingr = ingr.cpu().numpy()
            print(ingr, end='% ')
        printed_probs = 1
        print()
    outs, valid = prepare_output(recipe_ids[0], ingr_ids[0], ingrs_vocab, vocab)


    if valid['is_valid'] or show_anyways:
        BOLD = '\033[1m'
        END = '\033[0m'
        print(BOLD + 'RECIPE' + END, num_valid)
        num_valid += 1
        # print ("greedy:", greedy[i], "beam:", beam[i])
        print(BOLD + '\nTitle:' + END, outs['title'])
        print(BOLD + '\nIngredients:' + END)
        print(', '.join(outs['ingrs']))
        print(BOLD + '\nInstructions:' + END)
        print('-' + '\n-'.join(outs['recipe']))
        print('=' * 20)
    else:
        print("Not a valid recipe!")
        print("Reason: ", valid['reason'])


    food = ', '.join(outs['ingrs'])
    recipe = '-' + '\n-'.join(outs['recipe'])
    output_text = food + "00000000" + recipe

    if food==None:
        return "error!"
    else:
        return str(output_text)

#Define app to run on port 8080
app.run(port=8080)

