package com.shangcheng.home_module.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：图片轮播bean
 * 创建人：niuyunwang
 * 创建时间：2019/3/28 9:30
 * 修改人：niuyunwang
 * 修改时间：2019/3/28 9:30
 * 修改备注：暂无
 */
public class BannerBean {
    /**标记ID*/
    private String id;
    /**外链*/
    private String web_link;
    /**图片url*/
    @SerializedName("adPicture")
    private String img_url;
    /**图片名字*/
    @SerializedName("adTitle")
    private String img_name;
    /**分类*/
    private String type;
    /**商品id*/
    private String goodsId;
    private int defaultPic;

    public int getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(int defaultPic) {
        this.defaultPic = defaultPic;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
