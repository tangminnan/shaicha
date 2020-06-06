package com.shaicha.informationNEW.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 11:44:53
 */
public class SchoolNewDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String orgcode;
	//
	private String orgname;
	//
	private String shortname;
	//
	private String spellname;
	//0系统机构  1医疗机构  2学校机构 3 教委机构 4社区单位 9其他
	private Integer orgtype;
	//
	private String provincecode;
	//
	private String provincename;
	//
	private String citycode;
	//
	private String cityname;
	//
	private String areacode;
	//
	private String areaname;
	//
	private String street;
	//
	private String address;
	//
	private Integer sortnumber;
	//
	private String linker;
	//
	private String linkphone;
	//0 未启用 1 已启用
	private Integer enabledstatus;
	//0否 1是，公共机构可以在平台和项目中都可见
	private Integer ispublic;
	//
	private Date createdate;
	//学部
	private String xuebu;
	private Integer sysId;

	
	public Integer getSysId() {
		return sysId;
	}
	public void setSysId(Integer sysId) {
		this.sysId = sysId;
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
	 * 设置：
	 */
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	/**
	 * 获取：
	 */
	public String getOrgcode() {
		return orgcode;
	}
	/**
	 * 设置：
	 */
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	/**
	 * 获取：
	 */
	public String getOrgname() {
		return orgname;
	}
	/**
	 * 设置：
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	/**
	 * 获取：
	 */
	public String getShortname() {
		return shortname;
	}
	/**
	 * 设置：
	 */
	public void setSpellname(String spellname) {
		this.spellname = spellname;
	}
	/**
	 * 获取：
	 */
	public String getSpellname() {
		return spellname;
	}
	/**
	 * 设置：0系统机构  1医疗机构  2学校机构 3 教委机构 4社区单位 9其他
	 */
	public void setOrgtype(Integer orgtype) {
		this.orgtype = orgtype;
	}
	/**
	 * 获取：0系统机构  1医疗机构  2学校机构 3 教委机构 4社区单位 9其他
	 */
	public Integer getOrgtype() {
		return orgtype;
	}
	/**
	 * 设置：
	 */
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	/**
	 * 获取：
	 */
	public String getProvincecode() {
		return provincecode;
	}
	/**
	 * 设置：
	 */
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	/**
	 * 获取：
	 */
	public String getProvincename() {
		return provincename;
	}
	/**
	 * 设置：
	 */
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	/**
	 * 获取：
	 */
	public String getCitycode() {
		return citycode;
	}
	/**
	 * 设置：
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	/**
	 * 获取：
	 */
	public String getCityname() {
		return cityname;
	}
	/**
	 * 设置：
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	/**
	 * 获取：
	 */
	public String getAreacode() {
		return areacode;
	}
	/**
	 * 设置：
	 */
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	/**
	 * 获取：
	 */
	public String getAreaname() {
		return areaname;
	}
	/**
	 * 设置：
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * 获取：
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * 设置：
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：
	 */
	public void setSortnumber(Integer sortnumber) {
		this.sortnumber = sortnumber;
	}
	/**
	 * 获取：
	 */
	public Integer getSortnumber() {
		return sortnumber;
	}
	/**
	 * 设置：
	 */
	public void setLinker(String linker) {
		this.linker = linker;
	}
	/**
	 * 获取：
	 */
	public String getLinker() {
		return linker;
	}
	/**
	 * 设置：
	 */
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	/**
	 * 获取：
	 */
	public String getLinkphone() {
		return linkphone;
	}
	/**
	 * 设置：0 未启用 1 已启用
	 */
	public void setEnabledstatus(Integer enabledstatus) {
		this.enabledstatus = enabledstatus;
	}
	/**
	 * 获取：0 未启用 1 已启用
	 */
	public Integer getEnabledstatus() {
		return enabledstatus;
	}
	/**
	 * 设置：0否 1是，公共机构可以在平台和项目中都可见
	 */
	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	/**
	 * 获取：0否 1是，公共机构可以在平台和项目中都可见
	 */
	public Integer getIspublic() {
		return ispublic;
	}
	/**
	 * 设置：
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * 设置：学部
	 */
	public void setXuebu(String xuebu) {
		this.xuebu = xuebu;
	}
	/**
	 * 获取：学部
	 */
	public String getXuebu() {
		return xuebu;
	}
}
