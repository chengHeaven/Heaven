package com.github.chengheaven.heaven.view.main.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.bean.RxCustomer;
import com.github.chengheaven.heaven.bean.RxDaily;
import com.github.chengheaven.heaven.bean.RxPosition;
import com.github.chengheaven.heaven.constants.Constants;
import com.github.chengheaven.heaven.helper.BaseFragment;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.RxBus;
import com.github.chengheaven.heaven.http.cache.ACache;
import com.github.chengheaven.heaven.presenter.gank.EveryContract;
import com.github.chengheaven.heaven.tools.GlideImageLoader;
import com.github.chengheaven.heaven.tools.SharedPreferenceUtil;
import com.github.chengheaven.heaven.tools.TimeUtil;
import com.github.chengheaven.heaven.tools.Utils;
import com.github.chengheaven.heaven.view.webview.WebViewActivity;
import com.youth.banner.Banner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class EveryFragment extends BaseFragment implements EveryContract.View {

    @BindView(R.id.ll_loading)
    LinearLayout mLoading;
    @BindView(R.id.loading)
    ImageView mImageLoading;
    @BindView(R.id.every_recycler)
    RecyclerView mEveryView;

    private EveryAdapter mAdapter;
    private EveryBaseAdapter mEveryAdapter;
    private RotateAnimation animation;
    public ACache mCache;
    private boolean isFirst = true;
    private List<String> last = new ArrayList<>();

    private EveryContract.Presenter mPresenter;

    public static EveryFragment newInstance() {
        return new EveryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.every_fragment, container, false);
        ButterKnife.bind(this, view);

        mEveryView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mEveryAdapter = new EveryBaseAdapter();
        mEveryView.setAdapter(mEveryAdapter);
        mCache = ACache.get(getContext());

        String time = TimeUtil.getData();
        String[] s = time.split("-");

        if (isFirst) {
            isFirst = false;
            mLoading.setVisibility(View.VISIBLE);
//            mContent.setVisibility(View.GONE);
            animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);//设置动画持续时间
            animation.setInterpolator(new LinearInterpolator());//不停顿
            animation.setRepeatCount(100);
            mImageLoading.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mPresenter.getBannerUrl();
                    if (TimeUtil.isRightTime()) {
//                        mPresenter.getRecycler("2017", "09", "01");
                        mPresenter.getRecycler(s[0], s[1], s[2]);
                        last = Arrays.asList(s);
                    } else {
                        last = TimeUtil.getLastTime(s[0], s[1], s[2]);
                        mPresenter.getRecycler(last.get(0), last.get(1), last.get(2));
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    hideAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mImageLoading.startAnimation(animation);
        } else {
            ArrayList<String> urls = (ArrayList<String>) mCache.getAsObject(Constants.BANNER_PIC);
            setBannerUrl(urls, 1);
            ArrayList<List<HomeBean>> list = (ArrayList<List<HomeBean>>) mCache.getAsObject(Constants.EVERYDAY_CONTENT);
            mEveryAdapter.updateList(list);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume: ", "onResume");
        if (mAdapter != null) {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, SharedPreferenceUtil.getInstance(getContext()).getItemPosition().split(" "));
            mAdapter.update(list);
        }
    }

    @Override
    public void setBannerUrl(List<String> urls, int i) {
        if (urls != null && urls.size() != 0) {
            mEveryAdapter.updateUrl(urls);
            if (i == 0) {
                mCache.remove(Constants.BANNER_PIC);
                mCache.put(Constants.BANNER_PIC, (Serializable) urls, 259200);
            }
        } else {
            Utils.showSnackBar(getActivity(), "获取banner失败");
        }
    }

    public void showAnimation() {
        mLoading.setVisibility(View.VISIBLE);
        animation.startNow();
    }

    public void hideAnimation() {
        mLoading.setVisibility(View.GONE);
        animation.cancel();
    }

    @Override
    public void isFirst() {
        isFirst = true;
    }

    @Override
    public void updateRecyclerAdapter(ArrayList<List<HomeBean>> lists) {
        mCache.remove(Constants.EVERYDAY_CONTENT);
        mCache.put(Constants.EVERYDAY_CONTENT, lists, 432000);
        mEveryAdapter.updateList(lists);
    }

    @Override
    public void updateRecyclerFromCache() {
        ArrayList<List<HomeBean>> list = (ArrayList<List<HomeBean>>) mCache.getAsObject(Constants.EVERYDAY_CONTENT);
        if (list == null || list.size() == 0) {
            getAgainRecycler();
        } else {
            mEveryAdapter.updateList(list);
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (EveryContract.Presenter) presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }

    @Override
    public void getAgainRecycler() {
        getLastDate(last);
        mPresenter.getRecycler(last.get(0), last.get(1), last.get(2));
    }

    private List<String> getLastDate(List<String> lastList) {
        last = TimeUtil.getLastTime(lastList.get(0), lastList.get(1), lastList.get(2));
        return last;
    }

    class EveryBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<List<HomeBean>> mList;
        private List<String> urls = new ArrayList<>();
        private static final int HEADER = 1;
        private static final int RECYCLER = 2;
        private static final int BOTTOM = 3;

        void updateUrl(List<String> url) {
            urls.clear();
            urls = url;
            notifyDataSetChanged();
        }

        void updateList(List<List<HomeBean>> lists) {
            mList = new ArrayList<>();
            mList = lists;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return HEADER;
                case 1:
                    return RECYCLER;
                case 2:
                    return BOTTOM;
            }
            return super.getItemViewType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case HEADER:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_header, parent, false);
                    return new HeaderViewHolder(view);
                case RECYCLER:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_recycler, parent, false);
                    return new RecyclerViewHolder(view);
                case BOTTOM:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_bottom, parent, false);
                    return new BottomViewHolder(view);
                default:
                    break;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder) {
                ((HeaderViewHolder) holder).mBanner.setImageLoader(new GlideImageLoader());
                ((HeaderViewHolder) holder).mBanner.setImages(urls);
                ((HeaderViewHolder) holder).mBanner.setDelayTime(3000);
                ((HeaderViewHolder) holder).mBanner.start();
                ((HeaderViewHolder) holder).mBanner.getFocusedChild();
            } else if (holder instanceof RecyclerViewHolder) {
                ((RecyclerViewHolder) holder).mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                ((RecyclerViewHolder) holder).mRecycler.setNestedScrollingEnabled(true);
                mAdapter = new EveryAdapter(mList);
                ((RecyclerViewHolder) holder).mRecycler.setAdapter(mAdapter);
            } else if (holder instanceof BottomViewHolder) {
                ((BottomViewHolder) holder).mChangeItem.setVisibility(View.VISIBLE);
                ((BottomViewHolder) holder).mChangeItem.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), ItemChangeActivity.class);
                    startActivity(intent);
                });
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        class HeaderViewHolder extends RecyclerView.ViewHolder {

            @OnClick({R.id.every_xd, R.id.daily_btn, R.id.every_hot})
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.every_xd:
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("mUrl", "https://gank.io/xiandu");
                        intent.putExtra("mTitle", getActivity().getResources().getString(R.string.loading));
                        getActivity().startActivity(intent);
                        break;
                    case R.id.daily_btn:
                        RxPosition msgPosition = new RxPosition();
                        msgPosition.setPosition(2);
                        RxBus.getDefault().post(msgPosition);
                        RxCustomer msg = new RxCustomer();
                        msg.setType(getString(R.string.all));
                        RxBus.getDefault().post(msg);
                        break;
                    case R.id.every_hot:
                        RxDaily rxDaily = new RxDaily();
                        rxDaily.setPosition(1);
                        RxBus.getDefault().post(rxDaily);
                        break;
                    default:
                        break;
                }
            }

            @BindView(R.id.every_banner)
            Banner mBanner;

            HeaderViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class RecyclerViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.every_recycler)
            RecyclerView mRecycler;

            RecyclerViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class BottomViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.ll_change_item)
            LinearLayout mChangeItem;

            BottomViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class EveryItemAdapter extends RecyclerView.Adapter<EveryItemAdapter.ViewHolder> {

        private List<HomeBean> mList;

        EveryItemAdapter(List<HomeBean> list) {
            mList = new ArrayList<>();
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.everyday_adapter_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EveryItemAdapter.ViewHolder holder, int position) {
            if (mList.get(position).getType().contains("Welfare")) {
                Glide.with(getActivity())
                        .load(mList.get(position).getUrl())
                        .placeholder(R.drawable.img_two_bi_one)
                        .crossFade(1500)
                        .centerCrop()
                        .error(R.drawable.img_two_bi_one)
                        .into(holder.mImage);
            } else {

                Glide.with(getActivity())
//                        .load(mList.get(position).getUrl())
                        .load(mList.get(position).getImage())
                        .placeholder(R.drawable.img_two_bi_one)
                        .crossFade(1500)
//                        .centerCrop()
                        .error(R.drawable.img_two_bi_one)
                        .into(holder.mImage);
            }
            holder.mTextView.setText(mList.get(position).getDesc());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("mUrl", mList.get(position).getUrl());
                intent.putExtra("mTitle", getActivity().getResources().getString(R.string.loading));
                getActivity().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : (mList.size() > 6 ? 6 : mList.size());
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (mList.size() == 1) {
                        return 6;
                    } else if (mList.size() == 2) {
                        return 3;
                    } else if (mList.size() % 3 == 0) {
                        return 2;
                    } else if (mList.size() == 4) {
                        switch (position) {
                            case 3:
                                return 6;
                            default:
                                return 2;
                        }
                    } else if (mList.size() == 5) {
                        switch (position) {
                            case 3:
                                return 3;
                            case 4:
                                return 3;
                            default:
                                return 2;
                        }
                    }
                    return 0;
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.every_adapter_item_image)
            ImageView mImage;
            @BindView(R.id.every_adapter_item_text)
            TextView mTextView;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class EveryAdapter extends RecyclerView.Adapter<EveryAdapter.ViewHolder> {

        private List<List<HomeBean>> mList;
        private List<String> mItemList = Arrays.asList(SharedPreferenceUtil.getInstance(getContext()).getItemPosition().split(" "));
        EveryItemAdapter adapter;

        private boolean isHave = false;

        EveryAdapter(List<List<HomeBean>> lists) {
            mList = new ArrayList<>();
            this.mList = lists;
            notifyDataSetChanged();
        }

        void update(List<String> list) {
            mItemList = list;
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_four, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            isHave = false;
            for (int i = 0; i < mList.size(); i++) {
                if (mItemList.get(position).equals(Constants.getTitles().get(mList.get(i).get(0).getType()))) {
                    isHave = true;
                    holder.setVisibility(true);
                    holder.mText.setText(mItemList.get(position));
                    holder.mImage.setImageResource(mList.get(i).get(0).getDrawable());

                    holder.mEveryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 6));
                    holder.mEveryRecycler.setNestedScrollingEnabled(false);

                    if (mList.get(i).size() > 6) {
                        List<HomeBean> list = new ArrayList<>();
                        for (int j = 0; j < 6; j++) {
                            list.add(mList.get(i).get(j));
                        }
                        adapter = new EveryItemAdapter(list);
                    } else {
                        adapter = new EveryItemAdapter(mList.get(i));
                    }
                    holder.mEveryRecycler.setAdapter(adapter);
                }
            }

            if (!isHave) {
                holder.setVisibility(false);
            }

            holder.mMore.setOnClickListener(v -> {

                switch (holder.mText.getText().toString()) {
                    case "Android":
                        RxPosition rxPosition = new RxPosition();
                        rxPosition.setPosition(3);
                        RxBus.getDefault().post(rxPosition);
                        break;

                    case "福利":
                        RxPosition rxWelfare = new RxPosition();
                        rxWelfare.setPosition(1);
                        RxBus.getDefault().post(rxWelfare);
                        break;

                    default:
                        RxPosition msgPosition = new RxPosition();
                        msgPosition.setPosition(2);
                        RxBus.getDefault().post(msgPosition);
                        RxCustomer msg = new RxCustomer();
                        msg.setType(holder.mText.getText().toString());
                        RxBus.getDefault().post(msg);
                        break;
                }
            });

        }

        @Override
        public int getItemCount() {
            return mItemList == null ? 0 : mItemList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.item_recycler)
            RecyclerView mEveryRecycler;
            @BindView(R.id.title_image)
            ImageView mImage;
            @BindView(R.id.title_text)
            TextView mText;
            @BindView(R.id.tv_more)
            TextView mMore;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setVisibility(boolean visible) {
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                if (visible) {
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    itemView.setVisibility(View.VISIBLE);
                } else {
                    itemView.setVisibility(View.GONE);
                    param.height = 0;
                    param.width = 0;
                }
                itemView.setLayoutParams(param);
            }
        }
    }
}
