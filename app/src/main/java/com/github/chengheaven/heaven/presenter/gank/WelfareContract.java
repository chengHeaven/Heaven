package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class WelfareContract {

    public interface View extends BaseView {

        void updateList(List<String> urls);
    }

    public interface Presenter extends BasePresenter {

        void getWelfare(String id, int page, int per_page);
    }
}
