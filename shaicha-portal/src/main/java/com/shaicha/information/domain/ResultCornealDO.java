package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 角膜曲率详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
public class ResultCornealDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer tCornealId;
	//
	private Integer tOptometryId;
	//右眼曲率
	private Double cornealMm;
	//右眼屈光度
	private Double cornealD;
	//右眼角膜柱镜轴向
	private Integer cornealDeg;
	//R1=最大曲率值  R2=最小曲率值  AVG=平均取滤纸 CYL=曲率差值
	private String type;
	//L=左眼、R=右眼
	private String ifrl;
	//两周内的第几次检查  FIRST_CHECK=第一次检查   SECOND_CHECK=第二次检查
	private String firstSecond;

	private String identityCard;
	
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * 设置：id
	 */
	public void setTCornealId(Integer tCornealId) {
		this.tCornealId = tCornealId;
	}
	/**
	 * 获取：id
	 */
	public Integer getTCornealId() {
		return tCornealId;
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
	 * 设置：右眼曲率
	 */
	public void setCornealMm(Double cornealMm) {
		this.cornealMm = cornealMm;
	}
	/**
	 * 获取：右眼曲率
	 */
	public Double getCornealMm() {
		return cornealMm;
	}
	/**
	 * 设置：右眼屈光度
	 */
	public void setCornealD(Double cornealD) {
		this.cornealD = cornealD;
	}
	/**
	 * 获取：右眼屈光度
	 */
	public Double getCornealD() {
		return cornealD;
	}
	/**
	 * 设置：右眼角膜柱镜轴向
	 */
	public void setCornealDeg(Integer cornealDeg) {
		this.cornealDeg = cornealDeg;
	}
	/**
	 * 获取：右眼角膜柱镜轴向
	 */
	public Integer getCornealDeg() {
		return cornealDeg;
	}
	/**
	 * 设置：R1=最大曲率值  R2=最小曲率值  AVG=平均取滤纸 CYL=曲率差值
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：R1=最大曲率值  R2=最小曲率值  AVG=平均取滤纸 CYL=曲率差值
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
	public String getFirstSecond() {
		return firstSecond;
	}
	public void setFirstSecond(String firstSecond) {
		this.firstSecond = firstSecond;
	}
	public ResultCornealDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultCornealDO(Integer tOptometryId, Double cornealMm, Double cornealD, Integer cornealDeg, String type,
			String ifrl, String firstSecond,String identityCard) {
		super();
		this.tOptometryId = tOptometryId;
		this.cornealMm = cornealMm;
		this.cornealD = cornealD;
		this.cornealDeg = cornealDeg;
		this.type = type;
		this.ifrl = ifrl;
		this.firstSecond = firstSecond;
		this.identityCard=identityCard;
	}
	
	
	
}
