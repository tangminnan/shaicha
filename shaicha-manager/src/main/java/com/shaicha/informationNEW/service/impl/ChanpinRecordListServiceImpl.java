package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaicha.common.utils.ExcelExportUtil4DIY;
import com.shaicha.informationNEW.dao.ChanpinRecordDetailsDao;
import com.shaicha.informationNEW.dao.ChanpinRecordListDao;
import com.shaicha.informationNEW.domain.ChanpinRecordDetailsDO;
import com.shaicha.informationNEW.domain.ChanpinRecordListDO;
import com.shaicha.informationNEW.service.ChanpinRecordListService;



@Service
public class ChanpinRecordListServiceImpl implements ChanpinRecordListService {
	@Autowired
	private ChanpinRecordListDao chanpinRecordListDao;
	@Autowired
	private ChanpinRecordDetailsDao chanpinRecordDetailsDao;
	
	@Override
	public ChanpinRecordListDO get(Integer id){
		return chanpinRecordListDao.get(id);
	}
	
	@Override
	public List<ChanpinRecordListDO> list(Map<String, Object> map){
		return chanpinRecordListDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return chanpinRecordListDao.count(map);
	}
	
	@Override
	public int save(ChanpinRecordListDO chanpinRecordList){
		return chanpinRecordListDao.save(chanpinRecordList);
	}
	
	@Override
	public int update(ChanpinRecordListDO chanpinRecordList){
		return chanpinRecordListDao.update(chanpinRecordList);
	}
	
	@Override
	public int remove(Integer id){
		return chanpinRecordListDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return chanpinRecordListDao.batchRemove(ids);
	}

	@Override
	public void wenjuandaochu(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String,Object>> bb = new ArrayList<Map<String,Object>>();
		for (Integer integer : ids) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("recordId", integer);
			List<ChanpinRecordDetailsDO> list = chanpinRecordDetailsDao.list(map);
			if(list.size()>0){
				Map<String,Object> mapPP = new HashMap<String,Object>();
				mapPP.put("问卷名称", chanpinRecordListDao.get(integer).getChanpinName());
				for (ChanpinRecordDetailsDO chanpinRecordDetailsDO : list) {
					mapPP.put(chanpinRecordDetailsDO.getTitleName(), chanpinRecordDetailsDO.getRemarks());
				}
				bb.add(mapPP);
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "问卷记录"+format.format(new Date().getTime())+".xls";
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1"));
		/*
		Cookie status = new Cookie("status","success");
	    status.setMaxAge(600);
	    response.addCookie(status);
		*/
		OutputStream out = response.getOutputStream();
		
		try {
			ExcelExportUtil4DIY.exportToFile(bb,out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
}
