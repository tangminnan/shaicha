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
	private Integer eyePressureOd;
	//左眼眼内压
	private Integer eyePressureOs;
	//检查日期
	private Date checkDate;
	//状态0：正常1：禁止
	private Integer deleteFlag;

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
	public void setEyePressureOd(Integer eyePressureOd) {
		this.eyePressureOd = eyePressureOd;
	}
	/**
	 * 获取：右眼眼内压
	 */
	public Integer getEyePressureOd() {
		return eyePressureOd;
	}
	/**
	 * 设置：左眼眼内压
	 */
	public void setEyePressureOs(Integer eyePressureOs) {
		this.eyePressureOs = eyePressureOs;
	}
	/**
	 * 获取：左眼眼内压
	 */
	public Integer getEyePressureOs() {
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
	public ResultEyepressureDO(Long studentId, Long checkorId, Integer eyePressureOd, Integer eyePressureOs,
			Date checkDate, Integer deleteFlag) {
		super();
		this.studentId = studentId;
		this.checkorId = checkorId;
		this.eyePressureOd = eyePressureOd;
		this.eyePressureOs = eyePressureOs;
		this.checkDate = checkDate;
		this.deleteFlag = deleteFlag;
	}
	
}

