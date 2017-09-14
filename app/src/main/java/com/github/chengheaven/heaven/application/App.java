package com.github.chengheaven.heaven.application;

import android.support.multidex.MultiDexApplication;

import com.github.chengheaven.heaven.di.component.DaggerDataRepositoryComponent;
import com.github.chengheaven.heaven.di.component.DataRepositoryComponent;
import com.github.chengheaven.heaven.di.module.ApplicationModule;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

public class App extends MultiDexApplication {

    private DataRepositoryComponent mDataRepositoryComponent;
    public static App sInstance;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        mDataRepositoryComponent = DaggerDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public DataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }
}