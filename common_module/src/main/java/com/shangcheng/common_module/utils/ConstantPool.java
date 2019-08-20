package com.shangcheng.common_module.utils;

import com.shangcheng.common_module.BuildConfig;

/**
 * 常量池
 *
 * @author yangzm
 * @time 2017/12/8
 */

public class ConstantPool {


    public  static final String PARTY_MODE = "普通党员模式";

    /**
     * EventBus Code
     */
    public static final class EventCode {

        /**
         * WebView 视频
         */
        public static final int WEB_VIDEO = 0x000002;
        /**
         * WebView 显示图片
         */
        public static final int WEB_DISPLAY_PICTURE = 0x000003;
        /**
         * WebView 扫一扫
         */
        public static final int WEB_SCAN = 0x000003;
        /**
         * WebView 定位
         */
        public static final int WEB_LOCATION = 0x000004;
        /**
         * WebView 关闭加载框
         */
        public static final int WEB_DISMISS_DIALOG = 0x000005;
        /**
         * WebView 空白页
         */
        public static final int WEB_BLANK = 0x000006;
        /**
         * 打开相册
         */
        public static final int GALLERY = 0x000007;
        /**
         * 打开话题
         */
        public static final int TOPIC = 0x000008;
        /**
         * 打开相机
         */
        public static final int PHOTO = 0x000009;
        /**
         * 打开麦克风
         */
        public static final int MIC = 0x0000011;
        /**
         * 打开相册(党员视角发布专用)
         */
        public static final int GALLERY_FOR_PUBLISH = 0x000012;
        /**
         * 输入框发送
         */
        public static final int SEND = 0x000013;
        /**
         * 分享成功回调
         */
        public static final int SHARE_CALLBACK = 0x000014;
        /**
         * 退出APP
         */
        public static final int EXIT_APP = 0x000015;
        /**
         * 数据排名网络请求取消
         */
        public static final int DATA_RANK_REQUEST_CANCLE = 0x00000a;

        /**
         * 党组织入驻选择省市
         */
        public static final int SELECT_PROVINCE = 0x00000b;

        /**
         * 个人中心选择省市
         */
        public static final int PERSON_SELECT_PROVINCE = 0x00000c;

        /**
         * 选择省市带区
         */
        public static final int SELECT_PROVINCE_CITY_DISTRICT = 0x00000d;

        /**
         *  微信分享（shareWechat）
         */
        public static final int SHARE_WECHAT = 0x00000e;
        /**
         *  跳到首页
         */
        public static final int MAIN = 0x00000F;
        /**
         *  跳到我的
         */
        public static final int MY = 0x000020;
        /**
         *  刷新我的页面
         */
        public static final int REFRESH_USERINFO = 0x000021;
        /**
         *  编辑我的地址
         */
        public static final int EDIT_ADDRESS= 0x000022;
        /**
         *  增加我的地址
         */
        public static final int ADD_ADDRESS= 0x000023;
        /**
         *  选择规格
         */
        public static final int SELECT_RULE= 0x000024;
        /**
         *  选择银行名称
         */
        public static final int SELECT_BANK_NAME= 0x000025;
        /**
         *  删除我的地址
         */
        public static final int DEL_ADDRESS= 0x000026;
        /**
         *  设置推荐人
         */
        public static final int SET_RECOMMENDER= 0x000027;
        /**
         *  刷新淘批专区数据
         */
        public static final int REFRESH_TAOPI_AREA= 0x000028;
        /**
         *  刷新淘淘专区数据
         */
        public static final int REFRESH_TAOTAO_AREA= 0x000029;
        /**
         *  商品详情立即购买按钮
         */
        public static final int DETAIL_GOBUY= 0x000030;
        /**
         *  确定订单选择地址
         */
        public static final int SELECT_ADDRESS= 0x000031;
        /**
         *  关闭订单相关页面
         */
        public static final int ORDER_CLOSE= 0x000032;
        /**
         *  订单刷新
         */
        public static final int ORDER_REFRESH= 0x000033;
        /**
         *  关闭程序
         */
        public static final int APP_CLOSE= 0x000034;
        /**
         *  刷新我的账户
         */
        public static final int REFRESH_ACCOUNT = 0x000035;
        /**
         *  关闭webview
         */
        public static final int WEBVIEW_CLOSE = 0x000036;
        /**
         *  关闭Login页面
         */
        public static final int LOGIN_CLOSE = 0x000037;
    }

