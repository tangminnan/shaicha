package com.shaicha.information.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.information.domain.ResultDiopterDO;

/**
 * 曲光度详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 09:43:26
 */
@Mapper
public interface ResultDiopterDao {
	void saveDiopterDO(ResultDiopterDO resultDiopterDO);
	void removeAll(Integer tOptometryId);
	List<ResultDiopterDO> getByOptometryId(Integer tOptometryId);

    void saveYuCeData(ResultDiopterDO resultDiopterDOL);
}
