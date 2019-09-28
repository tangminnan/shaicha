package com.shaicha.information.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface ResultService {

	Map<String, Object> saveResultData(JSONObject obj);

	Map<String, Object> getStudentInfo(String identityCard);

	Map<String, Object> getStudentInfo(Long id);

}
