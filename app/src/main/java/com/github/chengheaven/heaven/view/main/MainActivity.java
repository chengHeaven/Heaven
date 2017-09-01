package com.github.chengheaven.heaven.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.application.App;
import com.github.chengheaven.heaven.di.component.DaggerMainComponent;
import com.github.chengheaven.heaven.di.module.MainPresenterModule;
import com.github.chengheaven.heaven.helper.BaseActivity;
import com.github.chengheaven.heaven.presenter.main.MainPresenter;
import com.github.chengheaven.heaven.view.home.AboutActivity;
import com.github.chengheaven.heaven.view.home.DownloadActivity;
import com.github.chengheaven.heaven.view.home.FeedbackActivity;
import com.github.chengheaven.heaven.view.home.HomePageActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.main_drawer_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        // main fragment
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_layout);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_frame_layout, mainFragment);
            transaction.commit();
        }

        DaggerMainComponent.builder()
                .dataRepositoryComponent(((App) getApplication()).getDataRepositoryComponent())
                .mainPresenterModule(new MainPresenterModule(mainFragment))
                .build()
                .inject(this);

        View headView = mNavigationView.getHeaderView(0);
        new NavigationHeaderView(headView);
    }

    class NavigationHeaderView {

        NavigationHeaderView(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ll_nav_homepage,
                R.id.ll_nav_scan_download,
                R.id.ll_nav_feedback,
                R.id.ll_nav_about,
                R.id.ll_nav_exit})
        void onClick(View view) {
            mDrawer.closeDrawer(GravityCompat.START);
            switch (view.getId()) {
                case R.id.ll_nav_homepage:
                    startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                    break;
                case R.id.ll_nav_scan_download:
                    startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                    break;
                case R.id.ll_nav_feedback:
                    startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                    break;
                case R.id.ll_nav_about:
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    break;
                case R.id.ll_nav_exit:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
