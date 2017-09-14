package com.github.chengheaven.heaven.view;

import com.github.chengheaven.heaven.presenter.BasePresenter;

/**
 * @author heaven_Cheng Created on 17/2/9.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

    void showWaiting();

    void hideWaiting();

    void toastMessage(String msg);

    void showLoading();

    void hideLoading();

    void showError();

    void hideError();
}
