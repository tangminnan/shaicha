package com.shaicha.information.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shaicha.information.domain.LinShiUrlDO;

public interface LinShiUrlService {
	
	List<LinShiUrlDO> list(Map<String,Object> map);
	int save(LinShiUrlDO linShiUrlDO);
	int remove(Integer id);
}
