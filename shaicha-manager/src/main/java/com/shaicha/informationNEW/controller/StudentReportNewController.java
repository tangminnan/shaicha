package com.shaicha.informationNEW.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.shaicha.common.utils.R;
import com.shaicha.information.domain.ChectorDO;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.information.service.LinShiUrlService;
import com.shaicha.informationNEW.service.ResultDiopterNewService;
import com.shaicha.informationNEW.service.StudentReportNewService;
import com.shaicha.informationNEW.service.StudentNewService;

@Controller
public class StudentReportNewController {

	@Autowired
	StudentNewService studentNewService;
	@Autowired
	StudentReportNewService studentReportNewService;
	@Autowired
	private ResultDiopterNewService resultDiopterNewService;
	@Autowired
	LinShiUrlService linShiUrlService;
	
	
	@GetMapping("/studentReportNEW/xuexiao")
	public String xuexiao(Model model){
		List<StudentNewDO> schoolName = studentNewService.querySchoolName();
		List<ResultDiopterNewDO> jianchashijian = resultDiopterNewService.jianchashijian();
		model.addAttribute("schoolName", schoolName);
		model.addAttribute("jianchashijian", jianchashijian);
		return "informationNEW/student/xuexiao";
	}
	
	@ResponseBody
	@PostMapping("/studentReportNEW/baogaoimg")
	public Map<String, Object> baogaoimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<>();
		String school = request.getParameter("school");
		String checkDate = request.getParameter("checkDate");
		Map<String, Object> mapp = new HashMap<>();
		mapp.put("checkDate", checkDate);
		List<ResultDiopterNewDO> list = resultDiopterNewService.list(mapp);
		if(list.size()<=0){
			map.put("code", "-1");
		}else{
			map.put("code", "0");
		String schoolNum = request.getParameter("schoolNum");
		Map<String, List<Object>> overYearMyopia = studentReportNewService.overYearMyopia(school);
		Map<String, List<Object>> gradeMyopia = studentReportNewService.gradeMyopia(school,checkDate);
		Map<String, List<Object>> overYearGradeMyopia = studentReportNewService.overYearGradeMyopia(school);
		Map<String, List<Double>> studentSexMyopia = studentReportNewService.studentSexMyopia(school,checkDate);
		Map<String, List<Object>> overYearSexNan = studentReportNewService.overYearSexNan(school);
		Map<String, List<Object>> overYearSexNv = studentReportNewService.overYearSexNv(school);
		Map<String, List<Object>> overYearGradeSex = studentReportNewService.overYearGradeSex(school,checkDate);
		
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
		
		map.put("year", overYearMyopia.get("year"));
		map.put("grade", gradeMyopia.get("grade"));
		}
		return map;
		
		
	}
	
	@ResponseBody
	@PostMapping("/studentReportNEW/xuexiaotu")
	public String xuexiaotu(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		Map<String,String> map = new HashMap<>();
		String overYear=request.getParameter("overYear");
		String overYear1 = baseString(overYear);
		String gradeMyopia=request.getParameter("gradeMyopia");
		String gradeMyopia1 = baseString(gradeMyopia);
		String overYearGradeMyopia=request.getParameter("overYearGradeMyopia");
		String overYearGradeMyopia1 = baseString(overYearGradeMyopia);
		String studentSexMyopia=request.getParameter("studentSexMyopia");
		String studentSexMyopia1 = baseString(studentSexMyopia);
		String overYearSexNan=request.getParameter("overYearSexNan");
		String overYearSexNan1 = baseString(overYearSexNan);
		String overYearSexNv=request.getParameter("overYearSexNv");
		String overYearSexNv1 = baseString(overYearSexNv);
		String overYearGradeSex=request.getParameter("overYearGradeSex");
		String overYearGradeSex1 = baseString(overYearGradeSex);
		map.put("overYear", overYear1);
		map.put("gradeMyopia", gradeMyopia1);
		map.put("overYearGradeMyopia",overYearGradeMyopia1 );
		map.put("studentSexMyopia", studentSexMyopia1);
		map.put("overYearSexNan", overYearSexNan1);
		map.put("overYearSexNv",overYearSexNv1 );
		map.put("overYearGradeSex", overYearGradeSex1);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LinShiUrlDO ls = new LinShiUrlDO();
			ls.setName(entry.getKey());
			ls.setImgUrl(entry.getValue());
			ls.setType(format);
			linShiUrlService.save(ls);
		}		
		return format;		
	}
	
	public String baseString(String imgData){
		 String newImageInfo = imgData.replaceAll(" ", "+");
	        // 数据中：data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABI4AAAEsCAYAAAClh/jbAAA ...
	        // 在"base64,"之后的才是图片信息
	        String[] arr = newImageInfo.split("base64,");
			return arr[1];

	}
	

	@GetMapping("/studentReportNEW/baogaoxuexiao")
	public void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response) throws IOException{
		studentReportNewService.baogaoxuexiao(request, response);
					
	}
	
	@GetMapping("/studentReportNEW/jiaoyuju")
	public String jiaoyuju(Model model){
		List<StudentNewDO> schoolName = studentNewService.querySchoolName();
		List<ResultDiopterNewDO> jianchashijian = resultDiopterNewService.jianchashijian();
		model.addAttribute("schoolName", schoolName);
		return "informationNEW/student/jiaoyuju";
	}
	@ResponseBody
	@PostMapping("/studentReportNEW/baogaojyjimg")
	public Map<String, Object> baogaojyjimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			List<ResultDiopterNewDO> timeBetween = resultDiopterNewService.queryTimeBetween(sdf.parse(startDate), sdf.parse(endDate));
			if(timeBetween.size()<=0){
				map.put("code", "-1");
			}else{
				map.put("code", "0");
				Map<String, List<Double>> jinshi = studentReportNewService.suoyounianjijinshi(sdf.parse(startDate),sdf.parse(endDate));
				Map<String, List<Double>> buliang = studentReportNewService.suoyounianjibuliang(sdf.parse(startDate),sdf.parse(endDate));
				Map<String, Object> nianling = studentReportNewService.genianlingjinshiyear(sdf.parse(startDate),sdf.parse(endDate));
				Map<String, Object> nannv = studentReportNewService.nannvjinshiyear(sdf.parse(startDate),sdf.parse(endDate));
				
				map.put("jinshi", jinshi.get("jinshi"));
				
				map.put("buliang", buliang.get("buliang"));
				
				map.put("nianling", nianling.get("nianling"));
				
				map.put("nan", nannv.get("nan"));
				map.put("nv", nannv.get("nv"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return map;
	}
	@ResponseBody
	@PostMapping("/studentReportNEW/jiaoyujutu")
	public String jiaoyujutu(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Map<String,String> map = new HashMap<>();
		String gejinshi=request.getParameter("gejinshi");
		String gejinshi1 = baseString(gejinshi);
		String gebuliang=request.getParameter("gebuliang");
		String gebuliang1 = baseString(gebuliang);
		String nianling=request.getParameter("nianling");
		String nianling1 = baseString(nianling);
		String nannv=request.getParameter("nannv");
		String nannv1 = baseString(nannv);
		map.put("gejinshi", gejinshi1);
		map.put("gebuliang", gebuliang1);
		map.put("nianling",nianling1);
		map.put("nannv", nannv1);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LinShiUrlDO ls = new LinShiUrlDO();
			ls.setName(entry.getKey());
			ls.setImgUrl(entry.getValue());
			ls.setType(format);
			linShiUrlService.save(ls);
		}		
		return format;
		
	}
	
	@GetMapping("/studentReportNEW/baogaojiaoyuju")
	public void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response) throws IOException{
		studentReportNewService.baogaojiaoyuju(request, response);
	}
	
	@GetMapping("/studentReportNEW/dengdaixuexiao")
	public String dengdai(HttpServletRequest request, HttpServletResponse response,Model model){
		String school = request.getParameter("school");
		String checkDate = request.getParameter("checkDate");
		String date = request.getParameter("date");
		String schoolNum = request.getParameter("schoolNum");		
		model.addAttribute("school", school);
		model.addAttribute("checkDate", checkDate);
		model.addAttribute("date", date);
		model.addAttribute("schoolNum", schoolNum);
		return "informationNEW/student/dengdaixuexiao";
		
			
	}
	
	@GetMapping("/studentReportNEW/dengdaijiaoyuju")
	public String dengdaijiaoyuju(HttpServletRequest request, HttpServletResponse response,Model model){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String date = request.getParameter("date");
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("date", date);
		return "informationNEW/student/dengdajiaoyuju";
		
			
	}
	
	
}
