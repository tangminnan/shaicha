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
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.service.ResultEyesightService;

/**
 * 视力检查
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/information/resultEyesight")
public class ResultEyesightController {
	@Autowired
	private ResultEyesightService resultEyesightService;
	
	@GetMapping()
	@RequiresPermissions("information:resultEyesight:resultEyesight")
	String ResultEyesight(){
	    return "information/resultEyesight/resultEyesight";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultEyesight:resultEyesight")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultEyesightDO> resultEyesightList = resultEyesightService.list(query);
		int total = resultEyesightService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyesightList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultEyesightDO> resultEyesightList = resultEyesightService.list(params);
		int total = resultEyesightService.count(params);
		PageUtils pageUtils = new PageUtils(resultEyesightList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultEyesight:add")
	String add(){
	    return "information/resultEyesight/add";
	}

	@GetMapping("/edit/{tEyesightId}")
	@RequiresPermissions("information:resultEyesight:edit")
	String edit(@PathVariable("tEyesightId") Integer tEyesightId,Model model){
		ResultEyesightDO resultEyesight = resultEyesightService.get(tEyesightId);
		model.addAttribute("resultEyesight", resultEyesight);
	    return "information/resultEyesight/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultEyesight:add")
	public R save( ResultEyesightDO resultEyesight){
		if(resultEyesightService.save(resultEyesight)>0){
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
	public R update( ResultEyesightDO resultEyesight){
		resultEyesightService.update(resultEyesight);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultEyesight:remove")
	public R remove( Integer tEyesightId){
		if(resultEyesightService.remove(tEyesightId)>0){
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
		resultEyesightService.batchRemove(tEyesightIds);
		return R.ok();
	}
	
}
