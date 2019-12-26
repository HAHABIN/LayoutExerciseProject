package com.example.habin.shopcar.cutleryRecycling.http;

import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;

import io.reactivex.Observable;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("XPS-UserId:691357")
    @POST("recyclingOrders/list")
    Observable<RecycleOrderListEntity> getDataList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("status") String status,
            @Query("key") String key,
            @Query("building") String building,
            @Query("ordersType") String ordersType
            );

    @Headers("XPS-UserId:691357")
    @POST("recyclingOrders/confirm")
    Observable<RecycleOrderListEntity> recConfirm(@Query("id") long id);

    @POST("userAddress/getBuildingList")
    Observable<Object> getBuildingList();


}
