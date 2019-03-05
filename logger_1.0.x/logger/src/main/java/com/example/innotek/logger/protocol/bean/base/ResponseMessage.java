package com.example.innotek.logger.protocol.bean.base;

/**
 * 响应消息
 * 
 * @author zhy
 * @since 1.0,2014-6-10
 */
public class ResponseMessage
{

	// 返回错误码（待补充完整）
	/** 成功=0 */
	public static final int ERRCODE_SUCCESS = 0; // 成功
	public static final int ERRCODE_FAILURE = -1; // 失败
	/** 非法命令=1 */
	public static final int ERRCODE_UNINITIALIZE = -2; // 未初始化
	public static final int ERRCODE_INVALID_COMMAND = 1; // 非法命令
	public static final int ERRCODE_INVALID_PARAM = 2; // 参数格式不正确
	public static final int ERRCODE_UNLOGIN = 3; // 用户未签到
	public static final int ERRCODE_INVALID_LOGINKEY = 4; // 签到验证码不存在或不正确
	public static final int ERRCODE_ALREADY_LOGOUT = 5; // 收费员已经登出设备
	public static final int ERRCODE_INVALID_TERMIANL = 1001; // 没有此终端
	public static final int ERRCODE_INVALID_USERORPASSWORD = 1002; // 账号或密码不正确
	public static final int ERRCODE_NOCOLLECTORDUTY = 1003; // 没有排班
	public static final int ERRCODE_ALREADY_CHECKIN = 1004; // 收费员已签到
	public static final int ERRCODE_INVALID_OLDPASSWORD = 1005; // 旧密码不正确
	public static final int ERRCODE_TERMINAL_NOTASSIGNED = 1006; // 终端没有分配给该收费员
	public static final int ERRCODE_PARKINGSITE_CHECKIN = 1007; // 停车点已经被其他收费员签到
	public static final int ERRCODE_HANDSET_BROKEN = 1008; // 终端已损坏
	public static final int ERRCODE_NEWWORD = 1009; // 网络故障
	public static final int ERRCODE_NOT_DUTYPARKINGSITE = 2001; // 不是值班的停车点
	public static final int ERRCODE_VERSION_NEW = 2002; // 当前版本是最新版本
	public static final int ERRCODE_VERSION_TYPE_NULL = 2003; // 当前设备未识别
	public static final int ERRCODE_NOT_OWEFEERECORD = 3001; // 没有欠费记录
	public static final int ERRCODE_NOT_PARKRECORD = 3002; // 没有该停车记录

	public static final int ERRCODE_ALREADY_CHECKOUT = 1010; // 收费员已签退

	// 卡操作错误码
	public static final int ERRCODE_PCARD_NOT_EXIST = 4001; // 缴费卡不存在
	public static final int ERRCODE_PCARD_NOT_BELONG_TO_COLLECTOR = 4002; // 缴费卡不属于当前收费员
	public static final int ERRCODE_PCARD_UNSOLD = 4003; // 缴费卡未出售
	public static final int ERRCODE_PCARD_ALREADY_SOLD = 4004; // 缴费卡已经出售
	public static final int ERRCODE_PCARD_ALREADY_CANNELED = 4005; // 缴费卡已经作废
	public static final int ERRCODE_PCARD_LOST = 4006; // 缴费卡已挂失
	public static final int ERRCODE_PCARD_BALANCE_NOT_ENOUGH = 4007; // 缴费卡余额不足
	public static final int ERRCODE_PCARD_CANNOT_BIND = 4008; // 收费员卡不能绑定付费
	public static final int ERRCODE_RFCARD_NOT_EXIST = 4011; // 充值卡不存在
	public static final int ERRCODE_RFCARD_NOT_BELONG_TO_COLLECTOR = 4012; // 充值卡不属于当前收费员
	public static final int ERRCODE_RFCARD_UNSOLD = 4013; // 充值卡未出售
	public static final int ERRCODE_RFCARD_ALREADY_SOLD = 4014; // 充值卡已经出售
	public static final int ERRCODE_RFCARD_ALREADY_CANNELED = 4015; // 充值卡已经作废
	public static final int ERRCODE_RFCARD_ALREADY_REFILLED = 4016; // 充值卡已充值
	public static final int ERRCODE_RFCARD_WRONG_PASSWORD = 4017; // 充值密码错误
	public static final int ERRCODE_RFCARD_WRONG_TYPE = 4018; // 缴费卡信息错误
	public static final int ERRCODE_DCARD_NOT_EXIST = 4019; // 车主卡不存在
	public static final int ERRCODE_DRIVER_ACCOUNT_NOT_EXIST = 4020; // 车主帐号不存在
	public static final int ERRCODE_CARD_IS_LOCKED = 4021; // 卡已锁定
	public static final int ERRCODE_DRIVER_ACCOUNT_NOT_VALID = 4022; // 车主帐号无效

