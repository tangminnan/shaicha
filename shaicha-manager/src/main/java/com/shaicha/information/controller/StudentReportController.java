package com.shaicha.information.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.information.domain.ActivityListDO;
import com.shaicha.information.domain.ChectorDO;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.ActivityListService;
import com.shaicha.information.service.LinShiUrlService;
import com.shaicha.information.service.ResultDiopterService;
import com.shaicha.information.service.SchoolReportService;
import com.shaicha.information.service.StudentService;
import com.shaicha.information.service.jiaoyujuReportService;

@Controller
public class StudentReportController {

	@Autowired
	StudentService studentService;
	@Autowired
	SchoolReportService schoolReportService;
	@Autowired
	jiaoyujuReportService jiaoyujuReportService;
	@Autowired
	private ResultDiopterService resultDiopterService;
	@Autowired
	LinShiUrlService linShiUrlService;
	@Autowired
	ActivityListService activityListService;
	
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@GetMapping("/studentReport/xuexiao")
	public String xuexiao(Model model){
		List<ActivityListDO> activityList = activityListService.list(new HashMap<>());
		model.addAttribute("activityList", activityList);
		/*List<StudentDO> schoolName = studentService.querySchoolName();
		List<ResultDiopterDO> jianchashijian = resultDiopterService.jianchashijian();
		model.addAttribute("schoolName", schoolName);
		model.addAttribute("jianchashijian", jianchashijian);*/
		return "information/student/xuexiao";
	}

