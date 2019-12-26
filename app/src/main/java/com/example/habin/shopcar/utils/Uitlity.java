package com.example.habin.shopcar.utils;


import com.example.habin.shopcar.shopCar.gson.ShopCar;
import com.google.gson.Gson;

/**
 * Create by HABIN on 2019/11/1813:06
 * Email:739115041@qq.com
 * 解析和处理服务器返回数据
 */
public class Uitlity {
    public static ShopCar handleShowResponse(String response){
        try {
            ShopCar shopCar =new Gson().fromJson(response,ShopCar.class);
            return shopCar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static items handleShowItem()
}
