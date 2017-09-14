package com.github.chengheaven.heaven.presenter.mainFragment;

import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

/**
 * @author Heaven・Cheng Created on 17/5/15.
 */

public class GankContract {

    public interface View extends BaseView {

        void setCurrentItem(int position);
    }

    public interface Presenter extends BasePresenter {

        void clear();
    }
}
