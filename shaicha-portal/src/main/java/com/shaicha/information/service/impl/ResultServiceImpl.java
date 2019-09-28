package com.shaicha.information.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.information.dao.ResultAdjustingDao;
import com.shaicha.information.dao.ResultCornealDao;
import com.shaicha.information.dao.ResultDao;
import com.shaicha.information.dao.ResultDiopterDao;
import com.shaicha.information.dao.ResultEyeaxisDao;
import com.shaicha.information.dao.ResultEyepressureDao;
import com.shaicha.information.dao.ResultEyesightDao;
import com.shaicha.information.dao.ResultOptometryDao;
import com.shaicha.information.dao.ResultVisibilityDao;
import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.ResultAdjustingDO;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.domain.ResultVisibilityDO;
import com.shaicha.information.domain.StudentDO;
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

	@Override
	public Map<String, Object> saveResultData(JSONObject obj) {
		Map<String,Object> result = new HashMap<String,Object>();
    	Date date = new Date();
		Long studentId=obj.getLong("studentId");
		JSONObject jsonObject =obj.getJSONObject("eyesight");//视力检查数据
    	String lifeFarvisionOd = jsonObject.getString("lifeFarvisionOd");
    	String lifeFarvisionOs = jsonObject.getString("lifeFarvisionOs");
    	String nakedFarvisionOd = jsonObject.getString("nakedFarvisionOd");
    	String nakedFarvisionOs = jsonObject.getString("nakedFarvisionOs");
    	String correctionFarvisionOd = jsonObject.getString("correctionFarvisionOd");
    	String correctionFarvisionOs = jsonObject.getString("correctionFarvisionOs");
    	String lifeNearvisionOd = jsonObject.getString("lifeNearvisionOd");
    	String lifeNearvisionOs = jsonObject.getString("lifeNearvisionOs");
    	String nakedNearvisionOd = jsonObject.getString("nakedNearvisionOd");
    	String nakedNearvisionOs = jsonObject.getString("nakedNearvisionOs");
    	ResultEyesightDO resultEyesightDO = new ResultEyesightDO(studentId,ShiroUtils.getUserId(), lifeFarvisionOd, lifeFarvisionOs, nakedFarvisionOd, nakedFarvisionOs, 
    			correctionFarvisionOd, correctionFarvisionOs, lifeNearvisionOd, lifeNearvisionOs, nakedNearvisionOd, nakedNearvisionOs,date, 0);
    	addUpdate(studentId, resultEyesightDO);
    	jsonObject =obj.getJSONObject("eyeaxis");//眼轴检查数据
		Double firstCheckOd=jsonObject.getDouble("firstCheckOd");
		Double firstCheckOs=jsonObject.getDouble("firstCheckOs");
		Double secondCheckOd=jsonObject.getDouble("secondCheckOd");
		Double secondCheckOs=jsonObject.getDouble("secondCheckOs");
		ResultEyeaxisDO  resultEyeaxisDO = new ResultEyeaxisDO(studentId,ShiroUtils.getUserId(), firstCheckOd, firstCheckOs,date, secondCheckOd, secondCheckOs, 0);
		addUpdate(studentId, resultEyeaxisDO);
		jsonObject =obj.getJSONObject("eyepressure");//眼内压检查数据
    	Integer eyePressureOd=jsonObject.getInteger("eyePressureOd");
    	Integer eyePressureOs=jsonObject.getInteger("eyePressureOs");
    	ResultEyepressureDO resultEyepressureDO= new ResultEyepressureDO(studentId,ShiroUtils.getUserId(), eyePressureOd, eyePressureOs, date, 0);
    	addUpdate(studentId, resultEyepressureDO);
    	jsonObject =obj.getJSONObject("adjusting");//调节灵敏度检查数据
    	Double adjustingOd=jsonObject.getDouble("adjustingOd");
    	Double adjustingOs=jsonObject.getDouble("adjustingOs");
    	Double adjustingOu=jsonObject.getDouble("adjustingOu");
    	Integer jjOd=jsonObject.getInteger("jjOd");
    	Integer jjOs=jsonObject.getInteger("jjOs");
    	Integer jjOu=jsonObject.getInteger("jjOu");
    	ResultAdjustingDO resultAdjustingDO=new ResultAdjustingDO(studentId,ShiroUtils.getUserId(), adjustingOd, adjustingOs, adjustingOu, jjOd, jjOs, jjOu, date, 0);
    	addUpdate(studentId, resultAdjustingDO);
    	jsonObject =obj.getJSONObject("visibility");//视功能检查数据
    	Integer stereoscopicViewingValue=jsonObject.getInteger("stereoscopicViewingValue");
		String stereoscopicViewingDis=jsonObject.getString("stereoscopicViewingDis");
		Double adjustmentRangeOd=jsonObject.getDouble("adjustmentRangeOd");
		Double adjustmentRangeOc=jsonObject.getDouble("adjustmentRangeOc");
		Double adjustmentRangeOu=jsonObject.getDouble("adjustmentRangeOu");
		Double gatherNearOd=jsonObject.getDouble("gatherNearOd");
		Double gatherNearOc=jsonObject.getDouble("gatherNearOc");
		Double gatherNearOu=jsonObject.getDouble("gatherNearOu");
		String obliqueValue=jsonObject.getString("obliqueValue");
		Double obliqueDis=jsonObject.getDouble("obliqueDis");
		Integer beforeAfterOdValue=jsonObject.getInteger("beforeAfterOdValue");
		String beforeAfterOdDis=jsonObject.getString("beforeAfterOdDis");
		Integer beforeAfterOsValue=jsonObject.getInteger("beforeAfterOsValue");
		String beforeAfterOsDis=jsonObject.getString("beforeAfterOsDis");
		ResultVisibilityDO resultVisibilityDO=new ResultVisibilityDO(studentId,ShiroUtils.getUserId(), stereoscopicViewingValue, stereoscopicViewingDis, 
				adjustmentRangeOd, adjustmentRangeOc, adjustmentRangeOu, gatherNearOd, gatherNearOc, gatherNearOu, obliqueValue, obliqueDis, beforeAfterOdValue, beforeAfterOdDis, beforeAfterOsValue, beforeAfterOsDis,date, 0);
    	
		addUpdate(studentId, resultVisibilityDO);
    	jsonObject =obj.getJSONObject("optometry");//电脑验光检查数据
    	Double firstCheckVd=jsonObject.getDouble("firstCheckVd");
    	Double firstCheckRps=jsonObject.getDouble("firstCheckRps");
    	Double firstCheckLps=jsonObject.getDouble("firstCheckLps");
    	Double firstCheckRcs=jsonObject.getDouble("firstCheckRcs");
    	Double firstCheckLcs=jsonObject.getDouble("firstCheckLcs");
    	Double secondCheckVd=jsonObject.getDouble("secondCheckVd");
    	Double secondCheckRps=jsonObject.getDouble("secondCheckRps");
    	Double secondCheckLps=jsonObject.getDouble("secondCheckLps");
    	Double secondCheckRcs=jsonObject.getDouble("secondCheckRcs");
    	Double secondCheckLcs=jsonObject.getDouble("secondCheckLcs");
    	Double firstCheckPd=jsonObject.getDouble("firstCheckPd");
    	Double secondCheckPd=jsonObject.getDouble("secondCheckPd");
    	ResultOptometryDO resultOptometryDO=new ResultOptometryDO(studentId, ShiroUtils.getUserId(), firstCheckVd, firstCheckPd, secondCheckPd, firstCheckRps, firstCheckLps, firstCheckRcs, firstCheckLcs,date, 
    			secondCheckVd, secondCheckRps, secondCheckLps, secondCheckRcs, secondCheckLcs, 0);
		int tOptometryId = addUpdate(studentId, resultOptometryDO);
    	JSONArray jsonArray =jsonObject.getJSONArray("diopter");
    	List<ResultDiopterDO> diopterDOs = new ArrayList<ResultDiopterDO>();
    	for(int j=0;j<jsonArray.size();j++){
    		JSONObject jb  = jsonArray.getJSONObject(j);
    		Double diopterS=jb.getDouble("diopterS");
    		Double diopterC=jb.getDouble("diopterC");
    		Double diopterA=jb.getDouble("diopterA");
    		Integer believe=jb.getInteger("believe");
    		Integer num=jb.getInteger("num");
    		String type=jb.getString("type");
    		String ifrl=jb.getString("ifrl");
    		String firstSecond = jb.getString("firstSecond");
    		ResultDiopterDO resultDiopterDO=new ResultDiopterDO(resultOptometryDO.getTOptometryId(), diopterS, diopterC, diopterA, believe, num, type, ifrl, firstSecond);
    		resultDiopterDO.setTOptometryId(tOptometryId);
    		diopterDOs.add(resultDiopterDO);
    	}
    	addUpdate(studentId, diopterDOs);
    	jsonArray =jsonObject.getJSONArray("corneal");
    	List<ResultCornealDO> cornealDOs = new ArrayList<ResultCornealDO>();
    	for(int j=0;j<jsonArray.size();j++){
    		JSONObject jb  = jsonArray.getJSONObject(j);
    		Double cornealMm=jb.getDouble("cornealMm");
    		Double cornealD=jb.getDouble("cornealD");
    		Integer cornealDeg=jb.getInteger("cornealDeg");
    		String type=jb.getString("type");
    		String ifrl=jb.getString("ifrl");
    		String firstSecond = jb.getString("firstSecond");
    		ResultCornealDO resultCornealDO = new ResultCornealDO(resultOptometryDO.getTOptometryId(), cornealMm, cornealD, cornealDeg, type, ifrl, firstSecond);
    		resultCornealDO.setTOptometryId(tOptometryId);
    		cornealDOs.add(resultCornealDO);
    	}
    		addUpdatec(studentId, cornealDOs);
    		studentDao.updateLastCheckTime(studentId,date);
    	
    	result.put("code", 0);
    	result.put("msg","上传数据成功");
		return result;
	}
    
	/**
	 * 判断是否需要新增数据 null=新增  !null=修改
	 */
	public Date checkIfSaveResult(Long studentId){
		StudentDO  studentDO = studentDao.get(studentId);
		if(studentDO!=null){
			Date lastCheckTime=studentDO.getLastCheckTime();
			if(lastCheckTime==null)
				return null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastCheckTime);
			calendar.add(Calendar.DAY_OF_YEAR,13);
			if(calendar.getTime().compareTo(new Date())>=0)
				return lastCheckTime;
			else
				return null;
		}
		return null;
	}

	public void addUpdate(Long studentId,ResultEyesightDO resultEyesightDO){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			eyesightDao.saveEyesightDO(resultEyesightDO);
		}else{
			ResultEyesightDO resultEyesightDO1=eyesightDao.getEyesightDO(studentId,lastCheckTime);
			if(resultEyesightDO1!=null){
				resultEyesightDO.setTEyesightId(resultEyesightDO1.getTEyesightId());
				eyesightDao.updateEyesightDO(resultEyesightDO);
			}
			else
				eyesightDao.saveEyesightDO(resultEyesightDO);
		}
	}
	public void addUpdate(Long studentId,ResultEyeaxisDO resultEyeaxisDO){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			eyeaxisDao.saveEyeaxisDO(resultEyeaxisDO);
		}else{
			ResultEyeaxisDO resultEyeaxisDO1=eyeaxisDao.getEyeaxisDO(studentId, lastCheckTime);
			if(resultEyeaxisDO1!=null){
				resultEyeaxisDO.setTEyeaxisId(resultEyeaxisDO1.getTEyeaxisId());
				eyeaxisDao.updateEyeaxisDO(resultEyeaxisDO);
			}
			else
				eyeaxisDao.saveEyeaxisDO(resultEyeaxisDO);
		}
	}
	public void addUpdate(Long studentId,ResultEyepressureDO resultEyepressureDO){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			eyepressureDao.saveEyepressureDO(resultEyepressureDO);
		}else{
			ResultEyepressureDO resultEyepressureDO1=eyepressureDao.getEyepressureDO(studentId, lastCheckTime);
			if(resultEyepressureDO1!=null){
				resultEyepressureDO.setTEyepressureId(resultEyepressureDO1.getTEyepressureId());
				eyepressureDao.updateEyepressureDO(resultEyepressureDO);
			}
			else
				eyepressureDao.saveEyepressureDO(resultEyepressureDO);
		}
	}
	public void addUpdate(Long studentId,ResultAdjustingDO resultAdjustingDO){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			adjustingDao.saveAdjustingDO(resultAdjustingDO);
		}else{
			ResultAdjustingDO resultAdjustingDO1=adjustingDao.getAdjustingDO(studentId, lastCheckTime);
			if(resultAdjustingDO1!=null){
				resultAdjustingDO.setTAdjustingId(resultAdjustingDO1.getTAdjustingId());
				adjustingDao.updateAdjustingDO(resultAdjustingDO);
			}
			else
				adjustingDao.saveAdjustingDO(resultAdjustingDO);
		}
	}
	public void addUpdate(Long studentId,ResultVisibilityDO resultVisibilityDO){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			visibilityDao.saveVisibilityDO(resultVisibilityDO);
		}else{
			ResultVisibilityDO resultVisibilityDO1=visibilityDao.getVisibilityDO(studentId, lastCheckTime);
			if(resultVisibilityDO1!=null){
				resultVisibilityDO.setTVisibilityId(resultVisibilityDO1.getTVisibilityId());
				visibilityDao.updateVisibilityDO(resultVisibilityDO);
			}
			else
				visibilityDao.saveVisibilityDO(resultVisibilityDO);
		}
	}
	public Integer addUpdate(Long studentId,ResultOptometryDO resultOptometryDO){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			optometryDao.saveOptometryDO(resultOptometryDO);
		}else{
			ResultOptometryDO resultOptometryDO1=optometryDao.getOptometryDO(studentId, lastCheckTime);
			if(resultOptometryDO1!=null){
				resultOptometryDO.setTOptometryId(resultOptometryDO1.getTOptometryId());
				optometryDao.updateOptometryDO(resultOptometryDO1);
			}
			else
				optometryDao.saveOptometryDO(resultOptometryDO);
		}
		return resultOptometryDO.getTOptometryId();
	}
	public void addUpdate(Long studentId,List<ResultDiopterDO> diopterDOs){
		if(diopterDOs.size()>0){
			Date lastCheckTime=checkIfSaveResult(studentId);
			if(lastCheckTime==null){
				for(ResultDiopterDO resultDiopterDO:diopterDOs){
					diopterDao.saveDiopterDO(resultDiopterDO);
				}
			}else{
				diopterDao.removeAll(diopterDOs.get(0).getTOptometryId());
				for(ResultDiopterDO resultDiopterDO:diopterDOs){
					diopterDao.saveDiopterDO(resultDiopterDO);
				}
			}
		}
	}
	public void addUpdatec(Long studentId,List<ResultCornealDO> cornealDOs){
		Date lastCheckTime=checkIfSaveResult(studentId);
		if(lastCheckTime==null){
			for(ResultCornealDO resultCornealDO:cornealDOs){
				cornealDao.saveCornealDO(resultCornealDO);
			}
		}else{
			cornealDao.removeAll(cornealDOs.get(0).getTOptometryId());
			for(ResultCornealDO resultCornealDO:cornealDOs){
				cornealDao.saveCornealDO(resultCornealDO);
			}
		}
	}
	
	@Override
	public Map<String, Object> getStudentInfo(String identityCard) {
		Map<String,Object> resultMap  =new HashMap<String,Object>();
		List<StudentDO>  list = studentDao.getStudentInfo(identityCard);
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
		return resultMap;
	}

	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getStudentInfo(Long id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		StudentDO  studentDO = studentDao.get(id);
		if(studentDO!=null){
			Date lastCheckTime=studentDO.getLastCheckTime();
			if(lastCheckTime!=null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lastCheckTime);
				calendar.add(Calendar.DAY_OF_YEAR,13);
				if(calendar.getTime().compareTo(new Date())<0){
					resultMap.put("code",-1);
					resultMap.put("msg","开始新一轮检查...");
				}
				else{//获取上一次的检查数据
					List<ResultDiopterDO> diopterDOs = new ArrayList<ResultDiopterDO>();
					List<ResultCornealDO> cornealDOs = new ArrayList<ResultCornealDO>();
					ResultEyesightDO resultEyesightDO=	eyesightDao.getEyesightDO(id,lastCheckTime);
					if(resultEyesightDO==null) resultEyesightDO=new ResultEyesightDO();
					ResultEyeaxisDO resultEyeaxisDO=  eyeaxisDao.getEyeaxisDO(id,lastCheckTime);
					if(resultEyeaxisDO==null) resultEyeaxisDO=new ResultEyeaxisDO();
					ResultEyepressureDO resultEyepressureDO=eyepressureDao.getEyepressureDO(id,lastCheckTime);
					if(resultEyepressureDO==null) resultEyepressureDO=new ResultEyepressureDO();
					ResultAdjustingDO resultAdjustingDO=adjustingDao.getAdjustingDO(id,lastCheckTime);
					if(resultAdjustingDO==null) resultAdjustingDO=new ResultAdjustingDO();
					ResultVisibilityDO resultVisibilityDO=visibilityDao.getVisibilityDO(id,lastCheckTime);
					if(resultVisibilityDO==null) resultVisibilityDO = new ResultVisibilityDO();
					ResultOptometryDO resultOptometryDO=optometryDao.getOptometryDO(id,lastCheckTime);
					if(resultOptometryDO==null) resultOptometryDO=new ResultOptometryDO();
					if(resultOptometryDO!=null){
						diopterDOs = diopterDao.getByOptometryId(resultOptometryDO.getTOptometryId());
						cornealDOs=cornealDao.getByOptometryId(resultOptometryDO.getTOptometryId());
					}
					resultMap.put("resultEyesightDO", resultEyesightDO);
					resultMap.put("resultEyeaxisDO", resultEyeaxisDO);
					resultMap.put("resultEyepressureDO", resultEyepressureDO);
					
					resultMap.put("resultAdjustingDO", resultAdjustingDO);
					resultMap.put("resultVisibilityDO", resultVisibilityDO);
					resultMap.put("resultOptometryDO", resultOptometryDO);
					
					resultMap.put("diopterDOs", diopterDOs);
					resultMap.put("cornealDOs", cornealDOs);
					resultMap.put("code",0);
					resultMap.put("msg","数据获取成功...");
				}
			}
			else{
				resultMap.put("code",-1);
				resultMap.put("msg","开始新一轮检查...");
			}
		}
		return resultMap;
	}	
}
