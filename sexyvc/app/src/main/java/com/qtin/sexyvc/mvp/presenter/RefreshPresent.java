package com.qtin.sexyvc.mvp.presenter;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.mvp.contract.RefreshContarct;
import com.qtin.sexyvc.mvp.model.entity.User;
import com.qtin.sexyvc.mvp.test.user.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/2/28.
 */
@ActivityScope
public class RefreshPresent extends BasePresenter<RefreshContarct.Model,RefreshContarct.View> {
    private RxErrorHandler errorHandler;
    private List<User> data=new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int lastUserId = 1;
    private boolean isFirst = true;

    @Inject
    public RefreshPresent(RefreshContarct.Model model, RefreshContarct.View rootView, RxErrorHandler errorHandler) {
        super(model, rootView);
        this.errorHandler = errorHandler;
        mAdapter=new UserAdapter(data);
        rootView.setAdapter(mAdapter);
    }

    public void requestUsers(final boolean pullToRefresh){
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }
        }, mRootView.getRxPermissions(), mRootView, errorHandler);

        if(pullToRefresh){
            lastUserId=1;
        }
        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b
        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存
        if (pullToRefresh && isFirst){//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }
        mModel.getUser(lastUserId,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(pullToRefresh){
                            mRootView.showLoading();
                        }else{
                            mRootView.startLoadMore();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if(pullToRefresh){
                            mRootView.hideLoading();
                        }else{
                            mRootView.endLoadMore();
                        }
                    }
                })
                .compose(RxUtils.<List<User>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<User>>(errorHandler) {
                    @Override
                    public void onNext(List<User> users) {
                        lastUserId = users.get(users.size() - 1).getId();//记录最后一个id,用于下一次请求
                        if (pullToRefresh) data.clear();//如果是上拉刷新则清空列表
                        data.addAll(users);
                        mAdapter.notifyDataSetChanged();//通知更新数据
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.data = null;
        this.errorHandler = null;
    }
}
