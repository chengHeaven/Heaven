package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.movieDetail.MovieDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */
@Module
public class MovieDetailPresenterModule {

    private final MovieDetailContract.View mView;

    public MovieDetailPresenterModule(MovieDetailContract.View mView) {
        this.mView = mView;
    }

    @Provides
    MovieDetailContract.View provideMovieDetailView() {
        return mView;
    }
}
