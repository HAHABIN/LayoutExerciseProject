package com.example.habin.shopcar.shopCar.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create by HABIN on 2019/11/1914:39
 * Email:739115041@qq.com
 */
public class ShopCar {

    @SerializedName("result")
    private String result;
    @SerializedName("payType")
    private String payType;
    @SerializedName("items")
    private List<items> itemsList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public List<items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<items> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public String toString() {
        return "ShopCar{" +
                "result='" + result + '\'' +
                ", payType='" + payType + '\'' +
                ", itemsList=" + itemsList +
                '}';
    }
}
