package com.shaicha.informationNEW.controller;

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
import com.shaicha.informationNEW.domain.ResultOptometryNewDO;
import com.shaicha.informationNEW.service.ResultOptometryNewService;

/**
 * 验光数据表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/informationNEW/resultOptometry")
public class ResultOptometryNewController {
	@Autowired
	private ResultOptometryNewService resultOptometryNewService;
	
	@GetMapping()
	@RequiresPermissions("information:resultOptometry:resultOptometry")
	String ResultOptometry(){
	    return "informationNEW/resultOptometry/resultOptometry";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultOptometry:resultOptometry")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultOptometryNewDO> resultOptometryList = resultOptometryNewService.list(query);
		int total = resultOptometryNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultOptometryList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultOptometryNewDO> resultOptometryList = resultOptometryNewService.list(params);
		int total = resultOptometryNewService.count(params);
		PageUtils pageUtils = new PageUtils(resultOptometryList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultOptometry:add")
	String add(){
	    return "informationNEW/resultOptometry/add";
	}

	@GetMapping("/edit/{tOptometryId}")
	@RequiresPermissions("information:resultOptometry:edit")
	String edit(@PathVariable("tOptometryId") Integer tOptometryId,Model model){
		ResultOptometryNewDO resultOptometry = resultOptometryNewService.get(tOptometryId);
		model.addAttribute("resultOptometry", resultOptometry);
	    return "informationNEW/resultOptometry/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultOptometry:add")
	public R save( ResultOptometryNewDO resultOptometry){
		if(resultOptometryNewService.save(resultOptometry)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultOptometry:edit")
	public R update( ResultOptometryNewDO resultOptometry){
		resultOptometryNewService.update(resultOptometry);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultOptometry:remove")
	public R remove( Integer tOptometryId){
		if(resultOptometryNewService.remove(tOptometryId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultOptometry:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tOptometryIds){
		resultOptometryNewService.batchRemove(tOptometryIds);
		return R.ok();
	}
	
}
