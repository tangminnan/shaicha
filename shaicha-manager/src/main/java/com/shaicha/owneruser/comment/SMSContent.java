package com.shaicha.owneruser.comment;

/**
 * 短信内容模板
 * @author tmn
 * @create 2017-4-13
 */
public enum SMSContent implements SMSTemplate{

	ZHUCE("欢迎您注册「银网物业」，您本次的注册验证码为：#randomCode#。请您妥善保存用户名及密码信息，勿向他人泄露。","注册"),
	
	
	ZHAOHUI_LOGIN("您正在进行密码找回操作，您本次的验证码为：#randomCode#。请您妥善保存用户名及密码信息，勿向他人泄露。", "找回登陆密码"),
	
	CUIKUAN("您当前余额已不足，请及时缴费！", "催款");
	

	private String content;
	private String type;

	private SMSContent(String content, String type) {
		this.content = content;
		this.type = type;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public String getType() {
		return type;
	}
}
