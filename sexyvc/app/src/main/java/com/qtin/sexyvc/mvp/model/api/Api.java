package com.qtin.sexyvc.mvp.model.api;

/**
 * Created by jess on 8/5/16 11:25
 * contact with jess.yan.effort@gmail.com
 */
public interface Api {
    int RequestSuccess = 0;
    String IMAGE_URL="http://oli02rut3.bkt.clouddn.com/";
    String APP_DOMAIN = "https://api.github.com/users";


    String QINIU_TOKEN="http://demo.api.sexyvc.com/admin/qiniu/token";
    String BASE_URL="http://demo.api.sexyvc.com/";

    //String QINIU_TOKEN="http://192.168.0.55:9099/admin/qiniu/token";
    //String BASE_URL="http://192.168.0.55:9099/";

    //分享相关
    String SHARE_COMMENT="http://vc.sexyvc.com/comment/";// 评论
    String SHARE_SUBJECT="http://vc.sexyvc.com/subject/";// 专题
    String SHARE_FUND="http://vc.sexyvc.com/fund/";// 机构
    String SHARE_INVESTOR="http://vc.sexyvc.com/investor/";// 投资人

    String SUBJECT_URL="http://vc.sexyvc.com/static/";

}
