package com.shaicha.informationNEW.service.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaicha.informationNEW.dao.*;
import com.shaicha.informationNEW.domain.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.common.utils.ExcelExportUtil4DIY;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.common.config.BootdoConfig;
import com.shaicha.information.dao.LinShiUrlDao;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.informationNEW.service.SchoolReportNewService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SchoolReportNewServiceImpl implements SchoolReportNewService{
	@Autowired
	private StudentNewDao studentNewDao;
	@Autowired
	private SchoolReportNewDao schoolReportDao;
	@Autowired
	private StudentNewDao studentDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private LinShiUrlDao linShiUrlDao;
	@Autowired
	private ResultDiopterNewDao resultDiopterDao;
	@Autowired
	private ResultEyesightNewDao resultEyesightDao;
	@Autowired
	private ResultCornealNewDao resultCornealDao;
	@Autowired
	private ResultEyeaxisNewDao resultEyeaxisDao;
	@Autowired
	private ResultEyepressureNewDao resultEyepressureDao;
	@Autowired
	private ActivityListNewDao activityListDao;
	@Autowired
	private ChanpinRecordListDao chanpinRecordListDao;
	@Autowired
	private ChanpinRecordDetailsDao chanpinRecordDetailsDao;
	@Autowired
	private ChanpinTitleChooseDao chanpinTitleChooseDao;
	@Autowired
	private SchoolNewDao schoolNewDao;
	@Autowired
	private ResultQuestionDao resultQuestionDao;



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
		da.add(qianrenshu==0?"":Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
		da.add(qurenshu==0?"":Double.parseDouble(df.format((double)qujinshi/(double)qurenshu*100)));
		da.add(xianrenshu==0?"":Double.parseDouble(df.format((double)xianjinshi/(double)xianrenshu*100)));
		List<Object> ye = new ArrayList<>();
		ye.add(sdf.format(qian));
		ye.add(sdf.format(qu));
		ye.add(sdf.format(xian));
		
		map.put("overYearMyopia", da);
		map.put("year", ye);
		return map;
	}


    //近视不良对比
	@Override
	public Map<String, Object> shangcibulingjinshi(String school, Integer activityId) {
		DecimalFormat df = new DecimalFormat("0.0");
		Map<String, Object> map = new HashMap<String, Object>();

		int zongNum = 0;
		int jinshinow = 0;
		int buliangnow = 0;			
		int zongNumshang = 0;
		int jinshilast = 0;
		int bulianglast = 0;
		double blshujus = 0;
		double jsshujus = 0;			
		double blshujuz = 0;
		double jsshujuz = 0;
		Date addTime = activityListDao.get(activityId).getAddTime();
		List<ActivityListNewDO> lastActivity = schoolReportDao.getLastActivity(ShiroUtils.getUserId().intValue(),addTime);
		if(lastActivity.size()<=0 && school.equals("济南市外海实验学校")){
			 zongNum = schoolReportDao.zongNum(activityId,school);
			 jinshinow = schoolReportDao.schoolCheckJinshi(activityId,school);
			 buliangnow = schoolReportDao.schoolCheckBuliang(activityId,school);			
			 zongNumshang = schoolReportDao.waihaijiancharenshu();
			 jinshilast = schoolReportDao.waihaijinshirenshu();
			 bulianglast  = schoolReportDao.waihaibuliangrenshu();
			 //myt1.add(zongNumshang==0?0:Double.parseDouble(df.format((double)bulianglast/(double)zongNumshang*100)));
			 //myt1.add(zongNumshang==0?0:Double.parseDouble(df.format((double)jinshilast/(double)zongNumshang*100)));			
			 //myt2.add(zongNum==0?0:Double.parseDouble(df.format((double)buliangnow/(double)zongNum*100)));
			 //myt2.add(zongNum==0?0:Double.parseDouble(df.format((double)jinshinow/(double)zongNum*100)));
			 blshujus = zongNumshang==0?0:Double.parseDouble(df.format((double)bulianglast/(double)zongNumshang*100));
			 jsshujus = zongNumshang==0?0:Double.parseDouble(df.format((double)jinshilast/(double)zongNumshang*100));			
			 blshujuz = zongNum==0?0:Double.parseDouble(df.format((double)buliangnow/(double)zongNum*100));
			 jsshujuz = zongNum==0?0:Double.parseDouble(df.format((double)jinshinow/(double)zongNum*100));
		}else{
			 zongNum = schoolReportDao.zongNum(activityId,school);
			 jinshinow = schoolReportDao.schoolCheckJinshi(activityId,school);
			 buliangnow  = schoolReportDao.schoolCheckBuliang(activityId,school);
			 if(lastActivity.size()<=0){
				 jinshilast =0;
				 bulianglast =0;
			 }else{
				 jinshilast = schoolReportDao.schoolCheckJinshi(lastActivity.get(0).getId(),school);
				 bulianglast  = schoolReportDao.schoolCheckBuliang(lastActivity.get(0).getId(),school);
			 }
			 
			 
			 blshujus = zongNum==0?0:Double.parseDouble(df.format((double)bulianglast/(double)zongNum*100));
			 jsshujus = zongNum==0?0:Double.parseDouble(df.format((double)jinshilast/(double)zongNum*100));			
			 blshujuz = zongNum==0?0:Double.parseDouble(df.format((double)buliangnow/(double)zongNum*100));
			 jsshujuz = zongNum==0?0:Double.parseDouble(df.format((double)jinshinow/(double)zongNum*100));
			
		}
		map.put("blshujus", blshujus);
		map.put("blshujuz", blshujuz);
		map.put("jsshujus", blshujus);
		map.put("jsshujuz", jsshujuz);
		return map;
	}
	
	
	
	//各年级近视
	@Override
	public Map<String, List<Object>> gradeMyopia(String school, Integer activityId) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
//		List<Object> da = new ArrayList<Object>();
		List<Object> ye = new ArrayList<>();
//		DecimalFormat df = new DecimalFormat("0.0");
		List<StudentNewDO> gegradejs = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gegradejs) {
//			int gradeCheck = schoolReportDao.activityGradeByCheckNum(activityId, school, studentDO.getGrade());
//			int gradeCheckjinshi = schoolReportDao.gradeCheckjinshi(activityId, school, studentDO.getGrade());
//			da.add(gradeCheck==0?0:Double.parseDouble(df.format((double)gradeCheckjinshi/(double)gradeCheck*100)));
			ye.add(studentDO.getGrade());
		}
