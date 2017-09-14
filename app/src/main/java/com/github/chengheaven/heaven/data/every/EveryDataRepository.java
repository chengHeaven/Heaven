package com.github.chengheaven.heaven.data.every;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.github.chengheaven.heaven.R;
import com.github.chengheaven.heaven.bean.FrontpageBean;
import com.github.chengheaven.heaven.bean.HomeBean;
import com.github.chengheaven.heaven.constants.Constants;
import com.github.chengheaven.heaven.data.RetrofitFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

public class EveryDataRepository implements EveryDataSource {

    private Context mContext;
    private List<String> mUrls = new ArrayList<>();

    public EveryDataRepository(Context context) {
        mContext = context;
    }

    @Override
    public void getBanner(EveryCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getTingInstance().getFrontpage();
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    int code = jsonObject.getInt("error_code");
                    if (code != 22000) {
                        String message = jsonObject.getString("error_code");
                        callback.onFailed(message);
                    }
                    JSONObject object = jsonObject.getJSONObject("result").getJSONObject("focus");
                    JSONArray jsonArray = object.getJSONArray("result");
                    List<FrontpageBean> frontpageBeenList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FrontpageBean>>() {
                    }.getType());

                    List<String> urls = new ArrayList<>();
                    for (int i = 0; i < frontpageBeenList.size(); i++) {
                        String url = frontpageBeenList.get(i).getRandpic();
                        urls.add(url);
                    }
                    callback.onSuccess(urls);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("网络异常");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getRecycler(String year, String month, String day, EveryContentCallback<HomeBean> callback) {
        Call<ResponseBody> call = RetrofitFactory.getGankInstance().getGankIoDay(year, month, day);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();

                    JSONObject json = new JSONObject(str);
                    String result = json.toString();
                    result = result.replaceAll("休息视频", "Video").replaceAll("福利", "Welfare").replaceAll("前端", "Front")
                            .replaceAll("拓展资源", "Expand").replaceAll("瞎推荐", "Recommend");
                    JSONObject jsonObject = new JSONObject(result);
                    boolean code = jsonObject.getBoolean("error");
                    if (code) {
                        String message = jsonObject.getString("error");
                        callback.onFailed(message);
                        return;
                    }

                    if (jsonObject.getString("results").equals("{}")) {
                        callback.onFailed("results is empty");
                        return;
                    }

                    ArrayList<List<HomeBean>> homeList = new ArrayList<>();

                    List<String> keyList = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("category");
                    for (String KEY : KEYS) {
                        if (jsonArray.toString().contains(KEY)) {
                            keyList.add(KEY);
                        }
                    }

                    for (int i = 0; i < keyList.size(); i++) {
                        JSONArray array = jsonObject.getJSONObject("results").getJSONArray(keyList.get(i));
                        List<HomeBean> homeBeanList = new Gson().fromJson(array.toString(), new TypeToken<List<HomeBean>>() {
                        }.getType());
                        for (int k = 0; k < homeBeanList.size(); k++) {
                            int r = new Random().nextInt(22);
                            homeBeanList.get(k).setImage(Constants.HOME_SIX_URLS[r]);
                            homeBeanList.get(k).setDrawable(getTitleDrawables().get(keyList.get(i)));
                            if (homeBeanList.size() % 3 == 1) {
                                if (k == homeBeanList.size() - 1) {
                                    int r0 = new Random().nextInt(13);
                                    homeBeanList.get(k).setImage(Constants.HOME_ONE_URLS[r0]);
                                }
                            }
                        }
                        homeList.add(homeBeanList);

                    }
                    callback.onSuccess(homeList);

                } catch (Exception e) {
                    callback.onFailed("获取数据失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("网络异常");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getGankData(String id, int page, int pre_page, EveryCallback callback) {
        Call<ResponseBody> call = RetrofitFactory.getGankInstance().getGankIoData(id, page, pre_page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.getBoolean("error")) {
                        callback.onFailed("获取数据失败");
                    }

                    if (jsonObject.getString("results").equals("{}")) {
                        callback.onFailed("results is empty");
                        return;
                    }

                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    List<HomeBean> welfareList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HomeBean>>() {
                    }.getType());
                    callback.onSuccess(welfareList);
                } catch (Exception e) {
                    callback.onFailed("获取数据失败");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailed("网络异常");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void setImageUrlsLocal(List<String> urls) {
        mUrls.addAll(urls);
    }

    @Override
    public List<String> getImageUrlsLocal() {
        return mUrls;
    }

    @Override
    public void clear() {
        mUrls.clear();
    }

    private static Map<String, Integer> getTitleDrawables() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Android", R.drawable.home_title_android);
        map.put("Welfare", R.drawable.home_title_meizi);
        map.put("iOS", R.drawable.home_title_ios);
        map.put("Video", R.drawable.home_title_movie);
        map.put("Expand", R.drawable.home_title_source);
        map.put("Front", R.drawable.home_title_qian);
        map.put("App", R.drawable.home_title_app);
        map.put("Recommend", R.drawable.home_title_xia);
        return map;
    }

    private static final Integer[] getTitleDrawables = new Integer[]{
            R.drawable.home_title_android,
            R.drawable.home_title_meizi,
            R.drawable.home_title_ios,
            R.drawable.home_title_movie,
            R.drawable.home_title_source,
            R.drawable.home_title_qian,
            R.drawable.home_title_app,
            R.drawable.home_title_xia
    };

    private static final String[] KEYS = new String[]{
            "Android", "Welfare", "iOS", "Video", "Expand", "Front", "App", "Recommend"
    };
}
