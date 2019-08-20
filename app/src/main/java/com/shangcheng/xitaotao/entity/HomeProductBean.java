package com.shangcheng.xitaotao.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：首页商品bean
 * 创建人：niuyunwang
 * 创建时间：2019/5/16 8:54
 * 修改人：niuyunwang
 * 修改时间：2019/5/16 8:54
 * 修改备注：暂无
 */
public class HomeProductBean {

    /**
     * id : 569ca06cacd24123454959d031c51486
     * name : 钉子
     * timer : 1523654
     * price : 100
     * stock : 100
     * taoticket : 124
     * discount : 6
     * sold
     * picture : https://file.xitaotao.cn/group1/M00/00/00/wKgU6VzUMySfM418.PNG
     */

    /**id*/
    private String id;
    /**商品名称*/
    private String name;
    /**倒计时时间 毫秒*/
    private long timer;
    /**商品价格*/
    private String price;
    /**商品库存*/
    private int stock;
    /**淘券*/
    private String taoticket;
    /**折扣*/
    private String discount;
    /**图片*/
    @SerializedName("firstPicture")
    private String picture;
    /**已售*/
    private String sold;
    /**原价*/
    private String originalPrice;

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTaoticket() {
        return taoticket;
    }

    public void setTaoticket(String taoticket) {
        this.taoticket = taoticket;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
