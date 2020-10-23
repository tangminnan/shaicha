package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 学生表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
public class StudentDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//学生姓名
	private String studentName;
	//性别 1：男2：女0：未知
	private Integer studentSex;
	//姓名简拼
	private String nameJianpin;
	//民族
	private String nation;
	//出生年月
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date birthday;
	//身份证号
	private String identityCard;
	//检查日期
	private Date lastCheckTime;
	//学校
	private String school;
	//年级
	private String grade;
	//班级
	private String studentClass;
	//检查地点
	private String checkLocal;
	//患者编号
	private String studentNumber;
	//联系电话
	private String phone;
	//右眼最新等效球镜
	private Double dengxiaoqiujingr;
	//左眼最新等效球镜
	private Double dengxiaoqiujingl;
	private String nakedFarvisionOd;//右眼裸眼近视力
	private String nakedFarvisionOs;//左眼裸眼近视力

	//联系地址
	private String address;
	//身高
	private Double height;
	//体重
	private Double weight;
	//添加时间
	private Date addTime;
	//状态0：正常1：禁止
	private Integer status;
	//二维码url
	private String QRCode;
	//
	private int age;
	
	//模板类型  XUE_XIAO=学校
	private String  modelType;
	//证件类型 SHEN_FENZHENG=身份证
	private String  ideentityType;
	//学校编码
	private String schoolCode;
	//学部
	private String xueBu;
	//检查类型 PU_TONG=普通筛查 SHI_FANXIAO=示范校筛查
	private String checkType;
	private Integer activityId;
	private Date mincheckdate;
	private Date maxcheckdate;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getMincheckdate() {
		return mincheckdate;
	}
	public void setMincheckdate(Date mincheckdate) {
		this.mincheckdate = mincheckdate;
	}
	public Date getMaxcheckdate() {
		return maxcheckdate;
	}
	public void setMaxcheckdate(Date maxcheckdate) {
		this.maxcheckdate = maxcheckdate;
	}
	public Double getDengxiaoqiujingr() {
		return dengxiaoqiujingr;
	}
	public void setDengxiaoqiujingr(Double dengxiaoqiujingr) {
		this.dengxiaoqiujingr = dengxiaoqiujingr;
	}
	public Double getDengxiaoqiujingl() {
		return dengxiaoqiujingl;
	}
	public void setDengxiaoqiujingl(Double dengxiaoqiujingl) {
		this.dengxiaoqiujingl = dengxiaoqiujingl;
	}
	public String getNameJianpin() {
		return nameJianpin;
	}
	public void setNameJianpin(String nameJianpin) {
		this.nameJianpin = nameJianpin;
	}
	public String getCheckLocal() {
		return checkLocal;
	}
	public void setCheckLocal(String checkLocal) {
		this.checkLocal = checkLocal;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Date getLastCheckTime() {
		return lastCheckTime;
	}
	public void setLastCheckTime(Date lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}
	public String getQRCode() {
		return QRCode;
	}
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：学生姓名
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * 获取：学生姓名
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置：性别 1：男2：女0：未知
	 */
	public void setStudentSex(Integer studentSex) {
		this.studentSex = studentSex;
	}
	/**
	 * 获取：性别 1：男2：女0：未知
	 */
	public Integer getStudentSex() {
		return studentSex;
	}
	/**
	 * 设置：民族
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * 获取：民族
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * 设置：出生年月
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：出生年月
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdentityCard() {
		return identityCard;
	}
	/**
	 * 设置：学校
	 */
	public void setSchool(String school) {
		this.school = school;
	}
	/**
	 * 获取：学校
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * 设置：年级
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * 获取：年级
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * 设置：班级
	 */
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	/**
	 * 获取：班级
	 */
	public String getStudentClass() {
		return studentClass;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：联系地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：联系地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：身高
	 */
	public void setHeight(Double height) {
		this.height = height;
	}
	/**
	 * 获取：身高
	 */
	public Double getHeight() {
		return height;
	}
	/**
	 * 设置：体重
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	/**
	 * 获取：体重
	 */
	public Double getWeight() {
		return weight;
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
	 * 设置：状态0：正常1：禁止
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态0：正常1：禁止
	 */
	public Integer getStatus() {
		return status;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getIdeentityType() {
		return ideentityType;
	}
	public void setIdeentityType(String ideentityType) {
		this.ideentityType = ideentityType;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getXueBu() {
		return xueBu;
	}
	public void setXueBu(String xueBu) {
		this.xueBu = xueBu;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getNakedFarvisionOd() {
		return nakedFarvisionOd;
	}
	public void setNakedFarvisionOd(String nakedFarvisionOd) {
		this.nakedFarvisionOd = nakedFarvisionOd;
	}
	public String getNakedFarvisionOs() {
		return nakedFarvisionOs;
	}
	public void setNakedFarvisionOs(String nakedFarvisionOs) {
		this.nakedFarvisionOs = nakedFarvisionOs;
	}
	
	
	
}
