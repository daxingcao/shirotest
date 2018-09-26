package com.caodaxing.entity;

public class UserInfo {
    /**
     * 用户信息ID
     */
    private Integer infoId;

    /**
     * 用户真实姓名
     */
    private String userName;

    /**
     * 用户年龄
     */
    private Integer userAge;

    /**
     * 用户性别 0 未知 1 男 2 女
     */
    private Integer userGander;

    /**
     * 用户居住地址
     */
    private String userAddress;

    /**
     * 用户QQ号
     */
    private String userQq;

    /**
     * 用户微信号
     */
    private String userWechat;

    /**
     * 用户手机
     */
    private String userMobile;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Integer getUserGander() {
        return userGander;
    }

    public void setUserGander(Integer userGander) {
        this.userGander = userGander;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}