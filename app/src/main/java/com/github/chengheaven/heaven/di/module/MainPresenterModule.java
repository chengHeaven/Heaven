package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.main.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

@Module
public class MainPresenterModule {

    private final MainContract.View mView;

    public MainPresenterModule(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainContract.View provideMainContractView() {
        return mView;
    }
}
