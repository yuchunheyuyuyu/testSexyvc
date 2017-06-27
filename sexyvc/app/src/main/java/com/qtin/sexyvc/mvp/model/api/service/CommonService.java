package com.qtin.sexyvc.mvp.model.api.service;

import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.flash.bean.FlashBean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBean;
import com.qtin.sexyvc.ui.request.ChangeGroupRequest;
import com.qtin.sexyvc.ui.request.FollowRequest;
import com.qtin.sexyvc.ui.subject.bean.DetailBean;
import com.qtin.sexyvc.ui.subject.bean.SubjectBean;

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

    /**
     * 投资人分组
     * @param token
     * @param page
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/group")
    Observable<BaseEntity<GroupEntity>> queryInvestorGroup(@Field("token")String token,@Field("investor_id")long investor_id,@Field("page")int page,@Field("page_size")int page_size);

    /**
     * 创建投资人分组
     * @param token
     * @param group_name
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/group/add")
    Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(@Field("token")String token,@Field("group_name")String group_name);

    /**
     * 编辑投资人分组
     * @param token
     * @param group_id
     * @param group_name
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/group/edit")
    Observable<CodeEntity> updateInvestorGroup(@Field("token")String token,@Field("group_id")long group_id,@Field("group_name")String group_name,@Field("status")int status);

    /**
     * 投资人分组详情
     * @param token
     * @param group_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/group/detail")
    Observable<BaseEntity<ConcernEntity>> queryGroupDetail(@Field("token")String token, @Field("group_id")long group_id, @Field("page")int page, @Field("page_size")int page_size);

    /**
     * 首页信息
     * @return
     */
    @POST("api/page/index")
    Observable<BaseEntity<HomeBean>> queryHome();

    /**
     * 投资人列表页
     * @param token
     * @param page
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("api/investor/list")
    Observable<BaseEntity<InvestorBean>> querySelectedInvestor(@Field("token")String token,@Field("page")int page,@Field("page_size")int page_size);

    /**
     * 关注投资人
     * @return
     */
    @POST("api/user/contact/investor/import")
    Observable<CodeEntity> followInvestor(@Body FollowRequest entity);

    /**
     * 设置投资人分组
     * @return
     */
    @POST("api/user/contact/investor/change/group")
    Observable<CodeEntity> changeGroup(@Body ChangeGroupRequest request);

    /**
     * 快讯列表页
     * @param flash_id
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/flash")
    Observable<BaseEntity<FlashBean>> queryFlashList(@Field("flash_id")long flash_id,@Field("page_size")int page_size);

    /**
     * 编辑联系人手机号码
     * @param token
     * @param contact_id
     * @param phone
     * @param backup_phone
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/edit")
    Observable<CodeEntity> editContactPhone(@Field("token")String token,@Field("contact_id")long contact_id,@Field("phone")String phone,@Field("backup_phone")String backup_phone);

    /**
     * 编辑联系人邮箱
     * @param token
     * @param contact_id
     * @param email
     * @param backup_email
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/edit")
    Observable<CodeEntity> editContactEmail(@Field("token")String token,@Field("contact_id")long contact_id,@Field("email")String email,@Field("backup_email")String backup_email);

    /**
     * 编辑联系人微信号
     * @param token
     * @param contact_id
     * @param wechat
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/edit")
    Observable<CodeEntity> editContactWechat(@Field("token")String token,@Field("contact_id")long contact_id,@Field("wechat")String wechat);

    /**
     * 编辑联系人备注
     * @param token
     * @param contact_id
     * @param remark
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/edit")
    Observable<CodeEntity> editContactemark(@Field("token")String token,@Field("contact_id")long contact_id,@Field("remark")String remark);

    /**
     * 专题列表页
     * @param subject_id
     * @param page_size
     * @param need_banner
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/subject")
    Observable<BaseEntity<SubjectBean>> querySubjectList(@Field("subject_id")long subject_id,@Field("page_size")int page_size,@Field("need_banner")int need_banner);

    /**
     * 专题详情页
     * @param token
     * @param subject_id
     * @param page_size
     * @param reply_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/subject/detail")
    Observable<BaseEntity<DetailBean>> querySubjectDetail(@Field("token")String token,@Field("subject_id")long subject_id,
                                                          @Field("page_size")int page_size,@Field("reply_id")long reply_id);

    /**
     * 回复
     * @param token
     * @param object_type 对象类型 1：评论；2：专题
     * @param object_id 对象ID
     * @param reply_id 回复评论ID
     * @param reply_content 回复内容
     * @return
     */
    @FormUrlEncoded
    @POST("api/action/reply")
    Observable<BaseEntity<ReplyIdBean>> reply(@Field("token")String token,@Field("object_type")int object_type,@Field("object_id")long object_id,
                                              @Field("reply_id")long reply_id,@Field("reply_content")String reply_content);

    /**
     * 点赞
     * @param token
     * @param object_type 对象类型 1：评论；2：专题；3：回复
     * @param object_id 对象ID
     * @param handle_type 操作类型 1 ：新增赞；0：取消赞
     * @return
     */
    @FormUrlEncoded
    @POST("api/action/praise")
    Observable<CodeEntity> praise(@Field("token")String token,@Field("object_type")int object_type,
                                  @Field("object_id")long object_id,@Field("handle_type")int handle_type);
}

