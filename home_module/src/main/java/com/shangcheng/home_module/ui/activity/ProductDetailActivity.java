package com.shangcheng.home_module.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.shangcheng.common_module.aspectj.CheckLogin;
import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.GoodsUtils;
import com.shangcheng.common_module.utils.LogUtil;
import com.shangcheng.common_module.utils.StatusBarCompatUtil;
import com.shangcheng.common_module.utils.SystemUtils;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.utils.TypeConvertUtils;
import com.shangcheng.common_module.utils.dalog.MsgCenterDialog;
import com.shangcheng.common_module.utils.shapeutils.DevShapeUtils;
import com.shangcheng.common_module.utils.shapeutils.shape.DevShape;
import com.shangcheng.common_module.utils.time.TimeUtils;
import com.shangcheng.home_module.R;
import com.shangcheng.home_module.adapter.LocalImageHolderView;
import com.shangcheng.home_module.adapter.ProductDetailBannerHolderView;
import com.shangcheng.home_module.adapter.ProductDetailPicAdapter;
import com.shangcheng.home_module.databinding.HomeActivityProductDetailBinding;
import com.shangcheng.home_module.entity.ProductDetailBean;
import com.shangcheng.home_module.entity.SelectConditionBeanEntity;
import com.shangcheng.home_module.entity.SelectRuleResultBean;
import com.shangcheng.home_module.mvp.contract.ProductDetailContract;
import com.shangcheng.home_module.mvp.presenter.ProductDetailPresenter;
import com.shangcheng.home_module.widget.MyLinearLayoutManager;
import com.shangcheng.home_module.widget.SelectConditionPopu;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * @author Navy
 */
public class ProductDetailActivity extends BaseMvpActivity<ProductDetailPresenter> implements ProductDetailContract.View {
    private HomeActivityProductDetailBinding binding;
    private String id;
    /**
     * 转手详情需要的转手人ID
     */
    private String userId;
    /**
     * 图片轮播
     */
    private List<String> localImages = new ArrayList<>();
    private ProductDetailBean detailBean;
    private ProductDetailPicAdapter adapter;
    private CountDownTimer countDownTimer;
    private SelectRuleResultBean resultBean;
    /**
     * 规格
     */
    private List<SelectConditionBeanEntity.SelectConditionBean> selectList = new ArrayList<>();
    private SelectConditionPopu popu;

    @Override
    public ProductDetailPresenter initPresenter() {
        return new ProductDetailPresenter(this);
    }

