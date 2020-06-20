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

import com.shaicha.informationNEW.domain.ChanpinTitleChooseDO;
import com.shaicha.informationNEW.service.ChanpinTitleChooseService;
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
@RequestMapping("/information/chanpinTitleChoose")
public class ChanpinTitleChooseController {
	@Autowired
	private ChanpinTitleChooseService chanpinTitleChooseService;
	
	@GetMapping()
	@RequiresPermissions("information:chanpinTitleChoose:chanpinTitleChoose")
	String ChanpinTitleChoose(){
	    return "information/chanpinTitleChoose/chanpinTitleChoose";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:chanpinTitleChoose:chanpinTitleChoose")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ChanpinTitleChooseDO> chanpinTitleChooseList = chanpinTitleChooseService.list(query);
		int total = chanpinTitleChooseService.count(query);
		PageUtils pageUtils = new PageUtils(chanpinTitleChooseList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:chanpinTitleChoose:add")
	String add(){
	    return "information/chanpinTitleChoose/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:chanpinTitleChoose:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ChanpinTitleChooseDO chanpinTitleChoose = chanpinTitleChooseService.get(id);
		model.addAttribute("chanpinTitleChoose", chanpinTitleChoose);
	    return "information/chanpinTitleChoose/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:chanpinTitleChoose:add")
	public R save( ChanpinTitleChooseDO chanpinTitleChoose){
		if(chanpinTitleChooseService.save(chanpinTitleChoose)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:chanpinTitleChoose:edit")
	public R update( ChanpinTitleChooseDO chanpinTitleChoose){
		chanpinTitleChooseService.update(chanpinTitleChoose);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:chanpinTitleChoose:remove")
	public R remove( Integer id){
		if(chanpinTitleChooseService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:chanpinTitleChoose:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		chanpinTitleChooseService.batchRemove(ids);
		return R.ok();
	}
	
}
