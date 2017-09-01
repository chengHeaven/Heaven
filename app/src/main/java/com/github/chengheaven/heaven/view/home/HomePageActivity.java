package com.github.chengheaven.heaven.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.customer.statusbar.StatusBarUtil;
import com.github.chengheaven.heaven.tools.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class HomePageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_share)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mToolbar.setTitle("项目介绍");
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);
        mFab.setOnClickListener(v -> ShareUtils.share(v.getContext(), R.string.share_text));
    }
}
