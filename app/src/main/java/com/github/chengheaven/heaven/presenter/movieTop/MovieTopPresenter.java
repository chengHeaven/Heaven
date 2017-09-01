package com.github.chengheaven.heaven.presenter.movieTop;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.movie.MovieDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieTopPresenter implements MovieTopContract.Presenter {

    private final MovieTopContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    MovieTopPresenter(MovieTopContract.View view, DataRepository dataRepository) {
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

    }

    @Override
    public void getMovieTopList(String type, int start, int count) {
        mDataRepository.getMovieTop(start, count, new MovieDataSource.MovieCallback<MovieBean>() {
            @Override
            public void onSuccess(List<MovieBean> results) {
                mView.hideLoading();
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            if (type == null) {
                                mView.updateMovieTopList(list);
                            } else {
                                mView.refreshMovieTopList(list);
                            }
                        });
            }

            @Override
            public void onFailed(String msg) {
                mView.hideLoading();
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mView.toastMessage(msg);
                            if (type == null) {
                                mView.updateMovieTopList(null);
                            } else {
                                mView.refreshMovieTopList(null);
                            }
                        });
            }
        });
    }
}
