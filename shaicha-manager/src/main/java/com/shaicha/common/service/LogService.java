package com.shaicha.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shaicha.common.domain.LogDO;
import com.shaicha.common.domain.PageDO;
import com.shaicha.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
