package com.shaicha.information.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.information.domain.ChectorDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.ResultDiopterService;
import com.shaicha.information.service.StudentReportService;
import com.shaicha.information.service.StudentService;

@Controller
public class StudentReportController {

	@Autowired
	StudentService studentService;
	@Autowired
	StudentReportService studentReportService;
	@Autowired
	private ResultDiopterService resultDiopterService;
	
	
/*	@ResponseBody
	@GetMapping("/overYear")
	public Map<String, List<Double>> overYear(String school){
		Map<String, List<Double>> myopia = studentReportService.overYearMyopia(school);
		return myopia;
		
	}
	
	@ResponseBody
	@GetMapping("/gradeMyopia")
	public Map<String, List<Double>> gradeMyopia(String school){
		Map<String, List<Double>> myopia = studentReportService.gradeMyopia(school);
		return myopia;
		
	}
	

	@ResponseBody
	@GetMapping("/overYearGradeMyopia")
	public Map<String, List<Double>> overYearGradeMyopia(String school){
		Map<String, List<Double>> myopia = studentReportService.overYearGradeMyopia(school);
		return myopia;
		
	}
	
	@ResponseBody
	@GetMapping("/studentSexMyopia")
	public Map<String, List<Double>> studentSexMyopia(String school){
		Map<String, List<Double>> myopia = studentReportService.studentSexMyopia(school);
		return myopia;
		
	}
	
	@ResponseBody
	@GetMapping("/overYearSexNan")
	public Map<String, List<Double>> overYearSexNan(String school){
		Map<String, List<Double>> myopia = studentReportService.overYearSexNan(school);
		return myopia;
		
	}
	
	@ResponseBody
	@GetMapping("/overYearSexNv")
	public Map<String, List<Double>> overYearSexNv(String school){
		Map<String, List<Double>> myopia = studentReportService.overYearSexNv(school);
		return myopia;
		
	}
	
	@ResponseBody
	@GetMapping("/overYearGradeSex")
	public Map<String, List<Double>> overYearGradeSex(String school){
		Map<String, List<Double>> myopia = studentReportService.overYearGradeSex(school);
		return myopia;
		
	}*/
	
	@GetMapping("/studentReport/xuexiao")
	public String xuexiao(Model model){
		List<StudentDO> schoolName = studentService.querySchoolName();
		List<ResultDiopterDO> jianchashijian = resultDiopterService.jianchashijian();
		model.addAttribute("schoolName", schoolName);
		model.addAttribute("jianchashijian", jianchashijian);
		return "information/student/xuexiao";
	}
	
	@ResponseBody
	@PostMapping("/studentReport/baogaoimg")
	public Map<String, List<Double>> baogaoimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, List<Double>> map = new HashMap<>();
		String school = request.getParameter("school");
		String checkDate = request.getParameter("checkDate");
		Map<String, Object> mapp = new HashMap<>();
		List<Double> mappp = new ArrayList<Double>();
		mapp.put("checkDate", checkDate);
		List<ResultDiopterDO> list = resultDiopterService.list(mapp);
		if(list.size()<=0){
			mappp = new ArrayList<Double>();
			mappp.add(-1.0);
			map.put("code", mappp);
		}else{
			mappp = new ArrayList<Double>();
			mappp.add(1.0);
			map.put("code", mappp);
		String schoolNum = request.getParameter("schoolNum");
		Map<String, List<Double>> overYearMyopia = studentReportService.overYearMyopia(school);
		Map<String, List<Double>> gradeMyopia = studentReportService.gradeMyopia(school,checkDate);
		Map<String, List<Double>> overYearGradeMyopia = studentReportService.overYearGradeMyopia(school);
		Map<String, List<Double>> studentSexMyopia = studentReportService.studentSexMyopia(school,checkDate);
		Map<String, List<Double>> overYearSexNan = studentReportService.overYearSexNan(school);
		Map<String, List<Double>> overYearSexNv = studentReportService.overYearSexNv(school);
		Map<String, List<Double>> overYearGradeSex = studentReportService.overYearGradeSex(school,checkDate);
		
		map.put("overYearMyopia", overYearMyopia.get("overYearMyopia"));
		
		map.put("gradeMyopia", gradeMyopia.get("gradeMyopia"));
		
		map.put("seventeen", overYearGradeMyopia.get("seventeen"));
		map.put("eighteen", overYearGradeMyopia.get("eighteen"));
		map.put("nineteen", overYearGradeMyopia.get("nineteen"));
		
		map.put("studentSexMyopia", studentSexMyopia.get("studentSexMyopia"));
		
		map.put("studentSexNanMyopia", overYearSexNan.get("studentSexMyopia"));
		
		map.put("studentSexNvMyopia", overYearSexNv.get("studentSexMyopia"));
		
		map.put("overYearSexNan", overYearGradeSex.get("overYearSexNan"));
		map.put("overYearSexNv", overYearGradeSex.get("overYearSexNv"));
		}
		return map;
		
		
	}
	
	@ResponseBody
	@PostMapping("/studentReport/baogaoxuexiao")
	public void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response) throws IOException{
		studentReportService.baogaoxuexiao(request, response);
			
	}

	
}
