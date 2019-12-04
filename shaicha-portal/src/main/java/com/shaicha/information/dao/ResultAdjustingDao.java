package com.shaicha.information.dao;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultAdjustingDO;

/**
 * 调节灵敏度
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultAdjustingDao {

	void saveAdjustingDO(ResultAdjustingDO resultAdjustingDO);
	List<ResultAdjustingDO> getAdjustingDO(Long studentId);

	void updateAdjustingDO(ResultAdjustingDO resultAdjustingDO);
}
