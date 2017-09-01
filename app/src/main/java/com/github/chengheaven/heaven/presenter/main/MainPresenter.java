package com.github.chengheaven.heaven.presenter.main;

import com.github.chengheaven.heaven.bean.RxDaily;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.helper.RxBus;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class MainPresenter implements MainContract.Presenter {

    private final DataRepository mDataRepository;
    private final MainContract.View mView;
    private Disposable mDisposable;

    @Inject
    MainPresenter(DataRepository dataRepository, MainContract.View view) {
        mDataRepository = dataRepository;
        mView = view;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        registerGoToHotMovieEvent();
    }

    private void registerGoToHotMovieEvent() {
        RxBus.getDefault().toObservable(RxDaily.class)
                .subscribe(new Observer<RxDaily>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(RxDaily value) {
                        mView.setCurrentItem(value.getPosition());
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
    public void clear() {
        mDataRepository.clear();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }
}
