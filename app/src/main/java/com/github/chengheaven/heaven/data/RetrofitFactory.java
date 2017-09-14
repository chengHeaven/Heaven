package com.github.chengheaven.heaven.data;

import android.annotation.SuppressLint;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Heaven・Cheng Created on 17/4/18.
 */

public class RetrofitFactory {

    private final static String API_GANKIO = "https://gank.io/api/";
    private final static String API_DOUBAN = "Https://api.douban.com/";
    private final static String API_TING = "https://tingapi.ting.baidu.com/v1/restserver/";

    private static ApiService sGankInstance;
    private static ApiService sDouBanInstance;
    private static ApiService sTingInstance;
    private static RetrofitFactory instance;
    private static OkHttpClient sOkHttpClient = getOkHttpClient();

    public static ApiService getGankInstance() {
        if (sGankInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sGankInstance == null) {
                    sGankInstance = getBuilder(API_GANKIO).build().create(ApiService.class);
                }
            }
        }
        return sGankInstance;
    }

    private static Retrofit.Builder getBuilder(String api) {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public static ApiService getDouBanInstance() {
        if (sDouBanInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sDouBanInstance == null) {
                    sDouBanInstance = getBuilder(API_DOUBAN).build().create(ApiService.class);
                }
            }
        }
        return sDouBanInstance;
    }

    public static ApiService getTingInstance() {
        if (sTingInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sTingInstance == null) {
                    sTingInstance = getBuilder(API_TING).build().create(ApiService.class);
                }
            }
        }
        return sTingInstance;
    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .sslSocketFactory(getSSLSocketFactory())
                .hostnameVerifier(getHostnameVerifier())
                .build();
    }

    private static SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    @SuppressLint("TrustAllX509TrustManager")
    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};
    }

    private static HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }
}
