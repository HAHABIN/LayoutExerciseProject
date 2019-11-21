package com.example.habin.shopcar.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create by HABIN on 2019/11/1812:09
 * Email:739115041@qq.com
 * 返回json数据
 *
 */
public class items {

    @SerializedName("isFirst")
    private String  isFirst;
    @SerializedName("address")
    private String address;
    @SerializedName("sendingFee")
    private String  sendingFee;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("deliver")
    private String  deliver;
    @SerializedName("discount")
    private String discount;

    @SerializedName("goods")
    private List<goods> goodsList;

    @SerializedName("fullReduction")
    private String fullReduction;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("deliverFee")
    private String  deliverFee;

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSendingFee() {
        return sendingFee;
    }

    public void setSendingFee(String sendingFee) {
        this.sendingFee = sendingFee;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<goods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getFullReduction() {
        return fullReduction;
    }

    public void setFullReduction(String fullReduction) {
        this.fullReduction = fullReduction;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getDeliverFee() {
        return deliverFee;
    }

    public void setDeliverFee(String deliverFee) {
        this.deliverFee = deliverFee;
    }

    @Override
    public String toString() {
        return "items{" +
                "isFirst=" + isFirst +
                ", address='" + address + '\'' +
                ", sendingFee='" + sendingFee + '\'' +
                ", shopName='" + shopName + '\'' +
                ", deliver='" + deliver + '\'' +
                ", discount=" + discount +
                ", goods=" + goodsList +
                '}';
    }
}
