package com.shaicha.informationNEW.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.informationNEW.domain.ResultCornealNewDO;
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;
import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;
import com.shaicha.informationNEW.domain.ResultEyesightNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;

/**
 * 学生表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
public interface StudentNewService {
	
	StudentNewDO get(Integer id);
	
	List<StudentNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StudentNewDO student);
	
	int update(StudentNewDO student);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	R importMember(Integer activityId,Integer schoolId,String checkType, MultipartFile file);
	
	List<StudentNewDO> getList();

	void downloadErweima(Integer[] ids,HttpServletRequest request,HttpServletResponse response);

	R daorudatijiguo(MultipartFile file);

	List<AnswerResultDO> listDati(Map<String, Object> map);

	int countDati(Map<String,Object> map);
	/**
	 * 筛查结果导出
	 */
	/*void shaichajieguodaochu(Integer[] ids, HttpServletResponse response);*/
	/**
	 * 示范校筛查结果导出
	 */
	/*void shifanshaichajieguodaochu(Integer[] ids, HttpServletResponse response);*/
	/**
	 * 普通筛查导出（freemarker模式）
	 * @param ids
	 * @param request
	 * @param response
	 */
	void exportWordPByFreemarker(Integer[] ids, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 示范校筛查结果导出（freemarker模式）
	 */
	void exportWordPBByFreemarkerSHIfanxiao(Integer[] ids,HttpServletRequest request,  HttpServletResponse response);

	List<ResultEyesightNewDO> getLatestResultEyesightDO(Integer id);

	List<ResultDiopterNewDO> getLatestResultDiopterDOListL(Integer id,String string);

	List<ResultEyepressureNewDO> getLatestResultEyepressureDO(Integer id);

	List<ResultEyeaxisNewDO> getLatelestResultEyeaxisDO(Integer id);

//	List<StudentNewDO> querySchoolName();

	List<ResultCornealNewDO> getResultCornealDOList(Integer id, String string, String string2);

//	Map<String, Object> getJInShiLv(Date startDate, Date endDate);
//
//	Map<String, Object> getJInShiLvSex(Date startDate, Date endDate);

//	Map<String,Object>  createDataToJiAOYuJu(Date parse, Date date);

	/**  
	 *首页真实数据展示
	*/  
	Map<String, Double> shouYeTrueData();
		
	List<StudentNewDO> queryBySchoolGrade(Integer activityId,String school);
			
	List<StudentNewDO> queryBySchoolStudentClass(Integer activityId,String school);
	
	List<StudentNewDO> schoolGrade(String school);
	
	List<StudentNewDO> schoolStudentClass(String school);
}
