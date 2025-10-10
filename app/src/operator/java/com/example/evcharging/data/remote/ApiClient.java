package com.example.evcharging.data.remote;

import com.example.evcharging.auth.BuildConfig;
import com.example.evcharging.utils.SpUtil;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://ev-charging-backend-it22055026.azurewebsites.net/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);

            // Add authorization interceptor
            httpClient.addInterceptor(chain -> {
                String token = SpUtil.getAccessToken();
                Request.Builder requestBuilder = chain.request().newBuilder();

                if (token != null && !token.isEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer " + token);
                }

                return chain.proceed(requestBuilder.build());
            });

            if (BuildConfig.DEBUG) {
                // Debug-only self-signed HTTPS support (use only for local dev)
                try {
                    TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                                @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                                @Override public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                            }
                    };
                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new SecureRandom());
                    SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                    httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                    httpClient.hostnameVerifier((hostname, session) ->
                            "10.0.2.2".equals(hostname) ||
                            "localhost".equals(hostname) ||
                            hostname.endsWith(".devtunnels.ms") ||
                            "ev-charging-backend-it22055026.azurewebsites.net".equals(hostname));
                } catch (Exception ignored) {
                }
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}