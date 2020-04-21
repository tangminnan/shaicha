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
import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;
import com.shaicha.informationNEW.service.ResultEyepressureNewService;

/**
 * 眼内压
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/informationNEW/resultEyepressure")
public class ResultEyepressureNewController {
	@Autowired
	private ResultEyepressureNewService resultEyepressureNewService;
	
	@GetMapping()
	@RequiresPermissions("information:resultEyepressure:resultEyepressure")
	String ResultEyepressure(){
	    return "informationNEW/resultEyepressure/resultEyepressure";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultEyepressure:resultEyepressure")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultEyepressureNewDO> resultEyepressureList = resultEyepressureNewService.list(query);
		int total = resultEyepressureNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyepressureList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{identityCard}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("identityCard") String id,@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		query.put("identityCard", id);
		List<ResultEyepressureNewDO> resultEyepressureList = resultEyepressureNewService.list(query);
		int total = resultEyepressureNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyepressureList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultEyepressure:add")
	String add(){
	    return "informationNEW/resultEyepressure/add";
	}

	@GetMapping("/edit/{tEyepressureId}")
	@RequiresPermissions("information:resultEyepressure:edit")
	String edit(@PathVariable("tEyepressureId") Integer tEyepressureId,Model model){
		ResultEyepressureNewDO resultEyepressure = resultEyepressureNewService.get(tEyepressureId);
		model.addAttribute("resultEyepressure", resultEyepressure);
	    return "informationNEW/resultEyepressure/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultEyepressure:add")
	public R save( ResultEyepressureNewDO resultEyepressure){
		if(resultEyepressureNewService.save(resultEyepressure)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultEyepressure:edit")
	public R update( ResultEyepressureNewDO resultEyepressure){
		resultEyepressureNewService.update(resultEyepressure);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultEyepressure:remove")
	public R remove( Integer tEyepressureId){
		if(resultEyepressureNewService.remove(tEyepressureId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultEyepressure:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tEyepressureIds){
		resultEyepressureNewService.batchRemove(tEyepressureIds);
		return R.ok();
	}
	
}
