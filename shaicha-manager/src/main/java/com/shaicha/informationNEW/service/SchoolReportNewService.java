package com.shaicha.informationNEW.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaicha.informationNEW.domain.StudentNewDO;

public interface SchoolReportNewService {
	
	Map<String,List<Object>> overYearMyopia(String school);

	Map<String,List<Object>> gradeMyopia(String school,Integer activityId);
	
	Map<String,List<Object>> overYearGradeMyopia(String school);

	Map<String,List<Object>> overYearGradeBuliang(String school);

	Map<String,List<Double>> studentSexMyopia(String school,Integer activityId);
	
	Map<String,List<Object>> overYearSexNan(String school);
	
	Map<String,List<Object>> overYearSexNv(String school);
	
	Map<String,List<Object>> overYearGradeSex(String school,Integer activityId);
	
	Map<String,Object> shangcibulingjinshi(String school,Integer activityId);

	void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response);
	
	List<StudentNewDO> schoolActivity(Integer activityId,Long sysId);
	
	void schoolGradeRep(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	void schoolClassRep(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	void shaichawenjuanRep(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void conditionExport(HttpServletRequest request,HttpServletResponse response) throws IOException;
}
