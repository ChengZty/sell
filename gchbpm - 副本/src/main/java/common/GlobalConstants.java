package common;

import java.util.ResourceBundle;

public class GlobalConstants {
	private GlobalConstants() {	  }
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("sysConfig");
	public static final String DOMAIN = bundle.getString("QN_domain");//七牛上传 域名
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	public static final String FIELD_STATUS = "status";
	
	//访问状态
	public static final String mag_success = "200";//成功
	public static final String mag_fail = "201";//失败
	
	//和字典表一致
	public static final String USER_TYPE_01 = "01";//后台
	public static final String USER_TYPE_02 = "02";//零售商
	public static final String USER_TYPE_05 = "05";//零售商员工
	public static final String USER_TYPE_03 = "03";//导购
	public static final String USER_TYPE_04 = "04";//顾客
	public static final String USER_TYPE_06 = "06";//交易顾客(tperson表是06,user表还是04)
	public static final String USER_TYPE_07 = "07";//尖刀产品顾客
	
	public static final String ROLE_TYPE_01 = "01";//后台       用于区分菜单
	public static final String ROLE_TYPE_02 = "02";//零售商        用于区分菜单
//	public static final String ROLE_TYPE_02 = "02";//零售商(人+货)
//	public static final String ROLE_TYPE_03 = "03";//零售商(只有货)
//	public static final String ROLE_TYPE_04 = "04";//零售商(只有人)
	//FTP图片存放路径
	public static final String CATEGORY_DIR = "category";//大类
	public static final String CATEGORY_NEWS = "news";//资讯
	public static final String PAY_TYPE = "payType";//支付方式
	public static final String GOODS_MAIN_PICS = "goodsMainPics";//商品主图
	public static final String GOODS_SMALL_PICS = "goodsSmallPics";//商品小图
	public static final String GOODS_DTL_PICS = "goodsDtlPics";//商品明细图
	public static final String BASE_ACTIVITY_PICS = "activityPics";//商品明细图
	public static final String GUIDE_RECMD_PICS = "guideRecmdPics";//导购推荐图片
	public static final String CERTIFICATE_PICS = "certificatePics";//专家证书图片
	public static final String NEWS_TYPE = "getAllNewType";//资讯分类
	public static final String BASE_BRAND = "baseBrand";//品牌
	public static final String SCENE = "scene";//场景
	public static final String MEMBERSHIP = "membership";//会员级别头像
	public static final String STORE_PICS = "storePics";//实体店图片
	public static final String CARD_PICS = "cardPics";//卡面图片
	public static final String FIN_ACTIVITY = "finActivity";//活动奖励封面图片
	public static final String SMS_POSTER_PIC = "smsPosterPic";//短信海报图片
	public static final String WX_UUID_PIC = "WxUUIDPic";//微信登录图片
	
	//小红点版本号格式
//	public static final String GUIDE_NEWS_VERSION = "GUIDE_NEWS_VERSION";//资讯版本号
	public static final String GUIDE_HELPER_VERSION = "GUIDE_HELPER_VERSION";//帮手资讯版本号
	public static final String GUIDE_GOODS_VERSION = "GUIDE_GOODS_VERSION";//集合店版本号前缀
	public static final String GUIDE_ORDER_VERSION = "GUIDE_ORDER_VERSION";//订单版本号前缀
	public static final String GUIDE_CLASS_CUSTOMER_VERSION = "GUIDE_CLASS_CUSTOMER_VERSION";//管家课堂 
	public static final String GUIDE_STORY_CUSTOMER_VERSION = "GUIDE_STORY_CUSTOMER_VERSION";//管家故事
	public static final String GUIDE_D_LINK_VERSION = "GUIDE_D_LINK_VERSION";//d+链接
	
	public static final String Q_COUNT = "Q_COUNT";//顾客问题的个数
	public static final String TAG_COUNT = "TAG_COUNT";//顾客标签的个数（某阶段）
	
	public static String PLATFORM_TYPE_1 = "1";//平台
	public static String PLATFORM_TYPE_2 = "2";//零售商
	public static String PLATFORM_TYPE_3 = "3";//零售商查询平台
	