    /**
     * 网络请求相关
     */
    public static final class NetWork {
        /**
         * 网络请求成功返回code
         */
        public static final int NETWORK_RESULT_SUCCESS = 1;
        /**
         * 每页的列数
         */
        public static final int PAGE_COUNT = 20;
        /**
         * 详情评论页 显示三条（取大于3条用于判断是否显示更多,因为缓存问题必须为20条）
         */
        public static final int COMMENT_PAGE_COUNT = 20;
        /**
         * 异常信息
         */
        public static final String SERVER_MESSAGE_ERROR = "服务器繁忙，请稍后再试";
        public static final String DATA_MESSAGE_ERROR = "数据异常，请稍后再试";
        /**
         * 提示信息
         */
        public static final String NOT_NETWORK_HINT = "网络不给力，请检测网络设置";
        public static final String SEND_MESSAGE_SUCCESS_HINT = "验证码发送成功";
        public static final String CODE_INVALID = "请确保二维码有效";
        //接口相关
        public static final String NETWORK_ERROR_5511 = "您的登录信息已过期，需要重新登录";
        public static final String NETWORK_ERROR_IM = "您的账号已在其他设备上登录";
        //文件存储相关
        public static final String SAVE_PHOTO_DCIM = "图片已保存至相册";
    }

    /**
     * 设备相关
     */
    public static final class DeviceCode {
        /**
         * 设备ID
         */
        public static final String APK_NAME = "xitaotao.apk";
    }

    /**
     * 隐式Intent
     */
    public static final class Action {
        /**
         * 登录页
         */
        public static final String ACTION_LOGIN = BuildConfig.APP_ID + ".login";
        /**
         * 验证码页
         */
        public static final String ACTION_INPUT_CODE = BuildConfig.APP_ID + ".input.code";
        /**
         * 扫描结果页
         */
        public static final String ACTION_SCAN_RESULT = BuildConfig.APP_ID + ".scan.result";
        /**
         * 网上党支部首页
         */
        public static final String ACTION_BRANCH_ONLINE = BuildConfig.APP_ID + ".branch.online";
        /**
         * 会议详情
         */
        public static final String ACTION_MEET_DETAIL = BuildConfig.APP_ID + ".meet.detail";
        /**
         * 新闻详情-工作信息
         */
        public static final String ACTION_NEW_ONLINE = BuildConfig.APP_ID + ".new.detail";
        /**
         * 新闻详情
         */
        public static final String ACTION_WORK_DETAIL = BuildConfig.APP_ID + ".work.detail";
        /**
         * 党支部首页
         */
        public static final String ACTION_PART_DETAIL = BuildConfig.APP_ID + ".part.detail";
        /**
         * 资料详情
         */
        public static final String ACTION_VIDEO_DETAIL = BuildConfig.APP_ID + ".video.detail";
        /**
         * 资料专题详情
         */
        public static final String ACTION_VIDEO_LIST = BuildConfig.APP_ID + ".video.list";
        /**
         * 视频详情页
         */
        public static final String ACTION_DOCUMENT_DETAIL = BuildConfig.APP_ID + ".document.detail";
        /**
         * 党支部首页
         */
        public static final String ACTION_DATA_DETAIL = BuildConfig.APP_ID + ".data.detail";
        /**
         * 积分记录页
         */
        public static final String ACTION_RANK_DAY = BuildConfig.APP_ID + ".rank.day";
        /**
         * IM-详情资料
         */
        public static final String ACTION_IM_DETAIL = BuildConfig.APP_ID + ".im.detail";
        /**
         * IM-详情资料
         */
        public static final String ACTION_IM_DETAIL_INFO = BuildConfig.APP_ID + ".im.detailInfo";
        /**
         * 开启视频会议
         */
        public static final String ACTION_START_MEETING = BuildConfig.APP_ID + ".meeting.start";
        /**
         * 转移党支部
         */
        public static final String ACTION_TRANSFER_BRANCH = BuildConfig.APP_ID + ".login.TransferBranchActivity";
        /**
         * 评论原创
         */
        public static final String ACTION_ORIGINAL_LIST = BuildConfig.APP_ID + ".original.OriginalActivity";
		/**
         * 书籍详情
         */
        public static final String ACTION_BOOK_DETAIL = BuildConfig.APP_ID + ".book.BookDetailActivity";
        /**
         * 二维码分享调用最近联系人界面
         */
        public static final String ACTION_SHARE_RECENT = BuildConfig.APP_ID + ".share.recent";
        /**
         * 申请进群界面
         */
        public static final String ACTION_APPLY_FOR_GROUP = BuildConfig.APP_ID + ".im.ApplyForGroupActivity";

