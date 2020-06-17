package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 16:19:31
 */
public class ChectorDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long chectorId;
	//删除状态   0-未删除   1-已删除
	private Integer deleted;
	//姓名
	private String chectorName;
	//编号
	private String doctorNum;
	//性别
	private String sex;
	//年龄
	private Integer age;
	//身份证号
	private String idCard;
	//手机号码
	private String phone;
	//常用邮箱
	private String email;
	//头像
	private String headshot;
	private MultipartFile imgFile;
	//添加时间
	private Date addTime;
	//修改时间
	private Date updateTime;
	//所在医院
	private String hospital;
	//所在科室
	private String section;
	//备注
	private String remark;
	//用户名
	private String username;
	private Long sysId;

	
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	/**
	 * 设置：
	 */
	public void setChectorId(Long chectorId) {
		this.chectorId = chectorId;
	}
	/**
	 * 获取：
	 */
	public Long getChectorId() {
		return chectorId;
	}
	/**
	 * 设置：删除状态   0-未删除   1-已删除
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * 获取：删除状态   0-未删除   1-已删除
	 */
	public Integer getDeleted() {
		return deleted;
	}
	/**
	 * 设置：姓名
	 */
	public void setChectorName(String chectorName) {
		this.chectorName = chectorName;
	}
	/**
	 * 获取：姓名
	 */
	public String getChectorName() {
		return chectorName;
	}
	/**
	 * 设置：编号
	 */
	public void setDoctorNum(String doctorNum) {
		this.doctorNum = doctorNum;
	}
	/**
	 * 获取：编号
	 */
	public String getDoctorNum() {
		return doctorNum;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * 设置：手机号码
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号码
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：常用邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：常用邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：头像
	 */
	public void setHeadshot(String headshot) {
		this.headshot = headshot;
	}
	/**
	 * 获取：头像
	 */
	public String getHeadshot() {
		return headshot;
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
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public MultipartFile getImgFile() {
		return imgFile;
	}
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
