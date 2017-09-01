package com.github.chengheaven.heaven.presenter.book;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.helper.BasePresenter;
import com.github.chengheaven.heaven.helper.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class LiteratureContract {

    public interface View extends BaseView {

        void refreshLiteratureView(List<BookBean> results);

        void updateLiteratureView(List<BookBean> results);
    }

    public interface Presenter extends BasePresenter {

        void getLiteratureList(String type, String tag, int start, int count);
    }
}

