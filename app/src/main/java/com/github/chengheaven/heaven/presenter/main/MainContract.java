package com.github.chengheaven.heaven.presenter.main;

import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

public class MainContract {

    public interface View extends BaseView {

        void setCurrentItem(int position);
    }

    public interface Presenter extends BasePresenter {

        void clear();
    }
}
