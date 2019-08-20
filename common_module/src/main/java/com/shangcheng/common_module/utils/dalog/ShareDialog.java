package com.shangcheng.common_module.utils.dalog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shangcheng.common_module.R;
import com.shangcheng.common_module.utils.ConstantPool;

import java.util.Objects;

/**
 * @author Navy
 */
public class ShareDialog extends Dialog{

    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static void showShareDialog(@NonNull Context context, String name, final String goodsId, final String userId, ClipboardManager clipboardManager) {
        ClipData data = ClipData.newPlainText(null, null);
        clipboardManager.setPrimaryClip(data);
        LayoutInflater layout = LayoutInflater.from(context);
        @SuppressLint("InflateParams")
        View view = layout.inflate(R.layout.dialog_share, null);
        final ShareDialog dialog = new ShareDialog(context,R.style.ShareDialog);
        DisplayMetrics dm = new DisplayMetrics();
        Activity activity = (Activity) context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int x = dm.widthPixels;// 屏幕的宽
        int y = dm.heightPixels;// 屏幕的高
        Objects.requireNonNull(dialog.getWindow()).setLayout((int) (x / 2.2), (int) (y / 0.7));
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView content = view.findViewById(R.id.content);
        content.setText(String.format("是否打开%s商品详情", name));
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("商品ID", goodsId);
                /*Intent intent = new Intent();
                intent.setAction(ConstantPool.Action.ACTION_PRODUCTDETAIL);
                intent.putExtra("id", goodsId);
                intent.putExtra("userId", userId);
                startActivity(intent);
                dialogPop.dismiss();*/
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
