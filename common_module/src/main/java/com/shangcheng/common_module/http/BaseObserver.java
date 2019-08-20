package com.shangcheng.common_module.http;

import android.app.Dialog;
import android.content.Context;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.common.model.BaseEntity;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.dalog.UpdateDialog;

import io.reactivex.disposables.Disposable;

/**
 * @author khj
 * @date 2017-12-25
 */

public abstract class BaseObserver<T extends BaseEntity> extends BaseNormalObserver<HttpResponse<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseObserver() {
        super();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onHandleSuccess(HttpResponse<T> tHttpResponse) {
        switch (tHttpResponse.getStatusCode()) {
            case "00001":
                onSuccess(tHttpResponse.getData());
                break;
            case "00000":
                showUpdateDialog(tHttpResponse.getStatusMsg(),tHttpResponse.getData().getDownloadUrl());
                break;
            default:
                onHandleError(tHttpResponse.getStatusCode(), tHttpResponse.getStatusMsg());
                break;
        }

    }

    protected abstract void onSuccess(T t);

    /**
     * 更新弹窗
     *
     * @param content
     * @param url
     */
    public void showUpdateDialog(final String content, final String url) {
        DownloadBuilder builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(
                        UIData.create().setContent(content)
                                .setDownloadUrl(url)
                                .setTitle("新版本来了")
                );
        builder.setCustomVersionDialogListener(new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                final UpdateDialog dialog = new UpdateDialog(mContext, context);
                dialog.setTopImg(R.mipmap.bc_bg_update_logo);
                dialog.setMark(versionBundle.getContent(), R.color.normal_font_color_tint, 13);
                dialog.setUpdateButton("立即更新");
                return dialog;
            }
        });
        builder.setForceUpdateListener(new ForceUpdateListener() {
            @Override
            public void onShouldForceUpdate() {
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.APP_CLOSE));
            }
        });
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.APP_CLOSE));
            }
        });
        builder.executeMission(mContext);
    }
}
