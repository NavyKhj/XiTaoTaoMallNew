package com.shangcheng.home_module.entity;

import com.shangcheng.common_module.common.model.BaseEntity;

import java.util.List;

/**
 * @author Navy
 */
public class GoodsBeanEntity extends BaseEntity {
    private List<GoodsBean> data;

    public List<GoodsBean> getList() {
        return data;
    }

    public void setList(List<GoodsBean> list) {
        this.data = list;
    }

    /**
     * @author: niuyunwang
     * 项目名称：XiTaoTaoMall
     * 类描述：商品bean
     * 创建人：niuyunwang
     * 创建时间：2019/5/16 13:14
     * 修改人：niuyunwang
     * 修改时间：2019/5/16 13:14
     * 修改备注：暂无
     */
    public class GoodsBean {
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
         */

        /**
         * 商品id
         */
        private String id;
        /**
         * 商品名称
         */
        private String name;
        /**
         * 商品区域
         */
        private String areaCode;
        /**
         * 原价
         */
        private String originalPrice;
        /**
         * 现在价格
         */
        private String price;
        /**
         * 已经卖出件数
         */
        private int sold;
        /**
         * 淘券
         */
        private String taoticket;
        /**
         * 积分
         */
        private int points;
        /**
         * 折扣
         */
        private String discount;
        private String limitCount;
        /**
         * 库存
         */
        private int stock;
        /**
         * 图片
         */
        private String firstPicture;

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

        public String getLimitCount() {
            return limitCount;
        }

        public void setLimitCount(String limitCount) {
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
    }
}
