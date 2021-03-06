package com.qtin.sexyvc.utils;

/**
 * Created by ls on 17/6/29.
 */

public class ConstantUtil {
    //默认id
    public static final long DEFALUT_ID=0;
    public static final long SPECIAL_ID=-1;

    //对象类型 1：评论；2：专题；3：回复
    public static final int OBJECT_TYPE_COMMENT=1;
    public static final int OBJECT_TYPE_SUBJECT=2;
    public static final int OBJECT_TYPE_REPLY=3;
    public static final int OBJECT_TYPE_ROAD=4;//路演

    //数据来源，本地or服务器
    public static final String DATA_FROM_TYPE="DATA_FROM_TYPE";
    public static final int DATA_FROM_LOCAL=1;
    public static final int DATA_FROM_WEB=0;
    //路演评价or撰写评论
    public static final String COMMENT_TYPE_INTENT="COMMENT_TYPE_INTENT";
    public static final int COMMENT_TYPE_ROAD=1;
    public static final int COMMENT_TYPE_EDIT=2;
    public static final int COMMENT_TYPE_NONE=3;

    //机构或者投资人
    public static final String TYPE_INVESTOR_FUND_INTENT="TYPE_INVESTOR_FUND_INTENT";
    public static final int TYPE_INVESTOR=1;
    public static final int TYPE_FUND=2;
    public static final int TYPE_UNAUTH=3;
    public static final int TYPE_INVESTOR_ROAD=4;
    public static final int TYPE_FUND_ROAD=5;

    public static final int OBJECT_TYPE_FUND=1;

    //关键字
    public static final String KEY_WORD_INTENT="key_word";
    //是forResult还是直接进入界面
    public static final String INTENT_IS_FOR_RESULT="INTENT_IS_FOR_RESULT";

    //新建还是编辑
    public static final String INTENT_IS_EDIT="INTENT_IS_EDIT";

    //id
    public static final String INTENT_ID="id";
    public static final String INTENT_ID_INVESTOR="investor_id";
    public static final String INTENT_ID_FUND="fund_id";
    public static final String INTENT_INDEX="index";
    //title
    public static final String INTENT_TITLE="title";

    public static final String INTENT_NUM="number";

    //地址
    public static final String INTENT_URL="url";
    //ParcelableArray
    public static final String INTENT_PARCELABLE_ARRAY="ParcelableArray";
    //Parcelable
    public static final String INTENT_PARCELABLE="Parcelable";
    //string
    public static final String INTENT_STRING_ARRAY="string_array";

    //认证状态
    public static final int AUTH_STATE_UNPASS=0;
    public static final int AUTH_STATE_PASS=1;
    public static final int AUTH_STATE_COMMITING=2;

    //身份
    public static final int AUTH_TYPE_UNKNOWN=0;
    public static final int AUTH_TYPE_FOUNDER=1;
    public static final int AUTH_TYPE_INVESTOR=2;
    public static final int AUTH_TYPE_FA=3;
    public static final int AUTH_TYPE_OTHER=4;

    //上传认证照片
    public static final int UPLOAD_VERTIFY_PHOTO_REQUEST_CODE=0x01c;

    public static final int TYPE_NORMAL=1;
    public static final int TYPE_UNNORMAL=2;
    public static final int TYPE_CUSTOM=3;
    public static final int TYPE_FOOTER=4;


    public static final int ACTION_CLEAR_HISTORY=-1;
    public static final int ACTION_TO_SEXYVC_SEACRCH=-2;

    public static final int REQUEST_CODE_ID=0x004;

    //写评论还是追评
    public static final String REVIEW_TYPE_INTENT="REVIEW_TYPE_INTENT";
    public static final int REVIEW_TYPE_COMMENT=1;
    public static final int REVIEW_TYPE_COMMENT_PLUS=2;
    
    public static final String ROAD_SUCCESS = "road_comment_success";
    public static final String SCORE_SUCCESS = "score_success";
    public static final String COMMENT_SUCCESS = "comment_success";
    public static final String QUERY_SUCCESS = "query_success";


    public static final String INTENT_CREATE_INVESTOR_TYPE="INTENT_CREATE_INVESTOR";
    public static final int TYPE_CREATE_INVESTOR_NAME=0x111;
    public static final int TYPE_CREATE_INVESTOR_FUND=0x112;
    public static final int TYPE_CREATE_INVESTOR_TITLE=0x113;
    public static final int TYPE_CREATE_INVESTOR_PHONE=0x114;
    public static final int TYPE_CREATE_INVESTOR_EMAIL=0x115;
    public static final int TYPE_CREATE_INVESTOR_REMARK=0x116;
    public static final int TYPE_CREATE_INVESTOR_WECHAT=0x117;
    public static final String INTENT_CREATE_INVESTOR_OLD_VALUE="INTENT_CREATE_INVESTOR_OLD_VALUE";

    public static final String INTENT_TYPE_SET_GROUP="set_group";
    public static final int TYPE_SET_GROUP_INVESTOR=1;
    public static final int TYPE_SET_GROUP_CONTACT=2;
    public static final int TYPE_SET_GROUP_FUND=3;

    //0代表进入路演评价，1代表进入文字评价
    public static int INTENT_ROAD_COMMENT=0;
    public static int INTENT_TEXT_COMMENT=1;

    public static final int SHARE_WECHAT=0;
    public static final int SHARE_WX_CIRCLE=1;
    public static final int SHARE_QQ=2;
    public static final int SHARE_SINA=3;

    public static String TYPE_KEY_DOMAIN="domain";
    public static String TYPE_KEY_STAGE="stage";

}
