package com.qtin.sexyvc.ui.main.fragmine.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.main.fragmine.FragMineContract;
import com.qtin.sexyvc.ui.main.fragmine.FragMineModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class FragmineModule {

    private FragMineContract.View view;
    public FragmineModule(FragMineContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    FragMineContract.View provideFragmineView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    FragMineContract.Model provideFragmineModel(FragMineModel model){
        return model;
    }
}
