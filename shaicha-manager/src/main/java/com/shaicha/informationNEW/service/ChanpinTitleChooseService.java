package com.shaicha.informationNEW.service;

import com.shaicha.informationNEW.domain.ChanpinTitleChooseDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:53
 */
public interface ChanpinTitleChooseService {
	
	ChanpinTitleChooseDO get(Integer id);
	
	List<ChanpinTitleChooseDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ChanpinTitleChooseDO chanpinTitleChoose);
	
	int update(ChanpinTitleChooseDO chanpinTitleChoose);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
