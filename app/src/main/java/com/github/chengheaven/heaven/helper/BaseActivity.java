package com.github.chengheaven.heaven.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.chengheaven.heaven.R;

/**
 * @author heaven_Cheng Created on 16/9/13.
 */
public abstract class BaseActivity extends AppCompatActivity {

    Context mContext;
    DoubleClickExitHelper doubleClick;
    protected Toolbar toolbar;
    DrawerLayout mDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        //设置statusBar全透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorTheme));

////            window.setStatusBarColor(Color.argb(255, 33, 150, 243));
//
//            //toolbar入侵statusBar
//            TypedValue typedValue = new TypedValue();
//            this.getTheme().resolveAttribute(R.color.colorPrimary, typedValue, true);
//            int[] attribute = new int[]{R.color.colorPrimary};
//            TypedArray array = this.obtainStyledAttributes(typedValue.resourceId, attribute);
//            int color = array.getColor(0, getResources().getColor(R.color.colorPrimary));
//            array.recycle();
//            window.setStatusBarColor(color);


        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }

//        statusBarTransparent(getWindow());


        doubleClick = new DoubleClickExitHelper(this);

        setContentView(getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.white_back);
        if (toolbar != null) {
            setToolbarTitle();
            setTitleColor();
            setToolbarColor();
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    protected abstract int getLayoutId();

    protected void setToolbarColor() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorTheme));
    }

    protected Drawable getToolbarColor() {
        return toolbar.getBackground();
    }

    protected void setToolbarTitle() {
        toolbar.setTitle(R.string.app_name);
    }

    protected void setTitleColor() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
    }

    protected CharSequence getToolbarTitle() {
        return toolbar.getTitle();
    }

//    @Override
//    public void onBackPressed() {
//        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//            mDrawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         *  判断是否显示返回按钮
         */
        if (null != getToolbar() && isShowBacking()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * this Activity of toolbar.
     */
    private Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 是否显示后退按钮,默认不显示,可在子类重写该方法.
     */
    protected boolean isShowBacking() {
        return false;
    }

    /**
     * 是否双击退出
     */
    protected boolean isDoubleExit() {
        return true;
    }

    @Override   //连按两次返回键退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawer != null) {
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            } else if (isDoubleExit()) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    return doubleClick.onKeyDown(keyCode, event);
                }
            }
            return false;
        } else if (isDoubleExit()) {
            return event.getKeyCode() == KeyEvent.KEYCODE_BACK && doubleClick.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void statusBarTransparent(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(option);
            window.setNavigationBarColor(Color.BLUE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
