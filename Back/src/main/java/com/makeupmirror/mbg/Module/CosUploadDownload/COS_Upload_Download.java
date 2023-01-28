package com.makeupmirror.mbg.Module.CosUploadDownload;

import com.makeupmirror.mbg.Module.Constant.Constant;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class COS_Upload_Download {

    private static String DownloadFolder = Constant.FILE_DOWNLOAD_FOLDER;

//    public static void main(String[] args) throws IOException {
//        String localFilePath = "D:/desktop/123.png";
////        File localFile = new File(localFilePath);
////        uploadfile(localFile, "999");
//        downloadFile("999", "1");
//    }

    public COS_Upload_Download() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDDJBoDQBfsoT1JK5YkfJfkbBUXJp42j9J";
        String secretKey = "yf7HAm6AxVmlTu3vWqtfBPM1QYkgLjqg";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
    }

    public static void uploadfile(File localFile, String userId) {
        String secretId = "AKIDDJBoDQBfsoT1JK5YkfJfkbBUXJp42j9J";
        String secretKey = "yf7HAm6AxVmlTu3vWqtfBPM1QYkgLjqg";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式


        // 指定文件将要存放的存储桶
        String bucketName = "mirror-video-1305927979";


        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String COSpath = "/用户图片/";
        String file_name = userId + ".jpg";
        String key = COSpath + file_name;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }

    public static String downloadFile(String userId, String tryMakeId) throws IOException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDDJBoDQBfsoT1JK5YkfJfkbBUXJp42j9J";
        String secretKey = "yf7HAm6AxVmlTu3vWqtfBPM1QYkgLjqg";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


        // 前面这些的话就是登陆用的没啥好说的

        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "mirror-video-1305927979";
        // 指定文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
        String key;

        GetObjectRequest getObjectRequest;

        //// 方法1 获取下载输入流
        //        getObjectRequest = new GetObjectRequest(bucketName, key);
        //        COSObject cosObject = cosClient.getObject(getObjectRequest);
        //        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        //// 下载对象的 CRC64
        //        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        //// 关闭输入流
        //        cosObjectInput.close();

        // 方法2 下载文件到本地的路径，例如 D 盘的某个目录

        // 然后下面就是用来下载的
        String outputFilePath = DownloadFolder + "user" + userId + ".jpg";
        File downFile = new File(outputFilePath);
        key = "/用户图片/" + userId + ".jpg";
        getObjectRequest = new GetObjectRequest(bucketName, key);
        try {
            ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        } catch (Exception e) {
            System.out.println("没有找到user文件");
            return "No User File";
        }
        //就是下载对象存储的照片到本地的某个文件夹，我这边的话，是在刚才那个downloadfolder里面

        outputFilePath = DownloadFolder + "trymakeup/" + tryMakeId + ".png";
        downFile = new File(outputFilePath);
        key = "/视频图片/image/" + tryMakeId + ".png";
        getObjectRequest = new GetObjectRequest(bucketName, key);
        try {
            ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        } catch (Exception e) {
            System.out.println("没有找到tryMakeUp文件");
            return "No TryMakeUp File";
        }
        // user的图片和尝试妆容的图片放在两个文件夹里，如果没找到的话，就返回没有

        return userId + ".jpg";
    }

//    public static void listbucket(){
//        // 1 初始化用户身份信息（secretId, secretKey）。
//        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
//        String secretId = "AKIDDJBoDQBfsoT1JK5YkfJfkbBUXJp42j9J";
//        String secretKey = "yf7HAm6AxVmlTu3vWqtfBPM1QYkgLjqg";
//        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
//        Region region = new Region("ap-nanjing");
//        ClientConfig clientConfig = new ClientConfig(region);
//        // 这里建议设置使用 https 协议
//        // 从 5.6.54 版本开始，默认使用了 https
//        clientConfig.setHttpProtocol(HttpProtocol.https);
//        // 3 生成 cos 客户端。
//        COSClient cosClient = new COSClient(cred, clientConfig);
//
//        List<Bucket> buckets = cosClient.listBuckets();
//        for (Bucket bucketElement : buckets) {
//            String bucketName = bucketElement.getName();
//            String bucketLocation = bucketElement.getLocation();
//            System.out.println(bucketName);
//        }
//    }
//
//    public static void listInBucket() {
//        // 1 初始化用户身份信息（secretId, secretKey）。
//        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
//        String secretId = "AKIDDJBoDQBfsoT1JK5YkfJfkbBUXJp42j9J";
//        String secretKey = "yf7HAm6AxVmlTu3vWqtfBPM1QYkgLjqg";
//        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
//        Region region = new Region("ap-nanjing");
//        ClientConfig clientConfig = new ClientConfig(region);
//        // 这里建议设置使用 https 协议
//        // 从 5.6.54 版本开始，默认使用了 https
//        clientConfig.setHttpProtocol(HttpProtocol.https);
//        // 3 生成 cos 客户端。
//        COSClient cosClient = new COSClient(cred, clientConfig);
//
//        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
//        String bucketName = "mirror-video-1305927979";
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
//        // 设置bucket名称
//        listObjectsRequest.setBucketName(bucketName);
//        // prefix表示列出的object的key以prefix开始
//        //        listObjectsRequest.setPrefix("images/");
//        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
//        listObjectsRequest.setDelimiter("");
//        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
//        listObjectsRequest.setMaxKeys(1000);
//        ObjectListing objectListing = null;
//        do {
//            try {
//                objectListing = cosClient.listObjects(listObjectsRequest);
//            } catch (CosClientException e) {
//                e.printStackTrace();
//                return;
//            }
//            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
//            List<String> commonPrefixs = objectListing.getCommonPrefixes();
//
//            // object summary表示所有列出的object列表
//            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
//            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
//                // 文件的路径key
//                String key = cosObjectSummary.getKey();
//                // 文件的etag
//                String etag = cosObjectSummary.getETag();
//                // 文件的长度
//                long fileSize = cosObjectSummary.getSize();
//                // 文件的存储类型
//                String storageClasses = cosObjectSummary.getStorageClass();
//                System.out.println(key + ", size:" + fileSize);
//            }
//
//            String nextMarker = objectListing.getNextMarker();
//            listObjectsRequest.setMarker(nextMarker);
//        } while (objectListing.isTruncated());
//    }

}
