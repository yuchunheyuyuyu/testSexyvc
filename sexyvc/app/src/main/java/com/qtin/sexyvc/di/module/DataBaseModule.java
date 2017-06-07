package com.qtin.sexyvc.di.module;

import com.qtin.sexyvc.mvp.model.entity.DaoSession;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/12.
 */
@Module
public class DataBaseModule {

    private DaoSession daoSession;

    public DataBaseModule(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(){
        return daoSession;
    }
}
