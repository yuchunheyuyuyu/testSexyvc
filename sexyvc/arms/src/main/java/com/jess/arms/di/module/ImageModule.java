package com.jess.arms.di.module;

import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;
import com.jess.arms.widget.imageloader.glide.GlideImageLoaderStrategy;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 8/5/16 16:10
 */
@Module
public class ImageModule {

    @Singleton
    @Provides
    public BaseImageLoaderStrategy provideImageLoaderStrategy(GlideImageLoaderStrategy glideImageLoaderStrategy) {
        return glideImageLoaderStrategy;
    }
}
