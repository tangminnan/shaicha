package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * 活动表---新
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 10:29:09
 */
public class ActivityListDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//活动id
	private Integer id;
	//活动名称
	private String activityName;
	//0未删除  1已删除
	private Integer delFlag;
	//添加时间
	private Date addTime;
	//更新时间
	private Date updateDate;
	

	/**
	 * 设置：活动id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：活动id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：活动名称
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	/**
	 * 获取：活动名称
	 */
	public String getActivityName() {
		return activityName;
	}
	/**
	 * 设置：0未删除  1已删除
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：0未删除  1已删除
	 */
	public Integer getDelFlag() {
		return delFlag;
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
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
}
