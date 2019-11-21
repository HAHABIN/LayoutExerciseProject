package com.example.habin.shopcar.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Create by HABIN on 2019/11/1811:06
 * Email:739115041@qq.com
 */
public class HttpUtils {
    private static final String address = "https://hsej.app360.cn/app/shoppingCar/listShoppingCar.do";

    public static void ShopCarWithOkHttp(okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("XPS-UserId","919202")
                .addHeader("XPS-Token","ZULw8uTHCNXJp3iwIgltygMPphBvWsr%2Fy7IGAHLJeQjvQQMZkj786s8knQNqp1TIVr0ibHtF5qic%0AkGTSwhwJFnoQ7AoXiqf7bMSQdMqk1fU0IfhCb%2FyMNg%3D%3D")
                .url(address)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }
}
