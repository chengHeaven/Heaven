package com.github.chengheaven.heaven.presenter.mainFragment;

import com.github.chengheaven.heaven.bean.RxPosition;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.helper.RxBus;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

public class GankPresenter implements GankContract.Presenter {

    private final GankContract.View mView;
    private final DataRepository mDataRepository;
    private Disposable mDisposable;

    @Inject
    GankPresenter(GankContract.View mView, DataRepository mDataRepository) {
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
        registerPosition();
    }

    private void registerPosition() {
        RxBus.getDefault().toObservable(RxPosition.class)
                .subscribe(new Observer<RxPosition>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(RxPosition value) {
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
