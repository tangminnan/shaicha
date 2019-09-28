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
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.service.ResultEyeaxisService;

/**
 * 眼轴长度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/information/resultEyeaxis")
public class ResultEyeaxisController {
	@Autowired
	private ResultEyeaxisService resultEyeaxisService;
	
	@GetMapping()
	@RequiresPermissions("information:resultEyeaxis:resultEyeaxis")
	String ResultEyeaxis(){
	    return "information/resultEyeaxis/resultEyeaxis";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultEyeaxis:resultEyeaxis")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultEyeaxisDO> resultEyeaxisList = resultEyeaxisService.list(query);
		int total = resultEyeaxisService.count(query);
		PageUtils pageUtils = new PageUtils(resultEyeaxisList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultEyeaxisDO> resultEyeaxisList = resultEyeaxisService.list(params);
		int total = resultEyeaxisService.count(params);
		PageUtils pageUtils = new PageUtils(resultEyeaxisList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultEyeaxis:add")
	String add(){
	    return "information/resultEyeaxis/add";
	}

	@GetMapping("/edit/{tEyeaxisId}")
	@RequiresPermissions("information:resultEyeaxis:edit")
	String edit(@PathVariable("tEyeaxisId") Integer tEyeaxisId,Model model){
		ResultEyeaxisDO resultEyeaxis = resultEyeaxisService.get(tEyeaxisId);
		model.addAttribute("resultEyeaxis", resultEyeaxis);
	    return "information/resultEyeaxis/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultEyeaxis:add")
	public R save( ResultEyeaxisDO resultEyeaxis){
		if(resultEyeaxisService.save(resultEyeaxis)>0){
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
	public R update( ResultEyeaxisDO resultEyeaxis){
		resultEyeaxisService.update(resultEyeaxis);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultEyeaxis:remove")
	public R remove( Integer tEyeaxisId){
		if(resultEyeaxisService.remove(tEyeaxisId)>0){
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
		resultEyeaxisService.batchRemove(tEyeaxisIds);
		return R.ok();
	}
	
}
