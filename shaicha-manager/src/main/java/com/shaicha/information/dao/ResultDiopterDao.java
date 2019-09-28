package com.shaicha.information.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.information.domain.ResultDiopterDO;

/**
 * 曲光度详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultDiopterDao {

	ResultDiopterDO get(Integer tDiopterId);
	
	List<ResultDiopterDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultDiopterDO resultDiopter);
	
	int update(ResultDiopterDO resultDiopter);
	
	int remove(Integer t_diopter_id);
	
	int batchRemove(Integer[] tDiopterIds);
	
	ResultDiopterDO getByToptometryId(Integer tOptometryId);
}
