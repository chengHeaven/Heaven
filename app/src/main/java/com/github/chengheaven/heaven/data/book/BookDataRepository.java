package com.github.chengheaven.heaven.data.book;

import android.content.Context;

import com.github.chengheaven.heaven.bean.BookBean;
import com.github.chengheaven.heaven.http.RetrofitFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class BookDataRepository implements BookDataSource {

    private Context mContext;

    public BookDataRepository(Context context) {
        mContext = context;
    }

    @Override
    public void getBookData(String tag, int start, int count, BookCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getDouBanInstance().getBook(tag, start, count);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray array = jsonObject.getJSONArray("books");
                    String s = array.toString();
                    List<BookBean> bookBean = new Gson().fromJson(s, new TypeToken<List<BookBean>>() {
                    }.getType());
                    callback.onSuccess(bookBean);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败!");
                    e.printStackTrace();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("获取书籍资源失败!");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getBookDetailData(String id, BookDetailCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getDouBanInstance().getBookDetail(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    BookBean bookBean = new Gson().fromJson(str, BookBean.class);
                    callback.onSuccess(bookBean);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败!");
                    e.printStackTrace();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("获取书籍资源失败!");
                t.printStackTrace();
            }
        });
    }
}