    @Override
    public void initData(Bundle bundle) {
        initView();
        getData(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData(intent);
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity_product_detail);
        getToolbar().setBackgroundResource(R.color.white_color);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white_color));
        StatusBarCompatUtil.getInstance().changeToLightStatusBar(this);
        binding.tvOldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //分类选择
        binding.rlSelect.setOnClickListener(this);
        //立即购买
        binding.btnBuyDetail.setOnClickListener(this);
        binding.llPhone.setOnClickListener(this);
    }

    private void getData(Intent intent) {
        id = intent.getStringExtra("id");
        userId = intent.getStringExtra("userId");
        mPresenter.getProductDetail(this, id);
        if (app.init().getUserInfo()!=null&&app.init().getUserInfo().isLogin()) {
            mPresenter.getSelectRule(this, id);
        }
    }

    @Override
    public void loadDataSuccess(Object data, String requestTag) {
        switch (requestTag) {
            case "getDetail":
                detailBean = (ProductDetailBean) data;
                setData(detailBean);
                break;
            case "getSelectRule":
                SelectConditionBeanEntity selectConditionBeanEntity = (SelectConditionBeanEntity) data;
                LogUtil.i(selectConditionBeanEntity.getData().get(0).getChild().get(0).getName());
                break;
            case "getUserInfo":
                mPresenter.checkStock(ProductDetailActivity.this,detailBean, resultBean, selectList, userId);
                break;
            case "checkAccount":
                /*Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra("prepareId", (String) data);
                intent.putExtra("areaCode", detailBean.getAreaCode());
                startActivity(intent);*/
                break;
            default:
                break;
        }
    }

    @Override
    public void loadDataError(Throwable e, int requestTag) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_select) {
            if (selectList == null || selectList.size() <= 0) {
                mPresenter.getSelectRule(ProductDetailActivity.this, id);
            } else {
                initConditionPopu();
            }
        } else if (i == R.id.btn_buy_detail) {
            if (detailBean.getStock() <= 0) {
                T.show("商品已售罄");
                return;
            }
            if (resultBean != null && resultBean.getIds().size() == selectList.size()) {
                if (GoodsUtils.isTypeTP(detailBean.getAreaCode())) {
                    //淘批
                    mPresenter.getUserInfo(ProductDetailActivity.this);
                } else {
                    mPresenter.checkStock(ProductDetailActivity.this, detailBean, resultBean, selectList, userId);
                }
            } else {
                if (selectList == null || selectList.size() <= 0) {
                    mPresenter.getSelectRule(ProductDetailActivity.this, id);
                } else {
                    initConditionPopu();
                }
            }
        } else if (i == R.id.ll_phone) {
            final MsgCenterDialog dialog = new MsgCenterDialog(mContext);
            dialog.setContent("是否联系电话客服");
            dialog.setMark("客服电话：400188866");
            dialog.setFirstButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setSecondButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.forwardToDial(ProductDetailActivity.this, "400188866");
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @CheckLogin
    private void initConditionPopu() {
        if (popu == null) {
            popu = new SelectConditionPopu(mContext, selectList, detailBean);
            if (!popu.isShowing()) {
                popu.showPopupWindow();
            }
        } else {
            if (!popu.isShowing()) {
                popu.showPopupWindow();
            }
        }
    }

    private void setData(ProductDetailBean detailBean) {
        localImages.clear();
        SpannableString spannableString = new SpannableString("¥ " + detailBean.getPrice());
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.6f);
        spannableString.setSpan(relativeSizeSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.tvPrice.setText(spannableString);
        //轮播
        if (detailBean.getCarousel() != null && detailBean.getCarousel().size() > 0) {
            localImages = detailBean.getCarousel();
            binding.rlBanner.setVisibility(View.VISIBLE);
            initBanerData();
        } else {
            if (!TextUtils.isEmpty(detailBean.getFirstPicture())) {
                localImages.add(detailBean.getFirstPicture());
                binding.rlBanner.setVisibility(View.VISIBLE);
                initBanerData();
            } else {
                binding.rlBanner.setVisibility(View.GONE);
            }
        }
        //详情图片
        if (detailBean.getDetails() != null && detailBean.getDetails().size() > 0) {
            adapter = new ProductDetailPicAdapter(mContext, detailBean.getDetails());
            MyLinearLayoutManager manager = new MyLinearLayoutManager(mContext);
            binding.recyclerView.setLayoutManager(manager);
            binding.recyclerView.setAdapter(adapter);
        }
        //标题
        binding.tvTitle.setText(detailBean.getName());
        //已售
        binding.tvMallTotal.setText("已售" + detailBean.getSold() + "件");
        //库存
        binding.tvTotal.setText("总库存" + detailBean.getStock() + "件");
        //限购
        if (detailBean.getLimitCount() <= 0) {
            binding.llRestrictions.setVisibility(View.GONE);
        } else {
            binding.llRestrictions.setVisibility(View.VISIBLE);
            binding.tvRestrictions.setText("此商品每人限购" + detailBean.getLimitCount() + "件");
        }
        //普淘
        if (GoodsUtils.isTypePT(detailBean.getAreaCode())) {
            binding.tvOldPrice.setVisibility(View.VISIBLE);
            binding.tvOldPrice.setText("¥ " + detailBean.getOriginalPrice());
            binding.tvTimer.setVisibility(View.GONE);
            if (TypeConvertUtils.getInstance().parseInt(detailBean.getTaoticket()) != 0) {
                binding.tvQuan.setVisibility(View.VISIBLE);
                binding.tvQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
                binding.tvQuan.setTextColor(ContextCompat.getColor(mContext, R.color.normal_content_background_color));
                binding.tvQuan.setText("+" + detailBean.getTaoticket() + "券");
            } else {
                binding.tvQuan.setVisibility(View.GONE);
            }
        } else if (GoodsUtils.isTypeSoure(detailBean.getAreaCode())) {
            //积分
            binding.tvOldPrice.setVisibility(View.VISIBLE);
            binding.tvOldPrice.setText("¥ " + detailBean.getOriginalPrice());
            binding.tvQuan.setVisibility(View.VISIBLE);
            binding.tvQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
            binding.tvQuan.setTextColor(ContextCompat.getColor(mContext, R.color.normal_content_background_color));
            binding.tvQuan.setText("+" + detailBean.getPoints() + "积分");
            binding.tvTimer.setVisibility(View.GONE);
        } else if (GoodsUtils.isTypeTT(detailBean.getAreaCode())) {
            //淘淘
            binding.tvOldPrice.setVisibility(View.GONE);
            binding.tvQuan.setVisibility(View.VISIBLE);
            SpannableString sp = new SpannableString("¥ " + detailBean.getOriginalPrice());
            RelativeSizeSpan rp = new RelativeSizeSpan(0.6f);
            sp.setSpan(rp, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            binding.tvPrice.setText(sp);
            DevShapeUtils.shape(DevShape.RECTANGLE)
                    .solid(R.color.normal_content_background_color)
                    .radius(8).into(binding.tvQuan);
            binding.tvQuan.setText("返" + detailBean.getTaoticket() + "券");
            binding.tvQuan.setTextColor(ContextCompat.getColor(mContext, R.color.bank_btn_noraml));
            long times = detailBean.getDifferOfflineTime();
            binding.tvTimer.setVisibility(View.VISIBLE);
            countDownTimer = new CountDownTimer(times, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String date = TimeUtils.millis2FitTimeSpan_(millisUntilFinished, 4);
                    binding.tvTimer.setText("距结束 " + date);
                }

                @Override
                public void onFinish() {
                    binding.tvTimer.setText("距结束 0天 00:00:00");
                }
            };
            countDownTimer.start();
        } else if (GoodsUtils.isTypeTP(detailBean.getAreaCode())) {
            //淘批
            binding.tvOldPrice.setVisibility(View.VISIBLE);
            binding.tvOldPrice.setText("¥ " + detailBean.getOriginalPrice());
            binding.tvQuan.setVisibility(View.VISIBLE);
            binding.tvQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
            binding.tvQuan.setTextColor(ContextCompat.getColor(mContext, R.color.normal_content_background_color));
            binding.tvQuan.setText("+" + detailBean.getTaoticket() + "券");
            long times = detailBean.getDifferOfflineTime();
            binding.tvTimer.setVisibility(View.VISIBLE);
            countDownTimer = new CountDownTimer(times, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String date = TimeUtils.millis2FitTimeSpan_(millisUntilFinished, 4);
                    binding.tvTimer.setText("距结束 " + date);
                }

                @Override
                public void onFinish() {
                    binding.tvTimer.setText("距结束 0天 00:00:00");
                }
            };
            countDownTimer.start();
        } else if (GoodsUtils.isTypeST(detailBean.getAreaCode())) {
            //奢淘
            binding.llRestrictions.setBackgroundColor(Color.parseColor("#A27B46"));
            binding.rlMidBg.setBackgroundResource(R.mipmap.shetao_mid_bg);
            binding.tvOldPrice.setVisibility(View.GONE);
            binding.tvOldPrice.setText("¥ " + detailBean.getOriginalPrice());
            binding.tvOldPrice.setTextColor(Color.parseColor("#CEB38F"));
            binding.tvQuan.setVisibility(View.VISIBLE);
            binding.tvQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
            binding.tvQuan.setTextColor(ContextCompat.getColor(mContext, R.color.normal_content_background_color));
            binding.tvQuan.setText("+" + detailBean.getTaoticket() + "券");
            binding.tvQuan.setTextColor(Color.parseColor("#CEB38F"));
            binding.tvPrice.setTextColor(Color.parseColor("#CEB38F"));
            binding.tvRestrictions.setTextColor(Color.parseColor("#CEB38F"));
            binding.tvRestrictionsHint.setTextColor(Color.parseColor("#CEB38F"));
            binding.btnBuyDetail.setBackgroundColor(Color.parseColor("#CEB38F"));
            long times = detailBean.getDifferOfflineTime();
            binding.tvTimer.setVisibility(View.GONE);
            countDownTimer = new CountDownTimer(times, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String date = TimeUtils.millis2FitTimeSpan_(millisUntilFinished, 4);
                    binding.tvTimer.setText("距结束 " + date);
                }

                @Override
                public void onFinish() {
                    binding.tvTimer.setText("距结束 0天 00:00:00");
                }
            };
            countDownTimer.start();
        }
        if (detailBean.getStock() <= 0) {
            binding.btnBuyDetail.setAlpha(0.4f);
            binding.btnBuyDetail.setText("已售罄");
        } else {
            binding.btnBuyDetail.setAlpha(1.0f);
            binding.btnBuyDetail.setText("立即购买");
        }
    }

    /**
     * 初始化顶部banner轮播
     */
    @SuppressLint({"ResourceType", "SetTextI18n"})
    private void initBanerData() {
        try {
            //已售总数背景
            DevShapeUtils.shape(DevShape.RECTANGLE)
                    .solid(ContextCompat.getColor(mContext, R.color.home_mark))
                    .radius(12).into(binding.tvBanner);
            if (localImages != null && localImages.size() > 0) {
                binding.tvBanner.setText(1 + "/" + localImages.size());
                //是否开启自动循环
                boolean isCanLoop = true;
                //开始自动翻页
                binding.banner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View view) {
                        return new ProductDetailBannerHolderView(view);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.basecommon_normal_banner_layout;
                    }
                }, localImages)
                        //设置指示器是否可见
                        .setPointViewVisible(false)
                        //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.mipmap.bc_banner_circle, R.mipmap.bc_banner_circle_hi})
                        //设置点击监听事件
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                            }
                        })
                        .setOnPageChangeListener(new OnPageChangeListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int i) {

                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int i, int i1) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                binding.tvBanner.setText(String.valueOf(position + 1) + "/" + String.valueOf(localImages.size()));
                            }
                        })
                        .setCanLoop(isCanLoop);
                binding.banner.startTurning(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void receiveEvent(Event event) {
        if (event.getCode() == ConstantPool.EventCode.SELECT_RULE) {
            if (event.getData() != null) {
                resultBean = (SelectRuleResultBean) event.getData();
                if (resultBean != null) {
                    if (resultBean.getNames() != null && resultBean.getNames().size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < resultBean.getNames().size(); i++) {
                            if (!TextUtils.isEmpty(resultBean.getNames().get(i))) {
                                sb.append(resultBean.getNames().get(i)).append(" ");
                            }
                        }
                        sb.append("×").append(resultBean.getNum());
                        binding.tvSpecs.setText(sb.toString());
                    }
                }
            }
        } else if (event.getCode() == ConstantPool.EventCode.DETAIL_GOBUY) {
            if (event.getData() != null) {
                resultBean = (SelectRuleResultBean) event.getData();
                if (resultBean != null) {
                    if (resultBean.getNames() != null && resultBean.getNames().size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < resultBean.getNames().size(); i++) {
                            if (!TextUtils.isEmpty(resultBean.getNames().get(i))) {
                                sb.append(resultBean.getNames().get(i)).append(" ");
                            }
                        }
                        sb.append("×").append(resultBean.getNum());
                        binding.tvSpecs.setText(sb.toString());
                    }
                    if (resultBean.getIds() != null && resultBean.getIds().size() == selectList.size()) {
                        if (GoodsUtils.isTypeTP(detailBean.getAreaCode())) {
                            //淘批
//                            getUserInfo(app.init().getUserInfo().getAccessToken());
                            mPresenter.getUserInfo(ProductDetailActivity.this);
                        } else {
                            mPresenter.checkStock(ProductDetailActivity.this, detailBean, resultBean, selectList, userId);
                        }
                    } else {
                        T.show("请选择规格");
                    }
                } else {
                    T.show("请选择规格");
                }
            }
        } else if (event.getCode() == ConstantPool.EventCode.ORDER_CLOSE) {
            finish();
        }
    }
}
