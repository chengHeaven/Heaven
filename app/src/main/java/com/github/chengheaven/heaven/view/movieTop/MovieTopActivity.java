package com.github.chengheaven.heaven.view.movieTop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.application.App;
import com.github.chengheaven.heaven.di.component.DaggerMovieTopComponent;
import com.github.chengheaven.heaven.di.module.MovieTopPresenterModule;
import com.github.chengheaven.heaven.helper.BaseActivity;
import com.github.chengheaven.heaven.presenter.movieTop.MovieTopPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieTopActivity extends BaseActivity {

    @Inject
    MovieTopPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.book_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        MovieTopFragment movieTopFragment = (MovieTopFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_top_content_frame);

        if (movieTopFragment == null) {
            movieTopFragment = MovieTopFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.movie_top_content_frame, movieTopFragment);
            transaction.commit();
        }

        DaggerMovieTopComponent.builder()
                .dataRepositoryComponent(((App) getApplication()).getDataRepositoryComponent())
                .movieTopPresenterModule(new MovieTopPresenterModule(movieTopFragment))
                .build()
                .inject(this);
    }


    @Override
    protected void setToolbarTitle() {
        toolbar.setTitle("豆瓣电影Top250");
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
