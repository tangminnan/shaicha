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
import com.shaicha.information.domain.ResultAdjustingDO;
import com.shaicha.information.service.ResultAdjustingService;

/**
 * 调节灵敏度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/information/resultAdjusting")
public class ResultAdjustingController {
	@Autowired
	private ResultAdjustingService resultAdjustingService;
	
	@GetMapping()
	@RequiresPermissions("information:resultAdjusting:resultAdjusting")
	String ResultAdjusting(){
	    return "information/resultAdjusting/resultAdjusting";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultAdjusting:resultAdjusting")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultAdjustingDO> resultAdjustingList = resultAdjustingService.list(query);
		int total = resultAdjustingService.count(query);
		PageUtils pageUtils = new PageUtils(resultAdjustingList, total);
		return pageUtils;
	}
	
	
	@ResponseBody
	@GetMapping("/getUserDetail/{identityCard}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("identityCard") String id,@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		query.put("identityCard", id);
		List<ResultAdjustingDO> resultAdjustingList = resultAdjustingService.list(query);
		int total = resultAdjustingService.count(query);
		PageUtils pageUtils = new PageUtils(resultAdjustingList, total);
		return pageUtils;
	}
	
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultAdjusting:add")
	String add(){
	    return "information/resultAdjusting/add";
	}

	@GetMapping("/edit/{tAdjustingId}")
	@RequiresPermissions("information:resultAdjusting:edit")
	String edit(@PathVariable("tAdjustingId") Integer tAdjustingId,Model model){
		ResultAdjustingDO resultAdjusting = resultAdjustingService.get(tAdjustingId);
		model.addAttribute("resultAdjusting", resultAdjusting);
	    return "information/resultAdjusting/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultAdjusting:add")
	public R save( ResultAdjustingDO resultAdjusting){
		if(resultAdjustingService.save(resultAdjusting)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultAdjusting:edit")
	public R update( ResultAdjustingDO resultAdjusting){
		resultAdjustingService.update(resultAdjusting);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultAdjusting:remove")
	public R remove( Integer tAdjustingId){
		if(resultAdjustingService.remove(tAdjustingId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultAdjusting:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tAdjustingIds){
		resultAdjustingService.batchRemove(tAdjustingIds);
		return R.ok();
	}
	
}
