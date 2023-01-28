package com.makeupmirror.mbg.Controller;

import com.makeupmirror.mbg.Service.UserService;
import com.makeupmirror.mbg.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.makeupmirror.mbg.Module.CosUploadDownload.COS_Upload_Download.uploadfile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Integer login(@RequestParam(value = "user_name") String user_name,
                         @RequestParam(value = "user_encryptedData") String user_encryptedData,
                         @RequestParam(value = "user_IV") String user_iv,
                         @RequestParam(value = "user_avatar") String user_avatar,
                         @RequestParam(value = "user_gender") Byte user_gender) {
        return this.userService.login(user_name, user_encryptedData, user_iv, user_avatar, user_gender);
    }

    @PostMapping("/updateFacialData")
    @ResponseBody
    public void updateFacialData(@RequestParam(value = "user_id") Integer user_id,
                                 @RequestParam(value = "user_facialData") String user_facialData,
                                 @RequestParam(value = "user_facialImage") String user_facialImage) {
        userService.updateFacialData(user_id, user_facialData, user_facialImage);
    }

    @PostMapping("/getFacialData")
    @ResponseBody
    public List<String> getFacialData(@RequestParam(value = "user_id") Integer userId) {
        return userService.getFacialData(userId);
    }

    @PostMapping("/getFacialImage")
    @ResponseBody
    public String getFacialImage(@RequestParam(value = "user_id") Integer userId) {
        return userService.getFacialImage(userId);
    }

    @PostMapping("/downloadMakeupImage")
    @ResponseBody
    public void downloadMakeupImage(@RequestParam(value = "user_id") Integer userId,
                                    @RequestParam(value = "video_id") Integer videoId) {
        userService.downloadMakeupImage(userId, videoId);
    }
}
