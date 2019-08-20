package com.shangcheng.common_module.utils;

import android.text.TextUtils;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：
 * 创建人：niuyunwang
 * 创建时间：2019/5/16 13:42
 * 修改人：niuyunwang
 * 修改时间：2019/5/16 13:42
 * 修改备注：暂无
 */
public class GoodsUtils {
    public static final String GENERALAREA="generalArea";
    public static final String TAOTAOAREA="taotaoArea";
    public static final String TAOPIAREA="taopiArea";
    public static final String POINTSAREA="pointsArea";
    public static final String SHETAOAREA="shetaoArea";



    /**
     * 是否是普淘类型
     * @param type
     * @return
     */
    public static boolean isTypePT(String type){
        return TextUtils.equals(GENERALAREA,type);
    }
    /**
     * 是否是积分类型
     * @param type
     * @return
     */
    public static boolean isTypeSoure(String type){
        return TextUtils.equals(POINTSAREA,type);
    }

    /**
     * 是否是淘淘类型
     * @param type
     * @return
     */
    public static boolean isTypeTT(String type){
        return TextUtils.equals(TAOTAOAREA,type);
    }

    /**
     * 是否是淘批类型
     * @param type
     * @return
     */
    public static boolean isTypeTP(String type){
        return TextUtils.equals(TAOPIAREA,type);
    }

    /**
     * 是否是奢淘类型
     * @param type
     * @return
     */
    public static boolean isTypeST(String type){
        return TextUtils.equals(SHETAOAREA,type);
    }
}
