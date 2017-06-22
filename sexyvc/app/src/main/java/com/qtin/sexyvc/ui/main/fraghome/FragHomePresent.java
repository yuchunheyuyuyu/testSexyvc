package com.qtin.sexyvc.ui.main.fraghome;

import android.app.Application;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CommentEntity;
import com.qtin.sexyvc.ui.bean.SubjectEntity;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBean;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemBannerEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemInvestorEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemNewsEntity;
import java.util.ArrayList;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class FragHomePresent extends BasePresenter<FragHomeContract.Model,FragHomeContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public FragHomePresent(FragHomeContract.Model model, FragHomeContract.View rootView, RxErrorHandler mErrorHandler,
                           AppManager mAppManager, Application mApplication) {
        super(model, rootView);
        this.mErrorHandler = mErrorHandler;
        this.mAppManager = mAppManager;
        this.mApplication = mApplication;
    }

    public void query(){
        mModel.queryHome()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();//显示上拉刷新的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    }
                })
                .compose(RxUtils.<BaseEntity<HomeBean>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseEntity<HomeBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<HomeBean> baseEntity) {
                        if(baseEntity.isSuccess()){
                            //数据处理过程
                            HomeBean homeBean=baseEntity.getItems();
                            //banner
                            ArrayList<HomeInterface> data=new ArrayList<HomeInterface>();
                            ItemBannerEntity itemBannerEntity=new ItemBannerEntity();
                            itemBannerEntity.setList(homeBean.getBanners());
                            data.add(itemBannerEntity);

                            //快讯
                            ItemNewsEntity itemNewsEntity=new ItemNewsEntity();
                            itemNewsEntity.setList(homeBean.getFlashes());
                            data.add(itemNewsEntity);

                            //投资人
                            ItemInvestorEntity itemInvestorEntity=new ItemInvestorEntity();
                            itemInvestorEntity.setList(homeBean.getInvestors());
                            data.add(itemInvestorEntity);

                            //评论
                            ArrayList<CommentEntity> commentList=homeBean.getComments();
                            for(int i=0;i<commentList.size();i++){
                                if(i==0){
                                    commentList.get(i).setFirst(true);
                                }
                                if(i==commentList.size()-1){
                                    commentList.get(i).setLast(true);
                                }
                            }
                            data.addAll(commentList);

                            //专题
                            ArrayList<SubjectEntity> subjectList=homeBean.getSubjects();
                            for(int i=0;i<subjectList.size();i++){
                                if(i==0){
                                    subjectList.get(i).setFirst(true);
                                }
                                if(i==subjectList.size()-1){
                                    subjectList.get(i).setLast(true);
                                }
                            }
                            data.addAll(subjectList);
                            //
                            mRootView.dataCallback(data);
                        }else{
                            mRootView.showMessage(baseEntity.getErrMsg());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler=null;
        mAppManager=null;
        mApplication=null;
    }
}
