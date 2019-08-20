package com.shangcheng.xitaotao.entity;

import com.shangcheng.common_module.common.model.BaseEntity;

import java.util.List;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：
 * 创建人：niuyunwang
 * 创建时间：2019/5/16 8:57
 * 修改人：niuyunwang
 * 修改时间：2019/5/16 8:57
 * 修改备注：暂无
 */
public class HomeListProductBean extends BaseEntity {
    /**普淘商品*/
    private List<HomeProductBean> generalArea;
    /**淘淘商品*/
    private List<HomeProductBean> taotaoArea;

    public List<HomeProductBean> getGeneralArea() {
        return generalArea;
    }

    public void setGeneralArea(List<HomeProductBean> generalArea) {
        this.generalArea = generalArea;
    }

    public List<HomeProductBean> getTaotaoArea() {
        return taotaoArea;
    }

    public void setTaotaoArea(List<HomeProductBean> taotaoArea) {
        this.taotaoArea = taotaoArea;
    }

    public List<HomeProductBean> getTaopiArea() {
        return taopiArea;
    }

    public void setTaopiArea(List<HomeProductBean> taopiArea) {
        this.taopiArea = taopiArea;
    }

    public List<HomeProductBean> getPointsArea() {
        return pointsArea;
    }

    public void setPointsArea(List<HomeProductBean> pointsArea) {
        this.pointsArea = pointsArea;
    }

    /**淘批商品*/
    private List<HomeProductBean> taopiArea;
    /**积分*/
    private List<HomeProductBean> pointsArea;


}
