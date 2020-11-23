package com.shaicha.information.dao;

import com.shaicha.information.domain.ResultQuestionDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * APP返回问卷答案
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-11-17 09:19:22
 */
@Mapper
public interface ResultQuestionDao {

	List<ResultQuestionDO> get(int studentId);
	
	List<ResultQuestionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultQuestionDO resultQuestion);
	
	int update(ResultQuestionDO resultQuestion);
	
	int remove(Integer student_id);
	
	int batchRemove(Integer[] studentIds);
}
