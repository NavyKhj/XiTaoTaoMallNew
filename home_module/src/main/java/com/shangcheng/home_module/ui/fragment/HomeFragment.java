package com.shangcheng.home_module.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shangcheng.common_module.base.mvp.BaseMvpFragment;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.SystemUtils;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.utils.TypeConvertUtils;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;
import com.shangcheng.common_module.utils.time.TimeUtils;
import com.shangcheng.home_module.R;
import com.shangcheng.home_module.adapter.LocalImageHolderView;
import com.shangcheng.home_module.adapter.TPLocalImageHolderView;
import com.shangcheng.home_module.adapter.TTLocalImageHolderView;
import com.shangcheng.home_module.databinding.FragmentHomeBinding;
import com.shangcheng.home_module.entity.BannerBean;
import com.shangcheng.home_module.entity.GoodsBeanEntity;
import com.shangcheng.home_module.entity.HomeListProductBean;
import com.shangcheng.home_module.entity.HomeProductBean;
import com.shangcheng.home_module.mvp.contract.HomeContract;
import com.shangcheng.home_module.mvp.presenter.HomePresenter;
import com.shangcheng.home_module.ui.activity.ProductDetailActivity;
import com.shangcheng.home_module.widget.HomeProductShowView;
import com.shangcheng.home_module.widget.RoundBackgroundColorSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Navy
 */
