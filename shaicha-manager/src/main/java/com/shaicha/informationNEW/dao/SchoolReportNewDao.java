package com.shaicha.informationNEW.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.domain.ActivityListNewDO;
import com.shaicha.informationNEW.domain.SchoolNewDO;

@Mapper
public interface SchoolReportNewDao {
	
	int activityByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int activityGradeByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int zongNum(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int gradeCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	SchoolNewDO schoolXuebu(@Param("OrgName") String OrgName);
	
	int schoolCheckJinshi(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int schoolCheckBuliang(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int schoolSexCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("studentSex") Integer studentSex);
	
	int schoolSexjinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("studentSex") Integer studentSex);

	int schoolGradeClassCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentClass") String studentClass);
	
	int schoolGradeClassjinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentClass") String studentClass);

	int schoolGradeClassbuliang(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentClass") String studentClass);
	
	int jinshiqianqi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int jiaxingjinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int didujinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int zhongdujinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int gaodujinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int qingdubuliang(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int zhongdubuliang(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int gaodubuliang(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int buliangTotal(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int linianCheckNum(@Param("school") String school,@Param("lastCheckTime") String lastCheckTime);
	
	int linianjinshi(@Param("school") String school,@Param("lastCheckTime") String lastCheckTime);

	int gradeCheck(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int gradeCheckjinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int liniangradeCheck(@Param("school") String school,@Param("grade") String grade,@Param("lastCheckTime") String lastCheckTime);
	int liniangradeCheckBySys(@Param("school") String school,@Param("grade") String grade,@Param("lastCheckTime") String lastCheckTime,@Param("sysId") Long sysId);

	int liniangradeCheckjinshi(@Param("school") String school,@Param("grade") String grade,@Param("lastCheckTime") String lastCheckTime);

	int liniangradeCheckbuliang(@Param("school") String school,@Param("grade") String grade,@Param("lastCheckTime") String lastCheckTime);
	int liniangradeCheckbuliangBySys(@Param("school") String school,@Param("grade") String grade,@Param("lastCheckTime") String lastCheckTime,@Param("sysId") Long sysId);

	int sexCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("studentSex") Integer studentSex);
	
	int sexCheckjinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("studentSex") Integer studentSex);
	
	int linianSexCheckNum(@Param("school") String school,@Param("studentSex") Integer studentSex,@Param("lastCheckTime") String lastCheckTime);
	
	int linianSexCheckjinshi(@Param("school") String school,@Param("studentSex") Integer studentSex,@Param("lastCheckTime") String lastCheckTime);

	int sexGradeCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentSex") Integer studentSex);
	
	int sexGradeCheckjinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentSex") Integer studentSex);

	List<StudentNewDO> schoolActivity(@Param("activityId") Integer activityId,@Param("sysId") Long sysId);
	
	int activitySexByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("studentSex") Integer studentSex);
	
	int activityGradeSexByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentSex") Integer studentSex);
	
	int activityGradeClassByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade,@Param("studentClass") String studentClass);

	List<ActivityListNewDO> getLastActivity(@Param("sysId")Integer sysId,@Param("addTime")Date addTime);
	
	int waihaijiancharenshu();
	
	int waihaijinshirenshu();
	
	int waihaibuliangrenshu();
}
