package com.shangcheng.common_module.utils.dalog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.DimensConstant;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;

/**
 * 更新弹窗
 *
 * @author yangzm
 * @time 2018/6/22
 */

public class UpdateDialog extends Dialog {
    private View mActionSheetView;
    private Context mContext,mDialogContext;
    //内容
    private TextView tv_content;
    //说明
    private TextView tv_mark;
    private ImageView iv_top;
    private ImageView iv_close;
    private SuperTextView btn_update;
    private RelativeLayout rl_update;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public UpdateDialog(Context context, Context dialogContext) {
        super(dialogContext, R.style.CustomProgressDialog);
        mContext = context;
        mDialogContext = dialogContext;
        initView();
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
     * @param mark     说明
     * @param color    颜色
     * @param textSize 字体大小
     */
    public void setMark(String mark, int color, int textSize) {
        tv_mark.setVisibility(View.VISIBLE);
        tv_mark.setText(mark);
        tv_mark.setTextColor(ContextCompat.getColor(mContext,color));
        ScreenMeasureUtil.getInstance().setMTextSize(tv_mark, textSize);
    }

    /**
     * 设置第一个按钮普通色
     *
     * @param text 按钮名称
     */
    public void setUpdateButton(String text/*, View.OnClickListener l*/) {
        rl_update.setVisibility(View.VISIBLE);
        iv_close.setVisibility(View.VISIBLE);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.APP_CLOSE));
                dismiss();
            }
        });
        btn_update.setText(text);
//        btn_update.setOnClickListener(l);
    }

    /**
     * 设置头部的图片
     *
     * @param imgId 图片id，R.mipmap.XX
     */
    public void setTopImg(int imgId) {
        iv_top.setImageResource(imgId);
        iv_top.setVisibility(View.VISIBLE);
        ScreenMeasureUtil.getInstance().setMLayoutParam(iv_top, 1f, 0.5f);
    }


    //初始化控件
    private void initView() {
        mActionSheetView = LayoutInflater.from(mContext).inflate(R.layout.basecommon_dialog_update, null);
        // 顶部图片
        iv_top = (ImageView) mActionSheetView.findViewById(R.id.iv_top);
        btn_update = mActionSheetView.findViewById(R.id.versionchecklib_version_dialog_commit);
        rl_update = mActionSheetView.findViewById(R.id.rl_update);
        iv_close = mActionSheetView.findViewById(R.id.iv_close);
        //内容
        tv_content = (TextView) mActionSheetView.findViewById(R.id.tv_content);
        ScreenMeasureUtil.getInstance().setMTextSize(tv_content, 16);
        ScreenMeasureUtil.getInstance().setMViewPadding(tv_content, DimensConstant.DimensScale.DIMENS_16, DimensConstant.DimensScale.DIMENS_40,
                DimensConstant.DimensScale.DIMENS_16, DimensConstant.DimensScale.DIMENS_40);
        //说明
        tv_mark = (TextView) mActionSheetView.findViewById(R.id.tv_mark);
        tv_mark.setMovementMethod(ScrollingMovementMethod.getInstance());
        //加载UI
        setContentView(mActionSheetView);
        //设置底部配置
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenMeasureUtil.getInstance().getWidthPixels() * 4 / 5;
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
//        dialogWindow.setWindowAnimations(R.style.bottom_dialog_anim_style);
        dialogWindow.setGravity(Gravity.CENTER);
    }


}
