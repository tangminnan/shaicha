package com.shaicha.information.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaicha.common.config.BootdoConfig;
import com.shaicha.information.dao.LinShiUrlDao;
import com.shaicha.information.dao.ResultCornealDao;
import com.shaicha.information.dao.ResultDiopterDao;
import com.shaicha.information.dao.ResultEyeaxisDao;
import com.shaicha.information.dao.ResultEyepressureDao;
import com.shaicha.information.dao.ResultEyesightDao;
import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.domain.xueshengDO;
import com.shaicha.information.service.StudentReportService;

import freemarker.template.Configuration;
import freemarker.template.Template;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class StudentReportServiceImpl implements StudentReportService{

	@Autowired
	private ResultDiopterDao resultDiopterDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private ResultEyesightDao resultEyesightDao;
	@Autowired
	private ResultCornealDao resultCornealDao;
	@Autowired
	private ResultEyeaxisDao resultEyeaxisDao;
	@Autowired
	private ResultEyepressureDao resultEyepressureDao;
	@Autowired
	private LinShiUrlDao linShiUrlDao;

	
	//历年近视率走势图
	@Override
	public Map<String, List<Double>> overYearMyopia(String school){
		Map<String, List<Double>> mapP = new HashMap<String, List<Double>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer first = 0;
		Integer second = 0;
		Integer third = 0;
		Map<String, Object> map = new HashMap<String,Object>();
		List<StudentDO> list = studentDao.list(map);
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		
/*		Map<String, Object> mapLN = new HashMap<String,Object>();
		mapLN.put("school", school);
		mapLN.put("checkDate", "2017");
		List<ResultEyesightDO> liNian = resultEyesightDao.liNian(mapLN);
		//String jsonString = JSONObject.toJSONString(overYearMyopia);
		//System.out.println(jsonString);
		if(liNian.size()>0){
			for (ResultEyesightDO resultEyesightDO : liNian) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map1 = new HashMap<String,Object>();
						map1.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map1);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								first++;
							}
						}
					}
				}
			}
		}
		
		Map<String, Object> mapLN2 = new HashMap<String,Object>();
		mapLN2.put("school", school);
		mapLN2.put("checkDate", "2018");
		List<ResultEyesightDO> liNian2 = resultEyesightDao.liNian(mapLN2);
		if(liNian2.size()>0){
			for (ResultEyesightDO resultEyesightDO : liNian2) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map2 = new HashMap<String,Object>();
						map2.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map2);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								second++;
							}
						}
					}
				}
			}
		}*/
		
		Map<String, Object> mapLN3 = new HashMap<String,Object>();
		mapLN3.put("school", school);
		mapLN3.put("checkDate", sdf.format(xian));
		List<ResultEyesightDO> liNian3 = resultEyesightDao.liNian(mapLN3);
		if(liNian3.size()>0){
			for (ResultEyesightDO resultEyesightDO : liNian3) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						map3.put("checkDate", sdf.format(xian));
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								third++;
							}
						}
					}
				}
			}
		}			
		
		/*System.out.println(first);
		System.out.println(second);
		System.out.println(third);*/
		List<Double> da = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("0.0");
		//da.add(Double.parseDouble(df.format((double)first/(double)liNian1.size()*100)));
		//da.add(Double.parseDouble(df.format((double)second/(double)liNian2.size()*100)));
		da.add(79.30);
		da.add(66.10);
		

		da.add(liNian3.size()==0?0:Double.parseDouble(df.format((double)third/(double)liNian3.size()*100)));
		
		mapP.put("overYearMyopia", da);
		

		return mapP;
	}
	
	//各年级近视
	@Override
	public Map<String, List<Double>> gradeMyopia(String school,String checkDate) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, List<Double>> gradeMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
			Date qian = null;
			Date hou = null;
			Date currdate = null;
			try {
				currdate = sdf1.parse(checkDate);
				/*Calendar ca = Calendar.getInstance();
				ca.setTime(currdate);
				ca.add(Calendar.DAY_OF_MONTH, -14);
				qian = ca.getTime();*/
				
				Calendar ca2 = Calendar.getInstance();
				ca2.setTime(currdate);
				ca2.add(Calendar.DAY_OF_MONTH, 14);
				hou = ca2.getTime();
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<StudentDO> gegradejs = studentDao.querySchoolGrade(school);
			for (StudentDO studentDO : gegradejs) {
				double gradeMyopiaFS = gradeMyopia(studentDO.getGrade(),school,currdate,hou);
				myt.add(gradeMyopiaFS);
			}
			
		gradeMyopia.put("gradeMyopia", myt);
		return gradeMyopia;
		
	}
	
	public double gradeMyopia(String grade,String school,
			@RequestParam(value= "start",required=false) Date start,
			@RequestParam(value= "end",required=false) Date end) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("grade", grade);
		mapP.put("school", school);
		mapP.put("start", start);
		mapP.put("end", end);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.queryMyopia(resultEyesightDO.getIdentityCard(),start,end);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat("0.0");
			parseDouble = Double.parseDouble(df.format((double)first/(double)jinShi.size()*100));		
				
		}			
		
		/*String jsonString = JSONObject.toJSONString(gradeMyopiaFS);
		System.out.println(jsonString);*/
		return parseDouble;				
	}
	
	public double gradeMyopiaLN(String grade,String school,@RequestParam(value= "checkDate",required=false) String checkDate) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("grade", grade);
		mapP.put("school", school);
		mapP.put("checkDate", checkDate);
		List<ResultEyesightDO> jinShi = resultEyesightDao.liNian(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						map3.put("checkDate", checkDate);
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat("0.0");
			parseDouble = Double.parseDouble(df.format((double)first/(double)jinShi.size()*100));		
				
		}			
		
		/*String jsonString = JSONObject.toJSONString(gradeMyopiaFS);
		System.out.println(jsonString);*/
		return parseDouble;				
	}
	
	//历年各年级近视率走势图
	@Override
	public Map<String, List<Double>> overYearGradeMyopia(String school) {
		Map<String, List<Double>> gradeMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		List<Double> myt2 = new ArrayList<Double>();
		List<Double> myt3 = new ArrayList<Double>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		/*Map<String, Object> gradeMyopiaFS = gradeMyopiaLN("一年级",school,"2017");
		Map<String, Object> gradeMyopiaSE = gradeMyopiaLN("二年级",school,"2017");
		Map<String, Object> gradeMyopiaTH = gradeMyopiaLN("三年级",school,"2017");
		Map<String, Object> gradeMyopiaFO = gradeMyopiaLN("四年级",school,"2017");
		Map<String, Object> gradeMyopiaFI = gradeMyopiaLN("五年级",school,"2017");
		Map<String, Object> gradeMyopiaSI = gradeMyopiaLN("六年级",school,"2017");
		
		Object gradeFS = gradeMyopiaFS.get("gradeMyopia");
		Object gradeSE = gradeMyopiaSE.get("gradeMyopia");
		Object gradeTH = gradeMyopiaTH.get("gradeMyopia");
		Object gradeFO = gradeMyopiaFO.get("gradeMyopia");
		Object gradeFI = gradeMyopiaFI.get("gradeMyopia");
		Object gradeSI = gradeMyopiaSI.get("gradeMyopia");
		myt.add((double)gradeFS);
		myt.add((double)gradeSE);
		myt.add((double)gradeTH);
		myt.add((double)gradeFO);
		myt.add((double)gradeFI);
		myt.add((double)gradeSI);*/
		myt.add(26.7);
		myt.add(26.5);
		myt.add(39.2);
		myt.add(35.4);
		myt.add(42.6);
		myt.add(39.3);
		gradeMyopia.put("seventeen", myt);
		/*Map<String, Object> gradeMyopiaFS2 = gradeMyopiaLN("一年级",school,"2018");
		Map<String, Object> gradeMyopiaSE2 = gradeMyopiaLN("二年级",school,"2018");
		Map<String, Object> gradeMyopiaTH2 = gradeMyopiaLN("三年级",school,"2018");
		Map<String, Object> gradeMyopiaFO2 = gradeMyopiaLN("四年级",school,"2018");
		Map<String, Object> gradeMyopiaFI2 = gradeMyopiaLN("五年级",school,"2018");
		Map<String, Object> gradeMyopiaSI2 = gradeMyopiaLN("六年级",school,"2018");
		
		Object gradeFS2 = gradeMyopiaFS2.get("gradeMyopia");
		Object gradeSE2 = gradeMyopiaSE2.get("gradeMyopia");
		Object gradeTH2 = gradeMyopiaTH2.get("gradeMyopia");
		Object gradeFO2 = gradeMyopiaFO2.get("gradeMyopia");
		Object gradeFI2 = gradeMyopiaFI2.get("gradeMyopia");
		Object gradeSI2 = gradeMyopiaSI2.get("gradeMyopia");
		myt2.add((double)gradeFS2);
		myt2.add((double)gradeSE2);
		myt2.add((double)gradeTH2);
		myt2.add((double)gradeFO2);
		myt2.add((double)gradeFI2);
		myt2.add((double)gradeSI2);*/
		myt2.add(25.5);
		myt2.add(25.7);
		myt2.add(35.2);
		myt2.add(33.8);
		myt2.add(40.1);
		myt2.add(37.7);
		gradeMyopia.put("eighteen", myt2);
			List<StudentDO> gegradejs = studentDao.querySchoolGrade(school);
			for (StudentDO studentDO : gegradejs) {
				double gradeMyopiaFS3 = gradeMyopiaLN(studentDO.getGrade(),school,sdf1.format(xian));
				myt3.add(gradeMyopiaFS3);
			}
		gradeMyopia.put("nineteen", myt3);
		
		
		return gradeMyopia;
	}

	//男、女生近视
	@Override
	public Map<String, List<Double>> studentSexMyopia(String school,String checkDate) {
		Map<String, List<Double>> studentSexMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date hou = null;
			Date currdate = null;
			try {
				currdate = sdf1.parse(checkDate);
				Calendar ca2 = Calendar.getInstance();
				ca2.setTime(currdate);
				ca2.add(Calendar.DAY_OF_MONTH, 14);
				hou = ca2.getTime();
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		double overYearSex = overYearSex(1,school,null,currdate,hou);//男
		myt.add(overYearSex);
		
		double overYearSex2 = overYearSex(2,school,null,currdate,hou);
		myt.add(overYearSex2);
		
		studentSexMyopia.put("studentSexMyopia",myt);
		return studentSexMyopia;
	}

	//历年男生近视率走势图
	@Override
	public Map<String, List<Double>> overYearSexNan(String school) {
		Map<String, List<Double>> studentSexMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		/*Map<String, Object> overYearSex = overYearSexLN(1,school,null,"2017");
		Object overYearSexNan = overYearSex.get("overYearSex");
		myt.add((double)overYearSexNan);
		Map<String, Object> overYearSex2 = overYearSexLN(1,school,null,"2018");
		Object overYearSexNan2 = overYearSex2.get("overYearSex");
		myt.add((double)overYearSexNan2);*/
		myt.add(78.9);
		myt.add(66.2);
		double overYearSex3 = overYearSexLN(1,school,null,sdf1.format(xian));
		myt.add(overYearSex3);
		studentSexMyopia.put("studentSexMyopia",myt);
		return studentSexMyopia;
	}

	//历年女生近视率走势图
	@Override
	public Map<String, List<Double>> overYearSexNv(String school) {
		Map<String, List<Double>> studentSexMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		/*Map<String, Object> overYearSex = overYearSexLN(2,school,null,"2017");
		Object overYearSexNv = overYearSex.get("overYearSex");
		myt.add((double)overYearSexNv);
		Map<String, Object> overYearSex2 = overYearSexLN(2,school,null,"2018");
		Object overYearSexNv2 = overYearSex2.get("overYearSex");
		myt.add((double)overYearSexNv2);*/
		myt.add(76.7);
		myt.add(60.5);
		double overYearSex3 = overYearSexLN(2,school,null,sdf1.format(xian));
		myt.add(overYearSex3);
		studentSexMyopia.put("studentSexMyopia",myt);
		return studentSexMyopia;
		
	}
	
	public double overYearSex(Integer studentSex,String school,
			@RequestParam(value= "grade",required=false)String grade,
			@RequestParam(value= "start",required=false) Date start,
			@RequestParam(value= "end",required=false) Date end) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("grade", grade);
		mapP.put("start", start);
		mapP.put("end", end);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.queryMyopia(resultEyesightDO.getIdentityCard(),start,end);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat("0.0");
			parseDouble = Double.parseDouble(df.format((double)first/(double)jinShi.size()*100));		
				
		}	

		return parseDouble;
	}
	
	public double overYearSexLN(Integer studentSex,String school,
			@RequestParam(value= "grade",required=false)String grade,
			@RequestParam(value= "checkDate",required=false) String checkDate) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("grade", grade);
		mapP.put("checkDate", checkDate);
		List<ResultEyesightDO> jinShi = resultEyesightDao.liNian(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						map3.put("checkDate", checkDate);
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat("0.0");
			parseDouble = Double.parseDouble(df.format((double)first/(double)jinShi.size()*100));		
				
		}	

		return parseDouble;
	}

	//男女生近视年级
	@Override
	public Map<String, List<Double>> overYearGradeSex(String school,String checkDate) {
		Map<String, List<Double>> overYearGradeSex = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		List<Double> myt2 = new ArrayList<Double>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date hou = null;
		Date currdate = null;
		try {
			currdate = sdf1.parse(checkDate);
			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(currdate);
			ca2.add(Calendar.DAY_OF_MONTH, 14);
			hou = ca2.getTime();
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<StudentDO> gegradejs = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gegradejs) {
			double overYearSexNan = overYearSex(1,school,studentDO.getGrade(),currdate,hou);
			myt.add(overYearSexNan);
		}
		for (StudentDO studentDO : gegradejs) {
			double overYearSexNv = overYearSex(2,school,studentDO.getGrade(),currdate,hou);
			myt2.add(overYearSexNv);
		}
		
		overYearGradeSex.put("overYearSexNan", myt);
		overYearGradeSex.put("overYearSexNv", myt2);
		
		return overYearGradeSex;
	}
	

	//学校数据
	public Map<String,Object> xuexiaobaogao(Date start,Date end,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		DecimalFormat df = new DecimalFormat("0.0");
		
		String school = request.getParameter("school");
		String checkDate = request.getParameter("checkDate");
		String date = request.getParameter("date");
		Integer schoolNum = Integer.parseInt(request.getParameter("schoolNum"));
		
		params.put("schoolName", school);
		params.put("newDate", sdf.format(new Date()));
		
		//图
		Map<String,Object> ls = new HashMap<>();
		ls.put("type", date);
		List<LinShiUrlDO> lsu = linShiUrlDao.list(ls);
		for (LinShiUrlDO linShiUrlDO : lsu) {
			params.put(linShiUrlDO.getName(), linShiUrlDO.getImgUrl());
			
			linShiUrlDao.remove(linShiUrlDO.getId());
		}

		//基本情况
		Integer jiancha = jiancha(school,start,end,null,null);
		if(jiancha == -1){
			params.put("checkNum", 0);
		}else{
			params.put("checkNum", jiancha);
		}
		Map<String, Object> num = new HashMap<String, Object>(); 
		num.put("school", school);
		List<StudentDO> list = studentDao.list(num);
		params.put("schoolNum", list.size());
		params.put("checkRate", df.format((double)jiancha/((double)list.size())*100));
		
		List<Map<String,Object>> listg = new ArrayList<Map<String,Object>>();
		List<StudentDO> gradeshu = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradeshu) {
			Map<String,Object> gNum = new HashMap<>();
 			List<StudentDO> gradeNum = studentDao.queryGradeNum(studentDO.getGrade());
			if(gradeNum.size()>0){
				Integer jiancha2 = jiancha(school,start,end,null,studentDO.getGrade());
				gNum.put("nianji", studentDO.getGrade());
				gNum.put("gradeNum", gradeNum.size());
				if(jiancha2 == -1){
					gNum.put("gradeCheckNum", 0);
					gNum.put("gradeCheckRate", 0);
				}else{
					gNum.put("gradeCheckNum", jiancha2);
					gNum.put("gradeCheckRate", df.format((double)jiancha2/((double)gradeNum.size())*100));
				}
			}
			listg.add(gNum);
		}
		params.put("nianjice", listg);
		
		//男女近视
		Integer nanjiancha = jiancha(school,start,end,1,null);
		Integer nanjinshi = jinshi(school,start,end,1,null,null);
		params.put("checkNanNum", nanjiancha);
		params.put("myopiaNanNum", nanjinshi);
		params.put("myopiaNanRate", df.format(((double)nanjinshi/(double)nanjiancha)*100));
		
		Integer nvjiancha = jiancha(school,start,end,2,null);
		Integer nvjinshi = jinshi(school,start,end,2,null,null);
		params.put("checkNvNum", nvjiancha);
		params.put("myopiaNvNum", nvjinshi);
		params.put("myopiaNvRate", df.format(((double)nvjinshi/(double)nvjiancha)*100));
	
		//男近视
		List<Map<String,Object>> listnn = new ArrayList<Map<String,Object>>();
		List<StudentDO> gradenn = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradenn) {
			Map<String,Object> nnNum = new HashMap<>();
			Integer nanjianchanianyi = jiancha(school,start,end,1,studentDO.getGrade());
			Integer nanjinshinianyi = jinshi(school,start,end,1,studentDO.getGrade(),null);
			if(nanjianchanianyi == -1){
				nnNum.put("checkNanNum", 0);
				nnNum.put("myopiaNanRate", 0);
			}else{
				nnNum.put("checkNanNum", nanjianchanianyi);
				nnNum.put("myopiaNanRate", df.format(((double)nanjinshinianyi/(double)nanjianchanianyi)*100));
			}
			nnNum.put("nj", studentDO.getGrade());
			nnNum.put("myopiaNanNum", nanjinshinianyi);
			listnn.add(nnNum);	
		}
		params.put("listnn", listnn);
		//女近视
		List<Map<String,Object>> listmm = new ArrayList<Map<String,Object>>();
		List<StudentDO> grademm = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : grademm) {
			Map<String,Object> mmNum = new HashMap<>();
			Integer nvjianchanianyi = jiancha(school,start,end,2,studentDO.getGrade());
			Integer nvjinshinianyi = jinshi(school,start,end,2,studentDO.getGrade(),null);
			if(nvjianchanianyi == -1){
				mmNum.put("checkNvNum", 0);
				mmNum.put("myopiaNvRate", 0);
			}else{
				mmNum.put("checkNvNum", nvjianchanianyi);
				mmNum.put("myopiaNvRate", df.format(((double)nvjinshinianyi/(double)nvjianchanianyi)*100));
			}
			mmNum.put("nj", studentDO.getGrade());
			mmNum.put("myopiaNvNum", nvjinshinianyi);
			listmm.add(mmNum);	
		}
		params.put("listmm", listmm);	
		
		//各班级近视率
		List<Map<String,Object>> listbj = new ArrayList<Map<String,Object>>();				
		
		List<StudentDO> gradebj = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradebj) {
			Map<String,Object> yi = new HashMap<String,Object>();
			List<Map<String,Object>> aa = new ArrayList<Map<String,Object>>();
			Map<String,Object> mapClassyi = new HashMap<String,Object>();
			mapClassyi.put("school", school);
			mapClassyi.put("grade", studentDO.getGrade());
			List<StudentDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
			for(int i= 1 ; i<=classCountyi.size();i++){
				Map<String,Object> classyi = new HashMap<String,Object>();
				classyi.put("class", i);
				Integer jcyi = jianchaban(school,start,end,String.valueOf(i), studentDO.getGrade());
				Integer jsyi = jinshi(school,start,end,null,studentDO.getGrade(),String.valueOf(i));
				if(jcyi == -1){
					classyi.put("classNum", 0);
					classyi.put("classMyopiaRate", 0);
				}else{
					classyi.put("classNum", jcyi);
					classyi.put("classMyopiaRate", df.format(((double)jsyi/(double)jcyi*100)));
				}
				classyi.put("classMyopiaNum", jsyi);
				aa.add(classyi);
			}
			yi.put("grade", studentDO.getGrade());
			yi.put("classyi", aa);
			listbj.add(yi);
		}
		params.put("firstClass", listbj);
			
		//不良
		List<Map<String,String>> listT = new ArrayList<Map<String,String>>();

		List<StudentDO> gradebl = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradebl) {
			Map<String,Object> dushuy = new HashMap<String,Object>();
			dushuy.put("school", school);
			dushuy.put("grade", studentDO.getGrade());
			dushuy.put("start", start);
			dushuy.put("end", end);
			List<ResultEyesightDO> shiLiy = resultEyesightDao.queryLuoYanShiLi(dushuy);
			Map<String,String> gradey = new HashMap<String,String>();
			int checkyi = shiLiy.size();
			int qingduyi = 0;
			int zhongduyi = 0;
			int zzhongduyi = 0;
			int buliangyi = 0;
			String qingduyiR = "0";
			String zhongduyiR= "0";
			String zzhongduyiR= "0";
			String buliangyiR= "0";
			if(shiLiy.size()>0){
				for (ResultEyesightDO resultEyesightDO : shiLiy) {
					if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
						if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
							qingduyi++;
						}
						if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
							zhongduyi++;
						}
						if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
							zzhongduyi++;
						}
						if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
							buliangyi++;
						}
					}
				}
				qingduyiR = df.format(((double)qingduyi/(double)checkyi)*100);
				zhongduyiR = df.format(((double)zhongduyi/(double)checkyi)*100);
				zzhongduyiR = df.format(((double)zzhongduyi/(double)checkyi)*100);
				buliangyiR =  df.format(((double)buliangyi/(double)checkyi)*100);
			}
			gradey.put("grade", studentDO.getGrade());
			gradey.put("xuebu", studentDO.getXueBu());
			gradey.put("gradeCheckNum", String.valueOf(checkyi));
			gradey.put("numQDSLBL", String.valueOf(qingduyi));
			gradey.put("rateQDSLBL", qingduyiR);
			gradey.put("numZDSLBL", String.valueOf(zhongduyi));
			gradey.put("rateZDSLBL", zhongduyiR);
			gradey.put("numZZDSLBL", String.valueOf(zzhongduyi));
			gradey.put("rateZZDSLBL", zzhongduyiR);
			gradey.put("numSLBL", String.valueOf(buliangyi));
			gradey.put("rateSLBL", buliangyiR);
			
			listT.add(gradey);
		}
		params.put("listbl", listT);
		
		int checkT = 0;
		int qingNT = 0;
		double qingRT =0.0;
		int zhongNT= 0;
		double zhongRT= 0.0;
		int zzhongNT= 0;
		double zzhongRT=0.0;
		int bulingNT=0;
		double bulingRT= 0.0;
		for (Map<String, String> map : listT) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("gradeCheckNum")){
					checkT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("numQDSLBL")){
					qingNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateQDSLBL")){
					qingRT += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numZDSLBL")){
					zhongNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateZDSLBL")){
					zhongRT += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numZZDSLBL")){
					zzhongNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateZZDSLBL")){
					zzhongRT += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numSLBL")){
					bulingNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateSLBL")){
					bulingRT += Double.parseDouble(m.getValue());
				}
			}
		}
		
		params.put("gradeCheckTotal", checkT);
		params.put("numQDSLBLTotal", qingNT);
		params.put("rateQDSLBLTotal", qingRT);
		params.put("numZDSLBLTotal", zhongNT);
		params.put("rateZDSLBLTotal", zhongRT);
		params.put("numZZDSLBLTotal", zzhongNT);
		params.put("rateZZDSLBLTotal", zzhongRT);
		params.put("numSLBLTotal", bulingNT);
		params.put("rateSLBLTotal", bulingRT);
		
		//近视
		List<Map<String,String>> jiajin = new ArrayList<Map<String,String>>();
		
		List<StudentDO> gradejs = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradejs) {
			Map<String,Object> dushuy = new HashMap<String,Object>();
			dushuy.put("school", school);
			dushuy.put("grade", studentDO.getGrade());
			dushuy.put("start", start);
			dushuy.put("end", end);
			List<ResultEyesightDO> shiLiy = resultEyesightDao.queryLuoYanShiLi(dushuy);
			Map<String,String> jia1 = new HashMap<String,String>();
			Integer linchuangy = 0;
			Integer jiajinshiy = 0;
			Integer diy = 0;
			Integer zhongy = 0;
			Integer gaoy = 0;
			String linchuangr = "0";
			String jiajinshir ="0";
			String dir = "0";
			String zhongr = "0";
			String gaor = "0";
			int jiay = shiLiy.size();
			if(shiLiy.size()>0){
				linchuangy = jiajinshi(school,start,end,studentDO.getGrade(),"1");
				jiajinshiy = jiajinshi(school,start,end,studentDO.getGrade(),"2");
				diy = jiajinshi(school,start,end,studentDO.getGrade(),"3");
				zhongy = jiajinshi(school,start,end,studentDO.getGrade(),"4");
				gaoy = jiajinshi(school,start,end,studentDO.getGrade(),"5");
				linchuangr = df.format(((double)linchuangy/(double)jiay)*100);
				jiajinshir = df.format(((double)jiajinshiy/(double)jiay)*100);
				dir = df.format(((double)diy/(double)jiay)*100);
				zhongr = df.format(((double)zhongy/(double)jiay)*100);
				gaor = df.format(((double)gaoy/(double)jiay)*100);
			}
			Integer jinzongy = diy+zhongy+gaoy;
			double jinzongr = Double.parseDouble(dir)+Double.parseDouble(zhongr)+Double.parseDouble(gaor);
			jia1.put("grade", studentDO.getGrade());
			jia1.put("xuebu", studentDO.getXueBu());
			jia1.put("gradeCheckNum", String.valueOf(jiay));
			jia1.put("numJSQQ", String.valueOf(linchuangy));
			jia1.put("rateJSQQ", linchuangr);
			jia1.put("numJXJS", String.valueOf(jiajinshiy));
			jia1.put("rateJXJS", jiajinshir);
			jia1.put("numDDJS", String.valueOf(diy));
			jia1.put("rateDDJS", dir);
			jia1.put("numZDJS", String.valueOf(zhongy));
			jia1.put("rateZDJS", zhongr);
			jia1.put("numGDJS", String.valueOf(gaoy));
			jia1.put("rateGDJS", gaor);
			jia1.put("numJS", String.valueOf(jinzongy));
			jia1.put("rateJS", String.valueOf(jinzongr));
			
			jiajin.add(jia1);
		}
		
		params.put("jiajin", jiajin);
		
		int jiaJS1 =0;
		double jiaJS2 = 0.0;
		int jiaJS3= 0;
		double jiaJS4= 0.0;
		int jiaJS5= 0;
		double jiaJS6= 0.0;
		int jiaJS7= 0;
		double jiaJS8= 0.0;
		int jiaJS9= 0;
		double jiaJS11= 0.0;
		int jiaJS22= 0;
		double jiaJS33=0.0;
		for (Map<String, String> map2 : jiajin) {
			for(Map.Entry<String, String> m : map2.entrySet()){
				if(m.getKey().equals("numJSQQ")){
					jiaJS1 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateJSQQ")){
					jiaJS2 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numJXJS")){
					jiaJS3 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateJXJS")){
					jiaJS4 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numDDJS")){
					jiaJS5 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateDDJS")){
					jiaJS6 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numZDJS")){
					jiaJS7 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateZDJS")){
					jiaJS8 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numGDJS")){
					jiaJS9 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateGDJS")){
					jiaJS11 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("numJS")){
					jiaJS22 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("rateJS")){
					jiaJS33 += Double.parseDouble(m.getValue());
				}
			}
		}
		
		params.put("numJSQQTotal", jiaJS1);
		params.put("rateJSQQTotal", jiaJS2);
		params.put("numJXJSTotal", jiaJS3);
		params.put("rateJXJSTotal", jiaJS4);
		params.put("numDDJSTotal", jiaJS5);
		params.put("rateDDJSTotal", jiaJS6);
		params.put("numZDJSTotal", jiaJS7);
		params.put("rateZDJSTotal", jiaJS8);
		params.put("numGDJSTotal", jiaJS9);
		params.put("rateGDJSTotal", jiaJS11);
		params.put("numJSTotal", jiaJS22);
		params.put("rateJSTotal", jiaJS33);
		
		//附件
		List<Map<String,Object>> xuesheng = new ArrayList<Map<String,Object>>();
		
		List<StudentDO> gradefj = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradefj) {
			Map<String,Object> mapClassyi = new HashMap<String,Object>();
			mapClassyi.put("school", school);
			mapClassyi.put("grade", studentDO.getGrade());
			List<StudentDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
			for(int i= 1; i<=classCountyi.size();i++){
				Map<String,Object> mapbb = new HashMap<String,Object>();
				List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
				List<StudentDO> grade = studentDao.queryStudentGrade(school,studentDO.getGrade(),start,end,String.valueOf(i));
				if(grade.size()>0){
				for (StudentDO studentDO2 : grade) {
					Map<String,Object> mapPP = new HashMap<String,Object>();
					ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
					ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
					ResultCornealDO resultCornealDO = new ResultCornealDO();
					ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
					ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
					List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getIdentityCard(),start,end);
					List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getIdentityCard(),start,end);
					List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getIdentityCard(),start,end);
					List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(), "R1",start,end);
					List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(), "R2",start,end);
					List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(), "R1",start,end);
					List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(), "R2",start,end);
					List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getIdentityCard(),start,end);
					List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getIdentityCard(),start,end);
					mapPP.put("studentName", studentDO2.getStudentName());
					mapPP.put("studentSex", studentDO2.getStudentSex());
					if(lifeShili.size()>0){
						resultEyesightDO = lifeShili.get(0);
						mapPP.put("nakedNearvisionOd", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
						mapPP.put("nakedNearvisionOs", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
						mapPP.put("lifeNearvisionOd", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
						mapPP.put("lifeNearvisionOs", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
					}else{
						mapPP.put("nakedNearvisionOd", "");
						mapPP.put("nakedNearvisionOs", "");
						mapPP.put("lifeNearvisionOd", "");
						mapPP.put("lifeNearvisionOs", "");
					}
					if(L.size()>0){
						resultDiopterDO = L.get(0);
						mapPP.put("diopterSOs", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
						mapPP.put("diopterCOs", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
						mapPP.put("diopterAOs", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("diopterSOs", "");
						mapPP.put("diopterCOs", "");
						mapPP.put("diopterAOs", "");
					}
					if(R.size()>0){
						resultDiopterDO = R.get(0);
						mapPP.put("diopterSOd", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
						mapPP.put("diopterCOd", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
						mapPP.put("diopterAOd", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("diopterSOd", "");
						mapPP.put("diopterCOd", "");
						mapPP.put("diopterAOd", "");
					}
					if(LR1.size()>0){
						resultCornealDO = LR1.get(0);
						mapPP.put("cornealR1Os", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("cornealR1Os", "");
					}
					if(LR2.size()>0){
						resultCornealDO = LR2.get(0);
						mapPP.put("cornealR2Os", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("cornealR2Os","");
					}
					if(RR1.size()>0){
						resultCornealDO = RR1.get(0);
						mapPP.put("cornealR1Od", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("cornealR1Od", "");
					}
					if(RR2.size()>0){
						resultCornealDO = RR2.get(0);
						mapPP.put("cornealR2Od", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("cornealR2Od", "");
					}
					if(eyeaxis.size()>0){
						resultEyeaxisDO = eyeaxis.get(0);
						mapPP.put("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
						mapPP.put("secondCheckOs",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
					}else{
						mapPP.put("secondCheckOd","");
						mapPP.put("secondCheckOs","");
					}
					if(eyepressure.size()>0){
						resultEyepressureDO = eyepressure.get(0);
						mapPP.put("eyePressureOd", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
						mapPP.put("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
					}else{
						mapPP.put("eyePressureOd", "");
						mapPP.put("eyePressureOs", "");
					}
					bb.add(mapPP);
				}
				mapbb.put("grade", studentDO.getGrade());
				mapbb.put("class", i);
				mapbb.put("chaDate", checkDate);
				mapbb.put("jieguo", bb);
				xuesheng.add(mapbb);
			}
			}
		}
		
		params.put("xuesheng", xuesheng);
		
		return params;
		
		
	}
	
	
	
	public Integer jiajinshi(String school,
			@RequestParam(value= "start",required=false) Date start,
			@RequestParam(value= "end",required=false) Date end,
			@RequestParam(value= "grade",required=false) String grade,String type){
		Integer first = 0;
		Integer second = 0;
		Integer third = 0;
		Integer fourth = 0;
		Integer fifth = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("school", school);
		mapP.put("start", start);
		mapP.put("end", end);
		mapP.put("grade", grade);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					List<ResultDiopterDO> studentMyopia = resultDiopterDao.queryMyopia(resultEyesightDO.getIdentityCard(),start,end);
					if(studentMyopia.size()>0){
						if(Double.parseDouble(resultEyesightDO.getDushu()) == 5.0){
							if(studentMyopia.get(0).getDengxiaoqiujing() >= -0.5 && studentMyopia.get(0).getDengxiaoqiujing() <= 0.75){
								first++;
							}
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								second++;
							}
						}
						if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5 && studentMyopia.get(0).getDengxiaoqiujing() > -3.0){
								third++;
							}
							if(studentMyopia.get(0).getDengxiaoqiujing() < -3.25 && studentMyopia.get(0).getDengxiaoqiujing() > -6.0){
								fourth++;
							}
							if(studentMyopia.get(0).getDengxiaoqiujing() < -6.0){
								fifth++;
							}
						}
					}
				}
			}
		}
		
		if(type.equals("1")){
			return first;
		}else if(type.equals("2")){
			return second;
		}else if(type.equals("3")){
			return third;
		}else if(type.equals("4")){
			return fourth;
		}else if(type.equals("5")){
			return fifth;
		}
		return null;
		
	}

	
	/**
	 * zip文件下载
	 */
	public static void craeteZipPath(String path,HttpServletResponse response) throws IOException{  

        ZipOutputStream zipOutputStream = null;
        OutputStream output=response.getOutputStream();  
//        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip");  
        response.setContentType("application/zip");  
        zipOutputStream = new ZipOutputStream(output,Charset.forName("UTF-8"));  
        File[] files = new File(path).listFiles();  
        FileInputStream fileInputStream = null;  
        byte[] buf = new byte[1024];  
        int len = 0;  
        if(files!=null && files.length > 0){  
            for(File wordFile:files){  
                String fileName = wordFile.getName();  
                fileInputStream = new FileInputStream(wordFile);  
                //放入压缩zip包中;  
                zipOutputStream.putNextEntry(new ZipEntry(fileName));  

                //读取文件;  
                while((len=fileInputStream.read(buf)) >0){  
                    zipOutputStream.write(buf, 0, len);  
                }  
                //关闭;  
                zipOutputStream.closeEntry();  
                if(fileInputStream != null){  
                    fileInputStream.close();  
                }  
            }  
        }  

        if(zipOutputStream !=null){  
            zipOutputStream.close();  
        }  
    } 
	
	public Integer jinshi(String school,
			@RequestParam(value= "start",required=false) Date start,
			@RequestParam(value= "end",required=false) Date end,
			@RequestParam(value= "studentSex",required=false) Integer studentSex,
			@RequestParam(value= "grade",required=false) String grade,
			@RequestParam(value= "studentClass",required=false) String studentClass) {
		Integer first = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("start", start);
		mapP.put("end", end);
		mapP.put("grade", grade);
		mapP.put("studentClass", studentClass);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.queryMyopia(resultEyesightDO.getIdentityCard(),start,end);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								first++;
							}
						}
					}
				}
			}
		}
		return first;
	}
	
	
	public Integer jiancha(String school,
			@RequestParam(value= "start",required=false) Date start,
			@RequestParam(value= "end",required=false) Date end,
			@RequestParam(value= "studentSex",required=false) Integer studentSex,
			@RequestParam(value= "grade",required=false) String grade) {
		
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("start", start);
		mapP.put("end", end);
		mapP.put("grade", grade);
		List<StudentDO> checkUserNum = studentDao.getCheckUserNum(mapP);
		if(checkUserNum.size()<=0){
			return -1;
		}else{
			return checkUserNum.size();
		}
	}
	
	public Integer jianchaban(String school,
			@RequestParam(value= "start",required=false) Date start,
			@RequestParam(value= "end",required=false) Date end,
			@RequestParam(value= "studentClass",required=false) String studentClass,
			@RequestParam(value= "grade",required=false) String grade) {
		
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("school", school);
		mapP.put("start", start);
		mapP.put("end", end);
		mapP.put("studentClass", studentClass);
		mapP.put("grade", grade);
		List<StudentDO> checkUserNum = studentDao.getGradeClassCheck(mapP);
		if(checkUserNum.size()<=0){
			return -1;
		}else{
			return checkUserNum.size();
		}
	}
	
	
	public void createDoc(HttpServletResponse response,Map<String, Object> dataMap, String fileName, String template) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(StudentReportServiceImpl.class, "/");
        Template t = null;
		//File outFile = new File(realPath + fileName);
//		Writer out = null;
        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate(template,"utf-8"); 
 //           out = new BufferedWriter(new OutputStreamWriter(
 //                   new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(fileName.getBytes(),"utf-8")))));                 //还有这里要设置编码
   //         t.process(dataMap, out);
            response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".docx");
            Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            t.process(dataMap, out);
            out.flush();
            out.close();
          
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
	/**
	 * freemarker导出工具类
	 */
	
	public void download(HttpServletRequest request,HttpServletResponse response, String fileUrl, String fileName) {
		InputStream bis = null;
		OutputStream bos = null;
		try{
			fileUrl = fileUrl + fileName;
			response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".docx");
			bis = new BufferedInputStream(new FileInputStream((fileUrl)));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024];
			int bytesRead;
			int i = 0;
	
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
				i++;
			}
			bos.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bis = null;
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bos = null;
			}
		}

    }


	/**
	 *学校报告
	 */
	@Override
	public void baogaoxuexiao(HttpServletRequest request,  HttpServletResponse response) {
		String school = request.getParameter("school");
		String checkDate = request.getParameter("checkDate");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date qian = null;
			Date hou = null;
			Date currdate = null;
			try {
				currdate = sdf1.parse(checkDate);
				/*Calendar ca = Calendar.getInstance();
				ca.setTime(currdate);
				ca.add(Calendar.DAY_OF_MONTH, -14);
				qian = ca.getTime();*/
				
				Calendar ca2 = Calendar.getInstance();
				ca2.setTime(currdate);
				ca2.add(Calendar.DAY_OF_MONTH, 14);
				hou = ca2.getTime();
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Map<String, Object> params = xuexiaobaogao(currdate,hou,request, response);
			createDoc(response,params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "给学校报告检测.ftl");
		//	download(request, response, bootdoConfig.getPoiword(),new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			
			//craeteZipPath(bootdoConfig.getPoiword(),response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			File file=new File(bootdoConfig.getPoiword());
	        if(file.exists()) {
	           File[] files = file.listFiles();
	           for(File f :files)
	              f.delete();
	        }
	     }  
	}

	   
		/**
		 * 教育局报告
		 */
		@Override
		public void baogaojiaoyuju(HttpServletRequest request,  HttpServletResponse response) {
			try {
					Map<String, Object> params = jiaoyujubaogao(request, response);
					createDoc(response,params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "给教育局报告检测.ftl");
	//				download(request, response, bootdoConfig.getPoiword(),new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				//craeteZipPath(bootdoConfig.getPoiword(),response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	
	//教育局数据
	public Map<String, Object> jiaoyujubaogao(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("0.0");
		String date = request.getParameter("date");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Date start = null;
		Date end = null;
		try {
			start = sdf1.parse(startDate);
			end = sdf1.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultEyesightDO queryMinMaxDate = resultEyesightDao.queryMinMaxDate(start,end);
		params.put("kai", sdf.format(queryMinMaxDate.getMinCheckDate()));
		params.put("jie", sdf.format(queryMinMaxDate.getMaxCheckDate()));
		params.put("newDate", sdf2.format(new Date()));
		//图
		
		Map<String,Object> ls = new HashMap<>();
		ls.put("type", date);
		List<LinShiUrlDO> lsu = linShiUrlDao.list(ls);
		for (LinShiUrlDO linShiUrlDO : lsu) {
			params.put(linShiUrlDO.getName(), linShiUrlDO.getImgUrl());
			
			linShiUrlDao.remove(linShiUrlDO.getId());
		}
									
		List<StudentDO> school = studentDao.getSchool(start,end);
		params.put("school", school.size());
		int renshu = 0;
		int you = 0;
		int xiao = 0;
		int chu = 0;
		int gao = 0;
		int your = 0;
		int xiaor = 0;
		int chur = 0;
		int gaor = 0;
		List<Integer> list2 = new ArrayList<>();
		for (StudentDO studentDO : school) {
			String school2 = studentDO.getSchool();
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("school", school2);
			List<StudentDO> list = studentDao.list(map);
			list2.add(list.size());
			if(studentDO.getXueBu().equals("幼儿园")){
				you++;
				Map<String, Object> map2 = new HashMap<String, Object>(); 
				map.put("school", school2);
				List<StudentDO> list3 = studentDao.list(map2);
				your += list3.size();
			}
			if(studentDO.getXueBu().equals("小学")){
				xiao++;
				Map<String, Object> map3 = new HashMap<String, Object>(); 
				map.put("school", school2);
				List<StudentDO> list4 = studentDao.list(map3);
				xiaor += list4.size();
			}
			if(studentDO.getXueBu().equals("初中")){
				chu++;
				Map<String, Object> map4 = new HashMap<String, Object>(); 
				map.put("school", school2);
				List<StudentDO> list5 = studentDao.list(map4);
				chur += list5.size();
			}
			if(studentDO.getXueBu().equals("高中")){
				gao++;
				Map<String, Object> map5 = new HashMap<String, Object>(); 
				map.put("school", school2);
				List<StudentDO> list6 = studentDao.list(map5);
				gaor += list6.size();
			}			
			
		}
		for (Integer integer : list2) {
			renshu += integer.intValue();
		}
		params.put("gong", renshu);
		params.put("you", you);//幼儿园数
		params.put("xiao", xiao);//小学数
		params.put("chu", chu);//初中数
		params.put("gao", gao);//高中数
		
		params.put("your", your);//幼儿园人数
		params.put("xiaor", xiaor);//小学人数
		params.put("chur", chur);//初中人数
		params.put("gaor", gaor);//高中人数
		
		List<Map<String, String>> list3 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> list5 = new ArrayList<Map<String, String>>();
		List<Map<String, Integer>> list6 = new ArrayList<Map<String, Integer>>();
		for (StudentDO studentDO : school) {
			Map<String,String> map = new HashMap<String,String>();
			Map<String,String> map2 = new HashMap<String,String>();
			Map<String,Integer> map3 = new HashMap<String,Integer>();
			Integer first = 0;
			Integer second = 0;
			Integer third = 0;
			Integer fourth = 0;
			Integer fifth = 0;
			String firstr = "0";
			String secondr ="0";
			String thirdr = "0";
			String fourthr = "0";
			String fifthr = "0";
			String school2 = studentDO.getSchool();
			List<StudentDO> lastCheckStudent = studentDao.getLastCheckStudent(school2, start, end);
			for (StudentDO studentDO2 : lastCheckStudent) {
				List<ResultEyesightDO> dushu = resultEyesightDao.queryDushu(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
				if(dushu.size()>0){
					ResultDiopterDO queryQiujing = resultDiopterDao.queryQiujing(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
					if(queryQiujing.getDengxiaoqiujing() != null){
						if(Double.parseDouble(dushu.get(0).getDushu()) == 5.0){
							if(queryQiujing.getDengxiaoqiujing() >= -0.5 && queryQiujing.getDengxiaoqiujing() <= 0.75){
								first++;
							}
							if(queryQiujing.getDengxiaoqiujing() < -0.5){
								second++;
							}
						}
						if(Double.parseDouble(dushu.get(0).getDushu()) < 5.0){
							if(queryQiujing.getDengxiaoqiujing() < -0.5 && queryQiujing.getDengxiaoqiujing() > -3.0){
								third++;
							}
							if(queryQiujing.getDengxiaoqiujing() < -3.25 && queryQiujing.getDengxiaoqiujing() > -6.0){
								fourth++;
							}
							if(queryQiujing.getDengxiaoqiujing() < -6.0){
								fifth++;
							}
						}
					}
				}
			}
			map.put("xuexiao", school2);
			map.put("xuebu", studentDO.getXueBu());
			map.put("jiancha", String.valueOf(lastCheckStudent.size()));
			map.put("qq", String.valueOf(first));
			map.put("jx", String.valueOf(second));
			map.put("dd", String.valueOf(third));
			map.put("zd", String.valueOf(fourth));
			map.put("gd", String.valueOf(fifth));
			firstr = df.format(((double)first/(double)lastCheckStudent.size())*100);
			secondr = df.format(((double)second/(double)lastCheckStudent.size())*100);
			thirdr = df.format(((double)third/(double)lastCheckStudent.size())*100);
			fourthr = df.format(((double)fourth/(double)lastCheckStudent.size())*100);
			fifthr = df.format(((double)fifth/(double)lastCheckStudent.size())*100);
			Integer jinzongy = third+fourth+fifth;
			double jinzongr = Double.parseDouble(thirdr)+Double.parseDouble(fourthr)+Double.parseDouble(fifthr);
			map.put("qqr", firstr);
			map.put("jxr", secondr);
			map.put("ddr", thirdr);
			map.put("zdr", fourthr);
			map.put("gdr", fifthr);
			map.put("zz", String.valueOf(jinzongy));
			map.put("zzr", String.valueOf(jinzongr));
			list3.add(map);
			map2.put(studentDO.getXueBu(), String.valueOf(jinzongr));
			list5.add(map2);
			map3.put(studentDO.getXueBu(), lastCheckStudent.size());
			list6.add(map3);
		}
		params.put("jinshi", list3);
		
		int jiaJS1 =0;
		double jiaJS2 = 0.0;
		int jiaJS3= 0;
		double jiaJS4= 0.0;
		int jiaJS5= 0;
		double jiaJS6= 0.0;
		int jiaJS7= 0;
		double jiaJS8= 0.0;
		int jiaJS9= 0;
		double jiaJS11= 0.0;
		int jiaJS22= 0;
		double jiaJS33=0.0;
		int jiaJS44=0;
		for (Map<String, String> map : list3) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("qq")){
					jiaJS1 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("qqr")){
					jiaJS2 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("jx")){
					jiaJS3 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("jxr")){
					jiaJS4 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("dd")){
					jiaJS5 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("ddr")){
					jiaJS6 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("zd")){
					jiaJS7 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zdr")){
					jiaJS8 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("gd")){
					jiaJS9 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("gdr")){
					jiaJS11 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("zz")){
					jiaJS22 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zzr")){
					jiaJS33 += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("jiancha")){
					jiaJS44 += Integer.parseInt(m.getValue());
				}
			}
		}
		params.put("AA", jiaJS1);
		params.put("BB", jiaJS2);
		params.put("CC", jiaJS3);
		params.put("DD", jiaJS4);
		params.put("EE", jiaJS5);
		params.put("FF", jiaJS6);
		params.put("GG", jiaJS7);
		params.put("HH", jiaJS8);
		params.put("II", jiaJS9);
		params.put("JJ", jiaJS11);
		params.put("KK", jiaJS22);
		params.put("LL", jiaJS33);
		params.put("MM", jiaJS44);
			
		int sy =0;
		int sx = 0;
		int sc= 0;
		int sg= 0;
		for (Map<String, Integer> map : list6) {
			for(Map.Entry<String, Integer> m : map.entrySet()){
				if(m.getKey().equals("幼儿园")){
					sy += m.getValue();
				}
				if(m.getKey().equals("小学")){
					sx += m.getValue();
				}
				if(m.getKey().equals("初中")){
					sc += m.getValue();
				}
				if(m.getKey().equals("高中")){
					sg += m.getValue();
				}
			}
		}
		params.put("sy", sy);
		params.put("sx", sx);
		params.put("sc", sc);
		params.put("sg", sg);
		
		List<Map<String, String>> list4 = new ArrayList<Map<String, String>>();
		for (StudentDO studentDO : school) {
			int qingduyi = 0;
			int zhongduyi = 0;
			int zzhongduyi = 0;
			int buliangyi = 0;
			String qingduyiR = "0";
			String zhongduyiR= "0";
			String zzhongduyiR= "0";
			String buliangyiR= "0";
			Map<String,String> map = new HashMap<String,String>();
			String school2 = studentDO.getSchool();
			List<StudentDO> lastCheckStudent = studentDao.getLastCheckStudent(school2, start, end);
			for (StudentDO studentDO2 : lastCheckStudent) {
				List<ResultEyesightDO> dushu = resultEyesightDao.queryDushu(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
				if(dushu.size()>0){
					if(Double.parseDouble(dushu.get(0).getDushu())==4.9){
						qingduyi++;
					}
					if(Double.parseDouble(dushu.get(0).getDushu())>=4.6 && Double.parseDouble(dushu.get(0).getDushu()) <= 4.9){
						zhongduyi++;
					}
					if(Double.parseDouble(dushu.get(0).getDushu()) <= 4.5){
						zzhongduyi++;
					}
					if(Double.parseDouble(dushu.get(0).getDushu()) < 5.0){
						buliangyi++;
					}
				}
			}
			qingduyiR = df.format(((double)qingduyi/(double)lastCheckStudent.size())*100);
			zhongduyiR = df.format(((double)zhongduyi/(double)lastCheckStudent.size())*100);
			zzhongduyiR = df.format(((double)zzhongduyi/(double)lastCheckStudent.size())*100);
			buliangyiR =  df.format(((double)buliangyi/(double)lastCheckStudent.size())*100);
			
			map.put("xuexiao", school2);
			map.put("xuebu", studentDO.getXueBu());
			map.put("jiancha", String.valueOf(lastCheckStudent.size()));
			map.put("qd", String.valueOf(qingduyi));
			map.put("qdr", qingduyiR);
			map.put("zd", String.valueOf(zhongduyi));
			map.put("zdr", zhongduyiR);
			map.put("zzd", String.valueOf(zzhongduyi));
			map.put("zzdr", zzhongduyiR);
			map.put("bl", String.valueOf(buliangyi));
			map.put("blr", buliangyiR);
			
			list4.add(map);
		}
		params.put("biliang", list4);
		
		int checkT = 0;
		int qingNT = 0;
		double qingRT =0.0;
		int zhongNT= 0;
		double zhongRT= 0.0;
		int zzhongNT= 0;
		double zzhongRT=0.0;
		int bulingNT=0;
		double bulingRT= 0.0;
		for (Map<String, String> map : list4) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("jiancha")){
					checkT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("qd")){
					qingNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("qdr")){
					qingRT += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("zd")){
					zhongNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zdr")){
					zhongRT += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("zzd")){
					zzhongNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zzdr")){
					zzhongRT += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("bl")){
					bulingNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("blr")){
					bulingRT += Double.parseDouble(m.getValue());
				}
			}
		}
		
		params.put("NN", checkT);
		params.put("OO", qingNT);
		params.put("PP", qingRT);
		params.put("QQ", zhongNT);
		params.put("RR", zhongRT);
		params.put("SS", zzhongNT);
		params.put("TT", zzhongRT);
		params.put("UU", bulingNT);
		params.put("VV", bulingRT);
		
		int daijingNum = resultEyesightDao.queryDaijingNum(start, end);
		String format = df.format(((double)daijingNum/(double)checkT)*100);
		params.put("dj", daijingNum);
		params.put("djr", format);
		
		double yo = 0;
		double xi = 0;
		double ch = 0;
		double ga = 0;
		for (Map<String, String> map : list5) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("幼儿园")){
					yo += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("小学")){
					xi += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("初中")){
					ch += Double.parseDouble(m.getValue());
				}
				if(m.getKey().equals("高中")){
					ga += Double.parseDouble(m.getValue());
				}
			}
		}
		params.put("yo", yo);
		params.put("xi", xi);
		params.put("ch", ch);
		params.put("ga", ga);
		
		int nn = 0;
		int vv = 0;
		int njs = 0;
		int vjs = 0;
		String njsr = "0";
		String vjsr= "0";
		for (StudentDO studentDO : school) {
			String school2 = studentDO.getSchool();
			List<StudentDO> lastCheckStudent = studentDao.getLastCheckStudent(school2, start, end);
			for (StudentDO studentDO2 : lastCheckStudent) {
				if(studentDO2.getStudentSex() == 1){
					nn++;
					List<ResultEyesightDO> dushu = resultEyesightDao.queryDushu(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
					if(dushu.size()>0){
						ResultDiopterDO queryQiujing = resultDiopterDao.queryQiujing(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
						if(queryQiujing.getDengxiaoqiujing() != null){
							if(Double.parseDouble(dushu.get(0).getDushu()) < 5.0){
								if(queryQiujing.getDengxiaoqiujing() < -0.5){
									njs++;
								}
							}
						}
					}
				}
				if(studentDO2.getStudentSex() == 2){
					vv++;
					List<ResultEyesightDO> dushu = resultEyesightDao.queryDushu(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
					if(dushu.size()>0){
						ResultDiopterDO queryQiujing = resultDiopterDao.queryQiujing(studentDO2.getIdentityCard(), studentDO2.getLastCheckTime());
						if(queryQiujing.getDengxiaoqiujing() != null){
							if(Double.parseDouble(dushu.get(0).getDushu()) < 5.0){
								if(queryQiujing.getDengxiaoqiujing() < -0.5){
									vjs++;
								}
							}
						}
					}
				}
			}
			
			njsr = df.format(((double)njs/(double)nn)*100);
			vjsr = df.format(((double)vjs/(double)vv)*100);
		}
		params.put("WW", nn);
		params.put("XX", vv);
		params.put("YY", njs);
		params.put("ZZ", vjs);
		params.put("njsr", njsr);
		params.put("vjsr", vjsr);
		
		
		return params;			
								
	}

	//各年级近视（教育局）
	@Override
	public Map<String, List<Double>> suoyounianjijinshi(Date start, Date end) {
		Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		double map1 = suoyounianjijinshi2(start,end,"幼儿园");
		double map2 = suoyounianjijinshi2(start,end,"1年级");
		double map3 = suoyounianjijinshi2(start,end,"2年级");
		double map4 = suoyounianjijinshi2(start,end,"3年级");
		double map5 = suoyounianjijinshi2(start,end,"4年级");
		double map6 = suoyounianjijinshi2(start,end,"5年级");
		double map7 = suoyounianjijinshi2(start,end,"6年级");
		double map8 = suoyounianjijinshi2(start,end,"初一");
		double map9 = suoyounianjijinshi2(start,end,"初二");
		double map11 = suoyounianjijinshi2(start,end,"初三");
		double map22 = suoyounianjijinshi2(start,end,"高一");
		double map33 = suoyounianjijinshi2(start,end,"高二");
		double map44 = suoyounianjijinshi2(start,end,"高三");
		myt.add(map1);
		myt.add(map2); myt.add(map3); myt.add(map4);
		myt.add(map5); myt.add(map6); myt.add(map7);
		myt.add(map8); myt.add(map9); myt.add(map11);
		myt.add(map22); myt.add(map33); myt.add(map44);
		jinshi.put("jinshi", myt);
		return jinshi;
	}
	
	public double suoyounianjijinshi2(Date start, Date end,String grade){
		DecimalFormat df = new DecimalFormat("0.0");
		int js = 0; 
		double rate ;
		List<StudentDO> allStudent = studentDao.getCheckAllStudent(start, end,grade);
		if(allStudent.size()>0){
			for (StudentDO studentDO : allStudent) {
				List<ResultEyesightDO> dushu = resultEyesightDao.queryDushu(studentDO.getIdentityCard(), studentDO.getLastCheckTime());
				if(dushu.size()>0){
					ResultDiopterDO queryQiujing = resultDiopterDao.queryQiujing(studentDO.getIdentityCard(), studentDO.getLastCheckTime());
					if(queryQiujing.getDengxiaoqiujing() != null){
						if(Double.parseDouble(dushu.get(0).getDushu()) < 5.0){
							if(queryQiujing.getDengxiaoqiujing() < -0.5){
								js++;
							}
						}
					}
				}
			}
			rate = Double.parseDouble(df.format((double)js/(double)allStudent.size()*100));
		}else{
			rate = 0;
		}				
		return rate;
		
	}
	
	//不良（教育局）
	@Override
	public Map<String, List<Double>> suoyounianjibuliang(Date start, Date end) {
		Map<String, List<Double>> buliang = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		double map1 = suoyounianjibuliang2(start,end,"幼儿园");
		double map2 = suoyounianjibuliang2(start,end,"1年级");
		double map3 = suoyounianjibuliang2(start,end,"2年级");
		double map4 = suoyounianjibuliang2(start,end,"3年级");
		double map5 = suoyounianjibuliang2(start,end,"4年级");
		double map6 = suoyounianjibuliang2(start,end,"5年级");
		double map7 = suoyounianjibuliang2(start,end,"6年级");
		double map8 = suoyounianjibuliang2(start,end,"初一");
		double map9 = suoyounianjibuliang2(start,end,"初二");
		double map11 = suoyounianjibuliang2(start,end,"初三");
		double map22 = suoyounianjibuliang2(start,end,"高一");
		double map33 = suoyounianjibuliang2(start,end,"高二");
		double map44 = suoyounianjibuliang2(start,end,"高三");
		myt.add(map1);
		myt.add(map2); myt.add(map3); myt.add(map4);
		myt.add(map5); myt.add(map6); myt.add(map7);
		myt.add(map8); myt.add(map9); myt.add(map11);
		myt.add(map22); myt.add(map33); myt.add(map44);
		buliang.put("buliang", myt);
		return buliang;
	}
	
	public double suoyounianjibuliang2(Date start, Date end,String grade){
		DecimalFormat df = new DecimalFormat("0.0");
		int bl = 0; 
		double rate ;
		List<StudentDO> allStudent = studentDao.getCheckAllStudent(start, end,grade);
		if(allStudent.size()>0){
			for (StudentDO studentDO : allStudent) {
				List<ResultEyesightDO> dushu = resultEyesightDao.queryDushu(studentDO.getIdentityCard(), studentDO.getLastCheckTime());
				if(dushu.size()>0){
					if(Double.parseDouble(dushu.get(0).getDushu()) < 5.0){
						bl++;
					}
				}
			}
			rate = Double.parseDouble(df.format((double)bl/(double)allStudent.size()*100));
		}else{
			rate = 0;
		}
		
		return rate;
		
	}
	
	//各年龄近视（教育局）
	@Override
	public Map<String, Object> genianlingjinshiyear(Date start, Date end) {
		Map<String, Object> jinshi2 = new HashMap<String, Object>();
		Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<ResultEyesightDO> getcheckDate = resultEyesightDao.getcheckDate(start,end);
		if(getcheckDate.size()>0){
			for (ResultEyesightDO resultEyesightDO : getcheckDate) {
				List<Double> myt = new ArrayList<Double>();
				Date checkDate = resultEyesightDO.getCheckDate();
				double map1 = genianlingjinshiyear2("幼儿园",checkDate);
				double map2 = genianlingjinshiyear2("小学",checkDate);
				double map3 = genianlingjinshiyear2("初中",checkDate);
				double map4 = genianlingjinshiyear2("高中",checkDate);
				myt.add(map1);
				myt.add(map2);
				myt.add(map3);
				myt.add(map4);
				jinshi.put(sdf.format(checkDate), myt);
			}
		}
		jinshi2.put("nianling", jinshi);
		return jinshi2;
	}
	
	public double genianlingjinshiyear2(String xueBu,Date checkDate){
		DecimalFormat df = new DecimalFormat("0.0");
		int js = 0; 
		double rate ;
		List<ResultEyesightDO> list = resultEyesightDao.getgenianlingjinshi(checkDate,xueBu);
		if(list.size()>0){
			for (ResultEyesightDO resultEyesightDO2 : list) {
				if(Double.parseDouble(resultEyesightDO2.getDushu()) < 5.0){
					ResultDiopterDO queryQiujing = resultDiopterDao.queryQiujing(resultEyesightDO2.getIdentityCard(), checkDate);
					if(queryQiujing.getDengxiaoqiujing() < -0.5){
						js++;
					}
				}
			}
			rate = Double.parseDouble(df.format((double)js/(double)list.size()*100));
		}else{
			rate = 0;
		}
		return rate;
		
	}
	
	//男女近视（教育局）
	@Override
	public Map<String, Object> nannvjinshiyear(Date start, Date end) {
		Map<String, Object> jinshi = new HashMap<String, Object>();
		Map<String, Object> map1 = nannvjinshiyear2(1,start,end);
		Map<String, Object> map2 = nannvjinshiyear2(2,start,end);
		jinshi.put("nan", map1);
		jinshi.put("nv", map2);
		return jinshi;
	}
	
	public Map<String, Object> nannvjinshiyear2(Integer studentSex,Date start, Date end) {
		Map<String, Object> map = new HashMap<String,Object>();
		DecimalFormat df = new DecimalFormat("0.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<ResultEyesightDO> getcheckDate = resultEyesightDao.getcheckDate(start,end);
		if(getcheckDate.size()>0){
			for (ResultEyesightDO resultEyesightDO : getcheckDate) {
				int js = 0; 
				double rate ;
				List<ResultEyesightDO> list = resultEyesightDao.getnannvjinshi(resultEyesightDO.getCheckDate(),studentSex);
				if(list.size()>0){
					for (ResultEyesightDO resultEyesightDO2 : list) {
						if(Double.parseDouble(resultEyesightDO2.getDushu()) < 5.0){
							ResultDiopterDO queryQiujing = resultDiopterDao.queryQiujing(resultEyesightDO2.getIdentityCard(), resultEyesightDO.getCheckDate());
							if(queryQiujing.getDengxiaoqiujing() < -0.5){
								js++;
							}
						}
					}
					rate = Double.parseDouble(df.format((double)js/(double)list.size()*100));
				}else{
					rate = 0;
				}
				map.put(sdf.format(resultEyesightDO.getCheckDate()), rate);
			}
		}
		
		return map;
		
	}
	
}
