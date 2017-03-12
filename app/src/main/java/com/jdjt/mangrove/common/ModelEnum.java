package com.jdjt.mangrove.common;

import java.util.Arrays;
import java.util.List;

/**
 * 针对不同接口的 模块定义
 * @author wangmingyu
 *
 */
public enum ModelEnum {

	/**
	 *用户模块
	 * 2014-6-16下午3:13:31 
	 * @author wangmingyu
	 */
	UUM("uum"),
	/**
	 *酒店资源 housekeeper
	 * 2014-6-16下午3:13:43 
	 * @author wangmingyu
	 */
	TK("housekeeper"),
	/**
	 *卡信息
	 * 2014-6-16下午3:13:58 
	 * @author wangmingyu
	 */
	CARD("card"),
	/**
	 * 餐饮
	 * 2014-6-16下午3:14:11 
	 * @author wangmingyu
	 */
	CATERING("catering"),
	
	/**
	 * 酒店服务（服务接口包括 ：酒店、服务、消息中心）
	 */
	HOTELSERVICE("hotelservice"),
	/**
	 * 二维码
	 */
	QRCODE("quickmark"),
	/**
	 * 商业街
	 * */
	MALL("mall"),

	/**
	 * 馅饼计划
	 */

	PIEPRO("piepro"),
	/**
	 * 支付前缀
	 * @author wangmingyu
	 * 2014-9-26
	 */
	PAY("pay");
	private String code;
	private ModelEnum(String code) {
		this.code = code;
	}
	
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}




	/** 
	* @Title: getAll 
	* @Description: TODO(获得所有的对象数据) 
	* @param @return    设定文件 
	* @return List<ModelEnum>    返回类型 
	* @throws 
	* @author 王明雨
	* @date 2013-5-30 上午09:58:31 
	*/ 
	public static List<ModelEnum> getAll() {
		return Arrays.asList(ModelEnum.values());
	}

	
	/** 
	* @Title: getTransactionTypesEnum 
	* @Description: TODO(根据code值获得对应的渠道类型) 
	* @param @param code
	* @param @return    设定文件 
	* @return ModelEnum    返回类型 
	* @throws 
	* @author 王明雨
	* @date 2013-5-30 上午09:58:12 
	*/ 
	public static ModelEnum  getTransactionTypesEnum(String code) {
		code = code.trim();
		for (ModelEnum transactionTypesEnum : values()) {
			if (code.equals(transactionTypesEnum.getCode())) {
				return transactionTypesEnum;
			}
		}
		return null;
	}
	
	
}
