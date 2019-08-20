package com.shangcheng.common_module.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 标签
 *
 * @author jingchao
 * @time 2018/4/24
 */
public class TagEntity implements Parcelable {
    @SerializedName("specsItemId")
    private String id;
    @SerializedName("specsItemName")
    private String name;

    public TagEntity(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public TagEntity() {
    }

    protected TagEntity(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<TagEntity> CREATOR = new Creator<TagEntity>() {
        @Override
        public TagEntity createFromParcel(Parcel source) {
            return new TagEntity(source);
        }

        @Override
        public TagEntity[] newArray(int size) {
            return new TagEntity[size];
        }
    };
}
