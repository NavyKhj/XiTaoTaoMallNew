package com.shangcheng.common_module.utils.dalog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.activity.WebLinkCacheActivity;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.databinding.DialogAgreementBinding;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;

import java.util.Objects;

public class AgreementDialog extends Dialog {
    private Context mContext;
    private OnDialogCancelListener listener;

    public AgreementDialog(@NonNull Context context) {
        super(context, R.style.ShareDialog);
        mContext = context;
        initView();
    }

    private void initView() {
        View mDialogContent = LayoutInflater.from(mContext).inflate(R.layout.dialog_agreement, null);
        DialogAgreementBinding binding = DialogAgreementBinding.bind(mDialogContent);
        setContentView(mDialogContent);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = Objects.requireNonNull(dialogWindow).getAttributes();
        lp.width = ScreenMeasureUtil.getInstance().getWidthPixels() * 4 / 5;
        lp.height = ScreenMeasureUtil.getInstance().getHeightPixels() * 3 / 5;
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("点击同意即表示您已阅读并同意");

        SpannableStringBuilder spannableString0 = new SpannableStringBuilder();
        spannableString0.append("《平台基本规则》");
        spannableString0.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                goToWeb("平台基本规则", "https://wap.xitaotao.cn/#/basisAgreement");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.agreement_font_color));
            }
        }, 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder spannableString1 = new SpannableStringBuilder();
        spannableString1.append("《平台使用协议》");
        spannableString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                goToWeb("平台使用协议", "https://wap.xitaotao.cn/#/userAgreement");
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.agreement_font_color));
            }
        }, 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder spannableString2 = new SpannableStringBuilder();
        spannableString2.append("《隐私政策》");
        spannableString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                goToWeb("隐私协议", "https://wap.xitaotao.cn/#/privacyAgreement");
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.agreement_font_color));
            }
        }, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder spannableString3 = new SpannableStringBuilder();
        spannableString3.append("《平台争议处理规范》");
        spannableString3.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                goToWeb("平台争议处理规范", "https://wap.xitaotao.cn/#/controversyAgreement");
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(mContext, R.color.agreement_font_color));
            }
        }, 0, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.append(spannableString0).append(spannableString1).append(spannableString2).append(spannableString3);

        binding.agreementContent.setText(spannableString);
        binding.agreementContent.setMovementMethod(LinkMovementMethod.getInstance());
        binding.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.agree();
                }
            }
        });
        binding.disAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.disAgree();
                }
            }
        });
    }

    public interface OnDialogCancelListener {
        void agree();

        void disAgree();
    }

    public void setOnDialogCancelListener(OnDialogCancelListener listener) {
        this.listener = listener;
    }

    private void goToWeb(String title, String url) {
        Intent intent = new Intent(mContext, WebLinkCacheActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }
}
