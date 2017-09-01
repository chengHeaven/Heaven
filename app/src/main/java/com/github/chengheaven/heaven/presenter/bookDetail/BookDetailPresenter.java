package com.github.chengheaven.heaven.presenter.bookDetail;

import android.os.Bundle;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.book.BookDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/6/13.
 */

public class BookDetailPresenter implements BookDetailContract.Presenter {

    private final BookDetailContract.View mView;
    private final DataRepository mDataRepository;
    private Bundle bundle;

    @Inject
    BookDetailPresenter(BookDetailContract.View view, DataRepository dataRepository) {
        this.mView = view;
        this.mDataRepository = dataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.updateTitleView(getIntentData());
        getBookDetailList();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public BookBean getIntentData() {
        return (BookBean) bundle.getSerializable("bookBean");
    }

    @Override
    public void getBookDetailList() {
        mDataRepository.getBookDetailData(getIntentData().getId(), new BookDataSource.BookDetailCallback() {
            @Override
            public void onSuccess(BookBean results) {
                mView.hideLoading();
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mView::updateView);
            }

            @Override
            public void onFailed(String msg) {
                mView.showError();
            }
        });
    }
}
