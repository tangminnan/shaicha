package com.shaicha.carousel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.carousel.domain.BannerDO;


/**
 * 轮播图表
 * @author wjl
 */
@Mapper
public interface BannerDao {

	BannerDO get(Long id);
	
	List<BannerDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(BannerDO file);
	
	int update(BannerDO file);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
