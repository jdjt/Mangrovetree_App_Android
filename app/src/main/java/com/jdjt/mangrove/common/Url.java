package com.jdjt.mangrove.common;

import com.jdjt.mangrove.BuildConfig;

/**
 * <p/>
 * Description: TODO
 * </P>
 *
 * @author csj 2013-7-18 上午10:00:48
 * @ClassName: Url
 */
public class Url {

    /**
     * 服务地址 2014-5-27上午10:15:44
     */
//  0、正式   1、45        2、36
//	static String[] SelectUrl = new String[]{"http://mws.mymhotel.com/","http://rc-ws.mymhotel.com/","http://192.168.1.36:8080/"};
//	static String [] SelectUrlHTTPS = new String[]{"http://mws.mymhotel.com/","https://rc-ws.mymhotel.com/","https://192.168.1.36:8080/"};

//
//    public static final String BASE = "http://_ip_";
//    public static final String BASEHTTPS = "http://_ip_";

    // 新接口测试地址
    // public static final String SYW_PROJECTMANAGER = "http://192.168.1.45:8080/";
//     public static String SYW_PROJECTMANAGER = "http://103.4.58.124:8080/";//（可外网访问）

    // 新接口正式地址
    public static String SYW_PROJECTMANAGER = "http://syw.mymhotel.com/";

    // 旧接口正式地址
    private static final String BLUE_KEY_BASE = "http://mws.mymhotel.com/";

    private static final String BLUE_KEY_BASE_TEST = "http://192.168.1.45:8181/";
    public static String GET_APP_VERSION_BY_FIR = "http://api.fir.im/apps/latest/";

//	static String BASE = "http://rc-ws.mymhotel.com/";
//	static String BASEHTTPS = "https://rc-ws.mymhotel.com/";

    //String BASE = "http://192.168.1.45:8181/";// 服务器
//	String BASE = "http://192.168.1.36:8080/";// 服务器内网
//	String BASEHTTPS = "https://192.168.1.36:8080/";// 服务器 内网https
    static String BASE = "http://mws.mymhotel.com/";// 服务器 外网
    static String BASEHTTPS = "http://mws.mymhotel.com/";// 服务器 外网
    public static final String WX_PAY_INFO_URL = BASE + ModelEnum.PAY.getCode() + "/payment/wechat/prepay";

    // ///////////////////////////////////酒店信息查询地址/////////////////////////////////////////////
    /**
     * @Fields HOTELRESERVE_ASSET_HOTELARRAY : 酒店信息查询
     * http://192.168.1.36:8080/housekeeper
     * /bookhotal/asset/hotel_array.json
     */
    public static final String HOTELRESERVE_ASSET_HOTELARRAY = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/hotel_array.json";


    /**
     * 房间介绍图片查询
     * 2014-7-30上午11:55:24
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ASSET_ROOMPIC_ARRAY = BASE + ModelEnum.TK.getCode() + "/bookhotal/asset/room_pic_array.json";

    /**
     * @Fields HOTELRESERVE_ASSET_BULIDINGARRAY : 楼宇信息查询
     */
    public static final String HOTELRESERVE_ASSET_BULIDING_ARRAY = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/buliding_array.json";

    /**
     * @Fields HOTELRESERVE_ASSET_FLOORARRAY : 楼层信息查询
     */
    public static final String HOTELRESERVE_ASSET_FLOORARRAY = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/floor_array.json";

    /**
     * @Fields HOTELRESERVE_ASSET_CALENDARARRAY : 日历时间指数
     */
    public static final String HOTELRESERVE_ASSET_CALENDARARRAY = BASE + ModelEnum.TK.getCode()
            + "/hotelreserve/asset/calendarArray";

    /**
     * @Fields HOTELRESERVE_ASSET_ROOMDETAIL : 酒店房间的详情
     */
    public static final String HOTELRESERVE_ASSET_ROOMDETAIL = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/room_detail.json";
    /**
     * 预算接口 2014-7-3下午9:03:45
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ACCURATE_BUDGET = BASEHTTPS + ModelEnum.TK.getCode()
            + "/bookhotal/accurate/budget.json";
    /**
     * 预订接口 2014-7-9下午7:39:09
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ACCURATE_RESERVE = BASEHTTPS + ModelEnum.TK.getCode()
            + "/bookhotal/accurate/reserve.json";

    /**
     * 取消预订 2014-7-11上午10:43:06
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ACCURATE_CLEARRESERVE = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/accurate/cancel_reserve.json";
    /**
     * 获取参数属性 2014-7-18下午3:07:02
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ACCURATE_PARAM_ARRAY = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/param_array.json";

    /**
     * @Fields TRADITION_ROOM_PIC_URL : TODO（传统预定酒店图片地址）
     */
    public static final String TRADITION_ROOM_PIC_URL = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/tradition/room_type_pic_array.json";


