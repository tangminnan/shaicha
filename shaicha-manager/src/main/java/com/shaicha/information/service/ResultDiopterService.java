package com.shaicha.information.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultDiopterDO;

/**
 * 曲光度详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultDiopterService {
	
	ResultDiopterDO get(Integer tDiopterId);
	
	List<ResultDiopterDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultDiopterDO resultDiopter);
	
	int update(ResultDiopterDO resultDiopter);
	
	int remove(Integer tDiopterId);
	
	int batchRemove(Integer[] tDiopterIds);
		
	List<ResultDiopterDO> getByToptometryId(Integer tOptometryId);
	
	List<ResultDiopterDO> jianchashijian();
	
	List<ResultDiopterDO> queryTimeBetween(Date startDate,Date endDate);
}