        /**
         * 答题列表
         */
        public static final String ACTION_ANSWER_LIST = BuildConfig.APP_ID + ".answer.AnswerListActivity";
        /**
         * 原创详情
         */
        public static final String ACTION_ORIGINAL_DETAIL = BuildConfig.APP_ID + ".original.detail";
        /**
         * 聊天
         */
        public static final String ACTION_CHAT = BuildConfig.APP_ID + ".chat.ChatActivity";
        /**
         * 我的主页
         */
        public static final String ACTION_MY_HOME = BuildConfig.APP_ID + ".my.home";
        /**
         * WebView 加载页
         */
        public static final String ACTION_WEB = BuildConfig.APP_ID + ".web";
        /**
         * 二维码登录
         */
        public static final String ACTION_ER_LOGIN = BuildConfig.APP_ID + ".qrcode.ScanErCodeLoginActivity";
        /**
         * 我的地址
         */
        public static final String ACTION_ADDRESS = BuildConfig.APP_ID + ".address";
        /**
         * 搜索
         */
        public static final String ACTION_SEARCH = BuildConfig.APP_ID + ".search";
        /**
         * 订单详情
         */
        public static final String ACTION_ORDERDETAIL = BuildConfig.APP_ID + ".orderDetail";
        /**
         * 订单售后
         */
        public static final String ACTION_AFTERSAFEDETAIL = BuildConfig.APP_ID + ".afterSaleDetail";
        /**
         * 订单售后
         */
        public static final String ACTION_PRODUCTDETAIL = BuildConfig.APP_ID + ".productDetail";
        /**
         * 退貨退款編輯
         */
        public static final String ACTION_EDITAFLERSAL = BuildConfig.APP_ID + ".EditAfterSale";
        /**
         * 订单物流信息
         */
        public static final String ACTION_ORDERLOGISTICS = BuildConfig.APP_ID + ".OrderLogistics";
        /**
         * 主页
         */
        public static final String ACTION_MAIN = BuildConfig.APP_ID + ".main";
        /**
         * 主页
         */
        public static final String ACTION_ORDEREDITZHUAN = BuildConfig.APP_ID + ".OrderEditZhuan";
        /**
         * 订单状态
         */
        public static final String ACTION_PAY_STATUS = BuildConfig.APP_ID + ".PayStatus";
    }

    /**
     * 基础常量
     */
    public static final class BasicsCode {
        /**
         * 二级菜单—首页
         */
        public static final String TAB_HOME = "home";
        /**
         * 二级菜单—购物车
         */
        public static final String TAB_SHOPCART = "shopcart";
        /**
         * 二级菜单—新品上市
         */
        public static final String TAB_NEWS = "news";
        /**
         * 二级菜单—我
         */
        public static final String TAB_MY = "my";


    }

