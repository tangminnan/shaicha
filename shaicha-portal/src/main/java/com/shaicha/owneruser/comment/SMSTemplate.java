package com.shaicha.owneruser.comment;

/**
 * 
* @ClassName: SMSContent  
* @Description:  短信模板接口 <br/>
* 				已有子类，默认：SMSContent； 
* @author TMN
* @date 2017年4月11日  
*
 */
public interface SMSTemplate {
	/**
	 * 短信模板中默认验证码标示：#randomCode#
	 */
	public static String RANDOMCODE = "#randomCode#";
	/**
	 * 短信模板中默认验证码标示：#number#
	 */
	public static String NUMBER = "#number#";
	/**
	 * 模板中需要替换的变量标示：${###}
	 */
	public static String VARIABLECODE = "###%%%###";
	/**
	 * 模板中需要替换的变量标示：#MONEY#
	 */
	public static String MONEY = "#money#";
	/**
	 * 获取需要发送的短信的类型
	 * @return
	 */
	public String getType();
	/**
	 * 获取需要发送的短信的类型
	 * @return
	 */
	public String getContent();
	
}
