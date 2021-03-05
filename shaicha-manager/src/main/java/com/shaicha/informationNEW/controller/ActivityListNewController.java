package com.shaicha.informationNEW.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.shaicha.informationNEW.domain.ActivityListNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.service.ActivityListNewService;
import com.shaicha.informationNEW.service.StudentNewService;
import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.common.utils.ShiroUtils;

/**
 * 活动表---新
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 10:29:09
 */
 
@Controller
@RequestMapping("/informationNEW/activityListNew")
public class ActivityListNewController {
	@Autowired
	private ActivityListNewService activityListNewService;
	@Autowired
	private StudentNewService studentNewService;
	
	@GetMapping()
	@RequiresPermissions("information:activityListNew:activityListNew")
	String ActivityListNew(){
	    return "informationNEW/activityListNew/activityListNew";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:activityListNew:activityListNew")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        if(!ShiroUtils.getUser().getUsername().equals("admin")){
        	query.put("sysId", ShiroUtils.getUserId());
        }
		List<ActivityListNewDO> activityListNewList = activityListNewService.list(query);
		for (ActivityListNewDO activityListNewDO : activityListNewList) {
			Integer id = activityListNewDO.getId();
			activityListNewDO.setYingjian(studentNewService.activityNum(id));
			activityListNewDO.setShoujian(studentNewService.activityCheckNum(id));

		}
		int total = activityListNewService.count(query);
		PageUtils pageUtils = new PageUtils(activityListNewList, total);
		return pageUtils;
	}
	
	@GetMapping("/schoolshuju")
	@ResponseBody
	List<ActivityListNewDO> schoolshuju(Integer id){
		List<ActivityListNewDO> al = new ArrayList<>();
		List<StudentNewDO> activityIdBySchool = studentNewService.activityIdBySchool(id);
		if(activityIdBySchool.size()>0){
			for (StudentNewDO studentNewDO : activityIdBySchool) {
				ActivityListNewDO aln = new ActivityListNewDO();
				Integer schoolId = studentNewDO.getSchoolId();
				aln.setActivityName(studentNewDO.getSchool());
				aln.setYingjian(studentNewService.activitySchoolNum(id, schoolId));
				aln.setShoujian(studentNewService.activitySchoolCheckNum(id, schoolId));
                List<String> schoolCheckDate = studentNewService.getSchoolCheckDate(id, schoolId);
                String checkDate = "";
                for (int i = 0;i<schoolCheckDate.size();i++){
                    checkDate = checkDate + schoolCheckDate.get(i) + ",";
                }
                if (schoolCheckDate.size()>0) checkDate = checkDate.substring(0,checkDate.length()-1);
                aln.setCheckDate(checkDate);
                al.add(aln);
			}
		}
		return al;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:activityListNew:add")
	String add(){
	    return "informationNEW/activityListNew/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:activityListNew:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ActivityListNewDO activityListNew = activityListNewService.get(id);
		model.addAttribute("activityListNew", activityListNew);
	    return "informationNEW/activityListNew/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:activityListNew:add")
	public R save( ActivityListNewDO activityListNew){
		activityListNew.setAddTime(new Date());
		activityListNew.setDelFlag(0);
		activityListNew.setSysId(ShiroUtils.getUserId());
		if(activityListNewService.save(activityListNew)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:activityListNew:edit")
	public R update( ActivityListNewDO activityListNew){
		activityListNew.setUpdateDate(new Date());
		activityListNewService.update(activityListNew);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:activityListNew:remove")
	public R remove( Integer id){
		if(activityListNewService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:activityListNew:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		activityListNewService.batchRemove(ids);
		return R.ok();
	}
	
}
