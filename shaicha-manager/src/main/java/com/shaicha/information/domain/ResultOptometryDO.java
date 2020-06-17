package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 验光数据表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public class ResultOptometryDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tOptometryId;
	//学生id
	private Integer studentId;
	//第一次电脑验光检查者id
	private Integer checkorId;
	//第一次验光VD
	private Double firstCheckVd;
	//第一次检查右眼ps
	private Double firstCheckRps;
	//第一次检查左眼ps
	private Double firstCheckLps;
	//第一次检查右眼cs
	private Double firstCheckRcs;
	//第一次检查左眼cs
	private Double firstCheckLcs;
	//第一次定脑验光日期
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date checkDate;
	private String checkDate1;
	//第二次验光VD
	private Double secondCheckVd;
	//第一次检查右眼ps
	private Double secondCheckRps;
	//第一次检查左眼ps
	private Double secondCheckLps;
	//第一次检查右眼cs
	private Double secondCheckRcs;
	//第一次检查左眼cs
	private Double secondCheckLcs;
	//状态0：正常1：禁止
	private Integer deleteFlag;
	//第一次验光PD
	private Double firstCheckPd;
	//第二次验光PD
	private Double secondCheckPd;

	private String identityCard;
	private String activityId;
	private String RelationPatientCrfId;
	
	
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getRelationPatientCrfId() {
		return RelationPatientCrfId;
	}
	public void setRelationPatientCrfId(String relationPatientCrfId) {
		RelationPatientCrfId = relationPatientCrfId;
	}
	public String getCheckDate1() {
		return checkDate1;
	}
	public void setCheckDate1(String checkDate1) {
		this.checkDate1 = checkDate1;
	}
	public Double getFirstCheckPd() {
		return firstCheckPd;
	}
	public void setFirstCheckPd(Double firstCheckPd) {
		this.firstCheckPd = firstCheckPd;
	}
	public Double getSecondCheckPd() {
		return secondCheckPd;
	}
	public void setSecondCheckPd(Double secondCheckPd) {
		this.secondCheckPd = secondCheckPd;
	}
	
	public Integer gettOptometryId() {
		return tOptometryId;
	}
	public void settOptometryId(Integer tOptometryId) {
		this.tOptometryId = tOptometryId;
	}
	/**
	 * 设置：id
	 */
	public void setTOptometryId(Integer tOptometryId) {
		this.tOptometryId = tOptometryId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTOptometryId() {
		return tOptometryId;
	}
	/**
	 * 设置：学生id
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：学生id
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * 设置：第一次电脑验光检查者id
	 */
	public void setCheckorId(Integer checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：第一次电脑验光检查者id
	 */
	public Integer getCheckorId() {
		return checkorId;
	}
	/**
	 * 设置：第一次验光VD
	 */
	public void setFirstCheckVd(Double firstCheckVd) {
		this.firstCheckVd = firstCheckVd;
	}
	/**
	 * 获取：第一次验光VD
	 */
	public Double getFirstCheckVd() {
		return firstCheckVd;
	}
	/**
	 * 设置：第一次检查右眼ps
	 */
	public void setFirstCheckRps(Double firstCheckRps) {
		this.firstCheckRps = firstCheckRps;
	}
	/**
	 * 获取：第一次检查右眼ps
	 */
	public Double getFirstCheckRps() {
		return firstCheckRps;
	}
	/**
	 * 设置：第一次检查左眼ps
	 */
	public void setFirstCheckLps(Double firstCheckLps) {
		this.firstCheckLps = firstCheckLps;
	}
	/**
	 * 获取：第一次检查左眼ps
	 */
	public Double getFirstCheckLps() {
		return firstCheckLps;
	}
	/**
	 * 设置：第一次检查右眼cs
	 */
	public void setFirstCheckRcs(Double firstCheckRcs) {
		this.firstCheckRcs = firstCheckRcs;
	}
	/**
	 * 获取：第一次检查右眼cs
	 */
	public Double getFirstCheckRcs() {
		return firstCheckRcs;
	}
	/**
	 * 设置：第一次检查左眼cs
	 */
	public void setFirstCheckLcs(Double firstCheckLcs) {
		this.firstCheckLcs = firstCheckLcs;
	}
	/**
	 * 获取：第一次检查左眼cs
	 */
	public Double getFirstCheckLcs() {
		return firstCheckLcs;
	}
	/**
	 * 设置：第一次定脑验光日期
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * 获取：第一次定脑验光日期
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * 设置：第二次验光VD
	 */
	public void setSecondCheckVd(Double secondCheckVd) {
		this.secondCheckVd = secondCheckVd;
	}
	/**
	 * 获取：第二次验光VD
	 */
	public Double getSecondCheckVd() {
		return secondCheckVd;
	}
	/**
	 * 设置：第一次检查右眼ps
	 */
	public void setSecondCheckRps(Double secondCheckRps) {
		this.secondCheckRps = secondCheckRps;
	}
	/**
	 * 获取：第一次检查右眼ps
	 */
	public Double getSecondCheckRps() {
		return secondCheckRps;
	}
	/**
	 * 设置：第一次检查左眼ps
	 */
	public void setSecondCheckLps(Double secondCheckLps) {
		this.secondCheckLps = secondCheckLps;
	}
	/**
	 * 获取：第一次检查左眼ps
	 */
	public Double getSecondCheckLps() {
		return secondCheckLps;
	}
	/**
	 * 设置：第一次检查右眼cs
	 */
	public void setSecondCheckRcs(Double secondCheckRcs) {
		this.secondCheckRcs = secondCheckRcs;
	}
	/**
	 * 获取：第一次检查右眼cs
	 */
	public Double getSecondCheckRcs() {
		return secondCheckRcs;
	}
	/**
	 * 设置：第一次检查左眼cs
	 */
	public void setSecondCheckLcs(Double secondCheckLcs) {
		this.secondCheckLcs = secondCheckLcs;
	}
	/**
	 * 获取：第一次检查左眼cs
	 */
	public Double getSecondCheckLcs() {
		return secondCheckLcs;
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
}
