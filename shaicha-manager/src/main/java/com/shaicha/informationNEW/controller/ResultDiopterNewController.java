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
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.domain.ResultOptometryNewDO;
import com.shaicha.informationNEW.service.ResultDiopterNewService;
import com.shaicha.informationNEW.service.ResultOptometryNewService;

/**
 * 曲光度详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
 
@Controller
@RequestMapping("/informationNEW/resultDiopter")
public class ResultDiopterNewController {
	@Autowired
	private ResultDiopterNewService resultDiopterNewService;
	@Autowired
	private ResultOptometryNewService resultOptometryNewService;
	
	@GetMapping()
	@RequiresPermissions("information:resultDiopter:resultDiopter")
	String ResultDiopter(){
	    return "informationNEW/resultDiopter/resultDiopter";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:resultDiopter:resultDiopter")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ResultDiopterNewDO> resultDiopterList = resultDiopterNewService.list(query);
		int total = resultDiopterNewService.count(query);
		PageUtils pageUtils = new PageUtils(resultDiopterList, total);
		return pageUtils;
	}
	
	@ResponseBody
	@GetMapping("/getUserDetail/{id}")
	@RequiresPermissions("information:student:student")
	public PageUtils getUserDetail(@PathVariable("id") Integer id){
		//查询列表数据
		List<ResultDiopterNewDO> resultDiopterDOList=new ArrayList<ResultDiopterNewDO>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", id);
		List<ResultOptometryNewDO> list = resultOptometryNewService.list(params);
		for (ResultOptometryNewDO resultOptometryDO : list) {
			Integer tOptometryId = resultOptometryDO.gettOptometryId();
			resultDiopterDOList.addAll(resultDiopterNewService.getByToptometryId(tOptometryId));
		
		}
		int size = resultDiopterDOList.size();
		PageUtils pageUtils = new PageUtils(resultDiopterDOList, size);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:resultDiopter:add")
	String add(){
	    return "informationNEW/resultDiopter/add";
	}

	@GetMapping("/edit/{tDiopterId}")
	@RequiresPermissions("information:resultDiopter:edit")
	String edit(@PathVariable("tDiopterId") Integer tDiopterId,Model model){
		ResultDiopterNewDO resultDiopter = resultDiopterNewService.get(tDiopterId);
		model.addAttribute("resultDiopter", resultDiopter);
	    return "informationNEW/resultDiopter/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:resultDiopter:add")
	public R save( ResultDiopterNewDO resultDiopter){
		if(resultDiopterNewService.save(resultDiopter)>0){
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
	public R update( ResultDiopterNewDO resultDiopter){
		resultDiopterNewService.update(resultDiopter);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:resultDiopter:remove")
	public R remove( Integer tDiopterId){
		if(resultDiopterNewService.remove(tDiopterId)>0){
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
		resultDiopterNewService.batchRemove(tDiopterIds);
		return R.ok();
	}
	
}
