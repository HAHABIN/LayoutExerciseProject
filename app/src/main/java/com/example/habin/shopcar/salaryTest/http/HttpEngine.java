package com.example.habin.shopcar.salaryTest.http;

import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//创建实现接口来方便调用
public class HttpEngine {

    private static ApiService apiService = RetrofitServiceManager.getInstance().create(ApiService.class);


    public static void getrecordDetails(String recordId,String teacherNo,Observer<recordDetailsEntity> observer){
        setSubscribe(apiService.getrecordDetails(recordId,teacherNo),observer);
    }

    private static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

}
