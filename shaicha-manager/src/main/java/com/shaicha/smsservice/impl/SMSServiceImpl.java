package com.shaicha.smsservice.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shaicha.common.redis.shiro.RedisManager;
import com.shaicha.owneruser.comment.DateUtil;
import com.shaicha.owneruser.comment.MD5;
import com.shaicha.owneruser.comment.SMSContent;
import com.shaicha.owneruser.comment.SMSPlatform;
import com.shaicha.owneruser.comment.SMSTemplate;
import com.shaicha.smsservice.service.ISMSService;


/**
 * 短信通道
 * @author tmn
 * @create 2017-4-13
 */
@Service("sMSService")
public class SMSServiceImpl implements ISMSService{
	
	private Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);
	private static String RANDOM_CODE = "SYS_RANDOM_CODE";
	private static String RANDOM_CODE_DAY = "SYS_RANDOM_CODE_DAY";
	private static String RANDOM_CODE_COUNT = "SYS_RANDOM_CODE_COUNT";
	private static int RANDOM_CODE_COUNT_LIMIT = 3;
	private static int RANDOM_CODE_COUNT_MAX = 20;
	private static String validateKey = "sys.sms.validate.code";
	private static HashSet<String> whiteList = new HashSet<String>();
	
	@Resource
	 RedisManager redisManager = new RedisManager();
	
	static{
		  String _limit="3";
		  String _max = "10";
		  if(StringUtils.isNotBlank(_limit)){
			  try{
				  RANDOM_CODE_COUNT_LIMIT = Integer.parseInt(_limit);
			  }catch(Exception exception){
				  exception.printStackTrace();
			  }
		  }
		  if(StringUtils.isNotBlank(_max)){
			  try{
				  RANDOM_CODE_COUNT_MAX = Integer.parseInt(_max);
			  }catch(Exception exception){
				  exception.printStackTrace();
			  }
		  }
	}
	
	@Override
	public boolean sendTongzhi(SMSPlatform gateway, String mobile,SMSTemplate contentType,Object... objs) {
		String content = contentType.getContent();
		if(objs!=null && objs.length>0){
			for(Object s: objs){
				content = content.replaceFirst(SMSTemplate.VARIABLECODE, s+"");
			}
		}
		content = gateway.qianming +  content;
		System.out.println("==gateway=="+gateway+"==mobile=="+mobile+"==content=="+content);
		return sendSMS(gateway, mobile, content);
	}
	
	public int sendNumber(String key){
		int number = 10;
		/*Object obj = redisManager.get(key);
		if(obj == null){*/
			number += (new Random()).nextInt(5);
		/*}else{
			number = Integer.parseInt(obj.toString()) + ((new Random()).nextInt(5)+1);
		}*/
		return number;
	}
	
	public Map<String, Object> sendCodeNumber(SMSPlatform gateway, String mobile,SMSTemplate contentType){
		return sendCodeNumber(gateway, mobile, contentType, (new Random()).nextInt(899999) + 100000);// 最大值位999999
	}
	
	public Map<String, Object> sendCodeNumber(SMSPlatform gateway, String mobile,SMSTemplate contentType,int randomCode){
		Map<String, Object> map = new HashMap<String, Object>();
		setRandomCode(mobile, randomCode+"");
		//String key = mobile+DateUtil.dateToString1(new Date());
		//int number = sendNumber(key);
		//redisCache.set(key, number, (1000*60*60*24));
		logger.info("手机号："+mobile+"您的验证码为："+randomCode);
		//logger.info("手机号："+mobile+"您的序号为："+number);
		String content = contentType.getContent().replaceAll(SMSTemplate.RANDOMCODE, randomCode+"");//.replaceAll(SMSTemplate.NUMBER, number+"")
		content= gateway.qianming + content;
		if(sendSMS(gateway, mobile, content)){
			logger.info("您的验证码是:"+randomCode);
			//添加验证码到redis中
			map.put("randomCode", randomCode);
			//map.put("number", number);
			return map;
		}
		return null;
	}
	
	@Override
	public String sendCode(SMSPlatform gateway, String mobile, SMSTemplate contentType) {
		return sendCode(gateway, mobile, contentType, (new Random()).nextInt(899999) + 100000);// 最大值位999999
	}
	
	
	
	@Override
	public String sendCode(SMSPlatform gateway, String mobile, SMSTemplate contentType,int randomCode) {
		//更新redis
		setRandomCode(mobile, randomCode+"");
		logger.info("手机号："+mobile+"您的验证码为："+randomCode);
		String content = contentType.getContent().replaceAll(SMSTemplate.RANDOMCODE, randomCode+"");
		content= gateway.qianming + content;
		if(sendSMS(gateway, mobile, content)){
			logger.info("您的验证码是:"+randomCode);
			//添加验证码到redis中
			
			return randomCode+"";
		}
		return "";
	}
	
	//发送短信
	private boolean sendSMS(final SMSPlatform gateway, final String mobile, final String content) {
		boolean isSendSuccess = false;
		try {
			
				Date date = new Date();  
		        String send = null;  
				DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
				send = sdf.format(date); 
				String password = MD5.MD5(MD5.MD5("123qaz789")+send);
				String kuaiyongyun = "http://210.51.191.35:8080/eums/sms/send.do?name=bjywhy&seed="+send+"&key="+password+"&dest=#MOBILES#&content=#SMS_CONTENT#";
				kuaiyongyun =kuaiyongyun
						.replaceAll("#MOBILES#", URLEncoder.encode(mobile,"GBK"))
						.replaceAll("#SMS_CONTENT#", URLEncoder.encode(content,"GBK"))
						.replaceAll("#.*#", "");
				logger.info("kuaiyongyun->" + kuaiyongyun);
				URL smsurl = new URL(kuaiyongyun);
				URLConnection UConn = smsurl.openConnection();  
				BufferedReader breader = new BufferedReader(new InputStreamReader(UConn.getInputStream()));
				 
				String str=breader.readLine();
				str = URLDecoder.decode(str,"GBK");
				if(str.toLowerCase().startsWith("success")){
					isSendSuccess = true;
					logger.info("[快用科技]短信发送成功:"+content+"==>>"+mobile);
				}else{
					str = "[快用科技]短信发送失败。mobile===>>:"+mobile+"  内容===>>:"+content+"  失败原因："+str.split(":")[1];
					logger.info(str);
				}
		} catch (Exception ex) {
			logger.error("短信通道发送短信时,出现异常:"+ex.getMessage());
			//清除redis记录
			removeRandomCode(mobile);
		}

		return isSendSuccess;
	}
