package com.shaicha.information.controller;

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
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.service.ResultCornealService;
import com.shaicha.information.service.ResultOptometryService;

/**
 * 角膜曲率详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/information/resultCorneal")
public class ResultCornealController {
	@Autowired
	private ResultCornealService resultCornealService;
	@Autowired
	private ResultOptometryService resultOptometryService;
	
	@GetMapping()
	@RequiresPermissions("information:resultCorneal:resultCorneal")
	String ResultCorneal(){
	    return "information/resultCorneal/resultCorneal";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultCorneal:resultCorneal")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultCornealDO> resultCornealList = resultCornealService.list(query);
		int total = resultCornealService.count(query);
		PageUtils pageUtils = new PageUtils(resultCornealList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		List<ResultCornealDO> resultCornealList = new ArrayList<ResultCornealDO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultOptometryDO> list = resultOptometryService.list(params);
		for (ResultOptometryDO resultOptometryDO : list) {
			Integer tOptometryId = resultOptometryDO.gettOptometryId();
			resultCornealList = resultCornealService.queryByToptometryTd(tOptometryId);
		
		}
		int size = resultCornealList.size();
		PageUtils pageUtils = new PageUtils(resultCornealList, size);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultCorneal:add")
	String add(){
	    return "information/resultCorneal/add";
	}

	@GetMapping("/edit/{tCornealId}")
	@RequiresPermissions("information:resultCorneal:edit")
	String edit(@PathVariable("tCornealId") Integer tCornealId,Model model){
		ResultCornealDO resultCorneal = resultCornealService.get(tCornealId);
		model.addAttribute("resultCorneal", resultCorneal);
	    return "information/resultCorneal/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultCorneal:add")
	public R save( ResultCornealDO resultCorneal){
		if(resultCornealService.save(resultCorneal)>0){
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
	public R update( ResultCornealDO resultCorneal){
		resultCornealService.update(resultCorneal);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultCorneal:remove")
	public R remove( Integer tCornealId){
		if(resultCornealService.remove(tCornealId)>0){
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
		resultCornealService.batchRemove(tCornealIds);
		return R.ok();
	}
	
}
