package com.shaicha.informationNEW.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * APP返回问卷答案
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-11-17 09:19:22
 */
public class ResultQuestionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer studentId;
	//
	private String studentName;
	//
	private Integer questionOneI;
	//
	private String questionOneS;
	//
	private String questionTwoR;
	//
	private String questionTwoL;
	//问卷
	private String questionThree;

	/**
	 * 设置：
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * 设置：
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * 获取：
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置：
	 */
	public void setQuestionOneI(Integer questionOneI) {
		this.questionOneI = questionOneI;
	}
	/**
	 * 获取：
	 */
	public Integer getQuestionOneI() {
		return questionOneI;
	}
	/**
	 * 设置：
	 */
	public void setQuestionOneS(String questionOneS) {
		this.questionOneS = questionOneS;
	}
	/**
	 * 获取：
	 */
	public String getQuestionOneS() {
		return questionOneS;
	}
	/**
	 * 设置：
	 */
	public void setQuestionTwoR(String questionTwoR) {
		this.questionTwoR = questionTwoR;
	}
	/**
	 * 获取：
	 */
	public String getQuestionTwoR() {
		return questionTwoR;
	}
	/**
	 * 设置：
	 */
	public void setQuestionTwoL(String questionTwoL) {
		this.questionTwoL = questionTwoL;
	}
	/**
	 * 获取：
	 */
	public String getQuestionTwoL() {
		return questionTwoL;
	}
	/**
	 * 设置：问卷
	 */
	public void setQuestionThree(String questionThree) {
		this.questionThree = questionThree;
	}
	/**
	 * 获取：问卷
	 */
	public String getQuestionThree() {
		return questionThree;
	}
}
