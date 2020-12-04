package com.shaicha.information.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.shaicha.information.domain.ResultQuestionDO;
import com.shaicha.information.domain.SchoolNewDO;

public interface ResultService {

	Map<String, Object> saveResultData(JSONObject obj);

	Map<String, Object> getStudentInfo(String identityCard);

	Map<String, Object> getStudentInfo(Long id);
	
	List<SchoolNewDO> list(Map<String,Object> map);

	Map<String, Object> saveResultQuestion(JSONObject obj);



}
