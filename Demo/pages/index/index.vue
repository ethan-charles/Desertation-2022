<template>	
		<view >
			<view v-if="index == 0" class="homepage">
				<view class="title">Food Master</view>
				<image src="../../static/text1.png" class="textimage"></image>
				<view class="buttonbox">
					<button @click="gofood()" class="cssbuttons-io-button">
					<span>Upload Ingredient</span>
					</button>
					<button @click="godish()" class="cssbuttons-io-button">
					<span>Upload Dishes</span>
					</button>
				</view>
			</view>
			<view v-if="index == 1" class="searchpage">
				<view class="searchbox">
				        <d-search-log 
				        :color_border="color_border"
				        :color_text="color_border"
				        :search_list_hot="search_list_hot"
				        :store_key="store_key"
				        @onClickDelAllApi="onClickDelAll"
				        @onSearchNameApi="onSearchName"
				        ></d-search-log>
				</view>
			</view>
			<view v-if="index == 2" class="formpage">
				<form class="form">
				    <p id="heading">Login</p>
				    <div class="field">
				    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
				    <path d="M13.106 7.222c0-2.967-2.249-5.032-5.482-5.032-3.35 0-5.646 2.318-5.646 5.702 0 3.493 2.235 5.708 5.762 5.708.862 0 1.689-.123 2.304-.335v-.862c-.43.199-1.354.328-2.29.328-2.926 0-4.813-1.88-4.813-4.798 0-2.844 1.921-4.881 4.594-4.881 2.735 0 4.608 1.688 4.608 4.156 0 1.682-.554 2.769-1.416 2.769-.492 0-.772-.28-.772-.76V5.206H8.923v.834h-.11c-.266-.595-.881-.964-1.6-.964-1.4 0-2.378 1.162-2.378 2.823 0 1.737.957 2.906 2.379 2.906.8 0 1.415-.39 1.709-1.087h.11c.081.67.703 1.148 1.503 1.148 1.572 0 2.57-1.415 2.57-3.643zm-7.177.704c0-1.197.54-1.907 1.456-1.907.93 0 1.524.738 1.524 1.907S8.308 9.84 7.371 9.84c-.895 0-1.442-.725-1.442-1.914z"></path>
				    </svg>
				      <input autocomplete="off" placeholder="Username" class="input-field" type="text">
				    </div>
				    <div class="field">
				    <svg class="input-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
				    <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"></path>
				    </svg>
				      <input placeholder="Password" class="input-field" type="password">
				    </div>
				    <div class="btn">
				    <button class="button1">Login</button>
				    <button class="button2">Sign Up</button>
				    </div>
				    <button class="button3">Forgot Password</button>
				</form>
			</view>
			<MagicNavigationBar :items="items" :height="80" :indicatorSize="60"
			@onTabSelect="onTab">
				<template v-slot:text="{text}">
					{{text}}
				</template>				
			</MagicNavigationBar>
		</view>		
		
</template>

<script>
	import MagicNavigationBar from "@/uni_modules/jorbin-MagicNavigationBar/components/jorbin-MagicNavigationBar/jorbin-MagicNavigationBar.vue"
	import dSearchLog from '@/uni_modules/d-search-log/components/d-search-log/d-search-log.vue'

	export default {
		components:{
			MagicNavigationBar,
			"dSearchLog": dSearchLog
		},
		data() {
			return {
				color_border:"#000000",
				                search_list_hot:[],
				                store_key:'search_list',
				index: 0,
				items:[
					{
						icon:{src:require("@/static/home.png"),width:30,height:30},
						text:'HOME',
						
					},
					{
						icon:{src:require("@/static/search.png"),width:40,height:40},
						text:'SEARCH',
						
					},
					{
						icon:{src:require("@/static/user.png"),width:30,height:30},
						text:'USER',
						
					},
				],
				
			}
		},
		onLoad() {
			 this.search_list_hot = [
			                'beef','noodles','pancake','Beans on toast','Chicken tikka masala'
			            ]
		},
		methods: {
			onTab(index,item){
				console.log(index,item);
				this.index=index;
			},
			onClickDelAll() {
			                console.log('[父级接收事件]：删除全部搜索记录')
			            },
			            onSearchName(e) {
			                console.log('[父级接收事件]：点击搜索:'+e)
			            },
			gofood() {
				
			},
			godish() {
				uni.navigateTo({
					url: '/pages/upload/upload'
				});
			}
		}
	}
</script>

