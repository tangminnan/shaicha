package com.shaicha.informationNEW.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.ResultDiopterNewDO;

/**
 * 曲光度详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultDiopterNewService {
	
	ResultDiopterNewDO get(Integer tDiopterId);
	
	List<ResultDiopterNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultDiopterNewDO resultDiopter);
	
	int update(ResultDiopterNewDO resultDiopter);
	
	int remove(Integer tDiopterId);
	
	int batchRemove(Integer[] tDiopterIds);
		
	List<ResultDiopterNewDO> getByToptometryId(Integer tOptometryId);
	
	List<ResultDiopterNewDO> jianchashijian();
	
	List<ResultDiopterNewDO> queryTimeBetween(Date startDate,Date endDate);
}
