package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.gank.AndroidContract;
import com.github.chengheaven.heaven.presenter.gank.CustomizationContract;
import com.github.chengheaven.heaven.presenter.gank.EveryContract;
import com.github.chengheaven.heaven.presenter.gank.WelfareContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

@Module
public class GankFragmentModule {

    private final AndroidContract.View mAndroidView;
    private final CustomizationContract.View mCustomView;
    private final EveryContract.View mEveryView;
    private final WelfareContract.View mWelfareView;

    public GankFragmentModule(
            EveryContract.View everyView,
            WelfareContract.View welfareView,
            CustomizationContract.View customView,
            AndroidContract.View androidView) {
        this.mEveryView = everyView;
        this.mWelfareView = welfareView;
        this.mCustomView = customView;
        this.mAndroidView = androidView;
    }

    @Provides
    AndroidContract.View provideAndroidView() {
        return mAndroidView;
    }

    @Provides
    CustomizationContract.View provideCustomView() {
        return mCustomView;
    }

    @Provides
    EveryContract.View provideEveryView() {
        return mEveryView;
    }

    @Provides
    WelfareContract.View provideWelfareView() {
        return mWelfareView;
    }
}
