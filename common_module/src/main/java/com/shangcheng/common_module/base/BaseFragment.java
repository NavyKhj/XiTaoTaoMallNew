package com.shangcheng.common_module.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.shangcheng.common_module.R;
import com.shangcheng.common_module.base.baseService.BaseFragmentInterface;

import java.util.Objects;

/**
 * @author Navy
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {
    private View rootView;
    @Nullable
    private TextView mToolbarTitle;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeInitView(savedInstanceState);
        if (rootView != null) {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getLayoutId(), container, false);
            Toolbar mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            mToolbarTitle = (TextView) rootView.findViewById(R.id.toolbar_title);
            if (mToolbar != null) {
                ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);
            }
            if (mToolbarTitle != null) {
                mToolbarTitle.setText((Objects.requireNonNull(getActivity())).getTitle());
                Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayShowTitleEnabled(false);
            }
            initView(savedInstanceState, rootView);
            initData(savedInstanceState, rootView);
        }
        return rootView;
    }

    @SuppressLint("RtlHardcoded")
    public void setToolBarTitle(@Nullable CharSequence title, @Nullable BaseActivity.GravityType gravityType) {
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) Objects.requireNonNull(mToolbarTitle).getLayoutParams();
        switch (Objects.requireNonNull(gravityType)) {
            case CENTER:
                layoutParams.gravity = Gravity.CENTER;
                setToolBar(title, layoutParams);
                break;
            case RIGHT:
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.rightMargin = 20;
                setToolBar(title, layoutParams);
                break;
            default:
                break;
        }
    }

    private void setToolBar(@Nullable CharSequence title, @Nullable Toolbar.LayoutParams layoutParams) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setLayoutParams(layoutParams);
            getToolbar().setVisibility(View.VISIBLE);
            mToolbarTitle.setText(title);
            //设置无图标
            mToolbarTitle.setCompoundDrawables(null, null, null, null);
        } else {
            getToolbar().setTitle(title);
            ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(getToolbar());
        }
    }

    public Toolbar getToolbar() {
        return (Toolbar) rootView.findViewById(R.id.toolbar);
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 500) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 设置下拉刷新
     *
     * @param refresh 布局
     * @param isHome  是否为首页效果
     */
    protected void refrshSetting(SmartRefreshLayout refresh, boolean isHome) {
        if (isHome) {
            refresh.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.refresh_top_start));
        } else {
            refresh.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.transparent_color));
        }

        //设置刷新下拉多少距离 触发刷新
        refresh.setHeaderTriggerRate(1f);
        //回弹动画时长（毫秒）
//        refresh.setReboundDuration(300);
        ////最大显示下拉高度/Header标准高度
        refresh.setHeaderMaxDragRate(2f);
        //是否下拉Header的时候向下平移列表或者内容
        refresh.setEnableHeaderTranslationContent(true);
        //是否启用下拉刷新功能
        refresh.setEnableRefresh(true);
        //是否启用上拉加载功能
        refresh.setEnableLoadMore(false);
        final TextView tvHint = refresh.findViewById(R.id.tv_hint);
        refresh.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                /**刷完消失*/
                if (percent <= 0 || offset <= 0) {
                    tvHint.setText("松手更新");
                }
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
                tvHint.setText("更新中...");
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

        });
    }
}
