package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.Date;



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
	//民族
	private String nation;
	//出生年月
	private Date birthday;
	//身份证号
	private String identityCard;
	//证件类型 SHEN_FENZHENG=身份证QITA=其他
	private String  ideentityType;
	//学校
	private String school;
	//年级
	private String grade;
	//班级
	private String studentClass;
	//联系电话
	private String phone;
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
	//上次检查时间
	private Date lastCheckTime;
	private String nakedFarvisionOd;//右眼裸眼近视力
	private String nakedFarvisionOs;//左眼裸眼近视力
	private Double dengxiaoqiujingr;
	private Double dengxiaoqiujingl;
	private Integer activityId;
	private Integer schoolId;
	private String schoolCode;
	private String xueBu;
	
	
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
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getIdeentityType() {
		return ideentityType;
	}
	public void setIdeentityType(String ideentityType) {
		this.ideentityType = ideentityType;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	public Date getLastCheckTime() {
		return lastCheckTime;
	}
	public void setLastCheckTime(Date lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
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
	
}