    // /////////////////////////////////////////////////////////////////////////////////////////////////

    /***************************** 杨成林 add begin *************************/

    /**
     * @Fields HOTELRESERVE_ASSET_RECOMMENDROOMARRAY : 获取推荐房间
     */
    public static final String HOTELRESERVE_ASSET_RECOMMENDROOMARRAY = BASE
            + ModelEnum.TK.getCode()
            + "/bookhotal/asset/specific_room_array.json";
    /**
     * 图形选择 房间列表 2014-6-20下午8:14:29
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ASSET_FLOOR_PLAN = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/floor_plan.json";

    /**
     * 精准 -房型列表 /bookhotal/asset/roomtype_array 2014-6-21上午10:28:39
     *
     * @author wangmingyu
     */
    public static final String HOTELRESERVE_ASSET_ROOMTYPE_ARRAY = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/asset/roomtype_array.json";

    /***************************** 杨成林 add end *************************/

    /**
     * ************************** 账户中心**开始 ****************************************************
     */
    public static final String MEM_TICKET_VALID = BASE + ModelEnum.UUM.getCode() + "/mem/ticket_valid.json";
    /**
     * 用户登录
     */
    public static final String METHOD_LOGIN = BASEHTTPS + ModelEnum.UUM.getCode()
            + "/mem/sso/login.json";

    /**
     * 用户退出
     */
    public static final String METHOD_LOGOUT = BASE + ModelEnum.UUM.getCode()
            + "/mem/sso/logout.json";
    /**
     * 用户注册
     */
    public static final String METHOD_REG = BASEHTTPS + ModelEnum.UUM.getCode()
            + "/mem/account/register.json";
    /**
     * 用户账号重复验证
     */
    public static final String MEM_CHECK_ACCOUNT = BASE + ModelEnum.UUM.getCode()
            + "/mem/account/check_account.json";
    /**
     * 获取用户验证码
     */
    public static final String METHOD_GETCODE = BASE + ModelEnum.UUM.getCode()
            + "/common/captcha/gain_captcha.json";

    /**
     * 验证码验证
     */
    public static final String METHOD_CHECKCAPTCHA = BASE
            + ModelEnum.UUM.getCode() + "/common/captcha/check_captcha.json";

    /**
     * 获取会员信息
     */
    public static final String METHOD_MEMBERINFO = BASE
            + ModelEnum.UUM.getCode() + "/mem/account/member_info.json";

    /**
     * 会员充值
     */
    public static final String METHOD_RECHANGE = BASEHTTPS
            + ModelEnum.UUM.getCode() + "/mem/account/sub/recharge.json";
    /**
     * 会员转帐
     */
    public static final String METHOD_TRANSFER = BASEHTTPS
            + ModelEnum.UUM.getCode() + "/mem/account/sub/transfer.json";
    /**
     * 会员帐户余额信息查询
     */
    public static final String METHOD_MEMBERBALANCEINFO = BASE
            + ModelEnum.UUM.getCode() + "/mem/account/member_balance_info.json";
    /**
     * 修改会员信息
     */
    public static final String METHOD_MODIFYMEMBER = BASE
            + ModelEnum.UUM.getCode() + "/mem/account/modify_member.json";

    /**
     * 重新绑定
     */
    public static final String METHOD_BINDING = BASEHTTPS + ModelEnum.UUM.getCode()
            + "/mem/account/binding.json";

    /**
     * 找回密码
     */
    public static final String METHOD_RESETPASSWORD = BASEHTTPS
            + ModelEnum.UUM.getCode() + "/mem/account/reset_password.json";

    /**
     * 修改用户密码
     */
    public static final String METHOD_MODIFYPASSWORD = BASEHTTPS
            + ModelEnum.UUM.getCode() + "/mem/account/modify_password.json";

    /**
     * 修改用户支付密码
     */
    public static final String METHOD_MODIFYPAYMENTPWD = BASEHTTPS
            + ModelEnum.UUM.getCode()
            + "/mem/account/modify_payment_password.json";

