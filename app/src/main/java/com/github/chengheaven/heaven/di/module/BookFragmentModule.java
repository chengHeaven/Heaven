package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.book.CultureContract;
import com.github.chengheaven.heaven.presenter.book.LifeContract;
import com.github.chengheaven.heaven.presenter.book.LiteratureContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */
@Module
public class BookFragmentModule {

    private final LiteratureContract.View mLiteratureView;
    private final CultureContract.View mCultureView;
    private final LifeContract.View mLifeView;

    public BookFragmentModule(
            LiteratureContract.View mLiteratureView,
            CultureContract.View mCultureView,
            LifeContract.View mLifeView) {
        this.mLiteratureView = mLiteratureView;
        this.mCultureView = mCultureView;
        this.mLifeView = mLifeView;
    }

    @Provides
    LiteratureContract.View provideLiteratureContractView() {
        return mLiteratureView;
    }

    @Provides
    CultureContract.View provideCultureContractView() {
        return mCultureView;
    }

    @Provides
    LifeContract.View provideLifeContractView() {
        return mLifeView;
    }
}
