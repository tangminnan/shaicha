package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 视力检查
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
public class ResultEyesightDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tEyesightId;
	//学生id
	private Long studentId;
	//视力检查者id
	private Long checkorId;
	//右眼生活远视力
	private String lifeFarvisionOd;
	//左眼生活远视力
	private String lifeFarvisionOs;
	//右眼裸眼远视力
	private String nakedFarvisionOd;
	//左眼裸眼远视力
	private String nakedFarvisionOs;
	//右眼矫正远视力
	private String correctionFarvisionOd;
	//左眼矫正远视力
	private String correctionFarvisionOs;
	//右眼生活近视力
	private String lifeNearvisionOd;
	//左眼生活近视力
	private String lifeNearvisionOs;
	//右眼裸眼近视力
	private String nakedNearvisionOd;
	//左眼裸眼近视力
	private String nakedNearvisionOs;
	
	//检查日期
	private Date checkDate;
	//状态0：正常1：禁止
	private Integer deleteFlag;
	private String identityCard;
	//戴镜视力右
	private String glassvisionOd;
				   
	//戴镜视力左
	private String glassvisionOs;
	
	private Integer activityId;
	
	
	
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
	public void setTEyesightId(Integer tEyesightId) {
		this.tEyesightId = tEyesightId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTEyesightId() {
		return tEyesightId;
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
	 * 设置：视力检查者id
	 */
	public void setCheckorId(Long checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：视力检查者id
	 */
	public Long getCheckorId() {
		return checkorId;
	}
	/**
	 * 设置：右眼生活远视力
	 */
	public void setLifeFarvisionOd(String lifeFarvisionOd) {
		this.lifeFarvisionOd = lifeFarvisionOd;
	}
	/**
	 * 获取：右眼生活远视力
	 */
	public String getLifeFarvisionOd() {
		return lifeFarvisionOd;
	}
	/**
	 * 设置：左眼生活远视力
	 */
	public void setLifeFarvisionOs(String lifeFarvisionOs) {
		this.lifeFarvisionOs = lifeFarvisionOs;
	}
	/**
	 * 获取：左眼生活远视力
	 */
	public String getLifeFarvisionOs() {
		return lifeFarvisionOs;
	}
	/**
	 * 设置：右眼裸眼远视力
	 */
	public void setNakedFarvisionOd(String nakedFarvisionOd) {
		this.nakedFarvisionOd = nakedFarvisionOd;
	}
	/**
	 * 获取：右眼裸眼远视力
	 */
	public String getNakedFarvisionOd() {
		return nakedFarvisionOd;
	}
	/**
	 * 设置：左眼裸眼远视力
	 */
	public void setNakedFarvisionOs(String nakedFarvisionOs) {
		this.nakedFarvisionOs = nakedFarvisionOs;
	}
	/**
	 * 获取：左眼裸眼远视力
	 */
	public String getNakedFarvisionOs() {
		return nakedFarvisionOs;
	}
	/**
	 * 设置：右眼矫正远视力
	 */
	public void setCorrectionFarvisionOd(String correctionFarvisionOd) {
		this.correctionFarvisionOd = correctionFarvisionOd;
	}
	/**
	 * 获取：右眼矫正远视力
	 */
	public String getCorrectionFarvisionOd() {
		return correctionFarvisionOd;
	}
	/**
	 * 设置：左眼矫正远视力
	 */
	public void setCorrectionFarvisionOs(String correctionFarvisionOs) {
		this.correctionFarvisionOs = correctionFarvisionOs;
	}
	/**
	 * 获取：左眼矫正远视力
	 */
	public String getCorrectionFarvisionOs() {
		return correctionFarvisionOs;
	}
	/**
	 * 设置：右眼生活近视力
	 */
	public void setLifeNearvisionOd(String lifeNearvisionOd) {
		this.lifeNearvisionOd = lifeNearvisionOd;
	}
	/**
	 * 获取：右眼生活近视力
	 */
	public String getLifeNearvisionOd() {
		return lifeNearvisionOd;
	}
	/**
	 * 设置：左眼生活近视力
	 */
	public void setLifeNearvisionOs(String lifeNearvisionOs) {
		this.lifeNearvisionOs = lifeNearvisionOs;
	}
	/**
	 * 获取：左眼生活近视力
	 */
	public String getLifeNearvisionOs() {
		return lifeNearvisionOs;
	}
	/**
	 * 设置：右眼裸眼近视力
	 */
	public void setNakedNearvisionOd(String nakedNearvisionOd) {
		this.nakedNearvisionOd = nakedNearvisionOd;
	}
	/**
	 * 获取：右眼裸眼近视力
	 */
	public String getNakedNearvisionOd() {
		return nakedNearvisionOd;
	}
	/**
	 * 设置：左眼裸眼近视力
	 */
	public void setNakedNearvisionOs(String nakedNearvisionOs) {
		this.nakedNearvisionOs = nakedNearvisionOs;
	}
	/**
	 * 获取：左眼裸眼近视力
	 */
	public String getNakedNearvisionOs() {
		return nakedNearvisionOs;
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
	
	
	public String getGlassvisionOd() {
		return glassvisionOd;
	}
	public void setGlassvisionOd(String glassvisionOd) {
		this.glassvisionOd = glassvisionOd;
	}
	public String getGlassvisionOs() {
		return glassvisionOs;
	}
	public void setGlassvisionOs(String glassvisionOs) {
		this.glassvisionOs = glassvisionOs;
	}
	public ResultEyesightDO(Long studentId, Long checkorId, //String lifeFarvisionOd, String lifeFarvisionOs,
			String nakedFarvisionOd, String nakedFarvisionOs, String correctionFarvisionOd,
			String correctionFarvisionOs, 
			//String lifeNearvisionOd, String lifeNearvisionOs, String nakedNearvisionOd,String nakedNearvisionOs, 
			Date checkDate, Integer deleteFlag,String identityCard,Integer activityId) {
		super();
		this.studentId = studentId;
		this.checkorId = checkorId;
		//this.lifeFarvisionOd = lifeFarvisionOd;
		//this.lifeFarvisionOs = lifeFarvisionOs;
		this.nakedFarvisionOd = nakedFarvisionOd;
		this.nakedFarvisionOs = nakedFarvisionOs;
		this.correctionFarvisionOd = correctionFarvisionOd;
		this.correctionFarvisionOs = correctionFarvisionOs;
		//this.lifeNearvisionOd = lifeNearvisionOd;
		//this.lifeNearvisionOs = lifeNearvisionOs;
		//this.nakedNearvisionOd = nakedNearvisionOd;
		//this.nakedNearvisionOs = nakedNearvisionOs;
		this.checkDate = checkDate;
		this.deleteFlag = deleteFlag;
		this.identityCard=identityCard;
		this.activityId=activityId;
	}
	public ResultEyesightDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
