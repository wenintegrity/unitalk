package com.chisw.switchmymind.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 19.01.18.
 */

public class NetworkManager {
    public static final String BASE_URL = "http://192.168.2.142:3005";
    private static final NetworkManager instance = new NetworkManager();

    private NetworkApi networkApi;

    private NetworkManager() {

    }

    public static NetworkManager getInstance() {
        return instance;
    }

    public NetworkApi getNetrowkAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(initClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetworkApi.class);
    }

    private OkHttpClient initClient() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor() {
            @Override
            public Response intercept(final Chain chain) throws IOException {
                final Request request = chain.request()
                        .newBuilder()
                        .addHeader("content-type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        }).addInterceptor(interceptor).build();
    }
}
