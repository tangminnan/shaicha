package com.shaicha.informationNEW.service.impl;

import com.shaicha.common.utils.*;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.informationNEW.dao.*;
import com.shaicha.informationNEW.domain.*;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.config.BootdoConfig;

import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.informationNEW.service.StudentNewService;
import com.shaicha.system.config.ExcelUtils;
import com.shaicha.system.dao.UserDao;
import com.shaicha.system.domain.UserDO;
import com.shaicha.system.service.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class StudentNewServiceImpl implements StudentNewService {
    @Autowired
    private StudentNewDao studentNewDao;
    @Autowired
    private BootdoConfig bootdoConfig;
    @Autowired
    private SchoolNewDao schoolDao;
    @Autowired
    private ResultDiopterNewDao resultDiopterNewDao;
    @Autowired
    private ResultEyesightNewDao resultEyesightNewDao;
    @Autowired
    private ResultOptometryNewDao resultOptometryNewDao;
    @Autowired
    private ResultQuestionDao questionDao;
    @Autowired
    UserDao userMapper;


    @Override
    public StudentNewDO get(Integer id) {
        return studentNewDao.get(id);
    }

    @Override
    public List<StudentNewDO> list(Map<String, Object> map) {
        return studentNewDao.list(map);
    }

    @Override
    public List<StudentNewDO> listNoShiFan(Map<String, Object> map) {
        return studentNewDao.listNoShiFan(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return studentNewDao.count(map);
    }

    @Override
    public List<StudentNewDO> activitygetschool(Integer activityId) {
        return studentNewDao.activitygetschool(activityId);
    }

    @Override
    public List<StudentNewDO> shifanactivityid(Map<String, Object> map) {
        return studentNewDao.shifanactivityid(map);
    }

    @Override
    public List<StudentNewDO> shifanschool(Integer activityId) {
        return studentNewDao.shifanschool(activityId);
    }

    @Override
    public int save(StudentNewDO student) {
        return studentNewDao.save(student);
    }

    @Override
    public int update(StudentNewDO student) {
        return studentNewDao.update(student);
    }

    @Override
    public int updateCode(StudentNewDO studentDO) {
        return studentNewDao.updateCode(studentDO);
    }

    @Override
    public int remove(Integer id) {
        return studentNewDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return studentNewDao.batchRemove(ids);
    }

    @Override
    public List<String> getSchoolCheckDate(Integer activityId, Integer schoolId) {
        return studentNewDao.getSchoolCheckDate(activityId, schoolId);
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> importMemberm(Integer activityId, Integer schoolId, String checkType, MultipartFile file) {
        System.out.println("==============file================" + file);
        Map<String, Object> map = new HashMap<String, Object>();
        SchoolNewDO schoolDO = schoolDao.get(schoolId);
        try {
            Map<String, Object> readExcel = ImportExcel.readExcel(file, activityId, schoolDO, checkType);
            if (readExcel.get("data") == null) {
                map.put("code", -1);
                map.put("msg", readExcel.get("msg"));
            } else {
                List<StudentNewDO> student = (List<StudentNewDO>) readExcel.get("data");
                int students = student.size();
                int i = 0;
                while (students > 20) {
                    studentNewDao.insertBatch(student.subList(i, i + 20));
                    i = i + 20;
                    students = students - 20;
                }
                if (students > 0) {
                    studentNewDao.insertBatch(student.subList(i, i + students));
                }
                map.put("code", 0);
                map.put("msg", "上传成功,共增加[" + students + "]条");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * excel数据导入
     */
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public R importMember(Integer activityId, Integer schoolId, String checkType, MultipartFile file) {
        System.out.println("==============file================" + file);
        int num = 0;
        InputStream in = null;
        Workbook book = null;
        List<Integer> list = new ArrayList<>();
        try {
            if (file != null) {
                in = file.getInputStream();
                book = ExcelUtils.getBook(file);
                Sheet sheet = book.getSheetAt(0);
                Row row;
                if (!"筛查".equals(ExcelUtils.getCellFormatValue(sheet.getRow(0).getCell((short) 0))) && !"验光".equals(ExcelUtils.getCellFormatValue(sheet.getRow(0).getCell((short) 0))))
                    return R.error("请使用提供的模板进行导入!");
                //判断导入的Excel中是否有未填项
                for (int a = 2; a <= sheet.getLastRowNum(); a++) {
                    row = sheet.getRow(a);
                    if (ExcelUtils.getCellFormatValue(row.getCell((short) 0)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 0)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 1)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 1)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 2)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 2)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 3)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 3)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 4)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 4)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 5)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 5)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 6)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 6)) != null &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 7)) != "" &&
                            ExcelUtils.getCellFormatValue(row.getCell((short) 7)) != null) ;
                    else
                        return R.error("Excel中有部分基本信息数据为空，请检查好重新导入");
                }
                SchoolNewDO schoolDO = schoolDao.get(schoolId);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    try {
                        row = sheet.getRow(rowNum);
                        String ideentityType = ExcelUtils.getCellFormatValue(row.getCell((short) 0)).replaceAll("[\t\n' ']", "");//证件类型
                        String identityCard;
                        if ("身份证".equals(ideentityType)) {
                            identityCard = ExcelUtils.getCellFormatValue(row.getCell((short) 1)).replaceAll("[^xX0-9]", "");    //身份证号
                        } else {
                            identityCard = ExcelUtils.getCellFormatValue(row.getCell((short) 1)).replaceAll("[\t\n' ']", "");    //身份证号
                        }
                        String name = ExcelUtils.getCellFormatValue(row.getCell((short) 2)).replaceAll("[\t\n' ']", "");    // 姓名
                        String sex = ExcelUtils.getCellFormatValue(row.getCell((short) 3)).replaceAll("[\t\n' ']", "");//性别
                        String grade = ExcelUtils.getCellFormatValue(row.getCell((short) 4)).replaceAll("[\t\n' ']", "");        //年级
                        Cell cell = row.getCell((short) 5);
                        cell.setCellType(CellType.STRING);
                        String studentClass = ExcelUtils.getCellFormatValue(cell).replaceAll("[\t\n' ']", "");    //班级
                        cell = row.getCell((short) 6);
                        cell.setCellType(CellType.STRING);
                        String phone = ExcelUtils.getCellFormatValue(cell).replaceAll("[^0-9]", "");        //手机号
                        String nation = ExcelUtils.getCellFormatValue(row.getCell((short) 7)).replaceAll("[\t\n' ']", "");//民族

                        String nakedFarvisionOs = ExcelUtils.getCellFormatValue(row.getCell((short) 8)).replaceAll("[\t\n' ']", "");//左眼裸眼视力
                        String correctionFarvisionOs = ExcelUtils.getCellFormatValue(row.getCell((short) 9)).replaceAll("[\t\n' ']", "");//左眼戴镜视力
                        String nakedFarvisionOd = ExcelUtils.getCellFormatValue(row.getCell((short) 10)).replaceAll("[\t\n' ']", "");//右眼裸眼视力
                        String correctionFarvisionOd = ExcelUtils.getCellFormatValue(row.getCell((short) 11)).replaceAll("[\t\n' ']", "");//右眼戴镜视力
                        String diopterSOd = ExcelUtils.getCellFormatValue(row.getCell((short) 12)).replaceAll("[\t\n' ']", "");//右-球镜
                        String diopterCOd = ExcelUtils.getCellFormatValue(row.getCell((short) 13)).replaceAll("[\t\n' ']", "");//右-柱镜
                        String diopterAOd = ExcelUtils.getCellFormatValue(row.getCell((short) 14)).replaceAll("[\t\n' ']", "");//右-轴位
                        String diopterSOs = ExcelUtils.getCellFormatValue(row.getCell((short) 15)).replaceAll("[\t\n' ']", "");//左-球镜
                        String diopterCOs = ExcelUtils.getCellFormatValue(row.getCell((short) 16)).replaceAll("[\t\n' ']", "");//左-柱镜
                        String diopterAOs = ExcelUtils.getCellFormatValue(row.getCell((short) 17)).replaceAll("[\t\n' ']", "");//左-轴位
                        String checktime = ExcelUtils.getCellFormatValue(row.getCell((short) 18)).replaceAll("[\t\n' ']", "");//检查时间

                        if (!checkRealName(name)) {
                            list.add(rowNum + 1);
                            continue;
                        }
                        StudentNewDO student = new StudentNewDO();

                        student.setSchoolId(schoolId);
                        student.setSchool(schoolDO.getOrgname());
                        student.setSchoolCode(schoolDO.getOrgcode());
                        student.setAddress(schoolDO.getAreaname());
                        student.setActivityId(activityId);
                        student.setCheckType(checkType);
                        student.setStudentName(name.trim());
                        student.setPhone(phone.trim());
                        student.setNation(nation);
                        if (!"0.0".equals(nakedFarvisionOd))
                            student.setNakedFarvisionOd(nakedFarvisionOd);
                        if (!"0.0".equals(nakedFarvisionOs))
                            student.setNakedFarvisionOs(nakedFarvisionOs);
                        Double dengOd = 0.0;
                        Double dengOs = 0.0;
                        if (StringUtils.isNotBlank(diopterSOd) && StringUtils.isNotBlank(diopterCOd))
                            dengOd = Double.parseDouble(diopterSOd) + (1.0 / 2) * (Double.parseDouble(diopterCOd));
                        if (StringUtils.isNotBlank(diopterSOs) && StringUtils.isNotBlank(diopterCOs))
                            dengOs = Double.parseDouble(diopterSOs) + (1.0 / 2) * (Double.parseDouble(diopterCOs));
                        student.setDengxiaoqiujingr(dengOd);
                        student.setDengxiaoqiujingl(dengOs);
                        student.setGrade(grade.trim());
                        student.setStudentClass(studentClass.trim());
                        student.setStatus(0);
                        student.setIdeentityType(ideentityType.trim());
                        student.setXueBu(schoolDO.getXuebu());
                        student.setSysId(ShiroUtils.getUserId());
                        student.setModelType("学校");
                        if (sex != null && sex != "") {
                            if (sex.equals("男")) {
                                student.setStudentSex(1);
                            }
                            if (sex.equals("女")) {
                                student.setStudentSex(2);
                            }
                        }

                        student.setAddTime(new Date());
                        if ("身份证".equals(ideentityType)) {
                            String s = isIDCardUtil.IDCardValidate(identityCard);
//                            System.out.println(s);
                            if ("该身份证有效！".equals(isIDCardUtil.IDCardValidate(identityCard))) {
                                String year = identityCard.substring(6, 10);
                                String month = identityCard.substring(10, 12);
                                String day = identityCard.substring(12, 14);
                                String bir = year + "-" + month + "-" + day;
                                try {
                                    student.setBirthday(sdf.parse(bir));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                student.setIdentityCard(identityCard);
                            } else {
                                list.add(rowNum + 1);
                                continue;
                            }
                        } else {
                            student.setIdentityCard(identityCard);
                        }
                        Date dd = new Date();
                        if (StringUtils.isNotBlank(checktime)) {
                            if (checktime.contains("-")) {
                                dd = sdf.parse(checktime);
                                student.setLastCheckTime(dd);
                            } else {
                                Calendar calendar = new GregorianCalendar(1900, 0, -1);
                                Date d = calendar.getTime();
                                dd = DateUtils.addDays(d, Integer.parseInt(checktime.substring(0, checktime.indexOf("."))));
                                student.setLastCheckTime(dd);
                            }
                        } else if (StringUtils.isNotBlank(nakedFarvisionOd) ||
                                StringUtils.isNotBlank(nakedFarvisionOs) ||
                                StringUtils.isNotBlank(correctionFarvisionOd) ||
                                StringUtils.isNotBlank(correctionFarvisionOs) ||
                                StringUtils.isNotBlank(diopterSOd) ||
                                StringUtils.isNotBlank(diopterCOd) ||
                                StringUtils.isNotBlank(diopterAOd) ||
                                StringUtils.isNotBlank(diopterSOs) ||
                                StringUtils.isNotBlank(diopterCOs) ||
                                StringUtils.isNotBlank(diopterAOs)) {
                            student.setLastCheckTime(dd);
                        }

                        studentNewDao.save(student);
                        if (StringUtils.isNotBlank(nakedFarvisionOd) ||
                                StringUtils.isNotBlank(nakedFarvisionOs) ||
                                StringUtils.isNotBlank(correctionFarvisionOd) ||
                                StringUtils.isNotBlank(correctionFarvisionOs)) {

                            ResultEyesightNewDO resultEyesightNewDO = new ResultEyesightNewDO();
                            resultEyesightNewDO.setStudentId(student.getId());
                            resultEyesightNewDO.setActivityId(activityId);
                            resultEyesightNewDO.setIdentityCard(identityCard);
                            resultEyesightNewDO.setDeleteFlag(0);
                            if (!"0.0".equals(nakedFarvisionOd))
                                resultEyesightNewDO.setNakedFarvisionOd(nakedFarvisionOd);
                            resultEyesightNewDO.setCheckDate(dd);
                            if (!"0.0".equals(nakedFarvisionOs))
                                resultEyesightNewDO.setNakedFarvisionOs(nakedFarvisionOs);
                            if (!"0.0".equals(correctionFarvisionOd))
                                resultEyesightNewDO.setCorrectionFarvisionOd(correctionFarvisionOd);
                            if (!"0.0".equals(correctionFarvisionOs))
                                resultEyesightNewDO.setCorrectionFarvisionOs(correctionFarvisionOs);
                            resultEyesightNewDao.save(resultEyesightNewDO);

                        }
                        if (StringUtils.isNotBlank(diopterSOd) ||
                                StringUtils.isNotBlank(diopterCOd) ||
                                StringUtils.isNotBlank(diopterAOd) ||
                                StringUtils.isNotBlank(diopterSOs) ||
                                StringUtils.isNotBlank(diopterCOs) ||
                                StringUtils.isNotBlank(diopterAOs)) {
                            ResultOptometryNewDO resultOptometryDO = new ResultOptometryNewDO();
                            resultOptometryDO.setStudentId(student.getId());
                            resultOptometryDO.setCheckDate(dd);
                            resultOptometryDO.setIdentityCard(identityCard);
                            resultOptometryDO.setActivityId(activityId.toString());
                            if (resultOptometryNewDao.save(resultOptometryDO) > 0) {
                                ResultDiopterNewDO rd = new ResultDiopterNewDO();
                                rd.settOptometryId(resultOptometryDO.gettOptometryId());
                                rd.setDiopterS(Double.parseDouble(diopterSOd == "" ? "0.0" : diopterSOd));
                                rd.setDiopterC(Double.parseDouble(diopterCOd == "" ? "0.0" : diopterCOd));
                                rd.setDiopterA(Double.parseDouble(diopterAOd == "" ? "0.0" : diopterAOd));
                                rd.setBelieve(0);
                                rd.setNum(0);
                                rd.setType("AVG");
                                rd.setIfrl("R");
                                rd.setFirstSecond("FIRST_CHECK");
                                rd.setDengxiaoqiujing(dengOd);
                                rd.setActivityId(activityId.toString());
                                rd.setCheckDate(dd);
                                rd.setIdentityCard(identityCard);
                                resultDiopterNewDao.save(rd);

                                ResultDiopterNewDO rs = new ResultDiopterNewDO();
                                rs.settOptometryId(resultOptometryDO.gettOptometryId());
                                rs.setDiopterS(Double.parseDouble(diopterSOs == "" ? "0.0" : diopterSOs));
                                rs.setDiopterC(Double.parseDouble(diopterCOs == "" ? "0.0" : diopterCOs));
                                rs.setDiopterA(Double.parseDouble(diopterAOs == "" ? "0.0" : diopterAOs));
                                rs.setBelieve(0);
                                rs.setNum(0);
                                rs.setType("AVG");
                                rs.setIfrl("L");
                                rs.setFirstSecond("FIRST_CHECK");
                                rs.setDengxiaoqiujing(dengOs);
                                rs.setActivityId(activityId.toString());
                                rs.setCheckDate(dd);
                                rs.setIdentityCard(identityCard);
                                resultDiopterNewDao.save(rs);
                            }


                        }

                        num++;


                    } catch (Exception e) {
                        System.out.println("导入失败======第" + (rowNum + 1) + "条==================");
                        e.printStackTrace();
                    }

                }
                if (list.size() > 0) {
                    return R.ok("上传成功,共增加[" + num + "]条,第" + list + "行导入用户失败，原因：姓名或者身份证号可能无效");
                } else {
                    return R.ok("上传成功,共增加[" + num + "]条");
                }
            } else {
                return R.error("请选择导入的文件!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (book != null)
                    book.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return R.error();
    }

    @Override
    public List<StudentNewDO> getList() {
        return studentNewDao.getList();
    }

    //判断名字是否都为汉字
    private boolean checkRealName(String realName) {
        if (StringUtils.isEmpty(realName)) {
            return false;
        }

        char[] ch = realName.toCharArray();
        for (char c : ch) {
            if (c < 0x4E00 || c > 0x9FA5) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Object> studentQRcodes(Integer ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        StudentNewDO studentDO = studentNewDao.get(ids);
        params.put("studentName", studentDO.getStudentName());
        params.put("grade", studentDO.getGrade() == null ? "" : studentDO.getGrade());
        params.put("studentClass", studentDO.getStudentClass() == null ? "" : studentDO.getStudentClass());
        String identityCard = studentDO.getIdentityCard();
        String code = QRCodeUtil.creatRrCode(ids.toString(), 500, 500);
//        String code = BarCodeUtils.generateBarCode128(ids.toString(), 10.0, 0.3, true, true);//条形码
        params.put("QRCode", code);
        return params;
    }

    /**
     * 二维码下载
     *
     * @throws IOException
     */
    @Override
    public void downloadErweima(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        try {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> params = studentQRcodes(ids[i]);
                if (params == null) continue;
                download(request, response, "studentQRcodes.ftl", ids[i].toString(), params);
				/*
				File file = new File(bootdoConfig.getUploadPath()+studentDO.getQRCode().substring(studentDO.getQRCode().lastIndexOf("/")+1));
				if(!file.exists())
					continue;
				params.put("${studentName}",studentDO.getStudentName());  
				params.put("${identityCard}",studentDO.getIdentityCard());  
				params.put("${studentSex}", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
				params.put("${school}",studentDO.getSchool()==null?"":studentDO.getSchool());  
				params.put("${grade}",studentDO.getGrade()==null?"":studentDO.getGrade());  
				params.put("${studentClass}",studentDO.getStudentClass()==null?"":studentDO.getStudentClass());
				if(studentDO.getQRCode()==null) continue;
				params.put("${QRCode}", new FileInputStream(new File(bootdoConfig.getUploadPath()+studentDO.getQRCode().substring(studentDO.getQRCode().lastIndexOf("/")+1))));
				String fileNameInResource = "erweima.docx";  
				InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);       
				XWPFDocument doc = new XWPFDocument(is);
				WordUtils.replaceInPara(doc, params);  
				doc.write(new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(studentDO.getStudentName().getBytes(),"utf-8")+".docx")));
				*/
            }
            craeteZipPath(bootdoConfig.getPoiword(), response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File file = new File(bootdoConfig.getPoiword());
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File f : files)
                    f.delete();
            }
        }
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
        zipOutputStream = new ZipOutputStream(output, Charset.forName("GBK"));
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

    /**
     * 答题结果导入
     */
    @Override
    public R daorudatijiguo(MultipartFile file) {
        try {
            Workbook book = ExcelUtils.getBook(file);
            if (book == null)
                return R.error("文件读取错误");
            Sheet sheet = book.getSheetAt(0);
            Date date = new Date();

            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                AnswerResultDO answerResultDO = new AnswerResultDO();
                Row row = sheet.getRow(rowNum);
                for (int cellNum = 0; cellNum < 79; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    String cellContent = ExcelUtils.getCellFormatValue(cell);
                    Class<?> clas = AnswerResultDO.class;
                    switch (cellNum) {
                        case 0:
                            break;
                        case 1:
                            Method method = clas.getMethod("setStatus", String.class);
                            method.invoke(answerResultDO, new Object[]{cellContent});
                            break;
                        case 2:
                            method = clas.getMethod("setFileName", String.class);
                            method.invoke(answerResultDO, new Object[]{cellContent});
                            break;
                        case 3:
                            method = clas.getMethod("setIdentityCard", String.class);
                            method.invoke(answerResultDO, new Object[]{cellContent});
                            break;
                        default:
                            int index = cellNum - 3;
                            method = clas.getMethod("setAnswerResult" + index, String.class);
                            method.invoke(answerResultDO, new Object[]{cellContent});
                    }
                }
                answerResultDO.setAddTime(date);
                studentNewDao.saveAnswer(answerResultDO);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @Override
    public List<AnswerResultDO> listDati(Map<String, Object> map) {
        return studentNewDao.listDati(map);
    }

    @Override
    public int countDati(Map<String, Object> map) {
        return studentNewDao.countDati(map);
    }

    /**
     * 筛查结果导出
     */
	/*@Override
	public void shaichajieguodaochu(Integer[] ids, HttpServletResponse response) {
		try {
			for(int i=0;i<ids.length;i++){
				Map<String, Object> params = createPeramsMap(ids[i]);
				if(params==null) continue;
				String fileNameInResource = "普通筛查导出模板.docx";  
				InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);       
				XWPFDocument doc = new XWPFDocument(is);
				WordUtils.replaceInPara(doc, params);  
				WordUtils.replaceInTable(doc, params);
		        doc.write(new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(ids[i].toString().getBytes(),"iso-8859-1")+".docx")));
			}
			craeteZipPath(bootdoConfig.getPoiword(),response);
		    } catch (IOException e) {
				e.printStackTrace();
			}finally{
				File file=new File(bootdoConfig.getPoiword());
		        if(file.exists()) {
		           File[] files = file.listFiles();
		           for(File f :files)
		              f.delete();
		        }
			}
		}		*/


    /**
     * 拼装普通筛查数据
     */
	/*private Map<String,Object> createPeramsMap(Integer id){
		Map<String, Object> params = new HashMap<String, Object>(); 
		//基本信息获取
		StudentDO studentDO = studentDao.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return null;
		params.put("${school}", studentDO.getSchool());
		params.put("${grade}",studentDO.getGrade().toString());
		params.put("${studentClass}",studentDO.getStudentClass().toString());
		params.put("${studentName}",studentDO.getStudentName());
		params.put("${studentSex}", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
		params.put("${lastCheckTime}", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentDao.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		params.put("${nakedFarvisionOd}",resultEyesightDO.getNakedFarvisionOd()==null? "":resultEyesightDO.getNakedFarvisionOd().toString());
		params.put("${nakedFarvisionOs}",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		params.put("${correctionFarvisionOd}",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
		params.put("${correctionFarvisionOs}",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("${diopterSL}",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("${diopterCL}",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("${diopterAL}",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("${diopterSR}",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("${diopterCR}",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("${diopterAR}",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());
		System.out.println("===========================");
		System.out.println("===========================");
		return params;
	}*/

    /**
     *示范校筛查结果导出 
     */
	/*@Override
	public void shifanshaichajieguodaochu(Integer[] ids, HttpServletResponse response) {
		try {
			for(int i=0;i<ids.length;i++){
				Map<String, Object> params = createPeramsMap12(ids[i]);
				if(params==null) continue;
				String fileNameInResource = "示范学校筛查导出模板.docx";  
				InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);       
				XWPFDocument doc = new XWPFDocument(is);
				WordUtils.replaceInPara(doc, params);  
				WordUtils.replaceInTable(doc, params);
		        doc.write(new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(ids[i].toString().getBytes(),"iso-8859-1")+".docx")));
			}
			craeteZipPath(bootdoConfig.getPoiword(),response);
		    } catch (IOException e) {
				e.printStackTrace();
			}finally{
				File file=new File(bootdoConfig.getPoiword());
		        if(file.exists()) {
		           File[] files = file.listFiles();
		           for(File f :files)
		              f.delete();
		        }
			}
	}  */
	
	/*private Map<String,Object> createPeramsMap12(Integer id){
		Map<String, Object> params = new HashMap<String, Object>(); 
		//基本信息获取
		StudentDO studentDO = studentDao.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return null;
		params.put("${school}", studentDO.getSchool());
		params.put("${grade}",studentDO.getGrade().toString());
		params.put("${studentClass}",studentDO.getStudentClass().toString());
		params.put("${studentName}",studentDO.getStudentName());
		params.put("${studentSex}", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
		params.put("${lastCheckTime}", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentDao.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		params.put("${nakedFarvisionOd}",resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString());
		params.put("${nakedFarvisionOs}",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		params.put("${correctionFarvisionOd}",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
		params.put("${correctionFarvisionOs}",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("${diopterSL}",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("${diopterCL}",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("${diopterAL}",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("${diopterSR}",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("${diopterCR}",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("${diopterAR}",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		//眼内压结果拼装
		List<ResultEyepressureDO> ResultEyepressureDOList = studentDao.getLatestResultEyepressureDO(studentDO.getId());
		ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
		if(ResultEyepressureDOList.size()>0)
			resultEyepressureDO=ResultEyepressureDOList.get(0);
		params.put("${eyePressureOd}",resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd().toString());
		params.put("${eyePressureOs}", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs().toString());
		//眼轴长度数据拼装
		List<ResultEyeaxisDO> resultEyeaxisDOList = studentDao.getLatelestResultEyeaxisDO(studentDO.getId());
		ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
		if(resultEyeaxisDOList.size()>0)
			resultEyeaxisDO=resultEyeaxisDOList.get(0);
		params.put("${secondCheckOd}",resultEyeaxisDO.getSecondCheckOd()==null?"":resultEyeaxisDO.getSecondCheckOd().toString());
		params.put("${secondCheckOs}", resultEyeaxisDO.getSecondCheckOs()==null?"":resultEyeaxisDO.getSecondCheckOs().toString());
		
		System.out.println("===========================");
		System.out.println("===========================");
		//临时数据拼装
		//报告临时数据
				params.put("${nakedFarvisionOdb}", "12");
				params.put("${nakedFarvisionOsb}", "11");
				params.put("${diopterSRb}", "500");
				params.put("${diopterSLb}", "300");
				params.put("${correctionFarvisionOdb}", "500");
				params.put("${correctionFarvisionOsb}", "300");
				params.put("${eyePressureOdb}", "1");
				params.put("${secondCheckOsb}", "1");
				params.put("${beforeAfterOdValueb}", "2");
				params.put("${beforeAfterOsValueb}", "2");
			
		return params;
	}
*/

    /**
     * 普通筛查导出（freemarker导出模式）
     */
    @Override
    public void exportWordPByFreemarker(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> params = createPeramsMapF(ids[i]);
                if (params == null) continue;
                params.put("i", i);
                list.add(params);
            }
            map.put("list", list);
            download(request, response, "普通筛查导出模板.ftl", "report", map);
            craeteZipPath(bootdoConfig.getPoiword(), response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File file = new File(bootdoConfig.getPoiword());
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File f : files)
                    f.delete();
            }
        }
    }

    @Override
    public void exportWordByFreemarker(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        try {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> params = createPeramsMapF(ids[i]);
                if (params == null) continue;
                download(request, response, "普通筛查导出模板单.ftl", ids[i].toString(), params);
            }
            craeteZipPath(bootdoConfig.getPoiword(), response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File file = new File(bootdoConfig.getPoiword());
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File f : files)
                    f.delete();
            }
        }
    }


    /**
     * freemarker导出工具类
     */

    public void download(HttpServletRequest request, HttpServletResponse response, String template, String newWordName, Map dataMap) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(StudentNewServiceImpl.class, "/");
        Template t = null;
        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate(template, "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(bootdoConfig.getPoiword() + new File(new String(newWordName.getBytes(), "iso-8859-1") + ".docx"))));                 //还有这里要设置编码
            t.process(dataMap, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * freemarker模式拼装普通筛查数据
     */
    private Map<String, Object> createPeramsMapF(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        //基本信息获取
        StudentNewDO studentDO = studentNewDao.get(id);
        if (studentDO == null || studentDO.getLastCheckTime() == null) return null;
        if(studentDO.getQRCode()==null){
            String qrCode = studentNewDao.qrCode(studentDO.getIdentityCard());
            if (StringUtils.isNotBlank(qrCode)){
                studentDO.setQRCode(qrCode);
            }else {
                qrCode = studentDO.getId().toString().substring(0,3) + studentDO.getStudentSex() + (int)(8999 * Math.random() + 1000);
                Map map = new HashMap();
                map.put("QRCode",qrCode);
                while (studentNewDao.list(map).size()>0){
                    qrCode = studentDO.getId().toString().substring(0,3) + studentDO.getStudentSex() + (int)(8999 * Math.random() + 1000);
                    map.put("QRCode",qrCode);
                }
                studentDO.setQRCode(qrCode);
                studentNewDao.updateCode(studentDO);
            }
        }

        params.put("qrCode", studentDO.getQRCode());
        params.put("school", studentDO.getSchool());
        params.put("grade", studentDO.getGrade().toString());
        params.put("studentClass", studentDO.getStudentClass().toString());
        params.put("studentName", studentDO.getStudentName());
        params.put("studentSex", studentDO.getStudentSex() == null ? "" : studentDO.getStudentSex() == 1 ? "男" : "女");
        params.put("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat df1 = new DecimalFormat("0.0");
        UserDO userDO;
        if (ShiroUtils.getUser().getUsername().equals("admin")) {
            userDO = userMapper.get(studentDO.getSysId());
        } else {
            userDO = userMapper.get(ShiroUtils.getUserId());
        }
        params.put("zhongxin", userDO);
        if (userDO.getZhongxinImg() != null && !userDO.getZhongxinImg().equals("")) {
            String zhongxinImg = Base64Utils.ImageToBase64ByLocal(bootdoConfig.getUploadPath() + userDO.getZhongxinImg().substring(6));
            params.put("zhongxinImg", zhongxinImg);
        } else {
            params.put("zhongxinImg", "");
        }
        //视力检查结果获取
        List<ResultEyesightNewDO> resultEyesightDOList = studentNewDao.getLatestResultEyesightDO(studentDO.getId());
        ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
        String nakedFarvisionOd = "";
        String nakedFarvisionOs = "";
        String correctionFarvisionOd = "5.0";
        String correctionFarvisionOs = "5.0";
        if (resultEyesightDOList.size() > 0) {
            resultEyesightDO = resultEyesightDOList.get(0);
            nakedFarvisionOd = resultEyesightDO.getNakedFarvisionOd() == null ? "" : resultEyesightDO.getNakedFarvisionOd().toString();
            nakedFarvisionOs = resultEyesightDO.getNakedFarvisionOs() == null ? "" : resultEyesightDO.getNakedFarvisionOs().toString();
            correctionFarvisionOd = resultEyesightDO.getCorrectionFarvisionOd() == null ? "" : resultEyesightDO.getCorrectionFarvisionOd().toString();
            correctionFarvisionOs = resultEyesightDO.getCorrectionFarvisionOs() == null ? "" : resultEyesightDO.getCorrectionFarvisionOs().toString();

        }
        if (nakedFarvisionOd.equals("10/10")) nakedFarvisionOd = "5.0";
        if (nakedFarvisionOs.equals("10/10")) nakedFarvisionOs = "5.0";
        if (correctionFarvisionOd.equals("10/10")) correctionFarvisionOd = "5.0";
        if (correctionFarvisionOs.equals("10/10")) correctionFarvisionOs = "5.0";
        params.put("nakedFarvisionOd", zhuanhuan1(nakedFarvisionOd) == "" ? "" : zhuanhuan1(nakedFarvisionOd));
        params.put("nakedFarvisionOs", zhuanhuan1(nakedFarvisionOs) == "" ? "" : zhuanhuan1(nakedFarvisionOs));
        params.put("glassvisionOd", zhuanhuan1(correctionFarvisionOd) == "" ? "" : zhuanhuan1(correctionFarvisionOd));
        params.put("glassvisionOs", zhuanhuan1(correctionFarvisionOd) == "" ? "" : zhuanhuan1(correctionFarvisionOs));


        //自动电脑验光结果(左眼)
        double dengxiaoqiujingL = 0.0, dengxiaoqiujingR = 0.0;
        ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
        ResultQuestionDO questionDO = questionDao.get(studentDO.getId());
        String diopterSL = "";
        if (questionDO != null && questionDO.getQuestionOneI() == 3) {
            BigDecimal bg = new BigDecimal(Double.parseDouble(questionDO.getQuestionTwoL()));
            diopterSL = "-" + df.format(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            List<ResultDiopterNewDO> resultDiopterDOList = studentNewDao.getLatestResultDiopterDOListL(studentDO.getId(), "L");
            if (resultDiopterDOList.size() > 0) {
                resultDiopterDO = resultDiopterDOList.get(0);
            } else {
                List<ResultDiopterNewDO> diopterDOListL = studentNewDao.getDiopterDOList(studentDO.getId(), "L");
                if (diopterDOListL.size() > 0) {
                    resultDiopterDO = diopterDOListL.get(0);
                }
            }
            if (resultDiopterDO.getDiopterS() != null) {
                diopterSL = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
                if (Double.valueOf(diopterSL) > 0) {
                    diopterSL = "+" + diopterSL;
                }
            }
        }

        params.put("diopterSL", diopterSL);
        params.put("diopterCL", resultDiopterDO.getDiopterC() == null ? "" : df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
        params.put("diopterAL", resultDiopterDO.getDiopterA() == null ? "" : zhuanhuan(resultDiopterDO.getDiopterA().toString()));
        dengxiaoqiujingL = resultDiopterDO.getDengxiaoqiujing() == null ? 0.0 : resultDiopterDO.getDengxiaoqiujing();
        double zhujingqL = resultDiopterDO.getDiopterC() == null ? 0.0 : resultDiopterDO.getDiopterC();


        //自动电脑验光结果(右眼)
        resultDiopterDO = new ResultDiopterNewDO();
        String diopterSR = "";
        if (questionDO != null && questionDO.getQuestionOneI() == 3) {
            BigDecimal bg = new BigDecimal(Double.parseDouble(questionDO.getQuestionTwoR()));
            diopterSR = "-" + df.format(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            List<ResultDiopterNewDO> resultDiopterDOList = studentNewDao.getLatestResultDiopterDOListL(studentDO.getId(), "R");
            if (resultDiopterDOList.size() > 0) {
                resultDiopterDO = resultDiopterDOList.get(0);
            } else {
                List<ResultDiopterNewDO> diopterDOListR = studentNewDao.getDiopterDOList(studentDO.getId(), "R");
                if (diopterDOListR.size() > 0) {
                    resultDiopterDO = diopterDOListR.get(0);
                }
            }
            if (resultDiopterDO.getDiopterS() != null) {
                diopterSR = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
                if (Double.valueOf(diopterSR) > 0) {
                    diopterSR = "+" + diopterSR;
                }
            }
        }

        params.put("diopterSR", diopterSR);
        params.put("diopterCR", resultDiopterDO.getDiopterC() == null ? "" : df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
        params.put("diopterAR", resultDiopterDO.getDiopterA() == null ? "" : zhuanhuan(resultDiopterDO.getDiopterA().toString()));
        dengxiaoqiujingR = resultDiopterDO.getDengxiaoqiujing() == null ? 0.0 : resultDiopterDO.getDengxiaoqiujing();
        double zhujingqr = resultDiopterDO.getDiopterC() == null ? 0.0 : resultDiopterDO.getDiopterC();

        //眼内压结果拼装
        List<ResultEyepressureNewDO> ResultEyepressureDOList = studentNewDao.getLatestResultEyepressureDO(studentDO.getId());
        ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
        if (ResultEyepressureDOList.size() > 0)
            resultEyepressureDO = ResultEyepressureDOList.get(0);
        params.put("eyePressureOd", resultEyepressureDO.getEyePressureOd() == null ? "" : zhuanhuan(resultEyepressureDO.getEyePressureOd().toString()));
        params.put("eyePressureOs", resultEyepressureDO.getEyePressureOs() == null ? "" : zhuanhuan(resultEyepressureDO.getEyePressureOs().toString()));
        //眼轴长度数据拼装
        List<ResultEyeaxisNewDO> resultEyeaxisDOList = studentNewDao.getLatelestResultEyeaxisDO(studentDO.getId());
        ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
        if (resultEyeaxisDOList.size() > 0)
            resultEyeaxisDO = resultEyeaxisDOList.get(0);
        params.put("secondCheckOd", resultEyeaxisDO.getFirstCheckOd() == null ? "" : df.format(resultEyeaxisDO.getFirstCheckOd()));
        params.put("secondCheckOs", resultEyeaxisDO.getFirstCheckOs() == null ? "" : df.format(resultEyeaxisDO.getFirstCheckOs()));

        System.out.println("===========================");
        System.out.println("===========================");
        //角膜验光拼装
        ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
        List<ResultCornealNewDO> resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "R", "R1");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);
        params.put("cornealMmr1R", resultCornealDO.getCornealMm() == null ? "" : zhuanhuan(resultCornealDO.getCornealMm()));
        params.put("cornealDr1R", resultCornealDO.getCornealDeg() == null ? "" : resultCornealDO.getCornealDeg());
        resultCornealDO = new ResultCornealNewDO();
        resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "R", "R2");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);
        params.put("cornealMmr2R", resultCornealDO.getCornealMm() == null ? "" : zhuanhuan(resultCornealDO.getCornealMm()));
        params.put("cornealDr2R", resultCornealDO.getCornealDeg() == null ? "" : resultCornealDO.getCornealDeg());

        resultCornealDO = new ResultCornealNewDO();
        resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "L", "R1");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);
        params.put("cornealMmr1L", resultCornealDO.getCornealMm() == null ? "" : zhuanhuan(resultCornealDO.getCornealMm()));
        params.put("cornealDr1L", resultCornealDO.getCornealDeg() == null ? "" : resultCornealDO.getCornealDeg());


        resultCornealDO = new ResultCornealNewDO();
        resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "L", "R2");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);

        params.put("cornealMmr2L", resultCornealDO.getCornealMm() == null ? "" : zhuanhuan(resultCornealDO.getCornealMm()));
        params.put("cornealDr2L", resultCornealDO.getCornealDeg() == null ? "" : resultCornealDO.getCornealDeg());
        //医生的建议
        Date birthday = studentDO.getBirthday() == null ? new Date() : studentDO.getBirthday();
        int birth = 0;
        try {
            birth = TimeUtils.getAgeByBirth(birthday);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//	   if(birth>=3 && birth<=5){
//		   params.put("ifStu","1");
//	   }else{
//		   params.put("ifStu","2");
//	   }
        if ("幼儿园".equals(studentDO.getXueBu())) {
            params.put("ifStu", "1");
        } else {
            params.put("ifStu", "2");
        }
        double od = 0.0, os = 0.0;
        if (!StringUtils.isBlank(nakedFarvisionOd) && isDouble(nakedFarvisionOd)) {
            od = Double.parseDouble(nakedFarvisionOd);
        }
        if (!StringUtils.isBlank(nakedFarvisionOs) && isDouble(nakedFarvisionOs)) {
            os = Double.parseDouble(nakedFarvisionOs);
        }
//	    od=od<os?od:os;
//	    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
        double yuanjingshiliL = 0, yuanjingshiliR = 0;//原镜视力
        String ssL = "ss", ssR = "ss";
        if (!StringUtils.isBlank(correctionFarvisionOd) && isDouble(correctionFarvisionOd)) {
//	    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
            yuanjingshiliR = Double.parseDouble(correctionFarvisionOd);
        }
        if (!StringUtils.isBlank(correctionFarvisionOs) && isDouble(correctionFarvisionOs)) {
//	    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
            yuanjingshiliL = Double.parseDouble(correctionFarvisionOs);
        }
        if (yuanjingshiliL == 0)
            ssL = "wuyuanjing";
        if (yuanjingshiliR == 0)
            ssR = "wuyuanjing";
        if (od >= 5.0 && dengxiaoqiujingR > 0.75 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "视力目前正常，无近视发生。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","无");
        }
        if (os >= 5.0 && dengxiaoqiujingL > 0.75 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "视力目前正常，无近视发生。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","无");
        }

        if (od >= 5.0 && dengxiaoqiujingR > 0.75 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "视力目前正常，散光，无近视发生。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","无");
        }
        if (os >= 5.0 && dengxiaoqiujingL > 0.75 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "视力目前正常，散光，无近视发生。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","无");
        }

        if (od >= 5.0 && dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "视力目前正常，近视临床前期。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，防控近视的发生，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","近视临床前期");
        }
        if (os >= 5.0 && dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "视力目前正常，近视临床前期。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，防控近视的发生，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","近视临床前期");
        }

        if (od >= 5.0 && dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "视力目前正常，散光，近视临床前期。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，防控近视的发生，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","近视临床前期");
        }
        if (os >= 5.0 && dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "视力目前正常，散光，近视临床前期。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，防控近视的发生，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","近视临床前期");
        }

        if (od >= 5.0 && dengxiaoqiujingR < -0.5 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "视力目前正常，属于假性近视。根据目前检查结果，有发生真性近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，采用科学的防控措施，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
            //    	model.addAttribute("yujing","假性近视");
        }
        if (os >= 5.0 && dengxiaoqiujingL < -0.5 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "视力目前正常，属于假性近视。根据目前检查结果，有发生真性近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，采用科学的防控措施，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
            //    	model.addAttribute("yujing","假性近视");
        }

        if (od >= 5.0 && dengxiaoqiujingR < -0.5 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "视力目前正常，属于假性近视，散光。根据目前检查结果，有发生真性近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，采用科学的防控措施，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
            //    	model.addAttribute("yujing","假性近视");
        }
        if (os >= 5.0 && dengxiaoqiujingL < -0.5 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "视力目前正常，属于假性近视，散光。根据目前检查结果，有发生真性近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，采用科学的防控措施，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
            //    	model.addAttribute("yujing","假性近视");
        }
        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR >= 5.0 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。合理卫生用眼，建议到医院进一步检查，了解远视储备值情况，预防近视的发生。");
            //    	model.addAttribute("yujing","无");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL >= 5.0 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。合理卫生用眼，建议到医院进一步检查，了解远视储备值情况，预防近视的发生。");
            //    	model.addAttribute("yujing","无");
        }

        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR >= 5.0 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "散光，戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。合理卫生用眼，建议到医院进一步检查，了解远视储备值情况，预防近视的发生。");
            //    	model.addAttribute("yujing","无");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL >= 5.0 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "散光，戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。合理卫生用眼，建议到医院进一步检查，了解远视储备值情况，预防近视的发生。");
            //    	model.addAttribute("yujing","无");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR >= 5.0 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展；建议到正规医院，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL >= 5.0 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展；建议到正规医院，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR >= 5.0 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "戴原镜视力正常，近视，散光。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展；建议到正规医院，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL >= 5.0 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "戴原镜视力正常，近视，散光。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展；建议到正规医院，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR < 5.0 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "视力异常。建议及时到正规医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼部疾病，并及时采取相应治疗措施；若已明确诊断，请遵医嘱及时定期复查，进行科学规范的治疗。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL < 5.0 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "视力异常。建议及时到正规医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼部疾病，并及时采取相应治疗措施；若已明确诊断，请遵医嘱及时定期复查，进行科学规范的治疗。");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR < 5.0 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "视力异常，散光。建议及时到正规医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼部疾病，并及时采取相应治疗措施；若已明确诊断，请遵医嘱及时定期复查，进行科学规范的治疗。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL < 5.0 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "视力异常，散光。建议及时到正规医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼部疾病，并及时采取相应治疗措施；若已明确诊断，请遵医嘱及时定期复查，进行科学规范的治疗。");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR < 5.0 && zhujingqr > -1.0) {
            params.put("ydoctorchubu", "视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病；若已进行近视矫治，根据检查结果提示，眼镜度数可能需要调整，请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL < 5.0 && zhujingqL > -1.0) {
            params.put("zdoctorchubu", "视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病；若已进行近视矫治，根据检查结果提示，眼镜度数可能需要调整，请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR < 5.0 && zhujingqr <= -1.0) {
            params.put("ydoctorchubu", "视力下降，近视，散光。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病；若已进行近视矫治，根据检查结果提示，眼镜度数可能需要调整，请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL < 5.0 && zhujingqL <= -1.0) {
            params.put("zdoctorchubu", "视力下降，近视，散光。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病；若已进行近视矫治，根据检查结果提示，眼镜度数可能需要调整，请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
            //    	model.addAttribute("yujing","近视");
        }

			   /* if(od>=5.0 && dengxiaoqiujingR>0.75){
			    	model.addAttribute("ydoctorchubu","视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
			//    	model.addAttribute("yujing","无");
			    }
			    if(os>=5.0 && dengxiaoqiujingL>0.75){
			    	model.addAttribute("zdoctorchubu","视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
			//    	model.addAttribute("yujing","无");
			    }

				if(od>=5.0 && dengxiaoqiujingR>=-0.5 && dengxiaoqiujingR<=0.75){
					model.addAttribute("ydoctorchubu","视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
			//    	model.addAttribute("yujing","近视临床前期");
				}
				if(os>=5.0 && dengxiaoqiujingL>=-0.5 && dengxiaoqiujingL<=0.75){
					model.addAttribute("zdoctorchubu","视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
			//    	model.addAttribute("yujing","近视临床前期");
				}

				if(od>=5.0 && dengxiaoqiujingR<-0.5){
					model.addAttribute("ydoctorchubu","视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
			//    	model.addAttribute("yujing","假性近视");
				}
				if(os>=5.0 && dengxiaoqiujingL<-0.5){
					model.addAttribute("zdoctorchubu","视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
			//    	model.addAttribute("yujing","假性近视");
				}

				if(od<5.0 &&dengxiaoqiujingR>=-0.5 && yuanjingshiliR==0 && ssR.equals("wuyuanjing")){
					model.addAttribute("ydoctorchubu","视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
			//    	model.addAttribute("yujing","无");
				}
				if(os<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshiliL==0 && ssL.equals("wuyuanjing")){
					model.addAttribute("zdoctorchubu","视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
			//    	model.addAttribute("yujing","无");
				}

				if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR==0 && ssR.equals("wuyuanjing")){
					model.addAttribute("ydoctorchubu","视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
			//    	model.addAttribute("yujing","近视");
				}
				if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL==0 && ssL.equals("wuyuanjing")){
					model.addAttribute("zdoctorchubu","视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
			//    	model.addAttribute("yujing","近视");
				}

				if(od<5.0 && dengxiaoqiujingR>=-0.5 && yuanjingshiliR>=5.0){
					model.addAttribute("ydoctorchubu","戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
			//    	model.addAttribute("yujing","无");
				}
				if(os<5.0 && dengxiaoqiujingL>=-0.5 && yuanjingshiliL>=5.0){
					model.addAttribute("zdoctorchubu","戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
			//    	model.addAttribute("yujing","无");
				}

				if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR>=5.0){
					model.addAttribute("ydoctorchubu","戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
			//    	model.addAttribute("yujing","近视");
				}
				if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL>=5.0){
					model.addAttribute("zdoctorchubu","戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
			//    	model.addAttribute("yujing","近视");
				}

				if(od<5.0 &&dengxiaoqiujingR>=-0.5 && yuanjingshiliR<5.0 && ssR.equals("ss")){
					model.addAttribute("ydoctorchubu","戴原镜视力异常。 请遵医嘱及时定期复查。");
			//    	model.addAttribute("yujing","无");
				}
				if(os<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshiliL<5.0 && ssL.equals("ss")){
					model.addAttribute("zdoctorchubu","戴原镜视力异常。 请遵医嘱及时定期复查。");
			//    	model.addAttribute("yujing","无");
				}

				if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR<5.0 && ssR.equals("ss")){
					model.addAttribute("ydoctorchubu","戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
			//    	model.addAttribute("yujing","近视增长");
				}
				if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL<5.0 && ssL.equals("ss")){
					model.addAttribute("zdoctorchubu","戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
			//    	model.addAttribute("yujing","近视增长");
				}*/
        System.out.println("===========================");
        System.out.println("===========================");
        return params;
    }

    /**
     * 示范校筛查结果导出（freemarker模式）
     */
    @Override
    public void exportWordPBByFreemarkerSHIfanxiao(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        try {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> params = createPeramsMap2F(ids[i]);
                if (params == null) continue;
                download(request, response, "示范学校筛查导出模板.ftl", ids[i].toString(), params);
            }
            craeteZipPath(bootdoConfig.getPoiword(), response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File file = new File(bootdoConfig.getPoiword());
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File f : files)
                    f.delete();
            }
        }
    }

    /**
     * freemarker拼装示范校筛查数据
     */
    private Map<String, Object> createPeramsMap2F(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        //基本信息获取
        StudentNewDO studentDO = studentNewDao.get(id);
        if (studentDO == null || studentDO.getLastCheckTime() == null) return null;
        if(studentDO.getQRCode()==null){
            String qrCode = studentNewDao.qrCode(studentDO.getIdentityCard());
            if (StringUtils.isNotBlank(qrCode)){
                studentDO.setQRCode(qrCode);
            }else {
                qrCode = studentDO.getId().toString().substring(0,3) + studentDO.getStudentSex() + (int)(8999 * Math.random() + 1000);
                Map map = new HashMap();
                map.put("QRCode",qrCode);
                while (studentNewDao.list(map).size()>0){
                    qrCode = studentDO.getId().toString().substring(0,3) + studentDO.getStudentSex() + (int)(8999 * Math.random() + 1000);
                    map.put("QRCode",qrCode);
                }
                studentDO.setQRCode(qrCode);
                studentNewDao.updateCode(studentDO);
            }
        }

        params.put("qrCode", studentDO.getQRCode());
        params.put("school", studentDO.getSchool());
        params.put("grade", studentDO.getGrade().toString());
        params.put("studentClass", studentDO.getStudentClass().toString());
        params.put("studentName", studentDO.getStudentName());
        params.put("studentSex", studentDO.getStudentSex() == null ? "" : studentDO.getStudentSex() == 1 ? "男" : "女");
        params.put("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat df1 = new DecimalFormat("0.0");
        UserDO userDO;
        if (ShiroUtils.getUser().getUsername().equals("admin")) {
            userDO = userMapper.get(studentDO.getSysId());
        } else {
            userDO = userMapper.get(ShiroUtils.getUserId());
        }
        params.put("zhongxin", userDO);
        if (userDO.getZhongxinImg() != null && !userDO.getZhongxinImg().equals("")) {
            String image = Base64Utils.ImageToBase64ByOnline(userDO.getZhongxinImg());
            params.put("zhongxinImg", image);
        } else {
            params.put("zhongxinImg", "");
        }
        //视力检查结果获取
        List<ResultEyesightNewDO> resultEyesightDOList = studentNewDao.getLatestResultEyesightDO(studentDO.getId());
        ResultEyesightNewDO resultEyesightDO = new ResultEyesightNewDO();
        String nakedFarvisionOd = "";
        String nakedFarvisionOs = "";
        String correctionFarvisionOd = "";
        String correctionFarvisionOs = "";
        if (resultEyesightDOList.size() > 0) {
            resultEyesightDO = resultEyesightDOList.get(0);
            nakedFarvisionOd = resultEyesightDO.getNakedFarvisionOd() == null ? "" : resultEyesightDO.getNakedFarvisionOd().toString();
            nakedFarvisionOs = resultEyesightDO.getNakedFarvisionOs() == null ? "" : resultEyesightDO.getNakedFarvisionOs().toString();
            correctionFarvisionOd = resultEyesightDO.getCorrectionFarvisionOd() == null ? "" : resultEyesightDO.getCorrectionFarvisionOd().toString();
            correctionFarvisionOs = resultEyesightDO.getCorrectionFarvisionOs() == null ? "" : resultEyesightDO.getCorrectionFarvisionOs().toString();
        }
        params.put("nakedFarvisionOd", zhuanhuan1(nakedFarvisionOd) == "" ? "" : zhuanhuan1(nakedFarvisionOd));
        params.put("nakedFarvisionOs", zhuanhuan1(nakedFarvisionOs) == "" ? "" : zhuanhuan1(nakedFarvisionOs));
        params.put("glassvisionOd", zhuanhuan1(correctionFarvisionOd) == "" ? "" : zhuanhuan1(correctionFarvisionOd));
        params.put("glassvisionOs", zhuanhuan1(correctionFarvisionOd) == "" ? "" : zhuanhuan1(correctionFarvisionOs));


        //自动电脑验光结果(左眼)
        double dengxiaoqiujingL = 0.0, dengxiaoqiujingR = 0.0;
        ResultDiopterNewDO resultDiopterDO = new ResultDiopterNewDO();
        ResultQuestionDO questionDO = questionDao.get(studentDO.getId());
        String diopterSL = "";
        if (questionDO != null && questionDO.getQuestionOneI() == 3) {
            BigDecimal bg = new BigDecimal(Double.parseDouble(questionDO.getQuestionTwoL()));
            diopterSL = "-" + df.format(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            List<ResultDiopterNewDO> resultDiopterDOList = studentNewDao.getLatestResultDiopterDOListL(studentDO.getId(), "L");
            if (resultDiopterDOList.size() > 0) {
                resultDiopterDO = resultDiopterDOList.get(0);
            } else {
                List<ResultDiopterNewDO> diopterDOListL = studentNewDao.getDiopterDOList(studentDO.getId(), "L");
                if (diopterDOListL.size() > 0) {
                    resultDiopterDO = diopterDOListL.get(0);
                }
            }
            if (resultDiopterDO.getDiopterS() != null) {
                diopterSL = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
                if (Double.valueOf(diopterSL) > 0) {
                    diopterSL = "+" + diopterSL;
                }
            }
        }

        params.put("diopterSL", diopterSL);
        params.put("diopterCL", resultDiopterDO.getDiopterC() == null ? "" : df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
        params.put("diopterAL", resultDiopterDO.getDiopterA() == null ? "" : zhuanhuan(resultDiopterDO.getDiopterA().toString()));
        dengxiaoqiujingL = resultDiopterDO.getDengxiaoqiujing() == null ? 0.0 : resultDiopterDO.getDengxiaoqiujing();


        //自动电脑验光结果(右眼)
        resultDiopterDO = new ResultDiopterNewDO();
        String diopterSR = "";
        if (questionDO != null && questionDO.getQuestionOneI() == 3) {
            BigDecimal bg = new BigDecimal(Double.parseDouble(questionDO.getQuestionTwoR()));
            diopterSR = "-" + df.format(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            List<ResultDiopterNewDO> resultDiopterDOList = studentNewDao.getLatestResultDiopterDOListL(studentDO.getId(), "R");
            if (resultDiopterDOList.size() > 0) {
                resultDiopterDO = resultDiopterDOList.get(0);
            } else {
                List<ResultDiopterNewDO> diopterDOListR = studentNewDao.getDiopterDOList(studentDO.getId(), "R");
                if (diopterDOListR.size() > 0) {
                    resultDiopterDO = diopterDOListR.get(0);
                }
            }
            if (resultDiopterDO.getDiopterS() != null) {
                diopterSR = df.format(zhuanhuan(resultDiopterDO.getDiopterS().toString()));
                if (Double.valueOf(diopterSR) > 0) {
                    diopterSR = "+" + diopterSR;
                }
            }
        }

        params.put("diopterSR", diopterSR);
        params.put("diopterCR", resultDiopterDO.getDiopterC() == null ? "" : df.format(zhuanhuan(resultDiopterDO.getDiopterC().toString())));
        params.put("diopterAR", resultDiopterDO.getDiopterA() == null ? "" : zhuanhuan(resultDiopterDO.getDiopterA().toString()));
        dengxiaoqiujingR = resultDiopterDO.getDengxiaoqiujing() == null ? 0.0 : resultDiopterDO.getDengxiaoqiujing();

        //眼内压结果拼装
        List<ResultEyepressureNewDO> ResultEyepressureDOList = studentNewDao.getLatestResultEyepressureDO(studentDO.getId());
        ResultEyepressureNewDO resultEyepressureDO = new ResultEyepressureNewDO();
        if (ResultEyepressureDOList.size() > 0)
            resultEyepressureDO = ResultEyepressureDOList.get(0);
        params.put("eyePressureOd", resultEyepressureDO.getEyePressureOd() == null ? "" : zhuanhuan(resultEyepressureDO.getEyePressureOd().toString()));
        params.put("eyePressureOs", resultEyepressureDO.getEyePressureOs() == null ? "" : zhuanhuan(resultEyepressureDO.getEyePressureOs().toString()));
        //眼轴长度数据拼装
        List<ResultEyeaxisNewDO> resultEyeaxisDOList = studentNewDao.getLatelestResultEyeaxisDO(studentDO.getId());
        ResultEyeaxisNewDO resultEyeaxisDO = new ResultEyeaxisNewDO();
        if (resultEyeaxisDOList.size() > 0)
            resultEyeaxisDO = resultEyeaxisDOList.get(0);
        params.put("secondCheckOd", resultEyeaxisDO.getFirstCheckOd() == null ? "" : zhuanhuan(resultEyeaxisDO.getFirstCheckOd().toString()));
        params.put("secondCheckOs", resultEyeaxisDO.getFirstCheckOs() == null ? "" : zhuanhuan(resultEyeaxisDO.getFirstCheckOs().toString()));

        System.out.println("===========================");
        System.out.println("===========================");
        //角膜验光拼装
        ResultCornealNewDO resultCornealDO = new ResultCornealNewDO();
        List<ResultCornealNewDO> resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "R", "R1");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);
        params.put("cornealMmr1R", resultCornealDO.getCornealD() == null ? "0" : zhuanhuan(resultCornealDO.getCornealD()));
        params.put("cornealDr1R", resultCornealDO.getCornealDeg() == null ? "0" : resultCornealDO.getCornealDeg());
        resultCornealDO = new ResultCornealNewDO();
        resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "R", "R2");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);
        params.put("cornealMmr2R", resultCornealDO.getCornealD() == null ? "0" : zhuanhuan(resultCornealDO.getCornealD()));
        params.put("cornealDr2R", resultCornealDO.getCornealDeg() == null ? "0" : resultCornealDO.getCornealDeg());

        resultCornealDO = new ResultCornealNewDO();
        resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "L", "R1");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);
        params.put("cornealMmr1L", resultCornealDO.getCornealD() == null ? "0" : zhuanhuan(resultCornealDO.getCornealD()));
        params.put("cornealDr1L", resultCornealDO.getCornealDeg() == null ? "0" : resultCornealDO.getCornealDeg());


        resultCornealDO = new ResultCornealNewDO();
        resultCornealDOList = studentNewDao.getResultCornealDOList(studentDO.getId(), "L", "R2");
        if (resultCornealDOList.size() > 0) resultCornealDO = resultCornealDOList.get(0);

        params.put("cornealMmr2L", resultCornealDO.getCornealD() == null ? "0" : zhuanhuan(resultCornealDO.getCornealD()));
        params.put("cornealDr2L", resultCornealDO.getCornealDeg() == null ? "0" : resultCornealDO.getCornealDeg());
        //医生的建议
        Date birthday = studentDO.getBirthday() == null ? new Date() : studentDO.getBirthday();
        int birth = 0;
        try {
            birth = TimeUtils.getAgeByBirth(birthday);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//			   if(birth>=3 && birth<=5){
//				   params.put("ifStu","1");
//			   }else{
//				   params.put("ifStu","2");
//			   }
        if ("幼儿园".equals(studentDO.getXueBu())) {
            params.put("ifStu", "1");
        } else {
            params.put("ifStu", "2");
        }
        double od = 0.0, os = 0.0;
        if (!StringUtils.isBlank(nakedFarvisionOd) && isDouble(nakedFarvisionOd)) {
            od = Double.parseDouble(nakedFarvisionOd);
        }
        if (!StringUtils.isBlank(nakedFarvisionOs) && isDouble(nakedFarvisionOs)) {
            os = Double.parseDouble(nakedFarvisionOs);
        }
//			    od=od<os?od:os;
//			    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
        double yuanjingshiliL = 0, yuanjingshiliR = 0;//原镜视力
        String ssL = "ss", ssR = "ss";
        if (!StringUtils.isBlank(correctionFarvisionOd) && isDouble(correctionFarvisionOd)) {
//			    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
            yuanjingshiliR = Double.parseDouble(correctionFarvisionOd);
        }
        if (!StringUtils.isBlank(correctionFarvisionOs) && isDouble(correctionFarvisionOs)) {
//			    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
            yuanjingshiliL = Double.parseDouble(correctionFarvisionOs);
        }
        if (yuanjingshiliL == 0)
            ssL = "wuyuanjing";
        if (yuanjingshiliR == 0)
            ssR = "wuyuanjing";
        if (od >= 5.0 && dengxiaoqiujingR > 0.75) {
            params.put("ydoctorchubu", "视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","无");
        }
        if (os >= 5.0 && dengxiaoqiujingL > 0.75) {
            params.put("zdoctorchubu", "视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","无");
        }

        if (od >= 5.0 && dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75) {
            params.put("ydoctorchubu", "视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","近视临床前期");
        }
        if (os >= 5.0 && dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75) {
            params.put("zdoctorchubu", "视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
            //    	model.addAttribute("yujing","近视临床前期");
        }

        if (od >= 5.0 && dengxiaoqiujingR < -0.5) {
            params.put("ydoctorchubu", "视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
            //    	model.addAttribute("yujing","假性近视");
        }
        if (os >= 5.0 && dengxiaoqiujingL < -0.5) {
            params.put("zdoctorchubu", "视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
            //    	model.addAttribute("yujing","假性近视");
        }

        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR == 0 && ssR.equals("wuyuanjing")) {
            params.put("ydoctorchubu", "视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
            //    	model.addAttribute("yujing","无");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL == 0 && ssL.equals("wuyuanjing")) {
            params.put("zdoctorchubu", "视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
            //    	model.addAttribute("yujing","无");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR == 0 && ssR.equals("wuyuanjing")) {
            params.put("ydoctorchubu", "视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL == 0 && ssL.equals("wuyuanjing")) {
            params.put("zdoctorchubu", "视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR >= 5.0) {
            params.put("ydoctorchubu", "戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
            //    	model.addAttribute("yujing","无");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL >= 5.0) {
            params.put("zdoctorchubu", "戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
            //    	model.addAttribute("yujing","无");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR >= 5.0) {
            params.put("ydoctorchubu", "戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
            //    	model.addAttribute("yujing","近视");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL >= 5.0) {
            params.put("zdoctorchubu", "戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
            //    	model.addAttribute("yujing","近视");
        }

        if (od < 5.0 && dengxiaoqiujingR >= -0.5 && yuanjingshiliR < 5.0 && ssR.equals("ss")) {
            params.put("ydoctorchubu", "戴原镜视力异常。 请遵医嘱及时定期复查。");
            //    	model.addAttribute("yujing","无");
        }
        if (os < 5.0 && dengxiaoqiujingL >= -0.5 && yuanjingshiliL < 5.0 && ssL.equals("ss")) {
            params.put("zdoctorchubu", "戴原镜视力异常。 请遵医嘱及时定期复查。");
            //    	model.addAttribute("yujing","无");
        }

        if (od < 5.0 && dengxiaoqiujingR < -0.5 && yuanjingshiliR < 5.0 && ssR.equals("ss")) {
            params.put("ydoctorchubu", "戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
            //    	model.addAttribute("yujing","近视增长");
        }
        if (os < 5.0 && dengxiaoqiujingL < -0.5 && yuanjingshiliL < 5.0 && ssL.equals("ss")) {
            params.put("zdoctorchubu", "戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
            //    	model.addAttribute("yujing","近视增长");
        }
        return params;
    }

    @Override
    public List<ResultEyesightNewDO> getLatestResultEyesightDO(Integer id) {
        return studentNewDao.getLatestResultEyesightDO(id);
    }

    @Override
    public List<ResultDiopterNewDO> getLatestResultDiopterDOListL(Integer id, String string) {
        return studentNewDao.getLatestResultDiopterDOListL(id, string);
    }

    @Override
    public List<ResultEyepressureNewDO> getLatestResultEyepressureDO(Integer id) {
        return studentNewDao.getLatestResultEyepressureDO(id);
    }

    @Override
    public List<ResultEyeaxisNewDO> getLatelestResultEyeaxisDO(Integer id) {
        return studentNewDao.getLatelestResultEyeaxisDO(id);
    }


    public List<ResultCornealNewDO> getResultCornealDOList(Integer studentId, String ifrl, String type) {
        return studentNewDao.getResultCornealDOList(studentId, ifrl, type);
    }


//	@Override
//	public List<StudentNewDO> querySchoolName() {
//		
//		return studentNewDao.querySchoolName();
//	}

	/*List<ResultEyesightDO> resultEyesightDOList11 = new ArrayList<ResultEyesightDO>();
	List<ResultDiopterDO> resultDiopterDOListR11 = new ArrayList<ResultDiopterDO>();
	List<ResultDiopterDO>  resultDiopterDOListL11= new ArrayList<ResultDiopterDO>();
	List<StudentDO>  studentDOlIST1= new ArrayList<StudentDO>();*/


    /**
     * 首页真实数据展示
     */
    @Override
    public Map<String, Double> shouYeTrueData() {
        Map<String, Object> paMap = new HashMap<String, Object>();
        int totalnumber = studentNewDao.countP(paMap);
        paMap.put("checkType", "SHI_FANXIAO");
        paMap.put("address", "济南市");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String str = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        paMap.put("lastCheckTime", str);
        int shifan = studentNewDao.countP(paMap);
        paMap.put("checkType", "PU_TONG");
        int putong = studentNewDao.countP(paMap);
        Map<String, Double> freeMap = new HashMap<String, Double>();

        freeMap.put("jslcqianqiNumber", 0.0);
        freeMap.put("jxjsNumber", 0.0);
        freeMap.put("gaodujinshiNumber", 0.0);
        freeMap.put("zhongdujinshiNumber", 0.0);
        freeMap.put("didujinshiNumber", 0.0);
        freeMap.put("jinshizongjiNumber", 0.0);
        freeMap.put("totalNumber", 0.0);
        freeMap.put("shifanxiao", 0.0);
        freeMap.put("putong", 0.0);
        freeMap.put("zhengshiNumber", 0.0);
        freeMap.put("buliangshiliNumber", 0.0);
        freeMap.put("yuanshiNumber", 0.0);
        freeMap.put("quguangcenciNumber", 0.0);
        freeMap.put("totalnumber", (double) totalnumber);
        freeMap.put("shifanxiaoshaicha", (double) shifan);
        freeMap.put("putongshaicha", (double) (putong));
        freeMap.put("nain6", 0.0);
        freeMap.put("nain612", 0.0);
        freeMap.put("nain1315", 0.0);
        freeMap.put("nain1618", 0.0);
        freeMap.put("nain18", 0.0);
        for (int i = 1; i <= totalnumber / 150000 + 1; i++) {
            countShouYe(i, 150000, freeMap);

        }


        Double jinshizongjiNumber = freeMap.get("didujinshiNumber") + freeMap.get("zhongdujinshiNumber")
                + freeMap.get("gaodujinshiNumber");//近视总人数
        freeMap.put("zhengchang", totalnumber - jinshizongjiNumber);
        freeMap.put("jinshilv", totalnumber == 0 ? 0 : FormatDouble((double) (jinshizongjiNumber * 10000 / totalnumber) / 100));
        freeMap.put("buliangshililv", totalnumber == 0 ? 0 : FormatDouble((double) (freeMap.get("buliangshiliNumber") * 10000 / totalnumber) / 100));
        freeMap.put("wubuliang", totalnumber - freeMap.get("buliangshiliNumber"));
        freeMap.put("yunshihuanbinglv", totalnumber == 0 ? 0 : FormatDouble((double) (freeMap.get("yuanshiNumber") * 10000 / totalnumber) / 100));
        freeMap.put("wuyuanshi", totalnumber - freeMap.get("yuanshiNumber"));
        freeMap.put("wuzhengshi", totalnumber - freeMap.get("zhengshiNumber"));
        freeMap.put("zhengshiyanlv", totalnumber == 0 ? 0 : FormatDouble((double) (freeMap.get("zhengshiNumber") * 10000 / totalnumber) / 100));
        freeMap.put("quguangcenciNumberLv", totalnumber == 0 ? 0 : FormatDouble((double) (freeMap.get("quguangcenciNumber") * 10000 / totalnumber) / 100));
        freeMap.put("wuquguangcenci", totalnumber - freeMap.get("quguangcenciNumber"));
        return freeMap;
    }

    private void countShouYe(int i, int j, Map<String, Double> freeMap) {
        long inittimnes = System.currentTimeMillis();
        List<StudentNewDO> studentDOList = studentNewDao.getAllCheckStudentDO((i - 1) * j, j);
        long ddt = System.currentTimeMillis();
        System.out.println("查询所用时间============" + (ddt - inittimnes));
        System.out.println("查到的数==============" + studentDOList.size());

        for (StudentNewDO s : studentDOList) {

            Double luoyanshilii = 0.0;
            Double dengxiaoqiujing = 0.0;
            String nakedFarvisionOd = s.getNakedFarvisionOd();
            String nakedFarvisionOs = s.getNakedFarvisionOs();
            if (!nakedFarvisionOd.matches("-?[0-9]+.?[0-9]*")) continue;
            if (!nakedFarvisionOs.matches("-?[0-9]+.?[0-9]*")) continue;
            nakedFarvisionOd = nakedFarvisionOd.compareTo(nakedFarvisionOs) > 0 ? nakedFarvisionOs : nakedFarvisionOd;
            if (!StringUtils.isBlank(nakedFarvisionOd))
                luoyanshilii = Double.parseDouble(nakedFarvisionOd);
            Double dengxiaoqiujingR = s.getDengxiaoqiujingr();
            Double dengxiaoqiujingL = s.getDengxiaoqiujingl();
            dengxiaoqiujing = dengxiaoqiujingR > dengxiaoqiujingL ? dengxiaoqiujingL : dengxiaoqiujingR;

            if (luoyanshilii == 5.0 && dengxiaoqiujing >= -0.5 && dengxiaoqiujing <= 0.75) {//近视临床前期
                freeMap.put("jslcqianqiNumber", freeMap.get("jslcqianqiNumber") + 1);
            }
            if (luoyanshilii == 5.0 && dengxiaoqiujing < -0.5) {//假性近视
                freeMap.put("jxjsNumber", freeMap.get("jxjsNumber") + 1);
            }
            if (luoyanshilii < 5.0 && dengxiaoqiujing < -6.0) {//高度近视
                freeMap.put("gaodujinshiNumber", freeMap.get("gaodujinshiNumber") + 1);
            }
            if (luoyanshilii < 5.0 && dengxiaoqiujing > -6.0 && dengxiaoqiujing < -3.25) {//中度近视
                freeMap.put("zhongdujinshiNumber", freeMap.get("zhongdujinshiNumber") + 1);
            }
            if (luoyanshilii < 5.0 && dengxiaoqiujing > -3.0 && dengxiaoqiujing < -0.5) {//低度近视
                freeMap.put("didujinshiNumber", freeMap.get("didujinshiNumber") + 1);
            }
            if (luoyanshilii < 5.0) {
                freeMap.put("buliangshiliNumber", freeMap.get("buliangshiliNumber") + 1);
            }
            if (dengxiaoqiujingR > 0.75 || dengxiaoqiujingL > 0.75) {
                freeMap.put("yuanshiNumber", freeMap.get("yuanshiNumber") + 1);
            }
            if ((dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75) ||
                    (dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75)) {
                freeMap.put("zhengshiNumber", freeMap.get("zhengshiNumber") + 1);
            }
            if (Math.abs(dengxiaoqiujingL - dengxiaoqiujingR) >= 1.0) {
                freeMap.put("quguangcenciNumber", freeMap.get("quguangcenciNumber") + 1);
            }
            /**
             * 年龄
             */

            Date da = s.getBirthday();

            if (da != null) {
                int now = Calendar.getInstance().get(Calendar.YEAR);
                Calendar c = Calendar.getInstance();
                c.setTime(da);
                int dd = c.get(Calendar.YEAR);
                int age = now - dd + 1;
                if (age < 6) {
                    freeMap.put("nain6", freeMap.get("nain6") + 1);
                } else if (age >= 6 && age <= 12) {
                    freeMap.put("nain612", freeMap.get("nain612") + 1);
                } else if (age >= 13 && age <= 15) {
                    freeMap.put("nain1315", freeMap.get("nain1315") + 1);
                } else if (age >= 16 && age <= 18) {
                    freeMap.put("nain1618", freeMap.get("nain1618") + 1);
                } else {
                    freeMap.put("nain18", freeMap.get("nain18") + 1);
                }
            }


        }
        long mm = System.currentTimeMillis();
        System.out.println("每一次的遍历时间===" + (mm - ddt));
        studentDOList = null;
        System.gc();
    }

    private static Double FormatDouble(double s) {

        DecimalFormat df = new DecimalFormat("######0.00"); // 保留两位小数

        return Double.parseDouble(df.format(s));
    }

    @Override
    public List<StudentNewDO> queryBySchoolGrade(Integer activityId, String school, Long sysId) {
        return studentNewDao.queryBySchoolGrade(activityId, school, sysId);
    }

    @Override
    public List<StudentNewDO> queryBySchoolStudentClass(Integer activityId, String school, Long sysId, String grade) {
        return studentNewDao.queryBySchoolStudentClass(activityId, school, sysId, grade);
    }

    @Override
    public List<StudentNewDO> schoolGrade(String school, Long sysId) {
        return studentNewDao.schoolGrade(school, sysId);
    }

    @Override
    public List<StudentNewDO> schoolStudentClass(String school, Long sysId, String grade) {
        return studentNewDao.schoolStudentClass(school, sysId, grade);
    }


    private static Object zhuanhuan(Object s) {
        Double d = null;
        if (s instanceof String) {
            if ("".equals((String) s))
                return "";
            d = Double.parseDouble((String) s);
        }
        if (s instanceof Double)
            d = (Double) s;
        System.out.println("d:" + d + " s:" + s);
        if (Math.floor(d) == d)
            return d.intValue();
        return d;
    }

    private static Object zhuanhuan1(Object s) {
        DecimalFormat df1 = new DecimalFormat("0.0");
        String d = null;
        if (s instanceof String) {
            if ("".equals((String) s))
                return "";
            d = (String) s;
        }
        if (s instanceof Double)
            d = df1.format(s);
        System.out.println("d:" + d + " s:" + s);

        return d;
    }


    public boolean isDouble(String s) {

        boolean matches = s.matches("-?[0-9]+.*[0-9]*");

        return matches;

    }

    @Override
    public int activityNum(Integer activityId) {
        return studentNewDao.activityNum(activityId);
    }

    @Override
    public int activityCheckNum(Integer activityId) {
        return studentNewDao.activityCheckNum(activityId);
    }

    @Override
    public List<StudentNewDO> activityIdBySchool(Integer activityId) {
        return studentNewDao.activityIdBySchool(activityId);
    }

    @Override
    public int activitySchoolNum(Integer activityId, Integer schoolId) {
        return studentNewDao.activitySchoolNum(activityId, schoolId);
    }

    @Override
    public int activitySchoolCheckNum(Integer activityId, Integer schoolId) {
        return studentNewDao.activitySchoolCheckNum(activityId, schoolId);
    }

    @Override
    public int countNoShiFan(Map<String, Object> map) {
        return studentNewDao.countNoShiFan(map);
    }

    @Override
    public String qrCode(String identityCard) {
        return studentNewDao.qrCode(identityCard);
    }

}



