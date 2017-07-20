package com.qtin.sexyvc.mvp.model.api.service;

import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommentIdBean;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ContactBean;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.FundBackEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.IdBean;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.comment.detail.bean.CommentBean;
import com.qtin.sexyvc.ui.comment.list.frag.bean.CommentItemsBean;
import com.qtin.sexyvc.ui.flash.bean.FlashBean;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBean;
import com.qtin.sexyvc.ui.request.ChangeContactGroupRequest;
import com.qtin.sexyvc.ui.request.ChangeInvestorGroupRequest;
import com.qtin.sexyvc.ui.request.ChangeReadStatusRequest;
import com.qtin.sexyvc.ui.request.CreateInvestorRequest;
import com.qtin.sexyvc.ui.request.FollowRequest;
import com.qtin.sexyvc.ui.request.InvestorRequest;
import com.qtin.sexyvc.ui.request.RateRequest;
import com.qtin.sexyvc.ui.road.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.bean.RoadRequest;
import com.qtin.sexyvc.ui.subject.bean.DetailBean;
import com.qtin.sexyvc.ui.subject.bean.SubjectBean;
import com.qtin.sexyvc.ui.user.bean.MsgItems;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectEntity;

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
    Observable<Typebean> getType(@Field("type_key") String type_key);

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
    Observable<BaseEntity<UserEntity>> login(@Field("client_type")int client_type,@Field("username")String username, @Field("account_type")int account_type,
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
     * 修改机构职位
     * @param token
     * @param u_auth_type
     * @param u_company
     * @param u_title
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info/edit")
    Observable<CodeEntity> editPosition(@Field("token")String token,@Field("u_auth_type")int u_auth_type,
                                        @Field("u_company")String u_company,@Field("u_title")String u_title);

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
    Observable<BaseEntity<GroupEntity>> queryInvestorGroup(@Field("token")String token,@Field("investor_id")long investor_id,@Field("contact_id")long contact_id,@Field("page")int page,@Field("page_size")int page_size);

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
    @POST("api/page/investor/list")
    Observable<BaseEntity<InvestorBean>> querySelectedInvestor(@Field("token")String token,@Field("page")int page,@Field("page_size")int page_size);

    /**
     * 关注投资人
     * @return
     */
    @POST("api/user/contact/investor/import")
    Observable<CodeEntity> followInvestor(@Body FollowRequest entity);

    /**
     * 取消关注投资人
     * @param token
     * @param investor_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/unfollow")
    Observable<CodeEntity> unFollowInvestor(@Field("token")String token,@Field("investor_id")long investor_id);

    /**
     * 设置投资人分组
     * @return
     */
    @POST("api/user/contact/investor/change/group")
    Observable<CodeEntity> changeGroup(@Body ChangeInvestorGroupRequest request);

    /**
     * 设置自己添加投资人的分组
     * @return
     */
    @POST("api/user/contact/investor/move/group/single")
    Observable<CodeEntity> changeContactGroup(@Body ChangeContactGroupRequest request);

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

    /**
     * 评论详情页
     * @param token
     * @param comment_id
     * @param page_size
     * @param reply_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/comment/detail")
    Observable<BaseEntity<CommentBean>> queryCommentDetail(@Field("token")String token,@Field("comment_id")long comment_id,
                                                           @Field("page_size")int page_size,@Field("reply_id")long reply_id);

    /**
     * 投资人详情页
     * @param token
     * @param investor_id
     * @param comment_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/investor/detail")
    Observable<BaseEntity<CallBackBean>> queryInvestorDetail(@Field("token")String token,@Field("investor_id")long investor_id,
                                                             @Field("comment_id")long comment_id);

    /**
     * 68. 我的投资人——投资人详情
     * @param token
     * @param contact_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/detail")
    Observable<BaseEntity<ContactBean>> queryContactDetail(@Field("token")String token,@Field("contact_id")long contact_id);

    /**
     * 投资机构详情
     * @param token
     * @param fund_id
     * @param comment_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/fund/detail")
    Observable<BaseEntity<FundDetailBackBean>> queryFundDetail(@Field("token")String token,@Field("fund_id")long fund_id,@Field("comment_id")long comment_id);

    /**
     * 搜索投资机构
     * @return
     */
    @POST("api/page/fund/list")
    Observable<BaseEntity<FundBackEntity>> queryFunds(@Body InvestorRequest request);

    /**
     * 搜索投资人
     * @return
     */
    @POST("api/page/investor/list")
    Observable<BaseEntity<InvestorBean>> queryInvestors(@Body InvestorRequest request);

    /**
     * 73. 用户——提交认证
     * @param token
     * @param img_url
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/auth")
    Observable<CodeEntity> uploadVertifyPhoto(@Field("token")String token,@Field("img_url")String img_url);

    /**
     * 新建项目
     * @param request
     * @return
     */
    @POST("api/project/info/add")
    Observable<CodeEntity> createProject(@Body ProjectBean request);

    /**
     * 编辑项目
     * @param request
     * @return
     */
    @POST("api/project/info/edit")
    Observable<CodeEntity> editProject(@Body ProjectBean request);

    /**
     * 我的项目
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("api/project/personal")
    Observable<BaseEntity<ProjectEntity>> queryMyProject(@Field("token")String token);

    /**
     * 搜索我关注的投资人
     * @param token
     * @param keyword
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/contact/investor/search")
    Observable<BaseEntity<ConcernEntity>> searchConcern(@Field("token")String token,@Field("keyword")String keyword);

    /**
     * 70. 路演评价——问卷列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/questions")
    Observable<BaseListEntity<QuestionBean>> queryRoadQuestion(@Field("token")String token);

    /**
     * 87. 操作--路演答题
     * @param request
     * @return
     */
    @POST("api/action/answer")
    Observable<CodeEntity> uploadAnswers(@Body RoadRequest request);

    /**
     * 88. 页面--常见问题
     * @return
     */
    @POST("api/page/recommend/questions")
    Observable<BaseListEntity<String>> queryNormalQuestion();

    /**
     * 评分
     * @param request
     * @return
     */
    @POST("api/action/score")
    Observable<CodeEntity> rateInvestor(@Body RateRequest request);

    /**
     * 操作——评论
     * @param token
     * @param title
     * @param content
     * @param investor_id
     * @param fund_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/action/comment")
    Observable<BaseEntity<CommentIdBean>> commentInvestor(@Field("token")String token, @Field("title")String title, @Field("content")String content,
                                                          @Field("investor_id")long investor_id, @Field("fund_id")long fund_id, @Field("is_anon")int is_anon);

    /**
     * 操作——追评
     * @param token
     * @param content
     * @param comment_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/action/append")
    Observable<CodeEntity> appendInvestor(@Field("token")String token,@Field("content")String content,@Field("comment_id")long comment_id);

    /**
     * 35. 新建投资人
     * @param request
     * @return
     */
    @POST("api/user/contact/investor/add")
    Observable<BaseEntity<IdBean>> createInvestor(@Body CreateInvestorRequest request);

    /**
     * 62. 页面——评论列表页
     * @param token
     * @param page
     * @param page_size
     * @param hot_comment
     * @return
     */
    @FormUrlEncoded
    @POST("api/page/comment/list")
    Observable<BaseEntity<CommentItemsBean>> queryCommentList(@Field("token")String token,@Field("page")int page,
                                                                 @Field("page_size")int page_size,@Field("hot_comment")int hot_comment);

    /**
     * 消息--通知列表
     * @param token
     * @param id
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/notice")
    Observable<BaseEntity<MsgItems>> queryNotice(@Field("token")String token,@Field("id")long id,@Field("page_size")int page_size);

    /**
     * 消息--消息列表
     * @param token
     * @param id
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/message")
    Observable<BaseEntity<MsgItems>> queryMessage(@Field("token")String token,@Field("id")long id,@Field("page_size")int page_size);

    /**
     * 操作--更改已读
     * @param request
     * @return
     */
    @POST("api/action/read")
    Observable<CodeEntity> changeReadStatus(@Body ChangeReadStatusRequest request);

 }

