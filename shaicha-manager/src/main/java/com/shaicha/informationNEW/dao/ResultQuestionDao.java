package com.shaicha.informationNEW.dao;

import com.shaicha.informationNEW.domain.ResultQuestionDO;

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

	ResultQuestionDO get(Integer studentId);
	List<ResultQuestionDO> getQuestion(Integer studentId);

	List<ResultQuestionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultQuestionDO resultQuestion);
	
	int update(ResultQuestionDO resultQuestion);
	
	int remove(Integer student_id);
	
	int batchRemove(Integer[] studentIds);
}
