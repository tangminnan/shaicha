package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.shaicha.informationNEW.dao.SchoolNewDao;
import com.shaicha.informationNEW.domain.SchoolNewDO;
import com.shaicha.informationNEW.service.SchoolNewService;
import com.shaicha.informationNEW.domain.TArea;



@Service
public class SchoolNewServiceImpl implements SchoolNewService {
	@Autowired
	private SchoolNewDao schoolDao;
	
	@Override
	public SchoolNewDO get(Integer id){
		return schoolDao.get(id);
	}
	
	@Override
	public List<SchoolNewDO> list(Map<String, Object> map){
		return schoolDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return schoolDao.count(map);
	}
	
	@Override
	public int save(SchoolNewDO school){
		return schoolDao.save(school);
	}
	
	@Override
	public int update(SchoolNewDO school){
        int update = schoolDao.update(school);
        if (update==1) {
            schoolDao.updateStudent(school.getOrgname(), school.getId());
        }
		return update;
	}
	
	@Override
	public int remove(Integer id){
		return schoolDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return schoolDao.batchRemove(ids);
	}

}
