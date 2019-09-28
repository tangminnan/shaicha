package com.shaicha.owneruser.comment;

/**
 * 短信内容模板
 * @author tmn
 * @create 2017-4-13
 */
public enum SMSContent implements SMSTemplate{

	ZHUCE("欢迎您注册「针灸」，您本次的注册验证码为：#randomCode#。请您妥善保存验证码，勿向他人泄露。","注册"),
	
	
	ZHAOHUI_LOGIN("您正在进行密码找回操作，您本次的验证码为：#randomCode#。请您妥善保存验证码，勿向他人泄露。", "找回登陆密码"),


	LOGIN("欢迎您登录「针灸」，您本次的验证码为：#randomCode#。请您妥善保存验证码，勿向他人泄露。", "登陆");
	

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
