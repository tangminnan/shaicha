package com.shaicha.informationNEW.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.informationNEW.domain.ActivityListNewDO;
import com.shaicha.information.domain.ChectorDO;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.service.ActivityListNewService;
import com.shaicha.information.service.LinShiUrlService;
import com.shaicha.informationNEW.service.ResultDiopterNewService;
import com.shaicha.informationNEW.service.SchoolReportNewService;
import com.shaicha.informationNEW.service.StudentNewService;
import com.shaicha.informationNEW.service.jiaoyujuReportNewService;

@Controller
public class StudentReportNewController {

	@Autowired
	StudentNewService studentService;
	@Autowired
	SchoolReportNewService schoolReportService;
	@Autowired
	jiaoyujuReportNewService jiaoyujuReportService;
	@Autowired
	private ResultDiopterNewService resultDiopterService;
	@Autowired
	LinShiUrlService linShiUrlService;
	@Autowired
	ActivityListNewService activityListService;
	
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@GetMapping("/studentReportNew/xuexiao")
	public String xuexiao(Model model){
		Map<String,Object> map = new HashMap<>();
		String username = ShiroUtils.getUser().getUsername();
		if(!username.equals("admin") && !username.equals("shujuzhongxin")){
			 map.put("sysId", ShiroUtils.getUserId());
	    }
		List<ActivityListNewDO> activityList = activityListService.list(map);
		model.addAttribute("activityList", activityList);
		model.addAttribute("sysId", ShiroUtils.getUser().getUsername());
		/*List<StudentDO> schoolName = studentService.querySchoolName();
		List<ResultDiopterDO> jianchashijian = resultDiopterService.jianchashijian();
		model.addAttribute("schoolName", schoolName);
		model.addAttribute("jianchashijian", jianchashijian);*/
		return "informationNEW/student/xuexiao";
	}

	/**
	 * 学校报告图片生成
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/studentReportNew/baogaoimg")
	public Map<String, Object> baogaoimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<>();
		String school = request.getParameter("school");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		/*Map<String, Object> mapp = new HashMap<>();
		mapp.put("activityId", activityId);
		List<StudentNewDO> list = studentService.list(mapp);
		if(list.size()<=0){
			map.put("code", "-1");
		}else{
			map.put("code", "0");*/
		//String schoolNum = request.getParameter("schoolNum");
		Map<String, List<Object>> overYearMyopia = schoolReportService.overYearMyopia(school);
		Map<String, List<Object>> gradeMyopia = schoolReportService.gradeMyopia(school,activityId);
		Map<String, List<Object>> overYearGradeMyopia = schoolReportService.overYearGradeMyopia(school);
		Map<String, List<Object>> overYearGradeBuliang = schoolReportService.overYearGradeBuliang(school);
//		Map<String, List<Double>> studentSexMyopia = schoolReportService.studentSexMyopia(school,activityId);
		Map<String, List<Object>> overYearSexNan = schoolReportService.overYearSexNan(school);
		Map<String, List<Object>> overYearSexNv = schoolReportService.overYearSexNv(school);
		Map<String, List<Object>> overYearGradeSex = schoolReportService.overYearGradeSex(school,activityId);
//		Map<String, Object> shangcibulingjinshi = schoolReportService.shangcibulingjinshi(school,activityId);
		
		map.put("overYearMyopia", overYearMyopia.get("overYearMyopia"));
		
//		map.put("gradeMyopia", gradeMyopia.get("gradeMyopia"));
		
		map.put("seventeen", overYearGradeMyopia.get("seventeen"));
		map.put("eighteen", overYearGradeMyopia.get("eighteen"));
		map.put("nineteen", overYearGradeMyopia.get("nineteen"));

		map.put("qu",overYearGradeBuliang.get("seventeen"));
		map.put("jin",overYearGradeBuliang.get("eighteen"));
		map.put("ming",overYearGradeBuliang.get("nineteen"));

//		map.put("studentSexMyopia", studentSexMyopia.get("studentSexMyopia"));
		
		map.put("studentSexNanMyopia", overYearSexNan.get("studentSexMyopia"));
		
		map.put("studentSexNvMyopia", overYearSexNv.get("studentSexMyopia"));
		
		map.put("overYearSexNan", overYearGradeSex.get("overYearSexNan"));
		map.put("overYearSexNv", overYearGradeSex.get("overYearSexNv"));
		
