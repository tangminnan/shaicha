package com.shaicha.information.service.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaicha.common.config.BootdoConfig;
import com.shaicha.information.dao.ResultCornealDao;
import com.shaicha.information.dao.ResultDiopterDao;
import com.shaicha.information.dao.ResultEyeaxisDao;
import com.shaicha.information.dao.ResultEyepressureDao;
import com.shaicha.information.dao.ResultEyesightDao;
import com.shaicha.information.dao.StudentDao;
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
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
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
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
								second++;
							}
						}
					}
				}
			}
		}*/
		
		Map<String, Object> mapLN3 = new HashMap<String,Object>();
		mapLN3.put("school", school);
		mapLN3.put("checkDate", "2019");
		List<ResultEyesightDO> liNian3 = resultEyesightDao.liNian(mapLN3);
		if(liNian3.size()>0){
			for (ResultEyesightDO resultEyesightDO : liNian3) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
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
		DecimalFormat df = new DecimalFormat(".0");
		//da.add(Double.parseDouble(df.format(first/list.size()*100)));
		//da.add(Double.parseDouble(df.format(second/list.size()*100)));
		da.add(79.30);
		da.add(66.10);
		da.add(Double.parseDouble(df.format(third/list.size()*100)));
		
		mapP.put("overYearMyopia", da);
		
		return mapP;
	}
	
	//各年级近视
	@Override
	public Map<String, List<Double>> gradeMyopia(String school,String checkDate) {
		Map<String, List<Double>> gradeMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		Map<String, Object> gradeMyopiaFS = gradeMyopia("1年级",school,checkDate);
		Map<String, Object> gradeMyopiaSE = gradeMyopia("2年级",school,checkDate);
		Map<String, Object> gradeMyopiaTH = gradeMyopia("3年级",school,checkDate);
		Map<String, Object> gradeMyopiaFO = gradeMyopia("4年级",school,checkDate);
		Map<String, Object> gradeMyopiaFI = gradeMyopia("5年级",school,checkDate);
		Map<String, Object> gradeMyopiaSI = gradeMyopia("6年级",school,checkDate);
		
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
		myt.add((double)gradeSI);
				
		gradeMyopia.put("gradeMyopia", myt);
		return gradeMyopia;
		
	}
	
	public Map<String, Object> gradeMyopia(String grade,String school,@RequestParam(value= "checkDate",required=false) String checkDate) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("grade", grade);
		mapP.put("school", school);
		mapP.put("checkDate", checkDate);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat(".0");
			parseDouble = Double.parseDouble(df.format(first/jinShi.size()*100));		
				
		}			
		
		map.put("gradeMyopia", parseDouble);
		/*String jsonString = JSONObject.toJSONString(gradeMyopiaFS);
		System.out.println(jsonString);*/
		return map;				
	}
	
	public Map<String, Object> gradeMyopiaLN(String grade,String school,@RequestParam(value= "checkDate",required=false) String checkDate) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> map = new HashMap<String,Object>();
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
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat(".0");
			parseDouble = Double.parseDouble(df.format(first/jinShi.size()*100));		
				
		}			
		
		map.put("gradeMyopia", parseDouble);
		/*String jsonString = JSONObject.toJSONString(gradeMyopiaFS);
		System.out.println(jsonString);*/
		return map;				
	}
	
	//历年各年级近视率走势图
	@Override
	public Map<String, List<Double>> overYearGradeMyopia(String school) {
		Map<String, List<Double>> gradeMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		List<Double> myt2 = new ArrayList<Double>();
		List<Double> myt3 = new ArrayList<Double>();
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
		myt.add(25.5);
		myt.add(30.2);
		myt.add(33.4);
		myt.add(45.6);
		myt.add(38.3);
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
		myt2.add(23.5);
		myt2.add(24.7);
		myt2.add(28.2);
		myt2.add(30.8);
		myt2.add(40.1);
		myt2.add(30.7);
		gradeMyopia.put("eighteen", myt2);
		Map<String, Object> gradeMyopiaFS3 = gradeMyopiaLN("1年级",school,"2019");
		Map<String, Object> gradeMyopiaSE3 = gradeMyopiaLN("2年级",school,"2019");
		Map<String, Object> gradeMyopiaTH3 = gradeMyopiaLN("3年级",school,"2019");
		Map<String, Object> gradeMyopiaFO3 = gradeMyopiaLN("4年级",school,"2019");
		Map<String, Object> gradeMyopiaFI3 = gradeMyopiaLN("5年级",school,"2019");
		Map<String, Object> gradeMyopiaSI3 = gradeMyopiaLN("6年级",school,"2019");
		
		Object gradeFS3 = gradeMyopiaFS3.get("gradeMyopia");
		Object gradeSE3 = gradeMyopiaSE3.get("gradeMyopia");
		Object gradeTH3 = gradeMyopiaTH3.get("gradeMyopia");
		Object gradeFO3 = gradeMyopiaFO3.get("gradeMyopia");
		Object gradeFI3 = gradeMyopiaFI3.get("gradeMyopia");
		Object gradeSI3 = gradeMyopiaSI3.get("gradeMyopia");
		myt3.add((double)gradeFS3);
		myt3.add((double)gradeSE3);
		myt3.add((double)gradeTH3);
		myt3.add((double)gradeFO3);
		myt3.add((double)gradeFI3);
		myt3.add((double)gradeSI3);
		gradeMyopia.put("nineteen", myt3);
		
		
		return gradeMyopia;
	}

	//男、女生近视
	@Override
	public Map<String, List<Double>> studentSexMyopia(String school,String checkDate) {
		Map<String, List<Double>> studentSexMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		Map<String, Object> overYearSex = overYearSex(1,school,null,checkDate);//男
		Object overYearSexNan = overYearSex.get("overYearSex");
		myt.add((double)overYearSexNan);
		
		Map<String, Object> overYearSex2 = overYearSex(2,school,null,checkDate);
		Object overYearSexNv = overYearSex2.get("overYearSex");
		myt.add((double)overYearSexNv);
		
		studentSexMyopia.put("studentSexMyopia",myt);
		return studentSexMyopia;
	}

	//历年男生近视率走势图
	@Override
	public Map<String, List<Double>> overYearSexNan(String school) {
		Map<String, List<Double>> studentSexMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		/*Map<String, Object> overYearSex = overYearSexLN(1,school,null,"2017");
		Object overYearSexNan = overYearSex.get("overYearSex");
		myt.add((double)overYearSexNan);
		Map<String, Object> overYearSex2 = overYearSexLN(1,school,null,"2018");
		Object overYearSexNan2 = overYearSex2.get("overYearSex");
		myt.add((double)overYearSexNan2);*/
		myt.add(78.9);
		myt.add(66.2);
		Map<String, Object> overYearSex3 = overYearSexLN(1,school,null,"2019");
		Object overYearSexNan3 = overYearSex3.get("overYearSex");
		myt.add((double)overYearSexNan3);
		studentSexMyopia.put("studentSexMyopia",myt);
		return studentSexMyopia;
	}

	//历年女生近视率走势图
	@Override
	public Map<String, List<Double>> overYearSexNv(String school) {
		Map<String, List<Double>> studentSexMyopia = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		/*Map<String, Object> overYearSex = overYearSexLN(2,school,null,"2017");
		Object overYearSexNv = overYearSex.get("overYearSex");
		myt.add((double)overYearSexNv);
		Map<String, Object> overYearSex2 = overYearSexLN(2,school,null,"2018");
		Object overYearSexNv2 = overYearSex2.get("overYearSex");
		myt.add((double)overYearSexNv2);*/
		myt.add(76.7);
		myt.add(60.5);
		Map<String, Object> overYearSex3 = overYearSexLN(2,school,null,"2019");
		Object overYearSexNv3 = overYearSex3.get("overYearSex");
		myt.add((double)overYearSexNv3);
		studentSexMyopia.put("studentSexMyopia",myt);
		return studentSexMyopia;
		
	}
	
	public Map<String, Object> overYearSex(Integer studentSex,String school,
			@RequestParam(value= "grade",required=false)String grade,
			@RequestParam(value= "checkDate",required=false) String checkDate) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("grade", grade);
		mapP.put("checkDate", checkDate);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat(".0");
			parseDouble = Double.parseDouble(df.format(first/jinShi.size()*100));		
				
		}	

		map.put("overYearSex", parseDouble);
		return map;
	}
	
	public Map<String, Object> overYearSexLN(Integer studentSex,String school,
			@RequestParam(value= "grade",required=false)String grade,
			@RequestParam(value= "checkDate",required=false) String checkDate) {
		Integer first = 0;
		double parseDouble = 0;
		Map<String, Object> map = new HashMap<String,Object>();
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
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
								first++;
							}
						}
					}
				}
			}
			DecimalFormat df = new DecimalFormat(".0");
			parseDouble = Double.parseDouble(df.format(first/jinShi.size()*100));		
				
		}	

		map.put("overYearSex", parseDouble);
		return map;
	}

	//男女生近视年级
	@Override
	public Map<String, List<Double>> overYearGradeSex(String school,String checkDate) {
		Map<String, List<Double>> overYearGradeSex = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		List<Double> myt2 = new ArrayList<Double>();
		Map<String, Object> overYearSexNan = overYearSex(1,school,"1年级",checkDate);
		Map<String, Object> overYearSexNan2 = overYearSex(1,school,"2年级",checkDate);
		Map<String, Object> overYearSexNan3 = overYearSex(1,school,"3年级",checkDate);
		Map<String, Object> overYearSexNan4 = overYearSex(1,school,"4年级",checkDate);
		Map<String, Object> overYearSexNan5 = overYearSex(1,school,"5年级",checkDate);
		Map<String, Object> overYearSexNan6 = overYearSex(1,school,"6年级",checkDate);
		Object overYearGradeSexNan = overYearSexNan.get("overYearSex");
		Object overYearGradeSexNan2 = overYearSexNan2.get("overYearSex");
		Object overYearGradeSexNan3 = overYearSexNan3.get("overYearSex");
		Object overYearGradeSexNan4 = overYearSexNan4.get("overYearSex");
		Object overYearGradeSexNan5 = overYearSexNan5.get("overYearSex");
		Object overYearGradeSexNan6 = overYearSexNan6.get("overYearSex");
		myt.add((double)overYearGradeSexNan);
		myt.add((double)overYearGradeSexNan2);
		myt.add((double)overYearGradeSexNan3);
		myt.add((double)overYearGradeSexNan4);
		myt.add((double)overYearGradeSexNan5);
		myt.add((double)overYearGradeSexNan6);
		overYearGradeSex.put("overYearSexNan", myt);
		
		Map<String, Object> overYearSexNv = overYearSex(2,school,"1年级",checkDate);
		Map<String, Object> overYearSexNv2 = overYearSex(2,school,"2年级",checkDate);
		Map<String, Object> overYearSexNv3 = overYearSex(2,school,"3年级",checkDate);
		Map<String, Object> overYearSexNv4 = overYearSex(2,school,"4年级",checkDate);
		Map<String, Object> overYearSexNv5 = overYearSex(2,school,"5年级",checkDate);
		Map<String, Object> overYearSexNv6 = overYearSex(2,school,"6年级",checkDate);
		Object overYearGradeSexNv = overYearSexNv.get("overYearSex");
		Object overYearGradeSexNv2 = overYearSexNv2.get("overYearSex");
		Object overYearGradeSexNv3 = overYearSexNv3.get("overYearSex");
		Object overYearGradeSexNv4 = overYearSexNv4.get("overYearSex");
		Object overYearGradeSexNv5 = overYearSexNv5.get("overYearSex");
		Object overYearGradeSexNv6 = overYearSexNv6.get("overYearSex");
		myt2.add((double)overYearGradeSexNv);
		myt2.add((double)overYearGradeSexNv2);
		myt2.add((double)overYearGradeSexNv3);
		myt2.add((double)overYearGradeSexNv4);
		myt2.add((double)overYearGradeSexNv5);
		myt2.add((double)overYearGradeSexNv6);
		overYearGradeSex.put("overYearSexNv", myt2);
		
		return overYearGradeSex;
	}
	
	
	/**
	 * freemarker导出工具类
	 */
	
	public void download(HttpServletRequest request,HttpServletResponse response,String template,String newWordName,Map dataMap) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(StudentServiceImpl.class, "/");  
        File dir=new File("D:/baogao/");
        if(!dir.exists()){
        	dir.mkdirs();
	    }
        File outFile = new File("D:/baogao/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".doc");//输出路径
        Template t=null;  
        Writer out = null;
        try {
            t = configuration.getTemplate("给学校报告检测.ftl", "utf-8"); //文件名，获取模板
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));  
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();  
        } finally {
            try {
                out.close();
            } catch (IOException  e1) {
                e1.printStackTrace(); 
            }
        }

       /* File file = null;  
        InputStream fin = null;  
        ServletOutputStream out = null;
        try {
        	Template t =configuration.getTemplate(template);
            //word.xml是要生成Word文件的模板文件
        	file = createDoc(dataMap,t,newWordName);   
            fin = new FileInputStream(file);  
            response.setCharacterEncoding("utf-8"); 
    		response.setContentType("application/msword"); 
    		response.setHeader("Content-Disposition", "attachment;filename="+new String(newWordName.getBytes(),"iso-8859-1")+".docx");
    		out = response.getOutputStream();
    		byte[] buffer = new byte[512];
    		int bytesToRead = -1;
    		while((bytesToRead = fin.read(buffer)) != -1) {
    		 out.write(buffer, 0, bytesToRead);
    		}
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if (fin != null){
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null){
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			if (file != null)
				file.delete();
		}*/

    }
