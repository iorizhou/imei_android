package com.imei666.android.mvp.model.dto;

//订单
public class OrderDTO {
	private long id;
	private String itemName;
	private long itemId;
	private String phoneNum;
	private String message;  //给客服的留言
	private long totalPrice;  //总价
	private int buyCount;  //购买数量
	private long djRedPacketId; //订金红包id
	private long wkRedPacketId; //尾款红包id
	private long yyRedPacketId;  //医院红包 
	
	private long djTotalCount;  //订金总金额 = 该项目的订金 乘 购买数量
	private long needPayCount;   //需要支付的订金
	private long payedCount;   //已支付总金额
	private long djDiscount;   //订金优惠金额
	private long wkCount;  //尾款 
	private int payStatus;   //订金支付状态  0为未支持  1为已支付
	private String payOrderId;   //订单如果支付了 则需要显示在微信或支付宝上的支付订单ID
	private int payChannel;   //支付渠道  0为微信  1为支付宝
	private int orderStatus;    //订单状态  0为创建成功  1为已支付 2为已消费  3为退款或过期
	private String orderInvalidTime;   //订单失效时间   如果一个订单，长时间未付款，则会将其置为状态3 即过期关闭状态.默认为30分钟
	private String consumeCode;  
	private long userId;
	private long consumeUserId;   //进行订单核销的医院操作人员ID。
	private String createDate;  //订单创建时间
	
	
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public long getNeedPayCount() {
		return needPayCount;
	}
	public void setNeedPayCount(long needPayCount) {
		this.needPayCount = needPayCount;
	}
	public long getConsumeUserId() {
		return consumeUserId;
	}
	public void setConsumeUserId(long consumeUserId) {
		this.consumeUserId = consumeUserId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getPayedCount() {
		return payedCount;
	}
	public void setPayedCount(long payedCount) {
		this.payedCount = payedCount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public long getDjRedPacketId() {
		return djRedPacketId;
	}
	public void setDjRedPacketId(long djRedPacketId) {
		this.djRedPacketId = djRedPacketId;
	}
	public long getWkRedPacketId() {
		return wkRedPacketId;
	}
	public void setWkRedPacketId(long wkRedPacketId) {
		this.wkRedPacketId = wkRedPacketId;
	}
	public long getYyRedPacketId() {
		return yyRedPacketId;
	}
	public void setYyRedPacketId(long yyRedPacketId) {
		this.yyRedPacketId = yyRedPacketId;
	}
	public long getDjTotalCount() {
		return djTotalCount;
	}
	public void setDjTotalCount(long djTotalCount) {
		this.djTotalCount = djTotalCount;
	}
	public long getDjDiscount() {
		return djDiscount;
	}
	public void setDjDiscount(long djDiscount) {
		this.djDiscount = djDiscount;
	}
	public long getWkCount() {
		return wkCount;
	}
	public void setWkCount(long wkCount) {
		this.wkCount = wkCount;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public int getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(int payChannel) {
		this.payChannel = payChannel;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderInvalidTime() {
		return orderInvalidTime;
	}
	public void setOrderInvalidTime(String orderInvalidTime) {
		this.orderInvalidTime = orderInvalidTime;
	}
	
	public String getConsumeCode() {
		return consumeCode;
	}
	public void setConsumeCode(String consumeCode) {
		this.consumeCode = consumeCode;
	}
	public OrderDTO(long id,String itemName, long itemId, String phoneNum, String message, long totalPrice, int buyCount,
			long djRedPacketId, long wkRedPacketId, long yyRedPacketId, long djTotalCount, long djDiscount,
			long wkCount, int payStatus, String payOrderId, int payChannel, int orderStatus, String orderInvalidTime,long payedCount,String consumeCode,long userId,long consumeUserId,long needPayCount,String createDate) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.itemId = itemId;
		this.phoneNum = phoneNum;
		this.message = message;
		this.totalPrice = totalPrice;
		this.buyCount = buyCount;
		this.djRedPacketId = djRedPacketId;
		this.wkRedPacketId = wkRedPacketId;
		this.yyRedPacketId = yyRedPacketId;
		this.djTotalCount = djTotalCount;
		this.djDiscount = djDiscount;
		this.wkCount = wkCount;
		this.payStatus = payStatus;
		this.payOrderId = payOrderId;
		this.payChannel = payChannel;
		this.orderStatus = orderStatus;
		this.orderInvalidTime = orderInvalidTime;
		this.payedCount = payedCount;
		this.consumeCode = consumeCode;
		this.userId = userId;
		this.consumeUserId = consumeUserId;
		this.needPayCount = needPayCount;
		this.createDate = createDate;
	}
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
