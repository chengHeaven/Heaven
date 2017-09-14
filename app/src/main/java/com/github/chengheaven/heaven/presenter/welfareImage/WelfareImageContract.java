package com.github.chengheaven.heaven.presenter.welfareImage;

import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/5/9.
 */

public class WelfareImageContract {

    public interface View extends BaseView {

        void updateViewPager(List<String> urls);

        void setCurrentItemViewPager(int position);

        void setIndicator(int size);

        void updateIndicator(int position);
    }

    public interface Presenter extends BasePresenter {

        void setPosition(int position);

        void initViewPager();
    }
}
