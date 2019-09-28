package com.shaicha.common.controller;

import org.springframework.stereotype.Controller;

import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.owneruser.domain.OwnerUserDO;
import com.shaicha.system.domain.UserToken;

@Controller
public class BaseController {
	public OwnerUserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getChectorId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	/*public Long getforIds() {
		return getUser().getUserId();
	}*/
}