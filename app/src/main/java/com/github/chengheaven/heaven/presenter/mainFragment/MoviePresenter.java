package com.github.chengheaven.heaven.presenter.mainFragment;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.movie.MovieDataSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

public class MoviePresenter implements MovieContract.Presenter {

    private final MovieContract.View mView;
    private final DataRepository mDataRepository;
    private Disposable mDisposable;

    @Inject
    MoviePresenter(MovieContract.View mView, DataRepository mDataRepository) {
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
    public void getMovieList() {
        mDataRepository.getHotMovie(new MovieDataSource.MovieCallback<MovieBean>() {
            @Override
            public void onSuccess(List<MovieBean> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mView.update(list);
                            Observable.timer(1000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(v -> mView.hideLoading());
                        });
            }

            @Override
            public void onFailed(String msg) {
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mView.toastMessage(msg);
                            mView.update(null);
                        });
            }
        });
    }
}
