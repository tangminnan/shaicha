package com.shaicha.informationNEW.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.domain.SchoolNewDO;

@Mapper
public interface jiaoyujuReportNewDao {
	
	SchoolNewDO getAddress(String OrgName);
	
	StudentNewDO getMaxMinCheckDate(Integer activityId);
	
	int getXuebuNum(@Param("activityId") Integer activityId,@Param("xueBu") String xueBu);
	
	int activityByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int schoolByCheckNum(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int jinshiqianqi(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int jiaxingjinshi(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int didujinshi(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int zhongdujinshi(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int gaodujinshi(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int qingdubuliang(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int zhongdubuliang(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int gaodubuliang(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int buliangtotal(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int nowXuebuJinshi(@Param("activityId") Integer activityId,@Param("xueBu") String xueBu);
	
	int sexCheckNum(@Param("activityId") Integer activityId,@Param("studentSex") Integer studentSex,@Param("school") String school);
	
	int sexJinshiNum(@Param("activityId") Integer activityId,@Param("studentSex") Integer studentSex,@Param("school") String school);
	
	int daijingrenshu(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int jiaozhengbuzurenshu(@Param("activityId") Integer activityId,@Param("school") String school);
	
	int nowCheckNum(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int nowgradejinshi(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int nowgradebuliang(@Param("activityId") Integer activityId,@Param("school") String school,@Param("grade") String grade);
	
	int linianXuebuRenshu(@Param("xueBu") String xueBu,@Param("activityId") Integer activityId,@Param("school") String school,@Param("lastCheckTime") String lastCheckTime);
	
	int linianXueBujinshi(@Param("xueBu") String xueBu,@Param("activityId") Integer activityId,@Param("school") String school,@Param("lastCheckTime") String lastCheckTime);
	
	int linianSexrenshu(@Param("studentSex") Integer studentSex,@Param("activityId") Integer activityId,@Param("school") String school,@Param("lastCheckTime") String lastCheckTime);
	
	int linianSexjinshi(@Param("studentSex") Integer studentSex,@Param("activityId") Integer activityId,@Param("school") String school,@Param("lastCheckTime") String lastCheckTime);

	StudentNewDO getSchoolxuebu(@Param("school") String school,@Param("activityId") Integer activityId);

}
