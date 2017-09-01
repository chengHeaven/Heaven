package com.github.chengheaven.heaven.view.main.gank;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.gank.WelfareContract;
import com.github.chengheaven.heaven.view.welfareImage.WelfareImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class WelfareFragment extends BaseFragment implements WelfareContract.View {

    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    @BindView(R.id.ll_loading_bar)
    LinearLayout mLoadingBar;
    @BindView(R.id.ll_error_refresh)
    LinearLayout mErrorBar;
    @BindView(R.id.welfare_recycler)
    XRecyclerView mRecycler;

    private WelfareAdapter mWelfareAdapter;
    private AnimationDrawable mAnimationDrawable;
    private int page = 1;

    private WelfareContract.Presenter mPresenter;

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.welfare_fragment, container, false);
        ButterKnife.bind(this, view);

        showLoading();
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecycler.setPullRefreshEnabled(false);
        mWelfareAdapter = new WelfareAdapter();
        mRecycler.setAdapter(mWelfareAdapter);

        page = 1;
        mPresenter.getWelfare(getString(R.string.welfare), page, 20);

        mRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getWelfare(getString(R.string.welfare), page, 20);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (WelfareContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    @Override
    public void showError() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    @Override
    public void updateList(List<String> urls) {
        mRecycler.refreshComplete();
        mWelfareAdapter.update(urls);
    }

    @OnClick({R.id.ll_error_refresh})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_error_refresh:
                showLoading();
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWelfareAdapter.clear();
    }

    class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.ViewHolder> {

        private List<String> mUrls = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mUrls.get(position))
                    .asBitmap()
                    .placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.img_two_bi_one)
                    .into(holder.mImage);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), WelfareImageActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mUrls == null ? 0 : mUrls.size();
        }

        void update(List<String> urls) {
            mUrls.addAll(urls);
            notifyDataSetChanged();
        }

        void clear() {
            mUrls.clear();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.welfare_image)
            ImageView mImage;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
