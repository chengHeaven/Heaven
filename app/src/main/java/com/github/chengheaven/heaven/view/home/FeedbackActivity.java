package com.github.chengheaven.heaven.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.helper.BaseActivity;

import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class FeedbackActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }
}