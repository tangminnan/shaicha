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
	List<ResultVisibilityDO> getVisibilityDO(Long studentId);

	void updateVisibilityDO(ResultVisibilityDO resultVisibilityDO);
}
