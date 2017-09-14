package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class EveryContract {

    public interface View extends BaseView {

        void setBannerUrl(List<String> urls, int i);

        void showAnimation();

        void hideAnimation();

        void updateRecyclerAdapter(ArrayList<List<HomeBean>> lists);

        void updateRecyclerFromCache();

        void getAgainRecycler();
    }

    public interface Presenter extends BasePresenter {

        void getBannerUrl();

        void getRecycler(String year, String month, String day);
    }
}
