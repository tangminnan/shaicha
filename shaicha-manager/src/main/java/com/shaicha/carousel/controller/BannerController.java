package com.shaicha.carousel.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.carousel.domain.BannerDO;
import com.shaicha.carousel.service.BannerService;
import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.controller.BaseController;
import com.shaicha.common.utils.FileUtil;
import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;


/**
 * 轮播图
 * 
 * @author wjl
 */
@Controller
@RequestMapping("/carousel/banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerService bannerService;

	@Autowired
	private BootdoConfig bootdoConfig;

	@GetMapping()
	@RequiresPermissions("carousel:banner:banner")
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "carousel/banner/banner";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("carousel:banner:banner")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<BannerDO> sysFileList = bannerService.list(query);
		int total = bannerService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("carousel:banner:add")
	String add() {
		return "carousel/banner/add";
	}

	@GetMapping("/edit/{id}")
	// @RequiresPermissions("carousel:bComments")
	String edit(@PathVariable("id") Long id, Model model) {
		BannerDO sysFile = bannerService.get(id);
		model.addAttribute("model", sysFile);
		return "carousel/banner/edit";
	}
	@GetMapping("/rocket/{id}")
	// @RequiresPermissions("carousel:bComments")
	String rocket(@PathVariable("id") Long id, Model model) {
		BannerDO sysFile = bannerService.get(id);
		model.addAttribute("model", sysFile);
		return "carousel/banner/rocket";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("carousel:info")
	public R info(@PathVariable("id") Long id) {
		BannerDO sysFile = bannerService.get(id);
		return R.ok().put("sysFile", sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("carousel:banner:add")
	public R save(BannerDO sysFile) {
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isEnable", "0");
		int total = bannerService.count(param);
		if(total >= 5 && sysFile.getIsEnable() == 0){
			return R.error("最多显示5张轮播图!");
		}
		
		
		String fileName = sysFile.getImgFile().getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		try {
			FileUtil.uploadFile(sysFile.getImgFile().getBytes(), bootdoConfig.getUploadPath(), fileName);
			sysFile.setUrl("/files/" + fileName);
			sysFile.setAddTime(new Date());
			sysFile.setUpdateTime(new Date());
			sysFile.setUserId(this.getUserId());
		} catch (Exception e) {
			return R.error();
		}
		
		
		if (bannerService.save(sysFile) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(value="/updateEnable")
	public R updateEnable(Long id,Integer enable) {
		System.out.println("id"+id);
		System.out.println("enable"+enable);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isEnable", "0");
		int total = bannerService.count(param);
		System.out.println("total:"+total);
		if(enable == 0 && total >= 5){
			return R.error("最多显示5张轮播图!");
		}
		
		BannerDO sysFile = bannerService.get(id);
		sysFile.setIsEnable(enable);
		bannerService.update(sysFile);
		
		return R.ok();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("carousel:update")
	public R update( BannerDO sysFile) {
		System.out.println("================");
		if(sysFile.getImgFile() != null && sysFile.getImgFile().getSize() > 0){
			String fileName = sysFile.getImgFile().getOriginalFilename();
			fileName = FileUtil.renameToUUID(fileName);
			try {
				FileUtil.uploadFile(sysFile.getImgFile().getBytes(), bootdoConfig.getUploadPath(), fileName);
				sysFile.setUrl("/files/" + fileName);
			} catch (Exception e) {
				return R.error();
			}
			
		}
		sysFile.setUserId(this.getUserId());
		sysFile.setUpdateTime(new Date());
		sysFile.setUserId(this.getUserId());
		bannerService.update(sysFile);
		
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	// @RequiresPermissions("carousel:remove")
	public R remove(Long id, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = bootdoConfig.getUploadPath() + bannerService.get(id).getUrl().replace("/files/", "");
		if (bannerService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("carousel:remove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		bannerService.batchRemove(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	R upload(@RequestParam("file") MultipartFile file,BannerDO sysFile, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		//BannerDO sysFile = new BannerDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		//BannerDO sysFile = new BannerDO(String name,int place,int type,Date startTime,Date endTime,int isEnable,String url,Long userId);
		try {
			FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			return R.error();
		}

		if (bannerService.save(sysFile) > 0) {
			return R.ok().put("fileName",sysFile.getUrl());
		}
		return R.error();
	}


}
