package com.shaicha.information.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.information.dao.LinShiUrlDao;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.information.service.LinShiUrlService;

@Service
public class LinShiUrlServiceImpl implements LinShiUrlService{
	
	@Autowired
	private LinShiUrlDao linShiUrlDao;

	@Override
	public int save(LinShiUrlDO linShiUrlDO) {
		return linShiUrlDao.save(linShiUrlDO);
	}

	@Override
	public List<LinShiUrlDO> list(Map<String, Object> map) {
		return linShiUrlDao.list(map);
	}

	@Override
	public int remove(Integer id) {
		return linShiUrlDao.remove(id);
	}

}
