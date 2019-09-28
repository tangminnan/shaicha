package com.shaicha.information.dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultVisibilityDO;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultVisibilityDao {
	void saveVisibilityDO(ResultVisibilityDO resultVisibilityDO);
	ResultVisibilityDO getVisibilityDO(@Param("studentId") Long studentId,@Param("lastCheckTime") Date lastCheckTime);

	void updateVisibilityDO(ResultVisibilityDO resultVisibilityDO);
}
