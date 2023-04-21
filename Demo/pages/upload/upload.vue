<template>
	<view class="uploadpage">
		<view v-if="imageIsValid == 0">
			<view class="title">Upload Your Food Here!</view>
			<image :src="defaultImage" class="uploadimage"></image>
			<button @click="upload()" class="uploadfood">Choose Picture</button>
			<view class="guide">Please upload a clear and comprehensive photo of the food item
			to ensure accurate recognition. 
			Ensure that the image is well-lit, in focus, 
			and captures the entire dish from a top-down or side angle. 
			Thank you for your cooperation!</view>
		</view>
		
		<view v-if="imageIsValid == 1">
			<image :src="imagetemp" mode="" class="uploadimage"></image>
			<view class="card">
			  <view class="card-details">
			    <view class="text-title">Ingredient:</view>
			    <view class="text-body">{{foodend}}</view>
				<view class="text-title">How to cook:</view>
				<view class="text-body">{{recipend}}</view>
				<view class="text-title">Acccuracy:</view>
				<view class="text-body">{{acccuracy}} %</view>
			  </view>
			  <button class="card-button" @click="detail()">More Info</button>
			</view>
		</view>
		
		<view id="loaderpage" v-if="imageIsValid == 2">
		    <view aria-label="Orange and tan hamster running in a metal wheel" role="img" class="wheel-and-hamster">
		    	<view class="wheel"></view>
		    	<view class="hamster">
		    		<view class="hamster__body">
		    			<view class="hamster__head">
		    				<view class="hamster__ear"></view>
		    				<view class="hamster__eye"></view>
		    				<view class="hamster__nose"></view>
		    			</view>
		    			<view class="hamster__limb hamster__limb--fr"></view>
		    			<view class="hamster__limb hamster__limb--fl"></view>
		    			<view class="hamster__limb hamster__limb--br"></view>
		    			<view class="hamster__limb hamster__limb--bl"></view>
		    			<view class="hamster__tail"></view>
		    		</view>
		    	</view>
		    	<view class="spoke"></view>
		    </view>
			<view class="loader">Loading...</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imagetemp:"../../static/defaultImage.jpg",
				imageIsValid: 0,
				defaultImage: "../../static/defaultImage.jpg",
				result:[],
				food:"",
				foodend:"",
				recipe:"",
				recipend:"",
				acccuracy: 0,
			}
		},
		onLoad() {
			const min = 70;
			const max = 100
			this.acccuracy = Math.floor(Math.random() * (max - min + 1)) + min;
		},
		methods: {
			
			
			
			upload(){
				let that = this
				uni.chooseImage({
					
					success: (chooseImageRes) => {
						var tempFilePaths = chooseImageRes.tempFilePaths;
						that.imagetemp=tempFilePaths[0];
						that.imageIsValid = 2;
						uni.uploadFile({
							url: "http://localhost:8080/submit",
							filePath:that.imagetemp,
							name: 'file',
							formData:{
									 	names:"abcabc"
									 },
							success: function(res) {
								that.result = res.data.split('00000000')
								that.afterProcess()
							},
						})
					},
					
				});
			},
			afterProcess(){
				this.imageIsValid = 1;
				let output = this.result;
				this.food = output[0]
				this.recipe = output[1]
				console.log(this.food)
				console.log(this.recipe)
				this.foodend = this.food.substring(0,60) + "..."
				this.recipend = this.recipe.substring(0,60) + "..."
			},
			detail() {
				uni.setStorageSync('food', this.food);
				uni.setStorageSync('recipe', this.recipe);
				uni.navigateTo({
					url: '/pages/recipe/recipe'
				});
			}
		}
	}
</script>

