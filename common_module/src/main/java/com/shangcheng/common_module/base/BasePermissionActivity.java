package com.shangcheng.common_module.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.base.baseService.IPermissionListener;
import com.shangcheng.common_module.utils.dalog.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.util.List;


/**
 * @author Navy
 */
public abstract class BasePermissionActivity extends AppCompatActivity  {
    private IPermissionListener listener;
    private String[] strings;

    public void requestPermission(String[] strings, IPermissionListener listener) {
        this.listener = listener;
        this.strings = strings;
        requestPermission(strings);

    }

    public void requestPermission(final String[] strings) {
        AndPermission.with(this)
                .runtime()
                .permission(strings)
                .rationale(new RuntimeRationale(BasePermissionActivity.this, listener))
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        listener.getPermissionSuccess();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (!AndPermission.hasPermissions(BasePermissionActivity.this, strings)) {
                            showSettingDialog(BasePermissionActivity.this, permissions);
                        }
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.getPermissionFailure();
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        requestPermission(strings);
                    }
                })
                .start();
    }
}
