<template>
	<view>
		<button @click="upload()">1111</button>
		<image v-for="item in imagetemp" :src="item" mode=""></image>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imagetemp:[],
				
			}
		},
		methods: {
			upload(){
				uni.chooseImage({
					success: (chooseImageRes) => {
						const tempFilePaths = chooseImageRes.tempFilePaths;
						this.imagetemp=tempFilePaths;
						console.log(this.imagetemp[0])
						uni.uploadFile({
							url: "http://localhost:8080/submit",
							filePath:this.imagetemp[0],
							name: 'file',
							formData:{
									 	names:"abcabc"
									 },
							success: function(res) {
								console.log(res.data);
							},
						})
					}
				});
			}
		}
	}
</script>

<style>

</style>
