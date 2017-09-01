package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class CustomizationContract {

    public interface View extends BaseView {

        void updateList(List<HomeBean> beanList);

        void updateSingleList(List<HomeBean> beanList);

        void setType(String type);
    }

    public interface Presenter extends BasePresenter {

        void clear();

        void getCustomization(String refresh, String id, int page, int per_page);
    }
}