/*	private static File createDoc(Map dataMap, Template template,String newWordName) {
		File f = null;
		try {
			f = new File(new String(newWordName.getBytes(),"iso-8859-1")+".docx");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Template t = template;
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			t.process(dataMap, w);
			 w.close();
			} catch (Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
			} 
		return f;
	}*/


	/**
	 * 
	 */
	@Override
	public void baogaoxuexiao(HttpServletRequest request,  HttpServletResponse response) {
		String school = request.getParameter("school");
		try {
			
				Map<String, Object> params = xuexiaobaogao(request, response);

				download(request, response, "给学校报告检测.ftl",school, params);
			
			//craeteZipPath(bootdoConfig.getPoiword(),response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Map<String,Object> xuexiaobaogao(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		DecimalFormat df = new DecimalFormat(".0");
		//Date checkDate = resultDiopterDao.maxCheckDate().getCheckDate();
		//图
		String school = request.getParameter("school");
		String checkDate = request.getParameter("checkDate");
		Integer schoolNum = Integer.parseInt(request.getParameter("schoolNum"));
		String overYear=request.getParameter("overYear");
		try {
			String string = savePictoServer(overYear,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("overYear", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String gradeMyopia=request.getParameter("gradeMyopia");
		try {
			String string = savePictoServer(gradeMyopia,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("gradeMyopia", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String overYearGradeMyopia=request.getParameter("overYearGradeMyopia");
		try {
			String string = savePictoServer(overYearGradeMyopia,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("overYearGradeMyopia", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String studentSexMyopia=request.getParameter("studentSexMyopia");
		try {
			String string = savePictoServer(studentSexMyopia,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("studentSexMyopia", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String overYearSexNan=request.getParameter("overYearSexNan");
		try {
			String string = savePictoServer(overYearSexNan,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("overYearSexNan", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String overYearSexNv=request.getParameter("overYearSexNv");
		try {
			String string = savePictoServer(overYearSexNv,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("overYearSexNv", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String overYearGradeSex=request.getParameter("overYearGradeSex");
		try {
			String string = savePictoServer(overYearGradeSex,"D:/word/img/");
			String imageStr = getImageStr(string);
			//params.put("overYearGradeSex", imageStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		params.put("schoolName", school);
		params.put("newDate", sdf.format(new Date()));

		//基本情况
		//List<StudentDO> checkNum = studentDao.queryCheckNum(school);
		Integer jiancha = jiancha(school,checkDate,null,null);
		params.put("schoolNum", schoolNum);
		params.put("checkNum", jiancha);
		params.put("checkRate", Double.parseDouble(df.format(jiancha/schoolNum*100))+"%");
		
		
		List<StudentDO> first = studentDao.queryGradeNum("1年级");
		List<StudentDO> second = studentDao.queryGradeNum("2年级");
		List<StudentDO> third = studentDao.queryGradeNum("3年级");
		List<StudentDO> fourth = studentDao.queryGradeNum("4年级");
		List<StudentDO> fifth = studentDao.queryGradeNum("5年级");
		List<StudentDO> sixth = studentDao.queryGradeNum("6年级");
		if(first.size()>0){
			Integer jiancha2 = jiancha(school,checkDate,null,"1年级");
			params.put("firstGradeNum", first.size());
		//	params.put("firstGradeCheckNum", jiancha2);
		//	params.put("firstGradeCheckRate", Double.parseDouble(df.format(jiancha2/first.size()*100))+"%");
		}
		if(second.size()>0){
			Integer jiancha3 = jiancha(school,checkDate,null,"2年级");
			params.put("secondGradeNum", second.size());
		//	params.put("secondGradeCheckNum", jiancha3);
		//	params.put("secondGradeCheckRate", Double.parseDouble(df.format(jiancha3/second.size()*100)));
		}
		if(third.size()>0){
			Integer jiancha4 = jiancha(school,checkDate,null,"3年级");
			params.put("thirdGradeNum", third.size());
			params.put("thirdGradeCheckNum", jiancha4);
			params.put("thirdGradeCheckRate", Double.parseDouble(df.format(jiancha4/third.size()*100)));
		}
		if(fourth.size()>0){
			Integer jiancha5 = jiancha(school,checkDate,null,"4年级");
			params.put("fourthGradeNum", fourth.size());
		//	params.put("fourthGradeCheckNum", jiancha5);
		//	params.put("fourthGradeCheckRate", Double.parseDouble(df.format(jiancha5/fourth.size()*100)));
		}
		if(fifth.size()>0){
			Integer jiancha6 = jiancha(school,checkDate,null,"5年级");
			params.put("fifthGradeNum", fifth.size());
		//	params.put("fifthGradeCheckNum", jiancha6);
		//	params.put("fifthGradeCheckRate", Double.parseDouble(df.format(jiancha6/fifth.size()*100)));
		}
		if(sixth.size()>0){
			Integer jiancha7 = jiancha(school,checkDate,null,"6年级");
			params.put("sixthGradeNum", sixth.size());
		//	params.put("sixthGradeCheckNum", jiancha7);
		//	params.put("sixthGradeCheckRate", Double.parseDouble(df.format(jiancha7/sixth.size()*100)));
		}
		Integer jin = 0;
		Map<String, Object> mapLN3 = new HashMap<String,Object>();
		mapLN3.put("school", school);
		mapLN3.put("checkDate", checkDate);
		List<ResultEyesightDO> liNian3 = resultEyesightDao.liNian(mapLN3);
		if(liNian3.size()>0){
			for (ResultEyesightDO resultEyesightDO : liNian3) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map3 = new HashMap<String,Object>();
						map3.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map3);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
								jin++;
							}
						}
					}
				}
			}
		}		
		
		params.put("myopiaNum", jin);
		params.put("myopiaRate", Double.parseDouble(df.format(jin/jiancha*100))+"%");
		
		Integer nanjiancha = jiancha(school,checkDate,1,null);
		Integer nanjinshi = jinshi(school,checkDate,1,null,null);
		params.put("checkNanNum", nanjiancha);
		params.put("myopiaNanNum", nanjinshi);
		params.put("myopiaNanRate", Double.parseDouble(df.format(nanjinshi/nanjiancha*100))+"%");
		
		Integer nvjiancha = jiancha(school,checkDate,2,null);
		Integer nvjinshi = jinshi(school,checkDate,2,null,null);
		params.put("checkNvNum", nvjiancha);
		params.put("myopiaNvNum", nvjinshi);
		params.put("myopiaNvRate", Double.parseDouble(df.format(nvjinshi/nvjiancha*100))+"%");
		
		Integer nanjianchanianyi = jiancha(school,checkDate,1,"1年级");
		Integer nanjinshinianyi = jinshi(school,checkDate,1,"1年级",null);
	//	params.put("firstCheckNanNum", nanjianchanianyi);
	//	params.put("firstMyopiaNanNum", nanjinshinianyi);
	//	params.put("firstMyopiaNanRate", Double.parseDouble(df.format(nanjinshinianyi/nanjianchanianyi*100))+"%");
		
		Integer nanjianchanianer = jiancha(school,checkDate,1,"2年级");
		Integer nanjinshinianer = jinshi(school,checkDate,1,"2年级",null);
	//	params.put("secondCheckNanNum", nanjianchanianer);
	//	params.put("secondMyopiaNanNum", nanjinshinianer);
	//	params.put("secondMyopiaNanRate", Double.parseDouble(df.format(nanjinshinianer/nanjianchanianer*100))+"%");
		
		Integer nanjianchaniansan = jiancha(school,checkDate,1,"3年级");
		Integer nanjinshiniansan = jinshi(school,checkDate,1,"3年级",null);
		params.put("thirdCheckNanNum", nanjianchaniansan);
		params.put("thirdMyopiaNanNum", nanjinshiniansan);
		params.put("thirdMyopiaNanRate", Double.parseDouble(df.format(nanjinshiniansan/nanjianchaniansan*100))+"%");
		
		Integer nanjianchaniansi = jiancha(school,checkDate,1,"4年级");
		Integer nanjinshiniansi = jinshi(school,checkDate,1,"4年级",null);
	//	params.put("fourthCheckNanNum", nanjianchaniansi);
	//	params.put("fourthMyopiaNanNum", nanjinshiniansi);
	//	params.put("fourthMyopiaNanRate", Double.parseDouble(df.format(nanjinshiniansi/nanjianchaniansi*100))+"%");
		
		Integer nanjianchanianwu = jiancha(school,checkDate,1,"5年级");
		Integer nanjinshinianwu = jinshi(school,checkDate,1,"5年级",null);
	//	params.put("fifthCheckNanNum", nanjianchanianwu);
	//	params.put("fifthMyopiaNanNum", nanjinshinianwu);
	//	params.put("fifthMyopiaNanRate", Double.parseDouble(df.format(nanjinshinianwu/nanjianchanianwu*100))+"%");
		
		Integer nanjianchanianliu = jiancha(school,checkDate,1,"6年级");
		Integer nanjinshinianliu = jinshi(school,checkDate,1,"6年级",null);
	//	params.put("sixthCheckNanNum", nanjianchanianliu);
	//	params.put("sixthMyopiaNanNum", nanjinshinianliu);
	//	params.put("sixthMyopiaNanRate", Double.parseDouble(df.format(nanjinshinianliu/nanjianchanianliu*100))+"%");
		
		Integer nvjianchanianyi = jiancha(school,checkDate,2,"1年级");
		Integer nvjinshinianyi = jinshi(school,checkDate,2,"1年级",null);
	//	params.put("firstCheckNvNum", nvjianchanianyi);
	//	params.put("firstMyopiaNvNum", nvjinshinianyi);
	//	params.put("firstMyopiaNvRate", Double.parseDouble(df.format(nvjinshinianyi/nvjianchanianyi*100))+"%");
		
		Integer nvjianchanianer = jiancha(school,checkDate,2,"2年级");
		Integer nvjinshinianer = jinshi(school,checkDate,2,"2年级",null);
	//	params.put("secondCheckNvNum", nvjianchanianer);
	//	params.put("secondMyopiaNvNum", nvjinshinianer);
	//	params.put("secondMyopiaNvRate", Double.parseDouble(df.format(nvjinshinianer/nvjianchanianer*100))+"%");
		
		Integer nvjianchaniansan = jiancha(school,checkDate,2,"3年级");
		Integer nvjinshiniansan = jinshi(school,checkDate,2,"3年级",null);
		params.put("thirdCheckNvNum", nvjianchaniansan);
		params.put("thirdMyopiaNvNum", nvjinshiniansan);
		params.put("thirdMyopiaNvRate", Double.parseDouble(df.format(nvjinshiniansan/nvjianchaniansan*100))+"%");
		
		Integer nvjianchaniansi = jiancha(school,checkDate,2,"4年级");
		Integer nvjinshiniansi = jinshi(school,checkDate,2,"4年级",null);
	//	params.put("fourthCheckNvNum", nvjianchaniansi);
	//	params.put("fourthMyopiaNvNum", nvjinshiniansi);
	//	params.put("fourthMyopiaNvRate", Double.parseDouble(df.format(nvjinshiniansi/nvjianchaniansi*100))+"%");
		
		Integer nvjianchanianwu = jiancha(school,checkDate,2,"5年级");
		Integer nvjinshinianwu = jinshi(school,checkDate,2,"5年级",null);
	//	params.put("fifthCheckNvNum", nvjianchanianwu);
	//	params.put("fifthMyopiaNvNum", nvjinshinianwu);
	//	params.put("fifthMyopiaNvRate", Double.parseDouble(df.format(nvjinshinianwu/nvjianchanianwu*100))+"%");
		
		Integer nvjianchanianliu = jiancha(school,checkDate,2,"6年级");
		Integer nvjinshinianliu = jinshi(school,checkDate,2,"6年级",null);
	//	params.put("sixthCheckNvNum", nvjianchanianliu);
	//	params.put("sixthMyopiaNvNum", nvjinshinianliu);
	//	params.put("sixthMyopiaNvRate", Double.parseDouble(df.format(nvjinshinianliu/nvjianchanianliu*100))+"%");
		
		Map<String,Object> mapClassyi = new HashMap<String,Object>();
		mapClassyi.put("school", school);
		mapClassyi.put("grade", "一年级");
		List<StudentDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		for(int i= 1 ; i<=classCountyi.size() ;i++){
			Map<String,Object> classyi = new HashMap<String,Object>();
			classyi.put("class", i);
			Integer jcyi = jianchaban(school,checkDate,String.valueOf(i), "一年级");
			Integer jsyi = jinshi(school,checkDate,null,null,String.valueOf(i));
			classyi.put("classNum", jcyi);
			classyi.put("classMyopiaNum", jsyi);
			classyi.put("ClassMyopiaRate", Double.parseDouble(df.format(jsyi/jcyi*100))+"%");
			list1.add(classyi);
		}
	//	params.put("firstClass", list1);
		
		Map<String,Object> mapClasser = new HashMap<String,Object>();
		mapClasser.put("school", school);
		mapClasser.put("grade", "二年级");
		List<StudentDO> classCounter = studentDao.queryGradeClassCount(mapClasser);
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		for(int i= 1 ; i<=classCounter.size() ;i++){
			Map<String,Object> classer = new HashMap<String,Object>();
			classer.put("class", i);
			Integer jcer = jianchaban(school,checkDate,String.valueOf(i), "二年级");
			Integer jser = jinshi(school,checkDate,null,null,String.valueOf(i));
			classer.put("classNum", jcer);
			classer.put("classMyopiaNum", jser);
			classer.put("ClassMyopiaRate", Double.parseDouble(df.format(jser/jcer*100))+"%");
			list2.add(classer);
		}
	//	params.put("secondClass", list2);
		
		Map<String,Object> mapClasssan = new HashMap<String,Object>();
		mapClasssan.put("school", school);
		mapClasssan.put("grade", "三年级");
		List<StudentDO> classCountsan = studentDao.queryGradeClassCount(mapClasssan);
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		for(int i= 1 ; i<=classCountsan.size() ;i++){
			Map<String,Object> classsan = new HashMap<String,Object>();
			classsan.put("class", i);
			Integer jcsan = jianchaban(school,checkDate,String.valueOf(i), "三年级");
			Integer jssan = jinshi(school,checkDate,null,null,String.valueOf(i));
			classsan.put("classNum", jcsan);
			classsan.put("classMyopiaNum", jssan);
			classsan.put("ClassMyopiaRate", Double.parseDouble(df.format(jssan/jcsan*100))+"%");
			list3.add(classsan);
		}
		//params.put("thirdClass", list3);
		Integer jcsan = 0;
		Integer jssan = 1;
		Integer jcsan2 = 0;
		Integer jssan2 = 1;
		params.put("class1", 1);
		jcsan = jianchaban(school,checkDate,String.valueOf(1), "三年级");
		jssan = jinshi(school,checkDate,null,null,String.valueOf(1));
		params.put("thirdClassNum1", jcsan);
		params.put("thirdClassMyopiaNum1", jssan);
		params.put("thirdClassMyopiaRate1", Double.parseDouble(df.format(jssan/jcsan*100))+"%");
		
		params.put("class2", 2);
		jcsan2 = jianchaban(school,checkDate,String.valueOf(2), "三年级");
		jssan2 = jinshi(school,checkDate,null,null,String.valueOf(2));
		params.put("thirdClassNum2", jcsan2);
		params.put("thirdClassMyopiaNum2", jssan2);
		params.put("thirdClassMyopiaRate2", Double.parseDouble(df.format(jssan2/jcsan2*100))+"%");
		
		
		
		Map<String,Object> mapClasssi = new HashMap<String,Object>();
		mapClasssi.put("school", school);
		mapClasssi.put("grade", "四年级");
		List<StudentDO> classCountsi = studentDao.queryGradeClassCount(mapClasssi);
		List<Map<String,Object>> list4 = new ArrayList<Map<String,Object>>();
		for(int i= 1 ; i<=classCountsi.size() ;i++){
			Map<String,Object> classsi = new HashMap<String,Object>();
			classsi.put("class", i);
			Integer jcsi = jianchaban(school,checkDate,String.valueOf(i), "四年级");
			Integer jssi = jinshi(school,checkDate,null,null,String.valueOf(i));
			classsi.put("classNum", jcsi);
			classsi.put("classMyopiaNum", jssi);
			classsi.put("ClassMyopiaRate", Double.parseDouble(df.format(jssi/jcsi*100))+"%");
			list4.add(classsi);
		}
	//	params.put("fourthClass", list4);
		
		Map<String,Object> mapClasswu = new HashMap<String,Object>();
		mapClasswu.put("school", school);
		mapClasswu.put("grade", "五年级");
		List<StudentDO> classCountwu = studentDao.queryGradeClassCount(mapClasswu);
		List<Map<String,Object>> list5 = new ArrayList<Map<String,Object>>();
		for(int i= 1 ; i<=classCountwu.size() ;i++){
			Map<String,Object> classwu = new HashMap<String,Object>();
			classwu.put("class", i);
			Integer jcwu = jianchaban(school,checkDate,String.valueOf(i), "五年级");
			Integer jswu = jinshi(school,checkDate,null,null,String.valueOf(i));
			classwu.put("classNum", jcwu);
			classwu.put("classMyopiaNum", jswu);
			classwu.put("ClassMyopiaRate", Double.parseDouble(df.format(jswu/jcwu*100))+"%");
			list5.add(classwu);
		}
	//	params.put("fifthClass", list5);
		
		Map<String,Object> mapClassliu = new HashMap<String,Object>();
		mapClassliu.put("school", school);
		mapClassliu.put("grade", "六年级");
		List<StudentDO> classCountliu = studentDao.queryGradeClassCount(mapClassliu);
		List<Map<String,Object>> list6 = new ArrayList<Map<String,Object>>();
		for(int i= 1 ; i<=classCountliu.size() ;i++){
			Map<String,Object> classliu = new HashMap<String,Object>();
			classliu.put("class", i);
			Integer jcliu = jianchaban(school,checkDate,String.valueOf(i), "六年级");
			Integer jsliu = jinshi(school,checkDate,null,null,String.valueOf(i));
			classliu.put("classNum", jcliu);
			classliu.put("classMyopiaNum", jsliu);
			classliu.put("ClassMyopiaRate", Double.parseDouble(df.format(jsliu/jcliu*100))+"%");
			list6.add(classliu);
		}
	//	params.put("sixthClass", list6);
		
		
		List<Map<String,Object>> listT = new ArrayList<Map<String,Object>>();

		Map<String,Object> dushuy = new HashMap<String,Object>();
		dushuy.put("school", school);
		dushuy.put("grade", "1年级");
		dushuy.put("checkDate", checkDate);
		List<ResultEyesightDO> shiLiy = resultEyesightDao.queryLuoYanShiLi(dushuy);
		Map<String,Object> gradey = new HashMap<String,Object>();
		int checkyi = shiLiy.size();
		int qingduyi = 0;
		int zhongduyi = 0;
		int zzhongduyi = 0;
		int buliangyi = 0;
		Double qingduyiR = 0.0 ;
		Double zhongduyiR = 0.0 ;
		Double zzhongduyiR = 0.0 ;
		Double buliangyiR = 0.0 ;
		if(shiLiy.size()>0){
			for (ResultEyesightDO resultEyesightDO : shiLiy) {
				if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
					if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
						qingduyi++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
						zhongduyi++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
						zzhongduyi++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						buliangyi++;
					}
				}
			}
			qingduyiR = Double.parseDouble(df.format(qingduyi/checkyi*100));
			zhongduyiR = Double.parseDouble(df.format(zhongduyi/checkyi*100));
			zzhongduyiR = Double.parseDouble(df.format(zzhongduyi/checkyi*100));
			buliangyiR =  Double.parseDouble(df.format(buliangyi/checkyi*100));
			gradey.put("grade", "1");
			gradey.put("gradeCheckNum", checkyi);
			gradey.put("numQDSLBL", qingduyi);
			gradey.put("rateQDSLBL", qingduyiR+"%");
			gradey.put("numZDSLBL", zhongduyi);
			gradey.put("rateZDSLBL", zhongduyiR+"%");
			gradey.put("numZZDSLBL", zzhongduyi);
			gradey.put("rateZZDSLBL", zzhongduyiR+"%");
			gradey.put("numSLBL", buliangyi);
			gradey.put("rateSLBL", buliangyiR+"%");
			
			listT.add(gradey);
			
		}
		
		Map<String,Object> dushue = new HashMap<String,Object>();
		dushue.put("school", school);
		dushue.put("grade", "2年级");
		dushue.put("checkDate", checkDate);
		List<ResultEyesightDO> shiLie = resultEyesightDao.queryLuoYanShiLi(dushue);
		Map<String,Object> gradee = new HashMap<String,Object>();
		int checker = shiLie.size();
		int qingduer = 0;
		int zhongduer = 0;
		int zzhongduer = 0;
		int bulianger = 0;
		Double qingduerR = 0.0 ;
		Double zhongduerR = 0.0 ;
		Double zzhongduerR = 0.0 ;
		Double buliangerR = 0.0 ;
		if(shiLie.size()>0){
			for (ResultEyesightDO resultEyesightDO : shiLie) {
				if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
					if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
						qingduer++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
						zhongduer++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
						zzhongduer++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						bulianger++;
					}
				}
			}
			qingduerR = Double.parseDouble(df.format(qingduer/checker*100));
			zhongduerR = Double.parseDouble(df.format(zhongduer/checker*100));
			zzhongduerR = Double.parseDouble(df.format(zzhongduer/checker*100));
			buliangerR =  Double.parseDouble(df.format(bulianger/checker*100));
			gradee.put("grade", "2");
			gradee.put("gradeCheckNum", checker);
			gradee.put("numQDSLBL", qingduer);
			gradee.put("rateQDSLBL", qingduerR+"%");
			gradee.put("numZDSLBL", zhongduer);
			gradee.put("rateZDSLBL", zhongduerR+"%");
			gradee.put("numZZDSLBL", zzhongduer);
			gradee.put("rateZZDSLBL", zzhongduerR+"%");
			gradee.put("numSLBL", bulianger);
			gradee.put("rateSLBL", buliangerR+"%");
			
			listT.add(gradee);
			
		}
		
		Map<String,Object> dushus = new HashMap<String,Object>();
		dushus.put("school", school);
		dushus.put("grade", "3年级");
		dushus.put("checkDate", checkDate);
		List<ResultEyesightDO> shiLis = resultEyesightDao.queryLuoYanShiLi(dushus);
		Map<String,Object> grades = new HashMap<String,Object>();
		int checksa = shiLis.size();
		int qingdusa = 0;
		int zhongdusa = 0;
		int zzhongdusa = 0;
		int buliangsa = 0;
		Double qingdusaR = 0.0 ;
		Double zhongdusaR = 0.0 ;
		Double zzhongdusaR = 0.0 ;
		Double buliangsaR = 0.0 ;
		if(shiLie.size()>0){
			for (ResultEyesightDO resultEyesightDO : shiLis) {
				if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
					if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
						qingdusa++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
						zhongdusa++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
						zzhongdusa++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						buliangsa++;
					}
				}
			}
			qingdusaR = Double.parseDouble(df.format(qingdusa/checksa*100));
			zhongdusaR = Double.parseDouble(df.format(zhongdusa/checksa*100));
			zzhongdusaR = Double.parseDouble(df.format(zzhongdusa/checksa*100));
			buliangsaR =  Double.parseDouble(df.format(buliangsa/checksa*100));
//			params.put("grade", "3");
		}
		

				params.put("gradeCheckNum", checksa);
				params.put("numQDSLBL", qingdusa);
				params.put("rateQDSLBL", qingdusaR+"%");
				params.put("numZDSLBL", zhongdusa);
				params.put("rateZDSLBL", zhongdusaR+"%");
				params.put("numZZDSLBL", zzhongdusa);
				params.put("rateZZDSLBL", zzhongdusaR+"%");
				params.put("numSLBL", buliangsa);
				params.put("rateSLBL", buliangsaR+"%");
				
			//	listT.add(grades);
				
				params.put("gradeCheckTotal", checksa);
				params.put("numQDSLBLTotal", qingdusa);
				params.put("rateQDSLBLTotal", qingdusaR+"%");
				params.put("numZDSLBLTotal", zhongdusa);
				params.put("rateSLBLTotal", zhongdusaR+"%");
				params.put("numZZDSLBLTotal", zzhongdusa);
				params.put("rateZZDSLBLTotal", zzhongdusaR+"%");
				params.put("numSLBLTotal", buliangsa);
				params.put("rateSLBLTotal", buliangsaR+"%");
		
		Map<String,Object> dushui = new HashMap<String,Object>();
		dushui.put("school", school);
		dushui.put("grade", "4年级");
		dushui.put("checkDate", checkDate);
		List<ResultEyesightDO> shiLii = resultEyesightDao.queryLuoYanShiLi(dushui);
		Map<String,Object> gradei = new HashMap<String,Object>();
		int checksi = shiLii.size();
		int qingdusi = 0;
		int zhongdusi = 0;
		int zzhongdusi = 0;
		int buliangsi = 0;
		Double qingdusiR = 0.0 ;
		Double zhongdusiR = 0.0 ;
		Double zzhongdusiR = 0.0 ;
		Double buliangsiR = 0.0 ;
		if(shiLie.size()>0){
			for (ResultEyesightDO resultEyesightDO : shiLii) {
				if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
					if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
						qingdusi++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
						zhongdusi++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
						zzhongdusi++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						buliangsi++;
					}
				}
			}
			qingdusiR = Double.parseDouble(df.format(qingdusi/checksi*100));
			zhongdusiR = Double.parseDouble(df.format(zhongdusi/checksi*100));
			zzhongdusiR = Double.parseDouble(df.format(zzhongdusi/checksi*100));
			buliangsiR =  Double.parseDouble(df.format(buliangsi/checksi*100));
			gradei.put("grade", "4");
			gradei.put("gradeCheckNum", checksi);
			gradei.put("numQDSLBL", qingdusi);
			gradei.put("rateQDSLBL", qingdusiR+"%");
			gradei.put("numZDSLBL", zhongdusi);
			gradei.put("rateZDSLBL", zhongdusiR+"%");
			gradei.put("numZZDSLBL", zzhongdusi);
			gradei.put("rateZZDSLBL", zzhongdusiR+"%");
			gradei.put("numSLBL", buliangsi);
			gradei.put("rateSLBL", buliangsiR+"%");
			
			listT.add(gradei);
			
		}
		
		Map<String,Object> dushuw = new HashMap<String,Object>();
		dushuw.put("school", school);
		dushuw.put("grade", "5年级");
		dushuw.put("checkDate", checkDate);
		List<ResultEyesightDO> shiLiw = resultEyesightDao.queryLuoYanShiLi(dushuw);
		Map<String,Object> gradew = new HashMap<String,Object>();
		int checkwu = shiLiw.size();
		int qingduwu = 0;
		int zhongduwu = 0;
		int zzhongduwu = 0;
		int buliangwu = 0;
		Double qingduwuR = 0.0 ;
		Double zhongduwuR = 0.0 ;
		Double zzhongduwuR = 0.0 ;
		Double buliangwuR = 0.0 ;
		if(shiLiw.size()>0){
			for (ResultEyesightDO resultEyesightDO : shiLiw) {
				if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
					if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
						qingduwu++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
						zhongduwu++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
						zzhongduwu++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						buliangwu++;
					}
				}
			}
			qingduwuR = Double.parseDouble(df.format(qingduwu/checkwu*100));
			zhongduwuR = Double.parseDouble(df.format(zhongduwu/checkwu*100));
			zzhongduwuR = Double.parseDouble(df.format(zzhongduwu/checkwu*100));
			buliangwuR =  Double.parseDouble(df.format(buliangwu/checkwu*100));
			gradew.put("grade", "5");
			gradew.put("gradeCheckNum", checkwu);
			gradew.put("numQDSLBL", qingduwu);
			gradew.put("rateQDSLBL", qingduwuR+"%");
			gradew.put("numZDSLBL", zhongduwu);
			gradew.put("rateZDSLBL", zhongduwuR+"%");
			gradew.put("numZZDSLBL", zzhongduwu);
			gradew.put("rateZZDSLBL", zzhongduwuR+"%");
			gradew.put("numSLBL", buliangwu);
			gradew.put("rateSLBL",buliangwuR+"%");
			
			listT.add(gradew);
			
		}
		
		Map<String,Object> dushul = new HashMap<String,Object>();
		dushul.put("school", school);
		dushul.put("grade", "6年级");
		dushul.put("checkDate", checkDate);
		List<ResultEyesightDO> shiLil = resultEyesightDao.queryLuoYanShiLi(dushul);
		Map<String,Object> gradel = new HashMap<String,Object>();
		int checkli = shiLil.size();
		int qingduli = 0;
		int zhongduli = 0;
		int zzhongduli = 0;
		int buliangli = 0;
		Double qingduliR = 0.0 ;
		Double zhongduliR = 0.0 ;
		Double zzhongduliR = 0.0 ;
		Double buliangliR = 0.0 ;
		if(shiLil.size()>0){
			for (ResultEyesightDO resultEyesightDO : shiLil) {
				if(resultEyesightDO.getDushu()!=null || resultEyesightDO.getDushu()!=""){
					if(Double.parseDouble(resultEyesightDO.getDushu())==4.9){
						qingduli++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu())>=4.6 && Double.parseDouble(resultEyesightDO.getDushu()) <= 4.9){
						zhongduli++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) <= 4.5){
						zzhongduli++;
					}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						buliangli++;
					}
				}
			}
			qingduliR = Double.parseDouble(df.format(qingduli/checkli*100));
			zhongduliR = Double.parseDouble(df.format(zhongduli/checkli*100));
			zzhongduliR = Double.parseDouble(df.format(zzhongduli/checkli*100));
			buliangliR =  Double.parseDouble(df.format(buliangli/checkli*100));
			gradel.put("grade", "6");
			gradel.put("gradeCheckNum", checkli);
			gradel.put("numQDSLBL", qingduli);
			gradel.put("rateQDSLBL", qingduliR+"%");
			gradel.put("numZDSLBL", zhongduli);
			gradel.put("rateZDSLBL", zhongduliR+"%");
			gradel.put("numZZDSLBL", zzhongduli);
			gradel.put("rateZZDSLBL", zzhongduliR+"%");
			gradel.put("numSLBL", buliangli);
			gradel.put("rateSLBL", buliangliR+"%");
			
			listT.add(gradel);
			
		}
	//	params.put("shilibuliang", listT);
		
		int checkT = checkyi+checker+checksa+checksi+checkwu+checkli;
		int qingNT = qingduyi+qingduer+qingdusa+qingdusi+qingduwu+qingduli;
		Double qingRT = qingduyiR+qingduerR+qingdusaR+qingdusiR+qingduwuR+qingduliR;
		int zhongNT= zhongduyi+zhongduer+zhongdusa+zhongdusi+zhongduwu+zhongduli;
		Double zhongRT= zhongduyiR+zhongduerR+zhongdusaR+zhongdusiR+zhongduwuR+zhongduliR;
		int zzhongNT= zzhongduyi+zzhongduer+zzhongdusa+zzhongdusi+zzhongduwu+zzhongduli;
		Double zzhongRT= zzhongduyiR+zzhongduerR+zzhongdusaR+zzhongdusiR+zzhongduwuR+zzhongduliR;
		int bulingNT= buliangyi+bulianger+buliangsa+buliangsi+buliangwu+buliangli;
		Double bulingRT= buliangyiR+buliangerR+buliangsaR+buliangsiR+buliangwuR+buliangliR;
		/*params.put("gradeCheckTotal", checkT);
		params.put("numQDSLBLTotal", qingNT);
		params.put("rateQDSLBLTotal", qingRT);
		params.put("numZDSLBLTotal", zhongNT);
		params.put("rateSLBLTotal", zhongRT);
		params.put("numZZDSLBLTotal", zzhongNT);
		params.put("rateZZDSLBLTotal", zzhongRT);
		params.put("numSLBLTotal", bulingNT);
		params.put("rateSLBLTotal", bulingRT);*/
		
		List<Map<String,Object>> jiajin = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> jia1 = new HashMap<String,Object>();
		Integer linchuangy = 0;
		Integer jiajinshiy = 0;
		Integer diy = 0;
		Integer zhongy = 0;
		Integer gaoy = 0;
		double linchuangr = 0;
		double jiajinshir =0;
		double dir = 0;
		double zhongr = 0;
		double gaor = 0;
		int jiay = shiLiy.size();
		if(shiLiy.size()>0){
			linchuangy = jiajinshi(school,checkDate,"1年级","first");
			jiajinshiy = jiajinshi(school,checkDate,"1年级","second");
			diy = jiajinshi(school,checkDate,"1年级","third");
			zhongy = jiajinshi(school,checkDate,"1年级","fourth");
			gaoy = jiajinshi(school,checkDate,"1年级","fifth");
			linchuangr = Double.parseDouble(df.format(linchuangy/jiay*100));
			jiajinshir = Double.parseDouble(df.format(jiajinshiy/jiay*100));
			dir = Double.parseDouble(df.format(diy/jiay*100));
			zhongr = Double.parseDouble(df.format(zhongy/jiay*100));
			gaor = Double.parseDouble(df.format(gaoy/jiay*100));
		}
		Integer jinzongy = linchuangy+jiajinshiy+diy+zhongy+gaoy;
		double jinzongr = linchuangr+jiajinshir+dir+zhongr+gaor;
		jia1.put("grade", "1");
		jia1.put("gradeCheckNum", jiay);
		jia1.put("numJSQQ", linchuangy);
		jia1.put("rateJSQQ", linchuangr+"%");
		jia1.put("numJXJS", jiajinshiy);
		jia1.put("rateJXJS", jiajinshir+"%");
		jia1.put("numDDJS", diy);
		jia1.put("rateDDJS", dir+"%");
		jia1.put("numZDJS", zhongy);
		jia1.put("rateZDJS", zhongr+"%");
		jia1.put("numGDJS", gaoy);
		jia1.put("rateGDJS", gaor+"%");
		jia1.put("numJS", jinzongy);
		jia1.put("rateJS", jinzongr+"%");
		//jiajin.add(jia1);
		
		Map<String,Object> jia2 = new HashMap<String,Object>();
		Integer linchuangy2 = 0;
		Integer jiajinshiy2 = 0;
		Integer diy2 = 0;
		Integer zhongy2 = 0;
		Integer gaoy2 = 0;
		double linchuangr2 = 0;
		double jiajinshir2 = 0;
		double dir2 = 0;
		double zhongr2 = 0;
		double gaor2 = 0;
		int jiay2 = shiLie.size();
		if(shiLie.size()>0){
			linchuangy2 = jiajinshi(school,checkDate,"2年级","first");
			jiajinshiy2 = jiajinshi(school,checkDate,"2年级","second");
			diy2 = jiajinshi(school,checkDate,"2年级","third");
			zhongy2 = jiajinshi(school,checkDate,"2年级","fourth");
			gaoy2 = jiajinshi(school,checkDate,"2年级","fifth");
			linchuangr2 = Double.parseDouble(df.format(linchuangy2/jiay2*100));
			jiajinshir2 = Double.parseDouble(df.format(jiajinshiy2/jiay2*100));
			dir2 = Double.parseDouble(df.format(diy2/jiay2*100));
			zhongr2 = Double.parseDouble(df.format(zhongy2/jiay2*100));
			gaor2 = Double.parseDouble(df.format(gaoy2/jiay2*100));
		}
		Integer jinzongy2 = linchuangy2+jiajinshiy2+diy2+zhongy2+gaoy2;
		double jinzongr2 = linchuangr2+jiajinshir2+dir2+zhongr2+gaor2;
		jia2.put("grade", "2");
		jia2.put("gradeCheckNum", jiay2);
		jia2.put("numJSQQ", linchuangy2);
		jia2.put("rateJSQQ", linchuangr2+"%");
		jia2.put("numJXJS", jiajinshiy2);
		jia2.put("rateJXJS", jiajinshir2+"%");
		jia2.put("numDDJS", diy2);
		jia2.put("rateDDJS", dir2+"%");
		jia2.put("numZDJS", zhongy2);
		jia2.put("rateZDJS", zhongr2+"%");
		jia2.put("numGDJS", gaoy2);
		jia2.put("rateGDJS", gaor2+"%");
		jia2.put("numJS", jinzongy2);
		jia2.put("rateJS", jinzongr2+"%");
		//jiajin.add(jia2);
		
		Map<String,Object> jia3 = new HashMap<String,Object>();
		Integer linchuangy3 = 0;
		Integer jiajinshiy3 = 0;
		Integer diy3 =0;
		Integer zhongy3 = 0;
		Integer gaoy3 = 0;
		double linchuangr3 = 0;
		double jiajinshir3 = 0;
		double dir3 =0;
		double zhongr3 = 0;
		double gaor3 =0;
		int jiay3 = shiLis.size();
		if(shiLis.size()>0){
			linchuangy3 = jiajinshi(school,checkDate,"3年级","first");
			jiajinshiy3 = jiajinshi(school,checkDate,"3年级","second");
			diy3 = jiajinshi(school,checkDate,"3年级","third");
			zhongy3 = jiajinshi(school,checkDate,"3年级","fourth");
			gaoy3 = jiajinshi(school,checkDate,"3年级","fifth");
			linchuangr3 = Double.parseDouble(df.format(linchuangy3/jiay3*100));
			jiajinshir3 = Double.parseDouble(df.format(jiajinshiy3/jiay3*100));
			dir3 = Double.parseDouble(df.format(diy3/jiay3*100));
			zhongr3 = Double.parseDouble(df.format(zhongy3/jiay3*100));
			gaor3 = Double.parseDouble(df.format(gaoy3/jiay3*100));
		}
		Integer jinzongy3 = linchuangy3+jiajinshiy3+diy3+zhongy3+gaoy3;
		double jinzongr3 = linchuangr3+jiajinshir3+dir3+zhongr3+gaor3;
		params.put("grade", "3");
		params.put("gradeCheckNum", jiay3);
		params.put("numJSQQ", linchuangy3);
		params.put("rateJSQQ", linchuangr3+"%");
		params.put("numJXJS", jiajinshiy3);
		params.put("rateJXJS", jiajinshir3+"%");
		params.put("numDDJS", diy3);
		params.put("rateDDJS", dir3+"%");
		params.put("numZDJS", zhongy3);
		params.put("rateZDJS", zhongr3+"%");
		params.put("numGDJS", gaoy3);
		params.put("rateGDJS", gaor3+"%");
		params.put("numJS", jinzongy3);
		params.put("rateJS", jinzongr3+"%");
		//jiajin.add(jia3);
		params.put("numJSQQTotal", linchuangy3);
		params.put("rateJSQQTotal", linchuangr3+"%");
		params.put("numJXJSTotal", jiajinshiy3);
		params.put("rateJXJSTotal", jiajinshir3+"%");
		params.put("numDDJSTotal", diy3);
		params.put("rateDDJSTotal", dir3+"%");
		params.put("numZDJSTotal", zhongy3);
		params.put("rateZDJSTotal", zhongr3+"%");
		params.put("numGDJSTotal", gaoy3);
		params.put("rateGDJSTotal", gaor3+"%");
		params.put("numJSTotal", jinzongy3);
		params.put("rateJSTotal", jinzongr3+"%");
		
		Map<String,Object> jia4 = new HashMap<String,Object>();
		Integer linchuangy4 = 0;
		Integer jiajinshiy4 = 0;
		Integer diy4 = 0;
		Integer zhongy4 = 0;
		Integer gaoy4 =0;
		double linchuangr4 = 0;
		double jiajinshir4 =0;
		double dir4 = 0;
		double zhongr4 = 0;
		double gaor4 = 0;
		int jiay4 = shiLii.size();
		if(shiLii.size()>0){
			linchuangy4 = jiajinshi(school,checkDate,"4年级","first");
			jiajinshiy4 = jiajinshi(school,checkDate,"4年级","second");
			diy4 = jiajinshi(school,checkDate,"4年级","third");
			zhongy4 = jiajinshi(school,checkDate,"4年级","fourth");
			gaoy4 = jiajinshi(school,checkDate,"4年级","fifth");
			linchuangr4 = Double.parseDouble(df.format(linchuangy4/jiay4*100));
			jiajinshir4 = Double.parseDouble(df.format(jiajinshiy4/jiay4*100));
			dir4 = Double.parseDouble(df.format(diy4/jiay4*100));
			zhongr4 = Double.parseDouble(df.format(zhongy4/jiay4*100));
			gaor4 = Double.parseDouble(df.format(gaoy4/jiay4*100));
		}
		Integer jinzongy4 = linchuangy4+jiajinshiy4+diy4+zhongy4+gaoy4;
		double jinzongr4 = linchuangr4+jiajinshir4+dir4+zhongr4+gaor4;
		jia4.put("grade", "4");
		jia4.put("gradeCheckNum", jiay4);
		jia4.put("numJSQQ", linchuangy4);
		jia4.put("rateJSQQ", linchuangr4+"%");
		jia4.put("numJXJS", jiajinshiy4);
		jia4.put("rateJXJS", jiajinshir4+"%");
		jia4.put("numDDJS", diy4);
		jia4.put("rateDDJS", dir4+"%");
		jia4.put("numZDJS", zhongy4);
		jia4.put("rateZDJS", zhongr4+"%");
		jia4.put("numGDJS", gaoy4);
		jia4.put("rateGDJS", gaor4+"%");
		jia4.put("numJS", jinzongy4);
		jia4.put("rateJS", jinzongr4+"%");
		//jiajin.add(jia4);
		
		Map<String,Object> jia5 = new HashMap<String,Object>();
		Integer linchuangy5 = 0;
		Integer jiajinshiy5 = 0;
		Integer diy5 = 0;
		Integer zhongy5 = 0;
		Integer gaoy5 = 0;
		double linchuangr5 = 0;
		double jiajinshir5 = 0;
		double dir5 = 0;
		double zhongr5 = 0;
		double gaor5 = 0;
		int jiay5 = shiLii.size();
		if(shiLii.size()>0){
			linchuangy5 = jiajinshi(school,checkDate,"5年级","first");
			jiajinshiy5 = jiajinshi(school,checkDate,"5年级","second");
			diy5 = jiajinshi(school,checkDate,"5年级","third");
			zhongy5 = jiajinshi(school,checkDate,"5年级","fourth");
			gaoy5 = jiajinshi(school,checkDate,"5年级","fifth");
			linchuangr5 = Double.parseDouble(df.format(linchuangy5/jiay5*100));
			jiajinshir5 = Double.parseDouble(df.format(jiajinshiy5/jiay5*100));
			dir5 = Double.parseDouble(df.format(diy5/jiay5*100));
			zhongr5 = Double.parseDouble(df.format(zhongy5/jiay5*100));
			gaor5 = Double.parseDouble(df.format(gaoy5/jiay5*100));
		}
		Integer jinzongy5 = linchuangy5+jiajinshiy5+diy5+zhongy5+gaoy5;
		double jinzongr5 = linchuangr5+jiajinshir5+dir5+zhongr5+gaor5;
		jia5.put("grade", "5");
		jia5.put("gradeCheckNum", jiay5);
		jia5.put("numJSQQ", linchuangy5);
		jia5.put("rateJSQQ", linchuangr5+"%");
		jia5.put("numJXJS", jiajinshiy5);
		jia5.put("rateJXJS", jiajinshir5+"%");
		jia5.put("numDDJS", diy5);
		jia5.put("rateDDJS", dir5+"%");
		jia5.put("numZDJS", zhongy5);
		jia5.put("rateZDJS", zhongr5+"%");
		jia5.put("numGDJS", gaoy5);
		jia5.put("rateGDJS", gaor5+"%");
		jia5.put("numJS", jinzongy5);
		jia5.put("rateJS", jinzongr5+"%");
		//jiajin.add(jia5);
		
		Map<String,Object> jia6 = new HashMap<String,Object>();
		Integer linchuangy6 = 0;
		Integer jiajinshiy6 = 0;
		Integer diy6 = 0;
		Integer zhongy6 = 0;
		Integer gaoy6 = 0;
		double linchuangr6 = 0;
		double jiajinshir6 = 0;
		double dir6 = 0;
		double zhongr6 = 0;
		double gaor6 = 0;
		int jiay6 = shiLii.size();
		if(shiLii.size()>0){
			linchuangy6 = jiajinshi(school,checkDate,"6年级","first");
			jiajinshiy6 = jiajinshi(school,checkDate,"6年级","second");
			diy6 = jiajinshi(school,checkDate,"6年级","third");
			zhongy6 = jiajinshi(school,checkDate,"6年级","fourth");
			gaoy6 = jiajinshi(school,checkDate,"6年级","fifth");
			linchuangr6 = Double.parseDouble(df.format(linchuangy6/jiay6*100));
			jiajinshir6 = Double.parseDouble(df.format(jiajinshiy6/jiay6*100));
			dir6 = Double.parseDouble(df.format(diy6/jiay6*100));
			zhongr6 = Double.parseDouble(df.format(zhongy6/jiay6*100));
			gaor6 = Double.parseDouble(df.format(gaoy6/jiay6*100));
		}
		Integer jinzongy6 = linchuangy6+jiajinshiy6+diy6+zhongy6+gaoy6;
		double jinzongr6 = linchuangr6+jiajinshir6+dir6+zhongr6+gaor6;
		jia6.put("grade", "6");
		jia6.put("gradeCheckNum", jiay6);
		jia6.put("numJSQQ", linchuangy6);
		jia6.put("rateJSQQ", linchuangr6+"%");
		jia6.put("numJXJS", jiajinshiy6);
		jia6.put("rateJXJS", jiajinshir6+"%");
		jia6.put("numDDJS", diy6);
		jia6.put("rateDDJS", dir6+"%");
		jia6.put("numZDJS", zhongy6);
		jia6.put("rateZDJS", zhongr6+"%");
		jia6.put("numGDJS", gaoy6);
		jia6.put("rateGDJS", gaor6+"%");
		jia6.put("numJS", jinzongy6);
		jia6.put("rateJS", jinzongr6+"%");
		jiajin.add(jia6);
		
	//	params.put("jinshi", jiajin);
		
		int jiaJS1 = linchuangy+linchuangy2+linchuangy3+linchuangy4+linchuangy5+linchuangy6;
		Double jiaJS2 = linchuangr+linchuangr2+linchuangr3+linchuangr4+linchuangr5+linchuangr6;
		int jiaJS3= jiajinshiy+jiajinshiy2+jiajinshiy3+jiajinshiy4+jiajinshiy5+jiajinshiy6;
		Double jiaJS4= jiajinshir+jiajinshir2+jiajinshir3+jiajinshir4+jiajinshir5+jiajinshir6;
		int jiaJS5= diy+diy2+diy3+diy4+diy5+diy6;
		Double jiaJS6= dir+dir2+dir3+dir4+dir5+dir6;
		int jiaJS7= zhongy+zhongy2+zhongy3+zhongy4+zhongy5+zhongy6;
		Double jiaJS8= zhongr+zhongr2+zhongr3+zhongr4+zhongr5+zhongr6;
		int jiaJS9= gaoy+gaoy2+gaoy3+gaoy4+gaoy5+gaoy6;
		Double jiaJS11= gaor+gaor2+gaor3+gaor4+gaor5+gaor6;
		int jiaJS22= jinzongy+jinzongy2+jinzongy3+jinzongy4+jinzongy5+jinzongy6;
		Double jiaJS33= jinzongr+jinzongr2+jinzongr3+jinzongr4+jinzongr5+jinzongr6;
		/*params.put("numJSQQTotal", jiaJS1);
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
		params.put("rateJSTotal", jiaJS33);*/
		
		Map<String,Object> jieguo = new HashMap<String,Object>();
		/*List<xueshengDO> yinianji = yinianji(school,checkDate);
		List<xueshengDO> ernianji = ernianji(school,checkDate);
		List<xueshengDO> sannianji = sannianji(school,checkDate);
		List<xueshengDO> sinianji = sinianji(school,checkDate);
		List<xueshengDO> wunianji = wunianji(school,checkDate);
		List<xueshengDO> liunianji = liunianji(school,checkDate);*/
		List<Map<String,Object>> sannianji1 = sannianji1(school,checkDate);
		List<Map<String,Object>> sannianji2 = sannianji2(school,checkDate);
		//params.put("yinianji", yinianji);
		//params.put("ernianji", ernianji);
		params.put("sannianji1", sannianji1);
		params.put("sannianji2", sannianji2);
		//params.put("sinianji", sinianji);
		//params.put("wunianji", wunianji);
		//params.put("liunianji", liunianji);
		
		return params;
		
		
		
		
		
		
	}
	
	public List<Map<String, Object>> sannianji1(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){		
			List<Map<String,Object>> xuesheng = new ArrayList<Map<String,Object>>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"3年级",checkDate,"1");
			for (StudentDO studentDO : grade) {
				Map<String,Object> mapPP = new HashMap<String,Object>();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				mapPP.put("studentName", studentDO.getStudentName());
				mapPP.put("studentSex", studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					mapPP.put("nakedNearvisionOd", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					mapPP.put("nakedNearvisionOs", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					mapPP.put("lifeNearvisionOd", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					mapPP.put("lifeNearvisionOs", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					mapPP.put("diopterSOs", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("diopterCOs", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("diopterAOs", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					mapPP.put("diopterSOd", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("diopterCOd", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("diopterAOd", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					mapPP.put("cornealR1Os", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					mapPP.put("cornealR2Os", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					mapPP.put("cornealR1Od", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					mapPP.put("cornealR2Od", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					mapPP.put("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					mapPP.put("secondCheckOs",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					mapPP.put("eyePressureOd", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					mapPP.put("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng.add(mapPP);
			}
			
				
		return xuesheng;
		
	}
	
	public List<Map<String, Object>> sannianji2(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){		
			List<Map<String,Object>> xuesheng = new ArrayList<Map<String,Object>>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"3年级",checkDate,"2");
			for (StudentDO studentDO : grade) {
				Map<String,Object> mapPP = new HashMap<String,Object>();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				mapPP.put("studentName", studentDO.getStudentName());
				mapPP.put("studentSex", studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					mapPP.put("nakedNearvisionOd", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					mapPP.put("nakedNearvisionOs", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					mapPP.put("lifeNearvisionOd", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					mapPP.put("lifeNearvisionOs", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					mapPP.put("diopterSOs", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("diopterCOs", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("diopterAOs", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					mapPP.put("diopterSOd", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("diopterCOd", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("diopterAOd", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					mapPP.put("cornealR1Os", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					mapPP.put("cornealR2Os", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					mapPP.put("cornealR1Od", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					mapPP.put("cornealR2Od", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					mapPP.put("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					mapPP.put("secondCheckOs",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					mapPP.put("eyePressureOd", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					mapPP.put("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng.add(mapPP);
			}
			
				
		return xuesheng;
		
	}
	
	
	public List<xueshengDO> yinianji(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){
		
		Map<String,Object> map = new HashMap<String,Object>();
		//List<Map<String,Object>> jieguo = new ArrayList<Map<String,Object>>();
		List<xueshengDO> jieguo = new ArrayList<>();
		List<StudentDO> classCount = studentDao.getCheckNianjiNum(school,"1年级",checkDate);
		for(int i = 1 ; i<=classCount.size();i++){
			Map<String,Object> stu = new HashMap<String,Object>();
			List<xueshengDO> xuesheng = new ArrayList<>();
			xueshengDO xss = new xueshengDO();
			//List<Map<String,Object>> xuesheng = new ArrayList<Map<String,Object>>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"1年级",checkDate,String.valueOf(i));
			for (StudentDO studentDO : grade) {
				xueshengDO xs = new xueshengDO();
				List<xueshengDO> xuesheng2 = xs.getXuesheng();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				xs.setStudentName(studentDO.getStudentName());
				xs.setStudentSex(studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					xs.setNakedNearvisionOd(resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					xs.setNakedNearvisionOs(resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					xs.setLifeNearvisionOd(resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					xs.setLifeNearvisionOs(resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					xs.setDiopterSOs(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOs(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOs(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					xs.setDiopterSOd(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOd(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOd(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					xs.setCornealR1Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					xs.setCornealR2Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					xs.setCornealR1Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					xs.setCornealR2Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					xs.setSecondCheckOd(resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					xs.setSecondCheckOs(resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					xs.setEyePressureOd(resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					xs.setEyePressureOs(resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng2.add(xs);
				xuesheng.addAll(xuesheng2);
			}
			
			xss.setGradeFJ("1");
			xss.setFirstClass(i);
			xss.setCheckDate(checkDate);
			jieguo.add(xss);
			jieguo.addAll(xuesheng);
			
		}
				
		return jieguo;
		
	}
	
	public List<xueshengDO> ernianji(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<xueshengDO> jieguo = new ArrayList<>();
		List<StudentDO> classCount = studentDao.getCheckNianjiNum(school,"2年级",checkDate);
		for(int i = 1 ; i<=classCount.size();i++){
			Map<String,Object> stu = new HashMap<String,Object>();
			List<xueshengDO> xuesheng = new ArrayList<>();
			xueshengDO xss = new xueshengDO();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"2年级",checkDate,String.valueOf(i));
			for (StudentDO studentDO : grade) {
				xueshengDO xs = new xueshengDO();
				List<xueshengDO> xuesheng2 = xs.getXuesheng();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				xs.setStudentName(studentDO.getStudentName());
				xs.setStudentSex(studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					xs.setNakedNearvisionOd(resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					xs.setNakedNearvisionOs(resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					xs.setLifeNearvisionOd(resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					xs.setLifeNearvisionOs(resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					xs.setDiopterSOs(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOs(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOs(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					xs.setDiopterSOd(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOd(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOd(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					xs.setCornealR1Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					xs.setCornealR2Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					xs.setCornealR1Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					xs.setCornealR2Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					xs.setSecondCheckOd(resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					xs.setSecondCheckOs(resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					xs.setEyePressureOd(resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					xs.setEyePressureOs(resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng2.add(xs);
				xuesheng.addAll(xuesheng2);
			}
			
			xss.setGradeFJ("2");
			xss.setFirstClass(i);
			xss.setCheckDate(checkDate);
			jieguo.add(xss);
			jieguo.addAll(xuesheng);
			
		}
				
		return jieguo;
		
	}
	
	public List<xueshengDO> sannianji(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<xueshengDO> jieguo = new ArrayList<>();
		xueshengDO xss = new xueshengDO();
		List<StudentDO> classCount = studentDao.getCheckNianjiNum(school,"3年级",checkDate);
		for(int i = 1 ; i<=classCount.size();i++){
			Map<String,Object> stu = new HashMap<String,Object>();
			List<xueshengDO> xuesheng = new ArrayList<>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"3年级",checkDate,String.valueOf(i));
			for (StudentDO studentDO : grade) {
				xueshengDO xs = new xueshengDO();
				List<xueshengDO> xuesheng2 = xs.getXuesheng();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				xs.setStudentName(studentDO.getStudentName());
				xs.setStudentSex(studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					xs.setNakedNearvisionOd(resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					xs.setNakedNearvisionOs(resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					xs.setLifeNearvisionOd(resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					xs.setLifeNearvisionOs(resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					xs.setDiopterSOs(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOs(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOs(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					xs.setDiopterSOd(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOd(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOd(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					xs.setCornealR1Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					xs.setCornealR2Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					xs.setCornealR1Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					xs.setCornealR2Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					xs.setSecondCheckOd(resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					xs.setSecondCheckOs(resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					xs.setEyePressureOd(resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					xs.setEyePressureOs(resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng2.add(xs);
				xuesheng.addAll(xuesheng2);
			}
			
			xss.setGradeFJ("3");
			xss.setFirstClass(i);
			xss.setCheckDate(checkDate);
			jieguo.add(xss);
			jieguo.addAll(xuesheng);
			
		}
				
		return jieguo;
		
	}
	
	public List<xueshengDO> sinianji(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<xueshengDO> jieguo = new ArrayList<>();
		xueshengDO xss = new xueshengDO();
		List<StudentDO> classCount = studentDao.getCheckNianjiNum(school,"4年级",checkDate);
		for(int i = 1 ; i<=classCount.size();i++){
			Map<String,Object> stu = new HashMap<String,Object>();
			List<xueshengDO> xuesheng = new ArrayList<>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"4年级",checkDate,String.valueOf(i));
			for (StudentDO studentDO : grade) {
				xueshengDO xs = new xueshengDO();
				List<xueshengDO> xuesheng2 = xs.getXuesheng();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				xs.setStudentName(studentDO.getStudentName());
				xs.setStudentSex(studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					xs.setNakedNearvisionOd(resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					xs.setNakedNearvisionOs(resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					xs.setLifeNearvisionOd(resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					xs.setLifeNearvisionOs(resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					xs.setDiopterSOs(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOs(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOs(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					xs.setDiopterSOd(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOd(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOd(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					xs.setCornealR1Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					xs.setCornealR2Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					xs.setCornealR1Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					xs.setCornealR2Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					xs.setSecondCheckOd(resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					xs.setSecondCheckOs(resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					xs.setEyePressureOd(resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					xs.setEyePressureOs(resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng2.add(xs);
				xuesheng.addAll(xuesheng2);
			}
			
			xss.setGradeFJ("4");
			xss.setFirstClass(i);
			xss.setCheckDate(checkDate);
			jieguo.add(xss);
			jieguo.addAll(xuesheng);
			
		}
				
		return jieguo;
		
	}
	
	public List<xueshengDO> wunianji(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){
		Map<String,Object> stu = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<xueshengDO> jieguo = new ArrayList<>();
		xueshengDO xss = new xueshengDO();
		List<StudentDO> classCount = studentDao.getCheckNianjiNum(school,"5年级",checkDate);
		for(int i = 1 ; i<=classCount.size();i++){
			
			List<xueshengDO> xuesheng = new ArrayList<>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"5年级",checkDate,String.valueOf(i));
			for (StudentDO studentDO : grade) {
				xueshengDO xs = new xueshengDO();
				List<xueshengDO> xuesheng2 = xs.getXuesheng();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				xs.setStudentName(studentDO.getStudentName());
				xs.setStudentSex(studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					xs.setNakedNearvisionOd(resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					xs.setNakedNearvisionOs(resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					xs.setLifeNearvisionOd(resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					xs.setLifeNearvisionOs(resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					xs.setDiopterSOs(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOs(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOs(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					xs.setDiopterSOd(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOd(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOd(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					xs.setCornealR1Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					xs.setCornealR2Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					xs.setCornealR1Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					xs.setCornealR2Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					xs.setSecondCheckOd(resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					xs.setSecondCheckOs(resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					xs.setEyePressureOd(resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					xs.setEyePressureOs(resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng2.add(xs);
				xuesheng.addAll(xuesheng2);
			}
			
			xss.setGradeFJ("5");
			xss.setFirstClass(i);
			xss.setCheckDate(checkDate);
			jieguo.add(xss);
			jieguo.addAll(xuesheng);
			
		}
				
		return jieguo;
		
	}
	
	public List<xueshengDO> liunianji(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate
			){
		Map<String,Object> stu = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<xueshengDO> jieguo = new ArrayList<>();
		xueshengDO xss = new xueshengDO();
		List<StudentDO> classCount = studentDao.getCheckNianjiNum(school,"6年级",checkDate);
		for(int i = 1 ; i<=classCount.size();i++){
			
			List<xueshengDO> xuesheng = new ArrayList<>();
			List<StudentDO> grade = studentDao.queryStudentGrade(school,"6年级",checkDate,String.valueOf(i));
			for (StudentDO studentDO : grade) {
				xueshengDO xs = new xueshengDO();
				List<xueshengDO> xuesheng2 = xs.getXuesheng();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO.getIdentityCard(),checkDate);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO.getIdentityCard(),checkDate);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R1",checkDate);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO.getIdentityCard(), "R2",checkDate);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO.getIdentityCard(),checkDate);
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO.getIdentityCard(),checkDate);
				xs.setStudentName(studentDO.getStudentName());
				xs.setStudentSex(studentDO.getStudentSex());
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					xs.setNakedNearvisionOd(resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					xs.setNakedNearvisionOs(resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					xs.setLifeNearvisionOd(resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					xs.setLifeNearvisionOs(resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					xs.setDiopterSOs(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOs(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOs(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					xs.setDiopterSOd(resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					xs.setDiopterCOd(resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					xs.setDiopterAOd(resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					xs.setCornealR1Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					xs.setCornealR2Os(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					xs.setCornealR1Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					xs.setCornealR2Od(resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					xs.setSecondCheckOd(resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					xs.setSecondCheckOs(resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					xs.setEyePressureOd(resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					xs.setEyePressureOs(resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}
				xuesheng2.add(xs);
				xuesheng.addAll(xuesheng2);
			}
			
			xss.setGradeFJ("6");
			xss.setFirstClass(i);
			xss.setCheckDate(checkDate);
			jieguo.add(xss);
			jieguo.addAll(xuesheng);
			
		}
				
		return jieguo;
		
	}
	
	
	public Integer jiajinshi(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate,
			@RequestParam(value= "grade",required=false) String grade,String type){
		Integer first = 0;
		Integer second = 0;
		Integer third = 0;
		Integer fourth = 0;
		Integer fifth = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("school", school);
		mapP.put("checkDate", checkDate);
		mapP.put("grade", grade);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					Map<String, Object> map = new HashMap<String,Object>();
					map.put("identityCard", resultEyesightDO.getIdentityCard());
					List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map);
					if(studentMyopia.size()>0){
						if(Double.parseDouble(resultEyesightDO.getDushu()) == 5.0){
							if(studentMyopia.get(0).getDengxiaoqiujing() >= -0.5 && studentMyopia.get(0).getDengxiaoqiujing() <= -0.75){
								first++;
							}else if(studentMyopia.get(0).getDengxiaoqiujing() < -0.5){
								second++;
							}
						}else if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
							if(studentMyopia.get(0).getDengxiaoqiujing() > -0.5 && studentMyopia.get(0).getDengxiaoqiujing() < -3.0){
								third++;
							}else if(studentMyopia.get(0).getDengxiaoqiujing() > -3.25 && studentMyopia.get(0).getDengxiaoqiujing() < -6.0){
								fourth++;
							}else if(studentMyopia.get(0).getDengxiaoqiujing() > -6.0){
								fifth++;
							}
						}
					}
				}
			}
		}
		
		if(type.equals("first")){
			return first;
		}else if(type.equals("second")){
			return second;
		}else if(type.equals("third")){
			return third;
		}else if(type.equals("fourth")){
			return fourth;
		}else if(type.equals("fifth")){
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
        zipOutputStream = new ZipOutputStream(output,Charset.forName("utf-8"));  
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
			@RequestParam(value= "checkDate",required=false) String checkDate,
			@RequestParam(value= "studentSex",required=false) Integer studentSex,
			@RequestParam(value= "grade",required=false) String grade,
			@RequestParam(value= "studentClass",required=false) String studentClass) {
		Integer first = 0;
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("checkDate", checkDate);
		mapP.put("grade", grade);
		mapP.put("studentClass", studentClass);
		List<ResultEyesightDO> jinShi = resultEyesightDao.jinShi(mapP);
		if(jinShi.size()>0){
			for (ResultEyesightDO resultEyesightDO : jinShi) {
				if(resultEyesightDO.getDushu()!= null || resultEyesightDO.getDushu()!= ""){
					if(Double.parseDouble(resultEyesightDO.getDushu()) < 5.0){
						Map<String, Object> map = new HashMap<String,Object>();
						map.put("identityCard", resultEyesightDO.getIdentityCard());
						List<ResultDiopterDO> studentMyopia = resultDiopterDao.getStudentMyopia(map);
						if(studentMyopia.size()>0){
							if(studentMyopia.get(0).getDengxiaoqiujing() <= -0.5){
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
			@RequestParam(value= "checkDate",required=false) String checkDate,
			@RequestParam(value= "studentSex",required=false) Integer studentSex,
			@RequestParam(value= "grade",required=false) String grade) {
		
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("studentSex", studentSex);
		mapP.put("school", school);
		mapP.put("checkDate", checkDate);
		mapP.put("grade", grade);
		List<StudentDO> checkUserNum = studentDao.getCheckUserNum(mapP);
		if(checkUserNum.size()<=0){
			return 1;
		}else{
			return checkUserNum.size();
		}
	}
	
	public Integer jianchaban(String school,
			@RequestParam(value= "checkDate",required=false) String checkDate,
			@RequestParam(value= "studentClass",required=false) String studentClass,
			@RequestParam(value= "grade",required=false) String grade) {
		
		Map<String, Object> mapP = new HashMap<String,Object>();
		mapP.put("school", school);
		mapP.put("checkDate", checkDate);
		mapP.put("studentClass", studentClass);
		mapP.put("grade", grade);
		List<StudentDO> checkUserNum = studentDao.getGradeClassCheck(mapP);
		if(checkUserNum.size()<=0){
			return 1;
		}else{
			return checkUserNum.size();
		}
	}
	
	   public static String savePictoServer(String base64String,String path)throws Exception{

	        BASE64Decoder decoder = new BASE64Decoder();  //此类需引入的jar包
	        //要把+在上传时变成的空格再改为+
	        //base64String = base64String.replaceAll(" ", "+");
	        //去掉“data:image/png;base64,”后面才是base64编码，去掉之后才能解析
	        base64String = base64String.replace("data:image/png;base64,","");
	        //在本地指定位置建立文件夹，path由控制台那边进行定义
	        File dir=new File(path);
	        if(!dir.exists()){
	         dir.mkdirs();
	        }
	        String uuidImg=UUID.randomUUID().toString()+".png";
	        String fileName=path+uuidImg;
	        try {  
	            byte[] buffer = decoder.decodeBuffer(base64String);  
	            OutputStream os = new FileOutputStream(fileName);
	            /*for(int i =0;i<buffer.length;++i){
	                if(buffer[i]<0){//调整异常数据
	                    buffer[i]+=256;
	                }
	            }*/
	            os.write(buffer);  
	            os.close();  
	        } catch (IOException e) {  
	            throw new RuntimeException();  
	        }  

	       return fileName;
	    }

	   public static String getImageStr(String imgFile) {  
	         InputStream in = null;  
	         byte[] data = null;  
	         try {  
	             in = new FileInputStream(imgFile);  
	             data = new byte[in.available()];  
	             in.read(data);  
	             in.close();  
	         } catch (IOException e) {  
	             e.printStackTrace();  
	         }  
	         BASE64Encoder encoder = new BASE64Encoder();  
	         return encoder.encode(data);  
	     }

	
}
