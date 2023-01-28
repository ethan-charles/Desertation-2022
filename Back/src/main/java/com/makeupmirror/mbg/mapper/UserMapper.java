package com.makeupmirror.mbg.mapper;

import com.makeupmirror.mbg.pojo.User;
import com.makeupmirror.mbg.utils.MyMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Select("select user_id from user where user_name = #{userName}")
    Integer findAllUser(@Param("userName") String userName);

    @Insert("insert into user (user_name, user_encryptedData, user_IV, user_avatar, user_gender) values (#{userName}, #{userencryptedData}, #{userIv}, #{userAvatar}, #{userGender})")
    void saveUser(@Param("userName") String userName,
                  @Param("userencryptedData") String userencryptedData,
                  @Param("userIv") String userIv,
                  @Param("userAvatar") String userAvatar,
                  @Param("userGender") Byte userGender
    );

    @Select("select user_IV from user where user_id = #{userId}")
    String findById(@Param("userId") Integer userId);

    //    @Update("insert into user (user_facialdata) values (#{userFacialData}) where user_id = #{userId}")
    @Update("UPDATE user SET user_facialdata = #{userFacialData}, user_facialimage = #{userFacialImage} WHERE user_id = #{userId}")
    void updateFacialData(@Param("userId") Integer userId,
                          @Param("userFacialData") String userFacialData,
                          @Param("userFacialImage") String userFacialImage
    );

    @Select("SELECT user_facialdata FROM user WHERE user_id = #{userId}")
    String getFacialData(@Param("userId") int userId);

    @Select("SELECT user_facialimage FROM user WHERE user_id = #{userId}")
    String getFacialImage(@Param("userId") int userId);
}