	// 停车
	public static final int ERRCODE_BERTH_NOT_EXIST = 5001; // 泊位不存在
	public static final int ERRCODE_BERTH_NO_PARKING = 5002; // 泊位上没有车辆
	public static final int ERRCODE_BERTH_USERING = 5003; // 泊位被占用
	public static final int ERRCODE_BERTH_PAY_OVERTIME = 5004;
	// 支付
	public static final int ERRCODE_CHARGECHANNEL_NOT_EXIST = 7001; // 支付方式不存在
	public static final int ERRCODE_CHARGECHANNEL_NOT_SETTING = 7002; // 没有设置支付方式
	public static final int ERRCODE_CHARGECHANNEL_NOT_MATCH = 7003; // 没有设置支付方式
	public static final int ERRCODE_ORDER_ALREADY_PAY = 7004; // 订单已经支付
	public static final int ERRCODE_ORDER_NOT_EXIST = 7005; // 订单不存在
	public static final int ERRCODE_PAY_INPROCESS = 7006; // 支付处理中
	public static final int ERRCODE_PAY_ERR = 7007; // 下单支付失败
	public static final int ERRCODE_PAY_WAIT_PAY = 7008; // 等待用户付款
	public static final int ERRCODE_PAY_CLOSED = 7009; // 交易已经关闭

	public static final int ERRCODE_UNKNOWN_ERR = 9999; // 未知错误

	protected int respCode; // 错误码

	public ResponseMessage()
	{

	}

	public ResponseMessage(int respCode)
	{

		this.respCode = respCode;
	}

	public void setRespCode(int respCode)
	{

		this.respCode = respCode;
	}

	public int getRespCode()
	{

		return respCode;
	}

	/**
	 * 将响应消息对象转换为响应字符串返回
	 */
	/*
	 * public String convertToRespString() { XmlBody xmlBody = new XmlBody();
	 * xmlBody.appendParam("errCode", respCode); xmlBody.appendParam("errMsg",
	 * getErrorMessage(respCode));
	 * 
	 * logger.debug(xmlBody.toString());
	 * 
	 * return xmlBody.toString(); }
	 */

