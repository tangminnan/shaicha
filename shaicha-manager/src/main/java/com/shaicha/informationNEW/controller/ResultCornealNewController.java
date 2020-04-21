package com.shaicha.informationNEW.controller;

import java.util.ArrayList;
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
import com.shaicha.informationNEW.domain.ResultCornealNewDO;
import com.shaicha.informationNEW.domain.ResultOptometryNewDO;
import com.shaicha.informationNEW.service.ResultCornealNewService;
import com.shaicha.informationNEW.service.ResultOptometryNewService;

/**
 * 角膜曲率详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/informationNEW/resultCorneal")
public class ResultCornealNewController {
	@Autowired
	private ResultCornealNewService resultCornealNewService;
	@Autowired
	private ResultOptometryNewService resultOptometryNewService;
	
	@GetMapping()
	@RequiresPermissions("information:resultCorneal:resultCorneal")
	String ResultCorneal(){
	    return "informationNEW/resultCorneal/resultCorneal";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultCorneal:resultCorneal")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultCornealNewDO> resultCornealList = resultCornealNewService.list(query);
		int total = resultCornealNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultCornealList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		List<ResultCornealNewDO> resultCornealList = new ArrayList<ResultCornealNewDO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultOptometryNewDO> list = resultOptometryNewService.list(params);
		for (ResultOptometryNewDO resultOptometryDO : list) {
			Integer tOptometryId = resultOptometryDO.gettOptometryId();
			resultCornealList.addAll(resultCornealNewService.queryByToptometryTd(tOptometryId));
		
		}
		int size = resultCornealList.size();
		PageUtils pageUtils = new PageUtils(resultCornealList, size);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultCorneal:add")
	String add(){
	    return "informationNEW/resultCorneal/add";
	}

	@GetMapping("/edit/{tCornealId}")
	@RequiresPermissions("information:resultCorneal:edit")
	String edit(@PathVariable("tCornealId") Integer tCornealId,Model model){
		ResultCornealNewDO resultCorneal = resultCornealNewService.get(tCornealId);
		model.addAttribute("resultCorneal", resultCorneal);
	    return "informationNEW/resultCorneal/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultCorneal:add")
	public R save( ResultCornealNewDO resultCorneal){
		if(resultCornealNewService.save(resultCorneal)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultCorneal:edit")
	public R update( ResultCornealNewDO resultCorneal){
		resultCornealNewService.update(resultCorneal);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultCorneal:remove")
	public R remove( Integer tCornealId){
		if(resultCornealNewService.remove(tCornealId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultCorneal:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tCornealIds){
		resultCornealNewService.batchRemove(tCornealIds);
		return R.ok();
	}
	
}
