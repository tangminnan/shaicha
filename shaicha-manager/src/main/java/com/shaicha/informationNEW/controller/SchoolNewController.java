package com.shaicha.informationNEW.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.shaicha.informationNEW.domain.SchoolNewDO;
import com.shaicha.informationNEW.service.SchoolNewService;
import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.common.utils.ShiroUtils;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 11:44:53
 */
 
@Controller
@RequestMapping("/information/school")
public class SchoolNewController {
	@Autowired
	private SchoolNewService schoolService;
	
	@GetMapping()
	@RequiresPermissions("information:school:school")
	String School(){
	    return "informationNEW/school/school";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:school:school")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        if(!ShiroUtils.getUser().getUsername().equals("admin")){
        	query.put("sysId", ShiroUtils.getUserId());
        }
		List<SchoolNewDO> schoolList = schoolService.list(query);
		int total = schoolService.count(query);
		PageUtils pageUtils = new PageUtils(schoolList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:school:add")
	String add(){
	    return "informationNEW/school/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:school:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SchoolNewDO school = schoolService.get(id);
		model.addAttribute("school", school);
	    return "informationNEW/school/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:school:add")
	public R save( SchoolNewDO school){
		school.setOrgcode("00000");
		school.setEnabledstatus(1);
		school.setCreatedate(new Date());
		school.setOrgtype(2);
		school.setIspublic(0);
		school.setSysId(ShiroUtils.getUserId());
		if(schoolService.save(school)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:school:edit")
	public R update( SchoolNewDO school){
		schoolService.update(school);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:school:remove")
	public R remove( Integer id){
		SchoolNewDO school = new SchoolNewDO();
		school.setId(id);
		school.setEnabledstatus(0);
		schoolService.update(school);
		//if(schoolService.remove(id)>0){
		return R.ok();
		//}
		//return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:school:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		schoolService.batchRemove(ids);
		return R.ok();
	}
		
	
}
