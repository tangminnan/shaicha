package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 视力检查
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public class ResultEyesightDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tEyesightId;
	//学生id
	private Integer studentId;
	//视力检查者id
	private Integer checkorId;
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
	private String glassOd;//左眼眼镜度数
	private String glassOs;//右眼眼镜度数
	private String qita;//其他
	//学校
	private String school;
	//年级
	private String grade;
	//性别 1：男2：女0：未知
	private Integer studentSex;
	//学部
	private String xueBu;
	//检查日期
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date checkDate;
	private String checkDate1;
	//状态0：正常1：禁止
	private Integer deleteFlag;
	private Date endTime;
	private Date startTime;
	
	private Date minCheckDate;
	private Date maxCheckDate;
	//检查类型   示范校筛查    普通筛查
	private String checkType;
	private Integer activityId;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Date getMinCheckDate() {
		return minCheckDate;
	}
	public void setMinCheckDate(Date minCheckDate) {
		this.minCheckDate = minCheckDate;
	}
	public Date getMaxCheckDate() {
		return maxCheckDate;
	}
	public void setMaxCheckDate(Date maxCheckDate) {
		this.maxCheckDate = maxCheckDate;
	}
	//戴镜视力右
	private String glassvisionOd;
	//身份证号
	private String identityCard;
					   
		//戴镜视力左
	private String glassvisionOs;
	//等效球镜
	private Double dengxiaoqiujing;
	
	private String dushu;
	
	public String getDushu() {
		return dushu;
	}
	public void setDushu(String dushu) {
		this.dushu = dushu;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	


	public String getCheckDate1() {
		return checkDate1;
	}
	public void setCheckDate1(String checkDate1) {
		this.checkDate1 = checkDate1;
	}
	public Integer gettEyesightId() {
		return tEyesightId;
	}
	public void settEyesightId(Integer tEyesightId) {
		this.tEyesightId = tEyesightId;
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
	 * 设置：视力检查者id
	 */
	public void setCheckorId(Integer checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：视力检查者id
	 */
	public Integer getCheckorId() {
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
	public ResultEyesightDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultEyesightDO(String nakedFarvisionOd, String nakedFarvisionOs, String correctionFarvisionOd,
			String correctionFarvisionOs
			) {
		
		this.nakedFarvisionOd = nakedFarvisionOd;
		this.nakedFarvisionOs = nakedFarvisionOs;
		this.correctionFarvisionOd = correctionFarvisionOd;
		this.correctionFarvisionOs = correctionFarvisionOs;
		
	}

	public String getGlassOd() {
		return glassOd;
	}

	public void setGlassOd(String glassOd) {
		this.glassOd = glassOd;
	}

	public String getGlassOs() {
		return glassOs;
	}

	public void setGlassOs(String glassOs) {
		this.glassOs = glassOs;
	}

	public String getQita() {
		return qita;
	}

	public void setQita(String qita) {
		this.qita = qita;
	}

	public String getGlassvisionOd() {
		return glassvisionOd;
	}
	public void setGlassvisionOd(String glassvisionOd) {
		this.glassvisionOd = glassvisionOd;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getGlassvisionOs() {
		return glassvisionOs;
	}
	public void setGlassvisionOs(String glassvisionOs) {
		this.glassvisionOs = glassvisionOs;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Integer getStudentSex() {
		return studentSex;
	}
	public void setStudentSex(Integer studentSex) {
		this.studentSex = studentSex;
	}
	public String getXueBu() {
		return xueBu;
	}
	public void setXueBu(String xueBu) {
		this.xueBu = xueBu;
	}
	public Double getDengxiaoqiujing() {
		return dengxiaoqiujing;
	}
	public void setDengxiaoqiujing(Double dengxiaoqiujing) {
		this.dengxiaoqiujing = dengxiaoqiujing;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	@Override
	public String toString() {
		return "ResultEyesightDO [tEyesightId=" + tEyesightId + ", studentId=" + studentId + ", checkorId=" + checkorId
				+ ", lifeFarvisionOd=" + lifeFarvisionOd + ", lifeFarvisionOs=" + lifeFarvisionOs
				+ ", nakedFarvisionOd=" + nakedFarvisionOd + ", nakedFarvisionOs=" + nakedFarvisionOs
				+ ", correctionFarvisionOd=" + correctionFarvisionOd + ", correctionFarvisionOs="
				+ correctionFarvisionOs + ", lifeNearvisionOd=" + lifeNearvisionOd + ", lifeNearvisionOs="
				+ lifeNearvisionOs + ", nakedNearvisionOd=" + nakedNearvisionOd + ", nakedNearvisionOs="
				+ nakedNearvisionOs + ", school=" + school + ", grade=" + grade + ", studentSex=" + studentSex
				+ ", xueBu=" + xueBu + ", checkDate=" + checkDate + ", checkDate1=" + checkDate1 + ", deleteFlag="
				+ deleteFlag + ", endTime=" + endTime + ", startTime=" + startTime + ", minCheckDate=" + minCheckDate
				+ ", maxCheckDate=" + maxCheckDate + ", checkType=" + checkType + ", glassvisionOd=" + glassvisionOd
				+ ", identityCard=" + identityCard + ", glassvisionOs=" + glassvisionOs + ", dengxiaoqiujing="
				+ dengxiaoqiujing + ", dushu=" + dushu + "]";
	}
	
	
}
