package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.dao.ChanpinTitleChooseDao;
import com.shaicha.informationNEW.domain.ChanpinTitleChooseDO;
import com.shaicha.informationNEW.service.ChanpinTitleChooseService;



@Service
public class ChanpinTitleChooseServiceImpl implements ChanpinTitleChooseService {
	@Autowired
	private ChanpinTitleChooseDao chanpinTitleChooseDao;
	
	@Override
	public ChanpinTitleChooseDO get(Integer id){
		return chanpinTitleChooseDao.get(id);
	}
	
	@Override
	public List<ChanpinTitleChooseDO> list(Map<String, Object> map){
		return chanpinTitleChooseDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return chanpinTitleChooseDao.count(map);
	}
	
	@Override
	public int save(ChanpinTitleChooseDO chanpinTitleChoose){
		return chanpinTitleChooseDao.save(chanpinTitleChoose);
	}
	
	@Override
	public int update(ChanpinTitleChooseDO chanpinTitleChoose){
		return chanpinTitleChooseDao.update(chanpinTitleChoose);
	}
	
	@Override
	public int remove(Integer id){
		return chanpinTitleChooseDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return chanpinTitleChooseDao.batchRemove(ids);
	}
	
}
