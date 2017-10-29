package com.newweather.app.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Mr.Qing on 2017/10/28.
 * 网络工具类
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String url,okhttp3.Callback callback){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);




    }

}
