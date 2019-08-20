package com.shangcheng.home_module.entity;

import com.google.gson.annotations.SerializedName;
import com.shangcheng.common_module.common.model.BaseEntity;
import com.shangcheng.common_module.common.model.TagEntity;

import java.util.List;

/**
 * @author Navy
 */
public class SelectConditionBeanEntity extends BaseEntity {
    private List<SelectConditionBean> data;

    public List<SelectConditionBean> getData() {
        return data;
    }

    public void setData(List<SelectConditionBean> data) {
        this.data = data;
    }

    /**
     * 项目名称：PartyBuildingCloud4
     * 类描述：筛选bean
     * 创建人：niuyunwang
     * 创建时间：2018/7/25 18:57
     * 修改人：niuyunwang
     * 修改时间：2018/7/25 18:57
     * 修改备注：暂无
     */

    public class SelectConditionBean {
        /**
         * title : 排序
         * params : order
         * child : [{"id":"1","name":"最新发布"},{"id":"2","name":"最早发布"}]
         */
        @SerializedName("specsName")
        private String title;
        @SerializedName("specsId")
        private String id;
        @SerializedName("items")
        private List<TagEntity> child;

        public List<TagEntity> getChild() {
            return child;
        }

        public void setChild(List<TagEntity> child) {
            this.child = child;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }




        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
