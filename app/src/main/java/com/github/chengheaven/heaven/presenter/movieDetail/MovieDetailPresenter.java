package com.github.chengheaven.heaven.presenter.movieDetail;

import android.os.Bundle;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.bean.MovieDetailBean;
import com.github.chengheaven.heaven.data.DataRepository;
import com.github.chengheaven.heaven.data.movie.MovieDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private final MovieDetailContract.View mView;
    private final DataRepository mDataRepository;
    private Bundle bundle;

    @Inject
    MovieDetailPresenter(MovieDetailContract.View view, DataRepository dataRepository) {
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
        getMovieDetail();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public MovieBean getIntentData() {
        return (MovieBean) bundle.getSerializable("movieBean");
    }

    @Override
    public void getMovieDetail() {
        mDataRepository.getMovieDetail(getIntentData().getId(), new MovieDataSource.MovieDetailCallback() {
            @Override
            public void onSuccess(MovieDetailBean results) {
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
