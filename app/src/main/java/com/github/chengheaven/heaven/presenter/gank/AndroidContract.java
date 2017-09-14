package com.github.chengheaven.heaven.presenter.gank;

import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.presenter.BasePresenter;
import com.github.chengheaven.heaven.view.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/4/21.
 */

public class AndroidContract {

    public interface View extends BaseView {

        void updateList(List<HomeBean> beanList);

        void refreshList(List<HomeBean> beanList);

        void recyclerComplete();
    }

    public interface Presenter extends BasePresenter {

        void getAndroid(String type, String id, int page, int per_page);
    }
}
