package com.github.chengheaven.heaven.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
