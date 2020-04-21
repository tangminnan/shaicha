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
import com.shaicha.informationNEW.domain.ResultEyesightNewDO;
import com.shaicha.informationNEW.service.ResultEyesightNewService;

/**
 * 视力检查
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/informationNEW/resultEyesight")
public class ResultEyesightNewController {
	@Autowired
	private ResultEyesightNewService resultEyesightNewService;
	
	@GetMapping()
	@RequiresPermissions("information:resultEyesight:resultEyesight")
	String ResultEyesight(){
	    return "informationNEW/resultEyesight/resultEyesight";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultEyesight:resultEyesight")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultEyesightNewDO> resultEyesightList = resultEyesightNewService.list(query);
		int total = resultEyesightNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyesightList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{identityCard}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("identityCard") String id,@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		query.put("identityCard", id);
		List<ResultEyesightNewDO> resultEyesightList = resultEyesightNewService.list(query);
		int total = resultEyesightNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyesightList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultEyesight:add")
	String add(){
	    return "informationNEW/resultEyesight/add";
	}

	@GetMapping("/edit/{tEyesightId}")
	@RequiresPermissions("information:resultEyesight:edit")
	String edit(@PathVariable("tEyesightId") Integer tEyesightId,Model model){
		ResultEyesightNewDO resultEyesight = resultEyesightNewService.get(tEyesightId);
		model.addAttribute("resultEyesight", resultEyesight);
	    return "informationNEW/resultEyesight/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultEyesight:add")
	public R save( ResultEyesightNewDO resultEyesight){
		if(resultEyesightNewService.save(resultEyesight)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultEyesight:edit")
	public R update( ResultEyesightNewDO resultEyesight){
		resultEyesightNewService.update(resultEyesight);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultEyesight:remove")
	public R remove( Integer tEyesightId){
		if(resultEyesightNewService.remove(tEyesightId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultEyesight:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tEyesightIds){
		resultEyesightNewService.batchRemove(tEyesightIds);
		return R.ok();
	}
	
}
