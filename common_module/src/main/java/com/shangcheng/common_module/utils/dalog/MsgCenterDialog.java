package com.shangcheng.common_module.utils.dalog;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.baseApplication.BaseApplication;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.DimensConstant;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;

/**
 * 项目名称：PartyBuildingCloud4
 * 类描述：信息提示dialog
 * 创建人：niuyunwang
 * 创建时间：2018-4-2 14:10
 * 修改人：niuyunwang
 * 修改时间：2018-4-2 14:10
 * 修改备注：暂无
 */

public class MsgCenterDialog extends Dialog {
    private View mActionSheetView;
    private Context mContext;
    //内容
    private TextView tv_content;
    //说明
    private TextView tv_mark;
    //底部按钮布局
    private LinearLayout ll_bottom;
    //取消
    private TextView tv_cancel;
    //确定（红色）
    private TextView tv_ok;
    //其他
    private TextView tv_right;
    //分割线
    private View line;
    private View btn_line_left, btn_line_right;
    private ImageView iv_top;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public MsgCenterDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        mContext = context;
        initView();
    }

    /**
     * toast形式弹出框
     *
     * @param content 内容
     * @param time    弹出时间毫秒
     */
    public void setToastForMsg(String content, long time) {
        tv_content.setText(content);
        CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                dismiss();
            }
        };
        countDownTimer.start();
    }

    /**
     * 设置消息内容
     *
     * @param content  内容
     * @param color    颜色
     * @param textSize 字体大小
     */
    public void setContent(String content, int color, int textSize) {
        tv_content.setText(content);
        tv_content.setTextColor(ColorUtil.getAttrColor(color, mContext));
        ScreenMeasureUtil.getInstance().setMTextSize(tv_content, textSize);
    }

    /**
     * 设置消息内容
     *
     * @param content  内容
     * @param textSize 字体大小
     */
    public void setContent(String content, int textSize, float marginTop) {
        tv_content.setText(content);
        ScreenMeasureUtil.getInstance().setMTextSize(tv_content, textSize);
        ScreenMeasureUtil.getInstance().setMViewPadding(tv_content, DimensConstant.DimensScale.DIMENS_16,
                marginTop,
                DimensConstant.DimensScale.DIMENS_16,
                marginTop);
    }

    /**
     * 设置消息内容
     *
     * @param content 内容
     * @param color   颜色
     */
    public void setContent(String content, int color) {
        tv_content.setText(content);
        tv_content.setTextColor(ColorUtil.getAttrColor(color, mContext));
    }

    /**
     * 设置消息内容(使用默认颜色#333333)
     *
     * @param content 内容
     */
    public void setContent(String content) {
        tv_content.setText(content);
    }

    /**
     * 设置消息内容(使用默认颜色#333333)
     *
     * @param content 内容
     */
    public void setContent(SpannableString content) {
        tv_content.setText(content);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 设置第二行说明
     *
     * @param mark  说明
     * @param color 颜色
     */
    public void setMark(String mark, int color) {
        tv_mark.setVisibility(View.VISIBLE);
        tv_mark.setText(mark);
        tv_mark.setTextColor(ColorUtil.getAttrColor(color, mContext));
    }

    /**
     * 设置第二行说明
     *
     * @param mark     说明
     * @param color    颜色
     * @param textSize 字体大小
     */
    public void setMark(String mark, int color, int textSize) {
        tv_mark.setVisibility(View.VISIBLE);
        tv_mark.setText(mark);
        tv_mark.setTextColor(ColorUtil.getAttrColor(color, mContext));
        ScreenMeasureUtil.getInstance().setMTextSize(tv_mark, textSize);
    }

    /**
     * 设置第二行说明（使用默认颜色#666666）
     *
     * @param mark 说明
     */
    public void setMark(String mark) {
        tv_mark.setVisibility(View.VISIBLE);
        tv_mark.setText(mark);
    }

    /**
     * 设置第二行说明（使用默认颜色#666666）
     *
     * @param mark 说明
     */
    public void setMark(SpannableString mark) {
        tv_mark.setVisibility(View.VISIBLE);
        tv_mark.setText(mark);
    }

    /**
     * 设置第一个按钮普通色
     *
     * @param text 按钮名称
     * @param l    点击事件
     */
    public void setFirstButton(String text, View.OnClickListener l) {
        btn_line_left.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_cancel.setVisibility(View.VISIBLE);
        tv_cancel.setText(text);
        tv_cancel.setOnClickListener(l);
    }

    /**
     * 设置第一个按钮普通色
     *
     * @param text     按钮名称
     * @param l        点击事件
     * @param color    颜色
     * @param textSize 字体大小
     */
    public void setFirstButton(String text, View.OnClickListener l, int color, int textSize) {
        btn_line_left.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_cancel.setVisibility(View.VISIBLE);
        tv_cancel.setText(text);
        tv_cancel.setTextColor(ColorUtil.getAttrColor(color, mContext));
        ScreenMeasureUtil.getInstance().setMTextSize(tv_cancel, textSize);
        tv_cancel.setOnClickListener(l);
    }

    /**
     * 设置第二个按钮红色
     *
     * @param text 按钮名称
     * @param l    点击事件
     */
    public void setSecondButton(String text, View.OnClickListener l) {
        line.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_ok.setVisibility(View.VISIBLE);
        tv_ok.setText(text);
        tv_ok.setOnClickListener(l);
    }

    /**
     * 设置第二个按钮红色
     *
     * @param text     按钮名称
     * @param l        点击事件
     * @param color    颜色
     * @param textSize 字体大小
     */
    public void setSecondButton(String text, View.OnClickListener l, int color, int textSize) {
        line.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_ok.setVisibility(View.VISIBLE);
        tv_ok.setText(text);
        tv_ok.setTextColor(ColorUtil.getAttrColor(color, mContext));
        ScreenMeasureUtil.getInstance().setMTextSize(tv_ok, textSize);
        tv_ok.setOnClickListener(l);
    }

    /**
     * 设置第三个按钮红色
     *
     * @param text 按钮名称
     * @param l    点击事件
     */
    public void setThirdButton(String text, View.OnClickListener l) {
        btn_line_right.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(text);
        tv_right.setOnClickListener(l);
    }

    /**
     * 设置第三个按钮红色
     *
     * @param text     按钮名称
     * @param l        点击事件
     * @param color    颜色
     * @param textSize 字体大小
     */
    public void setThirdButton(String text, View.OnClickListener l, int color, int textSize) {
        btn_line_right.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(text);
        tv_right.setTextColor(ColorUtil.getAttrColor(color, mContext));
        ScreenMeasureUtil.getInstance().setMTextSize(tv_right, textSize);
        tv_right.setOnClickListener(l);
    }

    /**
     * 设置头部的图片
     *
     * @param imgId     图片id，R.mipmap.XX
     * @param imgWidth  图片宽
     * @param imgHeight 图片高
     */
    public void setTopImg(int imgId, float imgWidth, float imgHeight) {
        iv_top.setImageResource(imgId);
        iv_top.setVisibility(View.VISIBLE);
        ScreenMeasureUtil.getInstance().setMLayoutParam(iv_top, imgWidth, imgHeight);
        ScreenMeasureUtil.getInstance().setMViewMargin(iv_top, 0, DimensConstant.DimensScale.DIMENS_32, 0, 0);
        ScreenMeasureUtil.getInstance().setMViewPadding(tv_content, DimensConstant.DimensScale.DIMENS_16,
                DimensConstant.DimensScale.DIMENS_24,
                DimensConstant.DimensScale.DIMENS_16,
                DimensConstant.DimensScale.DIMENS_24);

    }


    //初始化控件
    private void initView() {
        mActionSheetView = LayoutInflater.from(mContext).inflate(R.layout.basecommon_dialog_msg_center, null);
        // 顶部图片
        iv_top = (ImageView) mActionSheetView.findViewById(R.id.iv_top);

        //内容
        tv_content = (TextView) mActionSheetView.findViewById(R.id.tv_content);
        ScreenMeasureUtil.getInstance().setMTextSize(tv_content, 18);
        ScreenMeasureUtil.getInstance().setMViewPadding(tv_content, DimensConstant.DimensScale.DIMENS_16,
                DimensConstant.DimensScale.DIMENS_40,
                DimensConstant.DimensScale.DIMENS_16,
                DimensConstant.DimensScale.DIMENS_40);
        //说明
        tv_mark = (TextView) mActionSheetView.findViewById(R.id.tv_mark);
        tv_mark.setMovementMethod(ScrollingMovementMethod.getInstance());
        ScreenMeasureUtil.getInstance().setMTextSize(tv_mark, 11);
        ScreenMeasureUtil.getInstance().setMViewPadding(tv_mark, DimensConstant.DimensScale.DIMENS_16,
                0,
                DimensConstant.DimensScale.DIMENS_16,
                DimensConstant.DimensScale.DIMENS_40);
        //分割线
        line = mActionSheetView.findViewById(R.id.line);
        btn_line_left = mActionSheetView.findViewById(R.id.btn_line_left);
        btn_line_right = mActionSheetView.findViewById(R.id.btn_line_right);

        //底部两个按钮布局
        ll_bottom = (LinearLayout) mActionSheetView.findViewById(R.id.ll_bottom);
        ScreenMeasureUtil.getInstance().setMLayoutParam(ll_bottom, 1f, DimensConstant.DimensScale.DIMENS_120);
        //取消
        tv_cancel = (TextView) mActionSheetView.findViewById(R.id.tv_cancel);
        ScreenMeasureUtil.getInstance().setMTextSize(tv_cancel, 16);
        //确定（红色）
        tv_ok = (TextView) mActionSheetView.findViewById(R.id.tv_ok);
        ScreenMeasureUtil.getInstance().setMTextSize(tv_ok, 16);
        //其他
        tv_right = (TextView) mActionSheetView.findViewById(R.id.tv_right);
        ScreenMeasureUtil.getInstance().setMTextSize(tv_right, 16);

        //加载UI
        setContentView(mActionSheetView);
        //设置底部配置
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenMeasureUtil.getInstance().getWidthPixels() * 4 / 5;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
    }

    public void setMaskMaxLines(int lines) {
        if (tv_mark != null)
            tv_mark.setMaxLines(lines);
    }
    /**
     * 设置第二个按钮隐藏
     */
    public void setSecondButtonGone() {
        tv_ok.setVisibility(View.GONE);
    }

}