	/**
	 * 根据已定义的错误提示编码获取错误提示消息(待补充完整）
	 * 
	 * @param errCode
	 * @return
	 */
	public static String getErrorMessage(int errCode)
	{

		switch (errCode)
		{
		case ERRCODE_UNINITIALIZE:
			return "未初始化";
		case ERRCODE_SUCCESS:
			return "操作成功";
		case ERRCODE_INVALID_COMMAND:
			return "非法命令";
		case ERRCODE_INVALID_PARAM:
			return "参数格式不正确";
		case ERRCODE_UNLOGIN:
			return "登陆已失效，请重新登录";
		case ERRCODE_INVALID_LOGINKEY:
			return "登录验证码不存在或不正确";
		case ERRCODE_INVALID_TERMIANL:
			return "没有此终端";
		case ERRCODE_INVALID_USERORPASSWORD:
			return "账号或密码不正确";
		case ERRCODE_NOCOLLECTORDUTY:
			return "没有排班";
		case ERRCODE_ALREADY_CHECKIN:
			return "收费员在该停车已签到";
		case ERRCODE_ALREADY_LOGOUT:
			return "收费员已经登出设备";
		case ERRCODE_PARKINGSITE_CHECKIN:
			return "停车点已经被其他收费员签到";
		case ERRCODE_INVALID_OLDPASSWORD:
			return "旧密码不正确";
		case ERRCODE_TERMINAL_NOTASSIGNED:
			return "终端w";
		case ERRCODE_NOT_DUTYPARKINGSITE:
			return "非值班停车点";
		case ERRCODE_ALREADY_CHECKOUT:
			return "收费员已签退";
		case ERRCODE_PCARD_NOT_EXIST:
			return "缴费卡不存在";
		case ERRCODE_PCARD_NOT_BELONG_TO_COLLECTOR:
			return "缴费卡不属于当前收费员";
		case ERRCODE_PCARD_UNSOLD:
			return "缴费卡未出售";
		case ERRCODE_PCARD_ALREADY_SOLD:
			return "缴费卡已经出售";
		case ERRCODE_PCARD_ALREADY_CANNELED:
			return "缴费卡已经作废";
		case ERRCODE_PCARD_LOST:
			return "缴费卡已挂失";
		case ERRCODE_DCARD_NOT_EXIST:
			return "车主卡不存在";
		case ERRCODE_DRIVER_ACCOUNT_NOT_EXIST:
			return "车主账户不存在";
		case ERRCODE_DRIVER_ACCOUNT_NOT_VALID:
			return "车主账户无效";
		case ERRCODE_PCARD_BALANCE_NOT_ENOUGH:
			return "卡余额不足";
		case ERRCODE_RFCARD_NOT_EXIST:
			return "充值卡不存在";
		case ERRCODE_RFCARD_NOT_BELONG_TO_COLLECTOR:
			return "充值卡不属于当前收费员";
		case ERRCODE_RFCARD_UNSOLD:
			return "充值卡未出售";
		case ERRCODE_RFCARD_ALREADY_SOLD:
			return "充值卡已经出售";
		case ERRCODE_RFCARD_ALREADY_CANNELED:
			return "充值卡已经作废";
		case ERRCODE_RFCARD_ALREADY_REFILLED:
			return "充值卡已充值";
		case ERRCODE_CARD_IS_LOCKED:
			return "卡已锁定";
		case ERRCODE_RFCARD_WRONG_PASSWORD:
			return "充值密码错误";
		case ERRCODE_BERTH_NOT_EXIST:
			return "泊位不存在";
		case ERRCODE_BERTH_USERING:
			return "泊位被占用";
		case ERRCODE_BERTH_NO_PARKING:
			return "该泊位上没有停车";
		case ERRCODE_BERTH_PAY_OVERTIME:
			return "付费超时，请重新查询停车费";
		case ERRCODE_CHARGECHANNEL_NOT_EXIST:
			return "支付方式不存在";
		case ERRCODE_ORDER_ALREADY_PAY:
			return "订单已支付";
		case ERRCODE_ORDER_NOT_EXIST:
			return "订单不存在";
		case ERRCODE_PAY_CLOSED:
			return "交易已经关闭";
		case ERRCODE_PAY_ERR:
			return "下单支付失败";
		case ERRCODE_PAY_INPROCESS:
			return "下单成功，支付处理中";
		case ERRCODE_PAY_WAIT_PAY:
			return "等待用户付款中";
		case ERRCODE_UNKNOWN_ERR:
			return "未知错误";
		case ERRCODE_HANDSET_BROKEN:
			return "此终端已损坏，无法登录";
		case ERRCODE_NEWWORD:
			return "网络故障，请待网络良好位置重试";
		case ERRCODE_RFCARD_WRONG_TYPE:
			return "缴费卡信息错误";
		case ERRCODE_VERSION_NEW:
			return "当前版本是最新版本";
		case ERRCODE_VERSION_TYPE_NULL:
			return "当前设备未识别";
		case ERRCODE_NOT_OWEFEERECORD:
			return "没有欠费记录";
		case ERRCODE_NOT_PARKRECORD:
			return "没有停车记录";
		default:
			return "未知" + " - " + errCode;
		}
	}
}