	/**
	 * 学校报告图片生成
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/studentReport/baogaoimg")
	public Map<String, Object> baogaoimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<>();
		String school = request.getParameter("school");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		/*Map<String, Object> mapp = new HashMap<>();
		mapp.put("checkDate", checkDate);
		List<ResultDiopterDO> list = resultDiopterService.list(mapp);
		if(list.size()<=0){
			map.put("code", "-1");
		}else{
			map.put("code", "0");*/
		//String schoolNum = request.getParameter("schoolNum");
		Map<String, List<Object>> overYearMyopia = schoolReportService.overYearMyopia(school);
		Map<String, List<Object>> gradeMyopia = schoolReportService.gradeMyopia(school,activityId);
		Map<String, List<Object>> overYearGradeMyopia = schoolReportService.overYearGradeMyopia(school);
		Map<String, List<Double>> studentSexMyopia = schoolReportService.studentSexMyopia(school,activityId);
		Map<String, List<Object>> overYearSexNan = schoolReportService.overYearSexNan(school);
		Map<String, List<Object>> overYearSexNv = schoolReportService.overYearSexNv(school);
		Map<String, List<Object>> overYearGradeSex = schoolReportService.overYearGradeSex(school,activityId);
		
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
		//}
		return map;
		
		
	}
	
	/**
	 * 学校报告图片转码保存
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	@ResponseBody
	@PostMapping("/studentReport/xuexiaotu")
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
	
	/**
	 * 学校报告导出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/studentReport/baogaoxuexiao")
	public void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response) throws IOException{
		schoolReportService.baogaoxuexiao(request, response);
					
	}
	
//	/**
//	 * 作废
//	 * @param model
//	 * @return
//	 */
//	@GetMapping("/studentReport/jiaoyuju")
//	public String jiaoyuju(Model model){
//		List<StudentDO> schoolName = studentService.querySchoolName();
//		List<ResultDiopterDO> jianchashijian = resultDiopterService.jianchashijian();
//		model.addAttribute("schoolName", schoolName);
//		return "information/student/jiaoyuju";
//	}
	
	/**
	 * 教育局报告图片生成
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/studentReport/baogaojyjimg")
	public Map<String, Object> baogaojyjimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String activityId = request.getParameter("activityId");
		System.out.println(activityId);
		String[] parameter = request.getParameterValues("school[]");
		for (String string : parameter) {
			System.out.println(string);
		}
		
		Map<String, Object> map = new HashMap<>();
		/*List<ResultDiopterDO> timeBetween = resultDiopterService.queryTimeBetween(sdf.parse(startDate), sdf.parse(endDate));
		if(timeBetween.size()<=0){
			map.put("code", "-1");
		}else{
			map.put("code", "0");*/
			Map<String, List<Double>> jinshi = jiaoyujuReportService.suoyounianjijinshi(request);
			Map<String, List<Double>> buliang = jiaoyujuReportService.suoyounianjibuliang(request);
			Map<String, Object> nianling = jiaoyujuReportService.genianlingjinshiyear(request);
			Map<String, Object> nannv = jiaoyujuReportService.nannvjinshiyear(request);
			
			map.put("jinshi", jinshi.get("jinshi"));
			
			map.put("buliang", buliang.get("buliang"));
			
			map.put("nianling", nianling.get("nianling"));
			
			map.put("nan", nannv.get("nan"));
			map.put("nv", nannv.get("nv"));
		//}		
		
		return map;
	}
	
	/**
	 * 教育局报告图片转码保存
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/studentReport/jiaoyujutu")
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
	
	/**
	 * 教育局报告导出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/studentReport/baogaojiaoyuju")
	public void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response) throws IOException{
		jiaoyujuReportService.baogaojiaoyuju(request, response);
	}
	
	/**
	 * 导出等待页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("/studentReport/dengdaixuexiao")
	public String dengdai(HttpServletRequest request, HttpServletResponse response,Model model){
		String school = request.getParameter("school");
		String activityId = request.getParameter("activityId");
		String date = request.getParameter("date");
		model.addAttribute("school", school);
		model.addAttribute("date", date);
		model.addAttribute("activityId", activityId);
		return "information/student/dengdaixuexiao";
		
			
	}
	
	/**
	 * 导出等待页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("/studentReport/dengdaijiaoyuju")
	public String dengdaijiaoyuju(HttpServletRequest request, HttpServletResponse response,Model model){
		String activityId = request.getParameter("activityId");
		System.out.println(activityId);
		String parameter = request.getParameter("school");
		String[] split = parameter.split(",");
		for (String string : split) {
			System.out.println(string);
		}
		String date = request.getParameter("date");
		model.addAttribute("activityId", activityId);
		model.addAttribute("school", parameter);
		model.addAttribute("date", date);
		return "information/student/dengdajiaoyuju";
		
			
	}
	
	
	@ResponseBody
	@GetMapping("/studentReport/schoolActivity")
	public List<StudentDO> schoolActivity(Integer activityId){
		List<StudentDO> schoolActivity = schoolReportService.schoolActivity(activityId);
		
		return schoolActivity;
		
	}
	
	@ResponseBody
	@GetMapping("/studentReport/schoolGrade")
	public List<StudentDO> schoolGrade(Integer activityId,String school){
		List<StudentDO> schoolGrade = studentService.queryBySchoolGrade(activityId, school);
		
		return schoolGrade;
		
	}
	
	@ResponseBody
	@GetMapping("/studentReport/schoolStuClass")
	public List<StudentDO> schoolStuClass(Integer activityId,String school){
		List<StudentDO> stuClass = studentService.queryBySchoolStudentClass(activityId, school);
		
		return stuClass;
		
	}
	
	/**
	 * 年级报告导出
	 * @throws IOException 
	 */
	@GetMapping("/studentReport/gradeBaogao")
	public void gradeBaogao(HttpServletRequest request,HttpServletResponse response) throws IOException{
		schoolReportService.schoolGradeRep(request, response);
	}
	
	@GetMapping("/studentReport/dengdaigrade")
	public String dengdaigrade(HttpServletRequest request, HttpServletResponse response,Model model){
		String school = request.getParameter("school");
		String activityId = request.getParameter("activityId");
		String grade = request.getParameter("grade");
		String type = request.getParameter("type");
		String stuclass = request.getParameter("stuclass");
		model.addAttribute("school", school);
		model.addAttribute("grade", grade);
		model.addAttribute("activityId", activityId);
		model.addAttribute("type", type);
		model.addAttribute("stuclass", stuclass);
		return "information/student/dengdaigrade";
			
	}
	/**
	 * 班级报告导出
	 */
	@GetMapping("/studentReport/stuClassBaogao")
	public void stuClassBaogao(HttpServletRequest request,HttpServletResponse response) throws IOException{
		schoolReportService.schoolClassRep(request, response);
	}

	
	
	
	
}
