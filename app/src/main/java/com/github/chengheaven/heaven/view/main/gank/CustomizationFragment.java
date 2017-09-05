package com.github.chengheaven.heaven.view.main.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.presenter.gank.CustomizationContract;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class CustomizationFragment extends BaseFragment implements CustomizationContract.View {

    @BindView(R.id.customization_recycler)
    XRecyclerView mRecycler;
    private int page = 1;
    private String type = "all";
    private int per_page = 20;
    HeaderView mHeaderView;
    private Map<String, String> map = new LinkedHashMap<>();

    private CustomizationAdapter mAdapter;

    private CustomizationContract.Presenter mPresenter;

    public static CustomizationFragment newInstance() {
        return new CustomizationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customization_fragment, container, false);
        ButterKnife.bind(this, view);

        map.put("Android", "Android");
        map.put("福利", "Welfare");
        map.put("iOS", "iOS");
        map.put("休息视频", "Video");
        map.put("拓展资源", "Expand");
        map.put("前端", "Front");
        map.put("App", "App");
        map.put("瞎推荐", "Recommend");


        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setPullRefreshEnabled(false);
        View header = View.inflate(getContext(), R.layout.header_customization, null);
        mHeaderView = new HeaderView(header);
        mRecycler.addHeaderView(header);
        mAdapter = new CustomizationAdapter();
        mRecycler.setAdapter(mAdapter);

        mPresenter.getCustomization(null, type, page, per_page);

        mRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getCustomization("refresh", type, page, per_page);
            }
        });

        mPresenter.start();

        return view;
    }

    class HeaderView {

        @BindView(R.id.ll_choose_catalogue)
        LinearLayout mChoose;
        @BindView(R.id.customization_header_type_text)
        TextView mTypeText;

        HeaderView(View header) {
            ButterKnife.bind(this, header);

            mChoose.setOnClickListener(v -> new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                    .title("选择分类")
                    .sheet(R.menu.custom_header)
                    .listener((dialog, which) -> {
                        switch (which) {
                            case R.id.gank_all:
                                if (isOtherType(getString(R.string.all_all))) {
                                    mTypeText.setText(R.string.all_all);
                                    type = getString(R.string.all);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_android:
                                if (isOtherType(getString(R.string.android))) {
                                    mTypeText.setText(R.string.android);
                                    type = getString(R.string.android);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_ios:
                                if (isOtherType(getString(R.string.ios))) {
                                    mTypeText.setText(R.string.ios);
                                    type = getString(R.string.iOS);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_qian:
                                if (isOtherType(getString(R.string.front))) {
                                    mTypeText.setText(R.string.front);
                                    type = getString(R.string.front);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_xia:
                                if (isOtherType(getString(R.string.recommend))) {
                                    mTypeText.setText(R.string.recommend);
                                    type = getString(R.string.recommend);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_app:
                                if (isOtherType(getString(R.string.app))) {
                                    mTypeText.setText(R.string.app);
                                    type = getString(R.string.app);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_movie:
                                if (isOtherType(getString(R.string.video))) {
                                    mTypeText.setText(R.string.video);
                                    type = getString(R.string.video);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            case R.id.gank_resource:
                                if (isOtherType(getString(R.string.expand))) {
                                    mTypeText.setText(R.string.expand);
                                    type = getString(R.string.expand);
                                    page = 1;
                                    mPresenter.getCustomization(null, type, page, per_page);
                                }
                                break;

                            default:
                                break;
                        }
                    }).show());
        }

        private boolean isOtherType(String selectType) {
            String clickText = mTypeText.getText().toString().trim();
            if (clickText.equals(selectType)) {
                toastMessage("当前已经是" + selectType + "分类");
                return false;
            } else {
                // 重置XRecyclerView状态，解决 如出现刷新到底无内容再切换其他类别后，无法上拉加载的情况
                mRecycler.reset();
                return true;
            }
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (CustomizationContract.Presenter) presenter;
    }

    @Override
    public void updateList(List<HomeBean> beanList) {
        mRecycler.refreshComplete();
        mAdapter.update(beanList);
    }

    @Override
    public void updateSingleList(List<HomeBean> beanList) {
        mRecycler.refreshComplete();
        mAdapter.updateSingle(beanList);
    }

    @Override
    public void setType(String t) {
        type = t;
        mAdapter.clear();
        mHeaderView.mTypeText.setText(t);
        if (t.equals("IOS")) {
            type = getString(R.string.iOS);
        }
        mPresenter.getCustomization(null, type, page, per_page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    class CustomizationAdapter extends RecyclerView.Adapter<CustomizationAdapter.ViewHolder> {

        private List<HomeBean> mList = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customization_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mList.get(position).getType().equals(getString(R.string.welfare))) {
                holder.mWelfare.setVisibility(View.VISIBLE);
                holder.mNormal.setVisibility(View.GONE);
                Glide.with(getActivity())
                        .load(mList.get(position).getUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .into(holder.mWelfare);
            } else {
                holder.mNormal.setVisibility(View.VISIBLE);
                holder.mImage.setVisibility(View.GONE);
                holder.mWelfare.setVisibility(View.GONE);
                if (mList.get(position).getImages() != null) {
                    holder.mImage.setVisibility(View.VISIBLE);
                    Glide.with(getActivity())
                            .load(mList.get(position).getImages().get(0))
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(R.drawable.img_two_bi_one)
                            .error(R.drawable.img_two_bi_one)
                            .into(holder.mImage);
                }
                holder.mTitle.setText(mList.get(position).getDesc());
            }
            holder.mWho.setText(mList.get(position).getWho());
            holder.mType.setText(mList.get(position).getType());
            holder.mDate.setText(getDate(mList.get(position).getPublishedAt()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("mUrl", mList.get(position).getUrl());
                intent.putExtra("mTitle", getActivity().getResources().getString(R.string.loading));
                getActivity().startActivity(intent);
            });
        }

        String getDate(String date) {
            String[] s = date.split("-");
            date = s[1] + "-" + s[2].substring(0, 2);
            return date;
        }

        void clear() {
            mList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        void update(List<HomeBean> beanList) {
            if (beanList != null) {
                mList.addAll(beanList);
                notifyDataSetChanged();
            }
        }

        void updateSingle(List<HomeBean> beanList) {
            mList.clear();
            mList = beanList;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.customization_title)
            TextView mTitle;
            @BindView(R.id.customization_image)
            ImageView mImage;
            @BindView(R.id.customization_welfare)
            ImageView mWelfare;
            @BindView(R.id.customization_who)
            TextView mWho;
            @BindView(R.id.customization_type)
            TextView mType;
            @BindView(R.id.customization_date)
            TextView mDate;
            @BindView(R.id.customization_normal)
            RelativeLayout mNormal;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
