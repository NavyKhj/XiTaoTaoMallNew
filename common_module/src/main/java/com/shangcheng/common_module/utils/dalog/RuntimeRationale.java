package com.shangcheng.common_module.utils.dalog;

import android.content.Context;
import android.text.TextUtils;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.base.baseService.IPermissionListener;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

@SuppressWarnings("ALL")
public class RuntimeRationale implements Rationale<List<String>> {
    private Context context;
    private IPermissionListener listener;

    public RuntimeRationale(Context context, IPermissionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));
        PromptDialog promptDialog = new PromptDialog(context)
                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                .setAnimationEnable(false)
                .setSingle(true)
                .setCanCancel(false)
                .setTitleText(context.getString(R.string.prompt_info))
                .setContentText(message)
                .setPositiveListener(context.getString(R.string.excusme_sure), context.getString(R.string.btn_refuse), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                        // 你的dialog点击了确定调用：
                        executor.execute();
                    }

                    @Override
                    public void onCancleClick(PromptDialog dialog) {
                        dialog.dismiss();
                        // 你的dialog点击了取消调用：
                        listener.getPermissionFailure();
                        executor.cancel();
                    }
                });
        promptDialog.show();
    }
}
