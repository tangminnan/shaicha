package com.shaicha.information.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultAdjustingDO;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.domain.ResultVisibilityDO;
import com.shaicha.information.domain.StudentDO;

/**
 * 学生表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
@Mapper
public interface StudentDao {

	StudentDO get(Long id);

	void updateLastCheckTime(@Param("studentId") Long studentId, @Param("date") Date date);

	List<StudentDO> getStudentInfo(String identityCard);
	
	/*List<StudentDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StudentDO student);
	
	int update(StudentDO student);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<StudentDO> getList();
*/
	
}
