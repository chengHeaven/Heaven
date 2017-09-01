package com.github.chengheaven.heaven.view.movieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.application.App;
import com.github.chengheaven.heaven.customer.MyNestedScrollView;
import com.github.chengheaven.heaven.di.component.DaggerMovieDetailComponent;
import com.github.chengheaven.heaven.di.module.MovieDetailPresenterModule;
import com.github.chengheaven.heaven.helper.BaseActivity;
import com.github.chengheaven.heaven.presenter.movieDetail.MovieDetailPresenter;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/6/7.
 */

public class MovieDetailActivity extends BaseActivity {

    @BindView(R.id.movie_detail_name)
    TextView mMovieName;
    @BindView(R.id.movie_detail_performer)
    TextView mMoviePerformer;
    @BindView(R.id.movie_detail_act_image)
    ImageView mImage;
    @BindView(R.id.mns_base)
    MyNestedScrollView mBase;

    @Inject
    MovieDetailPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.movie_detail_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        toolbar.setBackgroundResource(R.color.transparent);

        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_detail_content_frame);

        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.movie_detail_content_frame, movieDetailFragment);
            transaction.commit();
        }

        DaggerMovieDetailComponent.builder()
                .dataRepositoryComponent(((App) getApplication()).getDataRepositoryComponent())
                .movieDetailPresenterModule(new MovieDetailPresenterModule(movieDetailFragment))
                .build()
                .inject(this);

        Bundle bundle = getIntent().getBundleExtra("item");
        mPresenter.setBundle(bundle);
    }

    public void setTitle(String movieName, String performer) {
        mMovieName.setText(movieName);
        mMoviePerformer.setText(performer);
    }

    public void setToolbarBackground(int color) {
        toolbar.setBackgroundColor(color);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ImageView getBackgroundImage() {
        return mImage;
    }

    public MyNestedScrollView getBaseScroll() {
        return mBase;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ext:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mTitle", mPresenter.getIntentData().getTitle());
                intent.putExtra("mUrl", mPresenter.getIntentData().getAlt());
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
