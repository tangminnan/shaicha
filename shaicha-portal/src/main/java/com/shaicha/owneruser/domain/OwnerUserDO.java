package com.shaicha.owneruser.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-27 09:55:49
 */
public class OwnerUserDO implements Serializable {
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
	//最后登录时间
	private Date loginTime;
	//用户名
	private String username;
	public Long getChectorId() {
		return chectorId;
	}
	public void setChectorId(Long chectorId) {
		this.chectorId = chectorId;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public String getChectorName() {
		return chectorName;
	}
	public void setChectorName(String chectorName) {
		this.chectorName = chectorName;
	}
	public String getDoctorNum() {
		return doctorNum;
	}
	public void setDoctorNum(String doctorNum) {
		this.doctorNum = doctorNum;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadshot() {
		return headshot;
	}
	public void setHeadshot(String headshot) {
		this.headshot = headshot;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
