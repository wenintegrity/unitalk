package com.unitalk.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.unitalk.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final int CONNECT_TIMEOUT = 20;
    private static NetworkManager instance;
    private final NetworkApi networkApi;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public NetworkApi getNetworkApi() {
        return networkApi;
    }

    private NetworkManager() {
        networkApi = createNetworkApi();
    }

    private NetworkApi createNetworkApi() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(initClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetworkApi.class);
    }

    private OkHttpClient initClient() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor() {
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
