package com.shaicha.informationNEW.service;

import com.shaicha.informationNEW.domain.ChanpinRecordDetailsDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:53
 */
public interface ChanpinRecordDetailsService {
	
	ChanpinRecordDetailsDO get(Integer id);
	
	List<ChanpinRecordDetailsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ChanpinRecordDetailsDO chanpinRecordDetails);
	
	int update(ChanpinRecordDetailsDO chanpinRecordDetails);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
