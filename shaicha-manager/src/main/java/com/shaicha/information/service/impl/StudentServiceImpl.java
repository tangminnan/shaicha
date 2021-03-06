package com.shaicha.information.service.impl;

import com.shaicha.informationNEW.dao.ResultMainDao;
import com.shaicha.informationNEW.dao.StudentNewDao;
import com.shaicha.informationNEW.domain.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.utils.QRCodeUtil;

import com.shaicha.common.utils.R;
import com.shaicha.common.utils.WordUtils;
import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.StudentService;
import com.shaicha.information.dao.ResultCornealDao;
import com.shaicha.information.dao.ResultDiopterDao;
import com.shaicha.information.dao.ResultOptometryDao;
import com.shaicha.system.config.ExcelUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
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
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private StudentNewDao studentNewDao;
	@Autowired
	private ResultMainDao resultMainDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private ResultOptometryDao resultOptometryDao;
	@Autowired
	private ResultCornealDao resultCornealDao;
	@Autowired
	private ResultDiopterDao resultDiopterDao;
	
	@Override
	public StudentDO get(Integer id){
		return studentDao.get(id);
	}
	
	@Override
	public List<StudentDO> list(Map<String, Object> map){
		return studentDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return studentDao.count(map);
	}
	
	@Override
	public int save(StudentDO student){
		return studentDao.save(student);
	}
	
	@Override
	public int update(StudentDO student){
		return studentDao.update(student);
	}
	
	@Override
	public int remove(Integer id){
		return studentDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return studentDao.batchRemove(ids);
	}
	
	/**
	 * excel数据导入
	 */
	@ResponseBody
	public R importMember(String checkType, MultipartFile file) {
		System.out.println("==============file================"+file);
		int num = 0;
		InputStream in=null;
		Workbook book=null;
		try {
			if(file != null){
				in = file.getInputStream();
				book =ExcelUtils.getBook(in);
				Sheet sheet = book.getSheetAt(0);
				Row row=null;
				String modelType= "",school = "", schoolCode= "";
				for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
					row = sheet.getRow(rowNum);
					if(rowNum==0){
						modelType = ExcelUtils.getCellFormatValue(row.getCell((short)1));//模板类型
						school = ExcelUtils.getCellFormatValue(row.getCell((short)3));//当前学校名称
						schoolCode= ExcelUtils.getCellFormatValue(row.getCell((short)5));//学校编码
					}
					if(rowNum>1){
						String ideentityType = ExcelUtils.getCellFormatValue(row.getCell((short)0));//证件类型
						String identityCard = ExcelUtils.getCellFormatValue(row.getCell((short)1));	//身份证号
						String name = ExcelUtils.getCellFormatValue(row.getCell((short)2));	// 姓名
						String sex = ExcelUtils.getCellFormatValue(row.getCell((short)3));			//性别
						String birthday =row.getCell(4).getStringCellValue();//生日
						String xueBu = ExcelUtils.getCellFormatValue(row.getCell((short)5));		//学部
						String grade = ExcelUtils.getCellFormatValue(row.getCell((short)6));		//年级
						String studentClass = ExcelUtils.getCellFormatValue(row.getCell((short)7));	//班级
						String phone = ExcelUtils.getCellFormatValue(row.getCell((short)8));		//手机号
						String nation = ExcelUtils.getCellFormatValue(row.getCell((short)9));		//民族
						StudentDO student = new StudentDO();
						student.setCheckType(checkType);
						student.setStudentName(name);
						student.setPhone(phone);
						student.setNation(nation);
						student.setSchool(school);
						student.setGrade(grade);
						student.setStudentClass(studentClass.substring(0,1));
						student.setStatus(0);
						student.setIdeentityType(ideentityType);
						student.setXueBu(xueBu);
						student.setSchoolCode(schoolCode);
						student.setModelType(modelType);
						if(sex != null && sex != ""){
							if(sex.equals("男")){
								student.setStudentSex(1);
							}
							if(sex.equals("女")){
								student.setStudentSex(2);
							}
						}
						if(birthday != null && birthday != ""){
								
						//	student.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
						}
						student.setAddTime(new Date());
						if(identityCard != null && identityCard != ""){
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("identityCard", identityCard);
							List<StudentDO> list = studentDao.list(map);
							if(list.size()>0){
								continue;
							}else{
								student.setIdentityCard(identityCard);
								String destPath = bootdoConfig.getUploadPath();
								String rand = new Random().nextInt(99999999)+".jpg";
								//生成二维码
								QRCodeUtil.encode(identityCard, null, destPath+"/"+rand, true);		
								student.setQRCode("/files/"+rand);
							}
						}else{
							continue;
						}
							studentDao.save(student);
							num++;
					}
						
				}
				if(num>0)
					return R.ok("上传成功,共增加["+num+"]条");
				else
					return R.ok("导入用户失败，原因：身份证号为空或已存在");
			}else{
			return R.error("请选择导入的文件!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(book!=null)
					book.close();
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return R.error();	
	}

	
	@Override
	public List<StudentDO> getList() {
		return studentDao.getList();
	}
	
	public Map<String, Object> studentQRcodes(Integer ids){
		Map<String, Object> params = new HashMap<String, Object>();  
		StudentDO studentDO=studentDao.get(ids);
		params.put("studentName",studentDO.getStudentName());
		params.put("grade",studentDO.getGrade()==null?"":studentDO.getGrade());  
		params.put("studentClass",studentDO.getStudentClass()==null?"":studentDO.getStudentClass());
		String identityCard = studentDO.getIdentityCard();
		String code = QRCodeUtil.creatRrCode(identityCard, 500,500);
		params.put("QRCode",code);
		return params;
	}

	/**
	 * 二维码下载
	 * @throws IOException 
	 */
	@Override
	public void downloadErweima(Integer[] ids,HttpServletRequest request,HttpServletResponse response){
		try{
			for(int i=0;i<ids.length;i++){
				Map<String, Object> params = studentQRcodes(ids[i]);
				if(params==null) continue;
				download(request, response, "studentQRcodes.ftl",ids[i].toString(), params);
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
			craeteZipPath(bootdoConfig.getPoiword(),response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			File file=new File(bootdoConfig.getPoiword());
	        if(file.exists()) {
	           File[] files = file.listFiles();
	           for(File f :files)
	              f.delete();
	        }
	     }  
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
        zipOutputStream = new ZipOutputStream(output,Charset.forName("GBK"));  
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

	/**
	 * 答题结果导入
	 */
	@Override
	public R daorudatijiguo(MultipartFile file) {
		try {
			Workbook book =ExcelUtils.getBook(file);
			if(book==null)
				return R.error("文件读取错误");
			Sheet sheet = book.getSheetAt(0);
			Date date = new Date();
			
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				AnswerResultDO answerResultDO = new AnswerResultDO(); 
				Row row = sheet.getRow(rowNum);
				for(int cellNum=0;cellNum<79;cellNum++){
					Cell cell = row.getCell(cellNum);
					String cellContent=ExcelUtils.getCellFormatValue(cell);
					Class<?> clas = AnswerResultDO.class;
					switch(cellNum){
						case  0: break;
						case  1:Method method = clas.getMethod("setStatus",String.class);
								method.invoke(answerResultDO, new Object[] {cellContent}); break;
						case  2:method = clas.getMethod("setFileName",String.class);
								method.invoke(answerResultDO, new Object[] {cellContent}); break;
						case  3:method = clas.getMethod("setIdentityCard",String.class);
								method.invoke(answerResultDO, new Object[] {cellContent}); break;
						default:int index = cellNum-3;
								method = clas.getMethod("setAnswerResult"+index,String.class);
								method.invoke(answerResultDO, new Object[] {cellContent});	
					} 
				}
				answerResultDO.setAddTime(date);
				studentDao.saveAnswer(answerResultDO);
			}	
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}

	@Override
	public List<AnswerResultDO> listDati(Map<String, Object> map) {
		return studentDao.listDati(map);
	}

	@Override
	public int countDati(Map<String, Object> map) {
		return studentDao.countDati(map);
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
		try {
			for(int i=0;i<ids.length;i++){
				Map<String, Object> params = createPeramsMapF(ids[i]);
				if(params==null) continue;
				download(request, response, "普通筛查导出模板.ftl",ids[i].toString(), params);
			}	
			craeteZipPath(bootdoConfig.getPoiword(),response);
		} catch (Exception e) {
				e.printStackTrace();
			}finally{
				File file=new File(bootdoConfig.getPoiword());
		        if(file.exists()) {
		           File[] files = file.listFiles();
		           for(File f :files)
		              f.delete();
		        }
			}
		}		
		
		
	
	
	/**
	 * freemarker导出工具类
	 */
	
	public void download(HttpServletRequest request,HttpServletResponse response,String template,String newWordName,Map dataMap) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(StudentServiceImpl.class, "/");
        Template t = null;
        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate(template,"utf-8");     
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(newWordName.getBytes(),"iso-8859-1")+".docx"))));                 //还有这里要设置编码
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
	private Map<String,Object> createPeramsMapF(Integer id){
		Map<String, Object> params = new HashMap<String, Object>(); 
		//基本信息获取
		StudentDO studentDO = studentDao.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return null;
		params.put("school", studentDO.getSchool());
		params.put("grade",studentDO.getGrade().toString());
		params.put("studentClass",studentDO.getStudentClass().toString());
		params.put("studentName",studentDO.getStudentName());
		params.put("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
		params.put("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentDao.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		params.put("nakedFarvisionOd",resultEyesightDO.getNakedFarvisionOd()==null? "":resultEyesightDO.getNakedFarvisionOd().toString());
		params.put("nakedFarvisionOs",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		params.put("correctionFarvisionOd",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
		params.put("correctionFarvisionOs",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("diopterSL",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("diopterCL",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("diopterAL",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("diopterSR",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("diopterCR",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("diopterAR",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());
		
		//医生的建议（临时数据）
		params.put("doctorchubu","注意用眼卫生");
		params.put("doctortebie","注意用眼卫生，养成良好的用眼习惯");
		System.out.println("===========================");
		System.out.println("===========================");
		return params;
	}

	/**
	 * 示范校筛查结果导出（freemarker模式）
	 */
	@Override
	public void exportWordPBByFreemarkerSHIfanxiao(Integer[] ids,HttpServletRequest request,  HttpServletResponse response) {
		try {
			for(int i=0;i<ids.length;i++){
				Map<String, Object> params = createPeramsMap2F(ids[i]);
				if(params==null) continue;
				download(request, response, "示范学校筛查导出模板.ftl",ids[i].toString(), params);
			}	
			craeteZipPath(bootdoConfig.getPoiword(),response);
		} catch (Exception e) {
				e.printStackTrace();
			}finally{
				File file=new File(bootdoConfig.getPoiword());
		        if(file.exists()) {
		           File[] files = file.listFiles();
		           for(File f :files)
		              f.delete();
		        }
			}
	}
	
	/**
	 * 
	 freemarker拼装示范校筛查数据
	 */
	private Map<String,Object> createPeramsMap2F(Integer id){
		Map<String, Object> params = new HashMap<String, Object>(); 
		//基本信息获取
		StudentDO studentDO = studentDao.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return null;
		params.put("school", studentDO.getSchool());
		params.put("grade",studentDO.getGrade().toString());
		params.put("studentClass",studentDO.getStudentClass().toString());
		params.put("studentName",studentDO.getStudentName());
		params.put("studentSex", studentDO.getStudentSex()==null?"":studentDO.getStudentSex()==1? "女":"男");
		params.put("lastCheckTime", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentDao.getLatestResultEyesightDO(studentDO.getId());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		String nakedFarvisionOd="";
		String nakedFarvisionOs="";
		String correctionFarvisionOd="";
		String correctionFarvisionOs="";
		if(resultEyesightDOList.size()>0){
			resultEyesightDO=resultEyesightDOList.get(0);
			nakedFarvisionOd=resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString();
			nakedFarvisionOs=resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString();
			correctionFarvisionOd=resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString();
			correctionFarvisionOs=resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString();
		}
		params.put("nakedFarvisionOd",nakedFarvisionOd);
		params.put("nakedFarvisionOs",nakedFarvisionOs);
		params.put("glassvisionOd",correctionFarvisionOd);
		params.put("glassvisionOs",correctionFarvisionOs);
		//自动电脑验光结果(左眼) 
		double dengxiaoqiujingL = 0.0,dengxiaoqiujingR=0.0;
		List<ResultDiopterDO> resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("diopterSL",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("diopterCL",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("diopterAL",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		dengxiaoqiujingL=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("diopterSR",resultDiopterDO.getDiopterS()==null?"":resultDiopterDO.getDiopterS().toString());
		params.put("diopterCR",resultDiopterDO.getDiopterC()==null?"":resultDiopterDO.getDiopterC().toString());
		params.put("diopterAR",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
		dengxiaoqiujingR=resultDiopterDO.getDengxiaoqiujing()==null?0.0:resultDiopterDO.getDengxiaoqiujing();
		
		//眼内压结果拼装
		List<ResultEyepressureDO> ResultEyepressureDOList = studentDao.getLatestResultEyepressureDO(studentDO.getId());
		ResultEyepressureDO resultEyepressureDO = new ResultEyepressureDO();
		if(ResultEyepressureDOList.size()>0)
			resultEyepressureDO=ResultEyepressureDOList.get(0);
		params.put("eyePressureOd",resultEyepressureDO.getEyePressureOd()==null?"":resultEyepressureDO.getEyePressureOd().toString());
		params.put("eyePressureOs", resultEyepressureDO.getEyePressureOs()==null?"":resultEyepressureDO.getEyePressureOs().toString());
		//眼轴长度数据拼装
		List<ResultEyeaxisDO> resultEyeaxisDOList = studentDao.getLatelestResultEyeaxisDO(studentDO.getId());
		ResultEyeaxisDO resultEyeaxisDO = new ResultEyeaxisDO();
		if(resultEyeaxisDOList.size()>0)
			resultEyeaxisDO=resultEyeaxisDOList.get(0);
		params.put("secondCheckOd",resultEyeaxisDO.getFirstCheckOd()==null?"":resultEyeaxisDO.getFirstCheckOd().toString());
		params.put("secondCheckOs", resultEyeaxisDO.getFirstCheckOs()==null?"":resultEyeaxisDO.getFirstCheckOs().toString());
		
		System.out.println("===========================");
		System.out.println("===========================");
		//角膜验光拼装
		ResultCornealDO resultCornealDO = new ResultCornealDO();
		List<ResultCornealDO> resultCornealDOList = studentDao.getResultCornealDOList(studentDO.getId(),"R","R1");
		if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
		params.put("cornealMmr1R",resultCornealDO.getCornealMm()==null?"0.0":resultCornealDO.getCornealMm());
		params.put("cornealDr1R", resultCornealDO.getCornealD()==null?"0.0":resultCornealDO.getCornealD());
		resultCornealDO = new ResultCornealDO();
		resultCornealDOList = studentDao.getResultCornealDOList(studentDO.getId(),"R","R2");
		if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
		params.put("cornealMmr2R",resultCornealDO.getCornealMm()==null?"0.0":resultCornealDO.getCornealMm());
		params.put("cornealDr2R", resultCornealDO.getCornealD()==null?"0.0":resultCornealDO.getCornealD());
		
		resultCornealDO = new ResultCornealDO();
	    resultCornealDOList = studentDao.getResultCornealDOList(studentDO.getId(),"L","R1");
	    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);
	    params.put("cornealMmr1L",resultCornealDO.getCornealMm()==null?"0.0":resultCornealDO.getCornealMm());
	    params.put("cornealDr1L", resultCornealDO.getCornealD()==null?"0.0":resultCornealDO.getCornealD());
		
		
	    
	    resultCornealDO = new ResultCornealDO();
	    resultCornealDOList = studentDao.getResultCornealDOList(studentDO.getId(),"L","R2");
	    if(resultCornealDOList.size()>0) resultCornealDO = resultCornealDOList.get(0);

	    params.put("cornealMmr2L",resultCornealDO.getCornealMm()==null?"0.0":resultCornealDO.getCornealMm());
	    params.put("cornealDr2L", resultCornealDO.getCornealD()==null?"0.0":resultCornealDO.getCornealD());
		
	  //医生的建议
		   double od=0.0,os=0.0;
		   if(!StringUtils.isBlank(nakedFarvisionOd)){
		    	od=Double.parseDouble(nakedFarvisionOd);
		    }
		    if(!StringUtils.isBlank(nakedFarvisionOs)){
		    	os=Double.parseDouble(nakedFarvisionOs);
		    }
//		    od=od<os?od:os;
//		    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
		    double yuanjingshiliL=0,yuanjingshiliR=0;//原镜视力
		    String ssL="ss",ssR="ss";
		    if(!StringUtils.isBlank(correctionFarvisionOd)){
//		    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
		    	yuanjingshiliR=Double.parseDouble(correctionFarvisionOd);
		    }
		    if(!StringUtils.isBlank(correctionFarvisionOs)){
//		    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
		    	yuanjingshiliL=Double.parseDouble(correctionFarvisionOs);
		    }
		    if(yuanjingshiliL==0)
		    	ssL="wuyuanjing";
		    if(yuanjingshiliR==0)
		    	ssR="wuyuanjing";
		    if(od>=5.0 && dengxiaoqiujingR>0.75){
		    	params.put("ydoctorchubu","视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
		//    	model.addAttribute("yujing","无");
		    }
		    if(os>=5.0 && dengxiaoqiujingL>0.75){
		    	params.put("zdoctorchubu","视力目前正常 。请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
		//    	model.addAttribute("yujing","无");
		    }
		    
			if(od>=5.0 && dengxiaoqiujingR>=-0.5 && dengxiaoqiujingR<=0.75){
				params.put("ydoctorchubu","视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
		//    	model.addAttribute("yujing","近视临床前期");
			}
			if(os>=5.0 && dengxiaoqiujingL>=-0.5 && dengxiaoqiujingL<=0.75){
				params.put("zdoctorchubu","视力目前正常，近视临床前期。 请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
		//    	model.addAttribute("yujing","近视临床前期");
			}
			
			if(od>=5.0 && dengxiaoqiujingR<-0.5){
				params.put("ydoctorchubu","视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
		//    	model.addAttribute("yujing","假性近视");
			}
			if(os>=5.0 && dengxiaoqiujingL<-0.5){
				params.put("zdoctorchubu","视力目前正常，假性近视，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
		//    	model.addAttribute("yujing","假性近视");
			}
			
			if(od<5.0 &&dengxiaoqiujingR>=-0.5 && yuanjingshiliR==0 && ssR.equals("wuyuanjing")){
				params.put("ydoctorchubu","视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
		//    	model.addAttribute("yujing","无");
			}
			if(os<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshiliL==0 && ssL.equals("wuyuanjing")){
				params.put("zdoctorchubu","视力异常。建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
		//    	model.addAttribute("yujing","无");
			}
			
			if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR==0 && ssR.equals("wuyuanjing")){
				params.put("ydoctorchubu","视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
		//    	model.addAttribute("yujing","近视");
			}
			if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL==0 && ssL.equals("wuyuanjing")){
				params.put("zdoctorchubu","视力下降，近视。建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
		//    	model.addAttribute("yujing","近视");
			}
		
			if(od<5.0 && dengxiaoqiujingR>=-0.5 && yuanjingshiliR>=5.0){
				params.put("ydoctorchubu","戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
		//    	model.addAttribute("yujing","无");
			}
			if(os<5.0 && dengxiaoqiujingL>=-0.5 && yuanjingshiliL>=5.0){
				params.put("zdoctorchubu","戴原镜视力正常。请继续佩戴原来的眼镜，遵医嘱定期复查。");
		//    	model.addAttribute("yujing","无");
			}
			
			if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR>=5.0){
				params.put("ydoctorchubu","戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
		//    	model.addAttribute("yujing","近视");
			}
			if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL>=5.0){
				params.put("zdoctorchubu","戴原镜视力正常，近视。请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
		//    	model.addAttribute("yujing","近视");
			}
			
			if(od<5.0 &&dengxiaoqiujingR>=-0.5 && yuanjingshiliR<5.0 && ssR.equals("ss")){
				params.put("ydoctorchubu","戴原镜视力异常。 请遵医嘱及时定期复查。");
		//    	model.addAttribute("yujing","无");
			}
			if(os<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshiliL<5.0 && ssL.equals("ss")){
				params.put("zdoctorchubu","戴原镜视力异常。 请遵医嘱及时定期复查。");
		//    	model.addAttribute("yujing","无");
			}
			
			if(od<5.0 && dengxiaoqiujingR<-0.5 && yuanjingshiliR<5.0 && ssR.equals("ss")){
				params.put("ydoctorchubu","戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
		//    	model.addAttribute("yujing","近视增长");
			}
			if(os<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshiliL<5.0 && ssL.equals("ss")){
				params.put("zdoctorchubu","戴原镜视力异常，近视增长。 请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
		//    	model.addAttribute("yujing","近视增长");
			}
				
		return params;
	}

	@Override
	public List<ResultEyesightDO> getLatestResultEyesightDO(Integer id) {
		return studentDao.getLatestResultEyesightDO(id);
	}

	@Override
	public List<ResultDiopterDO> getLatestResultDiopterDOListL(Integer id, String string) {
		return studentDao.getLatestResultDiopterDOListL(id,string);
	}

	@Override
	public List<ResultEyepressureDO> getLatestResultEyepressureDO(Integer id) {
		return studentDao.getLatestResultEyepressureDO(id);
	}

	@Override
	public List<ResultEyeaxisDO> getLatelestResultEyeaxisDO(Integer id) {
		return studentDao.getLatelestResultEyeaxisDO(id);
	}
	

	public List<ResultCornealDO> getResultCornealDOList(Integer studentId, String ifrl, String type) {
		return studentDao.getResultCornealDOList(studentId, ifrl, type);
	}


//
//	@Override
//	public List<StudentDO> querySchoolName() {
//		
//		return studentDao.querySchoolName();
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
//		forsave();
		Map<String,Object> paMap = new HashMap<String,Object>();
		int totalnumber =studentDao.countP(paMap);
		totalnumber+=studentNewDao.countP(paMap);
		paMap.put("checkType","SHI_FANXIAO");
		Calendar calendar  = Calendar.getInstance();
		calendar.set(Calendar.YEAR,2020);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		String str = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		paMap.put("lastCheckTime",str);
		int shifan = studentNewDao.countS(paMap);
		paMap.put("checkType","PU_TONG");
		int putong = studentNewDao.countP(paMap);

		ResultMainDO resultMainDO = resultMainDao.get(4);
		Map<String,Double> freeMap = new HashMap<String,Double>();

		 freeMap.put("totalnumber",(double)totalnumber);
		 freeMap.put("shifanxiaoshaicha",(double)shifan);
		 freeMap.put("putongshaicha",(double)putong);
		 freeMap.put("nain6",resultMainDO.getAge6());
		 freeMap.put("nain612",resultMainDO.getAge612());
		 freeMap.put("nain1315",resultMainDO.getAge1315());
		 freeMap.put("nain1618",resultMainDO.getAge1618());
		 freeMap.put("nain18",resultMainDO.getAge19());
		freeMap.put("gaodujinshiNumber", resultMainDO.getGaodujinshinumber());
		freeMap.put("zhongdujinshiNumber",resultMainDO.getZhongdujinshinumber());
		freeMap.put("didujinshiNumber",resultMainDO.getDidujinshinumber());
		freeMap.put("zhengshiNumber", resultMainDO.getZhengshinumber());
		freeMap.put("buliangshiliNumber",resultMainDO.getBuliangshilinumber());
		freeMap.put("yuanshiNumber",resultMainDO.getYuanshinumber());
		freeMap.put("quguangcenciNumber",resultMainDO.getQuguangcencinumber());

		 Double jinshizongjiNumber=freeMap.get("didujinshiNumber")+freeMap.get("zhongdujinshiNumber")
		 					+freeMap.get("gaodujinshiNumber");//近视总人数
		 freeMap.put("zhengchang",totalnumber-jinshizongjiNumber);
		 freeMap.put("jinshilv",totalnumber==0?0:FormatDouble((double)(jinshizongjiNumber*10000/totalnumber)/100));
		 freeMap.put("buliangshililv", totalnumber==0?0:FormatDouble((double)(freeMap.get("buliangshiliNumber")*10000/totalnumber)/100));
		 freeMap.put("wubuliang", totalnumber-freeMap.get("buliangshiliNumber"));
		 freeMap.put("yunshihuanbinglv",totalnumber==0?0:FormatDouble((double)(freeMap.get("yuanshiNumber")*10000/totalnumber)/100));
		 freeMap.put("wuyuanshi",totalnumber-freeMap.get("yuanshiNumber"));
		 freeMap.put("wuzhengshi", totalnumber-freeMap.get("zhengshiNumber"));
		 freeMap.put("zhengshiyanlv",totalnumber==0?0:FormatDouble((double)(freeMap.get("zhengshiNumber")*10000/totalnumber)/100));
		 freeMap.put("quguangcenciNumberLv",totalnumber==0?0:FormatDouble((double)(freeMap.get("quguangcenciNumber")*10000/totalnumber)/100) );
		 freeMap.put("wuquguangcenci", totalnumber-freeMap.get("quguangcenciNumber"));
		 return freeMap;
	}
	@Scheduled(cron = "0 0 0 1/1 * ? ")
	private void forsave(){
		Map<String,Object> paMap = new HashMap<String,Object>();
		int totalnumber =studentDao.countP(paMap);
		int newtotalnumber=studentNewDao.countP(paMap);
		ResultMainDO resultMainDO = new ResultMainDO();
		resultMainDO.setGaodujinshinumber(0.0);
		resultMainDO.setZhongdujinshinumber(0.0);
		resultMainDO.setDidujinshinumber(0.0);
		resultMainDO.setBuliangshilinumber(0.0);
		resultMainDO.setYuanshinumber(0.0);
		resultMainDO.setZhengshinumber(0.0);
		resultMainDO.setQuguangcencinumber(0.0);
		resultMainDO.setAge6(0.0);
		resultMainDO.setAge612(0.0);
		resultMainDO.setAge1315(0.0);
		resultMainDO.setAge1618(0.0);
		resultMainDO.setAge19(0.0);
		for(int i=1;i<=totalnumber/150000+1;i++){
			List<StudentDO> studentDOList = studentDao.getAllCheckStudentDO((i-1)*150000,150000);
			savemain(studentDOList,resultMainDO);
		}
		for(int i=1;i<=newtotalnumber/150000+1;i++){
			List<StudentDO> studentDOList = studentDao.getnewAllCheckStudentDO((i-1)*150000,150000);
			savemain(studentDOList,resultMainDO);
		}

		resultMainDO.setId(4);
		resultMainDao.update(resultMainDO);
	}
	private void savemain(List<StudentDO> studentDOList,ResultMainDO resultMainDO) {
		long ddt = System.currentTimeMillis();
		System.out.println("查到的数==============" + studentDOList.size());
		for (StudentDO s : studentDOList) {
			Double luoyanshilii = 0.0;
			Double dengxiaoqiujing = 0.0;
			String nakedFarvisionOd = s.getNakedFarvisionOd();
			String nakedFarvisionOs = s.getNakedFarvisionOs();
			nakedFarvisionOd = nakedFarvisionOd.compareTo(nakedFarvisionOs) > 0 ? nakedFarvisionOs : nakedFarvisionOd;
			if (!StringUtils.isBlank(nakedFarvisionOd)) {
				if ("1.0".equals(nakedFarvisionOd) || "10/10".equals(nakedFarvisionOd) || "5.0".equals(nakedFarvisionOd) ) {
					luoyanshilii = 5.0;
				}
			}
				Double dengxiaoqiujingR = s.getDengxiaoqiujingr();
				Double dengxiaoqiujingL = s.getDengxiaoqiujingl();
				dengxiaoqiujing = dengxiaoqiujingR > dengxiaoqiujingL ? dengxiaoqiujingL : dengxiaoqiujingR;

				if (luoyanshilii < 5.0 && dengxiaoqiujing < -6.0) {//高度近视
					resultMainDO.setGaodujinshinumber(resultMainDO.getGaodujinshinumber() + 1);
				}
				if (luoyanshilii < 5.0 && dengxiaoqiujing > -6.0 && dengxiaoqiujing < -3.25) {//中度近视
					resultMainDO.setZhongdujinshinumber(resultMainDO.getZhongdujinshinumber() + 1);
				}
				if (luoyanshilii < 5.0 && dengxiaoqiujing > -3.0 && dengxiaoqiujing < -0.5) {//低度近视
					resultMainDO.setDidujinshinumber(resultMainDO.getDidujinshinumber() + 1);
				}
				if (luoyanshilii < 5.0) {
					resultMainDO.setBuliangshilinumber(resultMainDO.getBuliangshilinumber() + 1);
				}
				if (dengxiaoqiujingR > 0.75 || dengxiaoqiujingL > 0.75) {
					resultMainDO.setYuanshinumber(resultMainDO.getYuanshinumber() + 1);
				}
				if ((dengxiaoqiujingR >= -0.5 && dengxiaoqiujingR <= 0.75) ||
						(dengxiaoqiujingL >= -0.5 && dengxiaoqiujingL <= 0.75)) {
					resultMainDO.setZhengshinumber(resultMainDO.getZhengshinumber() + 1);
				}
				if (Math.abs(dengxiaoqiujingL - dengxiaoqiujingR) >= 1.0) {
					resultMainDO.setQuguangcencinumber(resultMainDO.getQuguangcencinumber() + 1);
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
						resultMainDO.setAge6(resultMainDO.getAge6() + 1);
					} else if (age >= 6 && age <= 12) {
						resultMainDO.setAge612(resultMainDO.getAge612() + 1);
					} else if (age >= 13 && age <= 15) {
						resultMainDO.setAge1315(resultMainDO.getAge1315() + 1);
					} else if (age >= 16 && age <= 18) {
						resultMainDO.setAge1618(resultMainDO.getAge1618() + 1);
					} else {
						resultMainDO.setAge19(resultMainDO.getAge19() + 1);
					}
				}


			}
			long mm = System.currentTimeMillis();
			System.out.println("每一次的遍历时间===" + (mm - ddt));
			System.gc();
		}

	
	private static Double FormatDouble(double s){
		
			 DecimalFormat    df   = new DecimalFormat("######0.00"); // 保留两位小数
				
	         return Double.parseDouble(df.format(s));
		}

	@Override
	public List<StudentDO> querylistStudentName(String studentname, Integer offset, Integer limit) {
		return studentDao.querylistStudentName(studentname,offset,limit);
	}

	@Override
	public List<StudentDO> queryBySchoolGrade(Integer activityId, String school) {
		return studentDao.queryBySchoolGrade(activityId, school);
	}

	@Override
	public List<StudentDO> queryBySchoolStudentClass(Integer activityId, String school) {
		return studentDao.queryBySchoolStudentClass(activityId, school);
	}

	
	
}



