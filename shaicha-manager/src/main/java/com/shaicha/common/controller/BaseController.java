package com.shaicha.common.controller;

import org.springframework.stereotype.Controller;

import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.system.domain.UserDO;
import com.shaicha.system.domain.UserToken;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}