package com.shaicha.information.dao;





import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.shaicha.information.domain.ResultCornealDO;

/**
 * 角膜曲率详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultCornealDao {
	void saveCornealDO(ResultCornealDO resultCornealDO);
	void removeAll(Integer tOptometryId);
	List<ResultCornealDO> getByOptometryId(Integer tOptometryId);
}
