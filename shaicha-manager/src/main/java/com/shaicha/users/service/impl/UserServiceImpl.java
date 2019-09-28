package com.shaicha.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.users.dao.UserDao;
import com.shaicha.users.domain.UserDO;
import com.shaicha.users.service.UserService;

import java.util.List;
import java.util.Map;


@Service("UserService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDO get(Integer id){
		return userDao.get(id);
	}
	
	@Override
	public UserDO getidbyphone(String phone){
		return userDao.getidbyphone(phone);
	}
	
	@Override
	public List<UserDO> list(Map<String, Object> map){
		return userDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userDao.count(map);
	}
	
	@Override
	public int save(UserDO user){
		return userDao.save(user);
	}
	
	@Override
	public int update(UserDO user){
		return userDao.update(user);
	}
	
	@Override
	public int remove(Long id){
		return userDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userDao.batchRemove(ids);
	}



	
}
