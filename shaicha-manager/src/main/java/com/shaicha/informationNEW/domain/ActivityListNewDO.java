package com.shaicha.informationNEW.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 活动表---新
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 10:29:09
 */
public class ActivityListNewDO implements Serializable {
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
	private Long sysId;
	
	private Integer yingjian;
	
	private Integer shoujian;
	private String checkDate;


    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getYingjian() {
		return yingjian;
	}
	public void setYingjian(Integer yingjian) {
		this.yingjian = yingjian;
	}
	public Integer getShoujian() {
		return shoujian;
	}
	public void setShoujian(Integer shoujian) {
		this.shoujian = shoujian;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
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
