package com.github.chengheaven.heaven.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.helper.BaseActivity;
import com.github.chengheaven.heaven.tools.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class DownloadActivity extends BaseActivity {

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
