package com.makeupmirror.mbg.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.makeupmirror.mbg.Service.UserService;
import com.makeupmirror.mbg.mapper.UserMapper;
import com.makeupmirror.mbg.pojo.User;
import com.makeupmirror.mbg.Module.Constant.Constant;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static com.makeupmirror.mbg.Module.CosUploadDownload.COS_Upload_Download.downloadFile;
import static com.makeupmirror.mbg.Module.CosUploadDownload.COS_Upload_Download.uploadfile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer login(String userName, String userencryptedData, String userIv, String userAvatar, Byte userGender) {
        User user = new User();
        user.setUserName(userName);
        user.setUserEncrypteddata(userencryptedData);
        user.setUserIv(userIv);
        user.setUserAvatar(userAvatar);
        user.setUserGender(userGender);
        Integer allUsers = this.userMapper.findAllUser(userName);
        if (allUsers == null || allUsers == 0) {
            if (enroll(userName, userencryptedData, userIv, userAvatar, userGender))
                System.out.println("login: new account, enroll successful!");
            return this.userMapper.findAllUser(userName);
        } else {
            System.out.println("login: account already exists.");
            return allUsers;
        }
    }

    public boolean enroll(String userName, String userencryptedData, String userIv, String userAvatar,
                          Byte userGender) {
        User user = new User();
        user.setUserName(userName);
        user.setUserEncrypteddata(userencryptedData);
        user.setUserIv(userIv);
        user.setUserAvatar(userAvatar);
        user.setUserGender(userGender);
        this.userMapper.saveUser(userName, userencryptedData, userIv, userAvatar, userGender);
        return true;
    }

    public void updateFacialData(Integer userId, String userFacialData, String userFacialImage) {
        userMapper.updateFacialData(userId, userFacialData, userFacialImage);

        String base64 = userFacialImage;
        String filePath = Constant.FILE_DOWNLOAD_FOLDER + userId.toString() + ".jpg";
        try {
            Files.write(Paths.get(filePath), Base64.decode(base64), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File localFile = new File(filePath);
        uploadfile(localFile, userId.toString());
    }

    public List<String> getFacialData(Integer userId) {
//        System.out.println(userId);
        String userFacialData = userMapper.getFacialData(userId);
//        System.out.println(userFacialData);
        return facialDataToFaceAttributeUserFeedback(userFacialData);
    }

    @Override
    public String getFacialImage(Integer userId) {
        return userMapper.getFacialImage(userId);
    }

    @Override
    public void downloadMakeupImage(Integer userId, Integer videoId) {
        try {
            downloadFile(userId.toString(), videoId.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> facialDataToFaceAttributeUserFeedback(String facialData) {
        JSONObject facialDataJson = JSONObject.parseObject(facialData);
        JSONObject faceAttributesJson = facialDataJson.getJSONArray("media_info_list")
                                                      .getJSONObject(0)
                                                      .getJSONObject("media_extra")
                                                      .getJSONArray("faces")
                                                      .getJSONObject(0)
                                                      .getJSONObject("face_attributes");
        List<String> faceAttribute = new ArrayList<>(8);
        faceAttribute.add(faceAttributesJson.getJSONObject("face_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("cheek_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("jaw_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("eye_shape").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("eye_distance").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("mouth_thickness").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("lip_peak").getString("value"));
        faceAttribute.add(faceAttributesJson.getJSONObject("nose_shape").getString("value"));
        return faceAttribute;
    }
}