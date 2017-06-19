package com.qtin.sexyvc.mvp.model.api.service;

import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 存放通用的一些API
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface CommonService {

    /**
     * 旧版获取七牛token，可删除
     * @param url
     * @return
     */
    @POST
    Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(@Url String url);

    /**
     * 旧版注册，可删除
     * @param entity
     * @return
     */
    @POST("user/register")
    Observable<BaseListEntity<LoginEntity>> register(@Body com.qtin.sexyvc.mvp.model.entity.RegisterRequestEntity entity);

    /**
     * 获取七牛token
     * @param is_protected 是否是保密文件
     * @return
     */
    @FormUrlEncoded
    @POST("public/qiniu/token")
    Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(@Field("is_protected") int is_protected);

    /**
     * Type 列表
     * @param type_key 投资行业 common_domain 投资阶段 common_stage
     * @return
     */
    @FormUrlEncoded
    @POST("public/type/list")
    Observable<BaseListEntity<FilterEntity>> getType(@Field("type_key") String type_key);

    /**
     * 注册
     * @param entity
     * @return
     */
    @POST("api/user/register")
    Observable<BaseEntity<UserEntity>> doRegister(@Body RegisterRequestEntity entity);

    /**
     * 登录
     * @param username
     * @param account_type
     * @param password
     * @return
     */

    @FormUrlEncoded
    @POST("api/user/login")
    Observable<BaseEntity<UserEntity>> login(@Field("username")String username, @Field("account_type")int account_type,
                    @Field("password")String password,@Field("device_token")String device_token);

    /**
     * 获取验证码
     * @param mobile
     * @param code_type
     * @return
     */
    @FormUrlEncoded
    @POST("api/mobile/code")
    Observable<CodeEntity> getVertifyCode(@Field("mobile")String mobile, @Field("code_type")String code_type);

    /**
     * 验证验证码的正确性
     * @param mobile
     * @param code_type
     * @param code_value
     * @return
     */
    @FormUrlEncoded
    @POST("api/mobile/check")
    Observable<BaseEntity<BindEntity>> validateCode(@Field("mobile")String mobile, @Field("code_type")String code_type, @Field("code_value")String code_value);

    /**
     * 绑定手机号
     * @param token
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/bind")
    Observable<CodeEntity> bindMobile(@Field("token")String token,@Field("mobile") String mobile,@Field("password") String password);

    /**
     * 重置密码
     * @param code_value
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/forget")
    Observable<CodeEntity> resetPassword(@Field("code_value")String code_value,@Field("mobile")String mobile,@Field("password")String password);

    /**
     * 修改密码
     * @param token
     * @param old_password
     * @param new_password
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/reset")
    Observable<CodeEntity> modifyPassword(@Field("token")String token,@Field("old_password")String old_password,@Field("new_password")String new_password);

    /**
     * 获取个人信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info")
    Observable<BaseEntity<UserInfoEntity>> getUserInfo(@Field("token")String token);

    /**
     * 修改昵称
     * @param token
     * @param u_nickname
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editNick(@Field("token")String token,@Field("u_nickname")String u_nickname);

    /**
     * 修改性别
     * @param token
     * @param u_gender
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editSex(@Field("token")String token,@Field("u_gender")int u_gender);

    /**
     * 修改头像
     * @param token
     * @param u_avatar
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editAvatar(@Field("token")String token,@Field("u_avatar")String u_avatar);

    /**
     * 修改个性签名
     * @param token
     * @param u_signature
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editSignature(@Field("token")String token,@Field("u_signature")String u_signature);

    /**
     * 修改备用手机号
     * @param token
     * @param u_backup_phone
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editMobile(@Field("token")String token,@Field("u_backup_phone")String u_backup_phone);

    /**
     * 修改邮箱
     * @param token
     * @param u_email
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editEmail(@Field("token")String token,@Field("u_email")String u_email,@Field("u_backup_email")String u_backup_email);

}