/*	// 解析下发response
	public String xmlMt(String response) {
		String result = "0";
		Document document = null;
		try {
			document = DocumentHelper.parseText(response);
		} catch (Exception e) {
			e.printStackTrace();
			result = "-250";
		}
		Element root = document.getRootElement();
		result = root.elementText("error");
		if (null == result || "".equals(result)) {
			result = "-250";
		}
		return result;
	}
	*/
	
	
	
	@Override
	public String getRandomCode(String mobile) {
		/*if(StringUtils.isNotBlank(mobile))
			if(redisCache.keyExists(RANDOM_CODE+mobile))
				return (String) redisCache.get(RANDOM_CODE+mobile);*/
		return null;
	}

	@Override
	public boolean isRandomCodeAlive(String mobile) {
		/*if(!StringUtils.isNotBlank(mobile))
			throw new IllegalArgumentException("mobile is null");*/
		return false; //redisCache.keyExists(RANDOM_CODE+mobile);
	}

	private void removeRandomCode(String mobile){
		/*//清除redis记录
		if(redisCache.keyExists(RANDOM_CODE+mobile))
			redisCache.remove(RANDOM_CODE+mobile);
		Integer count = (Integer) redisCache.get(RANDOM_CODE_COUNT+mobile);
		if(count!=null && count>0){
			redisCache.set(RANDOM_CODE_COUNT+mobile, count-1);
		}*/
	}
	/**
	 * 添加验证码到缓存中
	 *  1. 单个手机号3分钟不能超过3条 <br/>
	 *  2. 单个手机号发送短信超过10条后，记录用户IP，并限制IP段当天不能再发送短信 <br/>
	 * @return
	 */
	private void setRandomCode(String mobile, String randomCode){
	/*	Integer count = (Integer) redisCache.get(RANDOM_CODE_COUNT+mobile);
		//剩余时间到第二天0点过期
		Date now = new Date();
		Date end = DateUtil.getEndTimeOfDate(now);
		long time = end.getTime() - now.getTime();
		
		redisCache.set(RANDOM_CODE+mobile, randomCode, 60*3*1000);
		redisCache.set(RANDOM_CODE_COUNT+mobile, count==null? 1 : count+1 , time);*/
	}
	
	@Override
	public boolean isRandomOk(Integer randomcode) {
/*		Integer uuid = (Integer)redisCache.get(validateKey+WebContext.getSession().getId());
		if(uuid != null && randomcode != null && randomcode.equals(uuid)){
			return true;
		}*/
		return false;
	}
	
	public static void main(String[] args) {
		new SMSServiceImpl().sendTongzhi(SMSPlatform.shaicha,"15210858517", SMSContent.ZHUCE,"1");
 
	}

}
