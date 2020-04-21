package com.shaicha.informationNEW.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.informationNEW.domain.ResultCornealNewDO;
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;
import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;
import com.shaicha.informationNEW.domain.ResultEyesightNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;

/**
 * 学生表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
@Mapper
public interface StudentNewDao {

	StudentNewDO get(Integer id);
	
	List<StudentNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StudentNewDO student);
	
	int update(StudentNewDO student);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<StudentNewDO> getList();

	void saveAnswer(AnswerResultDO answerResultDO);

	List<AnswerResultDO> listDati(Map<String, Object> map);

	int countDati(Map<String, Object> map);
	/**
		导出最新的检测视力
	 */
	List<ResultEyesightNewDO> getLatestResultEyesightDO(Integer studentId);
	/**
	 获取电脑验光最新数据
	 */
	List<ResultDiopterNewDO> getLatestResultDiopterDOListL(@Param("studentId") Integer studentId,@Param("ifRL") String ifRL);
	/**
	 * 获取最新的眼内压数据
	 */
	List<ResultEyepressureNewDO> getLatestResultEyepressureDO(@Param("studentId") Integer studentId);

	/**
	 * 获取最新的眼轴长度检测数据
	 */
	List<ResultEyeaxisNewDO> getLatelestResultEyeaxisDO(@Param("studentId") Integer studentId);
////////////////////////////////////////////////////////////
	void saveEyePressure(ResultEyepressureNewDO eyepressureDO);

	void saveResultEyesightDO(ResultEyesightNewDO resultEyesightDO);

	void ResultEyeaxisDO(ResultEyeaxisNewDO resultEyeaxisDO);

	void saveResultDiopterDO(ResultDiopterNewDO resultDiopterDO);
	
	List<StudentNewDO> querySchoolGrade(String school);
	
	List<StudentNewDO> querySchoolName();
			
	List<StudentNewDO> queryGradeNum(@Param("grade") String grade,@Param("school") String school);
	
	List<StudentNewDO> getCheckUserNum(Map<String,Object> map);
	
	List<StudentNewDO> queryGradeClassCount(Map<String,Object> map);
	
	List<StudentNewDO> getGradeClassCheck(Map<String,Object> map);
	
	List<StudentNewDO> queryStudentGrade(@Param("school") String school,@Param("grade") String grade,@Param("start") Date start,@Param("end") Date end,@Param("studentClass") String studentClass);
	
	List<StudentNewDO> getCheckNianjiNum(@Param("school") String school,@Param("grade") String grade,@Param("checkDate") String checkDate);


	List<ResultCornealNewDO> getResultCornealDOList(@Param("studentId") Integer studentId,@Param("ifrl") String ifrl,@Param("type") String type);

	List<ResultEyesightNewDO> getJInShiLv(@Param("i") int i,@Param("j") int j);

	List<ResultDiopterNewDO> getResultDiopterDO(@Param("i") int i,@Param("j") int j,@Param("ifRL") String ifRL);
	
	List<StudentNewDO> getSchool(@Param("start") Date start,@Param("end") Date end);
	
	List<StudentNewDO> getLastCheckStudent(@Param("school") String school,@Param("start") Date start,@Param("end") Date end);

	List<StudentNewDO> getCheckAllStudent(@Param("start") Date start,@Param("end") Date end,@Param("grade") String grade);

	List<StudentNewDO> jiancharenshu(Map<String,Object> map);

	List<StudentNewDO> getStudentDOshou(@Param("i") int i,@Param("j") int j);

	int countP(Map<String, Object> paMap);

	void updateS(StudentNewDO studentDO);

	List<StudentNewDO> getAllCheckStudentDO(@Param("i") int i,@Param("j") int j);


}
