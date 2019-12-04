package com.shaicha.information.controller;

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
import com.shaicha.information.domain.ResultVisibilityDO;
import com.shaicha.information.service.ResultVisibilityService;

/**
 * 视功能,眼节前后
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/information/resultVisibility")
public class ResultVisibilityController {
	@Autowired
	private ResultVisibilityService resultVisibilityService;
	
	@GetMapping()
	@RequiresPermissions("information:resultVisibility:resultVisibility")
	String ResultVisibility(){
	    return "information/resultVisibility/resultVisibility";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultVisibility:resultVisibility")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultVisibilityDO> resultVisibilityList = resultVisibilityService.list(query);
		int total = resultVisibilityService.count(query);
		PageUtils pageUtils = new PageUtils(resultVisibilityList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{identityCard}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("identityCard") String id,@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		query.put("identityCard", id);
		List<ResultVisibilityDO> resultVisibilityList = resultVisibilityService.list(query);
		int total = resultVisibilityService.count(query);
		PageUtils pageUtils = new PageUtils(resultVisibilityList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultVisibility:add")
	String add(){
	    return "information/resultVisibility/add";
	}

	@GetMapping("/edit/{tVisibilityId}")
	@RequiresPermissions("information:resultVisibility:edit")
	String edit(@PathVariable("tVisibilityId") Integer tVisibilityId,Model model){
		ResultVisibilityDO resultVisibility = resultVisibilityService.get(tVisibilityId);
		model.addAttribute("resultVisibility", resultVisibility);
	    return "information/resultVisibility/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultVisibility:add")
	public R save( ResultVisibilityDO resultVisibility){
		if(resultVisibilityService.save(resultVisibility)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultVisibility:edit")
	public R update( ResultVisibilityDO resultVisibility){
		resultVisibilityService.update(resultVisibility);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultVisibility:remove")
	public R remove( Integer tVisibilityId){
		if(resultVisibilityService.remove(tVisibilityId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultVisibility:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tVisibilityIds){
		resultVisibilityService.batchRemove(tVisibilityIds);
		return R.ok();
	}
	
}
