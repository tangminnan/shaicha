package com.shaicha.information.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.information.domain.LinShiUrlDO;


@Mapper
public interface LinShiUrlDao {
	
	List<LinShiUrlDO> list(Map<String,Object> map);
	int save(LinShiUrlDO linShiUrlDO);
	int remove(Integer id);
}
