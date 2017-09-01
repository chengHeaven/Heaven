package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.welfareImage.WelfareImageContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/5/9.
 */

@Module
public class WelfareImagePresenterModule {

    private WelfareImageContract.View mView;

    public WelfareImagePresenterModule(WelfareImageContract.View mView) {
        this.mView = mView;
    }

    @Provides
    WelfareImageContract.View getView() {
        return mView;
    }
}
