package com.shaicha.information.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.information.domain.ResultEyesightDO;

/**
 * 视力检查
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultEyesightDao {
	void saveEyesightDO(ResultEyesightDO resultEyesightDO);
	List<ResultEyesightDO> getEyesightDO(Long studentId);
	void updateEyesightDO(ResultEyesightDO resultEyesightDO);

    void saveYuCeData(ResultEyesightDO resultEyesightDOY);

    int updateDianziEye(ResultEyesightDO resultEyesightDO);

	int saveDianziEye(ResultEyesightDO resultEyesightDO);
}
