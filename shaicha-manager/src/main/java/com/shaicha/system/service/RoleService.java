package com.shaicha.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shaicha.system.domain.RoleDO;

@Service
public interface RoleService {

	RoleDO get(Long id);

	List<RoleDO> list();

	int save(RoleDO role);

	int update(RoleDO role);
	
	int updateStatus(RoleDO role);

	int remove(Long id);

	List<RoleDO> list(Long userId);
	
	List<RoleDO> listbyid(Long userId);

	int batchremove(Long[] ids);
}
