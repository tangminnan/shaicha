package com.shaicha.information.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultEyepressureDO;

/**
 * 眼内压
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultEyepressureDao {
	void saveEyepressureDO(ResultEyepressureDO resultEyepressureDO);
	ResultEyepressureDO getEyepressureDO(@Param("studentId") Long studentId,@Param("lastCheckTime") Date lastCheckTime);

	void updateEyepressureDO(ResultEyepressureDO resultEyepressureDO);
}
