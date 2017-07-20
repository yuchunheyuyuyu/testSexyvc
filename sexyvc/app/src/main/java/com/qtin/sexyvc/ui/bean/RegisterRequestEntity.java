package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/13.
 */

public class RegisterRequestEntity {
    private String username;
    private int account_type;
    private String password;
    private String nickname;
    private String avatar;
    private int gender;
    private String wx_union_id;
    private String device_token;
    private int client_type;

    public int getClient_type() {
        return client_type;
    }

    public void setClient_type(int client_type) {
        this.client_type = client_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getWx_union_id() {
        return wx_union_id;
    }

    public void setWx_union_id(String wx_union_id) {
        this.wx_union_id = wx_union_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
/**

 username 	主键，手机传手机号，其他传openid 	是
 account_type 	类型 1：手机号，2：微信；3：QQ 	是
 password 	密码 	account_type为1是必填
 nickname 	昵称 	否
 avatar 	头像 	否
 gender 	性别 	否
 wxunionid 	微信union_id 	否

 */