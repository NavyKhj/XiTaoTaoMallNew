package com.shangcheng.home_module.entity;

import com.shangcheng.common_module.common.model.BaseEntity;

import java.util.List;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：商品详情
 * 创建人：niuyunwang
 * 创建时间：2019/5/17 10:52
 * 修改人：niuyunwang
 * 修改时间：2019/5/17 10:52
 * 修改备注：暂无
 */
public class ProductDetailBean extends BaseEntity {

    /**
     * id : 569ca06cacd24123454959d031c51486
     * name : 意大利奶粉
     * areaCode : taotaoArea
     * originalPrice : 2365
     * price : 135.26
     * sold : 1230
     * taoticket : 45
     * points : 0
     * discount : 6
     * limitCount : null
     * stock : 125
     * firstPicture : https://file.xitaotao.cn/group1/M00/00/00/wKgWSfM418.PNG
     * differOfflineTime : 125632
     * carousel : ["https://file.xitaotao.cn/group1/M00/00/00/wKg418.PNG","https://file.xitaotao.cn/group1/M00/00/00/wKg18.PNG","https://file.xitaotao.cn/group1/M00/00/00/wKgUyCA418.PNG"]
     * details : ["https://file.xitaotao.cn/group1/M00/00/00/wKgU6WfM418.PNG","https://file.xitaotao.cn/group1/M00/00/00/wKgUM418.PNG","https://file.xitaotao.cn/group1/M00/00/00/wKg18.PNG"]
     */

    private String id;
    private String name;
    private String areaCode;
    private String originalPrice;
    private String price;
    private int sold;
    private String taoticket;
    private int points;
    private String discount;
    private int limitCount;
    private int stock;
    private String firstPicture;
    private long differOfflineTime;
    private List<String> carousel;
    private List<String> details;

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getTaoticket() {
        return taoticket;
    }

    public void setTaoticket(String taoticket) {
        this.taoticket = taoticket;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public long getDifferOfflineTime() {
        return differOfflineTime;
    }

    public void setDifferOfflineTime(long differOfflineTime) {
        this.differOfflineTime = differOfflineTime;
    }

    public List<String> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<String> carousel) {
        this.carousel = carousel;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
