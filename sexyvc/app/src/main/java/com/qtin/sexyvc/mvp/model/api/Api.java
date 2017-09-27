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

    String SHARE_BASE_URL="http://www.sexyvc.com";
    //String SHARE_BASE_URL="http://192.168.0.55:93";
    //分享相关
    String SHARE_COMMENT="/comment/detail/";// 评论
    String SHARE_SUBJECT="/subject/detail/";// 专题
    String SHARE_FUND="/organ/detail/";// 机构
    String SHARE_INVESTOR="/investor/detail/";// 投资人
    String SHARE_ROAD="/road/detail/";//路演评价

    String SUBJECT_URL="http://vc.sexyvc.com/static/";

}
