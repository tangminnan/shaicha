package com.shaicha.informationNEW.controller;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.w3c.dom.Document;

import com.shaicha.common.utils.PageUtils;
import com.shaicha.common.utils.QRCodeUtil;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.common.utils.TimeUtils;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.BuLiangShili;
import com.shaicha.informationNEW.domain.ActivityListNewDO;
import com.shaicha.informationNEW.domain.ResultCornealNewDO;
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;
import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;
import com.shaicha.informationNEW.domain.ResultEyesightNewDO;
import com.shaicha.informationNEW.domain.ResultOptometryNewDO;
import com.shaicha.informationNEW.domain.SchoolNewDO;
import com.shaicha.information.domain.ShiliJinShi;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.service.ActivityListNewService;
import com.shaicha.informationNEW.service.ResultEyesightNewService;
import com.shaicha.informationNEW.service.ResultOptometryNewService;
import com.shaicha.informationNEW.service.SchoolNewService;
import com.shaicha.informationNEW.service.StudentNewService;
import com.shaicha.system.dao.UserDao;
import com.shaicha.system.domain.UserDO;
import com.shaicha.system.service.UserService;


/**
 * 学生表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
 
@Controller
@RequestMapping("/informationNEW/student")
public class StudentNewController {
	@Autowired
	private StudentNewService studentNewService;
	@Autowired
	private ResultEyesightNewService resultEyesightNewService;
	@Autowired
	private ResultOptometryNewService resultOptometryNewService;
	@Autowired
	private SchoolNewService schoolService;
	@Autowired
	private ActivityListNewService activityListNewService;
	@Autowired
	UserService userMapper;
	
	@GetMapping()
	@RequiresPermissions("information:student:student")
	String Student(Model model){
		//List<StudentNewDO> studentList = studentNewService.getList();
		//model.addAttribute("studentList", studentList);
		Map<String, Object> params = new HashMap<>();
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			params.put("sysId", ShiroUtils.getUserId());
	    }
		List<SchoolNewDO> school = schoolService.list(params);
		model.addAttribute("school", school);
	    return "informationNEW/student/student";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:student:student")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("status", "0");
        query.put("checkType", "PU_TONG");
        if(!ShiroUtils.getUser().getUsername().equals("admin")){
        	query.put("sysId", ShiroUtils.getUserId());
        }
        List<StudentNewDO> studentList = studentNewService.list(query);
		int total = studentNewService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	/**
	 * 示范校筛查
	 */
	@GetMapping("/demonstration")
	public String demonstration(Model model){
		//List<StudentNewDO> studentList = studentNewService.getList();
		//model.addAttribute("studentList", studentList);
		Map<String, Object> params = new HashMap<>();
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			params.put("sysId", ShiroUtils.getUserId());
	    }
		List<SchoolNewDO> school = schoolService.list(params);
		model.addAttribute("school", school);
	    return "informationNEW/student/shifanstudent";
	}
	
	/**
	 * 示范校筛查
	 */
	@ResponseBody
	@GetMapping("/listshifan")
	public PageUtils listshifan(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("status", "0");
        query.put("checkType", "SHI_FANXIAO");
        if(!ShiroUtils.getUser().getUsername().equals("admin")){
        	query.put("sysId", ShiroUtils.getUserId());
        }
        List<StudentNewDO> studentList = studentNewService.list(query);
		int total = studentNewService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}

		
	
	@GetMapping("/add/{checkType}")
	@RequiresPermissions("information:student:add")
	String add(@PathVariable("checkType") String checkType,Model model){
		model.addAttribute("checkType", checkType);
		Map<String,Object> map = new HashMap<String, Object>();
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			map.put("sysId", ShiroUtils.getUserId());
        }
		List<SchoolNewDO> listSchool = schoolService.list(map);
		model.addAttribute("listSchool", listSchool);
		List<ActivityListNewDO> huodong = activityListNewService.list(map);
		model.addAttribute("huodong", huodong);
	    return "informationNEW/student/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:student:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		StudentNewDO student = studentNewService.get(id);
		model.addAttribute("student", student);
		Map<String,Object> map = new HashMap<String, Object>();
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			map.put("sysId", ShiroUtils.getUserId());
        }
		ActivityListNewDO activityname = activityListNewService.get(student.getActivityId());
		model.addAttribute("activityname", activityname.getActivityName());
		List<SchoolNewDO> listSchool = schoolService.list(map);
		model.addAttribute("listSchool", listSchool);
		List<ActivityListNewDO> huodong = activityListNewService.list(map);
		model.addAttribute("huodong", huodong);
	    return "informationNEW/student/edit";
	}
	
	@GetMapping("/code/{id}")
	@RequiresPermissions("information:student:edit")
	String code(@PathVariable("id") Integer id,Model model){
		StudentNewDO student = studentNewService.get(id);
		String identityCard = student.getIdentityCard();
		String code = QRCodeUtil.creatRrCode(identityCard+"JOIN"+id, 200,200);
		model.addAttribute("code", "data:image/png;base64,"+code);
		model.addAttribute("student", student);
	    return "informationNEW/student/QrCode";
	}
	
	@GetMapping("/detail/{id}")
	@RequiresPermissions("information:student:edit")
	String detail(@PathVariable("id") Integer id,Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		StudentNewDO student = studentNewService.get(id);
		List<ResultEyesightNewDO> list = resultEyesightNewService.list(map);
		model.addAttribute("list", list);
		model.addAttribute("student", student);
	    return "informationNEW/student/detailed";
	}
	
	@GetMapping("/optometry/{id}")
	@RequiresPermissions("information:student:edit")
	String optometry(@PathVariable("id") Integer id,Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		StudentNewDO student = studentNewService.get(id);
		map.put("studentId", id);
		List<ResultOptometryNewDO> list = resultOptometryNewService.list(map);
		list = list.stream().map(a -> {a.gettOptometryId();a.getCheckDate();return a;}).distinct().collect(Collectors.toList());
		model.addAttribute("list", list);
		model.addAttribute("student", student);
	    return "informationNEW/student/optometry";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:student:add")
	public R save( StudentNewDO student){
		SchoolNewDO schoolDO = schoolService.get(student.getSchoolId());
		student.setSchool(schoolDO.getOrgname());
		student.setAddress(schoolDO.getAreaname());
		student.setAddTime(new Date());
		student.setStatus(0);
		student.setModelType("学校");
		student.setSchoolCode(schoolDO.getOrgcode());
		student.setSysId(ShiroUtils.getUserId());
		student.setXueBu(schoolDO.getXuebu());
		if(studentNewService.save(student)>0){
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
	public R update( StudentNewDO student){
		if(null !=  student.getSchoolId()){
			SchoolNewDO schoolDO = schoolService.get(student.getSchoolId());
			student.setSchool(schoolDO.getOrgname());
			student.setAddress(schoolDO.getAreaname());
			student.setSchoolCode(schoolDO.getOrgcode());
			student.setXueBu(schoolDO.getXuebu());
		}
		StudentNewDO stu = studentNewService.get(student.getId());
		if(!stu.getIdentityCard().equals(student.getIdentityCard())){
			studentNewService.update(student);
			return R.ok("身份证号已修改，二维码已改变，请重新打印二维码进行筛查");
		}else{
			studentNewService.update(student);
			return R.ok();
		}
		//return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:student:remove")
	public R remove( Integer id){
		if(studentNewService.remove(id)>0){
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
		studentNewService.batchRemove(ids);
		return R.ok();
	}
	
	
	@GetMapping("/importtemplate/{checkType}")
	@RequiresPermissions("information:student:student")
	public String importtemplate(Model model,@PathVariable("checkType") String checkType){
		model.addAttribute("checkType", checkType);
		Map<String, Object> params = new HashMap<>();
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			params.put("sysId", ShiroUtils.getUserId());
	    }
		List<SchoolNewDO> listSchool = schoolService.list(params);
		model.addAttribute("listSchool", listSchool);
		List<ActivityListNewDO> huodong = activityListNewService.list(params);
		model.addAttribute("huodong", huodong);
		if("PU_TONG".equals(checkType)){
			return "informationNEW/student/importtemplate";
		}
		if("SHI_FANXIAO".equals(checkType))
			return "informationNEW/student/shifanimporttemplate";
		return null;  
	}
	
	
	@GetMapping("/datidaoru")
	@RequiresPermissions("information:student:student")
	public String datidaoru(){
		return "informationNEW/student/answer";
	}
	
	/**
	 * 导入
	 */
	@PostMapping( "/importMember")
	@ResponseBody
	@RequiresPermissions("information:student:student")
	public R importMember(Integer activityId,Integer schoolId,String checkType, MultipartFile file){
		return studentNewService.importMember(activityId,schoolId,checkType,file);
		
	}
	
	/**
	 * 答题导入
	 */
	@PostMapping( "/daorudatijiguo")
	@ResponseBody
	@RequiresPermissions("information:student:student")
	public R daorudatijiguo(MultipartFile file){
		return studentNewService.daorudatijiguo(file);	
	}

	@GetMapping("/downloadErweima")
	public void  downloadErweima(Integer[] ids,HttpServletRequest request,HttpServletResponse response){
		studentNewService.downloadErweima(ids,request,response);
		System.out.println(ids);
	}
	
	
	
	
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
		return "informationNEW/student/datijieguoR";
	}
	
	@ResponseBody
	@GetMapping("/listDati")
	@RequiresPermissions("information:student:student")
	public PageUtils listDati(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AnswerResultDO> studentList = studentNewService.listDati(query);
		int total = studentNewService.countDati(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	
	/**
	 * 普通筛查导出（freemarker导出模式）
	 */
	@GetMapping("/shaichajieguodaochu")
	public void exportWordPByFreemarker(Integer[] ids,HttpServletRequest request,  HttpServletResponse response){
		studentNewService.exportWordPByFreemarker(ids,request,response);
	}
	
	/**
	 * 示范校筛查结果导出（freemarker导出模式）
	 */
	@GetMapping("/shifanshaichajieguodaochu")
	public void exportWordPBByFreemarkerSHIfanxiao(Integer[] ids,HttpServletRequest request,  HttpServletResponse response){
		studentNewService.exportWordPBByFreemarkerSHIfanxiao(ids,request,response);
	}
	
	/**
	 * 浏览器打印二维码
	 */
	@GetMapping("/downLoadErWeiMaByBrower")
	public String downLoadErWeiMaByBrower(Integer id,Model model){
		StudentNewDO studentDO = Optional.ofNullable(studentNewService.get(id)).orElseGet(StudentNewDO::new);
		model.addAttribute("id",studentDO.getId());
		model.addAttribute("studentName",studentDO.getStudentName());
		model.addAttribute("identityCard",studentDO.getIdentityCard());
		model.addAttribute("studentSex",studentDO.getStudentSex());
		model.addAttribute("school",studentDO.getSchool());
		model.addAttribute("grade",studentDO.getGrade());
		model.addAttribute("studentClass",studentDO.getStudentClass());
		String identityCard = studentDO.getIdentityCard();
		String code = QRCodeUtil.creatRrCode(identityCard+"JOIN"+id, 200,200);
		model.addAttribute("QRCode", "data:image/png;base64,"+code);
		//model.addAttribute("QRCode",studentDO.getQRCode());
		return "informationNEW/student/二维码";
	}
	
	/**
	 * 普通筛查打印
	 */
	
	
	@GetMapping("/putongshaichadayin")
	public String putongshaichadayin(Integer id,Model model){
		
		//基本信息获取
				StudentNewDO studentDO = studentNewService.get(id);
				if(studentDO==null || studentDO.getLastCheckTime()==null) return "information/student/示范校筛查打印";
				model.addAttribute("school", studentDO.getSchool());
				model.addAttribute("grade",studentDO.getGrade().toString());
				model.addAttribute("studentClass",studentDO.getStudentClass().toString());
				model.addAttribute("studentName",studentDO.getStudentName());
				model.addAttribute("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "男":"女");
				model.addAttribute("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
				DecimalFormat df = new DecimalFormat("0.00");
				DecimalFormat df1 = new DecimalFormat("0.0");
				UserDO userDO ;
				if(ShiroUtils.getUser().getUsername().equals("admin")){
					 userDO = userMapper.get(studentDO.getSysId());
				}else{
					 userDO = userMapper.get(ShiroUtils.getUserId());
				}
				model.addAttribute("zhongxin",userDO);
				//视力检查结果获取
				List<ResultEyesightNewDO> resultEyesightDOList = studentNewService.getLatestResultEyesightDO(studentDO.getId());
				ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
				String nakedFarvisionOd="";
				String nakedFarvisionOs="";
				String correctionFarvisionOd="";
				String correctionFarvisionOs="";
				if(resultEyesightDOList.size()>0){
					resultEyesightDO=resultEyesightDOList.get(0);
					nakedFarvisionOd=resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString();
					nakedFarvisionOs=resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString();
					correctionFarvisionOd=resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString();
					correctionFarvisionOs=resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString();
				}
				model.addAttribute("nakedFarvisionOd",zhuanhuan1(nakedFarvisionOd)==""?"":zhuanhuan1(nakedFarvisionOd));
				model.addAttribute("nakedFarvisionOs",zhuanhuan1(nakedFarvisionOs)==""?"":zhuanhuan1(nakedFarvisionOs));
				model.addAttribute("glassvisionOd",zhuanhuan1(correctionFarvisionOd)==""?"":zhuanhuan1(correctionFarvisionOd));
				model.addAttribute("glassvisionOs",zhuanhuan1(correctionFarvisionOd)==""?"":zhuanhuan1(correctionFarvisionOs));
				
				
				//自动电脑验光结果(左眼) 
				double dengxiaoqiujingL = 0.0,dengxiaoqiujingR=0.0;
				List<ResultDiopterNewDO> resultDiopterDOList = studentNewService.getLatestResultDiopterDOListL(studentDO.getId(),"L");
				ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
				if(resultDiopterDOList.size()>0)
					resultDiopterDO=resultDiopterDOList.get(0);

					String diopterSL="";
					if(resultDiopterDO.getDiopterS()!=null){
						diopterSL = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
						if(Double.valueOf(diopterSL)>0){
							diopterSL="+"+diopterSL;
						}
					}
					
				model.addAttribute("diopterSL",diopterSL);
				model.addAttribute("diopterCL",resultDiopterDO.getDiopterC()==null?"":df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
				model.addAttribute("diopterAL",resultDiopterDO.getDiopterA()==null?"":zhuanhuan(resultDiopterDO.getDiopterA().toString()));
				dengxiaoqiujingL=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
				
				
				//自动电脑验光结果(右眼) 
				 resultDiopterDOList = studentNewService.getLatestResultDiopterDOListL(studentDO.getId(),"R");
				 resultDiopterDO = new ResultDiopterNewDO();
				if(resultDiopterDOList.size()>0)
					resultDiopterDO=resultDiopterDOList.get(0);
					String diopterSR="";
					if(resultDiopterDO.getDiopterS()!=null){
						diopterSR = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
						if(Double.valueOf(diopterSR)>0){
							diopterSR="+"+diopterSR;
						}
					}
					
				model.addAttribute("diopterSR",diopterSR);
				model.addAttribute("diopterCR",resultDiopterDO.getDiopterC()==null?"":df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
				model.addAttribute("diopterAR",resultDiopterDO.getDiopterA()==null?"":zhuanhuan(resultDiopterDO.getDiopterA().toString()));
				dengxiaoqiujingR=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
				
				//眼内压结果拼装
				List<ResultEyepressureNewDO> ResultEyepressureDOList = studentNewService.getLatestResultEyepressureDO(studentDO.getId());
				ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
				if(ResultEyepressureDOList.size()>0)
					resultEyepressureDO=ResultEyepressureDOList.get(0);
				model.addAttribute("eyePressureOd",resultEyepressureDO.getEyePressureOd()==null?"":zhuanhuan(resultEyepressureDO.getEyePressureOd().toString()));
				model.addAttribute("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":zhuanhuan(resultEyepressureDO.getEyePressureOs().toString()));
				//眼轴长度数据拼装
				List<ResultEyeaxisNewDO> resultEyeaxisDOList = studentNewService.getLatelestResultEyeaxisDO(studentDO.getId());
				ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
				if(resultEyeaxisDOList.size()>0)
					resultEyeaxisDO=resultEyeaxisDOList.get(0);
				model.addAttribute("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":zhuanhuan(resultEyeaxisDO.getFirstCheckOd().toString()));
				model.addAttribute("secondCheckOs", resultEyeaxisDO.getFirstCheckOs()==null?"":zhuanhuan(resultEyeaxisDO.getFirstCheckOs().toString()));
				
				System.out.println("===========================");
				System.out.println("===========================");
				//角膜验光拼装
				ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
				List<ResultCornealNewDO> resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"R","R1");
				if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
				model.addAttribute("cornealMmr1R",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
				model.addAttribute("cornealDr1R", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
				resultCornealDO = new ResultCornealNewDO();
				resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"R","R2");
				if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
				model.addAttribute("cornealMmr2R",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
				model.addAttribute("cornealDr2R", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
				
				resultCornealDO = new ResultCornealNewDO();
			    resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"L","R1");
			    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
			    model.addAttribute("cornealMmr1L",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
			    model.addAttribute("cornealDr1L", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
				
				
			    
			    resultCornealDO = new ResultCornealNewDO();
			    resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"L","R2");
			    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);

			   model.addAttribute("cornealMmr2L",resultCornealDO.getCornealMm()==null?"0":zhuanhuan(resultCornealDO.getCornealMm()));
			   model.addAttribute("cornealDr2L", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
				//医生的建议
			   Date birthday = studentDO.getBirthday()==null?new Date():studentDO.getBirthday();
			   int birth = 0;
			   try {
				   birth = TimeUtils.getAgeByBirth(birthday);
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			   if(birth>=3 && birth<=5){
				   model.addAttribute("ifStu","1");
			   }else{
				   model.addAttribute("ifStu","2");
			   }
			   double od=0.0,os=0.0;
			   if(!StringUtils.isBlank(nakedFarvisionOd) && isDouble(nakedFarvisionOd)){
			    	od=Double.parseDouble(nakedFarvisionOd);
			    }
			    if(!StringUtils.isBlank(nakedFarvisionOs) && isDouble(nakedFarvisionOs)){
			    	os=Double.parseDouble(nakedFarvisionOs);
			    }
//			    od=od<os?od:os;
//			    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
			    double yuanjingshiliL=0,yuanjingshiliR=0;//原镜视力
			    String ssL="ss",ssR="ss";
			    if(!StringUtils.isBlank(correctionFarvisionOd) && isDouble(correctionFarvisionOd)){
//			    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
			    	yuanjingshiliR=Double.parseDouble(correctionFarvisionOd);
			    }
			    if(!StringUtils.isBlank(correctionFarvisionOs) && isDouble(correctionFarvisionOs)){
//			    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
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
		return "informationNEW/student/普通筛查打印";
	}
	
	/**
	 * 示范校筛查打印
	 */
	
	@GetMapping("/shifanshaichadayin")
	public String shifanshaichadayin(Integer id,Model model){
		//基本信息获取
		StudentNewDO studentDO = studentNewService.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return "information/student/示范校筛查打印";
		model.addAttribute("school", studentDO.getSchool());
		model.addAttribute("grade",studentDO.getGrade().toString());
		model.addAttribute("studentClass",studentDO.getStudentClass().toString());
		model.addAttribute("studentName",studentDO.getStudentName());
		model.addAttribute("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "男":"女");
		model.addAttribute("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df1 = new DecimalFormat("0.0");
		UserDO userDO ;
		if(ShiroUtils.getUser().getUsername().equals("admin")){
			 userDO = userMapper.get(studentDO.getSysId());
		}else{
			 userDO = userMapper.get(ShiroUtils.getUserId());
		}
		model.addAttribute("zhongxin",userDO);
		//视力检查结果获取
		List<ResultEyesightNewDO> resultEyesightDOList = studentNewService.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
		String nakedFarvisionOd="";
		String nakedFarvisionOs="";
		String correctionFarvisionOd="";
		String correctionFarvisionOs="";
		if(resultEyesightDOList.size()>0){
			resultEyesightDO=resultEyesightDOList.get(0);
			nakedFarvisionOd=resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString();
			nakedFarvisionOs=resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString();
			correctionFarvisionOd=resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString();
			correctionFarvisionOs=resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString();
		}
		model.addAttribute("nakedFarvisionOd",zhuanhuan1(nakedFarvisionOd)==""?"":zhuanhuan1(nakedFarvisionOd));
		model.addAttribute("nakedFarvisionOs",zhuanhuan1(nakedFarvisionOs)==""?"":zhuanhuan1(nakedFarvisionOs));
		model.addAttribute("glassvisionOd",zhuanhuan1(correctionFarvisionOd)==""?"":zhuanhuan1(correctionFarvisionOd));
		model.addAttribute("glassvisionOs",zhuanhuan1(correctionFarvisionOd)==""?"":zhuanhuan1(correctionFarvisionOs));
		
		
		//自动电脑验光结果(左眼) 
		double dengxiaoqiujingL = 0.0,dengxiaoqiujingR=0.0;
		List<ResultDiopterNewDO> resultDiopterDOList = studentNewService.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);

			String diopterSL="";
			if(resultDiopterDO.getDiopterS()!=null){
				diopterSL = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
				if(Double.valueOf(diopterSL)>0){
					diopterSL="+"+diopterSL;
				}
			}
			
		model.addAttribute("diopterSL",diopterSL);
		model.addAttribute("diopterCL",resultDiopterDO.getDiopterC()==null?"":df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
		model.addAttribute("diopterAL",resultDiopterDO.getDiopterA()==null?"":zhuanhuan(resultDiopterDO.getDiopterA().toString()));
		dengxiaoqiujingL=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentNewService.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterNewDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
			String diopterSR="";
			if(resultDiopterDO.getDiopterS()!=null){
				diopterSR = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
				if(Double.valueOf(diopterSR)>0){
					diopterSR="+"+diopterSR;
				}
			}
			
		model.addAttribute("diopterSR",diopterSR);
		model.addAttribute("diopterCR",resultDiopterDO.getDiopterC()==null?"":df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
		model.addAttribute("diopterAR",resultDiopterDO.getDiopterA()==null?"":zhuanhuan(resultDiopterDO.getDiopterA().toString()));
		dengxiaoqiujingR=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
		
		//眼内压结果拼装
		List<ResultEyepressureNewDO> ResultEyepressureDOList = studentNewService.getLatestResultEyepressureDO(studentDO.getId());
		ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
		if(ResultEyepressureDOList.size()>0)
			resultEyepressureDO=ResultEyepressureDOList.get(0);
		model.addAttribute("eyePressureOd",resultEyepressureDO.getEyePressureOd()==null?"":zhuanhuan(resultEyepressureDO.getEyePressureOd().toString()));
		model.addAttribute("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":zhuanhuan(resultEyepressureDO.getEyePressureOs().toString()));
		//眼轴长度数据拼装
		List<ResultEyeaxisNewDO> resultEyeaxisDOList = studentNewService.getLatelestResultEyeaxisDO(studentDO.getId());
		ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
		if(resultEyeaxisDOList.size()>0)
			resultEyeaxisDO=resultEyeaxisDOList.get(0);
		model.addAttribute("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":zhuanhuan(resultEyeaxisDO.getFirstCheckOd().toString()));
		model.addAttribute("secondCheckOs", resultEyeaxisDO.getFirstCheckOs()==null?"":zhuanhuan(resultEyeaxisDO.getFirstCheckOs().toString()));
		
		System.out.println("===========================");
		System.out.println("===========================");
		//角膜验光拼装
		ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
		List<ResultCornealNewDO> resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"R","R1");
		if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
		model.addAttribute("cornealMmr1R",resultCornealDO.getCornealD()==null?"0":zhuanhuan(resultCornealDO.getCornealD()));
		model.addAttribute("cornealDr1R", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		resultCornealDO = new ResultCornealNewDO();
		resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"R","R2");
		if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
		model.addAttribute("cornealMmr2R",resultCornealDO.getCornealD()==null?"0":zhuanhuan(resultCornealDO.getCornealD()));
		model.addAttribute("cornealDr2R", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		
		resultCornealDO = new ResultCornealNewDO();
	    resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"L","R1");
	    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
	    model.addAttribute("cornealMmr1L",resultCornealDO.getCornealD()==null?"0":zhuanhuan(resultCornealDO.getCornealD()));
	    model.addAttribute("cornealDr1L", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		
		
	    
	    resultCornealDO = new ResultCornealNewDO();
	    resultCornealDOList = studentNewService.getResultCornealDOList(studentDO.getId(),"L","R2");
	    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);

	   model.addAttribute("cornealMmr2L",resultCornealDO.getCornealD()==null?"0":zhuanhuan(resultCornealDO.getCornealD()));
	   model.addAttribute("cornealDr2L", resultCornealDO.getCornealDeg()==null?"0":resultCornealDO.getCornealDeg());
		//医生的建议
	   Date birthday = studentDO.getBirthday()==null?new Date():studentDO.getBirthday();
	   int birth = 0;
	   try {
		   birth = TimeUtils.getAgeByBirth(birthday);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   if(birth>=3 && birth<=5){
		   model.addAttribute("ifStu","1");
	   }else{
		   model.addAttribute("ifStu","2");
	   }
	   double od=0.0,os=0.0;
	   if(!StringUtils.isBlank(nakedFarvisionOd) && isDouble(nakedFarvisionOd)){
	    	od=Double.parseDouble(nakedFarvisionOd);
	    }
	    if(!StringUtils.isBlank(nakedFarvisionOs) && isDouble(nakedFarvisionOs)){
	    	os=Double.parseDouble(nakedFarvisionOs);
	    }
//	    od=od<os?od:os;
//	    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
	    double yuanjingshiliL=0,yuanjingshiliR=0;//原镜视力
	    String ssL="ss",ssR="ss";
	    if(!StringUtils.isBlank(correctionFarvisionOd) && isDouble(correctionFarvisionOd)){
//	    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
	    	yuanjingshiliR=Double.parseDouble(correctionFarvisionOd);
	    }
	    if(!StringUtils.isBlank(correctionFarvisionOs) && isDouble(correctionFarvisionOs)){
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
		return "informationNEW/student/示范校筛查打印";
	}
	
	/**
	 * 查看筛查详情
	 */
	@GetMapping("/shifanshaichadetail")
	public String shifanshaichadetail(String identityCard,Model model){
		model.addAttribute("identityCard",identityCard);
		return "informationNEW/student/shifanshaichadetail";
	}
	
	
	/**
	 * 计算近视率	
	 */
	
	/*@ResponseBody
	@GetMapping("/getJInShiLv")
	public Map<String,Object> getJInShiLv(Date startDate,Date endDate){
		
		return  studentService.getJInShiLv(startDate,endDate);		
	}*/
	
	/**
	 * 男生女生近期近视率
	 */
	/*@GetMapping("/getJInShiLvSex")
	@ResponseBody
	public Map<String,Object> getJInShiLvSex(Date startDate,Date endDate){
		return  studentService.getJInShiLvSex( startDate,endDate);	
	}*/
	
	/**
	 * 教育局
	 * @throws ParseException 
	 */
	
//	@GetMapping("/getee")
//	public Map<String,Object>  getee() throws ParseException{
//		Map<String,Object> map =  studentService.createDataToJiAOYuJu(new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-10"),new Date());
//		System.out.println(map);
//		return map;
//	}
	
	/**
	 * 首页真实数据展示
	 */
	@ResponseBody
	@GetMapping("/shouYeTrueData")
	public Map<String,Double> shouYeTrueData(){
		return studentNewService.shouYeTrueData();	
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
	
	private static Object zhuanhuan1(Object s){
		DecimalFormat df1 = new DecimalFormat("0.0");
	       String d=null;
	        if(s instanceof String){
	        	if("".equals((String)s))
	        		return "";
	        	d = (String)s;
	        }
	        if(s instanceof Double)
	            d = df1.format(s);
	        System.out.println("d:"+d+" s:"+s);
	        
	        return d;
	    }
	

	public boolean isDouble( String s ){
		
        boolean matches = s.matches("-?[0-9]+.*[0-9]*");	
        
        return matches;

	}

	
	@GetMapping("/batchdayinerweima")
	public String batchdayinerweima(Integer[] ids,Model model){
		List<StudentNewDO> list = new ArrayList<>();
		for (Integer id : ids) {
			StudentNewDO studentDO = Optional.ofNullable(studentNewService.get(id)).orElseGet(StudentNewDO::new);
			String identityCard = studentDO.getIdentityCard();
			String code = QRCodeUtil.creatRrCode(identityCard+"JOIN"+id, 200,200);
			studentDO.setQRCode("data:image/png;base64,"+code);
			list.add(studentDO);
		}
		model.addAttribute("student", list);
		return "informationNEW/student/batchdayinerweima";
		
	}
	
	
	
	@ResponseBody
	@GetMapping("/schoolGrade")
	public List<StudentNewDO> schoolGrade(String school){
		Long sysId = null;
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			sysId = ShiroUtils.getUserId();
        }
		List<StudentNewDO> schoolGrade = studentNewService.schoolGrade(school,sysId);
		
		return schoolGrade;
		
	}
	
	@ResponseBody
	@GetMapping("/schoolStuClass")
	public List<StudentNewDO> schoolStuClass(String school,String grade){
		Long sysId = null;
		if(!ShiroUtils.getUser().getUsername().equals("admin")){
			sysId = ShiroUtils.getUserId();
        }
		List<StudentNewDO> stuClass = studentNewService.schoolStudentClass(school,sysId,grade);
		
		return stuClass;
		
	}


}
