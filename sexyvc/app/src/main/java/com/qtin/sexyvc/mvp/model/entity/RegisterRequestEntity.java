package com.qtin.sexyvc.mvp.model.entity;

/**
 * Created by ls on 17/3/6.
 */

public class RegisterRequestEntity {

    private int usertype;
    private String username;
    private String nickname;
    private String avatar;

    private int gender;
    private String country;
    private String province;
    private String city;

    private String privilege;
    private String wx_unionid;
    private String device_token;
    private String mobile_code;


    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getWx_unionid() {
        return wx_unionid;
    }

    public void setWx_unionid(String wx_unionid) {
        this.wx_unionid = wx_unionid;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getMobile_code() {
        return mobile_code;
    }

    public void setMobile_code(String mobile_code) {
        this.mobile_code = mobile_code;
    }
}
/**
 usertype 	用户注册类型，0：手机注册，1：微信，2：微博，3：QQ 	是
 username 	用户名，第三方登录传获取信息 	是
 nickname 	昵称 	否
 avatar 	头像 	否

 gender 	性别，1：男 ，2：女，0：未知 	否
 country 	国家 	否
 province 	省份 	否
 city 	城市 	否

 privilege 	兴趣爱好 	否
 wx_unionid 	微信unionid，微信多平台打通 	否
 device_token 	设备token，友盟推送 	否
 mobile_code 	手机号注册，需配合验证码 	否

 */