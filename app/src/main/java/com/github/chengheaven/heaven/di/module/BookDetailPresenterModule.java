package com.github.chengheaven.heaven.di.module;

import com.github.chengheaven.heaven.presenter.bookDetail.BookDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/6/13.
 */
@Module
public class BookDetailPresenterModule {

    private final BookDetailContract.View mView;

    public BookDetailPresenterModule(BookDetailContract.View mView) {
        this.mView = mView;
    }

    @Provides
    BookDetailContract.View provideBookContractView() {
        return mView;
    }
}
