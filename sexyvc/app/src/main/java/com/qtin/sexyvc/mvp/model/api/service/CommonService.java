package com.qtin.sexyvc.mvp.model.api.service;

import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.mvp.model.entity.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

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
     * 注册
     * @param entity
     * @return
     */
    @POST("user/register")
    Observable<BaseEntity<UserEntity>> doRegister(@Body RegisterRequestEntity entity);

    /**
     * 登录
     * @param username
     * @param account_type
     * @param password
     * @return
     */

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseEntity<UserEntity>> login(@Field("username")String username, @Field("account_type")int account_type,
                    @Field("password")String password);

    /**
     * 获取验证码
     * @param mobile
     * @param code_type
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/code")
    Observable<CodeEntity> getVertifyCode(@Field("mobile")String mobile, @Field("code_type")String code_type);

    /**
     * 验证验证码的正确性
     * @param mobile
     * @param code_type
     * @param code_value
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/check")
    Observable<CodeEntity> validateCode(@Field("mobile")String mobile, @Field("code_type")String code_type,@Field("code_value")String code_value);
}
