package com.shaicha.owneruser.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-03-18 14:11:10
 */
public class SetDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//用户
	private Long userid;
	//设置模式(0默认模式  1家长模式    2专业模式)
	private Integer flag;
	//治疗强度
	private Integer intensity;
	//治疗等级
	private Integer rank;
	//治疗时长
	private Integer treatTime;
	//频率
	private Integer frequency;
	//电流
	private Integer current;
	//波形 0密波   1疏密波   2疏波
	private Integer waveform;
	//工作方式 0 连续   1间断  2轮询
	private Integer workmethod;
	//外接电极 0左  1右   2关闭
	private Integer electrode;

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
	 * 设置：用户
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 设置：治疗强度
	 */
	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	/**
	 * 获取：治疗强度
	 */
	public Integer getIntensity() {
		return intensity;
	}
	/**
	 * 设置：治疗等级
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	/**
	 * 获取：治疗等级
	 */
	public Integer getRank() {
		return rank;
	}
	/**
	 * 设置：治疗时长
	 */
	public void setTreatTime(Integer treatTime) {
		this.treatTime = treatTime;
	}
	/**
	 * 获取：治疗时长
	 */
	public Integer getTreatTime() {
		return treatTime;
	}
	/**
	 * 设置：频率
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	/**
	 * 获取：频率
	 */
	public Integer getFrequency() {
		return frequency;
	}
	/**
	 * 设置：电流
	 */
	public void setCurrent(Integer current) {
		this.current = current;
	}
	/**
	 * 获取：电流
	 */
	public Integer getCurrent() {
		return current;
	}
	/**
	 * 设置：波形 0密波   1疏密波   2疏波
	 */
	public void setWaveform(Integer waveform) {
		this.waveform = waveform;
	}
	/**
	 * 获取：波形 0密波   1疏密波   2疏波
	 */
	public Integer getWaveform() {
		return waveform;
	}
	/**
	 * 设置：工作方式 0 连续   1间断  2轮训
	 */
	public void setWorkmethod(Integer workmethod) {
		this.workmethod = workmethod;
	}
	/**
	 * 获取：工作方式 0 连续   1间断  2轮训
	 */
	public Integer getWorkmethod() {
		return workmethod;
	}
	/**
	 * 设置：外接电极 0左  1右   2关闭
	 */
	public void setElectrode(Integer electrode) {
		this.electrode = electrode;
	}
	/**
	 * 获取：外接电极 0左  1右   2关闭
	 */
	public Integer getElectrode() {
		return electrode;
	}
	/**
	 * 获取：模式
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * 设置：模式
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
