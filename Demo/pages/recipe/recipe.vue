<template>
	<view class="recipePage">
		<view class="title">Recipe Details</view>
		<view>
			{{food}}
		</view>
		<view v-for="(recipe, index) in recipes">
			{{ index }}: {{recipe}}
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				food:"aaaaa,aaaaaaaa,aaa,a",
				recipes:["-In a large skillet, heat oil over medium heat.-Add garlic and onion and cook until tender."],
			}
		},
		// onLoad() {
			// this.food = uni.getStorageSync('food')
			// let recipeList = uni.getStorageSync('recipe')
			// this.recipes = recipeList.split('-')
			// this.recipes.shift()
		// },
		methods: {
			
		}
	}
</script>

<style>
	.recipePage{
		display: flex;
		flex-direction: column;
	}
	.title {
		text-align: center;
		
		font-size: 60rpx;
		font-weight: bold;
		color: #333;
		text-shadow: rgba(62,66,66,0.4) 0px 7px 24px;
		height: 20%;
		-webkit-box-shadow:0px 10px 39px 30px rgba(62,66,66,0.22);
		-moz-box-shadow: 0px 10px 39px 30px rgba(62,66,66,0.22);
		box-shadow: 0px 10px 39px 30px rgba(62,66,66,0.22);
	}
</style>