		map.put("year", overYearMyopia.get("year"));
		map.put("grade", gradeMyopia.get("grade"));
		
//		map.put("blshujus", shangcibulingjinshi.get("blshujus"));
//		map.put("blshujuz", shangcibulingjinshi.get("blshujuz"));
//		map.put("jsshujus", shangcibulingjinshi.get("jsshujus"));
//		map.put("jsshujuz", shangcibulingjinshi.get("jsshujuz"));
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
	@PostMapping("/studentReportNew/xuexiaotu")
	public String xuexiaotu(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		Map<String,String> map = new HashMap<>();
		String overYear=request.getParameter("overYear");
		String overYear1 = baseString(overYear);
//		String gradeMyopia=request.getParameter("gradeMyopia");
//		String gradeMyopia1 = baseString(gradeMyopia);
		String overYearGradeMyopia=request.getParameter("overYearGradeMyopia");
		String overYearGradeMyopia1 = baseString(overYearGradeMyopia);
//		String studentSexMyopia=request.getParameter("studentSexMyopia");
//		String studentSexMyopia1 = baseString(studentSexMyopia);
//		String overYearSexNan=request.getParameter("overYearSexNan");
//		String overYearSexNan1 = baseString(overYearSexNan);
		String overYearSexNv=request.getParameter("overYearSexNv");
		String school=request.getParameter("school");
		String overYearSexNv1 = baseString(overYearSexNv);
		String overYearGradeSex=request.getParameter("overYearGradeSex");
		String overYearGradeSex1 = baseString(overYearGradeSex);
		String shangcibulingjinshi=request.getParameter("shangcibulingjinshi");
		String shangcibulingjinshi1 = baseString(shangcibulingjinshi);
		map.put("overYear", overYear1);
//		map.put("gradeMyopia", gradeMyopia1);
		map.put("overYearGradeMyopia",overYearGradeMyopia1 );
//		map.put("studentSexMyopia", studentSexMyopia1);
//		map.put("overYearSexNan", overYearSexNan1);
		map.put("overYearSexNv",overYearSexNv1 );
		map.put("overYearGradeSex", overYearGradeSex1);
		map.put("shangcibulingjinshi", shangcibulingjinshi1);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LinShiUrlDO ls = new LinShiUrlDO();
			ls.setName(entry.getKey());
			ls.setImgUrl(entry.getValue());
			ls.setType(format);
			ls.setFore(school);
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
	@GetMapping("/studentReportNew/baogaoxuexiao")
	public void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response) throws IOException{
		schoolReportService.baogaoxuexiao(request, response);
					
	}
	

	
	/**
	 * 教育局报告图片生成
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/studentReportNew/baogaojyjimg")
	public Map<String, Object> baogaojyjimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String activityId = request.getParameter("activityId");
		System.out.println(activityId);
		String[] parameter = request.getParameterValues("school[]");
		List<Map> list = new ArrayList<>();
		for (String school : parameter) {
			System.out.println(school);
            Map<String, Object> map1 = new HashMap<>();
            Map<String, List<Object>> overYearMyopia = schoolReportService.overYearMyopia(school);
            Map<String, List<Object>> gradeMyopia = schoolReportService.gradeMyopia(school,Integer.parseInt(activityId));
            Map<String, List<Object>> overYearGradeMyopia = schoolReportService.overYearGradeMyopia(school);
            Map<String, List<Object>> overYearGradeBuliang = schoolReportService.overYearGradeBuliang(school);
            Map<String, List<Object>> overYearSexNan = schoolReportService.overYearSexNan(school);
            Map<String, List<Object>> overYearSexNv = schoolReportService.overYearSexNv(school);
            Map<String, List<Object>> overYearGradeSex = schoolReportService.overYearGradeSex(school,Integer.parseInt(activityId));
            map1.put("overYearMyopia", overYearMyopia.get("overYearMyopia"));
            map1.put("seventeen", overYearGradeMyopia.get("seventeen"));
            map1.put("eighteen", overYearGradeMyopia.get("eighteen"));
            map1.put("nineteen", overYearGradeMyopia.get("nineteen"));
            map1.put("qu",overYearGradeBuliang.get("seventeen"));
            map1.put("jin",overYearGradeBuliang.get("eighteen"));
            map1.put("ming",overYearGradeBuliang.get("nineteen"));
            map1.put("studentSexNanMyopia", overYearSexNan.get("studentSexMyopia"));
            map1.put("studentSexNvMyopia", overYearSexNv.get("studentSexMyopia"));
            map1.put("overYearSexNan", overYearGradeSex.get("overYearSexNan"));
            map1.put("overYearSexNv", overYearGradeSex.get("overYearSexNv"));
            map1.put("year", overYearMyopia.get("year"));
            map1.put("grade", gradeMyopia.get("grade"));
            map1.put("school",school);
            list.add(map1);
		}
		
		Map<String, Object> map = new HashMap<>();
			Map<String, List<Double>> jinshi = jiaoyujuReportService.suoyounianjijinshi(request);
			Map<String, List<Double>> buliang = jiaoyujuReportService.suoyounianjibuliang(request);
//			Map<String, Object> nianling = jiaoyujuReportService.genianlingjinshiyear(request);
			Map<String, Object> nianling = jiaoyujuReportService.gexuebujinshiyear(request);
//			Map<String, Object> nannv = jiaoyujuReportService.nannvjinshiyear(request);
			
			map.put("jinshi", jinshi.get("jinshi"));
			
			map.put("buliang", buliang.get("buliang"));
			
//			map.put("nianling", nianling.get("nianling"));
			map.put("nianling", nianling);

//			map.put("nan", nannv.get("nan"));
//			map.put("nv", nannv.get("nv"));
			map.put("list",list);


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
	@PostMapping("/studentReportNew/jiaoyujutu")
	public String jiaoyujutu(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
		Map<String,String> map = new HashMap<>();
		String gejinshi=request.getParameter("gejinshi");
		String gejinshi1 = baseString(gejinshi);
		String gebuliang=request.getParameter("gebuliang");
		String gebuliang1 = baseString(gebuliang);
		String nianling=request.getParameter("nianling");
		String nianling1 = baseString(nianling);
//		String nannv=request.getParameter("nannv");
//		String nannv1 = baseString(nannv);
		map.put("gejinshi", gejinshi1);
		map.put("gebuliang", gebuliang1);
		map.put("nianling",nianling1);
//		map.put("nannv", nannv1);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LinShiUrlDO ls = new LinShiUrlDO();
			ls.setName(entry.getKey());
			ls.setImgUrl(entry.getValue());
			ls.setType(format);
			ls.setFore("jyj");
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
	@GetMapping("/studentReportNew/baogaojiaoyuju")
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
	@GetMapping("/studentReportNew/dengdaixuexiao")
	public String dengdai(HttpServletRequest request, HttpServletResponse response,Model model){
		String school = request.getParameter("school");
		String activityId = request.getParameter("activityId");
		String date = request.getParameter("date");
		model.addAttribute("school", school);
		model.addAttribute("date", date);
		model.addAttribute("activityId", activityId);
		return "informationNEW/student/dengdaixuexiao";
		
			
	}
	
	/**
	 * 导出等待页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("/studentReportNew/dengdaijiaoyuju")
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
		return "informationNEW/student/dengdajiaoyuju";
		
			
	}
	
	
	@ResponseBody
	@GetMapping("/studentReportNew/schoolActivity")
	public List<StudentNewDO> schoolActivity(Integer activityId){
		Long sysId = null;
		String username = ShiroUtils.getUser().getUsername();
		if(!username.equals("admin") && !username.equals("shujuzhongxin")){
        	sysId = ShiroUtils.getUserId();
        }
		List<StudentNewDO> schoolActivity = schoolReportService.schoolActivity(activityId,sysId);
		
		return schoolActivity;
		
	}
	
	@ResponseBody
	@GetMapping("/studentReportNew/schoolGrade")
	public List<StudentNewDO> schoolGrade(Integer activityId,String school){
		Long sysId = null;
		String username = ShiroUtils.getUser().getUsername();
		if(!username.equals("admin") && !username.equals("shujuzhongxin")){
			sysId = ShiroUtils.getUserId();
        }
		List<StudentNewDO> schoolGrade = studentService.queryBySchoolGrade(activityId, school,sysId);
		
		return schoolGrade;
		
	}
	
	@ResponseBody
	@GetMapping("/studentReportNew/schoolStuClass")
	public List<StudentNewDO> schoolStuClass(Integer activityId,String school,String grade){
		Long sysId = null;
		String username = ShiroUtils.getUser().getUsername();
		if(!username.equals("admin") && !username.equals("shujuzhongxin")){
			sysId = ShiroUtils.getUserId();
        }
		List<StudentNewDO> stuClass = studentService.queryBySchoolStudentClass(activityId, school,sysId,grade);
		
		return stuClass;
		
	}
	
	/**
	 * 年级报告导出
	 * @throws IOException 
	 */
	@GetMapping("/studentReportNew/gradeBaogao")
	public void gradeBaogao(HttpServletRequest request,HttpServletResponse response) throws IOException{
		schoolReportService.schoolGradeRep(request, response);
	}
	
	@GetMapping("/studentReportNew/dengdaigrade")
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
		return "informationNEW/student/dengdaigrade";
			
	}
	/**
	 * 班级报告导出
	 */
	@GetMapping("/studentReportNew/stuClassBaogao")
	public void stuClassBaogao(HttpServletRequest request,HttpServletResponse response) throws IOException{
		schoolReportService.schoolClassRep(request, response);
	}

	/**
	 * 筛查问卷
	 */
	@GetMapping("/studentReportNew/shaichawenjuan")
	public void shaichawenjuan(HttpServletRequest request,HttpServletResponse response) throws IOException{
		schoolReportService.shaichawenjuanRep(request, response);
	}


	/**
	 *   根据条件导出数据
	 *   （手动直接在代码上修改条件）
	 */
	@GetMapping("/studentReportNew/conditionExport")
	public void conditionExport(HttpServletRequest request,HttpServletResponse response) throws IOException{
		schoolReportService.conditionExport(request, response);
	}
	
}
