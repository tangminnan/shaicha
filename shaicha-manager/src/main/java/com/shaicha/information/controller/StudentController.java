package com.shaicha.information.controller;



import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaicha.information.domain.*;
import com.shaicha.information.service.*;
import com.shaicha.system.config.ExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.QRCodeUtil;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;


/**
 * 学生表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
 
@Controller
@RequestMapping("/information/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private ResultEyesightService resultEyesightService;
	@Autowired
	private ResultOptometryService resultOptometryService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private ResultVisibilityService resultVisibilityService;
	@Autowired
	private ResultDiopterService resultDiopterService;
	@GetMapping()
	@RequiresPermissions("information:student:student")
	String Student(Model model){
		List<StudentDO> studentList = studentService.getList();
		model.addAttribute("studentList", studentList);
	    return "information/student/student";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:student:student")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("checkType", "PU_TONG");
        List<StudentDO> studentList = studentService.list(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	/**
	 * 示范校筛查
	 */
	@GetMapping("/demonstration")
	public String demonstration(Model model){
		List<StudentDO> studentList = studentService.getList();
		model.addAttribute("studentList", studentList);
		Map<String, Object> params = new HashMap<>();
		List<SchoolDO> school = schoolService.list(params);
		model.addAttribute("school", school);
	    return "information/student/shifanstudent";
	}
	
	//模糊查询（姓名）
	@ResponseBody
	@GetMapping("/studentName")
	List<StudentDO> studentName(String studentName, Integer offset, Integer limit){
		List<StudentDO> list = studentService.querylistStudentName(studentName,offset,limit);
		return list;
	}
	
	/**
	 * 示范校筛查
	 */
	@ResponseBody
	@GetMapping("/listshifan")
	public PageUtils listshifan(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("checkType", "SHI_FANXIAO");
        List<StudentDO> studentList = studentService.list(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:student:add")
	String add(){
	    return "information/student/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:student:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		StudentDO student = studentService.get(id);
		model.addAttribute("student", student);
	    return "information/student/edit";
	}
	
	@GetMapping("/code/{id}")
	@RequiresPermissions("information:student:edit")
	String code(@PathVariable("id") Integer id,Model model){
		StudentDO student = studentService.get(id);
		String identityCard = student.getIdentityCard();
		String code = QRCodeUtil.creatRrCode(identityCard, 200,200);
		model.addAttribute("code", "data:image/png;base64,"+code);
		model.addAttribute("student", student);
	    return "information/student/QrCode";
	}
	
	@GetMapping("/detail/{id}")
	@RequiresPermissions("information:student:edit")
	String detail(@PathVariable("id") Integer id,Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		StudentDO student = studentService.get(id);
		List<ResultEyesightDO> list = resultEyesightService.list(map);
		model.addAttribute("list", list);
		model.addAttribute("student", student);
	    return "information/student/detailed";
	}
	
	@GetMapping("/optometry/{id}")
	@RequiresPermissions("information:student:edit")
	String optometry(@PathVariable("id") Integer id,Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		StudentDO student = studentService.get(id);
		map.put("studentId", id);
		List<ResultOptometryDO> list = resultOptometryService.list(map);
		list = list.stream().map(a -> {a.gettOptometryId();a.getCheckDate();return a;}).distinct().collect(Collectors.toList());
		model.addAttribute("list", list);
		model.addAttribute("student", student);
	    return "information/student/optometry";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:student:add")
	public R save( StudentDO student){
		if(studentService.save(student)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:student:edit")
	public R update( StudentDO student){
		studentService.update(student);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:student:remove")
	public R remove( Integer id){
		if(studentService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:student:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		studentService.batchRemove(ids);
		return R.ok();
	}
	
	
	@GetMapping("/importtemplate/{checkType}")
	@RequiresPermissions("information:student:student")
	public String importtemplate(Model model,@PathVariable("checkType") String checkType){
		model.addAttribute("checkType", checkType);
		if("PU_TONG".equals(checkType)){
			return "information/student/importtemplate";
		}
		if("SHI_FANXIAO".equals(checkType))
			return "information/student/shifanimporttemplate";
		return null;  
	}
	
	
	@GetMapping("/datidaoru")
	@RequiresPermissions("information:student:student")
	public String datidaoru(){
		return "information/student/answer";
	}
	
	/**
	 * 导入
	 */
	@PostMapping( "/importMember")
	@ResponseBody
	@RequiresPermissions("information:student:student")
	public R importMember(String checkType, MultipartFile file){
		return studentService.importMember(checkType,file);
		
	}
	
	/**
	 * 答题导入
	 */
//	@PostMapping( "/daorudatijiguo")
//	@ResponseBody
//	@RequiresPermissions("information:student:student")
//	public R daorudatijiguo(MultipartFile file){
//		return studentService.daorudatijiguo(file);
//	}
//
//	@GetMapping("/downloadErweima")
//	public void  downloadErweima(Integer[] ids,HttpServletRequest request,HttpServletResponse response){
//		studentService.downloadErweima(ids,request,response);
//		System.out.println(ids);
//	}
	
	
	
	
	/**
	 * 筛查结果导出
	 */
//	@GetMapping("/shaichajieguodaochu")
//	public void shaichajieguodaochu(Integer[] ids,HttpServletResponse response){
//		studentService.shaichajieguodaochu(ids,response);
//	}
	
	/**
	 * 示范校筛查结果导出
	 */
//	@GetMapping("/shifanshaichajieguodaochu")
//	public void shifanshaichajieguodaochu(Integer[] ids,HttpServletResponse response){
//		studentService.shifanshaichajieguodaochu(ids,response);
//	}
	
	/**
	 * 答题结果
	 */
	@GetMapping("/datijieguoR/{identityCard}")
	public String datijieguoR(@PathVariable("identityCard") String identityCard,Model model){
		model.addAttribute("identityCard", identityCard);
		return "information/student/datijieguoR";
	}
	
	@ResponseBody
	@GetMapping("/listDati")
	@RequiresPermissions("information:student:student")
	public PageUtils listDati(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AnswerResultDO> studentList = studentService.listDati(query);
		int total = studentService.countDati(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	
	/**
	 * 普通筛查导出（freemarker导出模式）
	 */
	@GetMapping("/shaichajieguodaochu")
	public void exportWordPByFreemarker(Integer[] ids, HttpServletRequest request, HttpServletResponse response){
		studentService.exportWordPByFreemarker(ids,request,response);
	}
	
	/**
	 * 示范校筛查结果导出（freemarker导出模式）
	 */
	@GetMapping("/shifanshaichajieguodaochu")
	public void exportWordPBByFreemarkerSHIfanxiao(Integer[] ids,HttpServletRequest request,  HttpServletResponse response){
		studentService.exportWordPBByFreemarkerSHIfanxiao(ids,request,response);
	}
	
	/**
	 * 浏览器打印二维码
	 */
	@GetMapping("/downLoadErWeiMaByBrower")
	public String downLoadErWeiMaByBrower(Integer id,Model model){
		StudentDO studentDO = Optional.ofNullable(studentService.get(id)).orElseGet(StudentDO::new);
		model.addAttribute("studentName",studentDO.getStudentName());
		model.addAttribute("identityCard",studentDO.getIdentityCard());
		model.addAttribute("studentSex",studentDO.getStudentSex());
		model.addAttribute("school",studentDO.getSchool());
		model.addAttribute("grade",studentDO.getGrade());
		model.addAttribute("studentClass",studentDO.getStudentClass());
		String identityCard = studentDO.getIdentityCard();
		String code = QRCodeUtil.creatRrCode(identityCard, 200,200);
		model.addAttribute("QRCode", "data:image/png;base64,"+code);
		//model.addAttribute("QRCode",studentDO.getQRCode());
		return "information/student/二维码";
	}
	
	/**
	 * 普通筛查打印
	 */
	
	
	@GetMapping("/putongshaichadayin")
	public String putongshaichadayin(Integer id,Model model){
		
		//基本信息获取
		StudentDO studentDO = studentService.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null)
			studentDO = new StudentDO();
		model.addAttribute("school", studentDO.getSchool());
		model.addAttribute("grade",  studentDO.getGrade());
		model.addAttribute("studentClass",studentDO.getStudentClass());
		model.addAttribute("studentName",studentDO.getStudentName());
		model.addAttribute("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "男":"女");
		model.addAttribute("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentService.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		model.addAttribute("nakedFarvisionOd",resultEyesightDO.getNakedFarvisionOd()==null? "":resultEyesightDO.getNakedFarvisionOd().toString());
		model.addAttribute("nakedFarvisionOs",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		model.addAttribute("correctionFarvisionOd",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
		model.addAttribute("correctionFarvisionOs",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentService.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSL",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		model.addAttribute("diopterCL",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		model.addAttribute("diopterAL",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList =studentService.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSR",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		model.addAttribute("diopterCR",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		model.addAttribute("diopterAR",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());
		//医生的建议（临时数据）
		model.addAttribute("doctorchubu","注意用眼卫生");
		model.addAttribute("doctortebie","注意用眼卫生，养成良好的用眼习惯");
		System.out.println("===========================");
		System.out.println("===========================");
		return "information/student/普通筛查打印";
	}
	
	/**
	 * 示范校筛查打印
	 */
	
	@GetMapping("/shifanshaichadayin")
	public String shifanshaichadayin(Integer id,Model model){
		//基本信息获取
		StudentDO studentDO = studentService.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return "information/student/示范校筛查打印";
		model.addAttribute("school", studentDO.getSchool());
		model.addAttribute("grade",studentDO.getGrade().toString());
		model.addAttribute("studentClass",studentDO.getStudentClass().toString());
		model.addAttribute("studentName",studentDO.getStudentName());
		model.addAttribute("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "男":"女");
		model.addAttribute("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentService.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		String nakedFarvisionOd="";//右眼裸眼视力
		String nakedFarvisionOs="";//左眼裸眼视力
		String correctionFarvisionOd="";//右眼戴镜视力
		String correctionFarvisionOs="";//左眼戴镜视力
		if(resultEyesightDOList.size()>0){
			resultEyesightDO=resultEyesightDOList.get(0);
			nakedFarvisionOd=resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString();
			nakedFarvisionOs=resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString();
			correctionFarvisionOd=resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString();
			correctionFarvisionOs=resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString();
		}
		model.addAttribute("nakedFarvisionOd",zhuanhuan(nakedFarvisionOd));
		model.addAttribute("nakedFarvisionOs",zhuanhuan(nakedFarvisionOs));
		model.addAttribute("glassvisionOd",zhuanhuan(correctionFarvisionOd));
		model.addAttribute("glassvisionOs",zhuanhuan(correctionFarvisionOs));
		//自动电脑验光结果(左眼)
		double dengxiaoqiujingL = 0.0,dengxiaoqiujingR=0.0;
		List<ResultDiopterDO> resultDiopterDOList = studentService.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSL",resultDiopterDO.getDiopterS()==null?"":zhuanhuan(resultDiopterDO.getDiopterS().toString()));
		model.addAttribute("diopterCL",resultDiopterDO.getDiopterC()==null?"":zhuanhuan(resultDiopterDO.getDiopterC().toString()));
		model.addAttribute("diopterAL",resultDiopterDO.getDiopterA()==null?"":zhuanhuan(resultDiopterDO.getDiopterA().toString()));;
		dengxiaoqiujingL=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentService.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSR",resultDiopterDO.getDiopterS()==null?"":zhuanhuan(resultDiopterDO.getDiopterS().toString()));
		model.addAttribute("diopterCR",resultDiopterDO.getDiopterC()==null?"":zhuanhuan(resultDiopterDO.getDiopterC().toString()));
		model.addAttribute("diopterAR",resultDiopterDO.getDiopterA()==null?"":zhuanhuan(resultDiopterDO.getDiopterA().toString()));;
		dengxiaoqiujingR=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
		
		//眼内压结果拼装
		List<ResultEyepressureDO> ResultEyepressureDOList = studentService.getLatestResultEyepressureDO(studentDO.getId());
		ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
		if(ResultEyepressureDOList.size()>0)
			resultEyepressureDO=ResultEyepressureDOList.get(0);
		model.addAttribute("eyePressureOd",resultEyepressureDO.getEyePressureOd()==null?"":zhuanhuan(resultEyepressureDO.getEyePressureOd().toString()));
		model.addAttribute("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":zhuanhuan(resultEyepressureDO.getEyePressureOs().toString()));
		//眼轴长度数据拼装
		List<ResultEyeaxisDO> resultEyeaxisDOList = studentService.getLatelestResultEyeaxisDO(studentDO.getId());
		ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
		if(resultEyeaxisDOList.size()>0)
			resultEyeaxisDO=resultEyeaxisDOList.get(0);
		model.addAttribute("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":zhuanhuan(resultEyeaxisDO.getFirstCheckOd().toString()));
		model.addAttribute("secondCheckOs", resultEyeaxisDO.getFirstCheckOs()==null?"":zhuanhuan(resultEyeaxisDO.getFirstCheckOs().toString()));
		
		System.out.println("===========================");
		System.out.println("===========================");
		//角膜验光拼装
		ResultCornealDO resultCornealDO = new ResultCornealDO();
		List<ResultCornealDO> resultCornealDOList = studentService.getResultCornealDOList(studentDO.getId(),"R","R1");
		if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
		model.addAttribute("cornealMmr1R",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
		model.addAttribute("cornealDr1R", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		resultCornealDO = new ResultCornealDO();
		resultCornealDOList = studentService.getResultCornealDOList(studentDO.getId(),"R","R2");
		if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
		model.addAttribute("cornealMmr2R",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
		model.addAttribute("cornealDr2R", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		
		resultCornealDO = new ResultCornealDO();
	    resultCornealDOList = studentService.getResultCornealDOList(studentDO.getId(),"L","R1");
	    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
	    model.addAttribute("cornealMmr1L",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
	    model.addAttribute("cornealDr1L", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		
		
	    
	    resultCornealDO = new ResultCornealDO();
	    resultCornealDOList = studentService.getResultCornealDOList(studentDO.getId(),"L","R2");
	    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);

	   model.addAttribute("cornealMmr2L",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
	   model.addAttribute("cornealDr2L", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		//医生的建议
	   double od=0.0,os=0.0;
	   if(!StringUtils.isBlank(nakedFarvisionOd)){
	    	od=Double.parseDouble(nakedFarvisionOd);
	    }
	    if(!StringUtils.isBlank(nakedFarvisionOs)){
	    	os=Double.parseDouble(nakedFarvisionOs);
	    }
//	    od=od<os?od:os;
//	    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
	    double yuanjingshiliL=0,yuanjingshiliR=0;//原镜视力
	    String ssL="ss",ssR="ss";
	    if(!StringUtils.isBlank(correctionFarvisionOd)){
//	    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
	    	yuanjingshiliR=Double.parseDouble(correctionFarvisionOd);
	    }
	    if(!StringUtils.isBlank(correctionFarvisionOs)){
//	    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
	    	yuanjingshiliL=Double.parseDouble(correctionFarvisionOs);
	    }
	    if(yuanjingshiliL==0)
	    	ssL="wuyuanjing";
	    if(yuanjingshiliR==0)
	    	ssR="wuyuanjing";
	    if(od>=5.0 && dengxiaoqiujingR>0.75){
	    	model.addAttribute("ydoctorchubu","视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
	//    	model.addAttribute("yujing","无");
	    }
	    if(os>=5.0 && dengxiaoqiujingL>0.75){
	    	model.addAttribute("zdoctorchubu","视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
	//    	model.addAttribute("yujing","无");
	    }
	    
		if(od>=5.0 && dengxiaoqiujingR>=-0.5 && dengxiaoqiujingR<=0.75){
			model.addAttribute("ydoctorchubu","视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
	//    	model.addAttribute("yujing","近视临床前期");
		}
		if(os>=5.0 && dengxiaoqiujingL>=-0.5 && dengxiaoqiujingL<=0.75){
			model.addAttribute("zdoctorchubu","视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
	//    	model.addAttribute("yujing","近视临床前期");
		}
		
		if(od>=5.0 && dengxiaoqiujingR<-0.5){
			model.addAttribute("ydoctorchubu","视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
	//    	model.addAttribute("yujing","假性近视");
		}
		if(os>=5.0 && dengxiaoqiujingL<-0.5){
			model.addAttribute("zdoctorchubu","视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
	//    	model.addAttribute("yujing","假性近视");
		}
		
		if(od<5.0 &&dengxiaoqiujingR>=-0.5 && yuanjingshiliR==0 && ssR.equals("wuyuanjing")){
			model.addAttribute("ydoctorchubu","视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
	//    	model.addAttribute("yujing","无");
		}
		if(os<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshiliL==0 && ssL.equals("wuyuanjing")){
			model.addAttribute("zdoctorchubu","视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
	//    	model.addAttribute("yujing","无");
		}
		
		if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR==0 && ssR.equals("wuyuanjing")){
			model.addAttribute("ydoctorchubu","视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
	//    	model.addAttribute("yujing","近视");
		}
		if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL==0 && ssL.equals("wuyuanjing")){
			model.addAttribute("zdoctorchubu","视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
	//    	model.addAttribute("yujing","近视");
		}
	
		if(od<5.0 && dengxiaoqiujingR>=-0.5 && yuanjingshiliR>=5.0){
			model.addAttribute("ydoctorchubu","戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
	//    	model.addAttribute("yujing","无");
		}
		if(os<5.0 && dengxiaoqiujingL>=-0.5 && yuanjingshiliL>=5.0){
			model.addAttribute("zdoctorchubu","戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
	//    	model.addAttribute("yujing","无");
		}
		
		if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR>=5.0){
			model.addAttribute("ydoctorchubu","戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
	//    	model.addAttribute("yujing","近视");
		}
		if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL>=5.0){
			model.addAttribute("zdoctorchubu","戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
	//    	model.addAttribute("yujing","近视");
		}
		
		if(od<5.0 &&dengxiaoqiujingR>=-0.5 && yuanjingshiliR<5.0 && ssR.equals("ss")){
			model.addAttribute("ydoctorchubu","戴原镜视力异常。 请遵医嘱及时定期复查。");
	//    	model.addAttribute("yujing","无");
		}
		if(os<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshiliL<5.0 && ssL.equals("ss")){
			model.addAttribute("zdoctorchubu","戴原镜视力异常。 请遵医嘱及时定期复查。");
	//    	model.addAttribute("yujing","无");
		}
		
		if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR<5.0 && ssR.equals("ss")){
			model.addAttribute("ydoctorchubu","戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
	//    	model.addAttribute("yujing","近视增长");
		}
		if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL<5.0 && ssL.equals("ss")){
			model.addAttribute("zdoctorchubu","戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
	//    	model.addAttribute("yujing","近视增长");
		}
		return "information/student/示范校筛查打印";
	}
	
	/**
	 * 查看筛查详情
	 */
	@GetMapping("/shifanshaichadetail")
	public String shifanshaichadetail(String identityCard,Model model){
		model.addAttribute("identityCard",identityCard);
		return "information/student/shifanshaichadetail";
	}
	/**
	 * 首页真实数据展示
	 */
	@ResponseBody
	@GetMapping("/shouYeTrueData")
	public Map<String,Double> shouYeTrueData(){
		return studentService.shouYeTrueData();	
	}
	
	private static Object zhuanhuan(Object s){
	       Double d=null;
	        if(s instanceof String){
	        	if("".equals((String)s))
	        		return "";
	        	d = Double.parseDouble((String)s);
	        }
	        if(s instanceof Double)
	            d = (Double)s;
	        System.out.println("d:"+d+" s:"+s);
	        if(Math.floor(d)==d)
	            return d.intValue();
	        return d;
	    }


	    @GetMapping("/Import")
	    public String Import(){
			return "informationNEW/student/Import";
		}

	/**
	 * 	  筛查老数据导入
	 */
	 @PostMapping("/oldDataImport")
	 @ResponseBody
	public R importMember(MultipartFile file) throws IOException {
		DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat dfs = new DecimalFormat("0");

        Workbook book = ExcelUtils.getBook(file);
        Sheet sheet = book.getSheetAt(0);
        int num = 0;
        try {
			for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				if (rowNum > 1) {
					String school = ExcelUtils.getCellFormatValue(row.getCell((short) 3));//学校名称
			//		String grade = ExcelUtils.getCellFormatValue(row.getCell((short) 1));//年级
					String username = ExcelUtils.getCellFormatValue(row.getCell((short) 1));//姓名
					String xingbie = ExcelUtils.getCellFormatValue(row.getCell((short) 2));//性别
					String nakedFarvisionOd = ExcelUtils.getCellFormatValue(row.getCell((short) 4));//裸眼视力 右眼
					String nakedFarvisionOs = ExcelUtils.getCellFormatValue(row.getCell((short) 5));//裸眼视力  左眼
					String correctionFarvisionOd = ExcelUtils.getCellFormatValue(row.getCell((short) 6));// 右眼
					String correctionFarvisionOs = ExcelUtils.getCellFormatValue(row.getCell((short) 7));//  左眼

					String diopterSD = ExcelUtils.getCellFormatValue(row.getCell((short) 8));//裸眼视力  左眼
					if(StringUtils.isBlank(diopterSD)) diopterSD="0.0";
					String diopterC = ExcelUtils.getCellFormatValue(row.getCell((short) 9));// 右眼
					if(StringUtils.isBlank(diopterC)) diopterC="0.0";
					String diopterA = ExcelUtils.getCellFormatValue(row.getCell((short) 10));//  左眼
					if(StringUtils.isBlank(diopterA)) diopterA="0.0";

					String diopterSS = ExcelUtils.getCellFormatValue(row.getCell((short) 11));//裸眼视力  左眼
					if(StringUtils.isBlank(diopterSS)) diopterSS="0.0";
					String diopterCS = ExcelUtils.getCellFormatValue(row.getCell((short) 12));// 右眼
					if(StringUtils.isBlank(diopterCS)) diopterCS="0.0";
					String diopterAS = ExcelUtils.getCellFormatValue(row.getCell((short) 13));//  左眼
					if(StringUtils.isBlank(diopterAS)) diopterAS="0.0";





//
//					String yanguangD = ExcelUtils.getCellFormatValue(row.getCell((short) 7));
//					String yanguangZ = ExcelUtils.getCellFormatValue(row.getCell((short) 8));
//					if(yanguangD==null || "".equals(yanguangD)) yanguangD="0.0";
//					if(yanguangZ==null ||"".equals(yanguangZ)) yanguangZ="0.0";
					String yanqianjie = ExcelUtils.getCellFormatValue(row.getCell((short) 14));//眼前节
					String yanwei = ExcelUtils.getCellFormatValue(row.getCell((short) 15));
					StudentDO studentDO = new StudentDO();

					studentDO.setSchool(school);

//					String s  = grade.substring(0,1);
//					String y= grade.substring(2);
//					studentDO.setGrade(grade);
//					studentDO.setStudentClass(grade.substring(2));
					studentDO.setStudentName(username);
//					if (StringUtils.isNotBlank(xingbie)) {
//						if (xingbie.equals("男")) {
//							studentDO.setStudentSex(1);
//						}
//						if (xingbie.equals("女")) {
//							studentDO.setStudentSex(2);
//						}
//					}
					studentDO.setActivityId(44);
					studentDO.setNakedFarvisionOd(nakedFarvisionOd);
					studentDO.setNakedFarvisionOs(nakedFarvisionOs);
					studentDO.setCheckType("PU_TONG");
//					Double diopterSD = 0.0;//右眼球镜
//					Double diopterC = 0.0;//右眼柱镜
//					Double diopterA = 0.0;//右眼轴位
//					if (yanguangD != null) {//
//						if (yanguangD.contains("/")) {
//							String di = yanguangD.substring(0, yanguangD.indexOf("/"));
//							if (di.contains("+")) {
//								diopterSD = Double.parseDouble(di.substring(1));
//							} else {
//								diopterSD = Double.parseDouble(di.substring(0));
//							}
//							if (yanguangD.contains("*")) {
//								String ci = yanguangD.substring(yanguangD.indexOf("/") + 1, yanguangD.indexOf("*"));
//								if (ci.contains("+")) {
//									diopterC = Double.parseDouble(ci.substring(1));
//								} else {
//									diopterC = Double.parseDouble(ci.substring(0));
//								}
//								String ai = yanguangD.substring(yanguangD.indexOf("*") + 1);
//								if (ai.contains("+")) {
//									diopterA = Double.parseDouble(ai.substring(1));
//								} else {
//									diopterA = Double.parseDouble(ai.substring(0));
//								}
//							}
//						} else {
//							if (yanguangD.contains("+")) {
//								diopterSD = Double.parseDouble(yanguangD.substring(1));
//							} else {
//								diopterSD = Double.parseDouble(yanguangD.substring(0));
//							}
//						}
//					}
//					Double diopterSS = 0.0;//左眼球镜
//					Double diopterCS = 0.0;//左眼柱镜
//					Double diopterAS = 0.0;//左眼轴位
//					if (yanguangZ != null) {//
//						if (yanguangZ.contains("/")) {
//							String di = yanguangZ.substring(0, yanguangZ.indexOf("/"));
//							if (di.contains("+")) {
//								diopterSS = Double.parseDouble(di.substring(1));
//							} else {
//								diopterSS = Double.parseDouble(di.substring(0));
//							}
//							if (yanguangZ.contains("*")) {
//								String ci = yanguangZ.substring(yanguangZ.indexOf("/") + 1, yanguangZ.indexOf("*"));
//								if (ci.contains("+")) {
//									diopterCS = Double.parseDouble(ci.substring(1));
//								} else {
//									diopterCS = Double.parseDouble(ci.substring(0));
//								}
//								String ai = yanguangZ.substring(yanguangZ.indexOf("*") + 1);
//								if (ai.contains("+")) {
//									diopterAS = Double.parseDouble(ai.substring(1));
//								} else {
//									diopterAS = Double.parseDouble(ai.substring(0));
//								}
//							}
//						} else {
//							if (yanguangZ.contains("+")) {
//								diopterSS = Double.parseDouble(yanguangZ.substring(1));
//							} else {
//								diopterSS = Double.parseDouble(yanguangZ.substring(0));
//							}
//						}
//					}
					Double zd = Double.parseDouble(diopterSS) + 1.0 / 2 * Double.parseDouble(diopterCS) ;//左眼等效球镜
					Double yd = Double.parseDouble(diopterSD)  + 1.0 / 2 * Double.parseDouble(diopterC) ;//右眼等效球镜
					studentDO.setDengxiaoqiujingl(zd);
					studentDO.setDengxiaoqiujingr(yd);
					studentDO.setAddTime(new Date());
					studentService.save(studentDO);//保存学生信息
					ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
					resultEyesightDO.setStudentId(studentDO.getId());
					resultEyesightDO.setNakedFarvisionOd(nakedFarvisionOd);
					resultEyesightDO.setNakedFarvisionOs(nakedFarvisionOs);
					resultEyesightDO.setDeleteFlag(0);
					resultEyesightDO.setActivityId(44);
//					resultEyesightDO.setCorrectionFarvisionOd(correctionFarvisionOd);
//					resultEyesightDO.setCorrectionFarvisionOs(correctionFarvisionOs);
					resultEyesightService.save(resultEyesightDO);//保存裸眼视力
					ResultVisibilityDO resultVisibilityDO = new ResultVisibilityDO();
					resultVisibilityDO.setStudentId(studentDO.getId());
					resultVisibilityDO.setBeforeAfterOdDis(yanqianjie);
					resultVisibilityDO.setBeforeAfterOsDis(yanqianjie);
					resultVisibilityDO.setActivityId(44);
					resultVisibilityDO.setYyanwei(yanwei);
					resultVisibilityDO.setZyanwei(yanwei);
					resultVisibilityService.save(resultVisibilityDO);
					ResultOptometryDO resultOptometryDO = new ResultOptometryDO();
					resultOptometryDO.setStudentId(studentDO.getId());
					resultOptometryDO.setFirstCheckVd(0d);
					resultOptometryDO.setFirstCheckRps(0d);
					resultOptometryDO.setFirstCheckLps(0d);
					resultOptometryDO.setFirstCheckRcs(0d);
					resultOptometryDO.setFirstCheckLcs(0d);
					resultOptometryDO.setSecondCheckVd(0d);
					resultOptometryDO.setSecondCheckRps(0d);
					resultOptometryDO.setSecondCheckLps(0d);
					resultOptometryDO.setSecondCheckRcs(0d);
					resultOptometryDO.setSecondCheckLcs(0d);
					resultOptometryDO.setDeleteFlag(0);
					resultOptometryDO.setFirstCheckPd(0d);
					resultOptometryDO.setSecondCheckPd(0d);
					if (resultOptometryService.save(resultOptometryDO) > 0) {
						ResultDiopterDO rd;
						rd = new ResultDiopterDO();
						rd.settOptometryId(resultOptometryDO.gettOptometryId());
						rd.setDiopterS(Double.parseDouble(diopterSD) );
						rd.setDiopterC(Double.parseDouble(diopterC) );
						rd.setDiopterA(Double.parseDouble(diopterA) );
						rd.setBelieve(0);
						rd.setNum(0);
						rd.setType("AVG");
						rd.setIfrl("R");
						rd.setFirstSecond("FIRST_CHECK");
						rd.setDengxiaoqiujing(yd);
						rd.setActivityId(43);
						resultDiopterService.save(rd);

						rd = new ResultDiopterDO();
						rd.settOptometryId(resultOptometryDO.gettOptometryId());
						rd.setDiopterS(Double.parseDouble(diopterSS));
						rd.setDiopterC(Double.parseDouble(diopterCS));
						rd.setDiopterA(Double.parseDouble(diopterAS) );
						rd.setBelieve(0);
						rd.setNum(0);
						rd.setType("AVG");
						rd.setIfrl("L");
						rd.setFirstSecond("FIRST_CHECK");
						rd.setDengxiaoqiujing(zd);
						rd.setActivityId(43);
						resultDiopterService.save(rd);





					}

				}

				num++;
				System.out.println(num);
			}
			return R.ok("上传成功,共增加[" + num + "]条");
		}
		finally{
			try {
				if(book!=null)
					book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
