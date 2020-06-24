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

import com.shaicha.informationNEW.domain.ChanpinRecordDetailsDO;
import com.shaicha.informationNEW.service.ChanpinRecordDetailsService;
import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:53
 */
 
@Controller
@RequestMapping("/information/chanpinRecordDetails")
public class ChanpinRecordDetailsController {
	@Autowired
	private ChanpinRecordDetailsService chanpinRecordDetailsService;
	
	@GetMapping("/{id}")
	@RequiresPermissions("information:chanpinRecordDetails:chanpinRecordDetails")
	String ChanpinRecordDetails(@PathVariable("id") Integer id,Model model){
		model.addAttribute("id", id);
	    return "informationNEW/chanpinRecordDetails/chanpinRecordDetails";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:chanpinRecordDetails:chanpinRecordDetails")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ChanpinRecordDetailsDO> chanpinRecordDetailsList = chanpinRecordDetailsService.list(query);
		int total = chanpinRecordDetailsService.count(query);
		PageUtils pageUtils = new PageUtils(chanpinRecordDetailsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:chanpinRecordDetails:add")
	String add(){
	    return "informationNEW/chanpinRecordDetails/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:chanpinRecordDetails:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ChanpinRecordDetailsDO chanpinRecordDetails = chanpinRecordDetailsService.get(id);
		model.addAttribute("chanpinRecordDetails", chanpinRecordDetails);
	    return "informationNEW/chanpinRecordDetails/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:chanpinRecordDetails:add")
	public R save( ChanpinRecordDetailsDO chanpinRecordDetails){
		if(chanpinRecordDetailsService.save(chanpinRecordDetails)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:chanpinRecordDetails:edit")
	public R update( ChanpinRecordDetailsDO chanpinRecordDetails){
		chanpinRecordDetailsService.update(chanpinRecordDetails);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:chanpinRecordDetails:remove")
	public R remove( Integer id){
		if(chanpinRecordDetailsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:chanpinRecordDetails:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		chanpinRecordDetailsService.batchRemove(ids);
		return R.ok();
	}
	
}
