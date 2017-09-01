package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.every.EveryDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class WelfarePresenter implements WelfareContract.Presenter {

    private final DataRepository mDataRepository;
    private final WelfareContract.View mView;

    @Inject
    WelfarePresenter(DataRepository mDataRepository, WelfareContract.View mView) {
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
    public void getWelfare(String id, int page, int per_page) {
        mDataRepository.getGankData(id, page, per_page, new EveryDataSource.EveryCallback<HomeBean>() {
            @Override
            public void onSuccess(List<HomeBean> results) {
                mView.hideLoading();
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> {
                            List<String> urls = new ArrayList<>();
                            for (HomeBean homeBean : result) {
                                urls.add(homeBean.getUrl());
                            }
                            return urls;
                        })
                        .subscribe(urls -> {
                            mDataRepository.setImageUrlsLocal(urls);
                            mView.updateList(urls);
                        });
            }

            @Override
            public void onFailed(String msg) {
                mView.hideLoading();
                mView.updateList(null);
                mView.toastMessage(msg);
            }
        });
    }
}
