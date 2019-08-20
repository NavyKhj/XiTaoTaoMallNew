package com.shangcheng.home_module.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：选择规格后的返回结果
 * 创建人：niuyunwang
 * 创建时间：2019/5/17 17:01
 * 修改人：niuyunwang
 * 修改时间：2019/5/17 17:01
 * 修改备注：暂无
 */
public class SelectRuleResultBean implements Parcelable {
    private List<String> ids;
    private List<String> names;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.ids);
        dest.writeStringList(this.names);
        dest.writeInt(this.num);
    }

    public SelectRuleResultBean() {
    }

    protected SelectRuleResultBean(Parcel in) {
        this.ids = in.createStringArrayList();
        this.names = in.createStringArrayList();
        this.num = in.readInt();
    }

    public static final Parcelable.Creator<SelectRuleResultBean> CREATOR = new Parcelable.Creator<SelectRuleResultBean>() {
        @Override
        public SelectRuleResultBean createFromParcel(Parcel source) {
            return new SelectRuleResultBean(source);
        }

        @Override
        public SelectRuleResultBean[] newArray(int size) {
            return new SelectRuleResultBean[size];
        }
    };
}
