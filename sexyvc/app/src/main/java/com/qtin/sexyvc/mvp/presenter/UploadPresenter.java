package com.qtin.sexyvc.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qtin.sexyvc.mvp.contract.UploadContract;
import com.qtin.sexyvc.mvp.model.api.Api;
import com.qtin.sexyvc.mvp.model.entity.BaseEntity;
import com.qtin.sexyvc.mvp.model.entity.QiniuTokenEntity;
import org.json.JSONObject;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/3/1.
 */
@ActivityScope
public class UploadPresenter extends BasePresenter<UploadContract.Model, UploadContract.View> {
    private RxErrorHandler mErrorHandler;
    private String qiniuToken;
    private UploadManager uploadManager;

    @Inject
    public UploadPresenter(UploadContract.Model model, UploadContract.View rootView, RxErrorHandler mErrorHandler) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        uploadManager = new UploadManager();
    }

    public void upload(String path, String token) {
        String key = System.currentTimeMillis() + "ls.png";
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key,final double percent) {
                        mRootView.showProgress((int)(percent*100));
                    }
                }, null);
        uploadManager.put(path, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                mRootView.showMessage("上传成功");
            }
        }, uploadOptions);
    }

    public void getToken(final String path) {
        mModel.getQiniuToken(Api.QINIU_TOKEN)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseEntity<QiniuTokenEntity>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<QiniuTokenEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<QiniuTokenEntity> entity) {
                        if (entity.isSuccess()) {
                            qiniuToken = entity.getItems().getQiniu_token();
                            upload(path,qiniuToken);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
    }
}
