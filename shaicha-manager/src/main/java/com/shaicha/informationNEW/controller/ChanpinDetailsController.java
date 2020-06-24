package com.shaicha.informationNEW.controller;

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

import com.shaicha.informationNEW.domain.ChanpinDetailsDO;
import com.shaicha.informationNEW.service.ChanpinDetailsService;
import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:52
 */
 
@Controller
@RequestMapping("/information/chanpinDetails")
public class ChanpinDetailsController {
	@Autowired
	private ChanpinDetailsService chanpinDetailsService;
	
	@GetMapping()
	@RequiresPermissions("information:chanpinDetails:chanpinDetails")
	String ChanpinDetails(){
	    return "information/chanpinDetails/chanpinDetails";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:chanpinDetails:chanpinDetails")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ChanpinDetailsDO> chanpinDetailsList = chanpinDetailsService.list(query);
		int total = chanpinDetailsService.count(query);
		PageUtils pageUtils = new PageUtils(chanpinDetailsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:chanpinDetails:add")
	String add(){
	    return "information/chanpinDetails/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:chanpinDetails:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ChanpinDetailsDO chanpinDetails = chanpinDetailsService.get(id);
		model.addAttribute("chanpinDetails", chanpinDetails);
	    return "information/chanpinDetails/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:chanpinDetails:add")
	public R save( ChanpinDetailsDO chanpinDetails){
		if(chanpinDetailsService.save(chanpinDetails)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:chanpinDetails:edit")
	public R update( ChanpinDetailsDO chanpinDetails){
		chanpinDetailsService.update(chanpinDetails);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:chanpinDetails:remove")
	public R remove( Integer id){
		if(chanpinDetailsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:chanpinDetails:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		chanpinDetailsService.batchRemove(ids);
		return R.ok();
	}
	
}
