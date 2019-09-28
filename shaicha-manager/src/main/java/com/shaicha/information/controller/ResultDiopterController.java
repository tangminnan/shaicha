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
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.service.ResultDiopterService;
import com.shaicha.information.service.ResultOptometryService;

/**
 * 曲光度详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/information/resultDiopter")
public class ResultDiopterController {
	@Autowired
	private ResultDiopterService resultDiopterService;
	@Autowired
	private ResultOptometryService resultOptometryService;
	
	@GetMapping()
	@RequiresPermissions("information:resultDiopter:resultDiopter")
	String ResultDiopter(){
	    return "information/resultDiopter/resultDiopter";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultDiopter:resultDiopter")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultDiopterDO> resultDiopterList = resultDiopterService.list(query);
		int total = resultDiopterService.count(query);
		PageUtils pageUtils = new PageUtils(resultDiopterList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		List<ResultDiopterDO> resultDiopterList = new ArrayList<ResultDiopterDO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultOptometryDO> list = resultOptometryService.list(params);
		for (ResultOptometryDO resultOptometryDO : list) {
			Integer tOptometryId = resultOptometryDO.gettOptometryId();
			ResultDiopterDO resultDiopterDO = resultDiopterService.getByToptometryId(tOptometryId);
			resultDiopterList.add(resultDiopterDO);
		}
		int size = resultDiopterList.size();
		PageUtils pageUtils = new PageUtils(resultDiopterList, size);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultDiopter:add")
	String add(){
	    return "information/resultDiopter/add";
	}

	@GetMapping("/edit/{tDiopterId}")
	@RequiresPermissions("information:resultDiopter:edit")
	String edit(@PathVariable("tDiopterId") Integer tDiopterId,Model model){
		ResultDiopterDO resultDiopter = resultDiopterService.get(tDiopterId);
		model.addAttribute("resultDiopter", resultDiopter);
	    return "information/resultDiopter/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultDiopter:add")
	public R save( ResultDiopterDO resultDiopter){
		if(resultDiopterService.save(resultDiopter)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:resultDiopter:edit")
	public R update( ResultDiopterDO resultDiopter){
		resultDiopterService.update(resultDiopter);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultDiopter:remove")
	public R remove( Integer tDiopterId){
		if(resultDiopterService.remove(tDiopterId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:resultDiopter:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] tDiopterIds){
		resultDiopterService.batchRemove(tDiopterIds);
		return R.ok();
	}
	
}
