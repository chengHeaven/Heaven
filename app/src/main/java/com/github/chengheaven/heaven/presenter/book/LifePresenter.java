package com.github.chengheaven.heaven.presenter.book;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.book.BookDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class LifePresenter implements LifeContract.Presenter {

    private final LifeContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    LifePresenter(LifeContract.View mView, DataRepository mDataRepository) {
        this.mView = mView;
        this.mDataRepository = mDataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getLifeList(String type, String tag, int start, int count) {
        mDataRepository.getBookData(tag, start, count, new BookDataSource.BookCallback<BookBean>() {
            @Override
            public void onSuccess(List<BookBean> results) {
                mView.hideLoading();
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            if (type == null) {
                                mView.updateLifeView(list);
                            } else {
                                mView.refreshLifeView(list);
                            }
                        });
            }

            @Override
            public void onFailed(String message) {
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            if (type == null) {
                                mView.toastMessage(message);
                                mView.updateLifeView(null);
                            } else {
                                mView.toastMessage("刷新失败");
                                mView.refreshLifeView(null);
                            }
                        });
            }
        });
    }
}

