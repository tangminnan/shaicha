package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 眼轴长度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public class ResultEyeaxisDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tEyeaxisId;
	//学生id
	private Integer studentId;
	//第一次视力检查者id
	private Integer checkorId;
	//右眼第一次检查结果
	private Double firstCheckOd;
	//左眼第一次检查结果
	private Double firstCheckOs;
	//第一次检查日期
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date checkDate;
	//右眼第二次检查结果
	private Double secondCheckOd;
	//左眼第二次检查结果
	private Double secondCheckOs;
	//状态0：正常1：禁止
	private Integer deleteFlag;
	//身份证号
	private String identityCard;
	

	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public Integer gettEyeaxisId() {
		return tEyeaxisId;
	}
	public void settEyeaxisId(Integer tEyeaxisId) {
		this.tEyeaxisId = tEyeaxisId;
	}
	/**
	 * 设置：id
	 */
	public void setTEyeaxisId(Integer tEyeaxisId) {
		this.tEyeaxisId = tEyeaxisId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTEyeaxisId() {
		return tEyeaxisId;
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
	 * 设置：第一次视力检查者id
	 */
	public void setCheckorId(Integer checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：第一次视力检查者id
	 */
	public Integer getCheckorId() {
		return checkorId;
	}
	/**
	 * 设置：右眼第一次检查结果
	 */
	public void setFirstCheckOd(Double firstCheckOd) {
		this.firstCheckOd = firstCheckOd;
	}
	/**
	 * 获取：右眼第一次检查结果
	 */
	public Double getFirstCheckOd() {
		return firstCheckOd;
	}
	/**
	 * 设置：左眼第一次检查结果
	 */
	public void setFirstCheckOs(Double firstCheckOs) {
		this.firstCheckOs = firstCheckOs;
	}
	/**
	 * 获取：左眼第一次检查结果
	 */
	public Double getFirstCheckOs() {
		return firstCheckOs;
	}
	/**
	 * 设置：第一次检查日期
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * 获取：第一次检查日期
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * 设置：右眼第二次检查结果
	 */
	public void setSecondCheckOd(Double secondCheckOd) {
		this.secondCheckOd = secondCheckOd;
	}
	/**
	 * 获取：右眼第二次检查结果
	 */
	public Double getSecondCheckOd() {
		return secondCheckOd;
	}
	/**
	 * 设置：左眼第二次检查结果
	 */
	public void setSecondCheckOs(Double secondCheckOs) {
		this.secondCheckOs = secondCheckOs;
	}
	/**
	 * 获取：左眼第二次检查结果
	 */
	public Double getSecondCheckOs() {
		return secondCheckOs;
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
