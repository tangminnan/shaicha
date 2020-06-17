package com.shaicha.informationNEW.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface jiaoyujuReportNewService {
	
	void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response);
	
	Map<String,List<Double>> suoyounianjijinshi(HttpServletRequest request);
	
	Map<String,List<Double>> suoyounianjibuliang(HttpServletRequest request);
	
	Map<String,Object> genianlingjinshiyear(HttpServletRequest request);
	
	Map<String,Object> nannvjinshiyear(HttpServletRequest request);

}
