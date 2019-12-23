package com.example.habin.shopcar.cutleryRecycling.service;

import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//创建实现接口来方便调用
public class HttpEngine {

    private static ApiService apiService = RetrofitServiceManager.getInstance().create(ApiService.class);

    /*
     * 获取豆瓣电影榜单
     * **/
//    public static void getDuoBanTop(int start, int count, Observer<movieTopReq> observer) {
//        setSubscribe(movieService.getMovicTop(start, count), observer);
//    }

//    @Query("pageNo") String pageNo,
//    @Query("pageSize") String pageSize,
//    @Query("key") String key,
//    @Query("building") String building,
//    @Query("ordersType") String ordersType
//

    public static void getDataList(int pageNo, int pageSize,
                                   String status, String key,
                                   String building, String ordersType ,
                                   Observer<RecycleOrderListEntity> observer){
        setSubscribe(apiService.getDataList(pageNo,pageSize,status,key,building,ordersType),
                 observer);
    }

    public static void recConfirm(long id,Observer<RecycleOrderListEntity> observer){
        setSubscribe(apiService.recConfirm(id),observer);
    }

    public static void getBuildingList(Observer<Object> observer){
        setSubscribe(apiService.getBuildingList(),observer);
    }

    private static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

}
