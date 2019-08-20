package com.shangcheng.home_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.shangcheng.home_module.R;
import com.shangcheng.home_module.adapter.HomeProductShowAdapter;
import com.shangcheng.home_module.entity.GoodsBeanEntity;


import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author: niuyunwang
 * 项目名称：XiTaoTaoMall
 * 类描述：首页产品展示View(淘淘/普淘)
 * 创建人：niuyunwang
 * 创建时间：2019/3/29 9:46
 * 修改人：niuyunwang
 * 修改时间：2019/3/29 9:46
 * 修改备注：暂无
 */
public class HomeProductShowView extends LinearLayout {
    private Context mContext;
    /**
     * 标题
     */
    private String title;
    /**
     * 每行显示个数
     */
    private int gridNum = 2;
    private HomeProductShowAdapter adapter;
    /**
     * 展示标题
     */
    private TextView tvProductTitle;
    /**
     * 更多
     */
    private TextView tvProductMore;
    private RecyclerView recyclerView;
    /**
     * 列表点击事件
     */
    private onItemClickListener listener;
    private List<GoodsBeanEntity.GoodsBean> dataList;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        public void itemClick(int position);
    }

    public HomeProductShowView(Context context) {
        super(context);
        initView(context, null);
    }

    public HomeProductShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public HomeProductShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeProductShowView);
            title = typedArray.getString(R.styleable.HomeProductShowView_title);
            gridNum = typedArray.getInt(R.styleable.HomeProductShowView_gridnum, 2);
            typedArray.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.home_product_show_view, this);
        tvProductTitle = findViewById(R.id.tv_product_title);
        tvProductMore = findViewById(R.id.tv_product_more);
        recyclerView = findViewById(R.id.recyclerView);

    }

    /***
     * 加载数据
     * @param dataList
     */
    public void bindData(List<GoodsBeanEntity.GoodsBean> dataList) {
        this.dataList = dataList;
        tvProductTitle.setText(title);
        adapter = new HomeProductShowAdapter(mContext, this.dataList);
        adapter.setOnItemClickListener(new HomeProductShowAdapter.onItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (listener != null) {
                    listener.itemClick(position);
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
                    adapter.setScrolling(false);
                    adapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
                } else {
                    adapter.setScrolling(true);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(mContext, gridNum);
        gridLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
//        DividerItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.HORIZONTAL);
//        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.line));
//        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
    }

    public void updateData(List<GoodsBeanEntity.GoodsBean> dataList) {
        this.dataList = dataList;
        adapter.notifyDataSetChanged();
    }

    /**
     * 更多按钮点击事件
     *
     * @param onClickListener 点击监听
     */
    public void setMoreListener(OnClickListener onClickListener) {
        tvProductMore.setOnClickListener(onClickListener);
    }
}
