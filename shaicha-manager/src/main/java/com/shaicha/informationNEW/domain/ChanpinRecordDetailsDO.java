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
public class ChanpinRecordDetailsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//记录id
	private Integer recordId;
	//题目内容
	private String titleName;
	//题目id
	private Integer titleId;
	//题目类型
	private Integer titleType;
	//备注
	private String remarks;
	//添加时间
	private Date addTime;
	//状态1：正常2：禁止
	private Integer delFlag;
	
	
	

	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
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
	 * 设置：记录id
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：记录id
	 */
	public Integer getRecordId() {
		return recordId;
	}
	/**
	 * 设置：题目id
	 */
	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}
	/**
	 * 获取：题目id
	 */
	public Integer getTitleId() {
		return titleId;
	}
	/**
	 * 设置：题目类型
	 */
	public void setTitleType(Integer titleType) {
		this.titleType = titleType;
	}
	/**
	 * 获取：题目类型
	 */
	public Integer getTitleType() {
		return titleType;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
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
	 * 设置：状态1：正常2：禁止
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：状态1：正常2：禁止
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
