from flask import Flask, render_template, request, jsonify
#创建Flask对象app并初始化
app = Flask(__name__)

#app的路由地址"/submit"即为ajax中定义的url地址，采用POST、GET方法均可提交
@app.route("/submit",methods=["GET", "POST"])
#从这里定义具体的函数 返回值均为json格式
def submit():
    img = request.files.get('file')
    #如果获取的数据为空
    name = str(img) + '.jpg'
    print(name)
    food = "fish" + "apple"
    if name==None:
        return "error!"
    else:
        return food

#定义app在8080端口运行
app.run(port=8080)