package com.shaicha.information.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.common.utils.ExcelExportUtil4DIY;
import com.shaicha.common.config.BootdoConfig;
import com.shaicha.information.dao.LinShiUrlDao;
import com.shaicha.information.dao.ResultCornealDao;
import com.shaicha.information.dao.ResultDiopterDao;
import com.shaicha.information.dao.ResultEyeaxisDao;
import com.shaicha.information.dao.ResultEyepressureDao;
import com.shaicha.information.dao.ResultEyesightDao;
import com.shaicha.information.dao.SchoolReportDao;
import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.SchoolReportService;
import com.shaicha.information.dao.ActivityListDao;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SchoolReportServiceImpl implements SchoolReportService{
	@Autowired
	private SchoolReportDao schoolReportDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private LinShiUrlDao linShiUrlDao;
	@Autowired
	private ResultDiopterDao resultDiopterDao;
	@Autowired
	private ResultEyesightDao resultEyesightDao;
	@Autowired
	private ResultCornealDao resultCornealDao;
	@Autowired
	private ResultEyeaxisDao resultEyeaxisDao;
	@Autowired
	private ResultEyepressureDao resultEyepressureDao;
	@Autowired
	private ActivityListDao activityListDao;
	
	//历年近视率走势图
	@Override
	public Map<String, List<Object>> overYearMyopia(String school) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> da = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("0.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		int qianrenshu = schoolReportDao.linianCheckNum(school,sdf.format(qian));
		int qianjinshi = schoolReportDao.linianjinshi(school,sdf.format(qian));
		int qurenshu = schoolReportDao.linianCheckNum(school,sdf.format(qu));
		int qujinshi = schoolReportDao.linianjinshi(school,sdf.format(qu));
		int xianrenshu = schoolReportDao.linianCheckNum(school,sdf.format(xian));
		int xianjinshi = schoolReportDao.linianjinshi(school,sdf.format(xian));
		da.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
		da.add(qurenshu==0?0:Double.parseDouble(df.format((double)qujinshi/(double)qurenshu*100)));
		da.add(xianrenshu==0?0:Double.parseDouble(df.format((double)xianjinshi/(double)xianrenshu*100)));
		List<Object> ye = new ArrayList<>();
		ye.add(sdf.format(qian));
		ye.add(sdf.format(qu));
		ye.add(sdf.format(xian));
		
		map.put("overYearMyopia", da);
		map.put("year", ye);
		return map;
	}

	//各年级近视
	@Override
	public Map<String, List<Object>> gradeMyopia(String school, Integer activityId) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> da = new ArrayList<Object>();
		List<Object> ye = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("0.0");
		List<StudentDO> gegradejs = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gegradejs) {
			int gradeCheck = schoolReportDao.gradeCheck(activityId, school, studentDO.getGrade());
			int gradeCheckjinshi = schoolReportDao.gradeCheckjinshi(activityId, school, studentDO.getGrade());
			da.add(gradeCheck==0?0:Double.parseDouble(df.format((double)gradeCheckjinshi/(double)gradeCheck*100)));
			ye.add(studentDO.getGrade());
		}
		map.put("gradeMyopia", da);
		map.put("grade", ye);
		return map;
	}

	//历年各年级近视率走势图
	@Override
	public Map<String, List<Object>> overYearGradeMyopia(String school) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> myt1 = new ArrayList<Object>();
		List<Object> myt2 = new ArrayList<Object>();
		List<Object> myt3 = new ArrayList<Object>();
		List<Object> ye = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("0.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		List<StudentDO> gegradejs = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gegradejs) {
			int qianrenshu = schoolReportDao.liniangradeCheck(school,studentDO.getGrade(),sdf.format(qian));
			int qianjinshi = schoolReportDao.liniangradeCheckjinshi(school,studentDO.getGrade(),sdf.format(qian));
			myt1.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
			int qurenshu = schoolReportDao.liniangradeCheck(school,studentDO.getGrade(),sdf.format(qu));
			int qujinshi = schoolReportDao.liniangradeCheckjinshi(school,studentDO.getGrade(),sdf.format(qu));
			myt2.add(qurenshu==0?0:Double.parseDouble(df.format((double)qujinshi/(double)qurenshu*100)));
			int xianrenshu = schoolReportDao.liniangradeCheck(school,studentDO.getGrade(),sdf.format(xian));
			int xianjinshi = schoolReportDao.liniangradeCheckjinshi(school,studentDO.getGrade(),sdf.format(xian));
			myt3.add(xianrenshu==0?0:Double.parseDouble(df.format((double)xianjinshi/(double)xianrenshu*100)));
		}
		map.put("seventeen", myt1);
		map.put("eighteen", myt2);
		map.put("nineteen", myt3);
		return map;
	}

	//男、女生近视
	@Override
	public Map<String, List<Double>> studentSexMyopia(String school, Integer activityId) {
		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("0.0");
		int nanCheckNum = schoolReportDao.sexCheckNum(activityId, school, 1);//男
		int nanCheckjinshi = schoolReportDao.sexCheckjinshi(activityId, school, 1);
		myt.add(nanCheckNum==0?0:Double.parseDouble(df.format((double)nanCheckjinshi/(double)nanCheckNum*100)));
		int nvCheckNum = schoolReportDao.sexCheckNum(activityId, school, 2);
		int nvCheckjinshi = schoolReportDao.sexCheckjinshi(activityId, school, 2);
		myt.add(nvCheckNum==0?0:Double.parseDouble(df.format((double)nvCheckjinshi/(double)nvCheckNum*100)));
		map.put("studentSexMyopia",myt);
		return map;
	}

	//历年男生近视率走势图
	@Override
	public Map<String, List<Object>> overYearSexNan(String school) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> myt = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("0.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		int qianrenshu = schoolReportDao.linianSexCheckNum(school,1,sdf.format(qian));
		int qianjinshi = schoolReportDao.linianSexCheckjinshi(school,1,sdf.format(qian));
		myt.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
		int qurenshu = schoolReportDao.linianSexCheckNum(school,1,sdf.format(qu));
		int qujinshi = schoolReportDao.linianSexCheckjinshi(school,1,sdf.format(qu));
		myt.add(qurenshu==0?0:Double.parseDouble(df.format((double)qujinshi/(double)qurenshu*100)));
		int xianrenshu = schoolReportDao.linianSexCheckNum(school,1,sdf.format(xian));
		int xianjinshi = schoolReportDao.linianSexCheckjinshi(school,1,sdf.format(xian));
		myt.add(xianrenshu==0?0:Double.parseDouble(df.format((double)xianjinshi/(double)xianrenshu*100)));
		map.put("studentSexMyopia",myt);
		return map;
	}

	//历年女生近视率走势图
	@Override
	public Map<String, List<Object>> overYearSexNv(String school) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> myt = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("0.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		int qianrenshu = schoolReportDao.linianSexCheckNum(school,2,sdf.format(qian));
		int qianjinshi = schoolReportDao.linianSexCheckjinshi(school,2,sdf.format(qian));
		myt.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
		int qurenshu = schoolReportDao.linianSexCheckNum(school,2,sdf.format(qu));
		int qujinshi = schoolReportDao.linianSexCheckjinshi(school,2,sdf.format(qu));
		myt.add(qurenshu==0?0:Double.parseDouble(df.format((double)qujinshi/(double)qurenshu*100)));
		int xianrenshu = schoolReportDao.linianSexCheckNum(school,2,sdf.format(xian));
		int xianjinshi = schoolReportDao.linianSexCheckjinshi(school,2,sdf.format(xian));
		myt.add(xianrenshu==0?0:Double.parseDouble(df.format((double)xianjinshi/(double)xianrenshu*100)));
		map.put("studentSexMyopia",myt);
		return map;
	}

	//男女生近视年级
	@Override
	public Map<String, List<Object>> overYearGradeSex(String school, Integer activityId) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		DecimalFormat df = new DecimalFormat("0.0");
		List<Object> myt1 = new ArrayList<Object>();
		List<Object> myt2 = new ArrayList<Object>();
		List<StudentDO> gegradejs = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gegradejs) {
			int nanGradeCheckNum = schoolReportDao.sexGradeCheckNum(activityId,school,studentDO.getGrade(),1);//男
			int nanGradeCheckjinshi = schoolReportDao.sexGradeCheckjinshi(activityId,school,studentDO.getGrade(),1);
			myt1.add(nanGradeCheckNum==0?0:Double.parseDouble(df.format((double)nanGradeCheckjinshi/(double)nanGradeCheckNum*100)));
		
			int nvGradeCheckNum = schoolReportDao.sexGradeCheckNum(activityId,school,studentDO.getGrade(),2);
			int nvGradeCheckjinshi = schoolReportDao.sexGradeCheckjinshi(activityId,school,studentDO.getGrade(),2);
			myt2.add(nvGradeCheckNum==0?0:Double.parseDouble(df.format((double)nvGradeCheckjinshi/(double)nvGradeCheckNum*100)));
		
		}
		
		map.put("overYearSexNan", myt1);
		map.put("overYearSexNv", myt2);
		return map;
	}

	//学校报告
	@Override
	public void baogaoxuexiao(HttpServletRequest request, HttpServletResponse response) {
		String school = request.getParameter("school");
		String activityId = request.getParameter("activityId");
		try {
			Map<String, Object> params = xuexiaobaogao(request, response);
			createDoc(response,params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "给学校报告检测.ftl");
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
	
	//学校数据
	public Map<String,Object> xuexiaobaogao(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		DecimalFormat df = new DecimalFormat("0.0");
		String school = request.getParameter("school");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String date = request.getParameter("date");
		
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
		int zongNum = schoolReportDao.zongNum(activityId,school);
		params.put("schoolNum", zongNum);
		params.put("checkNum", zongNum);
		params.put("checkRate", zongNum==0?0:df.format((double)zongNum/((double)zongNum)*100));
		List<Map<String,Object>> listg = new ArrayList<Map<String,Object>>();
		List<StudentDO> gradeshu = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradeshu) {
			Map<String,Object> gNum = new HashMap<>();
			int gradeCheckNum = schoolReportDao.gradeCheckNum(activityId,school,studentDO.getGrade());
			gNum.put("nianji", studentDO.getGrade());
			gNum.put("gradeNum", gradeCheckNum);
			gNum.put("gradeCheckNum", gradeCheckNum);
			gNum.put("gradeCheckRate", gradeCheckNum==0?0:df.format((double)gradeCheckNum/((double)gradeCheckNum)*100));
			listg.add(gNum);
		}
		params.put("nianjice", listg);
		
		//男女近视
		int nanCheckNum = schoolReportDao.schoolSexCheckNum(activityId,school,1);
		int nanjinshi = schoolReportDao.schoolSexjinshi(activityId,school,1);
		params.put("checkNanNum", nanCheckNum);
		params.put("myopiaNanRate", nanCheckNum==0?0:df.format(((double)nanjinshi/(double)nanCheckNum)*100));
		params.put("myopiaNanNum", nanjinshi);	
		int nvCheckNum = schoolReportDao.schoolSexCheckNum(activityId,school,2);
		int nvjinshi = schoolReportDao.schoolSexjinshi(activityId,school,2);
		params.put("checkNvNum", nvCheckNum);
		params.put("myopiaNvRate", nvCheckNum==0?0:df.format(((double)nvjinshi/(double)nvCheckNum)*100));
		params.put("myopiaNvNum", nvjinshi);
		
		//男近视
		List<Map<String,Object>> listnn = new ArrayList<Map<String,Object>>();
		List<StudentDO> gradenn = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradenn) {
			Map<String,Object> nnNum = new HashMap<>();
			int nanGradeCheckNum = schoolReportDao.sexGradeCheckNum(activityId,school,studentDO.getGrade(),1);
			int nanGradeCheckjinshi = schoolReportDao.sexGradeCheckjinshi(activityId,school,studentDO.getGrade(),1);
			nnNum.put("checkNanNum", nanGradeCheckNum);
			nnNum.put("myopiaNanRate", nanGradeCheckNum==0?0:df.format(((double)nanGradeCheckjinshi/(double)nanGradeCheckNum)*100));
			nnNum.put("nj", studentDO.getGrade());
			nnNum.put("myopiaNanNum", nanGradeCheckjinshi);
			listnn.add(nnNum);	
		}
		params.put("listnn", listnn);
		
		//女近视
		List<Map<String,Object>> listmm = new ArrayList<Map<String,Object>>();
		List<StudentDO> grademm = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : grademm) {
			Map<String,Object> mmNum = new HashMap<>();
			int nvGradeCheckNum = schoolReportDao.sexGradeCheckNum(activityId,school,studentDO.getGrade(),2);
			int nvGradeCheckjinshi = schoolReportDao.sexGradeCheckjinshi(activityId,school,studentDO.getGrade(),2);
			mmNum.put("checkNvNum", nvGradeCheckNum);
			mmNum.put("myopiaNvRate", nvGradeCheckNum==0?0:df.format(((double)nvGradeCheckjinshi/(double)nvGradeCheckNum)*100));
			mmNum.put("nj", studentDO.getGrade());
			mmNum.put("myopiaNvNum", nvGradeCheckjinshi);
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
			mapClassyi.put("activityId", activityId);
			mapClassyi.put("grade", studentDO.getGrade());
			List<StudentDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
			for(int i= 1 ; i<=classCountyi.size();i++){
				Map<String,Object> classyi = new HashMap<String,Object>();
				classyi.put("class", i);
				int jcyi = schoolReportDao.schoolGradeClassCheckNum(activityId,school,studentDO.getGrade(),String.valueOf(i));
				int jsyi = schoolReportDao.schoolGradeClassjinshi(activityId,school,studentDO.getGrade(),String.valueOf(i));
				classyi.put("classNum", jcyi);
				classyi.put("classMyopiaRate", jcyi==0?0:df.format(((double)jsyi/(double)jcyi*100)));
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
			Map<String,String> gradey = new HashMap<String,String>();
			int qingduyi = 0;
			int zhongduyi = 0;
			int zzhongduyi = 0;
			int buliangyi = 0;
			String qingduyiR = "0";
			String zhongduyiR= "0";
			String zzhongduyiR= "0";
			String buliangyiR= "0";
			int gradeCheckNum = schoolReportDao.gradeCheckNum(activityId,school,studentDO.getGrade());
			qingduyi = schoolReportDao.qingdubuliang(activityId,school,studentDO.getGrade());
			zhongduyi = schoolReportDao.zhongdubuliang(activityId,school,studentDO.getGrade());
			zzhongduyi = schoolReportDao.gaodubuliang(activityId,school,studentDO.getGrade());
			buliangyi = schoolReportDao.buliangTotal(activityId,school,studentDO.getGrade());
			qingduyiR = gradeCheckNum==0?"0":df.format(((double)qingduyi/(double)gradeCheckNum)*100);
			zhongduyiR = gradeCheckNum==0?"0":df.format(((double)zhongduyi/(double)gradeCheckNum)*100);
			zzhongduyiR = gradeCheckNum==0?"0":df.format(((double)zzhongduyi/(double)gradeCheckNum)*100);
			buliangyiR =  gradeCheckNum==0?"0":df.format(((double)buliangyi/(double)gradeCheckNum)*100);
			
			gradey.put("grade", studentDO.getGrade());
			gradey.put("xuebu", studentDO.getXueBu());
			gradey.put("gradeCheckNum", String.valueOf(gradeCheckNum));
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
		int zhongNT= 0;
		int zzhongNT= 0;
		int bulingNT=0;
		for (Map<String, String> map : listT) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("gradeCheckNum")){
					checkT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("numQDSLBL")){
					qingNT += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numZDSLBL")){
					zhongNT += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numZZDSLBL")){
					zzhongNT += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numSLBL")){
					bulingNT += Integer.parseInt(m.getValue());
				}
				
			}
		}
		params.put("xuebu", gradebl.get(0).getXueBu());
		params.put("gradeCheckTotal", checkT);
		params.put("numQDSLBLTotal", qingNT);
		params.put("rateQDSLBLTotal", df.format(((double)qingNT/(double)checkT)*100));
		params.put("numZDSLBLTotal", zhongNT);
		params.put("rateZDSLBLTotal", df.format(((double)zhongNT/(double)checkT)*100));
		params.put("numZZDSLBLTotal", zzhongNT);
		params.put("rateZZDSLBLTotal", df.format(((double)zzhongNT/(double)checkT)*100));
		params.put("numSLBLTotal", bulingNT);
		params.put("rateSLBLTotal", df.format(((double)bulingNT/(double)checkT)*100));
		
		//近视
		List<Map<String,String>> jiajin = new ArrayList<Map<String,String>>();
		
		List<StudentDO> gradejs = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradejs) {
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
			int gradeCheckNum = schoolReportDao.gradeCheckNum(activityId,school,studentDO.getGrade());
			linchuangy = schoolReportDao.jinshiqianqi(activityId,school,studentDO.getGrade());
			jiajinshiy = schoolReportDao.jiaxingjinshi(activityId,school,studentDO.getGrade());
			diy = schoolReportDao.didujinshi(activityId,school,studentDO.getGrade());
			zhongy = schoolReportDao.zhongdujinshi(activityId,school,studentDO.getGrade());
			gaoy = schoolReportDao.gaodujinshi(activityId,school,studentDO.getGrade());
			linchuangr = gradeCheckNum==0?"0":df.format(((double)linchuangy/(double)gradeCheckNum)*100);
			jiajinshir = gradeCheckNum==0?"0":df.format(((double)jiajinshiy/(double)gradeCheckNum)*100);
			dir = gradeCheckNum==0?"0":df.format(((double)diy/(double)gradeCheckNum)*100);
			zhongr = gradeCheckNum==0?"0":df.format(((double)zhongy/(double)gradeCheckNum)*100);
			gaor = gradeCheckNum==0?"0":df.format(((double)gaoy/(double)gradeCheckNum)*100);
			Integer jinzongy = diy+zhongy+gaoy;
			String jinzongr = gradeCheckNum==0?"0":df.format(((double)jinzongy/(double)gradeCheckNum)*100);
			jia1.put("grade", studentDO.getGrade());
			jia1.put("xuebu", studentDO.getXueBu());
			jia1.put("gradeCheckNum", String.valueOf(gradeCheckNum));
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
			jia1.put("rateJS", jinzongr);
			
			jiajin.add(jia1);
		}
		params.put("jiajin", jiajin);
		
		int jiaJS1 =0;
		int jiaJS3= 0;
		int jiaJS5= 0;
		int jiaJS7= 0;
		int jiaJS9= 0;
		int jiaJS22= 0;
		for (Map<String, String> map2 : jiajin) {
			for(Map.Entry<String, String> m : map2.entrySet()){
				if(m.getKey().equals("numJSQQ")){
					jiaJS1 += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numJXJS")){
					jiaJS3 += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numDDJS")){
					jiaJS5 += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numZDJS")){
					jiaJS7 += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numGDJS")){
					jiaJS9 += Integer.parseInt(m.getValue());
				}
				
				if(m.getKey().equals("numJS")){
					jiaJS22 += Integer.parseInt(m.getValue());
				}
				
			}
		}
		
		params.put("numJSQQTotal", jiaJS1);
		params.put("rateJSQQTotal", df.format(((double)jiaJS1/(double)checkT)*100));
		params.put("numJXJSTotal", jiaJS3);
		params.put("rateJXJSTotal", df.format(((double)jiaJS3/(double)checkT)*100));
		params.put("numDDJSTotal", jiaJS5);
		params.put("rateDDJSTotal", df.format(((double)jiaJS5/(double)checkT)*100));
		params.put("numZDJSTotal", jiaJS7);
		params.put("rateZDJSTotal", df.format(((double)jiaJS7/(double)checkT)*100));
		params.put("numGDJSTotal", jiaJS9);
		params.put("rateGDJSTotal", df.format(((double)jiaJS9/(double)checkT)*100));
		params.put("numJSTotal", jiaJS22);
		params.put("rateJSTotal", df.format(((double)jiaJS22/(double)checkT)*100));
		
		//附件
		List<Map<String,Object>> xuesheng = new ArrayList<Map<String,Object>>();
				
		List<StudentDO> gradefj = studentDao.querySchoolGrade(school);
		for (StudentDO studentDO : gradefj) {
			Map<String,Object> mapClassyi = new HashMap<String,Object>();
			mapClassyi.put("school", school);
			mapClassyi.put("activityId", activityId);
			mapClassyi.put("grade", studentDO.getGrade());
			List<StudentDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
			for(StudentDO stu : classCountyi){
				Map<String,Object> mapbb = new HashMap<String,Object>();
				List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
				List<StudentDO> grade = studentDao.queryStudentGrade(school,studentDO.getGrade(),activityId,stu.getStudentClass());
				if(grade.size()>0){
				for (StudentDO studentDO2 : grade) {
					Map<String,Object> mapPP = new HashMap<String,Object>();
					ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
					ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
					ResultCornealDO resultCornealDO = new ResultCornealDO();
					ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
					ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
					List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getId());
					List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getIdentityCard(),activityId);
					List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getIdentityCard(),activityId);
					List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getId());
					List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getId());
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
				mapbb.put("class", stu.getStudentClass());
				mapbb.put("chaDate", sdf.format(activityListDao.get(activityId).getAddTime()));
				mapbb.put("jieguo", bb);
				xuesheng.add(mapbb);
			}
			}
		}
				
		params.put("xuesheng", xuesheng);
				
		
		return params;
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
	
	
	public void createDoc(HttpServletResponse response,Map<String, Object> dataMap, String fileName, String template) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(SchoolReportServiceImpl.class, "/");
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
           
			Cookie status = new Cookie("status","success");
		    status.setMaxAge(600);
		    response.addCookie(status);
			
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

	@Override
	public List<StudentDO> schoolActivity(Integer activityId) {
		return schoolReportDao.schoolActivity(activityId);
	}

	@Override
	public void schoolGradeRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String school = request.getParameter("school");
		String grade = request.getParameter("grade");
		Map<String,Object> mapClassyi = new HashMap<String,Object>();
		mapClassyi.put("school", school);
		mapClassyi.put("activityId", activityId);
		mapClassyi.put("grade", grade);
		List<StudentDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
		List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
		for (StudentDO studentDO : classCountyi) {
			List<StudentDO> stu = studentDao.queryStudentGrade(school,grade,activityId,studentDO.getStudentClass());
			if(stu.size()>0){
				for (StudentDO studentDO2 : stu) {
					Map<String,Object> mapPP = new HashMap<String,Object>();
					ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
					ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
					ResultCornealDO resultCornealDO = new ResultCornealDO();
					ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
					ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
					List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getId());
					List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getIdentityCard(),activityId);
					List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getIdentityCard(),activityId);
					List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getId());
					List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getId());
					mapPP.put("学校", school);
					mapPP.put("年级", grade);
					mapPP.put("班级", studentDO.getStudentClass());
					mapPP.put("姓名", studentDO2.getStudentName());
					if(studentDO2.getStudentSex()==1){
						mapPP.put("性别", "男");
					}else{
						mapPP.put("性别", "女");
					}
					mapPP.put("活动时间", sdf.format(activityListDao.get(activityId).getAddTime()));
					if(lifeShili.size()>0){
						resultEyesightDO = lifeShili.get(0);
						mapPP.put("右眼裸眼远视力", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
						mapPP.put("左眼裸眼远视力", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
						mapPP.put("右眼生活远视力", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
						mapPP.put("左眼生活远视力", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
					}else{
						mapPP.put("右眼裸眼远视力", "");
						mapPP.put("左眼裸眼远视力", "");
						mapPP.put("右眼生活远视力", "");
						mapPP.put("左眼生活远视力", "");
					}
					if(L.size()>0){
						resultDiopterDO = L.get(0);
						mapPP.put("左眼球镜", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
						mapPP.put("左眼柱镜", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
						mapPP.put("左眼轴位", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("左眼球镜", "");
						mapPP.put("左眼柱镜", "");
						mapPP.put("左眼轴位", "");
					}
					if(R.size()>0){
						resultDiopterDO = R.get(0);
						mapPP.put("右眼球镜", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
						mapPP.put("右眼柱镜", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
						mapPP.put("右眼轴位", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("右眼球镜", "");
						mapPP.put("右眼柱镜", "");
						mapPP.put("右眼轴位", "");
					}
					if(LR1.size()>0){
						resultCornealDO = LR1.get(0);
						mapPP.put("R1-左眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("R1-左眼", "");
					}
					if(LR2.size()>0){
						resultCornealDO = LR2.get(0);
						mapPP.put("R2-左眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("R2-左眼","");
					}
					if(RR1.size()>0){
						resultCornealDO = RR1.get(0);
						mapPP.put("R1-右眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("R1-右眼", "");
					}
					if(RR2.size()>0){
						resultCornealDO = RR2.get(0);
						mapPP.put("R2-右眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
					}else{
						mapPP.put("R2-右眼", "");
					}
					if(eyeaxis.size()>0){
						resultEyeaxisDO = eyeaxis.get(0);
						mapPP.put("眼轴长度右眼",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
						mapPP.put("眼轴长度左眼",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
					}else{
						mapPP.put("眼轴长度右眼","");
						mapPP.put("眼轴长度左眼","");
					}
					if(eyepressure.size()>0){
						resultEyepressureDO = eyepressure.get(0);
						mapPP.put("眼压右眼", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
						mapPP.put("眼压左眼", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
					}else{
						mapPP.put("眼压右眼", "");
						mapPP.put("眼压左眼", "");
					}
					bb.add(mapPP);
					
				}
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("年级报告", "暂无数据！！！");
				bb.add(map);
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "年级报告"+format.format(new Date().getTime())+".xls";
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1"));
		Cookie status = new Cookie("status","success");
	    status.setMaxAge(600);
	    response.addCookie(status);
		
		OutputStream out = response.getOutputStream();
		
		try {
			ExcelExportUtil4DIY.exportToFile(bb,out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}

	@Override
	public void schoolClassRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String school = request.getParameter("school");
		String grade = request.getParameter("grade");
		String stuclass = request.getParameter("stuclass");
		List<StudentDO> stu = studentDao.queryStudentGrade(school,grade,activityId,stuclass);
		List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
		if(stu.size()>0){
			for (StudentDO studentDO2 : stu) {
				Map<String,Object> mapPP = new HashMap<String,Object>();
				ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
				ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
				ResultCornealDO resultCornealDO = new ResultCornealDO();
				ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
				ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
				List<ResultEyesightDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getId());
				List<ResultDiopterDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getIdentityCard(),activityId);
				List<ResultDiopterDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getIdentityCard(),activityId);
				List<ResultCornealDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R1",activityId);
				List<ResultCornealDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R2",activityId);
				List<ResultCornealDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R1",activityId);
				List<ResultCornealDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R2",activityId);
				List<ResultEyeaxisDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getId());
				List<ResultEyepressureDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getId());
				mapPP.put("学校", school);
				mapPP.put("年级", grade);
				mapPP.put("班级", stuclass);
				mapPP.put("姓名", studentDO2.getStudentName());
				if(studentDO2.getStudentSex()==1){
					mapPP.put("性别", "男");
				}else{
					mapPP.put("性别", "女");
				}
				mapPP.put("活动时间", sdf.format(activityListDao.get(activityId).getAddTime()));
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					mapPP.put("右眼裸眼远视力", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					mapPP.put("左眼裸眼远视力", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					mapPP.put("右眼生活远视力", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					mapPP.put("左眼生活远视力", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}else{
					mapPP.put("右眼裸眼远视力", "");
					mapPP.put("左眼裸眼远视力", "");
					mapPP.put("右眼生活远视力", "");
					mapPP.put("左眼生活远视力", "");
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					mapPP.put("左眼球镜", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("左眼柱镜", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("左眼轴位", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}else{
					mapPP.put("左眼球镜", "");
					mapPP.put("左眼柱镜", "");
					mapPP.put("左眼轴位", "");
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					mapPP.put("右眼球镜", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("右眼柱镜", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("右眼轴位", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}else{
					mapPP.put("右眼球镜", "");
					mapPP.put("右眼柱镜", "");
					mapPP.put("右眼轴位", "");
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					mapPP.put("R1-左眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}else{
					mapPP.put("R1-左眼", "");
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					mapPP.put("R2-左眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}else{
					mapPP.put("R2-左眼","");
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					mapPP.put("R1-右眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}else{
					mapPP.put("R1-右眼", "");
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					mapPP.put("R2-右眼", resultCornealDO.getCornealMm()==null?"":resultCornealDO.getCornealMm());
				}else{
					mapPP.put("R2-右眼", "");
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					mapPP.put("眼轴长度右眼",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					mapPP.put("眼轴长度左眼",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}else{
					mapPP.put("眼轴长度右眼","");
					mapPP.put("眼轴长度左眼","");
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					mapPP.put("眼压右眼", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					mapPP.put("眼压左眼", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}else{
					mapPP.put("眼压右眼", "");
					mapPP.put("眼压左眼", "");
				}
				bb.add(mapPP);
				
			}
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("班级报告", "暂无数据！！！");
			bb.add(map);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "班级报告"+format.format(new Date().getTime())+".xls";
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1"));
		Cookie status = new Cookie("status","success");
	    status.setMaxAge(600);
	    response.addCookie(status);
		
		OutputStream out = response.getOutputStream();
		
		try {
			ExcelExportUtil4DIY.exportToFile(bb,out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	
}
