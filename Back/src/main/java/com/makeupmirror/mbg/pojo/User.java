package com.makeupmirror.mbg.pojo;


import javax.persistence.*;

@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_IV")
    private String userIv;

    @Column(name = "user_encryptedData")
    private String userEncrypteddata;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "current_mirror_id")
    private Long currentMirrorId;

    @Column(name = "user_gender")
    private Byte userGender;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "user_facialdata")
    private String userFacialdata;

    public User(Long userId, String userName, String userIv, String userEncrypteddata, String userAvatar, Long currentMirrorId, Byte userGender, String openId, String userFacialdata) {
        this.userId = userId;
        this.userName = userName;
        this.userIv = userIv;
        this.userEncrypteddata = userEncrypteddata;
        this.userAvatar = userAvatar;
        this.currentMirrorId = currentMirrorId;
        this.userGender = userGender;
        this.openId = openId;
        this.userFacialdata = userFacialdata;
    }

    public User(){
        super();
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user_IV
     */
    public String getUserIv() {
        return userIv;
    }

    /**
     * @param userIv
     */
    public void setUserIv(String userIv) {
        this.userIv = userIv;
    }

    /**
     * @return user_encryptedData
     */
    public String getUserEncrypteddata() {
        return userEncrypteddata;
    }

    /**
     * @param userEncrypteddata
     */
    public void setUserEncrypteddata(String userEncrypteddata) {
        this.userEncrypteddata = userEncrypteddata;
    }

    /**
     * @return user_avatar
     */
    public String getUserAvatar() {
        return userAvatar;
    }

    /**
     * @param userAvatar
     */
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    /**
     * @return current_mirror_id
     */
    public Long getCurrentMirrorId() {
        return currentMirrorId;
    }

    /**
     * @param currentMirrorId
     */
    public void setCurrentMirrorId(Long currentMirrorId) {
        this.currentMirrorId = currentMirrorId;
    }

    /**
     * @return user_gender
     */
    public Byte getUserGender() {
        return userGender;
    }

    /**
     * @param userGender
     */
    public void setUserGender(Byte userGender) {
        this.userGender = userGender;
    }

    /**
     * @return open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return user_facialdata
     */
    public String getUserFacialdata() {
        return userFacialdata;
    }

    /**
     * @param userFacialdata
     */
    public void setUserFacialdata(String userFacialdata) {
        this.userFacialdata = userFacialdata;
    }
}