package com.shaicha.information.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.shaicha.information.dao.*;
import com.shaicha.information.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.information.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService{
	
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ResultAdjustingDao adjustingDao;
	@Autowired
	private ResultCornealDao cornealDao;
	@Autowired
	private ResultDiopterDao diopterDao;
	@Autowired
	private ResultEyeaxisDao eyeaxisDao;
	@Autowired
	private ResultEyepressureDao eyepressureDao;
	@Autowired
	private ResultEyesightDao eyesightDao;
	@Autowired
	private ResultOptometryDao optometryDao;
	@Autowired
	private ResultVisibilityDao visibilityDao;
	@Autowired
	private SchoolNewDao  schoolNewDao;
	@Autowired
	private ResultQuestionDao resultQuestionDao;

	@Override
	public Map<String, Object> saveResultData(JSONObject obj) {
		Map<String,Object> result = new HashMap<String,Object>();
    	Date date = new Date();
		String identityCard1=obj.getString("identityCard");
		String identityCard = identityCard1.split("JOIN")[0];
		Long studentId = obj.getLong("studentId");
		Integer activityId = studentDao.get(studentId).getActivityId();
		Long chectorId = obj.getLong("chectorId");
		StudentDO stu = new StudentDO();
		JSONObject studentDetails = obj.getJSONObject("studentDetails");
		String studentName=studentDetails.getString("studentName");
		Integer studentSex=studentDetails.getInteger("studentSex");
		Integer schoolId=studentDetails.getInteger("schoolId");
		String nation=studentDetails.getString("nation");
		String grade=studentDetails.getString("grade");
		String studentClass=studentDetails.getString("studentClass");
		String phone=studentDetails.getString("phone");
		Integer questionOneI = studentDetails.getInteger("questionOneI");
		String questionOneS = studentDetails.getString("questionOneS");
		String questionTwoR = studentDetails.getString("questionTwoR");
		String questionTwoL = studentDetails.getString("questionTwoL");
		String questionThree = studentDetails.getString("questionThree");
		//String ideentityType=studentDetails.getString("ideentityType");
		/*if(ideentityType.equals("身份证") && identityCard.length() == 18){
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String year = identityCard.substring(6, 10);
			String month = identityCard.substring(10, 12);
			String day = identityCard.substring(12, 14);
			String bir = year+"-"+month+"-"+day;
			try {
				stu.setBirthday(sdf.parse(bir));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}*/
		stu.setStudentName(studentName);
		stu.setStudentSex(studentSex);
		stu.setNation(nation);
		stu.setGrade(grade);
		stu.setStudentClass(studentClass);
		stu.setPhone(phone);
		//stu.setIdeentityType(ideentityType);
		SchoolNewDO schoolNewDO = schoolNewDao.get(schoolId);
		stu.setSchool(schoolNewDO.getOrgname());
		stu.setAddress(schoolNewDO.getAreaname());
		stu.setSchoolCode(schoolNewDO.getOrgcode());
		stu.setXueBu(schoolNewDO.getXuebu());
		stu.setSchoolId(schoolId);
		stu.setId(studentId.intValue());
		studentDao.update(stu);
		//问卷
		ResultQuestionDO resultQuestionDO = new ResultQuestionDO(studentId.intValue(),studentName,questionOneI,questionOneS,questionTwoR,questionTwoL,questionThree);
		addUpdate(studentId,resultQuestionDO);

		JSONObject jsonObject =obj.getJSONObject("eyesight");//视力检查数据

    	String nakedFarvisionOd = jsonObject.getString("nakedFarvisionOd");
    	String nakedFarvisionOs = jsonObject.getString("nakedFarvisionOs");
    	String correctionFarvisionOd = jsonObject.getString("correctionFarvisionOd");
    	String correctionFarvisionOs = jsonObject.getString("correctionFarvisionOs");

    	ResultEyesightDO resultEyesightDO = new ResultEyesightDO(studentId,chectorId,  nakedFarvisionOd, nakedFarvisionOs, 
    			correctionFarvisionOd, correctionFarvisionOs,date, 0,identityCard,activityId);
    	if(		//"".equals(lifeFarvisionOd) && 
    			//"".equals(lifeFarvisionOs)&& 
    			"".equals(nakedFarvisionOd) &&
    			"".equals(nakedFarvisionOs) &&
    			//"".equals(lifeNearvisionOd) &&
    			//"".equals(lifeNearvisionOs)&&
    			"".equals(correctionFarvisionOd)&&
    			"".equals(correctionFarvisionOs) 
    			//"".equals(nakedNearvisionOd)&& 
    			//"".equals(nakedNearvisionOs)
    	   );
    	else	
    		addUpdate(studentId, resultEyesightDO);
    	jsonObject =obj.getJSONObject("eyeaxis");//眼轴检查数据
		double firstCheckOd=jsonObject.getDouble("firstCheckOd");
		double firstCheckOs=jsonObject.getDouble("firstCheckOs");
		//double secondCheckOd=jsonObject.getDouble("secondCheckOd");
		//double secondCheckOs=jsonObject.getDouble("secondCheckOs");
		ResultEyeaxisDO  resultEyeaxisDO = new ResultEyeaxisDO(studentId,chectorId, firstCheckOd, firstCheckOs,date, 0,identityCard,activityId);
		if(
				firstCheckOd==0.0 && 
				firstCheckOs==0.0 
				//secondCheckOd==0.0 && 
				//secondCheckOs==0.0
		);
		else
			addUpdate(studentId, resultEyeaxisDO);
		//jsonObject =obj.getJSONObject("eyepressure");//眼内压检查数据
    	//double eyePressureOd=jsonObject.getDouble("eyePressureOd");
    	//double eyePressureOs=jsonObject.getDouble("eyePressureOs");
    	//String cornealCurvatureOd=jsonObject.getString("cornealCurvatureOd");
    	//String cornealCurvatureOs=jsonObject.getString("cornealCurvatureOs");
    	//ResultEyepressureDO resultEyepressureDO= new ResultEyepressureDO(studentId,chectorId, eyePressureOd, eyePressureOs, date, 0,identityCard,activityId,"0","0");
    	//if(
    			//eyePressureOd==0.0 &&
    			//eyePressureOs==0.0
    			//"".equals(cornealCurvatureOd) && 
    			//"".equals(cornealCurvatureOs)
    	//);
    	//else
    		//addUpdate(studentId, resultEyepressureDO);
    	jsonObject =obj.getJSONObject("adjusting");//调节灵敏度检查数据
    	double adjustingOd=jsonObject.getDouble("adjustingOd");
    	double adjustingOs=jsonObject.getDouble("adjustingOs");
    	double adjustingOu=jsonObject.getDouble("adjustingOu");
    	Integer jjOd=jsonObject.getInteger("jjOd");
    	Integer jjOs=jsonObject.getInteger("jjOs");
    	Integer jjOu=jsonObject.getInteger("jjOu");
    	ResultAdjustingDO resultAdjustingDO=new ResultAdjustingDO(studentId,chectorId, adjustingOd, adjustingOs, adjustingOu, jjOd, jjOs, jjOu, date, 0,identityCard,activityId);
    	if(
    		adjustingOd==0.0 && 
    		adjustingOs==0.0 && 
    		adjustingOu==0.0 && 
    		jjOd==0 && 
    		jjOs==0 && 
    		jjOu==0
    	);
    	else
    		addUpdate(studentId, resultAdjustingDO);
    	jsonObject =obj.getJSONObject("visibility");//视功能检查数据
    	Integer stereoscopicViewingValue=jsonObject.getInteger("stereoscopicViewingValue");
		String stereoscopicViewingDis=jsonObject.getString("stereoscopicViewingDis");
		double adjustmentRangeOd=jsonObject.getDouble("adjustmentRangeOd");
		double adjustmentRangeOc=jsonObject.getDouble("adjustmentRangeOc");
		double adjustmentRangeOu=jsonObject.getDouble("adjustmentRangeOu");
//		double gatherNearOd=jsonObject.getDouble("gatherNearOd");
//		double gatherNearOc=jsonObject.getDouble("gatherNearOc");
//		double gatherNearOu=jsonObject.getDouble("gatherNearOu");
//		String obliqueValue=jsonObject.getString("obliqueValue");
//		double obliqueDis=jsonObject.getDouble("obliqueDis");
//		Integer beforeAfterOdValue=jsonObject.getInteger("beforeAfterOdValue");
//		String beforeAfterOdDis=jsonObject.getString("beforeAfterOdDis");
//		Integer beforeAfterOsValue=jsonObject.getInteger("beforeAfterOsValue");
//		String beforeAfterOsDis=jsonObject.getString("beforeAfterOsDis");
//		ResultVisibilityDO resultVisibilityDO=new ResultVisibilityDO(studentId,chectorId, stereoscopicViewingValue, stereoscopicViewingDis,
//				adjustmentRangeOd, adjustmentRangeOc, adjustmentRangeOu, gatherNearOd, gatherNearOc, gatherNearOu, obliqueValue, obliqueDis, beforeAfterOdValue, beforeAfterOdDis, beforeAfterOsValue, beforeAfterOsDis,date, 0,identityCard,activityId);
		ResultVisibilityDO resultVisibilityDO=new ResultVisibilityDO(studentId,chectorId, stereoscopicViewingValue, stereoscopicViewingDis,
				adjustmentRangeOd, adjustmentRangeOc, adjustmentRangeOu,date, 0,identityCard,activityId);

		if(
				stereoscopicViewingValue==0 && 
				"".equals(stereoscopicViewingDis)&&
				adjustmentRangeOd==0.0&&
				adjustmentRangeOc==0.0&&
				adjustmentRangeOu==0.0//&&
//				gatherNearOd==0.0&&
//				gatherNearOc==0.0&&
//				gatherNearOu==0.0&&
//				"".equals(obliqueValue)&&
//				obliqueDis==0.0 &&
//				beforeAfterOdValue==0 &&
//				"".equals(beforeAfterOdDis) &&
//				beforeAfterOsValue==0 &&
//				"".equals(beforeAfterOsDis)
		);
		
		else
			addUpdate(studentId, resultVisibilityDO);
    	jsonObject =obj.getJSONObject("optometry");//电脑验光检查数据
    	double firstCheckVd=jsonObject.getDouble("firstCheckVd");
    	double firstCheckRps=jsonObject.getDouble("firstCheckRps");
    	double firstCheckLps=jsonObject.getDouble("firstCheckLps");
    	double firstCheckRcs=jsonObject.getDouble("firstCheckRcs");
    	double firstCheckLcs=jsonObject.getDouble("firstCheckLcs");
    	double secondCheckVd=jsonObject.getDouble("secondCheckVd");
    	double secondCheckRps=jsonObject.getDouble("secondCheckRps");
    	double secondCheckLps=jsonObject.getDouble("secondCheckLps");
    	double secondCheckRcs=jsonObject.getDouble("secondCheckRcs");
    	double secondCheckLcs=jsonObject.getDouble("secondCheckLcs");
    	double firstCheckPd=jsonObject.getDouble("firstCheckPd");
    	double secondCheckPd=jsonObject.getDouble("secondCheckPd");
    	ResultOptometryDO resultOptometryDO=new ResultOptometryDO(studentId, chectorId, firstCheckVd, firstCheckPd, secondCheckPd, firstCheckRps, firstCheckLps, firstCheckRcs, firstCheckLcs,date, 
    			secondCheckVd, secondCheckRps, secondCheckLps, secondCheckRcs, secondCheckLcs, 0,identityCard,activityId);
    	int tOptometryId=0;
    	if(
				firstCheckVd==0.0 &&
				firstCheckRps==0.0 &&
				firstCheckLps==0.0&&
				firstCheckRcs==0.0&&
				firstCheckLcs==0.0&&
				secondCheckVd==0.0&&
				secondCheckRps==0.0&&
				secondCheckLps==0.0&&
				secondCheckRcs==0.0&&
				secondCheckLcs==0.0&&
				firstCheckPd==0.0&&
				secondCheckPd==0.0
		  );
		
		else
		tOptometryId = 	addUpdate(studentId, resultOptometryDO);
    	JSONArray jsonArray =obj.getJSONArray("diopter");
    	if(jsonArray!=null){
    		List<ResultDiopterDO> diopterDOs = new ArrayList<ResultDiopterDO>();
    		for(int j=0;j<jsonArray.size();j++){
    			JSONObject jb  = jsonArray.getJSONObject(j);
    			double diopterS=jb.getDouble("diopterS");
    			double diopterC=jb.getDouble("diopterC");
    			double diopterA=jb.getDouble("diopterA");
    			Integer believe=jb.getInteger("believe");
    			Integer num=jb.getInteger("num");
    			String type=jb.getString("type");
    			String ifrl=jb.getString("ifrl");
    			//String firstSecond = jb.getString("firstSecond");
    			ResultDiopterDO resultDiopterDO=new ResultDiopterDO(tOptometryId, diopterS, diopterC, diopterA, believe, num, type, ifrl, identityCard,activityId);
    			//计算等效球镜
    			resultDiopterDO.setDengxiaoqiujing(diopterS+1.0/2*diopterC);
    			System.out.println(resultDiopterDO);
    			resultDiopterDO.setTOptometryId(tOptometryId);
    			resultDiopterDO.setCheckDate(date);
    			if( 
    					diopterS==0.0 && 
    					diopterC==0.0 && 
    					diopterA==0.0 && 
    					believe==0 &&
    					num==0 && 
    					"".equals(type) && 
    					"".equals(ifrl)
    					//"".equals(firstSecond)
    					);
    			else
    				diopterDOs.add(resultDiopterDO);
    		}
    		if(diopterDOs.size()>0)
    			addUpdate(studentId, diopterDOs);
    	}
    	jsonArray =obj.getJSONArray("corneal");
    	if(jsonArray!=null){
    		List<ResultCornealDO> cornealDOs = new ArrayList<ResultCornealDO>();
    		for(int j=0;j<jsonArray.size();j++){
    			JSONObject jb  = jsonArray.getJSONObject(j);
    			double cornealMm=jb.getDouble("cornealMm");
    			double cornealD=jb.getDouble("cornealD");
    			Integer cornealDeg=jb.getInteger("cornealDeg");
    			String type=jb.getString("type");
    			String ifrl=jb.getString("ifrl");
    			//String firstSecond = jb.getString("firstSecond");
    			ResultCornealDO resultCornealDO = new ResultCornealDO(tOptometryId, cornealMm, cornealD, cornealDeg, type, ifrl,identityCard,activityId);
    			resultCornealDO.setTOptometryId(tOptometryId);
    			resultCornealDO.setCheckDate(date);
    			System.out.println(resultCornealDO);
    			if(
    					cornealMm==0.0&&
    					cornealD==0.0&&
    					cornealDeg==0&&
    					"".equals(type)&&
    					"".equals(ifrl)
    					//"".equals(firstSecond)
    					);
    			else 
    				cornealDOs.add(resultCornealDO);
    		}
    		if(cornealDOs.size()>0)
    			addUpdatec(studentId, cornealDOs);
    	}
    	studentDao.updateLastCheckTime(studentId,date);
    	
    	result.put("code", 0);
    	result.put("msg","上传数据成功");
		return result;
	}
    
	/**
	 * 判断是否需要新增数据 null=新增  !null=修改
	 */
	public boolean checkIfSaveResult(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,13);
		if(calendar.getTime().compareTo(new Date())>=0)
			return true;
		return false;
	}

	public void addUpdate(Long studentId,ResultEyesightDO resultEyesightDO){
		
		List<ResultEyesightDO> resultEyesightDOList=eyesightDao.getEyesightDO(studentId);
		if(resultEyesightDOList.size()>0){
			Date checkDate  = resultEyesightDOList.get(0).getCheckDate();
			if(checkDate==null)
				eyesightDao.saveEyesightDO(resultEyesightDO);
			else{
				if(checkIfSaveResult(checkDate)){
					resultEyesightDO.setTEyesightId(resultEyesightDOList.get(0).getTEyesightId());
					eyesightDao.updateEyesightDO(resultEyesightDO);
				}
				else
					eyesightDao.saveEyesightDO(resultEyesightDO);
			}
		}
		else{
			eyesightDao.saveEyesightDO(resultEyesightDO);
		}
		//向t_student表中更新nakedFarvisionOd nakedFarvisionOs
		StudentDO studentDO = new StudentDO();
		studentDO.setId(studentId.intValue());
		studentDO.setNakedFarvisionOd(resultEyesightDO.getNakedFarvisionOd());
		studentDO.setNakedFarvisionOs(resultEyesightDO.getNakedFarvisionOs());
		studentDao.updateStudentDOshi(studentDO);
	}
	public void addUpdate(Long studentId,ResultEyeaxisDO resultEyeaxisDO){
		
		List<ResultEyeaxisDO> resultEyeaxisDOList=eyeaxisDao.getEyeaxisDO(studentId);
		if(resultEyeaxisDOList.size()>0){
			Date checkDate  = resultEyeaxisDOList.get(0).getCheckDate();
			if(checkDate==null)
				eyeaxisDao.saveEyeaxisDO(resultEyeaxisDO);
			else{
				if(checkIfSaveResult(checkDate)){
					resultEyeaxisDO.setTEyeaxisId(resultEyeaxisDOList.get(0).getTEyeaxisId());
					eyeaxisDao.updateEyeaxisDO(resultEyeaxisDO);
				}
				else
					eyeaxisDao.saveEyeaxisDO(resultEyeaxisDO);
			}
		}
		else{
			eyeaxisDao.saveEyeaxisDO(resultEyeaxisDO);
		}
	}
	public void addUpdate(Long studentId,ResultEyepressureDO resultEyepressureDO){
		List<ResultEyepressureDO> resultEyepressureDOList=eyepressureDao.getEyepressureDO(studentId);
		if(resultEyepressureDOList.size()>0){
			Date checkDate  = resultEyepressureDOList.get(0).getCheckDate();
			if(checkDate==null)
				eyepressureDao.saveEyepressureDO(resultEyepressureDO);
			else{
				if(checkIfSaveResult(checkDate)){
					resultEyepressureDO.setTEyepressureId(resultEyepressureDOList.get(0).getTEyepressureId());
					eyepressureDao.updateEyepressureDO(resultEyepressureDO);
				}
				else
					eyepressureDao.saveEyepressureDO(resultEyepressureDO);
			}
		}
		else{
			eyepressureDao.saveEyepressureDO(resultEyepressureDO);
		}
	}
	public void addUpdate(Long studentId,ResultQuestionDO resultQuestionDO){
		List<ResultQuestionDO> ResultQuestionDOList=resultQuestionDao.get(studentId);
		if(ResultQuestionDOList.size()>0){
			Date checkDate  = studentDao.get(studentId).getLastCheckTime();
			if(checkDate!=null) {
				resultQuestionDao.update(resultQuestionDO);
			}
		}
		else{
			resultQuestionDao.save(resultQuestionDO);
		}
	}
	public void addUpdate(Long studentId,ResultAdjustingDO resultAdjustingDO){
		List<ResultAdjustingDO> resultAdjustingDOList=adjustingDao.getAdjustingDO(studentId);
		if(resultAdjustingDOList.size()>0){
			Date checkDate  = resultAdjustingDOList.get(0).getCheckDate();
			if(checkDate==null)
				adjustingDao.saveAdjustingDO(resultAdjustingDO);
			else{
				if(checkIfSaveResult(checkDate)){
					resultAdjustingDO.setTAdjustingId(resultAdjustingDOList.get(0).getTAdjustingId());
					adjustingDao.updateAdjustingDO(resultAdjustingDO);
				}
				else
					adjustingDao.saveAdjustingDO(resultAdjustingDO);
			}
		}
		else{
			adjustingDao.saveAdjustingDO(resultAdjustingDO);
		}
	}
	public void addUpdate(Long studentId,ResultVisibilityDO resultVisibilityDO){
		List<ResultVisibilityDO> resultVisibilityDOList=visibilityDao.getVisibilityDO(studentId);
		if(resultVisibilityDOList.size()>0){
			Date checkDate  = resultVisibilityDOList.get(0).getCheckDate();
			if(checkDate==null)
				visibilityDao.saveVisibilityDO(resultVisibilityDO);
			else{
				if(checkIfSaveResult(checkDate)){
					resultVisibilityDO.setTVisibilityId(resultVisibilityDOList.get(0).getTVisibilityId());
					visibilityDao.updateVisibilityDO(resultVisibilityDO);
				}
				else
					visibilityDao.saveVisibilityDO(resultVisibilityDO);
			}
		}
		else{
			visibilityDao.saveVisibilityDO(resultVisibilityDO);
		}
	}
	public Integer addUpdate(Long studentId,ResultOptometryDO resultOptometryDO){
		List<ResultOptometryDO> resultOptometryDOList=optometryDao.getOptometryDO(studentId);
		if(resultOptometryDOList.size()>0){
			Date checkDate  = resultOptometryDOList.get(0).getCheckDate();
			if(checkDate==null)
				optometryDao.saveOptometryDO(resultOptometryDO);
			else{
				if(checkIfSaveResult(checkDate)){
					resultOptometryDO.setTOptometryId(resultOptometryDOList.get(0).getTOptometryId());
					optometryDao.updateOptometryDO(resultOptometryDO);
					
				}
				else
					optometryDao.saveOptometryDO(resultOptometryDO);
			}
		}
		else{
			optometryDao.saveOptometryDO(resultOptometryDO);
		}
		return resultOptometryDO.getTOptometryId();
	}
	public void addUpdate(Long studentId,List<ResultDiopterDO> diopterDOs){
		
		
		if(diopterDOs.size()>0){
			diopterDao.removeAll(diopterDOs.get(0).getTOptometryId());
			for(ResultDiopterDO resultDiopterDO:diopterDOs){
				diopterDao.saveDiopterDO(resultDiopterDO);
			}
			//向t_student表中更新 dengxiaoqiujingr，dengxiaoqiujingl
			StudentDO studentDO = new StudentDO();
			studentDO.setId(studentId.intValue());
			for(ResultDiopterDO d :diopterDOs){
				if("AVG".equals(d.getType()) && "R".equals(d.getIfrl()))
					studentDO.setDengxiaoqiujingr(d.getDengxiaoqiujing());
				if("AVG".equals(d.getType()) && "L".equals(d.getIfrl()))
					studentDO.setDengxiaoqiujingl(d.getDengxiaoqiujing());
			}
			studentDao.updateStudentDOshi(studentDO);	
		}
	}
	public void addUpdatec(Long studentId,List<ResultCornealDO> cornealDOs){		
		if(cornealDOs.size()>0){
			cornealDao.removeAll(cornealDOs.get(0).getTOptometryId());
			for(ResultCornealDO cornealDO:cornealDOs){
				cornealDao.saveCornealDO(cornealDO);
			}
			
		}
	}


	@Override
	public Map<String, Object> getStudentInfo(String identityCard) {
		Map<String,Object> resultMap  =new HashMap<String,Object>();
		if(identityCard.length() <= 0 || identityCard.trim() == "" || identityCard.indexOf("JOIN") == -1){
			resultMap.put("code", 1);
			resultMap.put("msg", "请重新扫描二维码");
			resultMap.put("data", null);
		}else{
			String[] split = identityCard.split("JOIN");
			List<StudentDO>  list = studentDao.getStudentInfo(Integer.valueOf(split[1]));
			if(list.size()==0){
				resultMap.put("code", -1);
				resultMap.put("msg", "数据缺失");
				resultMap.put("data", null);
			}
			else{
				resultMap.put("code", 0);
				resultMap.put("msg", "获取到数据...");
				resultMap.put("data",list.get(0));
			}
		}
		return resultMap;
	}

	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getStudentInfo(Long id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ResultDiopterDO> diopterDOs = new ArrayList<ResultDiopterDO>();
		List<ResultCornealDO> cornealDOs = new ArrayList<ResultCornealDO>();
	
		List<ResultEyesightDO> resultEyesightDOList=	eyesightDao.getEyesightDO(id);
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0){
			Date date = resultEyesightDOList.get(0).getCheckDate();
			if(checkIfSaveResult(date))
				resultEyesightDO=resultEyesightDOList.get(0);
		}
		
		List<ResultEyeaxisDO> resultEyeaxisDOList=  eyeaxisDao.getEyeaxisDO(id);
		ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
		if(resultEyeaxisDOList.size()>0){
			Date date  = resultEyeaxisDOList.get(0).getCheckDate();
			if(checkIfSaveResult(date))
				resultEyeaxisDO=resultEyeaxisDOList.get(0);
		}	
		
		List<ResultEyepressureDO> resultEyepressureDOList=eyepressureDao.getEyepressureDO(id);
		ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
		if(resultEyepressureDOList.size()>0){
			Date date = resultEyepressureDOList.get(0).getCheckDate();
			if(checkIfSaveResult(date))
				resultEyepressureDO=resultEyepressureDOList.get(0);
		}
					
		List<ResultAdjustingDO> resultAdjustingDOList=adjustingDao.getAdjustingDO(id);
		ResultAdjustingDO resultAdjustingDO = new ResultAdjustingDO();
		if(resultAdjustingDOList.size()>0){
			Date date =resultAdjustingDOList.get(0).getCheckDate();
			if(checkIfSaveResult(date))
				resultAdjustingDO=resultAdjustingDOList.get(0);
		}			
		List<ResultVisibilityDO> resultVisibilityDOList=visibilityDao.getVisibilityDO(id);
		ResultVisibilityDO resultVisibilityDO = new ResultVisibilityDO();
		if(resultVisibilityDOList.size()>0){
			Date date = resultVisibilityDOList.get(0).getCheckDate();
			if(checkIfSaveResult(date))
				resultVisibilityDO=resultVisibilityDOList.get(0);
		}			
		List<ResultOptometryDO> resultOptometryDOList=optometryDao.getOptometryDO(id);
		ResultOptometryDO resultOptometryDO = new ResultOptometryDO();
		if(resultOptometryDOList.size()>0){
			Date date =resultOptometryDOList.get(0).getCheckDate();
			if(checkIfSaveResult(date))
				resultOptometryDO=resultOptometryDOList.get(0);
		}
		if(resultOptometryDO!=null){
			diopterDOs = diopterDao.getByOptometryId(resultOptometryDO.getTOptometryId());
			cornealDOs=cornealDao.getByOptometryId(resultOptometryDO.getTOptometryId());
		}
		List<ResultQuestionDO> ResultQuestionDOList=resultQuestionDao.get(id);
		ResultQuestionDO resultQuestionDO = new ResultQuestionDO();
		if (ResultQuestionDOList.size()>0){
			Date checkDate  = studentDao.get(id).getLastCheckTime();
			if(checkDate!=null) {
				resultQuestionDO = ResultQuestionDOList.get(0);
			}
		}
		resultMap.put("resultEyesightDO", resultEyesightDO);
		resultMap.put("resultEyeaxisDO", resultEyeaxisDO);
		resultMap.put("resultEyepressureDO", resultEyepressureDO);
					
		resultMap.put("resultAdjustingDO", resultAdjustingDO);
		resultMap.put("resultVisibilityDO", resultVisibilityDO);
		resultMap.put("resultOptometryDO", resultOptometryDO);

        resultMap.put("resultQuestionDO",resultQuestionDO);

		resultMap.put("diopterDOs", diopterDOs);
		resultMap.put("cornealDOs", cornealDOs);
		resultMap.put("code",0);
		resultMap.put("msg","数据获取成功...");

		return resultMap;
	}

	@Override
	public List<SchoolNewDO> list(Map<String, Object> map) {
		return schoolNewDao.list(map);
	}


}
