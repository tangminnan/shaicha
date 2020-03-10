package com.shaicha.information.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;

/**
 * 学生表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
@Mapper
public interface StudentDao {

	StudentDO get(Integer id);
	
	List<StudentDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StudentDO student);
	
	int update(StudentDO student);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<StudentDO> getList();

	void saveAnswer(AnswerResultDO answerResultDO);

	List<AnswerResultDO> listDati(Map<String, Object> map);

	int countDati(Map<String, Object> map);
	/**
		导出最新的检测视力
	 */
	List<ResultEyesightDO> getLatestResultEyesightDO(Integer studentId);
	/**
	 获取电脑验光最新数据
	 */
	List<ResultDiopterDO> getLatestResultDiopterDOListL(@Param("studentId") Integer studentId,@Param("ifRL") String ifRL);
	/**
	 * 获取最新的眼内压数据
	 */
	List<ResultEyepressureDO> getLatestResultEyepressureDO(@Param("studentId") Integer studentId);

	/**
	 * 获取最新的眼轴长度检测数据
	 */
	List<ResultEyeaxisDO> getLatelestResultEyeaxisDO(@Param("studentId") Integer studentId);
////////////////////////////////////////////////////////////
	void saveEyePressure(ResultEyepressureDO eyepressureDO);

	void saveResultEyesightDO(ResultEyesightDO resultEyesightDO);

	void ResultEyeaxisDO(ResultEyeaxisDO resultEyeaxisDO);

	void saveResultDiopterDO(ResultDiopterDO resultDiopterDO);
	
	List<StudentDO> querySchoolGrade(String school);
	
	List<StudentDO> querySchoolName();
			
	List<StudentDO> queryGradeNum(String grade);
	
	List<StudentDO> getCheckUserNum(Map<String,Object> map);
	
	List<StudentDO> queryGradeClassCount(Map<String,Object> map);
	
	List<StudentDO> getGradeClassCheck(Map<String,Object> map);
	
	List<StudentDO> queryStudentGrade(@Param("school") String school,@Param("grade") String grade,@Param("start") Date start,@Param("end") Date end,@Param("studentClass") String studentClass);
	
	List<StudentDO> getCheckNianjiNum(@Param("school") String school,@Param("grade") String grade,@Param("checkDate") String checkDate);


	List<ResultCornealDO> getResultCornealDOList(@Param("studentId") Integer studentId,@Param("ifrl") String ifrl,@Param("type") String type);

	List<ResultEyesightDO> getJInShiLv(@Param("i") int i,@Param("j") int j);

	List<ResultDiopterDO> getResultDiopterDO(@Param("i") int i,@Param("j") int j,@Param("ifRL") String ifRL);
	
	List<StudentDO> getSchool(@Param("start") Date start,@Param("end") Date end);
	
	List<StudentDO> getLastCheckStudent(@Param("school") String school,@Param("start") Date start,@Param("end") Date end);

	List<StudentDO> getCheckAllStudent(@Param("start") Date start,@Param("end") Date end,@Param("grade") String grade);

	List<StudentDO> getStudentDOshou(@Param("i") int i,@Param("j") int j);

}
