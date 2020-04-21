package com.shaicha.informationNEW.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public class ResultVisibilityNewDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tVisibilityId;
	//学生id
	private Integer studentId;
	//视功能检查者id
	private Integer checkorId;
	//立体视value 1=xx弧秒2=立体盲3=不配合4=其他
	private Integer stereoscopicViewingValue;
	//立体视备注
	private String stereoscopicViewingDis;
	//右眼调节幅度
	private Double adjustmentRangeOd;
	//左眼调节幅度
	private Double adjustmentRangeOc;
	//双眼调节幅度
	private Double adjustmentRangeOu;
	//右眼集合近点
	private Double gatherNearOd;
	//左眼集合近点
	private Double gatherNearOc;
	//双眼集合近点
	private Double gatherNearOu;
	//隐斜量value   EXO  ECO
	private String obliqueValue;
	//隐斜量值
	private Double obliqueDis;
	//右眼前后节value 0=正常   1=其他（备注）
	private Integer beforeAfterOdValue;
	//右眼前后节text
	private String beforeAfterOdDis;
	//左眼前后节value 0=正常   1=其他
	private Integer beforeAfterOsValue;
	//左眼前后节text
	private String beforeAfterOsDis;
	//检查日期
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date checkDate;
	//状态0：正常1：禁止
	private Integer deleteFlag;
	private String identityCard;
	private Date endTime;
	private Date startTime;
	
	private Integer activityId;
	
	
	
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public Integer gettVisibilityId() {
		return tVisibilityId;
	}
	public void settVisibilityId(Integer tVisibilityId) {
		this.tVisibilityId = tVisibilityId;
	}
	/**
	 * 设置：id
	 */
	public void setTVisibilityId(Integer tVisibilityId) {
		this.tVisibilityId = tVisibilityId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTVisibilityId() {
		return tVisibilityId;
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
	 * 设置：视功能检查者id
	 */
	public void setCheckorId(Integer checkorId) {
		this.checkorId = checkorId;
	}
	/**
	 * 获取：视功能检查者id
	 */
	public Integer getCheckorId() {
		return checkorId;
	}
	/**
	 * 设置：立体视value 
1=xx弧秒
2=立体盲
3=不配合
4=其他
	 */
	public void setStereoscopicViewingValue(Integer stereoscopicViewingValue) {
		this.stereoscopicViewingValue = stereoscopicViewingValue;
	}
	/**
	 * 获取：立体视value 
1=xx弧秒
2=立体盲
3=不配合
4=其他
	 */
	public Integer getStereoscopicViewingValue() {
		return stereoscopicViewingValue;
	}
	/**
	 * 设置：立体视备注
	 */
	public void setStereoscopicViewingDis(String stereoscopicViewingDis) {
		this.stereoscopicViewingDis = stereoscopicViewingDis;
	}
	/**
	 * 获取：立体视备注
	 */
	public String getStereoscopicViewingDis() {
		return stereoscopicViewingDis;
	}
	/**
	 * 设置：右眼调节幅度
	 */
	public void setAdjustmentRangeOd(Double adjustmentRangeOd) {
		this.adjustmentRangeOd = adjustmentRangeOd;
	}
	/**
	 * 获取：右眼调节幅度
	 */
	public Double getAdjustmentRangeOd() {
		return adjustmentRangeOd;
	}
	/**
	 * 设置：左眼调节幅度
	 */
	public void setAdjustmentRangeOc(Double adjustmentRangeOc) {
		this.adjustmentRangeOc = adjustmentRangeOc;
	}
	/**
	 * 获取：左眼调节幅度
	 */
	public Double getAdjustmentRangeOc() {
		return adjustmentRangeOc;
	}
	/**
	 * 设置：双眼调节幅度
	 */
	public void setAdjustmentRangeOu(Double adjustmentRangeOu) {
		this.adjustmentRangeOu = adjustmentRangeOu;
	}
	/**
	 * 获取：双眼调节幅度
	 */
	public Double getAdjustmentRangeOu() {
		return adjustmentRangeOu;
	}
	/**
	 * 设置：右眼集合近点
	 */
	public void setGatherNearOd(Double gatherNearOd) {
		this.gatherNearOd = gatherNearOd;
	}
	/**
	 * 获取：右眼集合近点
	 */
	public Double getGatherNearOd() {
		return gatherNearOd;
	}
	/**
	 * 设置：左眼集合近点
	 */
	public void setGatherNearOc(Double gatherNearOc) {
		this.gatherNearOc = gatherNearOc;
	}
	/**
	 * 获取：左眼集合近点
	 */
	public Double getGatherNearOc() {
		return gatherNearOc;
	}
	/**
	 * 设置：双眼集合近点
	 */
	public void setGatherNearOu(Double gatherNearOu) {
		this.gatherNearOu = gatherNearOu;
	}
	/**
	 * 获取：双眼集合近点
	 */
	public Double getGatherNearOu() {
		return gatherNearOu;
	}
	/**
	 * 设置：隐斜量value   EXO  ECO
	 */
	public void setObliqueValue(String obliqueValue) {
		this.obliqueValue = obliqueValue;
	}
	/**
	 * 获取：隐斜量value   EXO  ECO
	 */
	public String getObliqueValue() {
		return obliqueValue;
	}
	/**
	 * 设置：隐斜量值
	 */
	public void setObliqueDis(Double obliqueDis) {
		this.obliqueDis = obliqueDis;
	}
	/**
	 * 获取：隐斜量值
	 */
	public Double getObliqueDis() {
		return obliqueDis;
	}
	/**
	 * 设置：右眼前后节value 0=正常   1=其他（备注）
	 */
	public void setBeforeAfterOdValue(Integer beforeAfterOdValue) {
		this.beforeAfterOdValue = beforeAfterOdValue;
	}
	/**
	 * 获取：右眼前后节value 0=正常   1=其他（备注）
	 */
	public Integer getBeforeAfterOdValue() {
		return beforeAfterOdValue;
	}
	/**
	 * 设置：右眼前后节text
	 */
	public void setBeforeAfterOdDis(String beforeAfterOdDis) {
		this.beforeAfterOdDis = beforeAfterOdDis;
	}
	/**
	 * 获取：右眼前后节text
	 */
	public String getBeforeAfterOdDis() {
		return beforeAfterOdDis;
	}
	/**
	 * 设置：左眼前后节value 0=正常   1=其他
	 */
	public void setBeforeAfterOsValue(Integer beforeAfterOsValue) {
		this.beforeAfterOsValue = beforeAfterOsValue;
	}
	/**
	 * 获取：左眼前后节value 0=正常   1=其他
	 */
	public Integer getBeforeAfterOsValue() {
		return beforeAfterOsValue;
	}
	/**
	 * 设置：左眼前后节text
	 */
	public void setBeforeAfterOsDis(String beforeAfterOsDis) {
		this.beforeAfterOsDis = beforeAfterOsDis;
	}
	/**
	 * 获取：左眼前后节text
	 */
	public String getBeforeAfterOsDis() {
		return beforeAfterOsDis;
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
}
