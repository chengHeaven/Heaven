package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.movieTop.MovieTopContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */
@Module
public class MovieTopPresenterModule {

    private final MovieTopContract.View mView;

    public MovieTopPresenterModule(MovieTopContract.View view) {
        this.mView = view;
    }

    @Provides
    MovieTopContract.View provideMovieTopView() {
        return mView;
    }
}
