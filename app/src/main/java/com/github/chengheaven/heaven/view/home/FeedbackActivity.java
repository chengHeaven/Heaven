package com.github.chengheaven.heaven.view.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.helper.BaseActivity;
import com.github.chengheaven.heaven.tools.Utils;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/20.
 */

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle("问题反馈");
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    @OnClick({R.id.tv_issues,
            R.id.tv_faq,
            R.id.tv_qq,
            R.id.tv_email,
            R.id.tv_jianshu,
            R.id.tv_jianshu_old})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_issues:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mUrl", "https://github.com/chengHeaven/Heaven/issues");
                intent.putExtra("mTitle", getResources().getString(R.string.issues));
                startActivity(intent);
                break;
            case R.id.tv_qq:
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=464994292";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.tv_email:
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:chen464994292@163.com"));
                startActivity(data);
                break;
            case R.id.tv_jianshu:
                Intent jIntent = new Intent(this, WebViewActivity.class);
                jIntent.putExtra("mUrl", "http://www.jianshu.com/users/682e951a9205/latest_articles");
                jIntent.putExtra("mTitle", getResources().getString(R.string.loading));
                startActivity(jIntent);
                break;

            case R.id.tv_faq:
                Utils.showSnackBar(this, "还没有内容哦, 如果有问题, 点击 Heaven 进行反馈吧!");
                break;

            case R.id.tv_jianshu_old:
                Intent oIntent = new Intent(this, WebViewActivity.class);
                oIntent.putExtra("mUrl", "http://www.jianshu.com/users/e43c6e979831/latest_articles");
                oIntent.putExtra("mTitle", getResources().getString(R.string.loading));
                startActivity(oIntent);
                break;

            default:
                break;
        }
    }
}