package com.github.chengheaven.heaven.customer;

import android.webkit.CookieManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class WebViewCookie implements CookieJar {

    private CookieManager mCookieManager = CookieManager.getInstance();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String urlString = url.toString();

        for (Cookie cookie : cookies) {
            mCookieManager.setCookie(urlString, cookie.toString());
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String urlString = url.toString();
        String cookieString = mCookieManager.getCookie(urlString);

        if (cookieString != null && !cookieString.isEmpty()) {
            String[] cookies = cookieString.split(";");
            List<Cookie> cookieList = new ArrayList<>();
            for (String cookie : cookies) {
                cookieList.add(Cookie.parse(url, cookie));
            }
            return cookieList;
        }

        return Collections.emptyList();
    }

    // 添加到 AppService 类中
//    public static OkHttpClient getClient() {
//        return new OkHttpClient.Builder().cookieJar(new WebViewCookie()).build();
//    }
}