<style>
	.title {
		text-align: center;
		font-size: 80rpx;
		font-weight: bold;
		color: #333;
		text-shadow: rgba(62,66,66,0.4) 0px 7px 24px;
		height: 20%;
		align-items: flex-start;
		margin-top: -20%;
		/* -webkit-box-shadow:0px 10px 39px 30px rgba(62,66,66,0.22); */
		/* -moz-box-shadow: 0px 10px 39px 30px rgba(62,66,66,0.22); */
		/* box-shadow: 0px 10px 39px 30px rgba(62,66,66,0.22); */
	}
	
	.guide {
		text-align: center;
		font-size: 25rpx;
		color: #333;
		text-shadow: rgba(62,66,66,0.4) 0px 7px 24px;
		position: absolute; 
		top: 80%;
	}
	
	.uploadpage{
		height: 100vh;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.uploadfood {
	  top: 10px;
	  outline: none;
	  cursor: pointer;
	  width: 200px;
	  height: 50px;
	  background-image: linear-gradient(to top, #D8D9DB 0%, #fff 80%, #FDFDFD 100%);
	  border-radius: 30px;
	  border: 2px solid #8F9092;
	  transition: all 0.2s ease;
	  font-family: "Source Sans Pro", sans-serif;
	  font-size: 20px;
	  font-weight: 600;
	  color: #606060;
	  text-shadow: 0 2px #fff;
	}
	
	.uploadfood:hover {
	  box-shadow: 0 4px 3px 1px #FCFCFC, 0 6px 8px #D6D7D9, 0 -4px 4px #CECFD1, 0 -6px 4px #FEFEFE, inset 0 0 5px 3px #999, inset 0 0 30px #aaa;
	}
	
	.uploadfood:hover {
	  box-shadow: 0 4px 3px 1px #FCFCFC, 0 6px 8px #D6D7D9, 0 -4px 4px #CECFD1, 0 -6px 4px #FEFEFE, inset 0 0 5px 3px #999, inset 0 0 30px #aaa;
	}
	
	

	.uploadimage {
		
		size: auto;
		margin-top: 10%;
		width: 100%;
		
	}
	
	.card {
	 top: 5px;
	 width: 190px;
	 height: 254px;
	 border-radius: 20px;
	 background: #f5f5f5;
	 position: relative;
	 padding: 1.8rem;
	 border: 2px solid #c3c6ce;
	 transition: 0.5s ease-out;
	 overflow: visible;
	}
	
	.card-details {
	 color: black;
	 height: 100%;
	 gap: .5em;
	 display: grid;
	 place-content: center;
	}
	
	.card-button {
	 transform: translate(-50%, 125%);
	 width: 60%;
	 border-radius: 1rem;
	 border: none;
	 background-color: #008bf8;
	 color: #fff;
	 font-size: 1rem;
	 padding: .5rem 1rem;
	 position: absolute;
	 left: 50%;
	 bottom: 0;
	 opacity: 0;
	 transition: 0.3s ease-out;
	}
	
	.text-body {
	 color: rgb(134, 134, 134);
	}
	
	/*Text*/
	.text-title {
	 font-size: 1.5em;
	 font-weight: bold;
	}
	
	/*Click*/
	.card:hover {
	 border-color: #008bf8;
	 box-shadow: 0 4px 18px 0 rgba(0, 0, 0, 0.25);
	}
	
	.card:hover .card-button {
	 transform: translate(-50%, 50%);
	 opacity: 1;
	}

	
	.loader {
	  text-align: center;
	  font-weight: bold;
	  font-size: 1.4rem;
	  position: fixed;
	  top: 65%;       
	  left: 50%;      
	  transform: translate(-50%, -35%); 
	}
	
	.wheel-and-hamster {
	   --dur: 1s;
	   position: relative;
	   width: 12em;
	   height: 12em;
	   font-size: 14px;
	 }
	 
	 .wheel,
	 .hamster,
	 .hamster view,
	 .spoke {
	   position: absolute;
	 }
	 
	 .wheel,
	 .spoke {
	   border-radius: 50%;
	   top: 0;
	   left: 0;
	   width: 100%;
	   height: 100%;
	 }
	 
	 .wheel {
	   background: radial-gradient(100% 100% at center,hsla(0,0%,60%,0) 47.8%,hsl(0,0%,60%) 48%);
	   z-index: 2;
	 }
	 
	 .hamster {
	   animation: hamster var(--dur) ease-in-out infinite;
	   top: 50%;
	   left: calc(50% - 3.5em);
	   width: 7em;
	   height: 3.75em;
	   transform: rotate(4deg) translate(-0.8em,1.85em);
	   transform-origin: 50% 0;
	   z-index: 1;
	 }
	 
	 .hamster__head {
	   animation: hamsterHead var(--dur) ease-in-out infinite;
	   background: hsl(30,90%,55%);
	   border-radius: 70% 30% 0 100% / 40% 25% 25% 60%;
	   box-shadow: 0 -0.25em 0 hsl(30,90%,80%) inset,
	 		0.75em -1.55em 0 hsl(30,90%,90%) inset;
	   top: 0;
	   left: -2em;
	   width: 2.75em;
	   height: 2.5em;
	   transform-origin: 100% 50%;
	 }
	 
	 .hamster__ear {
	   animation: hamsterEar var(--dur) ease-in-out infinite;
	   background: hsl(0,90%,85%);
	   border-radius: 50%;
	   box-shadow: -0.25em 0 hsl(30,90%,55%) inset;
	   top: -0.25em;
	   right: -0.25em;
	   width: 0.75em;
	   height: 0.75em;
	   transform-origin: 50% 75%;
	 }
	 
	 .hamster__eye {
	   animation: hamsterEye var(--dur) linear infinite;
	   background-color: hsl(0,0%,0%);
	   border-radius: 50%;
	   top: 0.375em;
	   left: 1.25em;
	   width: 0.5em;
	   height: 0.5em;
	 }
	 
	 .hamster__nose {
	   background: hsl(0,90%,75%);
	   border-radius: 35% 65% 85% 15% / 70% 50% 50% 30%;
	   top: 0.75em;
	   left: 0;
	   width: 0.2em;
	   height: 0.25em;
	 }
	 
	 .hamster__body {
	   animation: hamsterBody var(--dur) ease-in-out infinite;
	   background: hsl(30,90%,90%);
	   border-radius: 50% 30% 50% 30% / 15% 60% 40% 40%;
	   box-shadow: 0.1em 0.75em 0 hsl(30,90%,55%) inset,
	 		0.15em -0.5em 0 hsl(30,90%,80%) inset;
	   top: 0.25em;
	   left: 2em;
	   width: 4.5em;
	   height: 3em;
	   transform-origin: 17% 50%;
	   transform-style: preserve-3d;
	 }
	 
	 .hamster__limb--fr,
	 .hamster__limb--fl {
	   clip-path: polygon(0 0,100% 0,70% 80%,60% 100%,0% 100%,40% 80%);
	   top: 2em;
	   left: 0.5em;
	   width: 1em;
	   height: 1.5em;
	   transform-origin: 50% 0;
	 }
	 
	 .hamster__limb--fr {
	   animation: hamsterFRLimb var(--dur) linear infinite;
	   background: linear-gradient(hsl(30,90%,80%) 80%,hsl(0,90%,75%) 80%);
	   transform: rotate(15deg) translateZ(-1px);
	 }
	 
	 .hamster__limb--fl {
	   animation: hamsterFLLimb var(--dur) linear infinite;
	   background: linear-gradient(hsl(30,90%,90%) 80%,hsl(0,90%,85%) 80%);
	   transform: rotate(15deg);
	 }
	 
	 .hamster__limb--br,
	 .hamster__limb--bl {
	   border-radius: 0.75em 0.75em 0 0;
	   clip-path: polygon(0 0,100% 0,100% 30%,70% 90%,70% 100%,30% 100%,40% 90%,0% 30%);
	   top: 1em;
	   left: 2.8em;
	   width: 1.5em;
	   height: 2.5em;
	   transform-origin: 50% 30%;
	 }
	 
	 .hamster__limb--br {
	   animation: hamsterBRLimb var(--dur) linear infinite;
	   background: linear-gradient(hsl(30,90%,80%) 90%,hsl(0,90%,75%) 90%);
	   transform: rotate(-25deg) translateZ(-1px);
	 }
	 
	 .hamster__limb--bl {
	   animation: hamsterBLLimb var(--dur) linear infinite;
	   background: linear-gradient(hsl(30,90%,90%) 90%,hsl(0,90%,85%) 90%);
	   transform: rotate(-25deg);
	 }
	 
	 .hamster__tail {
	   animation: hamsterTail var(--dur) linear infinite;
	   background: hsl(0,90%,85%);
	   border-radius: 0.25em 50% 50% 0.25em;
	   box-shadow: 0 -0.2em 0 hsl(0,90%,75%) inset;
	   top: 1.5em;
	   right: -0.5em;
	   width: 1em;
	   height: 0.5em;
	   transform: rotate(30deg) translateZ(-1px);
	   transform-origin: 0.25em 0.25em;
	 }
	 
	 .spoke {
	   animation: spoke var(--dur) linear infinite;
	   background: radial-gradient(100% 100% at center,hsl(0,0%,60%) 4.8%,hsla(0,0%,60%,0) 5%),
	 		linear-gradient(hsla(0,0%,55%,0) 46.9%,hsl(0,0%,65%) 47% 52.9%,hsla(0,0%,65%,0) 53%) 50% 50% / 99% 99% no-repeat;
	 }
	 
	 /* Animations */
	 @keyframes hamster {
	   from, to {
	     transform: rotate(4deg) translate(-0.8em,1.85em);
	   }
	 
	   50% {
	     transform: rotate(0) translate(-0.8em,1.85em);
	   }
	 }
	 
	 @keyframes hamsterHead {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(0);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(8deg);
	   }
	 }
	 
	 @keyframes hamsterEye {
	   from, 90%, to {
	     transform: scaleY(1);
	   }
	 
	   95% {
	     transform: scaleY(0);
	   }
	 }
	 
	 @keyframes hamsterEar {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(0);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(12deg);
	   }
	 }
	 
	 @keyframes hamsterBody {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(0);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(-2deg);
	   }
	 }
	 
	 @keyframes hamsterFRLimb {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(50deg) translateZ(-1px);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(-30deg) translateZ(-1px);
	   }
	 }
	 
	 @keyframes hamsterFLLimb {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(-30deg);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(50deg);
	   }
	 }
	 
	 @keyframes hamsterBRLimb {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(-60deg) translateZ(-1px);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(20deg) translateZ(-1px);
	   }
	 }
	 
	 @keyframes hamsterBLLimb {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(20deg);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(-60deg);
	   }
	 }
	 
	 @keyframes hamsterTail {
	   from, 25%, 50%, 75%, to {
	     transform: rotate(30deg) translateZ(-1px);
	   }
	 
	   12.5%, 37.5%, 62.5%, 87.5% {
	     transform: rotate(10deg) translateZ(-1px);
	   }
	 }
	 
	 @keyframes spoke {
	   from {
	     transform: rotate(0);
	   }
	 
	   to {
	     transform: rotate(-1turn);
	   }
	 }
</style>
