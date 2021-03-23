package com.shaicha.information.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.shaicha.common.annotation.Log;
import com.shaicha.information.domain.SchoolNewDO;
import com.shaicha.information.service.ResultService;

@RestController
@RequestMapping("/result/data")
public class ResultController {
	@Autowired
	private ResultService resultService;
	
	
	@Log("检查结果数据保存")
	@PostMapping("/saveResultData")
	public Map<String,Object> saveResultData(@RequestBody  JSONObject obj){
		return resultService.saveResultData(obj);
	}
	
	@Log("获取学生的信息")
	@GetMapping("/getStudentInfo")
	public Map<String,Object> getStudentInfo(String identityCard){
		return resultService.getStudentInfo(identityCard);
	}
	
	@Log("获取上次的检测结果")
	@GetMapping("/getLastCheckResult")
	public Map<String,Object> getLastCheckResult(Long id){
		return resultService.getStudentInfo(id);
	}
	
	@Log("获取学校的信息")
	@GetMapping("/getSchoolList")
	public Map<String,Object> getSchoolList(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<SchoolNewDO> list = resultService.list(map);
		map.put("data",list);
		map.put("code",0);
		map.put("msg","数据获取成功...");
		return map;
	}
	@Log("问卷结果数据保存")
	@PostMapping("/saveResultQuestion")
	public Map<String,Object> saveResultQuestion(@RequestBody  JSONObject obj){
		return resultService.saveResultQuestion(obj);
	}
}
