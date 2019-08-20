package com.shangcheng.home_module.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shangcheng.common_module.adpater.TclAdapter;
import com.shangcheng.common_module.base.mvp.BaseMvpActivity;
import com.shangcheng.common_module.baseApplication.BaseApplication;
import com.shangcheng.common_module.baseApplication.app;
import com.shangcheng.common_module.event.Event;
import com.shangcheng.common_module.event.EventBusUtil;
import com.shangcheng.common_module.utils.ColorUtil;
import com.shangcheng.common_module.utils.ConstantPool;
import com.shangcheng.common_module.utils.DimensConstant;
import com.shangcheng.common_module.utils.GoodsUtils;
import com.shangcheng.common_module.utils.ScreenMeasureUtil;
import com.shangcheng.common_module.utils.SystemUtils;
import com.shangcheng.common_module.utils.T;
import com.shangcheng.common_module.utils.TypeConvertUtils;
import com.shangcheng.common_module.utils.dalog.MsgCenterDialog;
import com.shangcheng.common_module.utils.imageloader.ImageLoader;
import com.shangcheng.common_module.utils.imageloader.ImageLoaderUtil;
import com.shangcheng.common_module.widget.MyTagCloud;
import com.shangcheng.home_module.R;
import com.shangcheng.home_module.databinding.HomePopuSelectProductInfoBinding;
import com.shangcheng.home_module.entity.ProductDetailBean;
import com.shangcheng.home_module.entity.SelectConditionBeanEntity;
import com.shangcheng.home_module.entity.SelectRuleResultBean;
import com.shangcheng.home_module.mvp.contract.ProductDetailContract;
import com.shangcheng.home_module.mvp.presenter.ProductDetailPresenter;
import com.shangcheng.home_module.ui.activity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import razerdp.basepopup.BasePopupWindow;


/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：商品选择popu
 * 创建人：niuyunwang
 * 创建时间：2019/4/15 16:23
 * 修改人：niuyunwang
 * 修改时间：2019/4/15 16:23
 * 修改备注：暂无
 */
public class SelectConditionPopu extends BasePopupWindow implements OnDismissListener, ProductDetailContract.View {
    private Context mContext;
    private List<SelectConditionBeanEntity.SelectConditionBean> itemList = new ArrayList<>();
    //记录选择的标签
    private Map<String, String> selectIds = new HashMap<>();
    private Map<String, String> selectNames = new HashMap<>();
    private HeaderNumberedAdapter adapter;
    private HomePopuSelectProductInfoBinding binding;
    private ImageLoaderUtil imageLoaderUtil;
    /**
     * 最大购买数
     */
    private int moreNum = 10;
    /**
     * 最小购买数
     */
    private int lessNum = 1;
    private ProductDetailBean detailBean;
    private ProductDetailPresenter detailPresenter;

