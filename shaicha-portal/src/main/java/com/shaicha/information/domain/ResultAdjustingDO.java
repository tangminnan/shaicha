package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 调节灵敏度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
public class ResultAdjustingDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tAdjustingId;
	//学生id
	private Long studentId;
	//调节灵敏度检查者id
	private Long checkorId;
	//右眼调节灵敏度
	private Double adjustingOd;
	//左眼调节灵敏度
	private Double adjustingOs;
	//双眼调节灵敏度
	private Double adjustingOu;
	//右眼调节灵敏度调整 0加  1减
	private Integer jjOd;
	//左眼调节灵敏度调整 0加  1减
	private Integer jjOs;
	//双眼调节灵敏度调整 0 加  1减
	private Integer jjOu;
	//检查日期
	private Date checkDate;
	//状态0：正常1：禁止
	private Integer deleteFlag;

	/**
	 * 设置：id
	 */
	public void setTAdjustingId(Integer tAdjustingId) {
		this.tAdjustingId = tAdjustingId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTAdjustingId() {
		return tAdjustingId;
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
	 * 设置：调节灵敏度检查者id
	 */
	public void setCheckorId(Long checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：调节灵敏度检查者id
	 */
	public Long getCheckorId() {
		return checkorId;
	}
	/**
	 * 设置：右眼调节灵敏度
	 */
	public void setAdjustingOd(Double adjustingOd) {
		this.adjustingOd = adjustingOd;
	}
	/**
	 * 获取：右眼调节灵敏度
	 */
	public Double getAdjustingOd() {
		return adjustingOd;
	}
	/**
	 * 设置：左眼调节灵敏度
	 */
	public void setAdjustingOs(Double adjustingOs) {
		this.adjustingOs = adjustingOs;
	}
	/**
	 * 获取：左眼调节灵敏度
	 */
	public Double getAdjustingOs() {
		return adjustingOs;
	}
	/**
	 * 设置：双眼调节灵敏度
	 */
	public void setAdjustingOu(Double adjustingOu) {
		this.adjustingOu = adjustingOu;
	}
	/**
	 * 获取：双眼调节灵敏度
	 */
	public Double getAdjustingOu() {
		return adjustingOu;
	}
	/**
	 * 设置：右眼调节灵敏度调整 0加  1减
	 */
	public void setJjOd(Integer jjOd) {
		this.jjOd = jjOd;
	}
	/**
	 * 获取：右眼调节灵敏度调整 0加  1减
	 */
	public Integer getJjOd() {
		return jjOd;
	}
	/**
	 * 设置：左眼调节灵敏度调整 0加  1减
	 */
	public void setJjOs(Integer jjOs) {
		this.jjOs = jjOs;
	}
	/**
	 * 获取：左眼调节灵敏度调整 0加  1减
	 */
	public Integer getJjOs() {
		return jjOs;
	}
	/**
	 * 设置：双眼调节灵敏度调整 0 加  1减
	 */
	public void setJjOu(Integer jjOu) {
		this.jjOu = jjOu;
	}
	/**
	 * 获取：双眼调节灵敏度调整 0 加  1减
	 */
	public Integer getJjOu() {
		return jjOu;
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
	public ResultAdjustingDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultAdjustingDO(Long studentId, Long checkorId, Double adjustingOd, Double adjustingOs, Double adjustingOu,
			Integer jjOd, Integer jjOs, Integer jjOu, Date checkDate, Integer deleteFlag) {
		super();
		this.studentId = studentId;
		this.checkorId = checkorId;
		this.adjustingOd = adjustingOd;
		this.adjustingOs = adjustingOs;
		this.adjustingOu = adjustingOu;
		this.jjOd = jjOd;
		this.jjOs = jjOs;
		this.jjOu = jjOu;
		this.checkDate = checkDate;
		this.deleteFlag = deleteFlag;
	}
	
	
}
