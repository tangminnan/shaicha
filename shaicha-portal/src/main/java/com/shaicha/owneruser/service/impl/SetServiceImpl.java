package com.shaicha.owneruser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.owneruser.dao.SetDao;
import com.shaicha.owneruser.domain.SetDO;
import com.shaicha.owneruser.service.SetService;

import java.util.List;
import java.util.Map;




@Service
public class SetServiceImpl implements SetService {
	@Autowired
	private SetDao setDao;
	
	
	
	@Override
	public List<SetDO> list(Map<String, Object> map){
		return setDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return setDao.count(map);
	}
	
	@Override
	public int save(SetDO set){
		return setDao.save(set);
	}
	
	@Override
	public int remove(Integer id){
		return setDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return setDao.batchRemove(ids);
	}

	@Override
	public SetDO getHouseHolderSet(Long userId) {
		// TODO Auto-generated method stub
		return setDao.getHouseHolderSet(userId);
	}

	@Override
	public int addsetUser(SetDO setDO) {
		return setDao.addsetUser(setDO);
	}

	@Override
	public SetDO getProfessionSet(Long userId) {
		// TODO Auto-generated method stub
		return setDao.getProfessionSet(userId);
	}

	@Override
	public int updatesetUser(SetDO setDO) {
		// TODO Auto-generated method stub
		return setDao.updatesetUser(setDO);
	}

	@Override
	public SetDO getFlag(Long userId) {
		// TODO Auto-generated method stub
		return setDao.getFlag(userId);
	}

	@Override
	public void updateFlag(SetDO setDO) {
		setDao.updateFlag(setDO);
	}
	
	
	

}
