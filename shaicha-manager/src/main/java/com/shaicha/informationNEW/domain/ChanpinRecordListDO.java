package com.shaicha.informationNEW.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:53
 */
public class ChanpinRecordListDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//产品id
	private Integer chanpinId;
	//产品名称
	private String chanpinName;
	//添加时间
	private Date addTime;
	//状态1：正常2：禁用
	private Integer delFlag;
	
	private String identityCard;
	

	
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getChanpinName() {
		return chanpinName;
	}
	public void setChanpinName(String chanpinName) {
		this.chanpinName = chanpinName;
	}
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：产品id
	 */
	public void setChanpinId(Integer chanpinId) {
		this.chanpinId = chanpinId;
	}
	/**
	 * 获取：产品id
	 */
	public Integer getChanpinId() {
		return chanpinId;
	}
	/**
	 * 设置：添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取：添加时间
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * 设置：状态1：正常2：禁用
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：状态1：正常2：禁用
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
