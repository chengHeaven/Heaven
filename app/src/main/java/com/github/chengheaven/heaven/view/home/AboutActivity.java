package com.github.chengheaven.heaven.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.view.BaseActivity;
import com.github.chengheaven.heaven.tools.Utils;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle("关于");
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    @OnClick({R.id.tv_new_version,
            R.id.tv_function,
            R.id.tv_about_star})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_new_version:
                Utils.showSnackBar(this, "已是最新版本");
                break;

            case R.id.tv_function:
                Utils.showSnackBar(this, "暂无更新日志");
                break;

            case R.id.tv_about_star:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mUrl", "https://github.com/chengHeaven/Heaven");
                intent.putExtra("mTitle", getResources().getString(R.string.loading));
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}