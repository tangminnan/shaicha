package com.shaicha.information.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.ResultEyesightService;
import com.shaicha.information.service.ResultOptometryService;
import com.shaicha.information.service.StudentService;


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
	    return "information/student/shifanstudent";
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
		List<ResultOptometryDO> list = resultOptometryService.list(map);
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
	@PostMapping( "/daorudatijiguo")
	@ResponseBody
	@RequiresPermissions("information:student:student")
	public R daorudatijiguo(MultipartFile file){
		return studentService.daorudatijiguo(file);	
	}

	@GetMapping("/downloadErweima")
	public void  downloadErweima(Integer[] ids,HttpServletResponse response){
		studentService.downloadErweima(ids,response);
		System.out.println(ids);
	}
	/**
	 * 筛查结果导出
	 */
	@GetMapping("/shaichajieguodaochu")
	public void shaichajieguodaochu(Integer[] ids,HttpServletResponse response){
		studentService.shaichajieguodaochu(ids,response);
	}
	
	/**
	 * 示范校筛查结果导出
	 */
	@GetMapping("/shifanshaichajieguodaochu")
	public void shifanshaichajieguodaochu(Integer[] ids,HttpServletResponse response){
		studentService.shifanshaichajieguodaochu(ids,response);
	}
	
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
//	@GetMapping("/shaichajieguodaochu")
	public void exportWordPByFreemarker(Integer[] ids,HttpServletRequest request,  HttpServletResponse response){
		studentService.exportWordPByFreemarker(ids,request,response);
	}
	
	/**
	 * 示范校筛查结果导出（freemarker导出模式）
	 */
//	@GetMapping("/shifanshaichajieguodaochu")
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
		model.addAttribute("QRCode",studentDO.getQRCode());
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
		model.addAttribute("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
		model.addAttribute("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentService.getLatestResultEyesightDO(studentDO.getId(),studentDO.getLastCheckTime());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		model.addAttribute("nakedFarvisionOd",resultEyesightDO.getNakedFarvisionOd()==null? "":resultEyesightDO.getNakedFarvisionOd().toString());
		model.addAttribute("nakedFarvisionOs",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		model.addAttribute("correctionFarvisionOd",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
		model.addAttribute("correctionFarvisionOs",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentService.getLatestResultDiopterDOListL(studentDO.getId(),studentDO.getLastCheckTime(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSL",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		model.addAttribute("diopterCL",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		model.addAttribute("diopterAL",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList =studentService.getLatestResultDiopterDOListL(studentDO.getId(),studentDO.getLastCheckTime(),"R");
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
		if(studentDO==null || studentDO.getLastCheckTime()==null) return null;
		model.addAttribute("school", studentDO.getSchool());
		model.addAttribute("grade",studentDO.getGrade().toString());
		model.addAttribute("studentClass",studentDO.getStudentClass().toString());
		model.addAttribute("studentName",studentDO.getStudentName());
		model.addAttribute("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
		model.addAttribute("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentService.getLatestResultEyesightDO(studentDO.getId(),studentDO.getLastCheckTime());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		model.addAttribute("nakedFarvisionOd",resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString());
		model.addAttribute("nakedFarvisionOs",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		model.addAttribute("correctionFarvisionOd",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
		model.addAttribute("correctionFarvisionOs",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentService.getLatestResultDiopterDOListL(studentDO.getId(),studentDO.getLastCheckTime(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSL",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		model.addAttribute("diopterCL",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		model.addAttribute("diopterAL",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentService.getLatestResultDiopterDOListL(studentDO.getId(),studentDO.getLastCheckTime(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		model.addAttribute("diopterSR",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		model.addAttribute("diopterCR",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		model.addAttribute("diopterAR",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		//眼内压结果拼装
		List<ResultEyepressureDO> ResultEyepressureDOList = studentService.getLatestResultEyepressureDO(studentDO.getId(),studentDO.getLastCheckTime());
		ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
		if(ResultEyepressureDOList.size()>0)
			resultEyepressureDO=ResultEyepressureDOList.get(0);
		model.addAttribute("eyePressureOd",resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd().toString());
		model.addAttribute("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs().toString());
		//眼轴长度数据拼装
		List<ResultEyeaxisDO> resultEyeaxisDOList = studentService.getLatelestResultEyeaxisDO(studentDO.getId(),studentDO.getLastCheckTime());
		ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
		if(resultEyeaxisDOList.size()>0)
			resultEyeaxisDO=resultEyeaxisDOList.get(0);
		model.addAttribute("secondCheckOd",resultEyeaxisDO.getSecondCheckOd()==null?"":resultEyeaxisDO.getSecondCheckOd().toString());
		model.addAttribute("secondCheckOs", resultEyeaxisDO.getSecondCheckOs()==null?"":resultEyeaxisDO.getSecondCheckOs().toString());
		
		System.out.println("===========================");
		System.out.println("===========================");
		//临时数据拼装
		//报告临时数据
		model.addAttribute("nakedFarvisionOdb", "12");
		model.addAttribute("nakedFarvisionOsb", "11");
		model.addAttribute("diopterSRb", "500");
		model.addAttribute("diopterSLb", "300");
		model.addAttribute("correctionFarvisionOdb", "500");
		model.addAttribute("correctionFarvisionOsb", "300");
		model.addAttribute("eyePressureOdb", "1");
		model.addAttribute("secondCheckOsb", "1");
		model.addAttribute("beforeAfterOdValueb", "2");
		model.addAttribute("beforeAfterOsValueb", "2");
				//医生的建议（临时数据）
		model.addAttribute("doctorchubu","注意用眼卫生");
		model.addAttribute("doctortebie","注意用眼卫生，养成良好的用眼习惯");
		return "information/student/示范校筛查打印";
	}
}
