package com.qtin.sexyvc.common;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.module.GlobeConfigModule;
import com.jess.arms.http.GlobeHttpHandler;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.BuildConfig;
import com.qtin.sexyvc.di.module.CacheModule;
import com.qtin.sexyvc.di.module.DataBaseModule;
import com.qtin.sexyvc.di.module.ServiceModule;
import com.qtin.sexyvc.mvp.model.api.Api;
import com.qtin.sexyvc.mvp.model.entity.DaoMaster;
import com.qtin.sexyvc.mvp.model.entity.DaoSession;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.greendao.database.Database;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by jess on 8/5/16 11:07
 * contact with jess.yan.effort@gmail.com
 */
public class CustomApplication extends BaseApplication {

    private DaoSession daoSession;

    public static String nick;
    public static String avatar;

    private AppComponent mAppComponent;
    private RefWatcher mRefWatcher;//leakCanary观察器

    {
        PlatformConfig.setWeixin("wxdd991af9d0c9ddbe", "e957f59de5bbf4a3b584dd7891b21ecb");
        PlatformConfig.setQQZone("1105726586", "yCie49iiNXUnjBAu");
        PlatformConfig.setSinaWeibo("3351202988", "c9efb278c7e754735b8a26daaddf2b47","http://sns.whalecloud.com");
    }

    public  String deviceToken;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);//分包
        //初始化数据库
        initDataBase();

        CrashReport.initCrashReport(getApplicationContext(), "fcc8e29927", true);
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(getAppModule())//baseApplication提供
                .clientModule(getClientModule())//baseApplication提供
                .imageModule(getImageModule())//baseApplication提供
                .globeConfigModule(getGlobeConfigModule())//全局配置
                .serviceModule(new ServiceModule())//需自行创建
                .cacheModule(new CacheModule())//需自行创建
                .dataBaseModule(new DataBaseModule(daoSession))
                .build();

        if (BuildConfig.LOG_DEBUG) {//Timber日志打印
            Timber.plant(new Timber.DebugTree());
        }
        //友盟分享
        UMShareAPI.get(this);
        //友盟统计
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity
        //友盟推送
        initPush();
        installLeakCanary();//leakCanary内存泄露检查
    }

    private void initDataBase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,  "sexyvc-db");
        Database db =helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    private void initPush(){
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //mPushAgent.setDebugMode(false);//正式发布 关闭日志输出。

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                deviceToken=deviceToken;
                Log.e("=====","===========deviceToken:"+deviceToken);//测试token Aty3-a0sozquvOYd5MO8KHuWeyi16tVW7D4gntb38k8t
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppComponent != null)
            this.mAppComponent = null;
        if (mRefWatcher != null)
            this.mRefWatcher = null;
    }

    /**
     * 安装leakCanary检测内存泄露
     */
    protected void installLeakCanary() {
        this.mRefWatcher = BuildConfig.USE_CANARY ? LeakCanary.install(this) : RefWatcher.DISABLED;
    }

    /**
     * 获得leakCanary观察器
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        CustomApplication application = (CustomApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例, 在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    /**
     * app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * @return
     */
    @Override
    protected GlobeConfigModule getGlobeConfigModule() {
        return GlobeConfigModule
                .buidler()
                .baseurl(Api.BASE_URL)
                .globeHttpHandler(new GlobeHttpHandler() {// 这里可以提供一个全局处理http响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        //这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                        //重新请求token,并重新执行请求
                        try {
                            if (!TextUtils.isEmpty(httpResult)) {
                                Log.e("","=========httpResult:"+httpResult);
                                Timber.tag(TAG).w(httpResult);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            return response;
                        }


                        //这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                        //注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                        // create a new request and modify it accordingly using the new token
//                    Request newRequest = chain.request().newBuilder().header("token", newToken)
//                            .build();

//                    // retry the request
//
//                    response.body().close();
                        //如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可

                        //如果不需要返回新的结果,则直接把response参数返回出去
                        return response;
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        //如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则返回request
                        if (request.method().equals("POST")) {
                            if (request.body() instanceof FormBody) {
                                FormBody.Builder bodyBuilder = new FormBody.Builder();
                                FormBody formBody = (FormBody) request.body();
                                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                                for (int i = 0; i < formBody.size(); i++) {
                                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                                }
                                //formBody=bodyBuilder.addEncoded("token","5643e5031a98de846f1d2bd4d01955f6").build();
                                formBody=bodyBuilder.build();
                                request=request.newBuilder().post(formBody).build();
                                return request;
                            }
                        }
                        return request;
                    }
                })
                .responseErroListener(new ResponseErroListener() {
                    //     用来提供处理所有错误的监听
                    //     rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效
                    @Override
                    public void handleResponseError(Context context, Exception e) {
                        Timber.tag(TAG).w("------------>" + e.getMessage());
                        UiUtils.SnackbarText(e.toString());
                    }
                }).build();
    }


}
