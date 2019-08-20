package com.shangcheng.xitaotao.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.shangcheng.common_module.aspectj.CheckLogin;
import com.shangcheng.common_module.base.BaseActivity;
import com.shangcheng.common_module.base.BaseFragment;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.DimensConstant;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.widget.bottomnavigation.BottomNavigationBar;
import com.shangcheng.common_module.widget.bottomnavigation.BottomNavigationItem;
import com.shangcheng.home_module.ui.fragment.HomeFragment;
import com.shangcheng.xitaotao.R;
import com.shangcheng.xitaotao.app.MainApplication;
import com.shangcheng.xitaotao.databinding.ActivityMainBinding;

import qiu.niorgai.StatusBarCompat;

/**
 * @author Navy
 */
public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private MainApplication application;
    /**
     * 所选导航位置
     */
    private int lastSelectedPosition = 0;
    /**
     * Fragment相关类
     */
    private FragmentTransaction ft;
    private FragmentManager fm;
    private HomeFragment homeFragment;
    /**
     * Fragment Tab
     */
    private String[] tags = new String[]{ConstantPool.BasicsCode.TAB_HOME,
            ConstantPool.BasicsCode.TAB_NEWS, ConstantPool.BasicsCode.TAB_MY};
    private String[] tagsName = new String[]{"首页", "分类", "我"};
    private int[] checkIcon = new int[]{R.mipmap.tab_icon_news_selector, R.mipmap.tab_icon_manage_selector, R.mipmap.tab_icon_my_selector};
    private int[] unCheckedIcon = new int[]{R.mipmap.tab_icon_news, R.mipmap.tab_icon_manage, R.mipmap.tab_icon_my};

    private void setTabSelected() {
        //设置Item数据(支持网络图片)
        binding.bottomNavigationBar.clearAll();
        binding.bottomNavigationBar
                .addItem(new BottomNavigationItem(checkIcon[0], tagsName[0]).setInactiveIconResource(unCheckedIcon[0]))
                .addItem(new BottomNavigationItem(checkIcon[1], tagsName[1]).setInactiveIconResource(unCheckedIcon[1]))
                .addItem(new BottomNavigationItem(checkIcon[2], tagsName[2]).setInactiveIconResource(unCheckedIcon[2]))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        binding.bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 2) {
                    if (app.init().getUserInfo() == null || !app.init().getUserInfo().isLogin()) {
                        binding.bottomNavigationBar.selectTabInternal(lastSelectedPosition, lastSelectedPosition == 0, false, false);
                    } else {
                        lastSelectedPosition = position;
                    }
                } else {
                    lastSelectedPosition = position;
                }
                initFragment(tags[position], true);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public void initData(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        application = (MainApplication) getApplicationContext();
        StatusBarCompat.translucentStatusBar(this, true);
        ScreenMeasureUtil.getInstance().setMLayoutParam(binding.bottomNavigationBar, 1, DimensConstant.DimensScale.DIMENS_98);
        /*底部栏初始化*/
        binding.bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED);
        binding.bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        setTabSelected();
        lastSelectedPosition = 0;
        initFragment(tags[lastSelectedPosition], true);
    }

    /**
     * Fragment初始化及切换
     *
     * @param tabId
     * @param isHidden
     */
    private void initFragment(String tabId, boolean isHidden) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        homeFragment = (HomeFragment) fm.findFragmentByTag(tags[0]);
        homeFragment = (HomeFragment) fm.findFragmentByTag(tags[1]);
        homeFragment = (HomeFragment) fm.findFragmentByTag(tags[2]);
        hideFragment(homeFragment, isHidden);
        hideFragment(homeFragment, isHidden);
        hideFragment(homeFragment, isHidden);

        if (TextUtils.equals(tabId, ConstantPool.BasicsCode.TAB_HOME)) {
            isHome();
        } else if (TextUtils.equals(tabId, ConstantPool.BasicsCode.TAB_NEWS)) {
            isNews();
        } else if (TextUtils.equals(tabId, ConstantPool.BasicsCode.TAB_MY)) {
            isMy();
        }

        if (!this.isFinishing()) {
            ft.commitAllowingStateLoss();
        }

    }

    /**
     * 隐藏Fragment
     *
     * @param baseFragment
     * @param isHidden
     */
    private void hideFragment(BaseFragment baseFragment, boolean isHidden) {
        if (baseFragment != null) {
            if (isHidden) {
                ft.hide(baseFragment);
            } else {
                ft.detach(baseFragment);
            }
        }
    }

    /**
     * 首页
     */
    private void isHome() {
        if (homeFragment == null) {
            ft.add(R.id.fl_main_tab_content, HomeFragment.newInstance(), tags[0]);
        } else {
            ft.attach(homeFragment);
            ft.show(homeFragment);
        }
    }

    /**
     * 首页
     */
    private void isNews() {
        if (homeFragment == null) {
            ft.add(R.id.fl_main_tab_content, HomeFragment.newInstance(), tags[1]);
        } else {
            ft.attach(homeFragment);
            ft.show(homeFragment);
        }
    }

    /**
     * 首页
     */
    @CheckLogin
    private void isMy() {
        if (homeFragment == null) {
            ft.add(R.id.fl_main_tab_content, HomeFragment.newInstance(), tags[2]);
        } else {
            ft.attach(homeFragment);
            ft.show(homeFragment);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case ConstantPool.EventCode.APP_CLOSE:
                exitApp();
                break;
            case ConstantPool.EventCode.EXIT_APP:
                app.init().withUserInfo(null);
                binding.bottomNavigationBar.selectTabInternal(0, true, false, false);
                lastSelectedPosition = 0;
                initFragment(tags[0], true);
                break;
            case ConstantPool.EventCode.MY:
                binding.bottomNavigationBar.selectTabInternal(2, false, false, false);
                lastSelectedPosition = 2;
                initFragment(tags[2], true);
                break;
            case ConstantPool.EventCode.MAIN:
                binding.bottomNavigationBar.selectTabInternal(0, true, false, false);
                lastSelectedPosition = 0;
                initFragment(tags[0], true);
                break;
            default:
                break;
        }
    }

    private void exitApp() {
        if (application.activityList != null && application.activityList.size() > 0) {
            for (int i = 0; i < application.activityList.size(); i++) {
                Activity activity = application.activityList.get(i);
                activity.finish();
            }
        } else {
            System.exit(0);
        }
    }

    private long lastTime;

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - lastTime < 3000) {
            exitApp();
        } else {
            T.show("再按一下退出程序");
            lastTime = time;
        }
    }
}
