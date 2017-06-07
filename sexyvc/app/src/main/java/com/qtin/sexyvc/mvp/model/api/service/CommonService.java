package com.qtin.sexyvc.mvp.model.api.service;

import com.qtin.sexyvc.mvp.model.entity.BaseEntity;
import com.qtin.sexyvc.mvp.model.entity.BaseListEntity;
import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.mvp.model.entity.QiniuTokenEntity;
import com.qtin.sexyvc.mvp.model.entity.RegisterRequestEntity;
import retrofit2.http.Body;
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

    @POST("user/register")
    Observable<BaseListEntity<LoginEntity>> register(@Body RegisterRequestEntity entity);
}
