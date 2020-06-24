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

import com.shaicha.informationNEW.domain.ChanpinListDO;
import com.shaicha.informationNEW.service.ChanpinListService;
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
@RequestMapping("/information/chanpinList")
public class ChanpinListController {
	@Autowired
	private ChanpinListService chanpinListService;
	
	@GetMapping()
	@RequiresPermissions("information:chanpinList:chanpinList")
	String ChanpinList(){
	    return "information/chanpinList/chanpinList";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:chanpinList:chanpinList")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ChanpinListDO> chanpinListList = chanpinListService.list(query);
		int total = chanpinListService.count(query);
		PageUtils pageUtils = new PageUtils(chanpinListList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:chanpinList:add")
	String add(){
	    return "information/chanpinList/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:chanpinList:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ChanpinListDO chanpinList = chanpinListService.get(id);
		model.addAttribute("chanpinList", chanpinList);
	    return "information/chanpinList/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:chanpinList:add")
	public R save( ChanpinListDO chanpinList){
		if(chanpinListService.save(chanpinList)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:chanpinList:edit")
	public R update( ChanpinListDO chanpinList){
		chanpinListService.update(chanpinList);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:chanpinList:remove")
	public R remove( Integer id){
		if(chanpinListService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:chanpinList:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		chanpinListService.batchRemove(ids);
		return R.ok();
	}
	
}
