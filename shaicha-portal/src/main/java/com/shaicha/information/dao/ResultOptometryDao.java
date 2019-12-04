package com.shaicha.information.dao;




import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultOptometryDO;

/**
 * 验光数据表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultOptometryDao {

	void saveOptometryDO(ResultOptometryDO resultOptometryDO);
	List<ResultOptometryDO> getOptometryDO(Long studentId);

	void updateOptometryDO(ResultOptometryDO resultOptometryDO);
}
