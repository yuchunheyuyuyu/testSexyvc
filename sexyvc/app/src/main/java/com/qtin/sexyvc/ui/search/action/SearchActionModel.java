package com.qtin.sexyvc.ui.search.action;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.KeyWordBean;
import com.qtin.sexyvc.ui.bean.KeyWordBeanDao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SearchActionModel extends BaseModel<ServiceManager,CacheManager> implements SearchActionContract.Model {

    @Inject
    public SearchActionModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public ArrayList<KeyWordBean> getKeys() {
        List<KeyWordBean> list=mCacheManager.getDaoSession().getKeyWordBeanDao().queryBuilder().orderDesc(KeyWordBeanDao.Properties.CreateTime).build().list();
        ArrayList<KeyWordBean> data=new ArrayList<>();
        if(list!=null){
            data.addAll(list);
        }
        return data;
    }

    @Override
    public void insertKey(KeyWordBean bean) {
        bean.setCreateTime(System.currentTimeMillis());
        List<KeyWordBean> list=mCacheManager.getDaoSession().getKeyWordBeanDao().queryBuilder().build().list();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getKeyWord().equals(bean.getKeyWord())){
                    mCacheManager.getDaoSession().getKeyWordBeanDao().delete(list.get(i));
                    break;
                }
            }
        }
        mCacheManager.getDaoSession().getKeyWordBeanDao().insert(bean);
    }

    @Override
    public void delete() {
        mCacheManager.getDaoSession().getKeyWordBeanDao().deleteAll();
    }
}
