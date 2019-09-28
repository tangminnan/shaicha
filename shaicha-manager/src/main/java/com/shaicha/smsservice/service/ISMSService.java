package com.shaicha.smsservice.service;

import java.util.Map;

import com.shaicha.owneruser.comment.SMSPlatform;
import com.shaicha.owneruser.comment.SMSTemplate;

/**
 * 短信通道 <br/>
 * 发短信限制规则：<br/>
 * 	1. 单个手机号3分钟不能超过3条 <br/>
 *  2. 单个手机号发送短信超过10条后，记录用户IP，并限制IP段当天不能再发送短信 <br/>
 * 短信模板类：默认：SMSContent； 玩家平台：SMSPlayContent
 * @author thb
 * @create 2015-12-11
 */
public interface ISMSService {
	
	/**
	 * 生成序号
	 * @author wjl
	 * @update wjl
	 * @return
	 */
	public int sendNumber(String key);
	/**
	 * 发送通知类短信
	 * @param gateway
	 * @param mobile
	 * @param contentType 短信模板类，默认：SMSContent； 玩家平台：SMSPlayContent
	 * @return
	 */
	public boolean sendTongzhi(SMSPlatform gateway, String mobile,SMSTemplate contentType,Object... objs);
	/**
	 * 发送短信，发送短信是需要从reques中获取IP地址，请确保WebContext.getRequest()有值
	 * @param mobile  发送手机号
	 * @param gateway 短信发送平台
	 * @param contentType 模板
	 * @return
	 */
	public String sendCode(SMSPlatform gateway, String mobile,SMSTemplate contentType);
	
	/**
	 * 发送短信，发送短信是需要从reques中获取IP地址，请确保WebContext.getRequest()有值
	 * @param mobile  发送手机号
	 * @param gateway 短信发送平台
	 * @param contentType 模板
	 * @return
	 */
	public String sendCode(SMSPlatform gateway, String mobile,SMSTemplate contentType,int randomCode);
	
	/**
	 * @说明 返回验证码、序号
	 * 发送短信，发送短信是需要从reques中获取IP地址，请确保WebContext.getRequest()有值
	 * @param mobile  发送手机号
	 * @param gateway 短信发送平台
	 * @param contentType 模板
	 * @return
	 */
	public Map<String, Object> sendCodeNumber(SMSPlatform gateway, String mobile,SMSTemplate contentType);
	
	/**
	 * @说明 返回验证码、序号
	 * 发送短信，发送短信是需要从reques中获取IP地址，请确保WebContext.getRequest()有值
	 * @param mobile  发送手机号
	 * @param gateway 短信发送平台
	 * @param contentType 模板
	 * @return
	 */
	public Map<String, Object> sendCodeNumber(SMSPlatform gateway, String mobile,SMSTemplate contentType,int randomCode);
	

	/**
	 * 获取验证码
	 * @param mobile
	 * @return
	 */
	public String getRandomCode(String mobile) ;
	
	/**
	 * 验证码是否有效
	 * @return
	 */
	public boolean isRandomCodeAlive(String mobile);
	
	/**
	 * 验证码五分钟之内是否有效
	 * @param mobileAndCode
	 * @return
	 */
	public boolean isRandomOk(Integer randomcode);
}
