package com.shaicha.carousel.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件表
 *
 * @author wjl
 */
public class FilesDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Long id;
	// 类型
	private Integer type;
	// 关联表ID
	private Long tableId;
	// URL地址
	private String url;
	//状态  0：使用中 1：已删除
	private int statue;
	// 创建时间
	private Date addTime;


	public FilesDO() {
		super();
	}


	public FilesDO(Integer type, Long tableId,String url, Date add_time) {
		super();
		this.type = type;
		this.tableId = tableId;
		this.url = url;
		this.statue = 0;
		this.addTime = add_time;
	}


	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：文件类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**
	 * 获取：文件类型
	 */
	public Integer getType() {
		return type;
	}
	
	/**
	 * 设置：URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 获取：URL地址
	 */
	public String getUrl() {
		return url;
	}
	
	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}


	public int getStatue() {
		return statue;
	}


	public void setStatue(int statue) {
		this.statue = statue;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * 设置：创建时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	@Override
	public String toString() {
		return "FileDO{" +
			"id=" + id +
			", type=" + type +
			", url='" + url + '\'' +
			", createDate=" + addTime +
			'}';
	}
}
