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
public class ChanpinTitleChooseDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//题目id
	private Integer timuId;
	//选项内容
	private String chooseName;
	//排序序号
	private Integer sort;
	//添加时间
	private Date addTime;
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
	 * 设置：题目id
	 */
	public void setTimuId(Integer timuId) {
		this.timuId = timuId;
	}
	/**
	 * 获取：题目id
	 */
	public Integer getTimuId() {
		return timuId;
	}
	/**
	 * 设置：选项内容
	 */
	public void setChooseName(String chooseName) {
		this.chooseName = chooseName;
	}
	/**
	 * 获取：选项内容
	 */
	public String getChooseName() {
		return chooseName;
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
