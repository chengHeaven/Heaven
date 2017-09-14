package com.github.chengheaven.heaven.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.view.BaseActivity;
import com.github.chengheaven.heaven.tools.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class DownloadActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_share)
    TextView mShare;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sweep;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle("扫码下载");
        mShare.setOnClickListener(v -> ShareUtils.share(v.getContext(), R.string.share_text));
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
