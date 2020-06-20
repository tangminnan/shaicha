package com.shaicha.informationNEW.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:52
 */
public class ChanpinDetailsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//产品id
	private Integer chanpinId;
	//题目内容
	private String timuName;
	//1:单选 2：多选3：简答
	private Integer type;
	//添加时间
	private Date addTime;
	//状态1：正常2：禁用
	private Integer delFlag;
	//排序序号
	private Integer sort;

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
	 * 设置：题目内容
	 */
	public void setTimuName(String timuName) {
		this.timuName = timuName;
	}
	/**
	 * 获取：题目内容
	 */
	public String getTimuName() {
		return timuName;
	}
	/**
	 * 设置：1:单选 2：多选3：简答
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1:单选 2：多选3：简答
	 */
	public Integer getType() {
		return type;
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
	/**
	 * 设置：排序序号
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序序号
	 */
	public Integer getSort() {
		return sort;
	}
}
