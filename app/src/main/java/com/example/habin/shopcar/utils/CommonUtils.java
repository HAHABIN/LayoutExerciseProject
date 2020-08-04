/**
 * 文 件 名:  CommonUtils
 * 版    权:  QuanTeng Tech. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  HABIN
 * 修改时间:  2020/7/28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.example.habin.shopcar.utils;

import android.content.Context;
import android.util.TypedValue;

import com.example.habin.shopcar.MyApplication;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author HABIN
 * @version 2020/7/28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonUtils {


    private static Context getContext()
    {
        return MyApplication.getContext();
    }

    public static int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp
                ,getContext().getResources().getDisplayMetrics());
    }
}
