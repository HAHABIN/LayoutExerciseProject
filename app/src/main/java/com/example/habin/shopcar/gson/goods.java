package com.example.habin.shopcar.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Create by HABIN on 2019/11/1812:12
 * Email:739115041@qq.com
 */
public class goods {

    //忽略序列化 是否选中
    @Expose(serialize = false)
    private boolean isCheck;

    @SerializedName("number")
    private int number;
    @SerializedName("image")
    private String image;
    @SerializedName("money")
    private float money;
    @SerializedName("price")
    private float price;
    @SerializedName("goodsId")
    private int goodsId;
    @SerializedName("shoppingCarId")
    private int shoppingCarId;
    @SerializedName("inventory")
    private int inventory;
    @SerializedName("goodsName")
    private String goodsName;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getShoppingCarId() {
        return shoppingCarId;
    }

    public void setShoppingCarId(int shoppingCarId) {
        this.shoppingCarId = shoppingCarId;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return
                "number=" + number +
                ", image='" + image + '\'' +
                ", money=" + money +
                ", price=" + price +
                ", goodsId=" + goodsId +
                ", shoppingCarId=" + shoppingCarId +
                ", inventory=" + inventory +
                ", goodsName='" + goodsName + '\''
                ;
    }
}
