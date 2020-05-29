package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 眼内压
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
public class ResultEyepressureDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tEyepressureId;
	//学生id
	private Long studentId;
	//眼内压检查者id
	private Long checkorId;
	//右眼眼内压
	private Double eyePressureOd;
	//左眼眼内压
	private Double eyePressureOs;
	//检查日期
	private Date checkDate;
	//状态0：正常1：禁止
	private Integer deleteFlag;
	//身份证号
	private String identityCard;
	private Integer activityId;
	//角膜曲率（右）
	private String cornealCurvatureOd;
	//角膜曲率（左）
	private String cornealCurvatureOs;
	
	
	
	
	public Integer gettEyepressureId() {
		return tEyepressureId;
	}
	public void settEyepressureId(Integer tEyepressureId) {
		this.tEyepressureId = tEyepressureId;
	}
	public String getCornealCurvatureOd() {
		return cornealCurvatureOd;
	}
	public void setCornealCurvatureOd(String cornealCurvatureOd) {
		this.cornealCurvatureOd = cornealCurvatureOd;
	}
	public String getCornealCurvatureOs() {
		return cornealCurvatureOs;
	}
	public void setCornealCurvatureOs(String cornealCurvatureOs) {
		this.cornealCurvatureOs = cornealCurvatureOs;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * 设置：id
	 */
	public void setTEyepressureId(Integer tEyepressureId) {
		this.tEyepressureId = tEyepressureId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTEyepressureId() {
		return tEyepressureId;
	}
	/**
	 * 设置：学生id
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：学生id
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * 设置：眼内压检查者id
	 */
	public void setCheckorId(Long checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：眼内压检查者id
	 */
	public Long getCheckorId() {
		return checkorId;
	}
	/**
	 * 设置：右眼眼内压
	 */
	public void setEyePressureOd(Double eyePressureOd) {
		this.eyePressureOd = eyePressureOd;
	}
	/**
	 * 获取：右眼眼内压
	 */
	public Double getEyePressureOd() {
		return eyePressureOd;
	}
	/**
	 * 设置：左眼眼内压
	 */
	public void setEyePressureOs(Double eyePressureOs) {
		this.eyePressureOs = eyePressureOs;
	}
	/**
	 * 获取：左眼眼内压
	 */
	public Double getEyePressureOs() {
		return eyePressureOs;
	}
	/**
	 * 设置：检查日期
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * 获取：检查日期
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * 设置：状态0：正常1：禁止
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * 获取：状态0：正常1：禁止
	 */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public ResultEyepressureDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultEyepressureDO(Long studentId, Long checkorId, Double eyePressureOd, Double eyePressureOs,
			Date checkDate, Integer deleteFlag,String identityCard,Integer activityId,String cornealCurvatureOd,String cornealCurvatureOs) {
		super();
		this.studentId = studentId;
		this.checkorId = checkorId;
		this.eyePressureOd = eyePressureOd;
		this.eyePressureOs = eyePressureOs;
		this.checkDate = checkDate;
		this.deleteFlag = deleteFlag;
		this.identityCard=identityCard;
		this.activityId=activityId;
		this.cornealCurvatureOd=cornealCurvatureOd;
		this.cornealCurvatureOs=cornealCurvatureOs;
	}
	
}

