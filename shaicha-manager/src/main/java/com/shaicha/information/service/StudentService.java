package com.shaicha.information.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;

/**
 * 学生表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 17:21:00
 */
public interface StudentService {
	
	StudentDO get(Integer id);
	
	List<StudentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StudentDO student);
	
	int update(StudentDO student);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	R importMember(String checkType, MultipartFile file);
	
	List<StudentDO> getList();

	void downloadErweima(Integer[] ids,HttpServletResponse response);

	R daorudatijiguo(MultipartFile file);

	List<AnswerResultDO> listDati(Map<String, Object> map);

	int countDati(Map<String,Object> map);
	/**
	 * 筛查结果导出
	 */
	void shaichajieguodaochu(Integer[] ids, HttpServletResponse response);
	/**
	 * 示范校筛查结果导出
	 */
	void shifanshaichajieguodaochu(Integer[] ids, HttpServletResponse response);
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

	List<ResultEyesightDO> getLatestResultEyesightDO(Integer id);

	List<ResultDiopterDO> getLatestResultDiopterDOListL(Integer id,String string);

	List<ResultEyepressureDO> getLatestResultEyepressureDO(Integer id);

	List<ResultEyeaxisDO> getLatelestResultEyeaxisDO(Integer id);
	
	List<StudentDO> querySchoolName();
}
