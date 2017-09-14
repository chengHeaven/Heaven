package com.github.chengheaven.heaven.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Heaven・Cheng Created on 17/9/5.
 */

public class SharedPreferenceUtil {

    private static SharedPreferenceUtil sInstance;

    private static SharedPreferences mPreferences;

    private SharedPreferenceUtil(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferenceUtil getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferenceUtil(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public void setItemPosition(String str) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("item_order", str);
        editor.apply();
    }

    public String getItemPosition() {
        return mPreferences.getString("item_order", "Android 福利 IOS 休息视频 拓展资源 前端 App");
    }
}
