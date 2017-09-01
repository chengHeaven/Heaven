package com.github.chengheaven.heaven.view.bookDetail;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.customer.statusbar.StatusBarUtil;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.bookDetail.BookDetailContract;
import com.github.chengheaven.heaven.tools.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author Heaven・Cheng Created on 17/6/13.
 */

public class BookDetailFragment extends BaseFragment implements BookDetailContract.View {

    @BindView(R.id.book_detail_background)
    ImageView mBackground;
    @BindView(R.id.book_detail_image)
    ImageView mImage;
    @BindView(R.id.book_detail_author)
    TextView mAuthor;
    @BindView(R.id.book_detail_score)
    TextView mScore;
    @BindView(R.id.book_detail_score_people)
    TextView mScorePeople;
    @BindView(R.id.book_detail_day)
    TextView mDay;
    @BindView(R.id.book_detail_press)
    TextView mPress;
    @BindView(R.id.book_detail_fragment_content)
    LinearLayout mContent;
    @BindView(R.id.book_detail_summary)
    TextView mBookSummary;
    @BindView(R.id.book_detail_author_summary)
    TextView mAuthorSummary;
    @BindView(R.id.book_detail_catalog)
    TextView mCatalog;

    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    @BindView(R.id.ll_loading_bar)
    LinearLayout mLoadingBar;
    @BindView(R.id.ll_error_refresh)
    LinearLayout mErrorBar;

    private AnimationDrawable mAnimationDrawable;

    private int slidingDistance;
    private int imageBgHeight;

    private BookDetailContract.Presenter mPresenter;

    public static BookDetailFragment newInstance() {
        return new BookDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        showLoading();
        mPresenter.start();

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (BookDetailContract.Presenter) presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateTitleView(BookBean result) {
        initSlideShapeTheme(mBackground);
        StringBuilder sb = new StringBuilder();
        sb.append("作者：");
        for (String author : result.getAuthor()) {
            sb.append(author).append(" / ");
        }
        ((BookDetailActivity) getActivity()).setTitle(result.getTitle(), sb.toString().equals("") ? "" : sb.substring(0, sb.length() - 3));

        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this)
                .load(result.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity().getApplicationContext(), 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((BookDetailActivity) getActivity()).setToolbarBackground(Color.TRANSPARENT);
                ((BookDetailActivity) getActivity()).getBackgroundImage().setImageAlpha(0);
                ((BookDetailActivity) getActivity()).getBackgroundImage().setVisibility(View.VISIBLE);
                return false;
            }
        }).into(((BookDetailActivity) getActivity()).getBackgroundImage());

        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this)
                .load(result.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity().getApplicationContext(), 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((BookDetailActivity) getActivity()).getBackgroundImage().setVisibility(View.VISIBLE);
                mBackground.setImageAlpha(255);
                mBackground.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(mBackground);

        Glide.with(getActivity())
                .load(result.getImages().getLarge())
                .into(mImage);

        mAuthor.setText(sb.toString().equals("") ? "" : sb.substring(0, sb.length() - 3));
        mScore.setText(String.format("评分：%s", result.getRating().getAverage()));
        mScorePeople.setText(String.format("%s 人评分", result.getRating().getNumRaters()));
        mDay.setText(result.getPubdate().equals("") ? "暂无" : result.getPubdate());
        mPress.setText(result.getPublisher().equals("") ? "暂无" : result.getPublisher());
    }

    @Override
    public void updateView(BookBean result) {
        mBookSummary.setText(result.getSummary().equals("") ? "暂无" : result.getSummary());
        mAuthorSummary.setText(result.getAuthor_intro().equals("") ? "暂无" : result.getAuthor_intro());
        mCatalog.setText(result.getCatalog().equals("") ? "暂无" : result.getCatalog());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void initSlideShapeTheme(ImageView mHeaderBg) {

        // toolbar 的高
        int toolbarHeight = ((BookDetailActivity) getActivity()).getToolbar().getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(getActivity());

        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = ((BookDetailActivity) getActivity()).getBackgroundImage().getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) ((BookDetailActivity) getActivity()).getBackgroundImage().getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        ((BookDetailActivity) getActivity()).getBackgroundImage().setImageAlpha(0);
        StatusBarUtil.setTranslucentImageHeader(getActivity(), 0, ((BookDetailActivity) getActivity()).getToolbar());

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (mHeaderBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mHeaderBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(getActivity()), 0, 0);

            ViewGroup.LayoutParams imgItemBgparams = mHeaderBg.getLayoutParams();
            // 获得高斯图背景的高度
            imageBgHeight = imgItemBgparams.height;
        }

        // 变色
        initScrollViewListener();
        initNewSlidingParams();
    }

    private void initScrollViewListener() {
        // 为了兼容23以下
        ((BookDetailActivity) getActivity()).getBaseScroll().setOnScrollChangeListener((scrollX, scrollY, oldScrollX, oldScrollY) -> scrollChangeHeader(scrollY));
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (CommonUtils.getDimens(R.dimen.nav_bar_height) + StatusBarUtil.getStatusBarHeight(getActivity()));
        // 减掉后，没到顶部就不透明了
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (CommonUtils.getDimens(R.dimen.base_header_activity_slide_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = ((BookDetailActivity) getActivity()).getBackgroundImage().getDrawable();

        if (drawable == null) {
            return;
        }
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            ((BookDetailActivity) getActivity()).getBackgroundImage().setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            ((BookDetailActivity) getActivity()).getBackgroundImage().setImageDrawable(drawable);
        }
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mContent.setVisibility(View.VISIBLE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    @Override
    public void showError() {
        mContent.setVisibility(View.GONE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }
}
