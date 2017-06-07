package com.qtin.sexyvc.ui.main.fraghome.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.main.fraghome.FragHomeContract;
import com.qtin.sexyvc.ui.main.fraghome.FragHomeModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class FragHomeModule {

    private FragHomeContract.View view;

    public FragHomeModule(FragHomeContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    FragHomeContract.View provideFragHomeView(){
        return this.view;
    }
    @FragmentScope
    @Provides
    FragHomeContract.Model provideFragHomeModel(FragHomeModel model){
        return model;
    }
}
