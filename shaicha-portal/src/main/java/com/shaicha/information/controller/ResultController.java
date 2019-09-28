package com.shaicha.information.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.shaicha.common.annotation.Log;
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
}