    /**
     * 会员帐户实名制
     */
    public static final String METHOD_SETTINTBALANCE = BASEHTTPS
            + ModelEnum.UUM.getCode()
            + "/mem/account/setting_member_balance.json";

    /**
     * 会员帐户消费明细
     */
    public static final String METHOD_VIRTUALTOTALDETAIL = BASE
            + ModelEnum.UUM.getCode()
            + "/mem/account/member_virtual_totaldetail.json";

    /**
     * 预订结果
     */
    public static final String METHOD_APPOINMENT = BASE
            + ModelEnum.PIEPRO.getCode()
            + "/program/reserve/mem_reserve_info.json";


    /***************************** 账户中心*结束 ******************************************************/

    /***************************** 餐饮**开始 ******************************************************/


    /**
     * 餐饮参数查询
     */
    public static final String METHOD_DININGPARAM = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/asset/param_array.json";

    /**
     * 菜品列表查询(带分页)
     */
    public static final String METHOD_DININGMENU = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/asset/menu_array.json";

    /**
     * 菜品介绍详情
     */
    public static final String METHOD_DININGMENUDETAIL = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/asset/menu_detail.json";
    /**
     * 预订菜品
     */
    public static final String METHOD_DININGRESERVE = BASE
            + ModelEnum.CATERING.getCode() + "/diningroom/reserve/reserve.json";

    /**
     * 菜品订单列表查询
     */
    public static final String METHOD_DININGORDER = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/order/order_detail_array.json";
    /**
     * 会员所属菜品订单列表查询
     */
    public static final String METHOD_DININGMEMORDER = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/order/member_order_detail_array.json";

    /**
     * 餐厅列表（带分页）
     */
    public static final String METHOD_DININGROOMARRAY = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/asset/diningroom_array.json";

    /**
     * 餐厅详情
     */
    public static final String METHOD_DININGROOMDETAIL = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/asset/diningroom_detail.json";

    /**
     * 餐厅图片列表
     */
    public static final String METHOD_DININGROOMPICARRAY = BASE
            + ModelEnum.CATERING.getCode()
            + "/diningroom/asset/diningroom_pic_array.json";


    /***************************** 餐饮**结束 ******************************************************/


    /***************************** 红树林卡** 开始 ******************************************************/
    /**
     * 红树林卡绑定
     */
    public static final String METHOD_CARDBINDING = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/binding.json";

    /**
     * 红树林卡解绑
     */
    public static final String METHOD_CARDUNBINDING = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/unbinding.json";

    /**
     * 度假卡使用密码验证
     */
    public static final String METHOD_CARDCHECKLOGIN = BASEHTTPS
            + ModelEnum.CARD.getCode()
            + "/card/idxcard/check_login.json";

    /**
     * 会员红树林卡列表信息
     */
    public static final String METHOD_CARDARRAY = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/member_card_array.json";


    /**
     * 某张卡信息查询接口
     */
    public static final String METHOD_CARDINFO = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/card_info.json";

    /**
     * 卡消费列表查询（带分页）
     */
    public static final String METHOD_CARDCONSUMEARRAY = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/consume_array.json";

    /**
     * 卡账户详细信息查询
     */
    public static final String METHOD_CARDACCOUNTINFO = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/card_account_info.json";

    /**
     * 用户绑定卡列表
     * 2014-8-11上午10:16:33
     *
     * @author wangmingyu
     */
    public static final String CARD_INFO_ARRAY = BASE
            + ModelEnum.CARD.getCode()
            + "/card/comm/member_card_array.json";
    /***************************** 红树林卡** 结束 ******************************************************/


    /**
     * 会员所属下的订单列表
     * 2014-7-26下午3:27:48
     *
     * @author wangmingyu
     */
    public static final String MEMBER_ORDER_ARRAY = BASE + ModelEnum.TK.getCode() + "/bookhotal/order/member_order_array.json";
    /**
     * 订单列表（未登录）
     * 2014-8-14下午2:45:47
     *
     * @author wangmingyu
     */
    public static final String ORDER_ARRAY = BASE + ModelEnum.TK.getCode() + "/bookhotal/order/order_array.json";


    /**
     * 订单消费详情
     * 2014-7-26上午10:43:55
     *
     * @author wangmingyu
     */
    public static final String ORDER_COST_DETAIL = BASE + ModelEnum.TK.getCode() + "/bookhotal/order/order_cost_detail.json";
    /**
     * 获取用户所属卡信息
     */
    public static final String METHOD_GETUSERCARDINFO = BASE
            + "/hotelreserve/card/cardInfo";

