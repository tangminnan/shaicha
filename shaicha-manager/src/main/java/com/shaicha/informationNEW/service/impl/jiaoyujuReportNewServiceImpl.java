package com.shaicha.informationNEW.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.information.dao.LinShiUrlDao;
import com.shaicha.informationNEW.dao.ActivityListNewDao;
import com.shaicha.informationNEW.dao.jiaoyujuReportNewDao;
import com.shaicha.information.domain.LinShiUrlDO;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.informationNEW.service.jiaoyujuReportNewService;
import com.shaicha.system.dao.UserDao;
import com.shaicha.system.domain.UserDO;
import com.shaicha.system.service.UserService;
import com.shaicha.informationNEW.domain.SchoolNewDO;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class jiaoyujuReportNewServiceImpl implements jiaoyujuReportNewService{
	
	@Autowired
	jiaoyujuReportNewDao jiaoyujuReportDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private LinShiUrlDao linShiUrlDao;
	@Autowired
	UserDao userMapper;
	@Autowired
	ActivityListNewDao activityListNewDao;
	
	//教育局报告
	@Override
	public void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> params = jiaoyujubaogao(request, response);
			createDoc(response,params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "给教育局报告检测.ftl");
		//download(request, response, bootdoConfig.getPoiword(),new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		//craeteZipPath(bootdoConfig.getPoiword(),response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//数据
	public Map<String, Object> jiaoyujubaogao(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("0.0");
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));	
		String parameter = request.getParameter("school");
		String[] split1 = parameter.split(",");
		String date = request.getParameter("date");
		String cityname = jiaoyujuReportDao.getAddress(split1[0]).getAreaname();
		System.out.println(cityname);
		params.put("address", cityname);
		StudentNewDO maxMin = jiaoyujuReportDao.getMaxMinCheckDate(activityId);
		params.put("kai", sdf.format(maxMin.getMincheckdate()));
		params.put("jie", sdf.format(maxMin.getMaxcheckdate()));
		params.put("newDate", sdf2.format(new Date()));
		//图
		Map<String,Object> ls = new HashMap<>();
		ls.put("type", date);
		List<LinShiUrlDO> lsu = linShiUrlDao.list(ls);
		for (LinShiUrlDO linShiUrlDO : lsu) {
			params.put(linShiUrlDO.getName(), linShiUrlDO.getImgUrl());
			
			linShiUrlDao.remove(linShiUrlDO.getId());
		}
		
		UserDO userDO = userMapper.get(activityListNewDao.get(activityId).getSysId());
		params.put("zhongxin", userDO.getZhongxinName());
		
		List<Map<String, Integer>> list1 = new ArrayList<Map<String, Integer>>();
		List<Integer> list2 = new ArrayList<>();
		String parameter1 = request.getParameter("school");
		String[] split = parameter1.split(",");
		params.put("school", split.length);
		for (String string : split) {
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put(jiaoyujuReportDao.getAddress(string).getXuebu(), jiaoyujuReportDao.schoolByCheckNum(activityId,string));
			list1.add(map);
			list2.add(jiaoyujuReportDao.schoolByCheckNum(activityId,string));
		}
		int renshu = 0;
		int you = 0;
		int xiao = 0;
		int chu = 0;
		int gao = 0;
		int your = 0;
		int xiaor = 0;
		int chur = 0;
		int gaor = 0;
		for(Map<String, Integer> map : list1){
			for(Map.Entry<String, Integer> m : map.entrySet()){
				if(m.getKey().equals("幼儿园")){
					you++;
					your += m.getValue();
				}
				if(m.getKey().equals("小学")){
					xiao++;
					xiaor += m.getValue();
				}
				if(m.getKey().equals("初中")){
					chu++;
					chur += m.getValue();
				}
				if(m.getKey().equals("高中")){
					gao++;
					gaor += m.getValue();
				}
			}
		}
		for (Integer integer : list2) {
			renshu += integer.intValue();
		}
		
		params.put("gong", renshu);
		params.put("you", you);//幼儿园数
		params.put("xiao", xiao);//小学数
		params.put("chu", chu);//初中数
		params.put("gao", gao);//高中数
		
		params.put("your", your);//幼儿园人数
		params.put("xiaor", xiaor);//小学人数
		params.put("chur", chur);//初中人数
		params.put("gaor", gaor);//高中人数
		
		//近视
		List<Map<String, String>> list3 = new ArrayList<Map<String, String>>();
		List<Map<String, Integer>> list4 = new ArrayList<Map<String, Integer>>();
		List<Map<String, Integer>> list6 = new ArrayList<Map<String, Integer>>();
		for (String string : split) {
			Map<String,String> map = new HashMap<String,String>();
			Map<String,Integer> map2 = new HashMap<String,Integer>();
			Map<String,Integer> map3 = new HashMap<String,Integer>();
			int checkNum = jiaoyujuReportDao.activityByCheckNum(activityId,string);
			int jinshiqianqi = jiaoyujuReportDao.jinshiqianqi(activityId,string);
			double double1 = checkNum==0?0:Double.parseDouble(df.format((double)jinshiqianqi/(double)checkNum*100));
			int jiaxingjinshi = jiaoyujuReportDao.jiaxingjinshi(activityId,string);
			double double2 = checkNum==0?0:Double.parseDouble(df.format((double)jiaxingjinshi/(double)checkNum*100));
			int didujinshi = jiaoyujuReportDao.didujinshi(activityId,string);
			double double3 = checkNum==0?0:Double.parseDouble(df.format((double)didujinshi/(double)checkNum*100));
			int zhongdujinshi = jiaoyujuReportDao.zhongdujinshi(activityId,string);
			double double4 = checkNum==0?0:Double.parseDouble(df.format((double)zhongdujinshi/(double)checkNum*100));
			int gaodujinshi = jiaoyujuReportDao.gaodujinshi(activityId,string);
			double double5 = gaodujinshi==0?0:Double.parseDouble(df.format((double)gaodujinshi/(double)checkNum*100));
			Integer jinzongy = didujinshi+zhongdujinshi+gaodujinshi;
			String jinzongr = df.format(double3+double4+double5);
			map.put("xuexiao", string);
			map.put("xuebu", jiaoyujuReportDao.getAddress(string).getXuebu());
			map.put("jiancha", String.valueOf(checkNum));
			map.put("qq", String.valueOf(jinshiqianqi));
			map.put("jx", String.valueOf(jiaxingjinshi));
			map.put("dd", String.valueOf(didujinshi));
			map.put("zd", String.valueOf(zhongdujinshi));
			map.put("gd", String.valueOf(gaodujinshi));
			map.put("qqr", String.valueOf(double1));
			map.put("jxr", String.valueOf(double2));
			map.put("ddr", String.valueOf(double3));
			map.put("zdr", String.valueOf(double4));
			map.put("gdr", String.valueOf(double5));
			map.put("zz", String.valueOf(jinzongy));
			map.put("zzr", jinzongr);
			list3.add(map);
			map2.put(jiaoyujuReportDao.getAddress(string).getXuebu(), checkNum);
			list4.add(map2);
			map3.put(jiaoyujuReportDao.getAddress(string).getXuebu(), jinzongy);
			list6.add(map3);
		}
		params.put("jinshi", list3);
		
		int jiaJS1 =0;
		int jiaJS3= 0;
		int jiaJS5= 0;
		int jiaJS7= 0;
		int jiaJS9= 0;
		int jiaJS22= 0;
		int jiaJS44=0;
		for (Map<String, String> map : list3) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("qq")){
					jiaJS1 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("jx")){
					jiaJS3 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("dd")){
					jiaJS5 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zd")){
					jiaJS7 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("gd")){
					jiaJS9 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zz")){
					jiaJS22 += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("jiancha")){
					jiaJS44 += Integer.parseInt(m.getValue());
				}
			}
		}
		params.put("AA", jiaJS1);
		params.put("BB", jiaJS44==0?0:df.format(((double)jiaJS1/(double)jiaJS44)*100));
		params.put("CC", jiaJS3);
		params.put("DD", jiaJS44==0?0:df.format(((double)jiaJS3/(double)jiaJS44)*100));
		params.put("EE", jiaJS5);
		params.put("FF", jiaJS44==0?0:df.format(((double)jiaJS5/(double)jiaJS44)*100));
		params.put("GG", jiaJS7);
		params.put("HH", jiaJS44==0?0:df.format(((double)jiaJS7/(double)jiaJS44)*100));
		params.put("II", jiaJS9);
		params.put("JJ", jiaJS44==0?0:df.format(((double)jiaJS9/(double)jiaJS44)*100));
		params.put("KK", jiaJS22);
		params.put("LL", jiaJS44==0?0:df.format(((double)jiaJS22/(double)jiaJS44)*100));
		params.put("MM", jiaJS44);
		
		int sy =0;
		int sx = 0;
		int sc= 0;
		int sg= 0;
		for (Map<String, Integer> map : list4) {
			for(Map.Entry<String, Integer> m : map.entrySet()){
				if(m.getKey().equals("幼儿园")){
					sy += m.getValue();
				}
				if(m.getKey().equals("小学")){
					sx += m.getValue();
				}
				if(m.getKey().equals("初中")){
					sc += m.getValue();
				}
				if(m.getKey().equals("高中")){
					sg += m.getValue();
				}
			}
		}
		params.put("sy", sy);
		params.put("sx", sx);
		params.put("sc", sc);
		params.put("sg", sg);
		
		//不良
		List<Map<String, String>> list5 = new ArrayList<Map<String, String>>();
		for (String string : split) {
			Map<String,String> map = new HashMap<String,String>();
			int checkNum = jiaoyujuReportDao.activityByCheckNum(activityId,string);
			int qingdubuliang = jiaoyujuReportDao.qingdubuliang(activityId,string);
			double double1 = checkNum==0?0:Double.parseDouble(df.format((double)qingdubuliang/(double)checkNum*100));
			int zhongdubuliang = jiaoyujuReportDao.zhongdubuliang(activityId,string);
			double double2 = checkNum==0?0:Double.parseDouble(df.format((double)zhongdubuliang/(double)checkNum*100));
			int gaodubuliang = jiaoyujuReportDao.gaodubuliang(activityId,string);
			double double3 = checkNum==0?0:Double.parseDouble(df.format((double)gaodubuliang/(double)checkNum*100));
			int buliangtotal = jiaoyujuReportDao.buliangtotal(activityId,string);
			double double4 = checkNum==0?0:Double.parseDouble(df.format((double)buliangtotal/(double)checkNum*100));
			map.put("xuexiao", string);
			map.put("xuebu", jiaoyujuReportDao.getAddress(string).getXuebu());
			map.put("jiancha", String.valueOf(checkNum));
			map.put("qd", String.valueOf(qingdubuliang));
			map.put("qdr", String.valueOf(double1));
			map.put("zd", String.valueOf(zhongdubuliang));
			map.put("zdr", String.valueOf(double2));
			map.put("zzd", String.valueOf(gaodubuliang));
			map.put("zzdr", String.valueOf(double3));
			map.put("bl", String.valueOf(buliangtotal));
			map.put("blr", String.valueOf(double4));
			
			list5.add(map);
		}
		params.put("biliang", list5);
		
		int checkT = 0;
		int qingNT = 0;
		int zhongNT= 0;
		int zzhongNT= 0;
		int bulingNT=0;
		for (Map<String, String> map : list5) {
			for(Map.Entry<String, String> m : map.entrySet()){
				if(m.getKey().equals("jiancha")){
					checkT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("qd")){
					qingNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zd")){
					zhongNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("zzd")){
					zzhongNT += Integer.parseInt(m.getValue());
				}
				if(m.getKey().equals("bl")){
					bulingNT += Integer.parseInt(m.getValue());
				}
			}
		}
		
		params.put("NN", checkT);
		params.put("OO", qingNT);
		params.put("PP", checkT==0?0:df.format(((double)qingNT/(double)checkT)*100));
		params.put("QQ", zhongNT);
		params.put("RR", checkT==0?0:df.format(((double)zhongNT/(double)checkT)*100));
		params.put("SS", zzhongNT);
		params.put("TT", checkT==0?0:df.format(((double)zzhongNT/(double)checkT)*100));
		params.put("UU", bulingNT);
		params.put("VV", checkT==0?0:df.format(((double)bulingNT/(double)checkT)*100));		
		
		int daijingNum = 0;
		int jiaozhengNum = 0;
		for (String string : split) {
			daijingNum += jiaoyujuReportDao.daijingrenshu(activityId,string);
			jiaozhengNum += jiaoyujuReportDao.jiaozhengbuzurenshu(activityId, string);
		}
		String format = checkT==0?"0":df.format(((double)daijingNum/(double)checkT)*100);
		String jiaozheng = daijingNum==0?"0":df.format(((double)jiaozhengNum/(double)daijingNum)*100);
		params.put("dj", daijingNum);
		params.put("djr", format);
		params.put("jz", jiaozhengNum);
		params.put("jzr", jiaozheng);
		
		double yo = 0;
		double xi = 0;
		double ch = 0;
		double ga = 0;
		for (Map<String, Integer> map : list6) {
			for(Map.Entry<String, Integer> m : map.entrySet()){
				if(m.getKey().equals("幼儿园")){
					yo += m.getValue();
				}
				if(m.getKey().equals("小学")){
					xi += m.getValue();
				}
				if(m.getKey().equals("初中")){
					ch += m.getValue();
				}
				if(m.getKey().equals("高中")){
					ga += m.getValue();
				}
			}
		}
		params.put("yo", sy==0?0:df.format(((double)yo/(double)sy)*100));
		params.put("xi", sx==0?0:df.format(((double)xi/(double)sx)*100));
		params.put("ch", sc==0?0:df.format(((double)ch/(double)sc)*100));
		params.put("ga", sg==0?0:df.format(((double)ga/(double)sg)*100));
		
		int nan = 0;
		int nanr = 0;
		int nv = 0;
		int nvr = 0;
		for (String string : split) {
			nan += jiaoyujuReportDao.sexCheckNum(activityId,1,string);
			nanr += jiaoyujuReportDao.sexJinshiNum(activityId,1,string);
			nv += jiaoyujuReportDao.sexCheckNum(activityId,2,string);
			nvr += jiaoyujuReportDao.sexJinshiNum(activityId,2,string);
		}
		params.put("WW", nan);
		params.put("XX", nv);
		params.put("YY", nanr);
		params.put("ZZ", nvr);
		params.put("njsr", nan==0?0:df.format(((double)nanr/(double)nan)*100));
		params.put("vjsr", nv==0?0:df.format(((double)nvr/(double)nv)*100));
		
		return params;
		
	}

	//各年级近视（教育局）
	@Override
	public Map<String, List<Double>> suoyounianjijinshi(HttpServletRequest request) {
		Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();				
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));	
		String[] parameter = request.getParameterValues("school[]");
		
		double double1 = suoyounianjijinshi2(activityId,parameter,"幼儿园");
		double double2 = suoyounianjijinshi2(activityId,parameter,"一年级");
		double double3 = suoyounianjijinshi2(activityId,parameter,"二年级");
		double double4 = suoyounianjijinshi2(activityId,parameter,"三年级");
		double double5 = suoyounianjijinshi2(activityId,parameter,"四年级");
		double double6 = suoyounianjijinshi2(activityId,parameter,"五年级");
		double double7 = suoyounianjijinshi2(activityId,parameter,"六年级");
		double double8 = suoyounianjijinshi2(activityId,parameter,"初一");
		double double9 = suoyounianjijinshi2(activityId,parameter,"初二");
		double double11 = suoyounianjijinshi2(activityId,parameter,"初三");
		double double21 = suoyounianjijinshi2(activityId,parameter,"高一");
		double double31 = suoyounianjijinshi2(activityId,parameter,"高二");
		double double41 = suoyounianjijinshi2(activityId,parameter,"高三");
		
		myt.add(double1); myt.add(double2); myt.add(double3);
		myt.add(double4); myt.add(double5); myt.add(double6);
		myt.add(double7); myt.add(double8); myt.add(double9);
		myt.add(double11); myt.add(double21); myt.add(double31);myt.add(double41);
		jinshi.put("jinshi", myt);
		return jinshi;
	}
	
	public double suoyounianjijinshi2(Integer activityId,String[] school,String grade){
		DecimalFormat df = new DecimalFormat("0.0");
		int renshu = 0;
		int jinshi = 0;
		for (String string : school) {
			 renshu += jiaoyujuReportDao.nowCheckNum(activityId,string,grade);	
			 jinshi += jiaoyujuReportDao.nowgradejinshi(activityId,string,grade);
		}
		double double1 = renshu==0?0:Double.parseDouble(df.format((double)jinshi/(double)renshu*100));
		return double1;
		
	}

	//不良（教育局）
	@Override
	public Map<String, List<Double>> suoyounianjibuliang(HttpServletRequest request) {
		Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
		List<Double> myt = new ArrayList<Double>();
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String[] parameter = request.getParameterValues("school[]");
		double double1 = suoyounianjibuliang2(activityId,parameter,"幼儿园");		
		double double2 = suoyounianjibuliang2(activityId,parameter,"一年级");		
		double double3 = suoyounianjibuliang2(activityId,parameter,"二年级");		
		double double4 = suoyounianjibuliang2(activityId,parameter,"三年级");		
		double double5 = suoyounianjibuliang2(activityId,parameter,"四年级");		
		double double6 = suoyounianjibuliang2(activityId,parameter,"五年级");		
		double double7 = suoyounianjibuliang2(activityId,parameter,"六年级");		
		double double8 = suoyounianjibuliang2(activityId,parameter,"初一");		
		double double9 = suoyounianjibuliang2(activityId,parameter,"初二");		
		double double11 = suoyounianjibuliang2(activityId,parameter,"初三");		
		double double21 = suoyounianjibuliang2(activityId,parameter,"高一");
		double double31 = suoyounianjibuliang2(activityId,parameter,"高二");
		double double41 = suoyounianjibuliang2(activityId,parameter,"高三");
		myt.add(double1); myt.add(double2); myt.add(double3);
		myt.add(double4); myt.add(double5); myt.add(double6);
		myt.add(double7); myt.add(double8); myt.add(double9);
		myt.add(double11); myt.add(double21); myt.add(double31);myt.add(double41);
		jinshi.put("buliang", myt);
		return jinshi;
	}
	
	public double suoyounianjibuliang2(Integer activityId,String[] school,String grade){
		DecimalFormat df = new DecimalFormat("0.0");
		int renshu = 0;
		int jinshi = 0;
		for (String string : school) {
			 renshu += jiaoyujuReportDao.nowCheckNum(activityId,string,grade);
			 jinshi += jiaoyujuReportDao.nowgradebuliang(activityId,string,grade);
		}
		double double1 = renshu==0?0:Double.parseDouble(df.format((double)jinshi/(double)renshu*100));
		return double1;
		
	}

	//各年龄近视（教育局）
	@Override
	public Map<String, Object> genianlingjinshiyear(HttpServletRequest request) {
		Map<String, Object> jinshi2 = new HashMap<String, Object>();
		Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
		//String[] parameter = request.getParameterValues("school[]");
		//String cityname = jiaoyujuReportDao.getAddress(parameter[0]).getAreaname();
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String[] parameter = request.getParameterValues("school[]");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		List<Double> myt1 = new ArrayList<Double>();
		double youer1 = genianlingjinshiyear2("幼儿园",activityId , parameter,sdf.format(xian));
		double xiaoxue1 = genianlingjinshiyear2("小学",activityId , parameter,sdf.format(xian));
		double chuzhong1 = genianlingjinshiyear2("初中",activityId , parameter,sdf.format(xian));
		double gaozhong1 = genianlingjinshiyear2("高中",activityId , parameter,sdf.format(xian));
		myt1.add(youer1);
		myt1.add(xiaoxue1);
		myt1.add(chuzhong1);
		myt1.add(gaozhong1);
		jinshi.put(sdf.format(xian), myt1);
		List<Double> myt2 = new ArrayList<Double>();
		double youer2 = genianlingjinshiyear2("幼儿园",activityId , parameter,sdf.format(qu));
		double xiaoxue2 = genianlingjinshiyear2("小学",activityId , parameter,sdf.format(qu));
		double chuzhong2 = genianlingjinshiyear2("初中",activityId , parameter,sdf.format(qu));
		double gaozhong2 = genianlingjinshiyear2("高中",activityId , parameter,sdf.format(qu));
		myt2.add(youer2);
		myt2.add(xiaoxue2);
		myt2.add(chuzhong2);
		myt2.add(gaozhong2);
		jinshi.put(sdf.format(qu), myt2);
		List<Double> myt3 = new ArrayList<Double>();
		double youer3 = genianlingjinshiyear2("幼儿园",activityId , parameter,sdf.format(qian));
		double xiaoxue3 = genianlingjinshiyear2("小学",activityId , parameter,sdf.format(qian));
		double chuzhong3 = genianlingjinshiyear2("初中",activityId , parameter,sdf.format(qian));
		double gaozhong3 = genianlingjinshiyear2("高中",activityId , parameter,sdf.format(qian));
		myt3.add(youer3);
		myt3.add(xiaoxue3);
		myt3.add(chuzhong3);
		myt3.add(gaozhong3);
		jinshi.put(sdf.format(qian), myt3);
		jinshi2.put("nianling", jinshi);
		return jinshi2;
	}
	
	public double genianlingjinshiyear2(String xueBu,Integer activityId ,String[] school,String checkDate){
		DecimalFormat df = new DecimalFormat("0.0");
		int renshu = 0;
		int jinshi = 0;
		for (String string : school) {
			 renshu += jiaoyujuReportDao.linianXuebuRenshu(xueBu,activityId,string,checkDate);
			 jinshi += jiaoyujuReportDao.linianXueBujinshi(xueBu,activityId,string,checkDate);
		}
		
		double double1 = renshu==0?0:Double.parseDouble(df.format((double)jinshi/(double)renshu*100));
		return double1;
		
	}

	//男女近视（教育局）
	@Override
	public Map<String, Object> nannvjinshiyear(HttpServletRequest request) {
		Map<String, Object> jinshi = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String,Object>();
		Map<String, Object> map2 = new HashMap<String,Object>();
		//String[] parameter = request.getParameterValues("school[]");
		//String cityname = jiaoyujuReportDao.getAddress(parameter[0]).getAreaname();
		Integer activityId = Integer.valueOf(request.getParameter("activityId"));
		String[] parameter = request.getParameterValues("school[]");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		Date xian = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qu = cal.getTime();
		cal.add(Calendar.YEAR, -1); 
		Date qian = cal.getTime();
		double nan1 = nannvjinshiyear2(1,activityId,parameter,sdf.format(xian));
		double nan2 = nannvjinshiyear2(1,activityId,parameter,sdf.format(qu));
		double nan3 = nannvjinshiyear2(1,activityId,parameter,sdf.format(qian));
		map1.put(sdf.format(xian), nan1);
		map1.put(sdf.format(qu), nan2);
		map1.put(sdf.format(qian), nan3);
		double nv1 = nannvjinshiyear2(2,activityId,parameter,sdf.format(xian));
		double nv2 = nannvjinshiyear2(2,activityId,parameter,sdf.format(qu));
		double nv3 = nannvjinshiyear2(2,activityId,parameter,sdf.format(qian));
		map2.put(sdf.format(xian), nv1);
		map2.put(sdf.format(qu), nv2);
		map2.put(sdf.format(qian), nv3);
		jinshi.put("nan", map1);
		jinshi.put("nv", map2);
		return jinshi;
	}
	
	public double nannvjinshiyear2(Integer studentSex,Integer activityId ,String[] school,String checkDate) {
		DecimalFormat df = new DecimalFormat("0.0");
		int renshu = 0;
		int jinshi = 0;
		for (String string : school) {
			 renshu += jiaoyujuReportDao.linianSexrenshu(studentSex,activityId,string,checkDate);
			 jinshi += jiaoyujuReportDao.linianSexjinshi(studentSex,activityId,string,checkDate);
		}
		
		double double1 = renshu==0?0:Double.parseDouble(df.format((double)jinshi/(double)renshu*100));
		return double1;
		
	}
	
	
	/**
	 * zip文件下载
	 */
	public static void craeteZipPath(String path,HttpServletResponse response) throws IOException{  

        ZipOutputStream zipOutputStream = null;
        OutputStream output=response.getOutputStream();  
//        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip");  
        response.setContentType("application/zip");  
        zipOutputStream = new ZipOutputStream(output,Charset.forName("UTF-8"));  
        File[] files = new File(path).listFiles();  
        FileInputStream fileInputStream = null;  
        byte[] buf = new byte[1024];  
        int len = 0;  
        if(files!=null && files.length > 0){  
            for(File wordFile:files){  
                String fileName = wordFile.getName();  
                fileInputStream = new FileInputStream(wordFile);  
                //放入压缩zip包中;  
                zipOutputStream.putNextEntry(new ZipEntry(fileName));  

                //读取文件;  
                while((len=fileInputStream.read(buf)) >0){  
                    zipOutputStream.write(buf, 0, len);  
                }  
                //关闭;  
                zipOutputStream.closeEntry();  
                if(fileInputStream != null){  
                    fileInputStream.close();  
                }  
            }  
        }  

        if(zipOutputStream !=null){  
            zipOutputStream.close();  
        }  
    } 
	
	
	public void createDoc(HttpServletResponse response,Map<String, Object> dataMap, String fileName, String template) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(SchoolReportNewServiceImpl.class, "/");
        Template t = null;
		//File outFile = new File(realPath + fileName);
//		Writer out = null;
        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate(template,"utf-8"); 
 //           out = new BufferedWriter(new OutputStreamWriter(
 //                   new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(fileName.getBytes(),"utf-8")))));                 //还有这里要设置编码
   //         t.process(dataMap, out);
            response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".docx");
           
			Cookie status = new Cookie("status","success");
		    status.setMaxAge(600);
		    response.addCookie(status);
			
			Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            t.process(dataMap, out);
            out.flush();
            out.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
	/**
	 * freemarker导出工具类
	 */
	
	public void download(HttpServletRequest request,HttpServletResponse response, String fileUrl, String fileName) {
		InputStream bis = null;
		OutputStream bos = null;
		try{
			fileUrl = fileUrl + fileName;
			response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".docx");
			bis = new BufferedInputStream(new FileInputStream((fileUrl)));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024];
			int bytesRead;
			int i = 0;
	
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
				i++;
			}
			bos.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bis = null;
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bos = null;
			}
		}

    }

}
