package com.shaicha.informationNEW.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.shaicha.informationNEW.domain.ChanpinRecordListDO;
import com.shaicha.informationNEW.service.ChanpinRecordListService;
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
@RequestMapping("/information/chanpinRecordList")
public class ChanpinRecordListController {
	@Autowired
	private ChanpinRecordListService chanpinRecordListService;
	
	@GetMapping()
	@RequiresPermissions("information:chanpinRecordList:chanpinRecordList")
	String ChanpinRecordList(){
	    return "informationNEW/chanpinRecordList/chanpinRecordList";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:chanpinRecordList:chanpinRecordList")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ChanpinRecordListDO> chanpinRecordListList = chanpinRecordListService.list(query);
		int total = chanpinRecordListService.count(query);
		PageUtils pageUtils = new PageUtils(chanpinRecordListList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:chanpinRecordList:add")
	String add(){
	    return "informationNEW/chanpinRecordList/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:chanpinRecordList:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ChanpinRecordListDO chanpinRecordList = chanpinRecordListService.get(id);
		model.addAttribute("chanpinRecordList", chanpinRecordList);
	    return "informationNEW/chanpinRecordList/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:chanpinRecordList:add")
	public R save( ChanpinRecordListDO chanpinRecordList){
		if(chanpinRecordListService.save(chanpinRecordList)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:chanpinRecordList:edit")
	public R update( ChanpinRecordListDO chanpinRecordList){
		chanpinRecordListService.update(chanpinRecordList);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:chanpinRecordList:remove")
	public R remove( Integer id){
		if(chanpinRecordListService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:chanpinRecordList:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		chanpinRecordListService.batchRemove(ids);
		return R.ok();
	}
	
	@GetMapping("/wenjuanjiludaochu")
	void wenjuanjiludaochu(Integer[] ids,HttpServletRequest request,  HttpServletResponse response) throws IOException{
		chanpinRecordListService.wenjuandaochu(ids, request, response);
	}
	
}
