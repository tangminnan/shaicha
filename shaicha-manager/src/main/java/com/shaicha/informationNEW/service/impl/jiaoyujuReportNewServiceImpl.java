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

import com.shaicha.informationNEW.dao.SchoolReportNewDao;
import com.shaicha.informationNEW.dao.StudentNewDao;
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
public class jiaoyujuReportNewServiceImpl implements jiaoyujuReportNewService {

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
    @Autowired
    private SchoolReportNewDao schoolReportDao;
    @Autowired
    private StudentNewDao studentDao;

    //教育局报告
    @Override
    public void baogaojiaoyuju(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = jyujubaogao(request, response);
            createDoc(response, params, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "教育局报告(II).ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //新教育局报告数据
    public Map<String, Object> jyujubaogao(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月");
        DecimalFormat df = new DecimalFormat("0.0");
        Integer activityId = Integer.valueOf(request.getParameter("activityId"));
        String parameter = request.getParameter("school");
        String[] split1 = parameter.split(",");
        String date = request.getParameter("date");
        SchoolNewDO aDo = jiaoyujuReportDao.getAddress(split1[0]).get(0);
        String cityname = "";
        if (aDo != null) {
            cityname = aDo.getCityname() + aDo.getAreaname();
        }
        params.put("address", cityname);
        params.put("schoolh", parameter);
        params.put("schoolf", split1);
        params.put("newDate", sdf2.format(new Date()));
        params.put("year", String.valueOf(new Date().getYear() + 1900));
        //图
        Map<String, Object> ls = new HashMap<>();
        ls.put("type", date);
        ls.put("fore", "jyj");
        List<LinShiUrlDO> lsu = linShiUrlDao.list(ls);
        for (LinShiUrlDO linShiUrlDO : lsu) {
            params.put(linShiUrlDO.getName(), linShiUrlDO.getImgUrl());

            linShiUrlDao.remove(linShiUrlDO.getId());
        }

        UserDO userDO = userMapper.get(activityListNewDao.get(activityId).getSysId());
        params.put("zhongxin", userDO.getZhongxinName());
        params.put("schoolNum", split1.length);
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        int ddd = 0;
        int sss = 0;
        int fff = 0;
        int ggg = 0;
        for (String string : split1) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("xuexiao", string);
            ddd = jiaoyujuReportDao.schoolByCheckNum(activityId, string);
            map.put("renshu", ddd);
            fff += ddd;//总人数
            sss = jiaoyujuReportDao.activityByCheckNum(activityId, string);
            map.put("jiancha", sss);
            ggg += sss;//检查人数
            map.put("zhanbi", ddd == 0 ? 0 : Double.parseDouble(df.format((double) sss / (double) ddd * 100)));
            list1.add(map);
        }
        params.put("list1", list1);
        params.put("renshu", fff);
        params.put("jiancharenshu", ggg);
        params.put("zongzhanbi", fff == 0 ? 0 : Double.parseDouble(df.format((double) ggg / (double) fff * 100)));
        //近视
        List<Map<String, String>> list3 = new ArrayList<Map<String, String>>();
        List<Map<String, Integer>> list4 = new ArrayList<Map<String, Integer>>();
        List<Map<String, Integer>> list6 = new ArrayList<Map<String, Integer>>();
        Map<String, Object> checkMap = new HashMap<String, Object>();
        checkMap.put("activityId", activityId);
        checkMap.put("check", "check");
        for (String string : split1) {
            Map<String, String> map = new HashMap<String, String>();
            Map<String, Integer> map2 = new HashMap<String, Integer>();
            Map<String, Integer> map3 = new HashMap<String, Integer>();
            int jinshiqianqi = 0;
            int jiaxingjinshi = 0;
            int didujinshi = 0;
            int zhongdujinshi = 0;
            int gaodujinshi = 0;
            checkMap.put("school", string);
            List<StudentNewDO> studentNewDOS = studentDao.listNoShiFan(checkMap);
            Double luoyanl;
            Double luoyanr;
            Double dengxiaoqiujingR;
            Double dengxiaoqiujingL;
            for (StudentNewDO s : studentNewDOS) {
                String nakedFarvisionOd = s.getNakedFarvisionOd() == null ? "0.0" : s.getNakedFarvisionOd();
                String nakedFarvisionOs = s.getNakedFarvisionOs() == null ? "0.0" : s.getNakedFarvisionOs();
                try {
                    luoyanl = Double.parseDouble(nakedFarvisionOs);
                } catch (NumberFormatException e) {
                    luoyanl = 0.0;
                }
                try {
                    luoyanr = Double.parseDouble(nakedFarvisionOd);
                } catch (NumberFormatException e) {
                    luoyanr = 0.0;
                }
                if (s.getDengxiaoqiujingl() == null || s.getDengxiaoqiujingr() == null) continue;
                dengxiaoqiujingR = s.getDengxiaoqiujingr();
                dengxiaoqiujingL = s.getDengxiaoqiujingl();
                if ((luoyanl < 5.0 && dengxiaoqiujingL < -6.0) || (luoyanr < 5.0 && dengxiaoqiujingR < -6.0)) {
                    gaodujinshi++;
                    continue;
                }
                if ((luoyanl < 5.0 && dengxiaoqiujingL >= -6.0 && dengxiaoqiujingL < -3.0) || (luoyanr < 5.0 && dengxiaoqiujingR >= -6.0 && dengxiaoqiujingR < -3.0)) {
                    zhongdujinshi++;
                    continue;
                }
                if ((luoyanl < 5.0 && dengxiaoqiujingL < -0.5 && dengxiaoqiujingL >= -3.0) || (luoyanr < 5.0 && dengxiaoqiujingR < -0.5 && dengxiaoqiujingR >= -3.0)) {
                    didujinshi++;
                    continue;
                }
                if ((luoyanl >= 5.0 && dengxiaoqiujingL < -0.5) || (luoyanr >= 5.0 && dengxiaoqiujingR < -0.5)) {
                    jiaxingjinshi++;
                    continue;
                }
                if ((dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75 && luoyanl >= 5.0) || (dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75 && luoyanr >= 5.0)) {
                    jinshiqianqi++;
                    continue;
                }

            }
//            int jinshiqianqi = jiaoyujuReportDao.jinshiqianqi(activityId,string);
//            int jiaxingjinshi = jiaoyujuReportDao.jiaxingjinshi(activityId,string);
//            int didujinshi = jiaoyujuReportDao.didujinshi(activityId,string);
//            int zhongdujinshi = jiaoyujuReportDao.zhongdujinshi(activityId,string);
//            int gaodujinshi = jiaoyujuReportDao.gaodujinshi(activityId,string);
            int checkNum = jiaoyujuReportDao.activityByCheckNum(activityId, string);
            double double1 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) jinshiqianqi / (double) checkNum * 100));
            double double2 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) jiaxingjinshi / (double) checkNum * 100));
            double double3 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) didujinshi / (double) checkNum * 100));
            double double4 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) zhongdujinshi / (double) checkNum * 100));
            double double5 = gaodujinshi == 0 ? 0 : Double.parseDouble(df.format((double) gaodujinshi / (double) checkNum * 100));
            Integer jinzongy = didujinshi + zhongdujinshi + gaodujinshi;
            String jinzongr = df.format(double3 + double4 + double5);
            map.put("xuexiao", string);
            map.put("xuebu", jiaoyujuReportDao.getSchoolxuebu(string, activityId).getXueBu());
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
            map2.put(jiaoyujuReportDao.getSchoolxuebu(string, activityId).getXueBu(), checkNum); //xuebu检查人数
            list4.add(map2);
            map3.put(jiaoyujuReportDao.getSchoolxuebu(string, activityId).getXueBu(), jinzongy);// 学部近视人数
            list6.add(map3);
        }
        params.put("schooljinshi", list3);

        int jiaJS1 = 0;
        int jiaJS3 = 0;
        int jiaJS5 = 0;
        int jiaJS7 = 0;
        int jiaJS9 = 0;
        int jiaJS22 = 0;
        int jiaJS44 = 0;
        for (Map<String, String> map : list3) {
            for (Map.Entry<String, String> m : map.entrySet()) {
                if (m.getKey().equals("qq")) {
                    jiaJS1 += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("jx")) {
                    jiaJS3 += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("dd")) {
                    jiaJS5 += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("zd")) {
                    jiaJS7 += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("gd")) {
                    jiaJS9 += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("zz")) {
                    jiaJS22 += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("jiancha")) {
                    jiaJS44 += Integer.parseInt(m.getValue());
                }
            }
        }
        params.put("lcqqnum", jiaJS1);
        params.put("lcqqlv", jiaJS44 == 0 ? 0 : df.format(((double) jiaJS1 / (double) jiaJS44) * 100));
        params.put("jxnum", jiaJS3);
        params.put("jxlv", jiaJS44 == 0 ? 0 : df.format(((double) jiaJS3 / (double) jiaJS44) * 100));
        params.put("ddnum", jiaJS5);
        params.put("ddlv", jiaJS44 == 0 ? 0 : df.format(((double) jiaJS5 / (double) jiaJS44) * 100));
        params.put("zdnum", jiaJS7);
        params.put("zdlv", jiaJS44 == 0 ? 0 : df.format(((double) jiaJS7 / (double) jiaJS44) * 100));
        params.put("gdnum", jiaJS9);
        params.put("gulv", jiaJS44 == 0 ? 0 : df.format(((double) jiaJS9 / (double) jiaJS44) * 100));
        params.put("jsnum", jiaJS22);
        params.put("jslv", jiaJS44 == 0 ? 0 : df.format(((double) jiaJS22 / (double) jiaJS44) * 100));
        params.put("schoolnum", jiaJS44);
        int sy = 0;
        ddd = 0;
        fff = 0;
        sss = 0;
        ggg = 0;
        int sx = 0;
        int sc = 0;
        int sg = 0;
        for (Map<String, Integer> map : list4) {
            for (Map.Entry<String, Integer> m : map.entrySet()) {
                if (m.getKey().equals("幼儿园")) {
                    ddd++; //幼儿园数
                    sy += m.getValue();
                }
                if (m.getKey().equals("小学")) {
                    fff++;//小学数
                    sx += m.getValue();
                }
                if (m.getKey().equals("初中")) {
                    sss++;//初中数
                    sc += m.getValue();
                }
                if (m.getKey().equals("高中")) {
                    ggg++;//高中数
                    sg += m.getValue();
                }
            }
        }
        params.put("you", ddd);//幼儿园数
        params.put("xiao", fff);//小学数
        params.put("chu", sss);//初中数
        params.put("gao", ggg);//高中数

        ddd = 0;
        fff = 0;
        sss = 0;
        ggg = 0;
        for (Map<String, Integer> map : list6) {
            for (Map.Entry<String, Integer> m : map.entrySet()) {
                if (m.getKey().equals("幼儿园")) {
                    ddd += m.getValue();
                }
                if (m.getKey().equals("小学")) {
                    fff += m.getValue();
                }
                if (m.getKey().equals("初中")) {
                    sss += m.getValue();
                }
                if (m.getKey().equals("高中")) {
                    ggg += m.getValue();
                }
            }
        }
        params.put("youlv", sy == 0 ? 0 : df.format(((double) ddd / (double) sy) * 100));
        params.put("xiaolv", sx == 0 ? 0 : df.format(((double) fff / (double) sx) * 100));
        params.put("chulv", sc == 0 ? 0 : df.format(((double) sss / (double) sc) * 100));
        params.put("gaolv", sg == 0 ? 0 : df.format(((double) ggg / (double) sg) * 100));
        //不良
        List<Map<String, String>> list5 = new ArrayList<Map<String, String>>();
        for (String string : split1) {
            Map<String, String> map = new HashMap<String, String>();
            int checkNum = jiaoyujuReportDao.activityByCheckNum(activityId, string);
            int qingdubuliang = jiaoyujuReportDao.qingdubuliang(activityId, string);
            double double1 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) qingdubuliang / (double) checkNum * 100));
            int zhongdubuliang = jiaoyujuReportDao.zhongdubuliang(activityId, string);
            double double2 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) zhongdubuliang / (double) checkNum * 100));
            int gaodubuliang = jiaoyujuReportDao.gaodubuliang(activityId, string);
            double double3 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) gaodubuliang / (double) checkNum * 100));
            int buliangtotal = jiaoyujuReportDao.buliangtotal(activityId, string);
            double double4 = checkNum == 0 ? 0 : Double.parseDouble(df.format((double) buliangtotal / (double) checkNum * 100));
            map.put("xuexiao", string);
            map.put("xuebu", jiaoyujuReportDao.getSchoolxuebu(string, activityId).getXueBu());
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
        params.put("schoolbuliang", list5);

        int checkT = 0;
        int qingNT = 0;
        int zhongNT = 0;
        int zzhongNT = 0;
        int bulingNT = 0;
        for (Map<String, String> map : list5) {
            for (Map.Entry<String, String> m : map.entrySet()) {
                if (m.getKey().equals("jiancha")) {
                    checkT += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("qd")) {
                    qingNT += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("zd")) {
                    zhongNT += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("zzd")) {
                    zzhongNT += Integer.parseInt(m.getValue());
                }
                if (m.getKey().equals("bl")) {
                    bulingNT += Integer.parseInt(m.getValue());
                }
            }
        }

        params.put("num", checkT);
        params.put("qingnum", qingNT);
        params.put("qinglv", checkT == 0 ? 0 : df.format(((double) qingNT / (double) checkT) * 100));
        params.put("zhongnum", zhongNT);
        params.put("zhonglv", checkT == 0 ? 0 : df.format(((double) zhongNT / (double) checkT) * 100));
        params.put("SS", zzhongNT);
        params.put("TT", checkT == 0 ? 0 : df.format(((double) zzhongNT / (double) checkT) * 100));
        params.put("UU", bulingNT);
        params.put("VV", checkT == 0 ? 0 : df.format(((double) bulingNT / (double) checkT) * 100));
        //戴镜 矫正不足
        int daijingNum = 0;
        int jiaozhengNum = 0;
        for (String string : split1) {
            daijingNum += jiaoyujuReportDao.daijingrenshu(activityId, string);
            jiaozhengNum += jiaoyujuReportDao.jiaozhengbuzurenshu(activityId, string);
        }
        String format = checkT == 0 ? "0" : df.format(((double) daijingNum / (double) checkT) * 100);
        String jiaozheng = daijingNum == 0 ? "0" : df.format(((double) jiaozhengNum / (double) daijingNum) * 100);
        params.put("dj", daijingNum);
        params.put("djr", format);
        params.put("jz", jiaozhengNum);
        params.put("jzr", jiaozheng);

        int nan = 0;
        int nanr = 0;
        int nv = 0;
        int nvr = 0;
        for (String string : split1) {
            nan += jiaoyujuReportDao.sexCheckNum(activityId, 1, string);
            nanr += jiaoyujuReportDao.sexJinshiNum(activityId, 1, string);
            nv += jiaoyujuReportDao.sexCheckNum(activityId, 2, string);
            nvr += jiaoyujuReportDao.sexJinshiNum(activityId, 2, string);
        }
        params.put("WW", nan);
        params.put("XX", nv);
        params.put("YY", nanr);
        params.put("ZZ", nvr);
        params.put("njsr", nan == 0 ? 0 : df.format(((double) nanr / (double) nan) * 100));
        params.put("vjsr", nv == 0 ? 0 : df.format(((double) nvr / (double) nv) * 100));

        if (Double.parseDouble(params.get("njsr").toString()) >= Double.parseDouble(params.get("vjsr").toString())) {
            params.put("first", "男生");
            params.put("second", "女生");
            params.put("baifen", Double.parseDouble(params.get("njsr").toString()) - Double.parseDouble(params.get("vjsr").toString()));
        } else {
            params.put("second", "男生");
            params.put("first", "女生");
            params.put("baifen", Double.parseDouble(params.get("vjsr").toString()) - Double.parseDouble(params.get("njsr").toString()));
        }
        xuexiaobaogao(params, activityId, split1, date);

        return params;
    }

    public void xuexiaobaogao(Map<String, Object> result, Integer activityId, String[] split1, String date) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (String school : split1) {
            Map<String, Object> params = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            DecimalFormat df = new DecimalFormat("0.0");

            params.put("schoolName", school);
            params.put("newDate", sdf.format(new Date()));

            //图
            Map<String, Object> ls = new HashMap<>();
            ls.put("type", date);
            ls.put("fore", school);
            List<LinShiUrlDO> lsu = linShiUrlDao.list(ls);
            for (LinShiUrlDO linShiUrlDO : lsu) {
                params.put(linShiUrlDO.getName(), linShiUrlDO.getImgUrl());

                linShiUrlDao.remove(linShiUrlDO.getId());
            }
            Map<String, Object> checkMap = new HashMap<String, Object>();
            checkMap.put("activityId", activityId);
            checkMap.put("school", school);

            //基本情况
            int zongNum = schoolReportDao.zongNum(activityId, school);
            int checkNum = schoolReportDao.activityByCheckNum(activityId, school);
            params.put("schoolNum", zongNum);
            params.put("checkNum", checkNum);
            params.put("checkRate", zongNum == 0 ? 0 : df.format((double) checkNum / ((double) zongNum) * 100));
            List<Map<String, Object>> listg = new ArrayList<Map<String, Object>>();
            List<StudentNewDO> gradeshu = studentDao.querySchoolGrade(school, activityId);
            for (StudentNewDO studentDO : gradeshu) {
                Map<String, Object> gNum = new HashMap<>();
                int gradeNum = schoolReportDao.gradeCheckNum(activityId, school, studentDO.getGrade());
                int gradeByCheckNum = schoolReportDao.activityGradeByCheckNum(activityId, school, studentDO.getGrade());
                gNum.put("nianji", studentDO.getGrade());
                gNum.put("gradeNum", gradeNum);
                gNum.put("gradeCheckNum", gradeByCheckNum);
                gNum.put("gradeCheckRate", gradeNum == 0 ? 0 : df.format((double) gradeByCheckNum / ((double) gradeNum) * 100));
                listg.add(gNum);
            }
            params.put("nianjice", listg);

            //男女近视
            int nanCheckNum = schoolReportDao.activitySexByCheckNum(activityId, school, 1);
            checkMap.put("studentSex", 1);
            checkMap.put("shili", "jinshi");
            int nanjinshi = studentDao.countNoShiFan(checkMap);
            params.put("checkNanNum", nanCheckNum);
            params.put("myopiaNanRate", nanCheckNum == 0 ? 0 : df.format(((double) nanjinshi / (double) nanCheckNum) * 100));
            params.put("myopiaNanNum", nanjinshi);
            int nvCheckNum = schoolReportDao.activitySexByCheckNum(activityId, school, 2);
            checkMap.put("studentSex", 2);
            int nvjinshi = studentDao.countNoShiFan(checkMap);
            params.put("checkNvNum", nvCheckNum);
            params.put("myopiaNvRate", nvCheckNum == 0 ? 0 : df.format(((double) nvjinshi / (double) nvCheckNum) * 100));
            params.put("myopiaNvNum", nvjinshi);

            //男近视
            List<Map<String, Object>> listnn = new ArrayList<Map<String, Object>>();
            List<StudentNewDO> gradenn = studentDao.querySchoolGrade(school, activityId);
            checkMap.put("studentSex", 1);
            for (StudentNewDO studentDO : gradenn) {
                Map<String, Object> nnNum = new HashMap<>();
                checkMap.put("grade", studentDO.getGrade());
                int nanGradeCheckNum = schoolReportDao.activityGradeSexByCheckNum(activityId, school, studentDO.getGrade(), 1);
                int nanGradeCheckjinshi = studentDao.countNoShiFan(checkMap);
                nnNum.put("checkNanNum", nanGradeCheckNum);
                nnNum.put("myopiaNanRate", nanGradeCheckNum == 0 ? 0 : df.format(((double) nanGradeCheckjinshi / (double) nanGradeCheckNum) * 100));
                nnNum.put("nj", studentDO.getGrade());
                nnNum.put("myopiaNanNum", nanGradeCheckjinshi);
                listnn.add(nnNum);
            }
            params.put("listnn", listnn);

            //女近视
            List<Map<String, Object>> listmm = new ArrayList<Map<String, Object>>();
            List<StudentNewDO> grademm = studentDao.querySchoolGrade(school, activityId);
            checkMap.put("studentSex", 2);
            for (StudentNewDO studentDO : grademm) {
                Map<String, Object> mmNum = new HashMap<>();
                checkMap.put("grade", studentDO.getGrade());
                int nvGradeCheckNum = schoolReportDao.activityGradeSexByCheckNum(activityId, school, studentDO.getGrade(), 2);
                int nvGradeCheckjinshi = studentDao.countNoShiFan(checkMap);
                mmNum.put("checkNvNum", nvGradeCheckNum);
                mmNum.put("myopiaNvRate", nvGradeCheckNum == 0 ? 0 : df.format(((double) nvGradeCheckjinshi / (double) nvGradeCheckNum) * 100));
                mmNum.put("nj", studentDO.getGrade());
                mmNum.put("myopiaNvNum", nvGradeCheckjinshi);
                listmm.add(mmNum);
            }
            params.put("listmm", listmm);

            //各班级近视率
//            List<Map<String,Object>> listbj = new ArrayList<Map<String,Object>>();
//
//            List<StudentNewDO> gradebj = studentDao.querySchoolGrade(school,activityId);
//            for (StudentNewDO studentDO : gradebj) {
//                Map<String,Object> yi = new HashMap<String,Object>();
//                List<Map<String,Object>> aa = new ArrayList<Map<String,Object>>();
//                Map<String,Object> mapClassyi = new HashMap<String,Object>();
//                mapClassyi.put("school", school);
//                mapClassyi.put("activityId", activityId);
//                mapClassyi.put("grade", studentDO.getGrade());
//                List<StudentNewDO> classCountyi = studentDao.queryGradeClassCount(mapClassyi);
//                for(StudentNewDO stu : classCountyi){
//                    Map<String,Object> classyi = new HashMap<String,Object>();
//                    mapClassyi.put("studentClass", stu.getStudentClass());
//                    int jcyi = schoolReportDao.activityGradeClassByCheckNum(activityId,school,studentDO.getGrade(),stu.getStudentClass());
//                    mapClassyi.put("shili", "jinshi");
//                    int jsyi = studentDao.countNoShiFan(mapClassyi);
//                    mapClassyi.put("shili", "buliang");
//                    int blyi = studentDao.countNoShiFan(mapClassyi);
//                    classyi.put("classNum", jcyi);
//                    classyi.put("class", stu.getStudentClass());
//                    classyi.put("classMyopiaRate", jcyi==0?0:df.format(((double)jsyi/(double)jcyi*100)));
//                    classyi.put("classMyopiaNum", jsyi);
//                    classyi.put("classbuliangRate", jcyi==0?0:df.format(((double)blyi/(double)jcyi*100)));
//                    classyi.put("classbuliang", blyi);
//                    aa.add(classyi);
//                }
//                yi.put("grade", studentDO.getGrade());
//                yi.put("classyi", aa);
//                listbj.add(yi);
//            }
//            params.put("firstClass", listbj);

            //不良
            List<Map<String, String>> listT = new ArrayList<Map<String, String>>();

            List<StudentNewDO> gradebl = studentDao.querySchoolGrade(school, activityId);
            for (StudentNewDO studentDO : gradebl) {
                Map<String, String> gradey = new HashMap<String, String>();
                int qingduyi = 0;
                int zhongduyi = 0;
                int zzhongduyi = 0;
                int buliangyi = 0;
                String qingduyiR = "0";
                String zhongduyiR = "0";
                String zzhongduyiR = "0";
                String buliangyiR = "0";
                int gradeCheckNum = schoolReportDao.activityGradeByCheckNum(activityId, school, studentDO.getGrade());
                qingduyi = schoolReportDao.qingdubuliang(activityId, school, studentDO.getGrade());
                zhongduyi = schoolReportDao.zhongdubuliang(activityId, school, studentDO.getGrade());
                zzhongduyi = schoolReportDao.gaodubuliang(activityId, school, studentDO.getGrade());
                buliangyi = schoolReportDao.buliangTotal(activityId, school, studentDO.getGrade());
                qingduyiR = gradeCheckNum == 0 ? "0" : df.format(((double) qingduyi / (double) gradeCheckNum) * 100);
                zhongduyiR = gradeCheckNum == 0 ? "0" : df.format(((double) zhongduyi / (double) gradeCheckNum) * 100);
                zzhongduyiR = gradeCheckNum == 0 ? "0" : df.format(((double) zzhongduyi / (double) gradeCheckNum) * 100);
                buliangyiR = gradeCheckNum == 0 ? "0" : df.format(((double) buliangyi / (double) gradeCheckNum) * 100);

                gradey.put("grade", studentDO.getGrade());
                gradey.put("xuebu", studentDO.getXueBu());
                gradey.put("gradeCheckNum", String.valueOf(gradeCheckNum));
                gradey.put("numQDSLBL", String.valueOf(qingduyi));
                gradey.put("rateQDSLBL", qingduyiR);
                gradey.put("numZDSLBL", String.valueOf(zhongduyi));
                gradey.put("rateZDSLBL", zhongduyiR);
                gradey.put("numZZDSLBL", String.valueOf(zzhongduyi));
                gradey.put("rateZZDSLBL", zzhongduyiR);
                gradey.put("numSLBL", String.valueOf(buliangyi));
                gradey.put("rateSLBL", buliangyiR);

                listT.add(gradey);
            }
            params.put("listbl", listT);

            int checkT = 0;
            int qingNT = 0;
            int zhongNT = 0;
            int zzhongNT = 0;
            int bulingNT = 0;
            for (Map<String, String> map : listT) {
                for (Map.Entry<String, String> m : map.entrySet()) {
                    if (m.getKey().equals("gradeCheckNum")) {
                        checkT += Integer.parseInt(m.getValue());
                    }
                    if (m.getKey().equals("numQDSLBL")) {
                        qingNT += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numZDSLBL")) {
                        zhongNT += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numZZDSLBL")) {
                        zzhongNT += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numSLBL")) {
                        bulingNT += Integer.parseInt(m.getValue());
                    }

                }
            }
            params.put("xuebu", gradebl.get(0).getXueBu());
            params.put("gradeCheckTotal", checkT);
            params.put("numQDSLBLTotal", qingNT);
            params.put("rateQDSLBLTotal", df.format(((double) qingNT / (double) checkT) * 100));
            params.put("numZDSLBLTotal", zhongNT);
            params.put("rateZDSLBLTotal", df.format(((double) zhongNT / (double) checkT) * 100));
            params.put("numZZDSLBLTotal", zzhongNT);
            params.put("rateZZDSLBLTotal", df.format(((double) zzhongNT / (double) checkT) * 100));
            params.put("numSLBLTotal", bulingNT);
            params.put("rateSLBLTotal", df.format(((double) bulingNT / (double) checkT) * 100));

            //近视
            List<Map<String, String>> jiajin = new ArrayList<Map<String, String>>();
            List<StudentNewDO> jiaxing = new ArrayList<>();
            List<StudentNewDO> gradejs = studentDao.querySchoolGrade(school, activityId);

            checkMap.remove("studentSex");
            checkMap.remove("shili");
            checkMap.put("check", "check");
            for (StudentNewDO studentDO : gradejs) {
                Map<String, String> jia1 = new HashMap<String, String>();
                Integer linchuangy = 0;
                Integer jiajinshiy = 0;
                Integer diy = 0;
                Integer zhongy = 0;
                Integer gaoy = 0;
                String linchuangr = "0";
                String jiajinshir = "0";
                String dir = "0";
                String zhongr = "0";
                String gaor = "0";
                checkMap.put("grade", studentDO.getGrade());
                List<StudentNewDO> studentNewDOS = studentDao.listNoShiFan(checkMap);
                int gradeCheckNum = schoolReportDao.activityGradeByCheckNum(activityId, school, studentDO.getGrade());
                Double luoyanl;
                Double luoyanr;
                Double dengxiaoqiujingR;
                Double dengxiaoqiujingL;
                for (StudentNewDO s : studentNewDOS) {

                    String nakedFarvisionOd = s.getNakedFarvisionOd() == null ? "0.0" : s.getNakedFarvisionOd();
                    String nakedFarvisionOs = s.getNakedFarvisionOs() == null ? "0.0" : s.getNakedFarvisionOs();
                    try {
                        luoyanl = Double.parseDouble(nakedFarvisionOs);
                    } catch (NumberFormatException e) {
                        luoyanl = 0.0;
                    }
                    try {
                        luoyanr = Double.parseDouble(nakedFarvisionOd);
                    } catch (NumberFormatException e) {
                        luoyanr = 0.0;
                    }
                    if (s.getDengxiaoqiujingl() == null || s.getDengxiaoqiujingr() == null) continue;
                    dengxiaoqiujingR = s.getDengxiaoqiujingr();
                    dengxiaoqiujingL = s.getDengxiaoqiujingl();
                    if ((luoyanl < 5.0 && dengxiaoqiujingL < -6.0) || (luoyanr < 5.0 && dengxiaoqiujingR < -6.0)) {
                        gaoy++;
                        continue;
                    }
                    if ((luoyanl < 5.0 && dengxiaoqiujingL >= -6.0 && dengxiaoqiujingL < -3.0) || (luoyanr < 5.0 && dengxiaoqiujingR >= -6.0 && dengxiaoqiujingR < -3.0)) {
                        zhongy++;
                        continue;
                    }
                    if ((luoyanl < 5.0 && dengxiaoqiujingL < -0.5 && dengxiaoqiujingL >= -3.0) || (luoyanr < 5.0 && dengxiaoqiujingR < -0.5 && dengxiaoqiujingR >= -3.0)) {
                        diy++;
                        continue;
                    }
                    if ((luoyanl >= 5.0 && dengxiaoqiujingL < -0.5) || (luoyanr >= 5.0 && dengxiaoqiujingR < -0.5)) {
                        jiajinshiy++;
                        jiaxing.add(s);
                        continue;
                    }
                    if ((dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75 && luoyanl >= 5.0) || (dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75 && luoyanr >= 5.0)) {
                        linchuangy++;
                        continue;
                    }

                }
                linchuangr = gradeCheckNum == 0 ? "0" : df.format(((double) linchuangy / (double) gradeCheckNum) * 100);
                jiajinshir = gradeCheckNum == 0 ? "0" : df.format(((double) jiajinshiy / (double) gradeCheckNum) * 100);
                dir = gradeCheckNum == 0 ? "0" : df.format(((double) diy / (double) gradeCheckNum) * 100);
                zhongr = gradeCheckNum == 0 ? "0" : df.format(((double) zhongy / (double) gradeCheckNum) * 100);
                gaor = gradeCheckNum == 0 ? "0" : df.format(((double) gaoy / (double) gradeCheckNum) * 100);
                Integer jinzongy = diy + zhongy + gaoy;
                String jinzongr = gradeCheckNum == 0 ? "0" : df.format(((double) jinzongy / (double) gradeCheckNum) * 100);
                jia1.put("grade", studentDO.getGrade());
                jia1.put("xuebu", studentDO.getXueBu());
                jia1.put("gradeCheckNum", String.valueOf(gradeCheckNum));
                jia1.put("numJSQQ", String.valueOf(linchuangy));
                jia1.put("rateJSQQ", linchuangr);
                jia1.put("numJXJS", String.valueOf(jiajinshiy));
                jia1.put("rateJXJS", jiajinshir);
                jia1.put("numDDJS", String.valueOf(diy));
                jia1.put("rateDDJS", dir);
                jia1.put("numZDJS", String.valueOf(zhongy));
                jia1.put("rateZDJS", zhongr);
                jia1.put("numGDJS", String.valueOf(gaoy));
                jia1.put("rateGDJS", gaor);
                jia1.put("numJS", String.valueOf(jinzongy));
                jia1.put("rateJS", jinzongr);

                jiajin.add(jia1);
            }
            params.put("jiajin", jiajin);

            int jiaJS1 = 0;
            int jiaJS3 = 0;
            int jiaJS5 = 0;
            int jiaJS7 = 0;
            int jiaJS9 = 0;
            int jiaJS22 = 0;
            for (Map<String, String> map2 : jiajin) {
                for (Map.Entry<String, String> m : map2.entrySet()) {
                    if (m.getKey().equals("numJSQQ")) {
                        jiaJS1 += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numJXJS")) {
                        jiaJS3 += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numDDJS")) {
                        jiaJS5 += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numZDJS")) {
                        jiaJS7 += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numGDJS")) {
                        jiaJS9 += Integer.parseInt(m.getValue());
                    }

                    if (m.getKey().equals("numJS")) {
                        jiaJS22 += Integer.parseInt(m.getValue());
                    }

                }
            }

            params.put("numJSQQTotal", jiaJS1);
            params.put("rateJSQQTotal", df.format(((double) jiaJS1 / (double) checkT) * 100));
            params.put("numJXJSTotal", jiaJS3);
            params.put("rateJXJSTotal", df.format(((double) jiaJS3 / (double) checkT) * 100));
            params.put("numDDJSTotal", jiaJS5);
            params.put("rateDDJSTotal", df.format(((double) jiaJS5 / (double) checkT) * 100));
            params.put("numZDJSTotal", jiaJS7);
            params.put("rateZDJSTotal", df.format(((double) jiaJS7 / (double) checkT) * 100));
            params.put("numGDJSTotal", jiaJS9);
            params.put("rateGDJSTotal", df.format(((double) jiaJS9 / (double) checkT) * 100));
            params.put("numJSTotal", jiaJS22);
            params.put("rateJSTotal", df.format(((double) jiaJS22 / (double) checkT) * 100));
            list.add(params);
        }
        result.put("xiexiaobaogao", list);
    }

    //各年级近视（教育局）
    @Override
    public Map<String, List<Double>> suoyounianjijinshi(HttpServletRequest request) {
        Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
        List<Double> myt = new ArrayList<Double>();
        Integer activityId = Integer.valueOf(request.getParameter("activityId"));
        String[] parameter = request.getParameterValues("school[]");

        double double1 = suoyounianjijinshi2(activityId, parameter, "幼儿园");
        double double2 = suoyounianjijinshi2(activityId, parameter, "一年级");
        double double3 = suoyounianjijinshi2(activityId, parameter, "二年级");
        double double4 = suoyounianjijinshi2(activityId, parameter, "三年级");
        double double5 = suoyounianjijinshi2(activityId, parameter, "四年级");
        double double6 = suoyounianjijinshi2(activityId, parameter, "五年级");
        double double7 = suoyounianjijinshi2(activityId, parameter, "六年级");
        double double8 = suoyounianjijinshi2(activityId, parameter, "初一");
        double double9 = suoyounianjijinshi2(activityId, parameter, "初二");
        double double11 = suoyounianjijinshi2(activityId, parameter, "初三");
        double double21 = suoyounianjijinshi2(activityId, parameter, "高一");
        double double31 = suoyounianjijinshi2(activityId, parameter, "高二");
        double double41 = suoyounianjijinshi2(activityId, parameter, "高三");

        myt.add(double1);
        myt.add(double2);
        myt.add(double3);
        myt.add(double4);
        myt.add(double5);
        myt.add(double6);
        myt.add(double7);
        myt.add(double8);
        myt.add(double9);
        myt.add(double11);
        myt.add(double21);
        myt.add(double31);
        myt.add(double41);
        jinshi.put("jinshi", myt);
        return jinshi;
    }

    public double suoyounianjijinshi2(Integer activityId, String[] school, String grade) {
        DecimalFormat df = new DecimalFormat("0.0");
        int renshu = 0;
        int jinshi = 0;
        Map<String, Object> checkMap = new HashMap<String, Object>();
        checkMap.put("activityId", activityId);
        checkMap.put("grade", grade);
        checkMap.put("shili", "jinshi");
        for (String string : school) {
            checkMap.put("school", string);
            renshu += jiaoyujuReportDao.nowCheckNum(activityId, string, grade);
            jinshi += studentDao.countNoShiFan(checkMap);
        }
        double double1 = renshu == 0 ? 0 : Double.parseDouble(df.format((double) jinshi / (double) renshu * 100));
        return double1;

    }

    //不良（教育局）
    @Override
    public Map<String, List<Double>> suoyounianjibuliang(HttpServletRequest request) {
        Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
        List<Double> myt = new ArrayList<Double>();
        Integer activityId = Integer.valueOf(request.getParameter("activityId"));
        String[] parameter = request.getParameterValues("school[]");
        double double1 = suoyounianjibuliang2(activityId, parameter, "幼儿园");
        double double2 = suoyounianjibuliang2(activityId, parameter, "一年级");
        double double3 = suoyounianjibuliang2(activityId, parameter, "二年级");
        double double4 = suoyounianjibuliang2(activityId, parameter, "三年级");
        double double5 = suoyounianjibuliang2(activityId, parameter, "四年级");
        double double6 = suoyounianjibuliang2(activityId, parameter, "五年级");
        double double7 = suoyounianjibuliang2(activityId, parameter, "六年级");
        double double8 = suoyounianjibuliang2(activityId, parameter, "初一");
        double double9 = suoyounianjibuliang2(activityId, parameter, "初二");
        double double11 = suoyounianjibuliang2(activityId, parameter, "初三");
        double double21 = suoyounianjibuliang2(activityId, parameter, "高一");
        double double31 = suoyounianjibuliang2(activityId, parameter, "高二");
        double double41 = suoyounianjibuliang2(activityId, parameter, "高三");
        myt.add(double1);
        myt.add(double2);
        myt.add(double3);
        myt.add(double4);
        myt.add(double5);
        myt.add(double6);
        myt.add(double7);
        myt.add(double8);
        myt.add(double9);
        myt.add(double11);
        myt.add(double21);
        myt.add(double31);
        myt.add(double41);
        jinshi.put("buliang", myt);
        return jinshi;
    }

    public double suoyounianjibuliang2(Integer activityId, String[] school, String grade) {
        DecimalFormat df = new DecimalFormat("0.0");
        int renshu = 0;
        int jinshi = 0;
        for (String string : school) {
            renshu += jiaoyujuReportDao.nowCheckNum(activityId, string, grade);
            jinshi += jiaoyujuReportDao.nowgradebuliang(activityId, string, grade);
        }
        double double1 = renshu == 0 ? 0 : Double.parseDouble(df.format((double) jinshi / (double) renshu * 100));
        return double1;

    }

    //各年龄近视（教育局）
    @Override
    public Map<String, Object> genianlingjinshiyear(HttpServletRequest request) {
        Map<String, Object> jinshi2 = new HashMap<String, Object>();
        Map<String, List<Double>> jinshi = new HashMap<String, List<Double>>();
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
        double youer1 = genianlingjinshiyear2("幼儿园", activityId, parameter, sdf.format(xian));
        double xiaoxue1 = genianlingjinshiyear2("小学", activityId, parameter, sdf.format(xian));
        double chuzhong1 = genianlingjinshiyear2("初中", activityId, parameter, sdf.format(xian));
        double gaozhong1 = genianlingjinshiyear2("高中", activityId, parameter, sdf.format(xian));
        myt1.add(youer1);
        myt1.add(xiaoxue1);
        myt1.add(chuzhong1);
        myt1.add(gaozhong1);
        jinshi.put(sdf.format(xian), myt1);
        List<Double> myt2 = new ArrayList<Double>();
        double youer2 = genianlingjinshiyear2("幼儿园", activityId, parameter, sdf.format(qu));
        double xiaoxue2 = genianlingjinshiyear2("小学", activityId, parameter, sdf.format(qu));
        double chuzhong2 = genianlingjinshiyear2("初中", activityId, parameter, sdf.format(qu));
        double gaozhong2 = genianlingjinshiyear2("高中", activityId, parameter, sdf.format(qu));
        myt2.add(youer2);
        myt2.add(xiaoxue2);
        myt2.add(chuzhong2);
        myt2.add(gaozhong2);
        jinshi.put(sdf.format(qu), myt2);
        List<Double> myt3 = new ArrayList<Double>();
        double youer3 = genianlingjinshiyear2("幼儿园", activityId, parameter, sdf.format(qian));
        double xiaoxue3 = genianlingjinshiyear2("小学", activityId, parameter, sdf.format(qian));
        double chuzhong3 = genianlingjinshiyear2("初中", activityId, parameter, sdf.format(qian));
        double gaozhong3 = genianlingjinshiyear2("高中", activityId, parameter, sdf.format(qian));
        myt3.add(youer3);
        myt3.add(xiaoxue3);
        myt3.add(chuzhong3);
        myt3.add(gaozhong3);
        jinshi.put(sdf.format(qian), myt3);
        jinshi2.put("nianling", jinshi);
        return jinshi2;
    }

    @Override
    public Map<String, Object> gexuebujinshiyear(HttpServletRequest request) {
        Map<String, Object> jinshi = new HashMap<>();
        Integer activityId = Integer.valueOf(request.getParameter("activityId"));
        String[] parameter = request.getParameterValues("school[]");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        Date xian = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        Date qu = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        Date qian = cal.getTime();
        List<Double> myt1 = new ArrayList<>();
        List<Double> myt2 = new ArrayList<>();
        List<Double> myt3 = new ArrayList<>();
        List<Double> myt4 = new ArrayList<>();
        List<String> myt5 = new ArrayList<>();
        double youer3 = genianlingjinshiyear2("幼儿园", activityId, parameter, sdf.format(qian));
        double xiaoxue3 = genianlingjinshiyear2("小学", activityId, parameter, sdf.format(qian));
        double chuzhong3 = genianlingjinshiyear2("初中", activityId, parameter, sdf.format(qian));
        double gaozhong3 = genianlingjinshiyear2("高中", activityId, parameter, sdf.format(qian));
        myt1.add(youer3);
        myt2.add(xiaoxue3);
        myt3.add(chuzhong3);
        myt4.add(gaozhong3);

        double youer2 = genianlingjinshiyear2("幼儿园", activityId, parameter, sdf.format(qu));
        double xiaoxue2 = genianlingjinshiyear2("小学", activityId, parameter, sdf.format(qu));
        double chuzhong2 = genianlingjinshiyear2("初中", activityId, parameter, sdf.format(qu));
        double gaozhong2 = genianlingjinshiyear2("高中", activityId, parameter, sdf.format(qu));
        myt1.add(youer2);
        myt2.add(xiaoxue2);
        myt3.add(chuzhong2);
        myt4.add(gaozhong2);

        double youer1 = genianlingjinshiyear2("幼儿园", activityId, parameter, sdf.format(xian));
        double xiaoxue1 = genianlingjinshiyear2("小学", activityId, parameter, sdf.format(xian));
        double chuzhong1 = genianlingjinshiyear2("初中", activityId, parameter, sdf.format(xian));
        double gaozhong1 = genianlingjinshiyear2("高中", activityId, parameter, sdf.format(xian));
        myt1.add(youer1);
        myt2.add(xiaoxue1);
        myt3.add(chuzhong1);
        myt4.add(gaozhong1);

        jinshi.put("youer", myt1);
        jinshi.put("xiaoxue", myt2);
        jinshi.put("chuzhong", myt3);
        jinshi.put("gaozhong", myt4);
        myt5.add(sdf.format(qian));
        myt5.add(sdf.format(qu));
        myt5.add(sdf.format(xian));
        jinshi.put("checkyear", myt5);
        return jinshi;
    }

    public double genianlingjinshiyear2(String xueBu, Integer activityId, String[] school, String checkDate) {
        DecimalFormat df = new DecimalFormat("0.0");
        int renshu = 0;
        int jinshi = 0;
        Map<String, Object> checkMap = new HashMap<String, Object>();
        checkMap.put("activityId", activityId);
        checkMap.put("xueBu", xueBu);
        checkMap.put("linian", checkDate);
        checkMap.put("shili", "jinshi");
        for (String string : school) {
            checkMap.put("school", string);
            renshu += jiaoyujuReportDao.linianXuebuRenshu(xueBu, activityId, string, checkDate);
            jinshi += studentDao.countNoShiFan(checkMap);
        }

        double double1 = renshu == 0 ? 0 : Double.parseDouble(df.format((double) jinshi / (double) renshu * 100));
        return double1;

    }

    //男女近视（教育局）
    @Override
    public Map<String, Object> nannvjinshiyear(HttpServletRequest request) {
        Map<String, Object> jinshi = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
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
        double nan1 = nannvjinshiyear2(1, activityId, parameter, sdf.format(xian));
        double nan2 = nannvjinshiyear2(1, activityId, parameter, sdf.format(qu));
        double nan3 = nannvjinshiyear2(1, activityId, parameter, sdf.format(qian));
        map1.put(sdf.format(xian), nan1);
        map1.put(sdf.format(qu), nan2);
        map1.put(sdf.format(qian), nan3);
        double nv1 = nannvjinshiyear2(2, activityId, parameter, sdf.format(xian));
        double nv2 = nannvjinshiyear2(2, activityId, parameter, sdf.format(qu));
        double nv3 = nannvjinshiyear2(2, activityId, parameter, sdf.format(qian));
        map2.put(sdf.format(xian), nv1);
        map2.put(sdf.format(qu), nv2);
        map2.put(sdf.format(qian), nv3);
        jinshi.put("nan", map1);
        jinshi.put("nv", map2);
        return jinshi;
    }

    public double nannvjinshiyear2(Integer studentSex, Integer activityId, String[] school, String checkDate) {
        DecimalFormat df = new DecimalFormat("0.0");
        int renshu = 0;
        int jinshi = 0;
        Map<String, Object> checkMap = new HashMap<String, Object>();
        checkMap.put("activityId", activityId);
        checkMap.put("studentSex", studentSex);
        checkMap.put("shili", "jinshi");
        checkMap.put("linian", checkDate);
        for (String string : school) {
            checkMap.put("school", string);
            renshu += jiaoyujuReportDao.linianSexrenshu(studentSex, activityId, string, checkDate);
            jinshi += studentDao.countNoShiFan(checkMap);
        }

        double double1 = renshu == 0 ? 0 : Double.parseDouble(df.format((double) jinshi / (double) renshu * 100));
        return double1;

    }


    /**
     * zip文件下载
     */
    public static void craeteZipPath(String path, HttpServletResponse response) throws IOException {

        ZipOutputStream zipOutputStream = null;
        OutputStream output = response.getOutputStream();
//        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip");
        response.setContentType("application/zip");
        zipOutputStream = new ZipOutputStream(output, Charset.forName("UTF-8"));
        File[] files = new File(path).listFiles();
        FileInputStream fileInputStream = null;
        byte[] buf = new byte[1024];
        int len = 0;
        if (files != null && files.length > 0) {
            for (File wordFile : files) {
                String fileName = wordFile.getName();
                fileInputStream = new FileInputStream(wordFile);
                //放入压缩zip包中;  
                zipOutputStream.putNextEntry(new ZipEntry(fileName));

                //读取文件;  
                while ((len = fileInputStream.read(buf)) > 0) {
                    zipOutputStream.write(buf, 0, len);
                }
                //关闭;  
                zipOutputStream.closeEntry();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
        }

        if (zipOutputStream != null) {
            zipOutputStream.close();
        }
    }


    public void createDoc(HttpServletResponse response, Map<String, Object> dataMap, String fileName, String template) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(SchoolReportNewServiceImpl.class, "/");
        Template t = null;
        //File outFile = new File(realPath + fileName);
//		Writer out = null;
        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate(template, "utf-8");
            //           out = new BufferedWriter(new OutputStreamWriter(
            //                   new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(fileName.getBytes(),"utf-8")))));                 //还有这里要设置编码
            //         t.process(dataMap, out);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1") + ".docx");

            Cookie status = new Cookie("status", "success");
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

    public void download(HttpServletRequest request, HttpServletResponse response, String fileUrl, String fileName) {
        InputStream bis = null;
        OutputStream bos = null;
        try {
            fileUrl = fileUrl + fileName;
            response.setContentType("multipart/form-data");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1") + ".docx");
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