    /**
     * 支付
     */
    public static final String BOOKHOTAL_PAY_PAYMENT = BASE + ModelEnum.TK.getCode()
            + "/bookhotal/accurate/payment.json";
    /**
     * 微信间夜支付
     *
     * @author wangmingyu
     * 2014-9-25
     */
    public static final String BOOKHOTAL_PAY_PAYMENT_IDX = BASEHTTPS + ModelEnum.TK.getCode()
            + "/bookhotal/accurate/payment_idx.json";
    /**
     * 微信预支付操作
     *
     * @author wangmingyu ///bookhotal/accurate/payment_amount该地址以保留并不做具体 操作
     * 2014-9-20
     */
    public static final String PAYMENT_AMOUNT = BASEHTTPS + ModelEnum.TK.getCode() + "/bookhotal/accurate/paiement_amount.json";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL_ARRAY = BASE + ModelEnum.TK.getCode() + "/bookhotal/order/order_detail_array.json";

    /**
     * 订单详情接口查询,预定号查询
     */
    public static final String ORDER_ORDER_DETAIL = BASE + ModelEnum.TK.getCode() + "/bookhotal/order/order_detail.json";

    /**
     * 退订信息列表查询
     */
    public static final String HOTEL_REFUNDRULEQUERY = BASE + ModelEnum.TK.getCode() + "/bookhotal/accurate/unsubscribequery_reserve.json";
    /**
     * 确认退订订单接口
     */
    public static final String HOTEL_UNSUBSCRIBE_RESERVE = BASE + ModelEnum.TK.getCode() + "/bookhotal/accurate/unsubscribe_reserve.json";

    /**
     * 传统预订房型分页列表
     */
    public static final String TRADITIONAL_HOTEL_SPECIFIC_ROOMTYPE_ARRAY = BASE + ModelEnum.TK.getCode() + "/bookhotal/asset/tradition/roomtype_array.json";
    /**
     * 传统预订房型详情分页列表
     */
    public static final String TRADITIONAL_HOTEL_ROOM_TYPE_DETAIL = BASE + ModelEnum.TK.getCode() + "/bookhotal/asset/tradition/room_type_detail.json";
    /**
     * 传统预订房型图片列表
     */
    public static final String TRADITIONAL_ROOM_TYPE_PIC_ARRAY = BASE + ModelEnum.TK.getCode() + "/bookhotal/asset/tradition/room_type_pic_array.json";

    /**
     * 传统预订房 立即预订
     */
    public static final String TRADITIONAL_ROOM_RESERVE = BASE + ModelEnum.TK.getCode() + "/bookhotal/tradition/reserve.json";

    /**
     * @Fields TRADITIONAL_ROOM_BUDGET : TODO（传统预定 价格计算）
     */
    public static final String TRADITIONAL_ROOM_BUDGET = BASE + ModelEnum.TK.getCode() + "/bookhotal/tradition/budget.json";
    /*************************酒店服务*************************************/
    /**
     * 酒店gps定位
     */
    public static final String HOTEL_GPS_POSITION = BASE
            + ModelEnum.HOTELSERVICE.getCode()
            + "/service/asset/hotel_gps_position.json";

    /**
     * 酒店背景图
     */
    public static final String HOTEL_BACKIMAGE_INFO = BASE
            + ModelEnum.HOTELSERVICE.getCode()
            + "/service/asset/hotel_backImage_info.json";


    /**
     * 消息中心列表
     */
    public static final String MEM_MSGCENTER_ARRAY = BASE
            + ModelEnum.HOTELSERVICE.getCode()
            + "/service/msg/msg_array.json";

    /**
     * 消息中心详情
     */
    public static final String MEM_MSGCENTER_DETAIL = BASE
            + ModelEnum.HOTELSERVICE.getCode()
            + "/service/msg/msg_detail.json";
    /**
     * 消息中数量
     */
    public static final String MEM_MSGCENTER_COUNT = BASE
            + ModelEnum.HOTELSERVICE.getCode()
            + "/service/msg/msg_count.json";
    /**
     * 消息中心系统时间获取
     */
    public static final String MEM_MSGCENTER_SYSDATE = BASE
            + ModelEnum.HOTELSERVICE.getCode()
            + "/service/comm/sysdate.json";

