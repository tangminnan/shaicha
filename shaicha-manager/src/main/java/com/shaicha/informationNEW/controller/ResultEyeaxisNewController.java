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
import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;
import com.shaicha.informationNEW.service.ResultEyeaxisNewService;

/**
 * 眼轴长度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/informationNEW/resultEyeaxis")
public class ResultEyeaxisNewController {
	@Autowired
	private ResultEyeaxisNewService resultEyeaxisNewService;
	
	@GetMapping()
	@RequiresPermissions("information:resultEyeaxis:resultEyeaxis")
	String ResultEyeaxis(){
	    return "informationNEW/resultEyeaxis/resultEyeaxis";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultEyeaxis:resultEyeaxis")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultEyeaxisNewDO> resultEyeaxisList = resultEyeaxisNewService.list(query);
		int total = resultEyeaxisNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyeaxisList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{identityCard}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("identityCard") String id,@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		query.put("identityCard", id);
		List<ResultEyeaxisNewDO> resultEyeaxisList = resultEyeaxisNewService.list(query);
		int total = resultEyeaxisNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyeaxisList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultEyeaxis:add")
	String add(){
	    return "informationNEW/resultEyeaxis/add";
	}

	@GetMapping("/edit/{tEyeaxisId}")
	@RequiresPermissions("information:resultEyeaxis:edit")
	String edit(@PathVariable("tEyeaxisId") Integer tEyeaxisId,Model model){
		ResultEyeaxisNewDO resultEyeaxis = resultEyeaxisNewService.get(tEyeaxisId);
		model.addAttribute("resultEyeaxis", resultEyeaxis);
	    return "informationNEW/resultEyeaxis/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultEyeaxis:add")
	public R save( ResultEyeaxisNewDO resultEyeaxis){
		if(resultEyeaxisNewService.save(resultEyeaxis)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultEyeaxis:edit")
	public R update( ResultEyeaxisNewDO resultEyeaxis){
		resultEyeaxisNewService.update(resultEyeaxis);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultEyeaxis:remove")
	public R remove( Integer tEyeaxisId){
		if(resultEyeaxisNewService.remove(tEyeaxisId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultEyeaxis:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tEyeaxisIds){
		resultEyeaxisNewService.batchRemove(tEyeaxisIds);
		return R.ok();
	}
	
}
