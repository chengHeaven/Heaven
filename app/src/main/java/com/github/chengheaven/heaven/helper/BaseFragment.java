package com.github.chengheaven.heaven.helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.chengheaven.heaven.customer.LoadingProgressDialog;

/**
 * @author Heaven・Cheng Created on 17/2/21.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private LoadingProgressDialog mLoadingProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingProgressDialog = new LoadingProgressDialog(getActivity());
    }


    @Override
    public void showWaiting() {
        mLoadingProgressDialog.show();
    }

    @Override
    public void hideWaiting() {
        mLoadingProgressDialog.dismiss();
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void hideError() {

    }
}
