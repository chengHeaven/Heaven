package com.github.chengheaven.heaven.view.bookDetail;

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
import com.github.chengheaven.heaven.di.component.DaggerBookDetailComponent;
import com.github.chengheaven.heaven.di.module.BookDetailPresenterModule;
import com.github.chengheaven.heaven.view.BaseActivity;
import com.github.chengheaven.heaven.presenter.bookDetail.BookDetailPresenter;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/6/13.
 */

public class BookDetailActivity extends BaseActivity {

    @BindView(R.id.book_detail_name)
    TextView mBookName;
    @BindView(R.id.book_detail_author)
    TextView mBookAuthor;
    @BindView(R.id.book_detail_act_image)
    ImageView mImage;
    @BindView(R.id.mns_base)
    MyNestedScrollView mBase;

    @Inject
    BookDetailPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.book_detail_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        toolbar.setBackgroundResource(R.color.transparent);

        BookDetailFragment bookDetailFragment = (BookDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.book_detail_content_frame);

        if (bookDetailFragment == null) {
            bookDetailFragment = BookDetailFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.book_detail_content_frame, bookDetailFragment);
            transaction.commit();
        }

        DaggerBookDetailComponent.builder()
                .dataRepositoryComponent(((App) getApplication()).getDataRepositoryComponent())
                .bookDetailPresenterModule(new BookDetailPresenterModule(bookDetailFragment))
                .build()
                .inject(this);

        Bundle bundle = getIntent().getBundleExtra("item");
        mPresenter.setBundle(bundle);
    }

    public void setTitle(String bookName, String performer) {
        mBookName.setText(bookName);
        mBookAuthor.setText(performer);
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
