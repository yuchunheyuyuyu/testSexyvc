package com.qtin.sexyvc.ui.main.fraghome;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.qtin.sexyvc.ui.bean.CommentEntity;
import com.qtin.sexyvc.ui.bean.SubjectEntity;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBackBean;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBean;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemBannerEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemInvestorEntity;
import com.qtin.sexyvc.ui.main.fraghome.entity.ItemNewsEntity;
import com.qtin.sexyvc.utils.LocalFileReader;
import java.util.ArrayList;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

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

    public void getHomeData(){
        new LocalFileReader().readAssets(mRootView.getContext(), "homeData.json", new LocalFileReader.ReadListener() {
            @Override
            public void complete(String result) {

                HomeBackBean homeBackBean=new Gson().fromJson(result,HomeBackBean.class);
                //数据处理过程
                HomeBean homeBean=homeBackBean.getItems();

                //banner
                ArrayList<HomeInterface> data=new ArrayList<HomeInterface>();
                ItemBannerEntity itemBannerEntity=new ItemBannerEntity();
                itemBannerEntity.setList(homeBean.getBanner().getList());
                data.add(itemBannerEntity);

                //快讯
                ItemNewsEntity itemNewsEntity=new ItemNewsEntity();
                itemNewsEntity.setList(homeBean.getNews().getList());
                data.add(itemNewsEntity);

                //投资人
                ItemInvestorEntity itemInvestorEntity=new ItemInvestorEntity();
                itemInvestorEntity.setList(homeBean.getInventors().getList());
                data.add(itemInvestorEntity);

                //评论
                ArrayList<CommentEntity> commentList=homeBean.getComment().getList();
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
                ArrayList<SubjectEntity> subjectList=homeBean.getSubjects().getList();
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
