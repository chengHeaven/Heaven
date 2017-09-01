package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.mainFragment.BookContract;
import com.github.chengheaven.heaven.presenter.mainFragment.GankContract;
import com.github.chengheaven.heaven.presenter.mainFragment.MovieContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

@Module
public class MainFragmentPresenterModule {

    private final BookContract.View mBookView;
    private final GankContract.View mGankView;
    private final MovieContract.View mMovieView;

    public MainFragmentPresenterModule(
            GankContract.View gankView,
            MovieContract.View movieView,
            BookContract.View bookView) {
        this.mBookView = bookView;
        this.mGankView = gankView;
        this.mMovieView = movieView;
    }

    @Provides
    BookContract.View provideBookView() {
        return mBookView;
    }

    @Provides
    GankContract.View provideGankView() {
        return mGankView;
    }

    @Provides
    MovieContract.View provideMovieView() {
        return mMovieView;
    }
}
