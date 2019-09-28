package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 眼轴长度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
public class ResultEyeaxisDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tEyeaxisId;
	//学生id
	private Long studentId;
	//视力检查者id
	private Long checkorId;
	//右眼第一次检查结果
	private Double firstCheckOd;
	//左眼第一次检查结果
	private Double firstCheckOs;
	//检查日期
	private Date checkDate;
	//右眼第二次检查结果
	private Double secondCheckOd;
	//左眼第二次检查结果
	private Double secondCheckOs;
	//状态0：正常1：禁止
	private Integer deleteFlag;

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
	public Long getCheckorId() {
		return checkorId;
	}
	public void setCheckorId(Long checkorId) {
		this.checkorId = checkorId;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public ResultEyeaxisDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultEyeaxisDO(Long studentId, Long checkorId, Double firstCheckOd, Double firstCheckOs, Date checkDate,
			Double secondCheckOd, Double secondCheckOs, Integer deleteFlag) {
		super();
		this.studentId = studentId;
		this.checkorId = checkorId;
		this.firstCheckOd = firstCheckOd;
		this.firstCheckOs = firstCheckOs;
		this.checkDate = checkDate;
		this.secondCheckOd = secondCheckOd;
		this.secondCheckOs = secondCheckOs;
		this.deleteFlag = deleteFlag;
	}
	
	
}
