package com.github.chengheaven.heaven.view.welfareImage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.welfareImage.WelfareImageContract;
import com.github.chengheaven.heaven.tools.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven・Cheng Created on 17/5/9.
 */

public class WelfareImageFragment extends BaseFragment implements WelfareImageContract.View,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.welfare_fragment_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.viewpager_indicators)
    LinearLayout mIndicator;
    @BindView(R.id.welfare_item_save)
    TextView mSave;
    private int mIndicatorsNum;

    WelfareViewPagerAdapter mPagerAdapter;

    private WelfareImageContract.Presenter mPresenter;

    public WelfareImageFragment() {

    }

    public static WelfareImageFragment newInstance() {
        return new WelfareImageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPagerAdapter = new WelfareViewPagerAdapter(getActivity(), new ArrayList<>());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_image_fragment, container, false);
        ButterKnife.bind(this, view);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mPresenter.initViewPager();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (WelfareImageContract.Presenter) presenter;
    }

    @Override
    public void updateViewPager(List<String> urls) {
        mPagerAdapter.update(urls);
    }

    @Override
    public void setCurrentItemViewPager(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void setIndicator(int size) {
        mIndicatorsNum = size;
        mIndicator.removeAllViews();
        final TextView indicator = getTextView();
        // set default
        String indicatorText = "1" + "/" + size;
        SpannableStringBuilder builder = getSpannableStringBuilder(indicatorText);
        indicator.setText(builder);
        mIndicator.addView(indicator);
    }

    @Override
    public void updateIndicator(int position) {
        mIndicator.removeAllViews();
        final TextView indicator = getTextView();

        if (position > 2) {
            position = position % mIndicatorsNum;
        }

        String indicatorText = (position + 1) + "/" + mIndicatorsNum;
        SpannableStringBuilder builder = getSpannableStringBuilder(indicatorText);
        indicator.setText(builder);
        mIndicator.addView(indicator);
    }

    @NonNull
    private TextView getTextView() {
        final TextView indicator = new TextView(getActivity());
        indicator.setTextSize(13);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(17, 0, 17, 10);
        indicator.setLayoutParams(params);
        return indicator;
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private SpannableStringBuilder getSpannableStringBuilder(String indicatorText) {
        SpannableStringBuilder builder = new SpannableStringBuilder(indicatorText);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.indicator));
        ForegroundColorSpan graySpan = new ForegroundColorSpan(getResources().getColor(R.color.indicator_text));
        int index = indicatorText.indexOf("/");
        builder.setSpan(blueSpan, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(graySpan, index, indicatorText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPagerAdapter.clear();
    }

    @OnClick(R.id.welfare_item_save)
    public void onClick() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Observable.create(e -> {
            int position = mViewPager.getCurrentItem();
            String path = getImagePath(mPagerAdapter.mUrls.get(position));
            if (path != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                if (bitmap != null) {
                    saveImageToGallery(bitmap);
                    Utils.showSnackBar(getActivity(), "已保存至" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/heaven");
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    String getImagePath(String url) {
        String path = null;
        FutureTarget<File> future = Glide.with(getActivity())
                .load(url)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 保存图片至相册
     */
    void saveImageToGallery(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "heaven");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.getAbsoluteFile())));
    }


    class WelfareViewPagerAdapter extends PagerAdapter {

        @BindView(R.id.welfare_item_image)
        ImageView mImage;
        List<String> mUrls;
        Activity mContext;
        private List<View> mViews = new ArrayList<>();

        WelfareViewPagerAdapter(Activity context, List<String> urls) {
            mContext = context;
            mUrls = urls;
        }

        void update(List<String> urls) {
            mUrls = urls;
            notifyDataSetChanged();
        }

        void clear() {
            mUrls.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.welfare_image_item, null);
            ButterKnife.bind(this, view);

            String path = mUrls.get(position);
            Glide.with(getActivity())
                    .load(path)
                    .crossFade(700)
                    .placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.img_two_bi_one)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            toastMessage("资源加载异常");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            int height = mImage.getHeight();
                            int h = getActivity().getWindowManager().getDefaultDisplay().getHeight();
                            if (height > h) {
                                mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                mImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    }).into(mImage);

            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View imageView = (View) object;
            if (imageView == null) {
                return;
            }
            Glide.clear(imageView);
            container.removeView(imageView);
        }
    }
}
