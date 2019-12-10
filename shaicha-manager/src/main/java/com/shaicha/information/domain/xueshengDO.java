package com.shaicha.information.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class xueshengDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer studentSex; 
	private String studentName; 
	private String nakedNearvisionOd; 
	private String nakedNearvisionOs; 
	private String lifeNearvisionOd; 
	private String lifeNearvisionOs; 
	
	private Object diopterSOs; 
	private Object diopterCOs; 
	private Object diopterAOs; 
	private Object diopterSOd; 
	private Object diopterCOd; 
	private Object diopterAOd; 
	private Object cornealR1Os; 
	private Object cornealR2Os; 
	private Object cornealR1Od; 
	private Object cornealR2Od; 
	private Object secondCheckOd; 
	private Object secondCheckOs; 
	private Object eyePressureOd; 
	private Object eyePressureOs; 
	
	private Integer firstClass; 
	private String gradeFJ; 
	private String checkDate; 
	
	private List<xueshengDO> xuesheng = new ArrayList<>();

	public Integer getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(Integer studentSex) {
		this.studentSex = studentSex;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getNakedNearvisionOd() {
		return nakedNearvisionOd;
	}

	public void setNakedNearvisionOd(String nakedNearvisionOd) {
		this.nakedNearvisionOd = nakedNearvisionOd;
	}

	public String getNakedNearvisionOs() {
		return nakedNearvisionOs;
	}

	public void setNakedNearvisionOs(String nakedNearvisionOs) {
		this.nakedNearvisionOs = nakedNearvisionOs;
	}

	public String getLifeNearvisionOd() {
		return lifeNearvisionOd;
	}

	public void setLifeNearvisionOd(String lifeNearvisionOd) {
		this.lifeNearvisionOd = lifeNearvisionOd;
	}

	public String getLifeNearvisionOs() {
		return lifeNearvisionOs;
	}

	public void setLifeNearvisionOs(String lifeNearvisionOs) {
		this.lifeNearvisionOs = lifeNearvisionOs;
	}

	public Object getDiopterSOs() {
		return diopterSOs;
	}

	public void setDiopterSOs(Object diopterSOs) {
		this.diopterSOs = diopterSOs;
	}

	public Object getDiopterCOs() {
		return diopterCOs;
	}

	public void setDiopterCOs(Object diopterCOs) {
		this.diopterCOs = diopterCOs;
	}

	public Object getDiopterAOs() {
		return diopterAOs;
	}

	public void setDiopterAOs(Object diopterAOs) {
		this.diopterAOs = diopterAOs;
	}

	public Object getDiopterSOd() {
		return diopterSOd;
	}

	public void setDiopterSOd(Object diopterSOd) {
		this.diopterSOd = diopterSOd;
	}

	public Object getDiopterCOd() {
		return diopterCOd;
	}

	public void setDiopterCOd(Object diopterCOd) {
		this.diopterCOd = diopterCOd;
	}

	public Object getDiopterAOd() {
		return diopterAOd;
	}

	public void setDiopterAOd(Object diopterAOd) {
		this.diopterAOd = diopterAOd;
	}

	public Object getCornealR1Os() {
		return cornealR1Os;
	}

	public void setCornealR1Os(Object cornealR1Os) {
		this.cornealR1Os = cornealR1Os;
	}

	public Object getCornealR2Os() {
		return cornealR2Os;
	}

	public void setCornealR2Os(Object cornealR2Os) {
		this.cornealR2Os = cornealR2Os;
	}

	public Object getCornealR1Od() {
		return cornealR1Od;
	}

	public void setCornealR1Od(Object cornealR1Od) {
		this.cornealR1Od = cornealR1Od;
	}

	public Object getCornealR2Od() {
		return cornealR2Od;
	}

	public void setCornealR2Od(Object cornealR2Od) {
		this.cornealR2Od = cornealR2Od;
	}

	public Object getSecondCheckOd() {
		return secondCheckOd;
	}

	public void setSecondCheckOd(Object secondCheckOd) {
		this.secondCheckOd = secondCheckOd;
	}

	public Object getSecondCheckOs() {
		return secondCheckOs;
	}

	public void setSecondCheckOs(Object secondCheckOs) {
		this.secondCheckOs = secondCheckOs;
	}

	public Object getEyePressureOd() {
		return eyePressureOd;
	}

	public void setEyePressureOd(Object eyePressureOd) {
		this.eyePressureOd = eyePressureOd;
	}

	public Object getEyePressureOs() {
		return eyePressureOs;
	}

	public void setEyePressureOs(Object eyePressureOs) {
		this.eyePressureOs = eyePressureOs;
	}

	public Integer getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(Integer firstClass) {
		this.firstClass = firstClass;
	}

	public String getGradeFJ() {
		return gradeFJ;
	}

	public void setGradeFJ(String gradeFJ) {
		this.gradeFJ = gradeFJ;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public List<xueshengDO> getXuesheng() {
		return xuesheng;
	}

	public void setXuesheng(List<xueshengDO> xuesheng) {
		this.xuesheng = xuesheng;
	} 
	
	
	
	
}