    /**
     * 网络连接类型
     */
    public static final class NetWorkType {
        // 无网络
        public static final int NONE = 0x000000;
        //流量
        public static final int MOBILE = 0x000001;
        //wifi
        public static final int WIFI = 0x000002;

    }

    /**
     * SharedPreferences Code
     */
    public static final class SPCode {
        //spName
        public static final String SP_NAME = "party_building_cloud";
        //设置WebView字体
        public static final String WEB_TEXT_SIZE = "WEB_TEXT_SIZE";
    }

    /**
     * 功能模块code
     */
    public static final class ModuleCode {
        // 信息 - 工作信息
        public static final int CODE_1001 = 1001;
        // 信息 - 党员风采
        public static final int CODE_1002 = 1002;
        // 信息 - 业务工作
        public static final int CODE_1003 = 1003;
        // 信息 - 新闻
        public static final int CODE_1004 = 1004;
        // 学习 - 资料
        public static final int CODE_2001 = 2001;
        // 学习 - 视频
        public static final int CODE_2002 = 2002;
        // 学习 - 答题
        public static final int CODE_2003 = 2003;
        // 学习 - 原创
        public static final int CODE_2004 = 2004;
        // 学习 - 音频
        public static final int CODE_2005 = 2005;
        // 学习 - 读书
        public static final int CODE_2006 = 2006;
        // 发现 - 党员视角
        public static final int CODE_3001 = 3001;
        // 发现 - 党员论坛
        public static final int CODE_3002 = 3002;
        // 发现 - 通知公告
        public static final int CODE_3003 = 3003;
        // 发现 - 网格化管理
        public static final int CODE_3004 = 3004;
        // 发现 - 组织生活统计
        public static final int CODE_3005 = 3005;
        // 发现 - 排行榜
        public static final int CODE_3006 = 3006;
        // 发现 - 管理控制台 (只作显示控制,无更新使用)
        public static final int CODE_3007 = 3007;
        // 发现 - 网上支部
        public static final int CODE_3008 = 3008;
        // 发现 - 党费缴纳	(党建云特有 — 接入农行的服务)
        public static final int CODE_3009 = 3009;
        // 发现 - 应知应会	(支部工作=》中信云 党建云=》百度云)
        public static final int CODE_3010 = 3010;
        // 发现 - 党组织主页
        public static final int CODE_3017 = 3017;
        // 发现 - 视频会议
        public static final int CODE_3025 = 3025;
        // 发现 - 星级评比
        public static final int CODE_3029 = 3029;
        // 消息 - IM
        public static final int CODE_4001 = 4001;
        // 发现 - 党务工作管理(组织信息管理)
        public static final int CODE_5002 = 5002;

        /*外链网址*/
        // 发现 - 党费通	(支部工作特有 — 接入中信的党费通)
        public static final int CODE_3011 = 3011;
        // 发现 - 中信书院
        public static final int CODE_3012 = 3012;
        // 发现 - 移动平台
        public static final int CODE_3013 = 3013;
        // 发现 - 学习中国
        public static final int CODE_3014 = 3014;
        // 发现 - 科协	(党建云特有)
        public static final int CODE_3015 = 3015;
        // 发现 - 年度总结
        public static final int CODE_3037 = 3037;


        /*其他层级页面*/
        //签到详情页
        public static final int CODE_6601 = 6601;
        //IM-新的朋友详情页
        public static final int CODE_6602 = 6602;
    }

    /**
     * 原创类型类型
     */
    public static final class StudyType {
        // 资料
        public static final String DOCUMENT = "1";
        // 视频
        public static final String VIDEO = "2";
        // 音频
        public static final String AUDIO = "3";
        // 读书
        public static final String BOOK = "4";
        // 原创
        public static final String ORIGINAL = "5";
    }


}