@SuppressWarnings("unchecked")
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {
    private FragmentHomeBinding binding;
    private List<HomeProductBean> putaoProduct = new ArrayList<>();
    private List<HomeProductBean> soureProduct = new ArrayList<>();
    private List<HomeProductBean> taotaoProduct = new ArrayList<>();
    private List<HomeProductBean> taopiProduct = new ArrayList<>();
    /**
     * hot数据
     */
    private List<GoodsBeanEntity.GoodsBean> hotList = new ArrayList<>();
    private List<GoodsBeanEntity.GoodsBean> hotMoreList = new ArrayList<>();
    public int ptshowLast = 0;
    public int srshowLast = 0;
    protected ImageLoaderUtil imageLoaderUtil;
    private CountDownTimer countDownTimer;
    private int page = 1;
    /**
     * 图片轮播
     */
    private ArrayList<BannerBean> localImages = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0xff:
                    if (ptshowLast + 1 <= putaoProduct.size() - 1) {
                        if (!TextUtils.isEmpty(putaoProduct.get(ptshowLast + 1).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowFirst, putaoProduct.get(ptshowLast + 1).getPicture());
                        } else {
                            binding.ivPutaoShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(putaoProduct.get(ptshowLast + 1).getPrice())) {
                            binding.tvPutaoShowFrist.setText("¥" + putaoProduct.get(ptshowLast + 1).getPrice());
                        }
                        binding.llPutaoShowFirst.setTag(putaoProduct.get(ptshowLast + 1).getId());
                        ptshowLast = ptshowLast + 1;
                    } else {
                        if (!TextUtils.isEmpty(putaoProduct.get(0).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowFirst, putaoProduct.get(0).getPicture());
                        } else {
                            binding.ivPutaoShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(putaoProduct.get(0).getPrice())) {
                            binding.tvPutaoShowFrist.setText("¥" + putaoProduct.get(0).getPrice());
                        }
                        binding.llPutaoShowFirst.setTag((putaoProduct.get(0).getId()));
                        ptshowLast = 0;
                    }
                    if (ptshowLast + 1 <= putaoProduct.size() - 1) {
                        if (!TextUtils.isEmpty(putaoProduct.get(ptshowLast + 1).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowSecond, putaoProduct.get(ptshowLast + 1).getPicture());
                        } else {
                            binding.ivPutaoShowSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(putaoProduct.get(ptshowLast + 1).getPrice())) {
                            binding.tvPutaoShowSecond.setText("¥" + putaoProduct.get(ptshowLast + 1).getPrice());
                        }
                        binding.llPutaoShowSecond.setTag(putaoProduct.get(ptshowLast + 1).getId());
                        ptshowLast = ptshowLast + 1;
                    } else {
                        if (!TextUtils.isEmpty(putaoProduct.get(0).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowSecond, putaoProduct.get(0).getPicture());
                        } else {
                            binding.ivPutaoShowSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(putaoProduct.get(0).getPrice())) {
                            binding.tvPutaoShowSecond.setText("¥" + putaoProduct.get(0).getPrice());
                        }
                        binding.llPutaoShowSecond.setTag(putaoProduct.get(0).getId());
                        ptshowLast = 0;
                    }
                    mHandler.sendEmptyMessageDelayed(0xff, 5000);
                    break;
                case 0xdd:
                    if (srshowLast + 1 <= soureProduct.size() - 1) {
                        if (!TextUtils.isEmpty(soureProduct.get(srshowLast + 1).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowFirst, soureProduct.get(srshowLast + 1).getPicture());
                        } else {
                            binding.ivSoureShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(soureProduct.get(srshowLast + 1).getPrice())) {
                            binding.tvSoureShowFrist.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(srshowLast + 1).getOriginalPrice(), soureProduct.get(srshowLast + 1).getPrice()));
                        }
                        binding.llSoureShowFirst.setTag(soureProduct.get(srshowLast + 1).getId());
                        srshowLast = srshowLast + 1;
                    } else {
                        if (!TextUtils.isEmpty(soureProduct.get(0).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowFirst, soureProduct.get(0).getPicture());
                        } else {
                            binding.ivSoureShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(soureProduct.get(0).getPrice())) {
                            binding.tvSoureShowFrist.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(0).getOriginalPrice(), soureProduct.get(0).getPrice()));
                        }
                        binding.llSoureShowFirst.setTag(soureProduct.get(0).getId());
                        srshowLast = 0;
                    }
                    if (srshowLast + 1 <= soureProduct.size() - 1) {
                        if (!TextUtils.isEmpty(soureProduct.get(srshowLast + 1).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowSecond, soureProduct.get(srshowLast + 1).getPicture());
                        } else {
                            binding.ivSoureShowSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(soureProduct.get(srshowLast + 1).getPrice())) {
                            binding.tvSoureShowSecond.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(srshowLast + 1).getOriginalPrice(), soureProduct.get(srshowLast + 1).getPrice()));
                        }
                        binding.llSoureShowSecond.setTag(soureProduct.get(srshowLast + 1).getId());
                        srshowLast = srshowLast + 1;
                    } else {
                        if (!TextUtils.isEmpty(soureProduct.get(0).getPicture())) {
                            imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowSecond, soureProduct.get(0).getPicture());
                        } else {
                            binding.ivSoureShowSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
                        }
                        if (!TextUtils.isEmpty(soureProduct.get(0).getPrice())) {
                            binding.tvSoureShowSecond.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(0).getOriginalPrice(), soureProduct.get(0).getPrice()));
                        }
                        binding.llSoureShowSecond.setTag(soureProduct.get(0).getId());
                        srshowLast = 0;
                    }
                    mHandler.sendEmptyMessageDelayed(0xdd, 5000);
                    break;
                default:
                    break;
            }
        }
    };

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("ResourceType")
    @Override
    public void initView(Bundle savedInstanceState, View rootView) {
        binding = FragmentHomeBinding.bind(rootView);
        refrshSetting(binding.refresh, true);
        imageLoaderUtil = new ImageLoaderUtil();
        //搜索
        binding.rlSearch.setOnClickListener(this);
        //普淘专区按钮
        binding.rlShetaoList.setOnClickListener(this);
        //普淘专区按钮
        binding.rlPutaoList.setOnClickListener(this);
        //淘淘专区按钮
        binding.rlTaotaoList.setOnClickListener(this);
        //淘批专区按钮
        binding.rlTaopiList.setOnClickListener(this);
        //积分专区按钮
        binding.rlSoureList.setOnClickListener(this);
        //普淘展示第一个商品
        binding.llPutaoShowFirst.setOnClickListener(this);
        //普淘展示第二个商品
        binding.llPutaoShowSecond.setOnClickListener(this);
        //积分展示第一个商品
        binding.llSoureShowFirst.setOnClickListener(this);
        //积分展示第二个商品
        binding.llSoureShowSecond.setOnClickListener(this);
        //淘批查看更多
        binding.tvTaopiMore.setOnClickListener(this);
        //淘淘查看更多
        binding.tvTaotaoMore.setOnClickListener(this);
        //第一个广告
        binding.ivAdFrist.setOnClickListener(this);
        //第二个广告
        binding.ivAdSecond.setOnClickListener(this);
        //第三个广告
        binding.ivAdThree.setOnClickListener(this);
        ScreenMeasureUtil.getInstance().setMViewMargin(binding.rlSearch,
                ScreenMeasureUtil.getInstance().dip2px(12) / (float) ScreenMeasureUtil.getInstance().getWidthPixels(),
                ScreenMeasureUtil.getInstance().getStatusBarHeight(),
                ScreenMeasureUtil.getInstance().dip2px(12) / (float) ScreenMeasureUtil.getInstance().getWidthPixels(),
                0);
        //搜索框设置
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .solid(ContextCompat.getColor(getActivity(),R.color.normal_content_background_color))
                .radius(100).into(binding.rlSearch);
        //普淘积分展示框
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .line(1, ContextCompat.getColor(getActivity(),R.color.normal_divider_line))
                .solid(ContextCompat.getColor(getActivity(),R.color.normal_content_background_color))
                .radius(3).into(binding.llPutaoShow);
        //淘批展示框
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .line(1, ContextCompat.getColor(getActivity(),R.color.normal_divider_line))
                .solid(ContextCompat.getColor(getActivity(),R.color.normal_content_background_color))
                .radius(3).into(binding.rlTaopiShow);
        //淘淘展示框
        DevShapeUtils.shape(DevShape.RECTANGLE)
                .line(1,ContextCompat.getColor(getActivity(), R.color.normal_divider_line))
                .solid(ContextCompat.getColor(getActivity(),R.color.normal_content_background_color))
                .radius(3).into(binding.rlTaotaoShow);
    }

    @Override
    public void initData(Bundle bundle, View rootView) {
        mPresenter.getAreaProduct(HomeFragment.this);
        binding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getHotList(HomeFragment.this, page);
                mPresenter.getAreaProduct(HomeFragment.this);
            }
        });
        //是否启用上拉加载功能
        binding.refresh.setEnableLoadMore(true);
        binding.refresh.setEnableLoadMoreWhenContentNotFull(false);
        //加载更多
        binding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getHotList(HomeFragment.this, page);
            }
        });
        initBannerDefault();
    }

    @Override
    public void loadDataSuccess(Object data, String requestTag) {
        binding.refresh.finishRefresh();
        switch (requestTag) {
            case "getAreaProduct":
                HomeListProductBean bean = (HomeListProductBean) data;
                if (bean != null && ((bean.getGeneralArea() != null && bean.getGeneralArea().size() > 0)
                        || (bean.getPointsArea() != null && bean.getPointsArea().size() > 0)
                        || (bean.getTaopiArea() != null && bean.getTaopiArea().size() > 0)
                        || (bean.getTaotaoArea() != null && bean.getTaotaoArea().size() > 0))
                ) {
                    mHandler.removeCallbacksAndMessages(null);
                    putaoProduct = bean.getGeneralArea();
                    soureProduct = bean.getPointsArea();
                    taotaoProduct = bean.getTaotaoArea();
                    taopiProduct = bean.getTaopiArea();
                    initPuTaoProduct();
                    initSoureProduct();
                    initTaoPiProduct();
                    initTaoTaoProduct();
                    mPresenter.getHotList(HomeFragment.this, page);
                    getAds();
                }
                break;
            case "getHotList":
                final GoodsBeanEntity entity = (GoodsBeanEntity) data;
                hotList = entity.getList();
                if (hotList != null && hotList.size() > 0) {
                    binding.ivHot.setVisibility(View.VISIBLE);
                    if (hotList.size() >= 2) {
                        binding.showProduct.setVisibility(View.VISIBLE);
                        binding.showProductMore.setVisibility(View.VISIBLE);
                        final List<GoodsBeanEntity.GoodsBean> list = new ArrayList<>();
                        list.add(hotList.get(0));
                        list.add(hotList.get(1));
                        binding.showProduct.bindData(list);
                        binding.showProduct.setOnItemClickListener(new HomeProductShowView.onItemClickListener() {
                            @Override
                            public void itemClick(int position) {
                                goToPruductDetail(list.get(position).getId());
                            }
                        });
                        List<GoodsBeanEntity.GoodsBean> morList = new ArrayList<>(hotList);
                        morList.remove(0);
                        morList.remove(0);
                        if (hotMoreList.size() > 0) {
                            hotMoreList.clear();
                        }
                        hotMoreList.addAll(morList);
                        binding.showProductMore.bindData(hotMoreList);
                        binding.showProductMore.setOnItemClickListener(new HomeProductShowView.onItemClickListener() {
                            @Override
                            public void itemClick(int position) {
                                goToPruductDetail(hotMoreList.get(position).getId());
                            }
                        });
                    } else {
                        binding.showProduct.setVisibility(View.VISIBLE);
                        binding.showProductMore.setVisibility(View.GONE);
                        binding.showProduct.bindData(entity.getList());
                        binding.showProduct.setOnItemClickListener(new HomeProductShowView.onItemClickListener() {
                            @Override
                            public void itemClick(int position) {
                                goToPruductDetail(entity.getList().get(position).getId());
                            }
                        });
                    }

                } else {
                    binding.ivHot.setVisibility(View.GONE);
                    binding.showProduct.setVisibility(View.GONE);
                    binding.showProductMore.setVisibility(View.GONE);
                }
                break;
            case "getHotListMore":
                final GoodsBeanEntity moreEntity = (GoodsBeanEntity) data;
                List<GoodsBeanEntity.GoodsBean> ListMore = moreEntity.getList();
                if (ListMore != null && ListMore.size() > 0) {
                    hotMoreList.addAll(ListMore);
                    binding.showProductMore.updateData(hotMoreList);
                } else {
                    T.show("无更多数据");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void loadDataError(Throwable e, int requestTag) {

    }

    private void getAds() {
        binding.ivAdFrist.setVisibility(View.VISIBLE);
        binding.ivAdSecond.setVisibility(View.VISIBLE);
        binding.ivAdThree.setVisibility(View.VISIBLE);
    }

    /**
     * 普淘展示
     */
    @SuppressLint("SetTextI18n")
    private void initPuTaoProduct() {
        //普淘展示
        if (putaoProduct != null && putaoProduct.size() > 0) {
            binding.llPutaoShow.setVisibility(View.VISIBLE);
            binding.rlPutaoShow.setVisibility(View.VISIBLE);
            //普淘展示商品
            if (putaoProduct != null && putaoProduct.size() > 0) {
                binding.llPutaoShow.setVisibility(View.VISIBLE);
                if (putaoProduct.size() >= 2) {
                    binding.ivPutaoShowFirst.setVisibility(View.VISIBLE);
                    binding.ivPutaoShowSecond.setVisibility(View.VISIBLE);
                    binding.tvPutaoShowFrist.setVisibility(View.VISIBLE);
                    binding.tvPutaoShowSecond.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(putaoProduct.get(0).getPicture())) {
                        imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowFirst, putaoProduct.get(0).getPicture());
                    } else {
                        binding.ivPutaoShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                    }
                    if (!TextUtils.isEmpty(putaoProduct.get(1).getPicture())) {
                        imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowSecond, putaoProduct.get(1).getPicture());
                    } else {
                        binding.ivPutaoShowSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
                    }
                    if (!TextUtils.isEmpty(putaoProduct.get(0).getPrice())) {
                        binding.tvPutaoShowFrist.setText("¥" + putaoProduct.get(0).getPrice());
                    }
                    binding.llPutaoShowFirst.setTag(putaoProduct.get(0).getId());
                    if (!TextUtils.isEmpty(putaoProduct.get(1).getPrice())) {
                        binding.tvPutaoShowSecond.setText("¥" + putaoProduct.get(1).getPrice());
                    }
                    binding.llPutaoShowSecond.setTag(putaoProduct.get(1).getId());
                    if (putaoProduct.size() > 2) {
                        ptshowLast = 1;
                        mHandler.sendEmptyMessageDelayed(0xff, 5000);
                    }
                } else {
                    if (!TextUtils.isEmpty(putaoProduct.get(0).getPicture())) {
                        imageLoaderUtil.loadImage(getActivity(), binding.ivPutaoShowFirst, putaoProduct.get(0).getPicture());
                    } else {
                        binding.ivPutaoShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                    }
                    if (!TextUtils.isEmpty(putaoProduct.get(0).getPrice())) {
                        binding.tvPutaoShowFrist.setText("¥" + putaoProduct.get(0).getPrice());
                    }
                    binding.llPutaoShowFirst.setTag(putaoProduct.get(0).getId());
                    binding.ivPutaoShowFirst.setVisibility(View.VISIBLE);
                    binding.ivPutaoShowSecond.setVisibility(View.INVISIBLE);
                    binding.tvPutaoShowFrist.setVisibility(View.VISIBLE);
                    binding.tvPutaoShowSecond.setVisibility(View.INVISIBLE);
                }
            } else {
                binding.llPutaoShow.setVisibility(View.GONE);
            }
        } else {
            if (soureProduct != null && soureProduct.size() > 0) {
                binding.llPutaoShow.setVisibility(View.VISIBLE);
                binding.rlPutaoShow.setVisibility(View.INVISIBLE);
            } else {
                binding.llPutaoShow.setVisibility(View.GONE);
                binding.rlPutaoShow.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 积分商品展示
     */
    @SuppressLint("SetTextI18n")
    private void initSoureProduct() {
        /**积分展示商品*/
        if (soureProduct != null && soureProduct.size() > 0) {
            binding.llSoureShow.setVisibility(View.VISIBLE);
            if (soureProduct.size() >= 2) {
                binding.ivSoureShowFirst.setVisibility(View.VISIBLE);
                binding.ivSoureShowSecond.setVisibility(View.VISIBLE);
                binding.tvSoureShowFrist.setVisibility(View.VISIBLE);
                binding.tvSoureShowSecond.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(soureProduct.get(0).getPicture())) {
                    imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowFirst, soureProduct.get(0).getPicture());
                } else {
                    binding.ivSoureShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                }
                if (!TextUtils.isEmpty(soureProduct.get(1).getPicture())) {
                    imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowSecond, soureProduct.get(1).getPicture());
                } else {
                    binding.ivSoureShowSecond.setImageResource(R.mipmap.bc_ic_placeholder_large);
                }
                if (!TextUtils.isEmpty(soureProduct.get(0).getPrice())) {
                    binding.tvSoureShowFrist.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(0).getOriginalPrice(), soureProduct.get(0).getPrice()));
                }
                binding.llSoureShowFirst.setTag(soureProduct.get(0).getId());
                if (!TextUtils.isEmpty(soureProduct.get(1).getPrice())) {
                    binding.tvSoureShowSecond.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(1).getOriginalPrice(), soureProduct.get(1).getPrice()));
                }
                binding.llSoureShowSecond.setTag(soureProduct.get(1).getId());
                if (soureProduct.size() > 2) {
                    srshowLast = 1;
                    mHandler.sendEmptyMessageDelayed(0xdd, 5000);
                }
            } else {
                if (!TextUtils.isEmpty(soureProduct.get(0).getPicture())) {
                    imageLoaderUtil.loadImage(getActivity(), binding.ivSoureShowFirst, soureProduct.get(0).getPicture());
                } else {
                    binding.ivSoureShowFirst.setImageResource(R.mipmap.bc_ic_placeholder_large);
                }
                if (!TextUtils.isEmpty(soureProduct.get(0).getPrice())) {
                    binding.tvSoureShowFrist.setText("立省¥" + TypeConvertUtils.getInstance().subtract(soureProduct.get(0).getOriginalPrice(), soureProduct.get(0).getPrice()));
                }
                binding.llSoureShowFirst.setTag(soureProduct.get(0).getId());
                binding.ivSoureShowFirst.setVisibility(View.VISIBLE);
                binding.ivSoureShowSecond.setVisibility(View.INVISIBLE);
                binding.tvSoureShowFrist.setVisibility(View.VISIBLE);
                binding.tvSoureShowSecond.setVisibility(View.INVISIBLE);
            }
        } else {
            binding.llSoureShow.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 淘批展示
     */
    private void initTaoPiProduct() {
        if (taopiProduct != null && taopiProduct.size() > 0) {
            binding.rlTaopiShow.setVisibility(View.VISIBLE);
            List<List<HomeProductBean>> list_tp = SystemUtils.fixedGrouping(taopiProduct, 3);
            initBanerDataTP(list_tp);
            if (taopiProduct.get(0).getTimer() > 0) {
                binding.tvTaopiTimer.setVisibility(View.VISIBLE);
                //普淘倒计时
                long times = taopiProduct.get(0).getTimer();
                countDownTimer = new CountDownTimer(times, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String date = TimeUtils.millis2FitTimeSpan_(millisUntilFinished, 4);
                        SpannableString sp = new SpannableString(date);
                        int day = Objects.requireNonNull(date).indexOf("天");
                        int hour = date.indexOf(":");
                        int second = date.lastIndexOf(":");

                        //设置天颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.normal_body_font_color), ContextCompat.getColor(getActivity(), R.color.normal_content_background_color)),
                                0, day, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //设置小时颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.normal_body_font_color), ContextCompat.getColor(getActivity(), R.color.normal_content_background_color)),
                                day + 1, hour, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //设置分钟颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.normal_body_font_color), ContextCompat.getColor(getActivity(), R.color.normal_content_background_color)),
                                hour + 1, second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //设置秒颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.normal_body_font_color), ContextCompat.getColor(getActivity(), R.color.normal_content_background_color)),
                                second + 1, date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        binding.tvTaopiTimer.setText(sp);
                    }

                    @Override
                    public void onFinish() {
                        String date = "0天 00:00:00";
                        SpannableString sp = new SpannableString(date);
                        int day = date.indexOf("天");
                        int hour = date.indexOf(":");
                        int second = date.lastIndexOf(":");

                        //设置天颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(),R.color.normal_body_font_color), ContextCompat.getColor(getActivity(),R.color.normal_content_background_color)), 0, day, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //设置小时颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(),R.color.normal_body_font_color), ContextCompat.getColor(getActivity(),R.color.normal_content_background_color)), day + 1, hour, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //设置分钟颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(),R.color.normal_body_font_color), ContextCompat.getColor(getActivity(),R.color.normal_content_background_color)), hour + 1, second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //设置秒颜色
                        sp.setSpan(new RoundBackgroundColorSpan(ContextCompat.getColor(getActivity(),R.color.normal_body_font_color), ContextCompat.getColor(getActivity(),R.color.normal_content_background_color)), second + 1, date.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        binding.tvTaopiTimer.setText(sp);
                    }
                };
                countDownTimer.start();
            } else {
                binding.tvTaopiTimer.setVisibility(View.GONE);
            }
        } else {
            binding.rlTaopiShow.setVisibility(View.GONE);
        }
    }

    /**
     * 淘淘展示
     */
    private void initTaoTaoProduct() {
        if (taotaoProduct != null && taotaoProduct.size() > 0) {
            binding.rlTaotaoShow.setVisibility(View.VISIBLE);
            List<List<HomeProductBean>> list_tp = SystemUtils.fixedGrouping(taotaoProduct, 3);
            initBannerDataTT(list_tp);
        } else {
            binding.rlTaotaoShow.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化淘批banner轮播
     */
    @SuppressWarnings("unchecked")
    private void initBanerDataTP(List<List<HomeProductBean>> localImagesTP) {
        try {
            if (localImagesTP != null && localImagesTP.size() > 0) {
                //是否开启自动循环
                boolean isCanLoop = false;
                if (localImagesTP.size() > 1) {
                    isCanLoop = true;
                }
                //开始自动翻页
                binding.bannerTaopi.setPages(new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View view) {
                        return new TPLocalImageHolderView(view, new TPLocalImageHolderView.ITPLocalImageHolderListener() {
                            @Override
                            public void onClick(String id) {
                                goToPruductDetail(id);
                            }
                        });
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.home_taopi_show_view;
                    }

                }, localImagesTP)
                        //设置指示器是否可见
                        .setPointViewVisible(true)
                        //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.mipmap.bc_banner_circle, R.mipmap.bc_banner_circle_hi})
                        .setCanLoop(isCanLoop);

                binding.bannerTaopi.startTurning(5000);
            }
        } catch (Exception e) {

        }


    }

    private void initBannerDefault() {
        localImages.clear();
        for (int i = 0; i < 3; i++) {
            BannerBean bannerBean = new BannerBean();
            if (i == 0) {
                bannerBean.setDefaultPic(R.mipmap.home_banner1);
            }
            if (i == 1) {
                bannerBean.setDefaultPic(R.mipmap.home_banner2);
            }
            if (i == 2) {
                bannerBean.setDefaultPic(R.mipmap.home_banner4);
            }
            if (i == 3) {
                bannerBean.setDefaultPic(R.mipmap.home_banner4);
            }
            localImages.add(bannerBean);
        }
        initBannerData();
    }

    private void initBannerData() {
        try {
            if (localImages != null && localImages.size() > 0) {
                //是否开启自动循环
                boolean isCanLoop = false;
                if (localImages.size() > 1) {
                    isCanLoop = true;
                }
                //开始自动翻页
                binding.convenientBanner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View view) {
                        return new LocalImageHolderView(view);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.basecommon_banner_layout;
                    }

                }, localImages)
                        //设置指示器是否可见
                        .setPointViewVisible(true)
                        //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设*/
                        .setPageIndicator(new int[]{R.mipmap.bc_banner_circle, R.mipmap.bc_banner_circle_hi})
                        //设置点击监听事件*/
                        /*.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                BannerBean bean = localImages.get(position);
                                if (bean == null) {
                                    return;
                                }
                            }
                        })*/
                        .setOnPageChangeListener(new OnPageChangeListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int i) {

                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int i, int i1) {

                            }

                            @Override
                            public void onPageSelected(int position) {

                            }
                        })
                        .setCanLoop(isCanLoop);

                binding.convenientBanner.startTurning(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化淘淘banner轮播
     */
    private void initBannerDataTT(List<List<HomeProductBean>> localImagesTP) {
        try {
            if (localImagesTP != null && localImagesTP.size() > 0) {

                //是否开启自动循环
                boolean isCanLoop = false;
                if (localImagesTP.size() > 1) {
                    isCanLoop = true;
                }
                //开始自动翻页
                binding.bannerTaotao.setPages(new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View view) {
                        return new TTLocalImageHolderView(view, new TTLocalImageHolderView.ITTLocalImageHolderListener() {
                            @Override
                            public void onClick(String id) {
                                goToPruductDetail(id);
                            }
                        });
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.home_taopi_show_view;
                    }
                }, localImagesTP)
                        //设置指示器是否可见
                        .setPointViewVisible(true)
                        //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.mipmap.bc_banner_circle, R.mipmap.bc_banner_circle_hi})
                        .setCanLoop(isCanLoop);

                binding.bannerTaotao.startTurning(5000);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 跳转到产品详情详情
     */
    private void goToPruductDetail(String id) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_search) {
            //搜索
        } else if (i == R.id.rl_shetao_list) {
            //奢淘
        } else if (i == R.id.rl_putao_list) {
            //普淘
        } else if (i == R.id.rl_taotao_list) {
            //淘淘
        } else if (i == R.id.rl_taopi_list) {
            //淘批
        } else if (i == R.id.rl_soure_list) {
            //积分专区
        } else if (i == R.id.ll_putao_show_first) {
            //普淘第一个商品
        } else if (i == R.id.ll_putao_show_second) {
            //普淘展示第二个商品
        } else if (i == R.id.ll_soure_show_first) {
            //积分展示第一个商品
        } else if (i == R.id.ll_soure_show_second) {
            //积分展示第二个商品
        } else if (i == R.id.tv_taopi_more) {
            //淘批查看更多
        } else if (i == R.id.tv_taotao_more) {
            //淘淘查看更多
        } else if (i == R.id.iv_ad_frist) {
            //第一个广告
        } else if (i == R.id.iv_ad_second) {
            //第二个广告
        } else if (i == R.id.iv_ad_three) {
            //第三个广告
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