<style>
	.homepage{
		height: 100vh;
		width: 100vw;
		background: url('https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-03/plant-based-food-mc-220323-02-273c7b.jpg') no-repeat center center;
		background-size: auto;
		display: flex;
		flex-direction: column;
	}
	.title{
		margin-top: 5%;
		font-size: 20vw;
		text-align: center;
		color: #fff;
		text-shadow: 
		    0 0 10px #0ebeff,
		    0 0 20px #0ebeff,
		    0 0 50px #0ebeff,
		    0 0 100px #55aa00,
		    0 0 200px #55aa00
	}
	.textimage {
		display: block;
		margin: 0 auto;
		margin-top: -100rpx;
		margin-bottom: -100rpx;
		height: 200px;
		width: 100%;
	}
	.cssbuttons-io-button {
	 display: flex;
	 align-items: center;
	 justify-content: center;
	 font-family: inherit;
	 font-weight: 500;
	 font-size: 20px;
	 padding: 0.8em 1.5em 0.8em 1.2em;
	 color: white;
	 background: #ad5389;
	 background: linear-gradient(0deg, rgba(85, 170, 0, 1.0) 0%, rgba(114, 254, 243, 1.0) 100%);
	 border: none;
	 box-shadow: 0 0.7em 1.5em -0.5em #4d36d0be;
	 letter-spacing: 0.05em;
	 border-radius: 20em;
	 margin-top: 0.8em;
	 
	}
	
	.buttonbox {
		width: 70%;
		opacity: 93%;
		margin-left: 15%;
	}
	
	.cssbuttons-io-button:active {
	 box-shadow: 0 0.3em 1em -0.5em #4d36d0be;
	}
	.searchpage{
		background: linear-gradient(117deg,#72fef3,#55aa00);
		height: 100vh;
		width: 100vw;
		background-size: auto;
		display: flex;
		flex-direction: column;
	}
	.searchbox{
		margin-top: 1em;
	}
	.my-theme-bg {
	        background: linear-gradient(117deg,#60DF9D,#31CB7B);
	        color:#fff;
	}
	.formpage {
		background: linear-gradient(117deg,#3e3e3e,#000000);
		height: 100vh;
		width: 100vw;
		background-size: auto;
		display: flex;
		flex-direction: column;
	}
	
	.form {
	  margin-top: 5em;
	  gap: 10px;
	  padding-left: 2em;
	  padding-right: 2em;
	  padding-bottom: 0.4em;
	  background-color: #171717;
	  border-radius: 25px;
	  transition: .4s ease-in-out;
	}
	
	#heading {
	  text-align: center;
	  margin: 2em;
	  color: rgb(255, 255, 255);
	  font-size: 1.2em;
	}
	
	.field {
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  gap: 0.5em;
	  border-radius: 25px;
	  padding: 0.6em;
	  border: none;
	  outline: none;
	  color: white;
	  background-color: #171717;
	  box-shadow: inset 2px 5px 10px rgb(5, 5, 5);
	}
	
	.input-icon {
	  height: 1.3em;
	  width: 1.3em;
	  fill: white;
	}
	
	.input-field {
	  background: none;
	  border: none;
	  outline: none;
	  width: 100%;
	  color: #d3d3d3;
	}
	
	.form .btn {
	  display: flex;
	  justify-content: center;
	  flex-direction: row;
	  margin-top: 2em;
	}
	
	.button1 {
	  padding: 0.5em;
	  padding-left: 1.1em;
	  padding-right: 1.1em;
	  border-radius: 10px;
	  margin-right: 0.5em;
	  border: none;
	  outline: none;
	  transition: .4s ease-in-out;
	  background-color: #171717;
	  color: white;
	}
	
	.button1:active {
	  background-color: black;
	  color: white;
	}
	
	.button2 {
	  padding: 0.5em;
	  padding-left: 2.3em;
	  padding-right: 2.3em;
	  border-radius: 5px;
	  border: none;
	  outline: none;
	  transition: .4s ease-in-out;
	  background-color: #171717;
	  color: white;
	}
	
	.button2:active {
	  background-color: black;
	  color: white;
	}
	
	.button3 {
	  margin-bottom: 3em;
	  padding: 0.5em;
	  border-radius: 5px;
	  border: none;
	  outline: none;
	  transition: .4s ease-in-out;
	  background-color: #171717;
	  color: white;
	}
	
	.button3:active {
	  background-color: gray;
	  color: white;
	}
</style>
