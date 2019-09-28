package com.shaicha.users.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.users.domain.UserDO;
import com.shaicha.users.service.UserService;




/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-09-27 10:18:38
 */
 

@Controller("UserController")
@RequestMapping("/information/users")
public class UserController{
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("information:user:user")
	String User(){
	    return "users/user";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:user:user")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserDO> userList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtils = new PageUtils(userList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:user:add")
	String add(){
	    return "users/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:user:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		UserDO user = userService.get(id);
		model.addAttribute("user", user);
	    return "users/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:user:add")
	public R save( UserDO user){	
		if(userService.save(user)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:user:edit")
	public R update( UserDO user){
		userService.update(user);
		return R.ok();
	}
		

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:user:remove")
	public R remove( Long id){
		
		UserDO user = new UserDO();
        user.setId(id);
        user.setDeleteFlag(0);
		userService.update(user);
		
//		if(userService.remove(id)>0){
//			return R.ok();
//		}	        	
//		return R.error();
		return R.ok();
		
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:user:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userService.batchRemove(ids);
		return R.ok();
	}


}
