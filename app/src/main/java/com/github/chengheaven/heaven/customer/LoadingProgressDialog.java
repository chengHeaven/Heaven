package com.github.chengheaven.heaven.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.github.chengheaven.heaven.R;

public class LoadingProgressDialog extends ProgressDialog {

    public LoadingProgressDialog(Context context) {
        super(context, R.style.Progress_Dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_progress_dialog);
    }
}
