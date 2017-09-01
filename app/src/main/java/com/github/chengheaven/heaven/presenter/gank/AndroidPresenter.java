package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.every.EveryDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class AndroidPresenter implements AndroidContract.Presenter {

    private final DataRepository mDataRepository;
    private final AndroidContract.View mView;

    @Inject
    AndroidPresenter(DataRepository mDataRepository, AndroidContract.View mView) {
        this.mDataRepository = mDataRepository;
        this.mView = mView;
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
    public void getAndroid(String type, String id, int page, int per_page) {
        mDataRepository.getGankData(id, page, per_page, new EveryDataSource.EveryCallback() {
            @Override
            public void onSuccess(List results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resultList -> {
                            if (type == null) {
                                mView.updateList(resultList);
                            } else {
                                mView.refreshList(resultList);
                            }
                        });
            }

            @Override
            public void onFailed(String msg) {
                mView.updateList(null);
                mView.toastMessage(msg);
            }
        });
    }
}
