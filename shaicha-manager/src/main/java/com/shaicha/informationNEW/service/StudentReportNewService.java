package com.shaicha.informationNEW.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface StudentReportNewService {
	
	Map<String,List<Object>> overYearMyopia(String school);
	
	Map<String,List<Object>> gradeMyopia(String school,String checkDate);
	
	Map<String,List<Object>> overYearGradeMyopia(String school);
	
	Map<String,List<Double>> studentSexMyopia(String school,String checkDate);
	
	Map<String,List<Object>> overYearSexNan(String school);
	
	Map<String,List<Object>> overYearSexNv(String school);
	
	Map<String,List<Object>> overYearGradeSex(String school,String checkDate);

	void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response);
	
	void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response);
	
	Map<String,List<Double>> suoyounianjijinshi(Date start,Date end);
	
	Map<String,List<Double>> suoyounianjibuliang(Date start,Date end);
	
	Map<String,Object> genianlingjinshiyear(Date start,Date end);
	
	Map<String,Object> nannvjinshiyear(Date start,Date end);
}
