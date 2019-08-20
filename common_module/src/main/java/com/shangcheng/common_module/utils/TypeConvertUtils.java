package com.shangcheng.common_module.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 项目名称：CompanyHelper
 * 类描述：类型转换
 * 创建人：niuyunwang
 * 创建时间：2017-9-25 16:11
 * 修改人：niuyunwang
 * 修改时间：2017-9-25 16:11
 * 修改备注：暂无
 */

public class TypeConvertUtils {
    private static TypeConvertUtils typeConvertUtils;

    private TypeConvertUtils() {

    }

    public static TypeConvertUtils getInstance() {
        if (typeConvertUtils == null) {
            synchronized (TypeConvertUtils.class) {
                if (typeConvertUtils == null) {
                    typeConvertUtils = new TypeConvertUtils();
                }
            }
        }
        return typeConvertUtils;
    }

    public int parseInt(String data) {
        int returnData = 0;
        if (!TextUtils.isEmpty(data)) {
            try {
                returnData = Integer.parseInt(data);
            } catch (Exception e) {

            }
        }
        return returnData;
    }

    public float parseFloat(String data) {
        float returnData = 0;
        if (!TextUtils.isEmpty(data)) {
            try {
                returnData = Float.parseFloat(data);
            } catch (Exception e) {

            }
        }
        return returnData;
    }

    public double parseDouble(String data) {
        double returnData = 0;
        if (!TextUtils.isEmpty(data)) {
            try {
                returnData = Double.parseDouble(data);
            } catch (Exception e) {

            }
        }
        return returnData;
    }

    public long parseLong(String data) {
        long returnData = 0;
        if (!TextUtils.isEmpty(data)) {
            try {
                returnData = Long.parseLong(data);
            } catch (Exception e) {

            }
        }
        return returnData;
    }

    public int subtract(String v1, String v2){
        return (int) Math.abs(parseFloat(v1)-parseFloat(v2));
    }
    // 进行加法运算

    public double add(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    // 进行减法运算

    public  double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }


    // 进行乘法运算
    public  double multiply(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

    // 进行除法运算

    public static double div(double d1, double d2, int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 进行四舍五入操作

    public double round(double d, int len) {
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public String digString(double num){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(num);
    }

}
