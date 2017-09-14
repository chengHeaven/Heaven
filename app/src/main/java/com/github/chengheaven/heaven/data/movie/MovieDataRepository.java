package com.github.chengheaven.heaven.data.movie;

import android.content.Context;

import com.github.chengheaven.heaven.bean.MovieBean;
import com.github.chengheaven.heaven.bean.MovieDetailBean;
import com.github.chengheaven.heaven.data.RetrofitFactory;
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
 * @author Heaven・Cheng Created on 17/5/16.
 */

public class MovieDataRepository implements MovieDataSource {

    private Context mContext;

    public MovieDataRepository(Context context) {
        mContext = context;
    }

    @Override
    public void getHotMovie(MovieCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getDouBanInstance().getHotMovie();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray array = jsonObject.getJSONArray("subjects");
                    String s = array.toString();
                    List<MovieBean> movieBeen = new Gson().fromJson(s, new TypeToken<List<MovieBean>>() {
                    }.getType());
                    callback.onSuccess(movieBeen);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("获取电影资源失败!");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getMovieTop(int start, int count, MovieCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getDouBanInstance().getMovieTop250(start, count);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray array = jsonObject.getJSONArray("subjects");
                    String s = array.toString();
                    List<MovieBean> movieBean = new Gson().fromJson(s, new TypeToken<List<MovieBean>>() {
                    }.getType());
                    callback.onSuccess(movieBean);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("获取电影资源失败!");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getMovieDetail(String id, MovieDetailCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getDouBanInstance().getMovieDetail(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    MovieDetailBean movieDetailBean = new Gson().fromJson(str, MovieDetailBean.class);
                    callback.onSuccess(movieDetailBean);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败!");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("获取电影资源失败!");
                t.printStackTrace();
            }
        });

    }
}
