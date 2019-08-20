package com.shangcheng.common_module.utils;

/**
 * Created by niuyunwang on 2018-4-2.
 * 大小尺寸常量
 * 以下尺寸px均以屏幕宽度是750为基准
 */

public class DimensConstant {
    //字体大小 px
    public static final class TextSize{
        //导航栏字体大小42
        public static final int NAVIGATION_TEXT_SIZE = 48;
        //需要突出的字体大小38
        public static final int OUTSTANDING_TEXT_SIZE = 44;
        //列表标题、按钮、表头字体大小34
        public static final int TITLE_TEXT_SIZE = 40;
        //正文字体大小30
        public static final int BODY_TEXT_SIZE = 36;
        //提示信息字体大小26
        public static final int TIP_TEXT_SIZE = 32;
        //备注，底部菜单栏字体大小22
        public static final int REMARK_TEXT_SIZE = 28;

    }
    // 宽高尺寸比列
    public static final class WidthAndHeightScale{
        //导航栏高度 88px
        public static final float NAVIGATION_HEIGHT_SCALE = 0.1173f;
        //菜单栏高度 98px
        public static final float MUNE_HEIGHT_SCALE = 0.1307f;
        //文字列表高度 100px
        public static final float LISTVIEW_WORD_HEIGHT_SCALE = 0.1333f;
        //文图列表高度 140px
        public static final float LISTVIEW_WORD_PIC_HEIGHT_SCALE = 0.1867f;
        //大按钮高度 90px
        public static final float BUTTON_HEIGHT_SCALE = 0.12f;
        //小按钮高度 60px
        public static final float SMALL_BUTTON_HEIGHT_SCALE = 0.08f;
        //弹框按钮高度 120px
        public static final float DIALOG_BUTTON_HEIGHT_SCALE = 0.16f;
        //头像大小48px
        public static final float CIRCLE_HEAD_L_SCALE = 0.064f;
        //头像大小64px
        public static final float CIRCLE_HEAD_M_SCALE = 0.0853f;
        //头像大小80px
        public static final float CIRCLE_HEAD_H_SCALE = 0.1067f;
        //头像大小96px
        public static final float CIRCLE_HEAD_XH_SCALE = 0.128f;
        //头像大小120px
        public static final float CIRCLE_HEAD_XXH_SCALE = 0.16f;
        //头像大小160px
        public static final float CIRCLE_HEAD_XXXH_SCALE = 0.2133f;
        //首页banner图宽750px
        public static final float HOME_BANNER_PIC_WIDTH_SCALE = 1f;
        //首页banner图高375px
        public static final float HOME_BANNER_PIC_HEIGHT_SCALE = 0.5f;
        //学习banner图宽682
        public static final float STUDY_BANNER_PIC_WIDTH_SCALE = 0.9093f;
        //学习banner图高384px
        public static final float STUDY_BANNER_PIC_HEIGHT_SCALE = 0.512f;
        //首页列表中图宽180px
        public static final float HOME_LISTVIEW_PIC_WIDTH_SCALE = 0.24f;
        //首页banner图高120px
        public static final float HOME_LISTVIEW_PIC_HEIGHT_SCALE = 0.16f;
        //学习列表中图宽340px
        public static final float STUDY_LISTVIEW_PIC_WIDTH_SCALE = 0.4533f;
        //学习banner图高192px
        public static final float STUDY_LISTVIEW_PIC_HEIGHT_SCALE = 0.256f;
        //搜索评论等一行输入框高度60px
        public static final float EDITTEXT_SINGLE_LINE_HEIGHT_SCALE = 0.08f;
        //下拉框item高度88px
        public static final float POPU_ITEM_HEIGHT_SCALE = 0.08f;


    }
    // 间距尺寸
    public static final class MarginAndPaddingScale{
        //内容块最小间距 16px
        public static final float CONTENT_MARGIN_SCALE = 0.0213f;
        //内容距左右距离 24px
        public static final float HORIZONTAL_MARGIN_SCALE = 0.032f;
        //标题和正文间距 48px
        public static final float TITLE_BODY_MARGIN_SCALE = 0.064f;
        //导航栏左右间距 60px
        public static final float NAVIGATION_TWO_MARGIN_SCALE = 0.08f;
        // 弹框内容文字上下间距 40px
        public static final float DIALOG_CONTENT_VERTICAL_MARGIN_SCALE = 0.0533f;
        // 弹框内容文字左右间距 32px
        public static final float DIALOG_CONTENT_HORIZONTAL_MARGIN_SCALE = 0.0427f;
        // 弹框标题与内容文字间距 30px
        public static final float DIALOG_TITLE_CONTENT_MARGIN_SCALE = 0.04f;
        // 弹框按钮上下间距 10px
        public static final float DIALOG_BUTTON_MARGIN_SCALE = 0.0133f;

    }

    public static final class DimensScale{
        //尺寸8
        public static final float DIMENS_8 = 0.01067f;
        //尺寸10
        public static final float DIMENS_10 = 0.0133f;
        //尺寸16
        public static final float DIMENS_16 = 0.0213f;
        //尺寸24
        public static final float DIMENS_24 = 0.032f;
        //尺寸28
        public static final float DIMENS_28 = 0.0373f;
        //尺寸32
        public static final float DIMENS_32 = 0.0427f;
        //尺寸40
        public static final float DIMENS_40 = 0.0533f;
        //尺寸44
        public static final float DIMENS_44 = 0.0587f;
        //尺寸48
        public static final float DIMENS_48 = 0.064f;
        //尺寸56
        public static final float DIMENS_56 = 0.0747f;
        //尺寸60
        public static final float DIMENS_60 = 0.08f;
        //尺寸64
        public static final float DIMENS_64 = 0.0853f;
        //尺寸72
        public static final float DIMENS_72 = 0.096f;
        //尺寸80
        public static final float DIMENS_80 = 0.1067f;
        //尺寸88
        public static final float DIMENS_88 = 0.1173f;
        //尺寸90
        public static final float DIMENS_90 = 0.12f;
        //尺寸96
        public static final float DIMENS_96 = 0.128f;
        //尺寸98
        public static final float DIMENS_98 = 0.1307f;
        //尺寸100
        public static final float DIMENS_100 = 0.1333f;
        //尺寸120
        public static final float DIMENS_120 = 0.16f;
        //尺寸140
        public static final float DIMENS_140 = 0.1867f;
        //尺寸160
        public static final float DIMENS_160 = 0.2133f;
        //尺寸180
        public static final float DIMENS_180 = 0.24f;
        //尺寸192
        public static final float DIMENS_192 = 0.256f;
        //尺寸340
        public static final float DIMENS_340 = 0.4533f;
        //尺寸375
        public static final float DIMENS_375 = 0.5f;
        //尺寸384
        public static final float DIMENS_384 = 0.512f;
        //尺寸750
        public static final float DIMENS_750 = 1f;

    }

    public static final class TextScale{
        //导航栏字体大小42
        public static final int TEXT_42 = 50;
        //需要突出的字体大小38
        public static final int TEXT_38 = 46;
        //列表标题、按钮、表头字体大小34
        public static final int TEXT_34 = 42;
        //正文字体大小30
        public static final int TEXT_30 = 38;
        //提示信息字体大小26
        public static final int TEXT_26 = 34;
        //提示信息字体大小24
        public static final int TEXT_24 = 32;
        //备注，底部菜单栏字体大小22
        public static final int TEXT_22 = 30;
    }



}
