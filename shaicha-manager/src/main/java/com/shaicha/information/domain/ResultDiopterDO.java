package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 曲光度详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public class ResultDiopterDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tDiopterId;
	//
	private Integer tOptometryId;
	//右眼球镜（近、远视度数）
	private Double diopterS;
	//右眼柱镜（散光度数）
	private Double diopterC;
	//右眼轴位（散光轴位）
	private Double diopterA;
	//可信度
	private Integer believe;
	//SCA多次普通顺序 1 2  3...
	private Integer num;
	//保存的数据类型 PU_TONG=多次批量检查SCAAVG =SCA的平均值L_DATA=夜间视力检查
	private String type;
	//L=左眼、R=右眼
	private String ifrl;
	//两周内的第几次检查  FIRST_CHECK=第一次检查   SECOND_CHECK=第二次检查'
	private String firstSecond;
	//身份证号
	private String identityCard;
	//等效球镜
	private Double dengxiaoqiujing;
	private Date checkDate;
	private String checkDate1;
	private String endTime;
	private String startTime;
	private Integer activityId;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCheckDate1() {
		return checkDate1;
	}
	public void setCheckDate1(String checkDate1) {
		this.checkDate1 = checkDate1;
	}
	public Integer gettDiopterId() {
		return tDiopterId;
	}
	public void settDiopterId(Integer tDiopterId) {
		this.tDiopterId = tDiopterId;
	}
	public Integer gettOptometryId() {
		return tOptometryId;
	}
	public void settOptometryId(Integer tOptometryId) {
		this.tOptometryId = tOptometryId;
	}
	public String getFirstSecond() {
		return firstSecond;
	}
	public void setFirstSecond(String firstSecond) {
		this.firstSecond = firstSecond;
	}
	/**
	 * 设置：id
	 */
	public void setTDiopterId(Integer tDiopterId) {
		this.tDiopterId = tDiopterId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTDiopterId() {
		return tDiopterId;
	}
	/**
	 * 设置：
	 */
	public void setTOptometryId(Integer tOptometryId) {
		this.tOptometryId = tOptometryId;
	}
	/**
	 * 获取：
	 */
	public Integer getTOptometryId() {
		return tOptometryId;
	}
	/**
	 * 设置：右眼球镜（近、远视度数）
	 */
	public void setDiopterS(Double diopterS) {
		this.diopterS = diopterS;
	}
	/**
	 * 获取：右眼球镜（近、远视度数）
	 */
	public Double getDiopterS() {
		return diopterS;
	}
	/**
	 * 设置：右眼柱镜（散光度数）
	 */
	public void setDiopterC(Double diopterC) {
		this.diopterC = diopterC;
	}
	/**
	 * 获取：右眼柱镜（散光度数）
	 */
	public Double getDiopterC() {
		return diopterC;
	}
	/**
	 * 设置：右眼轴位（散光轴位）
	 */
	public void setDiopterA(Double diopterA) {
		this.diopterA = diopterA;
	}
	/**
	 * 获取：右眼轴位（散光轴位）
	 */
	public Double getDiopterA() {
		return diopterA;
	}
	
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * 设置：可信度
	 */
	public void setBelieve(Integer believe) {
		this.believe = believe;
	}
	/**
	 * 获取：可信度
	 */
	public Integer getBelieve() {
		return believe;
	}
	/**
	 * 设置：SCA多次普通顺序 1 2  3...
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 获取：SCA多次普通顺序 1 2  3...
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置：保存的数据类型 
PU_TONG=多次批量检查SCA
AVG =SCA的平均值
L_DATA=夜间视力检查
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：保存的数据类型 
PU_TONG=多次批量检查SCA
AVG =SCA的平均值
L_DATA=夜间视力检查
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：L=左眼、R=右眼
	 */
	public void setIfrl(String ifrl) {
		this.ifrl = ifrl;
	}
	/**
	 * 获取：L=左眼、R=右眼
	 */
	public String getIfrl() {
		return ifrl;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Double getDengxiaoqiujing() {
		return dengxiaoqiujing;
	}
	public void setDengxiaoqiujing(Double dengxiaoqiujing) {
		this.dengxiaoqiujing = dengxiaoqiujing;
	}
	
}
