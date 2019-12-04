package com.shaicha.information.dao;

import java.util.Date;
import java.util.List;

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
	List<ResultEyepressureDO> getEyepressureDO(Long studentId);

	void updateEyepressureDO(ResultEyepressureDO resultEyepressureDO);
}
