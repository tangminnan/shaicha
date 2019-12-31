package com.shaicha.information.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.shaicha.information.domain.ResultDiopterDO;

public interface StudentReportService {
	
	Map<String,List<Double>> overYearMyopia(String school);
	
	Map<String,List<Double>> gradeMyopia(String school,String checkDate);
	
	Map<String,List<Double>> overYearGradeMyopia(String school);
	
	Map<String,List<Double>> studentSexMyopia(String school,String checkDate);
	
	Map<String,List<Double>> overYearSexNan(String school);
	
	Map<String,List<Double>> overYearSexNv(String school);
	
	Map<String,List<Double>> overYearGradeSex(String school,String checkDate);

	void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response);
	
	void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response);
	
	Map<String,List<Double>> suoyounianjijinshi(Date start,Date end);
	
	Map<String,List<Double>> suoyounianjibuliang(Date start,Date end);
	
	Map<String,Object> genianlingjinshiyear(Date start,Date end);
	
	Map<String,Object> nannvjinshiyear(Date start,Date end);
}
