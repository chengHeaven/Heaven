package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.every.EveryDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class EveryPresenter implements EveryContract.Presenter {

    private final DataRepository mDataRepository;
    private final EveryContract.View mView;

    @Inject
    EveryPresenter(DataRepository mDataRepository, EveryContract.View mView) {
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
    public void getBannerUrl() {
        mDataRepository.getBanner(new EveryDataSource.EveryCallback<String>() {
            @Override
            public void onSuccess(List<String> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(urls -> mView.setBannerUrl(urls, 0));
            }

            @Override
            public void onFailed(String msg) {
                mView.setBannerUrl(null, 1);
            }
        });
    }

    @Override
    public void getRecycler(String year, String month, String day) {
        mDataRepository.getRecycler(year, month, day, new EveryDataSource.EveryContentCallback<HomeBean>() {
            @Override
            public void onSuccess(ArrayList<List<HomeBean>> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object -> {
                            if (object.size() == 0) {
                                mView.updateRecyclerFromCache();
                            } else {
                                mView.updateRecyclerAdapter(object);
                            }
                            Observable.timer(3000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(v -> mView.hideAnimation());
                        });
            }

            @Override
            public void onFailed(String msg) {
                mView.hideAnimation();
                mView.toastMessage(msg);
                mView.updateRecyclerFromCache();
            }
        });
    }
}
