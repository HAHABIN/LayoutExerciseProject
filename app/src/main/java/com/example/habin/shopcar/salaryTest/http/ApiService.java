package com.example.habin.shopcar.salaryTest.http;

import com.example.habin.shopcar.cutleryRecycling.bean.RecycleOrderListEntity;
import com.example.habin.shopcar.salaryTest.Entity.recordDetailsEntity;

import io.reactivex.Observable;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("XPS-UserToken:2903c93fe3537a2e298179f1b144fd20")
    @POST("teacher/user/salary/record/info/recordDetails")
    Observable<recordDetailsEntity> getrecordDetails(@Query("recordId") String recordId,
                                                     @Query("teacherNo") String teacherNo0);

}