    /**
     * 绑定客房
     *
     * @author wangmingyu
     * 2014-8-27
     */
    public static final String HOTEL_ROOM_BINDING = BASE
            + ModelEnum.TK.getCode() + "/bookhotal/asset/room_binding.json";

    /**
     * 获取房间绑定信息
     *
     * @author wangmingyu
     * 2014-8-27
     */
    public static final String HOTEL_ROOM_BINDING_DETAIL = BASE
            + ModelEnum.TK.getCode() + "/bookhotal/asset/room_binding_detail.json";

    /**
     * 解绑客房
     *
     * @author wangmingyu
     * 2014-8-27
     */
    public static final String HOTEL_ROOM_UNBINDING = BASE
            + ModelEnum.TK.getCode() + "/bookhotal/asset/room_unbinding.json";

    /**
     * 商业街首页
     *
     * @author mars
     * 2014-9-4
     */
    public static final String SHOP_STREET = BASE + ModelEnum.MALL.getCode() + "/shops/asset/shops_array.json";
    ///shops/asset/param_array
    /**
     * 商业街参数查询
     *
     * @author mars
     * 2014-9-4
     */
    public static final String SHOP_PARAMS = BASE + ModelEnum.MALL.getCode() + "/shops/asset/param_array.json";
    /**
     * 商业街详情
     *
     * @author mars
     * 2014-9-4
     */
    public static final String SHOP_DETAIL = BASE + ModelEnum.MALL.getCode() + "/shops/asset/shops_detail.json";
    /**
     * 酒店服务列表
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_SERVICE_LIST = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/service_array.json";
    /**
     * 酒店服务列参数
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_SERVICE_PARAM = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/param_array.json";
    /**
     * 酒店服务详情
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_SERVICE_DETAIL = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/service_detail.json";
//
    /**
     * 酒店服务项目图片详情
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_SERVICE_DETAIL_PICS = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/serviceitem_pic_array.json";
    /**
     * 酒店介绍图片列表
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_INTRO_PICS = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/hotel_pic_array.json";
    /**
     * 酒店介绍项目图片列表
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_INTRO_FAC_PICS = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/hotel_item_array.json";
    /**
     * 酒店介绍项目详情
     *
     * @author mars
     * 2014-9-4
     */
    public static final String HOTEL_INTRO_FAC_PIC = BASE + ModelEnum.HOTELSERVICE.getCode() + "/service/asset/hotel_item_pic_array.json";
    /**
     * 日历
     *
     * @author mars
     * 2014-9-4
     */
    public static final String BOOKHOTEL_CALENDAR = BASE + ModelEnum.TK.getCode() + "/bookhotal/asset/calendar_array.json";
    /**
     * 二维码
     *
     * @author mars
     * 2014-9-4
     */
    public static final String QRCODE_SCAN = BASE + ModelEnum.QRCODE.getCode() + "/scan/common/token/querymessage.json";
    /**
     * 二维码查房
     *
     * @author mars
     * 2014-9-4
     */
    public static final String QRCODE_SCAN_ROOM_INFO = BASE + ModelEnum.TK.getCode() + "/bookhotal/asset/room_info.json";

    /*********************************************************/
    /**
     * 支付渠道接口
     *
     * @author wangmingyu
     * 2014-9-26
     */
    public static final String PAY_CHANNEL_ARRAY = BASE + ModelEnum.PAY.getCode() + "/payment/pay_channel_array.json";

    /*********************************************************/
    /**
     * 版本更新
     * 2014-9-26
     */
    public static final String MEM_VERSIONS = BASE + ModelEnum.UUM.getCode() + "/common/versions.json";

    /*************************馅饼计划***********************************/
    /**
     * 签到接口
     */
    public static final String MEM_SIGNING = BASE + ModelEnum.PIEPRO.getCode() + "/program/sign/mem_signing.json";
    /**
     * 签到验证
     */
    public static final String MEM_SIGN_INFO = BASE + ModelEnum.PIEPRO.getCode() + "/program/sign/mem_sign_info.json";
    /**
     * 验证是否预约
     */
    public static final String CHECK_MEM_RESERVED = BASE + ModelEnum.PIEPRO.getCode() + "/program/reserve/check_mem_reserved.json";
    /**
     * 查询当前登录用户预约信息
     */
    public static final String MEM_RESERVE_INFO = BASE + ModelEnum.PIEPRO.getCode() + "/program/reserve/mem_reserve_info.json";

