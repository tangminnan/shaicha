package com.shaicha.information.controller;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.utils.FileUtil;
import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.information.domain.ChectorDO;
import com.shaicha.information.service.ChectorService;
/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 16:19:31
 */
 
@Controller
@RequestMapping("/information/chector")
public class ChectorController {
	@Autowired
	private ChectorService chectorService;
	@Autowired
	private BootdoConfig bootdoConfig;
	
	@GetMapping()
	@RequiresPermissions("information:chector:chector")
	String Chector(){
	    return "information/chector/chector";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:chector:chector")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        if(!ShiroUtils.getUser().getUsername().equals("admin")){
        	query.put("sysId", ShiroUtils.getUserId());
        }
		List<ChectorDO> chectorList = chectorService.list(query);
		int total = chectorService.count(query);
		PageUtils pageUtils = new PageUtils(chectorList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:chector:add")
	String add(){
	    return "information/chector/add";
	}

	@GetMapping("/edit/{chectorId}")
	@RequiresPermissions("information:chector:edit")
	String edit(@PathVariable("chectorId") Long chectorId,Model model){
		ChectorDO chector = chectorService.get(chectorId);
		model.addAttribute("chector", chector);
	    return "information/chector/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:chector:add")
	public R save( ChectorDO chector){
		Map<String,Object> params= new HashMap<String,Object>();
		params.put("phone",chector.getPhone());
		List<ChectorDO> list=chectorService.list(params);
		if(list.size()>0)
			return R.error("手机号码已存在");
		try{
			MultipartFile file = chector.getImgFile();
			if(file!=null && file.getSize()>0){
				String fileName = FileUtil.renameToUUID(file.getOriginalFilename());
				FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath()+"chector/", fileName);
				chector.setHeadshot("/files/chector/"+fileName);
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		chector.setAddTime(new Date());
		chector.setUpdateTime(new Date());
		chector.setUsername(chector.getPhone());
		chector.setDeleted(0);
		chector.setSysId(ShiroUtils.getUserId());
		if(chectorService.save(chector)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:chector:edit")
	public R update( ChectorDO chector){
		Map<String,Object> params= new HashMap<String,Object>();
		params.put("phone",chector.getPhone());
		List<ChectorDO> list=chectorService.list(params);
		for(ChectorDO chectorDO:list){
			if(chectorDO.getChectorId()!=chector.getChectorId())
				return R.error("手机号码已存在");
		}
		try{
			MultipartFile file = chector.getImgFile();
			if(file!=null && file.getSize()>0){
				String fileName = FileUtil.renameToUUID(file.getOriginalFilename());
				FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath()+"chector/", fileName);
				chector.setHeadshot("/files/chector/"+fileName);
			}		
		}catch (Exception e) {
			e.printStackTrace();
		}
		chector.setUpdateTime(new Date());
		chector.setUsername(chector.getPhone());
		chectorService.update(chector);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:chector:remove")
	public R remove( Long chectorId){
			chectorService.remove(chectorId);
		return R.ok();
		
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:chector:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] chectorIds){
		chectorService.batchRemove(chectorIds);
		return R.ok();
	}
	
}