//		map.put("gradeMyopia", da);
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
		List<StudentNewDO> gegradejs = studentDao.querySchoolGradeLiNian(school);
		for (StudentNewDO studentDO : gegradejs) {
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

    @Override
    public Map<String, List<Object>> overYearGradeBuliang(String school) {
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
        List<StudentNewDO> gegradejs = studentDao.querySchoolGradeLiNian(school);
        for (StudentNewDO studentDO : gegradejs) {
            int qianrenshu = schoolReportDao.liniangradeCheck(school,studentDO.getGrade(),sdf.format(qian));
            int qianjinshi = schoolReportDao.liniangradeCheckbuliang(school,studentDO.getGrade(),sdf.format(qian));
            myt1.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
            int qurenshu = schoolReportDao.liniangradeCheck(school,studentDO.getGrade(),sdf.format(qu));
            int qujinshi = schoolReportDao.liniangradeCheckbuliang(school,studentDO.getGrade(),sdf.format(qu));
            myt2.add(qurenshu==0?0:Double.parseDouble(df.format((double)qujinshi/(double)qurenshu*100)));
            int xianrenshu = schoolReportDao.liniangradeCheck(school,studentDO.getGrade(),sdf.format(xian));
            int xianjinshi = schoolReportDao.liniangradeCheckbuliang(school,studentDO.getGrade(),sdf.format(xian));
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
		int nanCheckNum = schoolReportDao.activitySexByCheckNum(activityId, school, 1);//男
		int nanCheckjinshi = schoolReportDao.sexCheckjinshi(activityId, school, 1);
		myt.add(nanCheckNum==0?0:Double.parseDouble(df.format((double)nanCheckjinshi/(double)nanCheckNum*100)));
		int nvCheckNum = schoolReportDao.activitySexByCheckNum(activityId, school, 2);
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
		//myt.add(32.9);
		//myt.add(16.7);
		myt.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
		int qurenshu = schoolReportDao.linianSexCheckNum(school,1,sdf.format(qu));
		int qujinshi = schoolReportDao.linianSexCheckjinshi(school,1,sdf.format(qu));
		//myt.add(32.9);
		//myt.add(16.3);
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
		//myt.add(41.4);
		//myt.add(29.6);
		myt.add(qianrenshu==0?0:Double.parseDouble(df.format((double)qianjinshi/(double)qianrenshu*100)));
		int qurenshu = schoolReportDao.linianSexCheckNum(school,2,sdf.format(qu));
		int qujinshi = schoolReportDao.linianSexCheckjinshi(school,2,sdf.format(qu));
		//myt.add(38.6);
		//myt.add(14.3);
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
		List<StudentNewDO> gegradejs = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gegradejs) {
			int nanGradeCheckNum = schoolReportDao.activityGradeSexByCheckNum(activityId,school,studentDO.getGrade(),1);//男
			int nanGradeCheckjinshi = schoolReportDao.sexGradeCheckjinshi(activityId,school,studentDO.getGrade(),1);
			myt1.add(nanGradeCheckNum==0?0:Double.parseDouble(df.format((double)nanGradeCheckjinshi/(double)nanGradeCheckNum*100)));
		
			int nvGradeCheckNum = schoolReportDao.activityGradeSexByCheckNum(activityId,school,studentDO.getGrade(),2);
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
			createDoc(response,params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "学校报告模板.ftl");
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
		int checkNum = schoolReportDao.activityByCheckNum(activityId,school);
		params.put("schoolNum", zongNum);
		params.put("checkNum", checkNum);
		params.put("checkRate", zongNum==0?0:df.format((double)checkNum/((double)zongNum)*100));
		List<Map<String,Object>> listg = new ArrayList<Map<String,Object>>();
		List<StudentNewDO> gradeshu = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gradeshu) {
			Map<String,Object> gNum = new HashMap<>();
			int gradeNum = schoolReportDao.gradeCheckNum(activityId,school,studentDO.getGrade());
			int gradeByCheckNum = schoolReportDao.activityGradeByCheckNum(activityId,school,studentDO.getGrade());
			gNum.put("nianji", studentDO.getGrade());
			gNum.put("gradeNum", gradeNum);
			gNum.put("gradeCheckNum", gradeByCheckNum);
			gNum.put("gradeCheckRate", gradeNum==0?0:df.format((double)gradeByCheckNum/((double)gradeNum)*100));
			listg.add(gNum);
		}
		params.put("nianjice", listg);
		
		//男女近视
		int nanCheckNum = schoolReportDao.activitySexByCheckNum(activityId,school,1);
		int nanjinshi = schoolReportDao.schoolSexjinshi(activityId,school,1);
		params.put("checkNanNum", nanCheckNum);
		params.put("myopiaNanRate", nanCheckNum==0?0:df.format(((double)nanjinshi/(double)nanCheckNum)*100));
		params.put("myopiaNanNum", nanjinshi);	
		int nvCheckNum = schoolReportDao.activitySexByCheckNum(activityId,school,2);
		int nvjinshi = schoolReportDao.schoolSexjinshi(activityId,school,2);
		params.put("checkNvNum", nvCheckNum);
		params.put("myopiaNvRate", nvCheckNum==0?0:df.format(((double)nvjinshi/(double)nvCheckNum)*100));
		params.put("myopiaNvNum", nvjinshi);
		
		//男近视
		List<Map<String,Object>> listnn = new ArrayList<Map<String,Object>>();
		List<StudentNewDO> gradenn = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gradenn) {
			Map<String,Object> nnNum = new HashMap<>();
			int nanGradeCheckNum = schoolReportDao.activityGradeSexByCheckNum(activityId,school,studentDO.getGrade(),1);
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
		List<StudentNewDO> grademm = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : grademm) {
			Map<String,Object> mmNum = new HashMap<>();
			int nvGradeCheckNum = schoolReportDao.activityGradeSexByCheckNum(activityId,school,studentDO.getGrade(),2);
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
		
		List<StudentNewDO> gradebj = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gradebj) {
			Map<String,Object> yi = new HashMap<String,Object>();
			List<Map<String,Object>> aa = new ArrayList<Map<String,Object>>();
			Map<String,Object> mapClassyi = new HashMap<String,Object>();
			mapClassyi.put("school", school);
			mapClassyi.put("activityId", activityId);
			mapClassyi.put("grade", studentDO.getGrade());
			List<StudentNewDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
			for(StudentNewDO stu : classCountyi){
				Map<String,Object> classyi = new HashMap<String,Object>();
				classyi.put("class", stu.getStudentClass());
				int jcyi = schoolReportDao.activityGradeClassByCheckNum(activityId,school,studentDO.getGrade(),stu.getStudentClass());
				int jsyi = schoolReportDao.schoolGradeClassjinshi(activityId,school,studentDO.getGrade(),stu.getStudentClass());
				int blyi = schoolReportDao.schoolGradeClassbuliang(activityId,school,studentDO.getGrade(),stu.getStudentClass());
				classyi.put("classNum", jcyi);
				classyi.put("classMyopiaRate", jcyi==0?0:df.format(((double)jsyi/(double)jcyi*100)));
				classyi.put("classMyopiaNum", jsyi);
				classyi.put("classbuliangRate", jcyi==0?0:df.format(((double)blyi/(double)jcyi*100)));
				classyi.put("classbuliang", blyi);
				aa.add(classyi);
			}
			yi.put("grade", studentDO.getGrade());
			yi.put("classyi", aa);
			listbj.add(yi);
		}
		params.put("firstClass", listbj);
		
		//不良
		List<Map<String,String>> listT = new ArrayList<Map<String,String>>();
		
		List<StudentNewDO> gradebl = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gradebl) {
			Map<String,String> gradey = new HashMap<String,String>();
			int qingduyi = 0;
			int zhongduyi = 0;
			int zzhongduyi = 0;
			int buliangyi = 0;
			String qingduyiR = "0";
			String zhongduyiR= "0";
			String zzhongduyiR= "0";
			String buliangyiR= "0";
			int gradeCheckNum = schoolReportDao.activityGradeByCheckNum(activityId,school,studentDO.getGrade());
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
		
		List<StudentNewDO> gradejs = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gradejs) {
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
			int gradeCheckNum = schoolReportDao.activityGradeByCheckNum(activityId,school,studentDO.getGrade());
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
		DecimalFormat df2 = new DecimalFormat("0.00");
		List<StudentNewDO> gradefj = studentDao.querySchoolGrade(school,activityId);
		for (StudentNewDO studentDO : gradefj) {
			Map<String,Object> mapClassyi = new HashMap<String,Object>();
			mapClassyi.put("school", school);
			mapClassyi.put("activityId", activityId);
			mapClassyi.put("grade", studentDO.getGrade());
			List<StudentNewDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
			for(StudentNewDO stu : classCountyi){
				Map<String,Object> mapbb = new HashMap<String,Object>();
				List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
				List<StudentNewDO> grade = studentDao.queryStudentGrade(school,studentDO.getGrade(),activityId,stu.getStudentClass());
				if(grade.size()>0){
				for (StudentNewDO studentDO2 : grade) {
					Map<String,Object> mapPP = new HashMap<String,Object>();
					ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
					ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
					ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
					ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
					ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
					List<ResultEyesightNewDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getId());
					List<ResultDiopterNewDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getId(),activityId);
					List<ResultDiopterNewDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getId(),activityId);
					List<ResultCornealNewDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealNewDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultCornealNewDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealNewDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultEyeaxisNewDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getId());
					List<ResultEyepressureNewDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getId());
					mapPP.put("studentName", studentDO2.getStudentName());
					mapPP.put("studentSex", studentDO2.getStudentSex()==null? 1:studentDO2.getStudentSex());
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
						String diopterSL="";
						if(resultDiopterDO.getDiopterS()!=null){
							if(resultDiopterDO.getDiopterS()==0)
								diopterSL = "0";
							if(resultDiopterDO.getDiopterS()!=0)
								diopterSL = df2.format(resultDiopterDO.getDiopterS());
							if(resultDiopterDO.getDiopterS()>0){
								diopterSL="+"+diopterSL;
							}
						}
						String diopterCL = "";
						if(resultDiopterDO.getDiopterC()!=null){
							if(resultDiopterDO.getDiopterC()==0){
								diopterCL = "0";
							}
							if(resultDiopterDO.getDiopterC()!=0){
								diopterCL = df2.format(resultDiopterDO.getDiopterC());
							}
						}
						mapPP.put("diopterSOs", diopterSL);
						mapPP.put("diopterCOs", diopterCL);
						mapPP.put("diopterAOs", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("diopterSOs", "");
						mapPP.put("diopterCOs", "");
						mapPP.put("diopterAOs", "");
					}
					if(R.size()>0){
						resultDiopterDO = R.get(0);
						String diopterSR="";
						if(resultDiopterDO.getDiopterS()!=null){
							if(resultDiopterDO.getDiopterS()==0)
								diopterSR = "0";
							if(resultDiopterDO.getDiopterS()!=0)
								diopterSR = df2.format(resultDiopterDO.getDiopterS());
							if(resultDiopterDO.getDiopterS()>0){
								diopterSR="+"+diopterSR;
							}
						}
						String diopterCR = "";
						if(resultDiopterDO.getDiopterC()!=null){
							if(resultDiopterDO.getDiopterC()==0){
								diopterCR = "0";
							}
							if(resultDiopterDO.getDiopterC()!=0){
								diopterCR = df2.format(resultDiopterDO.getDiopterC());
							}
						}
						mapPP.put("diopterSOd", diopterSR);
						mapPP.put("diopterCOd", diopterCR);
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
        configuration.setClassForTemplateLoading(SchoolReportNewServiceImpl.class, "/");
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
	public List<StudentNewDO> schoolActivity(Integer activityId,Long sysId) {
		return schoolReportDao.schoolActivity(activityId,sysId);
	}

	
		//年级报告
		@Override
		public void schoolGradeRep(HttpServletRequest request, HttpServletResponse response) {

			try {
				Map<String, Object> params = gradeRep(request, response);
                String school = request.getParameter("school");
                String grade = request.getParameter("grade");
				createExe(response,params, school+grade, "给年级报告检测.ftl");
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
	
	
	
	public Map<String,Object> gradeRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> mapP = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String school = request.getParameter("school");
		String grade = request.getParameter("grade");
		Map<String,Object> mapClassyi = new HashMap<String,Object>();
		mapClassyi.put("school", school);
		mapClassyi.put("activityId", activityId);
		mapClassyi.put("grade", grade);
		List<StudentNewDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
		List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
		for (StudentNewDO studentDO : classCountyi) {
			List<StudentNewDO> stu = studentDao.queryStudentGrade(school,grade,activityId,studentDO.getStudentClass());
			if(stu.size()>0){
				for (StudentNewDO studentDO2 : stu) {
					Map<String,Object> mapPP = new HashMap<String,Object>();
					ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
					ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
					ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
					ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
					ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
					List<ResultEyesightNewDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getId());
					List<ResultDiopterNewDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getId(),activityId);
					List<ResultDiopterNewDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getId(),activityId);
					List<ResultCornealNewDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealNewDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultCornealNewDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R1",activityId);
					List<ResultCornealNewDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R2",activityId);
					List<ResultEyeaxisNewDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getId());
					List<ResultEyepressureNewDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getId());
					mapPP.put("school", school);
					mapPP.put("grade", grade);
					mapPP.put("stuClass", studentDO.getStudentClass());
					mapPP.put("stuName", studentDO2.getStudentName());
					mapPP.put("shenfen", studentDO2.getIdentityCard());
					mapPP.put("shouji", studentDO2.getPhone()==null?"":studentDO2.getPhone());
					mapPP.put("shengri", studentDO2.getBirthday()==null?"":sdf.format(studentDO2.getBirthday()));
					if(studentDO2.getStudentSex()==1){
						mapPP.put("stuSex", "男");
					}else{
						mapPP.put("stuSex", "女");
					}
					mapPP.put("activitytime", sdf.format(activityListDao.get(activityId).getAddTime()));
					if(lifeShili.size()>0){
						resultEyesightDO = lifeShili.get(0);
						mapPP.put("youluoyan", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
						mapPP.put("zuoluoyan", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
						mapPP.put("youshenghuo", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
						mapPP.put("zuoshenghuo", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
					}else{
						mapPP.put("youluoyan", "");
						mapPP.put("zuoluoyan", "");
						mapPP.put("youshenghuo", "");
						mapPP.put("zuoshenghuo", "");
					}
					if(L.size()>0){
						resultDiopterDO = L.get(0);
						mapPP.put("zuoqiu", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
						mapPP.put("zuozhu", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
						mapPP.put("zuozhou", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("zuoqiu", "");
						mapPP.put("zuozhu", "");
						mapPP.put("zuozhou", "");
					}
					if(R.size()>0){
						resultDiopterDO = R.get(0);
						mapPP.put("youqiu", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
						mapPP.put("youzhu", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
						mapPP.put("youzhou", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
					}else{
						mapPP.put("youqiu", "");
						mapPP.put("youzhu", "");
						mapPP.put("youzhou", "");
					}
					if(LR1.size()>0){
						resultCornealDO = LR1.get(0);
						mapPP.put("R1zuo", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
					}else{
						mapPP.put("R1zuo", "");
					}
					if(LR2.size()>0){
						resultCornealDO = LR2.get(0);
						mapPP.put("R2zuo", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
					}else{
						mapPP.put("R2zuo","");
					}
					if(RR1.size()>0){
						resultCornealDO = RR1.get(0);
						mapPP.put("R1you", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
					}else{
						mapPP.put("R1you", "");
					}
					if(RR2.size()>0){
						resultCornealDO = RR2.get(0);
						mapPP.put("R2you", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
					}else{
						mapPP.put("R2you", "");
					}
					if(eyeaxis.size()>0){
						resultEyeaxisDO = eyeaxis.get(0);
						mapPP.put("yanzhouyou",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
						mapPP.put("yanzhouzuo",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
					}else{
						mapPP.put("yanzhouyou","");
						mapPP.put("yanzhouzuo","");
					}
					if(eyepressure.size()>0){
						resultEyepressureDO = eyepressure.get(0);
						mapPP.put("yanyayou", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
						mapPP.put("yanyazuo", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
					}else{
						mapPP.put("yanyayou", "");
						mapPP.put("yanyazuo", "");
					}
					
					double luo;
					double jiao;
					double deng;
					Map<String,Object> pa = new HashMap<>();
					pa.put("studentId", studentDO2.getId());
					pa.put("activityId", activityId);
					List<ResultEyesightNewDO> re = resultEyesightDao.list(pa);
					if(re.size()>0){

						String nfd = re.get(0).getNakedFarvisionOd();
						if(re.get(0).getNakedFarvisionOd().length()<=0 ||!isDouble(re.get(0).getNakedFarvisionOd())|| re.get(0).getNakedFarvisionOd().equals(""))nfd="0";
						String nfs = re.get(0).getNakedFarvisionOs();
						if(re.get(0).getNakedFarvisionOs().length()<=0 ||!isDouble(re.get(0).getNakedFarvisionOs())|| re.get(0).getNakedFarvisionOs().equals(""))nfs="0";

						if("10/10".equals(nfd)) nfd="5.0";
						if("10/10".equals(nfs)) nfs="5.0";
						if(Double.valueOf(nfd)>Double.valueOf(nfs))luo=Double.valueOf(nfs);
						else luo=Double.valueOf(nfs);
						String cfd = re.get(0).getCorrectionFarvisionOd();
						if(re.get(0).getCorrectionFarvisionOd().length()<=0 ||!isDouble(re.get(0).getCorrectionFarvisionOd())|| re.get(0).getCorrectionFarvisionOd().equals(""))cfd="0";
						String cfs = re.get(0).getCorrectionFarvisionOs();
						if(re.get(0).getCorrectionFarvisionOs().length()<=0 ||!isDouble(re.get(0).getCorrectionFarvisionOs())|| re.get(0).getCorrectionFarvisionOs().equals(""))cfs="0";

						if("10/10".equals(cfd)) cfd="5.0";
						if("10/10".equals(cfs)) cfs="5.0";
						if(Double.valueOf(cfd)>Double.valueOf(cfs))jiao=Double.valueOf(cfs);
						else jiao=Double.valueOf(cfd);
						double dxql = studentDO2.getDengxiaoqiujingl()==null?0:studentDO2.getDengxiaoqiujingl();
						double dxqr = studentDO2.getDengxiaoqiujingr()==null?0:studentDO2.getDengxiaoqiujingr();
						if(dxql>dxqr)deng=dxqr;
						else deng=dxql;
						if(jiao == 0){
							if(luo>= 5 && deng > 0.75){
								mapPP.put("yujing", "视力目前正常，无近视发生");
							}else if(luo>= 5 && deng >= -0.5 && deng <= 0.75){
								mapPP.put("yujing", "视力目前正常,近视临床前期");
							}else if(luo>= 5 && deng < -0.5){
								mapPP.put("yujing", "视力目前正常，属于假性近视");
							}else if(luo< 5 && deng >= -0.5){
								mapPP.put("yujing", "视力异常");
							}else if(luo< 5 && deng < -0.5){
								mapPP.put("yujing", "视力下降，近视");
							}else{
								mapPP.put("yujing", "");
							}
						}else{
							if(luo< 5 && deng >= -0.5 && jiao >=5){
								mapPP.put("yujing", "戴原镜视力正常");
							}else if(luo< 5 && deng < -0.5 && jiao >=5){
								mapPP.put("yujing", "戴原镜视力正常，近视");
							}else if(luo< 5 && deng >= -0.5 && jiao < 5){
								mapPP.put("yujing", "戴原镜视力异常");
							}else if(luo< 5 && deng < -0.5 && jiao < 5){
								mapPP.put("yujing", "戴原镜视力异常，近视增长");
							}else{
								mapPP.put("yujing", "");
							}
						}
					}else{
						mapPP.put("yujing", "");
					}
					
					
					bb.add(mapPP);
					
				}
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("yujing", "暂无数据！！！");
				bb.add(map);
			}
		}
		
		mapP.put("shuju", bb);
		return mapP;
	/*	
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
		}*/
		
	}
	
	
	
	//班级报告
	@Override
	public void schoolClassRep(HttpServletRequest request, HttpServletResponse response) {

		try {
			Map<String, Object> params = classRep(request, response);
			createExe(response,params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "给年级报告检测.ftl");
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

	
	public Map<String,Object> classRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> mapP = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String school = request.getParameter("school");
		String grade = request.getParameter("grade");
		String stuclass = request.getParameter("stuclass");
		List<StudentNewDO> stu = studentDao.queryStudentGrade(school,grade,activityId,stuclass);
		List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
		if(stu.size()>0){
			for (StudentNewDO studentDO2 : stu) {
				Map<String,Object> mapPP = new HashMap<String,Object>();
				ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
				ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
				ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
				ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
				ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
				List<ResultEyesightNewDO> lifeShili = resultEyesightDao.getLifeShili(studentDO2.getId());
				List<ResultDiopterNewDO> L = resultDiopterDao.getYanGuang("L", studentDO2.getId(),activityId);
				List<ResultDiopterNewDO> R = resultDiopterDao.getYanGuang("R", studentDO2.getId(),activityId);
				List<ResultCornealNewDO> LR1 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R1",activityId);
				List<ResultCornealNewDO> LR2 = resultCornealDao.getCornealMm("L", studentDO2.getIdentityCard(),"R2",activityId);
				List<ResultCornealNewDO> RR1 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R1",activityId);
				List<ResultCornealNewDO> RR2 = resultCornealDao.getCornealMm("R", studentDO2.getIdentityCard(),"R2",activityId);
				List<ResultEyeaxisNewDO> eyeaxis = resultEyeaxisDao.getEyeaxis(studentDO2.getId());
				List<ResultEyepressureNewDO> eyepressure = resultEyepressureDao.getEyepressure(studentDO2.getId());
				mapPP.put("school", school);
				mapPP.put("grade", grade);
				mapPP.put("stuClass", stuclass);
				mapPP.put("stuName", studentDO2.getStudentName());
				mapPP.put("shenfen", studentDO2.getIdentityCard());
				mapPP.put("shouji", studentDO2.getPhone()==null?"":studentDO2.getPhone());
				mapPP.put("shengri", studentDO2.getBirthday()==null?"":sdf.format(studentDO2.getBirthday()));
				if(studentDO2.getStudentSex()==1){
					mapPP.put("stuSex", "男");
				}else{
					mapPP.put("stuSex", "女");
				}
				mapPP.put("activitytime", sdf.format(activityListDao.get(activityId).getAddTime()));
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					mapPP.put("youluoyan", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					mapPP.put("zuoluoyan", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					mapPP.put("youshenghuo", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					mapPP.put("zuoshenghuo", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}else{
					mapPP.put("youluoyan", "");
					mapPP.put("zuoluoyan", "");
					mapPP.put("youshenghuo", "");
					mapPP.put("zuoshenghuo", "");
				}
				if(L.size()>0){
					resultDiopterDO = L.get(0);
					mapPP.put("zuoqiu", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("zuozhu", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("zuozhou", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}else{
					mapPP.put("zuoqiu", "");
					mapPP.put("zuozhu", "");
					mapPP.put("zuozhou", "");
				}
				if(R.size()>0){
					resultDiopterDO = R.get(0);
					mapPP.put("youqiu", resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS());
					mapPP.put("youzhu", resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC());
					mapPP.put("youzhou", resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA());
				}else{
					mapPP.put("youqiu", "");
					mapPP.put("youzhu", "");
					mapPP.put("youzhou", "");
				}
				if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					mapPP.put("R1zuo", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R1zuo", "");
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					mapPP.put("R2zuo", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R2zuo","");
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					mapPP.put("R1you", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R1you", "");
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					mapPP.put("R2you", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R2you", "");
				}
				if(eyeaxis.size()>0){
					resultEyeaxisDO = eyeaxis.get(0);
					mapPP.put("yanzhouyou",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd());
					mapPP.put("yanzhouzuo",resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs());
				}else{
					mapPP.put("yanzhouyou","");
					mapPP.put("yanzhouzuo","");
				}
				if(eyepressure.size()>0){
					resultEyepressureDO = eyepressure.get(0);
					mapPP.put("yanyayou", resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd());
					mapPP.put("yanyazuo", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs());
				}else{
					mapPP.put("yanyayou", "");
					mapPP.put("yanyazuo", "");
				}
				
				double luo;
				double jiao;
				double deng;
				Map<String,Object> pa = new HashMap<>();
				pa.put("studentId", studentDO2.getId());
				pa.put("activityId", activityId);
				List<ResultEyesightNewDO> re = resultEyesightDao.list(pa);
				if(re.size()>0){

					String nfd = re.get(0).getNakedFarvisionOd();
					if(re.get(0).getNakedFarvisionOd().length()<=0 ||!isDouble(re.get(0).getNakedFarvisionOd())|| re.get(0).getNakedFarvisionOd().equals(""))nfd="0";
					String nfs = re.get(0).getNakedFarvisionOs();
					if(re.get(0).getNakedFarvisionOs().length()<=0 ||!isDouble(re.get(0).getNakedFarvisionOs())|| re.get(0).getNakedFarvisionOs().equals(""))nfs="0";
					if(Double.valueOf(nfd)>Double.valueOf(nfs))luo=Double.valueOf(nfs);
					else luo=Double.valueOf(nfs);
					String cfd = re.get(0).getCorrectionFarvisionOd();
					if(re.get(0).getCorrectionFarvisionOd().length()<=0 ||!isDouble(re.get(0).getCorrectionFarvisionOd())|| re.get(0).getCorrectionFarvisionOd().equals(""))cfd="0";
					String cfs = re.get(0).getCorrectionFarvisionOs();
					if(re.get(0).getCorrectionFarvisionOs().length()<=0 ||!isDouble(re.get(0).getCorrectionFarvisionOs())|| re.get(0).getCorrectionFarvisionOs().equals(""))cfs="0";
					if(Double.valueOf(cfd)>Double.valueOf(cfs))jiao=Double.valueOf(cfs);
					else jiao=Double.valueOf(cfd);
					double dxql = studentDO2.getDengxiaoqiujingl()==null?0:studentDO2.getDengxiaoqiujingl();
					double dxqr = studentDO2.getDengxiaoqiujingr()==null?0:studentDO2.getDengxiaoqiujingr();
					if(dxql>dxqr)deng=dxqr;
					else deng=dxql;
					if(jiao == 0){
						if(luo>= 5 && deng > 0.75){
							mapPP.put("yujing", "视力目前正常，无近视发生");
						}else if(luo>= 5 && deng >= -0.5 && deng <= 0.75){
							mapPP.put("yujing", "视力目前正常,近视临床前期");
						}else if(luo>= 5 && deng < -0.5){
							mapPP.put("yujing", "视力目前正常，属于假性近视");
						}else if(luo< 5 && deng >= -0.5){
							mapPP.put("yujing", "视力异常");
						}else if(luo< 5 && deng < -0.5){
							mapPP.put("yujing", "视力下降，近视");
						}else{
							mapPP.put("yujing", "");
						}
					}else{
						if(luo< 5 && deng >= -0.5 && jiao >=5){
							mapPP.put("yujing", "戴原镜视力正常");
						}else if(luo< 5 && deng < -0.5 && jiao >=5){
							mapPP.put("yujing", "戴原镜视力正常，近视");
						}else if(luo< 5 && deng >= -0.5 && jiao < 5){
							mapPP.put("yujing", "戴原镜视力异常");
						}else if(luo< 5 && deng < -0.5 && jiao < 5){
							mapPP.put("yujing", "戴原镜视力异常，近视增长");
						}else{
							mapPP.put("yujing", "");
						}
					}
				}else{
					mapPP.put("yujing", "");
				}
				
				bb.add(mapPP);
				
			}
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("班级报告", "暂无数据！！！");
			bb.add(map);
		}
		
		mapP.put("shuju", bb);
		return mapP;
		/*
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
		}*/
	}

	public boolean isDouble( String s ){
		
        boolean matches = s.matches("-?[0-9]+.*[0-9]*");	
        
        return matches;

	}
	
	
	
	public void createExe(HttpServletResponse response,Map<String, Object> dataMap, String fileName, String template) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(SchoolReportNewServiceImpl.class, "/");
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
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".xlsx");
           
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

	@Override
	public void shaichawenjuanRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("activityId", activityId);
//		map.put("school", "外海实验学校");
//		map.put("shili","buliang");
		List<StudentNewDO> list = studentDao.list(map);

		if(list.size()>0){
            String activitytime = sdf.format(activityListDao.get(activityId).getAddTime());
            for (StudentNewDO studentNewDO : list) {
				Map<String,Object> mapPP = new LinkedHashMap<String,Object>();
				ResultEyesightNewDO resultEyesightDO ;
				ResultDiopterNewDO resultDiopterDO ;
				List<ResultEyesightNewDO> lifeShili = resultEyesightDao.getLifeShili(studentNewDO.getId());
				List<ResultDiopterNewDO> L = resultDiopterDao.getYanGuang("L", studentNewDO.getId(),activityId);
				List<ResultDiopterNewDO> R = resultDiopterDao.getYanGuang("R", studentNewDO.getId(),activityId);
				//List<ResultCornealNewDO> LR1 = resultCornealDao.getCornealMm("L", identityCard,"R1",activityId);
				//List<ResultCornealNewDO> LR2 = resultCornealDao.getCornealMm("L", identityCard,"R2",activityId);
				//List<ResultCornealNewDO> RR1 = resultCornealDao.getCornealMm("R", identityCard,"R1",activityId);
				//List<ResultCornealNewDO> RR2 = resultCornealDao.getCornealMm("R", identityCard,"R2",activityId);
				mapPP.put("学校", studentNewDO.getSchool());
				mapPP.put("年级", studentNewDO.getGrade());
				mapPP.put("班级", studentNewDO.getStudentClass());
				mapPP.put("学部", studentNewDO.getXueBu()==null?"":studentNewDO.getXueBu());
				mapPP.put("姓名", studentNewDO.getStudentName());
				mapPP.put("身份证号", studentNewDO.getIdentityCard());
				mapPP.put("民族",studentNewDO.getNation()==null?"":studentNewDO.getNation());
				mapPP.put("手机号", studentNewDO.getPhone()==null?"":studentNewDO.getPhone());
				mapPP.put("生日", studentNewDO.getBirthday()==null?"":sdf.format(studentNewDO.getBirthday()));
				mapPP.put("检查时间", studentNewDO.getLastCheckTime()==null?"":sdf.format(studentNewDO.getLastCheckTime()));
				if(studentNewDO.getStudentSex()!=null && studentNewDO.getStudentSex()==1){
					mapPP.put("性别", "男");
				}else if(studentNewDO.getStudentSex()!=null && studentNewDO.getStudentSex()==2){
					mapPP.put("性别", "女");
				}else{
					mapPP.put("性别", "");
				}
                SchoolNewDO schoolNewDO = schoolNewDao.get(studentNewDO.getSchoolId());
                mapPP.put("省", schoolNewDO.getProvincename());
                mapPP.put("市", schoolNewDO.getCityname());
                mapPP.put("市区县", schoolNewDO.getAreaname());
                mapPP.put("活动时间", activitytime);
				if(lifeShili.size()>0){
					resultEyesightDO = lifeShili.get(0);
					mapPP.put("裸眼视力-右", resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd());
					mapPP.put("裸眼视力-左", resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs());
					mapPP.put("生活视力-右", resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd());
					mapPP.put("生活视力-左", resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs());
				}else{
					mapPP.put("裸眼视力-右", "");
					mapPP.put("裸眼视力-左", "");
					mapPP.put("生活视力-右", "");
					mapPP.put("生活视力-左", "");
				}
                List<ResultQuestionDO> questionDOList = resultQuestionDao.getQuestion(studentNewDO.getId());
				if (questionDOList.size()>0 && questionDOList.get(0).getQuestionTwoL()!=null && !"".equals(questionDOList.get(0).getQuestionTwoL())){
                    mapPP.put("球镜-左", "-" + questionDOList.get(0).getQuestionTwoL());
                    mapPP.put("柱镜-左", "");
                    mapPP.put("轴位-左", "");
                }else {
                    if (L.size() > 0) {
                        resultDiopterDO = L.get(0);
                        mapPP.put("球镜-左", resultDiopterDO.getDiopterS() == null ? "" : resultDiopterDO.getDiopterS());
                        mapPP.put("柱镜-左", resultDiopterDO.getDiopterC() == null ? "" : resultDiopterDO.getDiopterC());
                        mapPP.put("轴位-左", resultDiopterDO.getDiopterA() == null ? "" : resultDiopterDO.getDiopterA());
                    } else {
                        mapPP.put("球镜-左", "");
                        mapPP.put("柱镜-左", "");
                        mapPP.put("轴位-左", "");
                    }
                }
                if (questionDOList.size()>0 && questionDOList.get(0).getQuestionTwoR()!=null && !"".equals(questionDOList.get(0).getQuestionTwoR())){
                    mapPP.put("球镜-右", "-" + questionDOList.get(0).getQuestionTwoR());
                    mapPP.put("柱镜-右", "");
                    mapPP.put("轴位-右", "");
                }else {
                    if (R.size() > 0) {
                        resultDiopterDO = R.get(0);
                        mapPP.put("球镜-右", resultDiopterDO.getDiopterS() == null ? "" : resultDiopterDO.getDiopterS());
                        mapPP.put("柱镜-右", resultDiopterDO.getDiopterC() == null ? "" : resultDiopterDO.getDiopterC());
                        mapPP.put("轴位-右", resultDiopterDO.getDiopterA() == null ? "" : resultDiopterDO.getDiopterA());
                    } else {
                        mapPP.put("球镜-右", "");
                        mapPP.put("柱镜-右", "");
                        mapPP.put("轴位-右", "");
                    }
                }

				if (questionDOList.size()>0){
					ResultQuestionDO resultQuestionDO = questionDOList.get(0);
					if ("".equals(resultQuestionDO.getQuestionOneI()) && resultQuestionDO.getQuestionOneI()==null){
						mapPP.put("目前孩子戴镜类型","");
					}else if (resultQuestionDO.getQuestionOneI()==1){
						mapPP.put("目前孩子戴镜类型","框架眼镜");
					}else if (resultQuestionDO.getQuestionOneI()==2){
						mapPP.put("目前孩子戴镜类型","隐形眼镜");
					}else if (resultQuestionDO.getQuestionOneI()==3){
						mapPP.put("目前孩子戴镜类型","夜戴角膜塑形镜");
					}else if (resultQuestionDO.getQuestionOneI()==4){
						mapPP.put("目前孩子戴镜类型", "不戴镜");
					}
					if (!"".equals(resultQuestionDO.getQuestionOneS()) && resultQuestionDO.getQuestionOneS()!=null){
						mapPP.put("目前孩子戴镜类型",resultQuestionDO.getQuestionOneS());
					}

					mapPP.put("右眼戴镜的近视度数",resultQuestionDO.getQuestionTwoR()==null?"":resultQuestionDO.getQuestionTwoR());
					mapPP.put("左眼戴镜的近视度数",resultQuestionDO.getQuestionTwoL()==null?"":resultQuestionDO.getQuestionTwoL());
					mapPP.put("是否存在眼部疾病史",resultQuestionDO.getQuestionThree()==null?"":resultQuestionDO.getQuestionThree());

				}else {
					mapPP.put("目前孩子戴镜类型","");
					mapPP.put("右眼戴镜的近视度数","");
					mapPP.put("左眼戴镜的近视度数","");
					mapPP.put("是否存在眼部疾病史","");
				}
				/*if(LR1.size()>0){
					resultCornealDO = LR1.get(0);
					mapPP.put("R1-左", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R1-左", "");
				}
				if(LR2.size()>0){
					resultCornealDO = LR2.get(0);
					mapPP.put("R2-左", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R2-左","");
				}
				if(RR1.size()>0){
					resultCornealDO = RR1.get(0);
					mapPP.put("R1-右", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R1-右", "");
				}
				if(RR2.size()>0){
					resultCornealDO = RR2.get(0);
					mapPP.put("R2-右", resultCornealDO.getCornealD()==null?"":resultCornealDO.getCornealD());
				}else{
					mapPP.put("R2-右", "");
				}*/
//                List<ChanpinRecordDetailsDO> list4 = chanpinRecordDetailsDao.getByChanpin(null);
//                for (ChanpinRecordDetailsDO chanpinRecordDetailsDO : list4) {
//                    mapPP.put(chanpinRecordDetailsDO.getTitleName(), "");
//                }
//				List<ChanpinRecordListDO> list2 = chanpinRecordListDao.getIdentityCard(identityCard);
//				if(list2.size()>0){
//					for (ChanpinRecordListDO chanpinRecordListDO : list2) {
//
//						List<ChanpinRecordDetailsDO> list3 = chanpinRecordDetailsDao.getByChanpin(chanpinRecordListDO.getId());
//						Map<String,Object> mapPPP = new HashMap<String,Object>();
//						//mapPP.put("问卷名称", chanpinRecordListDao.get(chanpinRecordListDO.getId()).getChanpinName());
//						for (ChanpinRecordDetailsDO chanpinRecordDetailsDO : list3) {
//                            if(chanpinRecordDetailsDO.getChooseSort() != null){
//                                mapPP.put(chanpinRecordDetailsDO.getTitleName(), chanpinRecordDetailsDO.getChooseSort());
//                            }
//						}
//						//mapPP.putAll(mapPPP);
//					}
//				}

				bb.add(mapPP);
			}
		}else{
			Map<String,Object> mapp = new HashMap<String,Object>();
			mapp.put("信息", "暂无数据！！！");
			bb.add(mapp);
		}


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "问卷记录"+format.format(new Date().getTime())+".xls";
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

	/**
	 *  条件导出数据
	 */
	@Override
	public void conditionExport(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		double dengxiaoqiujingL = 0.0,dengxiaoqiujingR=0.0;
		double zhujingqL = 0.0;
		double zhujingqR = 0.0;
		double od=0.0,os=0.0;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("school","济南市育秀中学小学部");
		List<StudentNewDO> listAll = studentDao.list(map);
		Map<String, List<StudentNewDO> > listMap = listAll.stream().collect(Collectors.groupingBy(k -> k.getGrade()+k.getStudentClass()));
		Set<Map.Entry<String, List<StudentNewDO>>> entries = listMap.entrySet();
		String fileName="";
		for(Map.Entry<String, List<StudentNewDO>> e :entries) {
			List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
			List<StudentNewDO> list = e.getValue();
			for (StudentNewDO studentNewDO : list) {
				fileName=studentNewDO.getSchool()+studentNewDO.getGrade()+studentNewDO.getStudentClass();
				Map<String, Object> mapPP = new LinkedHashMap<String, Object>();
				ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
				ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
				List<ResultEyesightNewDO> lifeShili = resultEyesightDao.getLifeShili(studentNewDO.getId());
				List<ResultDiopterNewDO> L = studentNewDao.getLatestResultDiopterDOListL(studentNewDO.getId(), "L");
				List<ResultDiopterNewDO> R = studentNewDao.getLatestResultDiopterDOListL(studentNewDO.getId(), "R");
				mapPP.put("学校", studentNewDO.getSchool());
				mapPP.put("年级", studentNewDO.getGrade());
				mapPP.put("班级", studentNewDO.getStudentClass());
				mapPP.put("学部", studentNewDO.getXueBu() == null ? "" : studentNewDO.getXueBu());
				mapPP.put("姓名", studentNewDO.getStudentName());
				mapPP.put("身份证号", studentNewDO.getIdentityCard());
				mapPP.put("手机号", studentNewDO.getPhone() == null ? "" : studentNewDO.getPhone());
				mapPP.put("生日", studentNewDO.getBirthday() == null ? "" : sdf.format(studentNewDO.getBirthday()));
				mapPP.put("检查时间", studentNewDO.getLastCheckTime() == null ? "" : sdf.format(studentNewDO.getLastCheckTime()));
				if (studentNewDO.getStudentSex() != null && studentNewDO.getStudentSex() == 1) {
					mapPP.put("性别", "男");
				} else if (studentNewDO.getStudentSex() != null && studentNewDO.getStudentSex() == 2) {
					mapPP.put("性别", "女");
				} else {
					mapPP.put("性别", "");
				}
				SchoolNewDO schoolNewDO = schoolNewDao.get(studentNewDO.getSchoolId());
				mapPP.put("省", schoolNewDO.getProvincename());
				mapPP.put("市", schoolNewDO.getCityname());
				mapPP.put("市区县", schoolNewDO.getAreaname());
				String nakedFarvisionOd="";
				String nakedFarvisionOs="";
				String correctionFarvisionOd="";
				String correctionFarvisionOs="";
				if (lifeShili.size() > 0) {
					resultEyesightDO = lifeShili.get(0);
					nakedFarvisionOd = resultEyesightDO.getNakedFarvisionOd() == null ? "" : resultEyesightDO.getNakedFarvisionOd();
					nakedFarvisionOs = resultEyesightDO.getNakedFarvisionOs() == null ? "" : resultEyesightDO.getNakedFarvisionOs();
					correctionFarvisionOd = resultEyesightDO.getCorrectionFarvisionOd() == null ? "" : resultEyesightDO.getCorrectionFarvisionOd();
					correctionFarvisionOs = resultEyesightDO.getCorrectionFarvisionOs() == null ? "" : resultEyesightDO.getCorrectionFarvisionOs();
				}
				mapPP.put("裸眼视力-右", nakedFarvisionOd);
				mapPP.put("裸眼视力-左", nakedFarvisionOs);
				mapPP.put("生活视力-右", correctionFarvisionOd);
				mapPP.put("生活视力-左", correctionFarvisionOs);
				if(!StringUtils.isBlank(nakedFarvisionOd) && isDouble(nakedFarvisionOd)){
					od=Double.parseDouble(nakedFarvisionOd);
				}
				if(!StringUtils.isBlank(nakedFarvisionOs) && isDouble(nakedFarvisionOs)){
					os=Double.parseDouble(nakedFarvisionOs);
				}

				if (L.size() > 0) {
					resultDiopterDO = L.get(0);
					mapPP.put("球镜-左", resultDiopterDO.getDiopterS() == null ? "" : resultDiopterDO.getDiopterS());
					mapPP.put("柱镜-左", resultDiopterDO.getDiopterC() == null ? "" : resultDiopterDO.getDiopterC());
					mapPP.put("轴位-左", resultDiopterDO.getDiopterA() == null ? "" : resultDiopterDO.getDiopterA());
					dengxiaoqiujingL=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
					zhujingqL = resultDiopterDO.getDiopterC() == null ? 0.0 : resultDiopterDO.getDiopterC();
				} else {
					mapPP.put("球镜-左", "");
					mapPP.put("柱镜-左", "");
					mapPP.put("轴位-左", "");
				}
				if (R.size() > 0) {
					resultDiopterDO = R.get(0);
					mapPP.put("球镜-右", resultDiopterDO.getDiopterS() == null ? "" : resultDiopterDO.getDiopterS());
					mapPP.put("柱镜-右", resultDiopterDO.getDiopterC() == null ? "" : resultDiopterDO.getDiopterC());
					mapPP.put("轴位-右", resultDiopterDO.getDiopterA() == null ? "" : resultDiopterDO.getDiopterA());
					dengxiaoqiujingR=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
					zhujingqR = resultDiopterDO.getDiopterC() == null ? 0.0 : resultDiopterDO.getDiopterC();
				} else {
					mapPP.put("球镜-右", "");
					mapPP.put("柱镜-右", "");
					mapPP.put("轴位-右", "");
				}
				String yz= "";
				String zz="";
			/*	if (od >= 5.0 && dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75 && zhujingqR > -1.0) {
					yz= "近视临床前期";
				}
				if (os >= 5.0 && dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75 && zhujingqL > -1.0) {
					zz =  "近视临床前期";
				}

				if (od >= 5.0 && dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75 && zhujingqR <= -1.0) {
					yz= "近视临床前期";
				}
				if (os >= 5.0 && dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75 && zhujingqL <= -1.0) {
					zz= "近视临床前期";
				}*/

				if (od >= 5.0 && dengxiaoqiujingR < -0.5 && zhujingqR > -1.0) {
					yz =  "假性近视";
				}
				if (os >= 5.0 && dengxiaoqiujingL < -0.5 && zhujingqL > -1.0) {
					zz= "假性近视";
				}

				if (od >= 5.0 && dengxiaoqiujingR < -0.5 && zhujingqR <= -1.0) {
					yz= "假性近视";
				}
				if (os >= 5.0 && dengxiaoqiujingL < -0.5 && zhujingqL <= -1.0) {
					zz  = "假性近视";
				}
				mapPP.put("右眼结果", yz);
				mapPP.put("左眼结果",zz);
				if(!"".equals(yz) || !"".equals(zz))
					bb.add(mapPP);
			}

			if(bb.size()>0) {
				OutputStream out = new FileOutputStream(bootdoConfig.getPoiword()+new String(fileName.getBytes(),"UTF-8")+".xls");
				try {
					ExcelExportUtil4DIY.exportToFile(bb, out);
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}


	}


}
