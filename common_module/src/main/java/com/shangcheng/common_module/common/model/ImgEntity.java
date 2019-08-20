package com.shangcheng.common_module.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片信息格式(统一返回/请求)
 *
 * @author yangzm
 * @time 2018/4/24
 */
public class ImgEntity implements Parcelable {

    private String hash;//图片hash
    private String url;//图片资源路径
    private String name;//图片名称
    private String width;//图片宽
    private String height;//图片高
    private String sets_url;//信息放大图使用


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSets_url() {
        return sets_url;
    }

    public void setSets_url(String sets_url) {
        this.sets_url = sets_url;
    }

    public ImgEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hash);
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeString(this.sets_url);
    }

    protected ImgEntity(Parcel in) {
        this.hash = in.readString();
        this.url = in.readString();
        this.name = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.sets_url = in.readString();
    }

    public static final Creator<ImgEntity> CREATOR = new Creator<ImgEntity>() {
        @Override
        public ImgEntity createFromParcel(Parcel source) {
            return new ImgEntity(source);
        }

        @Override
        public ImgEntity[] newArray(int size) {
            return new ImgEntity[size];
        }
    };
}