    @Override
    public void onDismiss() {
        super.onDismiss();
        SelectRuleResultBean resultBean = selectData();

        EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.SELECT_RULE, resultBean));
    }

    private SelectRuleResultBean selectData() {
        SelectRuleResultBean resultBean = new SelectRuleResultBean();
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        if (selectIds != null && selectIds.size() > 0) {
            for (int i = 0; i < itemList.size(); i++) {
                if (selectIds.get(itemList.get(i).getId()) != null) {
                    ids.add(selectIds.get(itemList.get(i).getId()));
                }
                if (selectNames.get(itemList.get(i).getId()) != null) {
                    names.add(selectNames.get(itemList.get(i).getId()));
                }

            }
        }
        int num = TypeConvertUtils.getInstance().parseInt(binding.etNum.getText().toString().trim());
        resultBean.setIds(ids);
        resultBean.setNames(names);
        resultBean.setNum(num);
        return resultBean;
    }

    public SelectConditionPopu(Context context, List<SelectConditionBeanEntity.SelectConditionBean> itemList, ProductDetailBean detailBean) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        imageLoaderUtil = new ImageLoaderUtil();
        this.mContext = context;
        this.itemList = itemList;
        this.detailBean = detailBean;
        initView();
        initData();
        initListener();
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.home_popu_select_product_info);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }


    private void initView() {
        binding = HomePopuSelectProductInfoBinding.bind(getContentView());
    }

    private void initData() {
        setTopData();
        /**处理数据*/
        adapter = new HeaderNumberedAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        binding.popuSelectList.setLayoutManager(linearLayoutManager);
        binding.popuSelectList.setAdapter(adapter);


        String content = binding.etNum.getText().toString();
        setImage(content);
    }

    private void setTopData() {
        binding.tvPopuPrice.setText("¥ " + detailBean.getPrice());
        if (!TextUtils.isEmpty(detailBean.getFirstPicture())) {
            ImageLoader imageLoader = new ImageLoader.Builder()
                    .url(detailBean.getFirstPicture())
                    .imgView(binding.ivPopuPic)
                    .placeHolder(R.mipmap.bc_ic_placeholder_large)
                    .errorHolder(R.mipmap.bc_ic_placeholder_large)
                    .scaleType(1)
                    .build();
            imageLoaderUtil.loadImage(mContext, imageLoader);
        } else {
            binding.ivPopuPic.setImageResource(R.mipmap.bc_ic_placeholder_large);
        }
        /**标题*/
        binding.tvPopuTitle.setText(detailBean.getName());
        /**已售*/
        binding.tvPopuMall.setText("已售" + detailBean.getSold() + "件");
        /**库存*/
        binding.tvPopuTotal.setText("库存" + detailBean.getStock() + "件");
        /**限购*/
        if (detailBean.getLimitCount() <= 0) {
            binding.tvPopuBuyNum.setVisibility(View.GONE);
            moreNum = detailBean.getStock();
        } else {
            binding.tvPopuBuyNum.setText("此商品每人限购" + detailBean.getLimitCount() + "件");
            if (detailBean.getLimitCount() <= detailBean.getStock()) {
                moreNum = detailBean.getLimitCount();
            } else {
                moreNum = detailBean.getStock();
            }
        }
        /**普淘*/
        if (GoodsUtils.isTypePT(detailBean.getAreaCode())) {
            binding.tvPopuQuan.setVisibility(View.VISIBLE);
            binding.tvPopuQuan.setText("+" + detailBean.getTaoticket() + "券");
        } else if (GoodsUtils.isTypeSoure(detailBean.getAreaCode())) {
            binding.tvPopuQuan.setVisibility(View.VISIBLE);
            binding.tvPopuQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
            binding.tvPopuQuan.setText("+" + detailBean.getPoints() + "积分");
            /**积分*/
        } else if (GoodsUtils.isTypeTT(detailBean.getAreaCode())) {
            //淘淘
            binding.tvPopuQuan.setVisibility(View.VISIBLE);
            binding.tvPopuQuan.setText("返" + detailBean.getTaoticket() + "券");
            binding.tvPopuPrice.setText("¥ " + detailBean.getOriginalPrice());
        } else if (GoodsUtils.isTypeTP(detailBean.getAreaCode())) {
            //淘批
            binding.tvPopuQuan.setVisibility(View.VISIBLE);
            binding.tvPopuQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
            binding.tvPopuQuan.setText("+" + detailBean.getTaoticket() + "券");
        } else if (GoodsUtils.isTypeST(detailBean.getAreaCode())) {
            //奢淘
            binding.tvPopuQuan.setVisibility(View.VISIBLE);
            binding.tvPopuQuan.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_color));
            binding.tvPopuQuan.setText("+" + detailBean.getTaoticket() + "券");
            binding.btnBuy.setBackgroundColor(Color.parseColor("#CEB38F"));
            binding.tvPopuQuan.setTextColor(Color.parseColor("#CEB38F"));
            binding.tvPopuPrice.setTextColor(Color.parseColor("#CEB38F"));
        }
    }

    private void initListener() {
        /**关闭按钮*/
        binding.ivPopuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        /**删减商品*/
        binding.rlReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etNum.getText().toString();
                int num = TypeConvertUtils.getInstance().parseInt(content);
                if (num <= lessNum) {
                    T.show("最少购买一件哦");
                    return;
                }
                num = num - 1;
                binding.etNum.setText(String.valueOf(num));
            }
        });
        /**增加商品*/
        binding.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etNum.getText().toString();
                int num = TypeConvertUtils.getInstance().parseInt(content);
                if (num >= moreNum) {
                    T.show("最多购买" + moreNum + "件哦");
                    return;
                }
                num = num + 1;
                binding.etNum.setText(String.valueOf(num));
            }
        });
        /**监听商品数量输入框*/
        binding.etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (!TextUtils.isEmpty(content)) {
                    int num = TypeConvertUtils.getInstance().parseInt(content);
                    /**输入数量为最大*/
                    if (num > moreNum) {
                        T.show("最多购买" + moreNum + "件哦");
                        content = String.valueOf(moreNum);
                        binding.etNum.setText(content);
                    }
                    /**输入数量为最小*/
                    if (num < lessNum) {
                        T.show("最少购买" + lessNum + "件哦");
                        content = String.valueOf(lessNum);
                        binding.etNum.setText(content);
                    }
                    setImage(content);
                }

            }
        });
        /**立即购买*/
        binding.btnBuy.setText("立即购买");
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectIds.size() != itemList.size()) {
                    T.show("请选择规格");
                    return;
                }
                dismiss();
                SelectRuleResultBean resultBean = selectData();
                EventBusUtil.sendEvent(new Event(ConstantPool.EventCode.DETAIL_GOBUY, resultBean));
            }
        });
        binding.llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        SystemUtils.forwardToDial((ProductDetailActivity) mContext, "400188866");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    /***
     * 设置加减图片颜色
     * @param content 输入的数量
     */
    public void setImage(String content) {
        if (!TextUtils.isEmpty(content)) {
            int num = TypeConvertUtils.getInstance().parseInt(content);
            /**判断输入数量是都为最小*/
            if (num == lessNum) {
                binding.ivReduce.setAlpha(0.2f);
                binding.ivAdd.setAlpha(1f);
            }
            /**判断输入数量在中间状态*/
            if (num > lessNum && num < moreNum) {
                binding.ivReduce.setAlpha(1f);
                binding.ivAdd.setAlpha(1f);
            }
            /**判断输入数量是否为最大*/
            if (num == moreNum) {
                binding.ivReduce.setAlpha(1f);
                binding.ivAdd.setAlpha(0.2f);
            }
        }
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }


    class HeaderNumberedAdapter extends RecyclerView.Adapter<HeaderNumberedAdapter.TextViewHolder> {


        @Override
        public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextViewHolder mViewHolder;
            View header = LayoutInflater.from(mContext).inflate(R.layout.news_select_list_item_title, parent, false);
            mViewHolder = new TextViewHolder(header);
            return mViewHolder;
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(final TextViewHolder holder, final int position) {
            // Subtract headerNumber for heade
            holder.textView.setText(itemList.get(position).getTitle());
            holder.flowLayout.setTagSpacing((int) (ScreenMeasureUtil.getInstance().getWidthPixels() * DimensConstant.DimensScale.DIMENS_24));
            holder.flowLayout.setLineSpacing((int) (ScreenMeasureUtil.getInstance().getWidthPixels() * DimensConstant.DimensScale.DIMENS_24));
            if (itemList.get(position).getChild() != null && itemList.get(position).getChild().size() > 0) {
                final TclAdapter tclAdapter = new TclAdapter(mContext, itemList.get(position).getChild());
                holder.flowLayout.setAdapter(tclAdapter);
                tclAdapter.setSelectPosition(-1);
                holder.flowLayout.setItemClickListener(new MyTagCloud.TagItemClickListener() {
                    @Override
                    public void itemClick(int child) {
                        selectIds.put(itemList.get(position).getId(), itemList.get(position).getChild().get(child).getId());
                        selectNames.put(itemList.get(position).getId(), itemList.get(position).getChild().get(child).getName());
                        tclAdapter.setSelectPosition(child);
                        if (selectIds.size() == itemList.size()) {
                            getDetail();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        class TextViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public MyTagCloud flowLayout;

            public TextViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv_name);
                flowLayout = itemView.findViewById(R.id.flowlayout);
            }
        }
    }

    @Override
    public void initMvp() {
        detailPresenter = new ProductDetailPresenter(this);
    }

    @Override
    public void loadDataSuccess(Object data, String requestTag) {
        ProductDetailBean productDetailBean = (ProductDetailBean) data;
        if (productDetailBean != null) {
            if (!TextUtils.isEmpty(productDetailBean.getFirstPicture())) {
                detailBean.setFirstPicture(productDetailBean.getFirstPicture());
            }
            detailBean.setSold(productDetailBean.getSold());
            detailBean.setStock(productDetailBean.getStock());
            setTopData();
        }
    }

    @Override
    public void loadDataError(Throwable e, int requestTag) {

    }

    /**
     * 获取详情数据
     */
    public void getDetail() {
        detailPresenter.getProductDetail((BaseMvpActivity) mContext, detailBean.getId());
    }
}
