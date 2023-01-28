package com.makeupmirror.mbg.Service;

import java.util.List;

public interface UserService {

    Integer login(String userName, String userencryptedData, String userIv, String userAvatar, Byte userGender);

    void updateFacialData(Integer userId, String userFacialData, String userFacialImage);

    List<String> getFacialData(Integer userId);

    String getFacialImage(Integer userId);

    void downloadMakeupImage(Integer userId, Integer videoId);
}