    public static final String CHECK_CHECKIN_AUTH = BASE + "hotelservice/checkin/auth/check_checkin_auth.json";
    /*
    * 发起服务请求
    * */
    public static final String CALL_TASK = BASE + "hotelservice/checkin/service/add_task.json";
    //入住客人取消任务进度url
    public static final String CANCEL_TASK = BASE + "hotelservice/checkin/service/cancel_task.json";
    //入住客人查询任务进度url
    public static final String TASK_PROGRESS = BASE + "hotelservice/checkin/service/task_progress_info.json";
    //入住客人查询任务进度url
    public static final String TASKCODE_PROGRESS = BASE + "hotelservice/checkin/service/get_task.json";
    //入住客人接收消息接口url
    public static final String CLIENT_RECEVE_MSG = BASE + "hotelservice/checkin/service/receive_chat";
    //入住客人（服务员）发送消息接口url
    public static final String CLIENT_SEND_MSG = BASE + "hotelservice/checkin/service/send_chat";
    //入住客人确认任务url
    public static final String CONFIRM_TASK = BASE + "hotelservice/checkin/service/confirm_task";
    //入住客人获取需要确认的任务url
    public static final String UNCONFIRM_TASK = BASE + "hotelservice/checkin/service/unconfirm_task_info";
    //根据任务号查询任务信息url
    public static final String CLIENT_GET_TASK_INFO = BASE + "hotelservice/checkin/service/get_task.json";

    /**
     * 顾客端获取服务请求列表
     */
    public static final String SERVICE_TASK_INFO = BASE + "/hotelservice/checkin/service/task_progress_info";

    /**
     * 即时通讯获取单聊双方ID和appkey
     */
    public static final String TASK_IM_USER = BASE + "hotelservice/manage/service/get_im_user";


    /*
     * 蓝牙钥匙
     * */
    public static final String GET_BlUETOOTH_KEY = BLUE_KEY_BASE + "hotelservice/getKey.do";

    /***********************************************************************************/
    /*                              新接口请求地址                                       */
    /***********************************************************************************/
    /**
     * 获取酒店列表
     */
    public static final String GET_HOTELLIST = SYW_PROJECTMANAGER + "syw_projectmanager/hotellist/getHotelList";

    /**
     * 获取类型信息
     */
    public static final String GET_CATEGORYLIST = SYW_PROJECTMANAGER + "syw_projectmanager/categorylist/getCategoryList";
    /**
     * 获取酒店详情
     */
    public static final String GET_HOTELDETAIL = SYW_PROJECTMANAGER + "syw_projectmanager/hoteldetail/getHotelDetail";

    /**
     * 业态详情
     */
    public static final String GET_ACTIVITYDETAIL = SYW_PROJECTMANAGER + "syw_projectmanager/activitydetail/getActivitydetail";
    /**
     * 标签列表
     */
    public static final String GET_ACTIVITYTAGLIST = SYW_PROJECTMANAGER + "syw_projectmanager/activitydetail/getActivityTagList";

    /**
     * 业态列表
     */
    public static final String GET_ACTIVITY_LIST = SYW_PROJECTMANAGER + "syw_projectmanager/activitylist/getActivityList";
    /**
     * 获取套餐列表
     */
    public static final String GET_COMBOLIST = SYW_PROJECTMANAGER + "syw_projectmanager/combolist/getComboList";
    /**
     * 获取套餐详情
     */
    public static final String GET_COMBODETAIL = SYW_PROJECTMANAGER + "syw_projectmanager/combodetail/getComboDetail";

    /**
     * 获取购买套餐规格状态信息
     */
    public static final String GET_BILLINFO = SYW_PROJECTMANAGER + "syw_projectmanager/billInfo/getBillInfo";
    /**
     * 将要退款某个套餐规格
     */
    public static final String TO_RETURN_COMBOKIND = SYW_PROJECTMANAGER + "syw_projectmanager/toreturncombokind/combokind";
    /**
     * 将要购买某个套餐规格
     */
    public static final String TO_BUY_COMBOKIND = SYW_PROJECTMANAGER + "syw_projectmanager/tobuy/combo";
    /*
    * 购买套餐规格列表
    * */
    public static final String GET_SELL_INFO = SYW_PROJECTMANAGER + "syw_projectmanager/sellinfo/getSellInfo";
    /*
       * 购买套餐规格列表
       * */
    public static final String GET_ACTIVITY_COMMODITY_LIST = SYW_PROJECTMANAGER + "syw_projectmanager/activityCommodityList/getActivityCommodityList";
}


