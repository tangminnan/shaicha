package com.shaicha.information.controller;
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

	/**
	 * 星康接口 查询用户
	 * @param AppKey
	 * @param AppSecret
	 * @param userID
	 * @return
	 * {
	  "json":
	   {
	    "status":0,
	    "errmsg":"",
	    "result":  {"userName": “XXX”,”userClass”:”XXX” }
	}
	}
	 */
	@PostMapping("/getUserInfo")
	public Map<String,Object> getUserInfo(String AppKey, String AppSecret, Long userID ){
		StudentDO studentInfoByUserID = resultService.getStudentInfoByUserID(userID);
		Map<String,Object> resultMap = new HashMap<>();
		Map<String,Object> result11Map = new HashMap<>();
        if(studentInfoByUserID==null){
			resultMap.put("status",1);
			resultMap.put("errmsg","用户不存在!");
			result11Map.put("userName","");
			result11Map.put("userClass","");
			resultMap.put("reslut",result11Map);
		}else{

        	result11Map.put("userName",studentInfoByUserID.getStudentName());
        	result11Map.put("userClass",studentInfoByUserID.getGrade()+studentInfoByUserID.getStudentClass());
			resultMap.put("result",result11Map);
			resultMap.put("status",0);
			resultMap.put("errmsg","数据查询到 姓名为【"+studentInfoByUserID.getStudentName()+"】，班级为【"+studentInfoByUserID.getGrade()+studentInfoByUserID.getStudentClass()+"】");
		}
		Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("json",resultMap);
        return jsonMap;
	}

	/**
	 * 星康接口 数据保存接口
	 * @param AppKey
	 * @param AppSecret
	 * @param userID
	 * @param Right1
	 * @param Right2
	 * @param Left1
	 * @param Left2
	 * @param isGlasses
	 * @return
	 * {
	  "json":
	   {
	    "status":0,
	    "errmsg":"",
	    "result":  {"object":略 }
	}
	}
	 */
	@PostMapping("/saveDianziEye")
	public Map<String,Object> saveDianziEye(
			String AppKey,
			String AppSecret,
			Long userID,
			String Right1,
			String Right2,
			String Left1,
			String Left2,
			Integer isGlasses) {
		ResultEyesightDO resultEyesightDO  = new ResultEyesightDO();
		resultEyesightDO.setStudentId(userID);
		if(isGlasses==0){//裸眼视力
			resultEyesightDO.setNakedFarvisionOd(Right2);
			resultEyesightDO.setNakedFarvisionOs(Left2);
		}
		if(isGlasses==1){//戴镜视力
			resultEyesightDO.setCorrectionFarvisionOd(Right2);
			resultEyesightDO.setCorrectionFarvisionOs(Left2);
		}
	   int result = resultService.updateDianziEye(resultEyesightDO);
		Map<String,Object> resultMap11 = new HashMap<>();
		resultMap11.put("object","");
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result",resultMap11);
		resultMap.put("status" , result>0?0:1);
		resultMap.put("errmsg" , result>0?"数据保存成功":"数据保存失败");
		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("json",resultMap);
		return jsonMap;
	}
}
