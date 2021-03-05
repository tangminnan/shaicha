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

	List<StudentNewDO> activitygetschool(Integer activityId);

	
	int count(Map<String,Object> map);
	
	int save(StudentNewDO student);
	int insertBatch(List<StudentNewDO> student);
	
	int update(StudentNewDO student);

	List<StudentNewDO> shifanactivityid();
	List<StudentNewDO> shifanschool(Integer activityId);

	
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
	List<ResultDiopterNewDO> getDiopterDOList(@Param("studentId") Integer studentId,@Param("ifRL") String ifRL);
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
	
	List<StudentNewDO> querySchoolGrade(@Param("school") String school,@Param("activityId") Integer activityId);
	List<StudentNewDO> querySchoolGradeLiNian(String school);
	List<StudentNewDO> queryGradeClassCount(Map<String,Object> map);
		
	List<StudentNewDO> queryStudentGrade(@Param("school") String school,@Param("grade") String grade,@Param("activityId") Integer activityId,@Param("studentClass") String studentClass);	

	List<ResultCornealNewDO> getResultCornealDOList(@Param("studentId") Integer studentId,@Param("ifrl") String ifrl,@Param("type") String type);

	List<ResultEyesightNewDO> getJInShiLv(@Param("i") int i,@Param("j") int j);

	List<ResultDiopterNewDO> getResultDiopterDO(@Param("i") int i,@Param("j") int j,@Param("ifRL") String ifRL);

	List<StudentNewDO> getStudentDOshou(@Param("i") int i,@Param("j") int j);

	int countP(Map<String, Object> paMap);
	int countS(Map<String, Object> paMap);

	void updateS(StudentNewDO studentDO);

	List<StudentNewDO> getAllCheckStudentDO(@Param("i") int i,@Param("j") int j);


	List<StudentNewDO> queryBySchoolGrade(@Param("activityId") Integer activityId,@Param("school") String school,@Param("sysId") Long sysId);
	
	List<StudentNewDO> queryBySchoolStudentClass(@Param("activityId") Integer activityId,@Param("school") String school,@Param("sysId") Long sysId,@Param("grade") String grade);

	List<StudentNewDO> schoolGrade(@Param("school") String school,@Param("sysId") Long sysId);
	
	List<StudentNewDO> schoolStudentClass(@Param("school") String school,@Param("sysId") Long sysId,@Param("grade") String grade);

	int activityNum(@Param("activityId") Integer activityId);
	
	int activityCheckNum(@Param("activityId") Integer activityId);
	
	List<StudentNewDO> activityIdBySchool(@Param("activityId") Integer activityId);
	
	int activitySchoolNum(@Param("activityId") Integer activityId,@Param("schoolId") Integer schoolId);
	
	int activitySchoolCheckNum(@Param("activityId") Integer activityId,@Param("schoolId") Integer schoolId);


    List<StudentNewDO> listNoShiFan(Map<String, Object> map);

    int countNoShiFan(Map<String, Object> map);

    List<String> getSchoolCheckDate(@Param("activityId") Integer activityId,@Param("schoolId") Integer schoolId);
}
