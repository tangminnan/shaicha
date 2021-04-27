package com.shaicha.informationNEW.dao;

import com.shaicha.informationNEW.domain.SchoolNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.domain.TArea;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 11:44:53
 */
@Mapper
public interface SchoolNewDao {

	SchoolNewDO get(Integer id);
	
	List<SchoolNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SchoolNewDO school);
	
	int update(SchoolNewDO school);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    List<StudentNewDO> listAll();
    void updateStudent(@Param("school") String school, @Param("schoolId") Integer schoolId);
}
