package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.bean.RxCustomer;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.every.EveryDataSource;
import com.github.chengheaven.heaven.helper.RxBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class CustomizationPresenter implements CustomizationContract.Presenter {

    private final DataRepository mDataRepository;
    private final CustomizationContract.View mView;
    private Disposable mDisposable;

    @Inject
    CustomizationPresenter(DataRepository mDataRepository, CustomizationContract.View mView) {
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
        registerShowTypeContent();
    }

    private void registerShowTypeContent() {
        RxBus.getDefault().toObservable(RxCustomer.class)
                .subscribe(new Observer<RxCustomer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(RxCustomer value) {
                        mView.setType(value.getType());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCustomization(String refresh, String id, int page, int per_page) {
        mDataRepository.getGankData(id, page, per_page, new EveryDataSource.EveryCallback<HomeBean>() {
            @Override
            public void onSuccess(List<HomeBean> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resultList -> {
                            if (refresh == null) {
                                mView.updateSingleList(resultList);
                            } else {
                                mView.updateList(resultList);
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

    @Override
    public void clear() {
        mDataRepository.clear();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }
}
