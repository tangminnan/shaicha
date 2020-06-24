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
public class ChanpinListDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//产品名称
	private String chanpinName;
	//添加时间
	private Date addTime;
	//1发布2未发布
	private Integer status;
	//状态1：正常2：禁用
	private Integer delFlag;

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
	 * 设置：产品名称
	 */
	public void setChanpinName(String chanpinName) {
		this.chanpinName = chanpinName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getChanpinName() {
		return chanpinName;
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
	 * 设置：1发布2未发布
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1发布2未发布
	 */
	public Integer getStatus() {
		return status;
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