	/**发送短信的前缀*/
	public static String SEND_MSG_ = "SEND_MSG_";
	public static final Integer SEND_MSG_TIME = 60;
	
	
	//规定短信验证码2分钟后失效(有效时间为2分钟)
	public static final int VALIDATEION_EFFICIENT_TIME = 2;
	public static final Integer WECHAT_REFRESH_TIME = 60 * 60;
	
	/**发送短信短链接前缀名*/
	public static String SEND_URL_LEFT = "快戳";
	/**发送短信短链接后缀名*/
	public static String SEND_URL_RIGHT = "，";
	/**1代表来自短信顾客列表选择，否则为其他页面来源*/
	public static String SEND_PAGE_SOURCE = "1";
	
	//订单自动回复短信内容
	public static String ORDER_CONFIRM = "order_confirm";
	public static String ORDER_PAY = "order_pay";
	public static String ORDER_SEND = "order_send";
	public static String ORDER_APPLY = "order_apply";
	public static String ORDER_RETURNSUCC = "order_returnSucc";
	
//	public static String ORDER_PHONE = "phone";//客服电话
//	public static String ORDER_RETURN_PHONE = "return_phone";//退货电话
//	public static String ORDER_RETURN_ADDRESS = "return_address";//退货地址
//	
//	public static Map<String,List<String>> orderMsgMap = new HashMap<String,List<String>>();
//	static{
//		/**订单确认自动回复*/
//		List<String> list1 = new ArrayList<String>();
//		list1.add("您好，您在（零售商名称）的订单（订单号）将被保留一个小时。点击首页->我的->待付款订单，进行支付。如有疑问联系客服：<span phone>（客服电话）</span>");
//		orderMsgMap.put(GlobalConstants.ORDER_CONFIRM, list1);
//		
//		/**支付确认自动回复*/
//		List<String> list2 = new ArrayList<String>();
//		list2.add("您好，您在（零售商名称）的订单（订单号）已经支付成功！订单详情页可关注物流状况。如有疑问联系客服：<span phone>（客服电话）</span>");
//		orderMsgMap.put(GlobalConstants.ORDER_PAY, list2);
//		
//		/**发货自动回复*/
//		List<String> list3 = new ArrayList<String>();
//		list3.add("您好，您的订单（订单号）已发货（物流单号：（物流单号））如有疑问联系客服：<span phone>（客服电话）</span>");
//		orderMsgMap.put(GlobalConstants.ORDER_SEND, list3);
//	
//		/**申请退货自动回复*/
//		List<String> list4 = new ArrayList<String>();
//		list4.add("您好，您的退货申请已通过。如尚未收货，我们将尝试追回商品，如无法追回，请您拒收。如您已收货，请将退货商品寄到：<span returnAddress>（退货地址）</span>，退货组收，电话：<span returnPhone>（退货号码）</span>。请将发货单或联系方式写明放入寄回的商品中，且快递不要选择到付。仓库收到商品后，将为你退款。如有疑问联系客服：<span phone>（客服电话）</span>");
//		orderMsgMap.put(GlobalConstants.ORDER_APPLY, list4);
//		
//		/**退货成功自动回复*/
//		List<String> list5 = new ArrayList<String>();
//		list5.add("您好，您的订单（订单号）退款申请已处理，商品退款：（退款金额）元，支付宝/财付通/银行卡原路退款预计1-7个工作日到账，请您留意最近的账户资金变化。如有疑问联系客服：<span phone>（客服电话）</span>");
//		orderMsgMap.put(GlobalConstants.ORDER_RETURNSUCC, list5);
//	}
	
	public static String ORDER_TYPE_BILL = "6";//账单流水号单号类型
	
	public static String ACT_GOODS = "act_goods_";//活动商品
	
	public static final String GUIDE_OD_RED_1 = "GUIDE_OD_RED_1";//工作台订单红点前缀（待付款）
	public static final String GUIDE_OD_RED_2 = "GUIDE_OD_RED_2";//工作台订单红点前缀（待发货）
	public static final String GUIDE_OD_RED_3 = "GUIDE_OD_RED_3";//工作台订单红点前缀（已发货）
	public static final String GUIDE_OD_RED_9 = "GUIDE_OD_RED_9";//工作台订单红点前缀（已取消）
	public static final String GUIDE_OD_RED_R = "GUIDE_OD_RED_R";//工作台订单红点前缀（退款）
	
	//是否打标记
	public static String SIGN_YES="Y";//打红点
	public static String SIGN_NO="N";//不打红点
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
