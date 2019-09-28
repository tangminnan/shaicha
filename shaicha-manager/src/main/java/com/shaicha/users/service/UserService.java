package com.shaicha.users.service;



import java.util.List;
import java.util.Map;

import com.shaicha.users.domain.UserDO;


/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-09-27 10:18:38
 */

public interface UserService {
	
	UserDO get(Integer id);
	
	UserDO getidbyphone(String phone);
	
	
	List<UserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

}
