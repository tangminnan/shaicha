package com.shaicha.informationNEW.domain;



import java.math.BigDecimal;

public class TArea implements java.io.Serializable{

	private static final long serialVersionUID = 9052142472201234113L;
	private Long id; //ID
	private Integer code; //地区编码
	private String areaShort; //地区简称
    private String areaName; //地区名称
    private Integer parentCode; //父级编码
    private Integer areaLevel; //等级
   
    private String pinyin; //拼音
    private String telCode; //区号
    private BigDecimal lng; //经度 
    private BigDecimal lat; //纬度
    private String mergerName; //地区合并名称
	
	public TArea() {
	}

	public TArea(Long id, Integer code, String areaShort, String areaName,Integer parentCode, Integer areaLevel, String pinyin,String telCode, BigDecimal lng, BigDecimal lat, String mergerName) {
		this.id = id;
		this.code = code;
		this.areaShort = areaShort;
		this.areaName = areaName;
		this.parentCode = parentCode;
		this.areaLevel = areaLevel;
		this.pinyin = pinyin;
		this.telCode = telCode;
		this.lng = lng;
		this.lat = lat;
		this.mergerName = mergerName;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAreaShort() {
		return areaShort;
	}
	public void setAreaShort(String areaShort) {
		this.areaShort = areaShort;
	}

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getParentCode() {
		return parentCode;
	}
	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getAreaLevel() {
		return areaLevel;
	}
	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getTelCode() {
		return telCode;
	}
	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getMergerName() {
		return mergerName;
	}

	public void setMergerName(String mergerName) {
		this.mergerName = mergerName;
	}
	 
}
