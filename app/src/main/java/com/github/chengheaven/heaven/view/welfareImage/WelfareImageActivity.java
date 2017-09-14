package com.github.chengheaven.heaven.view.welfareImage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.application.App;
import com.github.chengheaven.heaven.di.component.DaggerWelfareImageComponent;
import com.github.chengheaven.heaven.di.module.WelfareImagePresenterModule;
import com.github.chengheaven.heaven.view.BaseActivity;
import com.github.chengheaven.heaven.presenter.welfareImage.WelfareImagePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/5/9.
 */

public class WelfareImageActivity extends BaseActivity {

    @Inject
    WelfareImagePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.view_image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        WelfareImageFragment welfareImageFragment = (WelfareImageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.welfare_content_frame);

        if (welfareImageFragment == null) {
            welfareImageFragment = WelfareImageFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.welfare_content_frame, welfareImageFragment);
            transaction.commit();
        }

        DaggerWelfareImageComponent.builder()
                .dataRepositoryComponent(((App) getApplication()).getDataRepositoryComponent())
                .welfareImagePresenterModule(new WelfareImagePresenterModule(welfareImageFragment))
                .build()
                .inject(this);

        int position = getIntent().getIntExtra("position", 0);
        mPresenter.setPosition(position);
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
