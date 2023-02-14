<template>
	<view class="uploadpage">
		<view v-if="imageIsValid == 0">
			<image :src="defaultImage" class="uploadimage"></image>
		</view>
		<view v-else>
			<image v-for="item in imagetemp" :src="item" mode="" class="uploadimage"></image>
		</view>
		<button @click="upload()">Your Food</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imagetemp:[],
				imageIsValid: 0,
				defaultImage: "../../static/defaultImage.jpg"
			}
		},
		methods: {
			upload(){
				uni.chooseImage({
					success: (chooseImageRes) => {
						const tempFilePaths = chooseImageRes.tempFilePaths;
						this.imagetemp=tempFilePaths;
						console.log(this.imagetemp[0])
						this.imageIsValid = 1;
						uni.uploadFile({
							url: "http://localhost:8080/submit",
							filePath:this.imagetemp[0],
							name: 'file',
							formData:{
									 	names:"abcabc"
									 },
							success: function(res) {
								console.log(res.data);
								alert(res.data)
							},
						})
					}
				});
			}
		}
	}
</script>

<style>
	.uploadpage{
		height: 100vh;
		width: 100vw;
		display: flex;
		flex-direction: column;
	}
	button {
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  outline: none;
	  cursor: pointer;
	  width: 150px;
	  height: 50px;
	  background-image: linear-gradient(to top, #D8D9DB 0%, #fff 80%, #FDFDFD 100%);
	  border-radius: 30px;
	  border: 1px solid #8F9092;
	  transition: all 0.2s ease;
	  font-family: "Source Sans Pro", sans-serif;
	  font-size: 14px;
	  font-weight: 600;
	  color: #606060;
	  text-shadow: 0 1px #fff;
	}
	
	button:active {
	  box-shadow: 0 4px 3px 1px #FCFCFC, 0 6px 8px #D6D7D9, 0 -4px 4px #CECFD1, 0 -6px 4px #FEFEFE, inset 0 0 5px 3px #999, inset 0 0 30px #aaa;
	}
	
	button:focus {
	  box-shadow: 0 4px 3px 1px #FCFCFC, 0 6px 8px #D6D7D9, 0 -4px 4px #CECFD1, 0 -6px 4px #FEFEFE, inset 0 0 5px 3px #999, inset 0 0 30px #aaa;
	}
	
	

	.uploadimage {

		size: auto;
		width: 100%;
	}

</style>
