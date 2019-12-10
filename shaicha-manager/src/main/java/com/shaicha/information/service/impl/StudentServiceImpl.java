package com.shaicha.information.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.utils.QRCodeUtil;

import com.shaicha.common.utils.R;
import com.shaicha.common.utils.WordUtils;
import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.BuLiangShili;
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.ShiliJinShi;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.StudentService;
import com.shaicha.system.config.ExcelUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	
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

	/**
	 * 二维码下载
	 * @throws IOException 
	 */
	@Override
	public void downloadErweima(Integer[] ids,HttpServletResponse response){
		try{
			for(int i=0;i<ids.length;i++){
				Map<String, Object> params = new HashMap<String, Object>();  
				StudentDO studentDO=studentDao.get(ids[i]);
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
		//医生的建议（临时数据）
	   double od=0.0,os=0.0;
	   if(!StringUtils.isBlank(nakedFarvisionOd)){
	    	od=Double.parseDouble(nakedFarvisionOd);
	    }
	    if(!StringUtils.isBlank(nakedFarvisionOs)){
	    	os=Double.parseDouble(nakedFarvisionOs);
	    }
	    od=od<os?od:os;
	    dengxiaoqiujingL=dengxiaoqiujingL<dengxiaoqiujingR?dengxiaoqiujingL:dengxiaoqiujingR;
	    double yuanjingshili=0;//原镜视力
	    if(!StringUtils.isBlank(correctionFarvisionOd) || !StringUtils.isBlank(correctionFarvisionOs)){
	    	correctionFarvisionOd=correctionFarvisionOd.compareTo(correctionFarvisionOs)>0?correctionFarvisionOs:correctionFarvisionOd;
	    	yuanjingshili=Double.parseDouble(correctionFarvisionOd);
	    }
	    if(od>=5.0 && dengxiaoqiujingL>0.75){
	    	params.put("doctorchubu","请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，更好地进行近视发生的预警。");
	    	params.put("yujing","");
	    }
		if(od>=5.0 && dengxiaoqiujingL>=-0.5 && dengxiaoqiujingL<=0.75){
			params.put("doctorchubu","请注意卫生用眼，避免长时间近距离持续用眼，多参加户外活动，建议建立完善的视觉健康档案，避免近视的发生，更好地进行近视发生的预警。");
			params.put("yujing","近视临床前期");
		}
		if(od>=5.0 && dengxiaoqiujingL<-0.5){
			params.put("doctorchubu","视力目前正常，但有发生近视的可能。建议您到医院进行进一步散瞳检查，以确定是否近期可能发展为近视，并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，避免假性近视发展为真性近视。");
			params.put("yujing","假性近视");
		}
		if(od<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshili==0){
			params.put("doctorchubu","建议及时到医院接受详细检查，明确诊断是否为屈光不正、弱视、斜视、视功能异常以及其他眼病，并及时采取相应治疗措施。");
			params.put("yujing","");
		}
		if(od<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshili==0){
			params.put("doctorchubu","建议及时到医院接受近视的详细检查，通过散瞳明确近视的程度并排除其他眼病，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生");
			params.put("yujing","近视");
		}
	
		if(od<5.0 && dengxiaoqiujingL>=-0.5 && yuanjingshili==1.0){
			params.put("doctorchubu","请继续佩戴原来的眼镜，遵医嘱定期复查。");
			params.put("yujing","");
		}
		if(od<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshili==1.0){
			params.put("doctorchubu","请继续佩戴原来的眼镜，遵医嘱定期复查。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的发生；采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。");
			params.put("yujing","近视");
		}
		if(od<5.0 &&dengxiaoqiujingL>=-0.5 && yuanjingshili<1.0){
			params.put("doctorchubu","请遵医嘱及时定期复查。");
			params.put("yujing","");
		}
		if(od<5.0 && dengxiaoqiujingL<-0.5 && yuanjingshili<1.0){
			params.put("doctorchubu","请及时到医院进行复查，采取科学的方法进行近视的防控或采取相应眼病治疗措施，避免低度近视发展为中度近视，避免中度近视发展为高度近视，减少高度近视的并发症发生。并请严格注意用眼卫生，避免长时间近距离持续用眼，多参加户外活动，建立完善的视觉健康档案，延缓近视的进展。");
			params.put("yujing","近视增长");
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

	@Override
	public List<StudentDO> querySchoolName() {
		return studentDao.querySchoolName();
	}

	public List<ResultCornealDO> getResultCornealDOList(Integer studentId, String ifrl, String type) {
		return studentDao.getResultCornealDOList(studentId, ifrl, type);
	}

	@Override
	public Map<String, Object> getJInShiLv(Date startDate, Date endDate) {
		//裸眼视力小于5.0 = 近视
		 
		List<ResultEyesightDO> resultEyesightDOList = studentDao.getJInShiLv(startDate,endDate);
		Map<Date,List<ResultEyesightDO>> mapc = resultEyesightDOList.stream()
				  													.collect(Collectors.groupingBy(ResultEyesightDO::getCheckDate));																							
		
		Map<Date,List<Long>> cMap = new HashMap<Date,List<Long>>();
		for(Entry<Date,List<ResultEyesightDO>> entry :mapc.entrySet()){
			List<ResultEyesightDO> list = entry.getValue();
			List<Long> l = Arrays.asList(
					list.stream().filter(r -> r.getXueBu().equals("幼儿园")).count(),
					list.stream().filter(r -> r.getXueBu().equals("小学")).count(),
					list.stream().filter(r -> r.getXueBu().equals("初中")).count(),
					list.stream().filter(r -> r.getXueBu().equals("高中")).count()
					);
			cMap.put(entry.getKey(), l);
		}
		Map<Date,List<ResultEyesightDO>> map = resultEyesightDOList.stream()
																  .filter(resultDiopterDO ->resultDiopterDO.getNakedFarvisionOd().equals("")||
																		  resultDiopterDO.getNakedFarvisionOs().equals("")||
																  		  Double.parseDouble(resultDiopterDO.getNakedFarvisionOd())<5.0 ||
																	      Double.parseDouble(resultDiopterDO.getNakedFarvisionOs())<5.0)
																  	.collect(Collectors.groupingBy(ResultEyesightDO::getCheckDate));																							
																		  
		Map<Date,List<Long>> jinMap = new HashMap<Date,List<Long>>();
		for(Entry<Date,List<ResultEyesightDO>> entry :map.entrySet()){
			List<ResultEyesightDO> list = entry.getValue();
			List<Long> l = Arrays.asList(
					list.stream().filter(r -> r.getXueBu().equals("幼儿园")).count(),
					list.stream().filter(r -> r.getXueBu().equals("小学")).count(),
					list.stream().filter(r -> r.getXueBu().equals("初中")).count(),
					list.stream().filter(r -> r.getXueBu().equals("高中")).count()
					);
			jinMap.put(entry.getKey(), l);
		}
		
		Set<Date> set  = cMap.keySet();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(Date d :set){
			List<Long> lj = jinMap.get(d);
			List<Long> lc = cMap.get(d);
			List<Float> l = new ArrayList<Float>();
			for(int i=0;i<lj.size();i++){
				l.add(lc.get(i)==0? 0f:(float)lj.get(i)*100/lc.get(i));
			}
			resultMap.put(new SimpleDateFormat("yyyy-MM-dd").format(d), l);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> getJInShiLvSex(Date startDate, Date endDate) {
		//裸眼视力小于5.0 = 近视
		 List<ResultEyesightDO> resultEyesightDOList = studentDao.getJInShiLv(startDate,endDate);
		 Map<Integer,List<ResultEyesightDO>> mapc = resultEyesightDOList.stream()
						  													.collect(Collectors.groupingBy(ResultEyesightDO::getStudentSex));																							
		 List<Date> dateList = resultEyesightDOList.stream().map(ResultEyesightDO::getCheckDate).distinct().sorted().collect(Collectors.toList());
				
		Map<Integer,List<Long>> cMap = new HashMap<Integer,List<Long>>();
		for(Entry<Integer,List<ResultEyesightDO>> entry :mapc.entrySet()){
			List<ResultEyesightDO> list = entry.getValue();
			List<Long> l=  new ArrayList<Long>();
			for(Date date :dateList){
				l.add(list.stream().filter(r ->r.getCheckDate().getTime()== date.getTime()).count());
			}		
			cMap.put(entry.getKey(), l);
		}
		Map<Integer,List<ResultEyesightDO>> map = resultEyesightDOList.stream()
																		  .filter(resultDiopterDO ->resultDiopterDO.getNakedFarvisionOd().equals("")||
																				  resultDiopterDO.getNakedFarvisionOs().equals("")||
																		  		  Double.parseDouble(resultDiopterDO.getNakedFarvisionOd())<5.0 ||
																			      Double.parseDouble(resultDiopterDO.getNakedFarvisionOs())<5.0)
																		  	.collect(Collectors.groupingBy(ResultEyesightDO::getStudentSex));																							
																				  
		Map<Integer,List<Long>> jinMap = new HashMap<Integer,List<Long>>();
		for(Entry<Integer,List<ResultEyesightDO>> entry :map.entrySet()){
			List<ResultEyesightDO> list = entry.getValue();
			List<Long> l = new ArrayList<Long>();
			for(Date date :dateList){
				l.add(list.stream().filter(r ->r.getCheckDate().getTime()== date.getTime()).count());
			}
			jinMap.put(entry.getKey(), l);
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//统计男性的近视率
		List<Long> lj = jinMap.get(1);
		List<Long> lc = cMap.get(1);
		List<Float> l = new ArrayList<Float>();
		for(int i=0;i<lj.size();i++){
			l.add(lc.get(i)==0? 0f:(float)lj.get(i)*100/lc.get(i));
		}
		resultMap.put("男", l);
		//统计女性近视率、
		lj = jinMap.get(2);
		lc = cMap.get(2);
		l = new ArrayList<Float>();
		for(int i=0;i<lj.size();i++){
			l.add(lc.get(i)==0? 0f:(float)lj.get(i)*100/lc.get(i));
		}
		resultMap.put("女", l);
		List<String> tt =  dateList.stream().map(d ->new SimpleDateFormat("yyyy-MM-dd").format(d)).collect(Collectors.toList());
		resultMap.put("time", tt);
		return resultMap;
	}
	
	/**
	 * 生成给教育局的报告（数据拼装）
	 */
	
	public  Map<String,Object>  createDataToJiAOYuJu(Date startDate,Date endDate){
		Map<String,Object> freeMap = new HashMap<String,Object>();
		
		 List<ResultEyesightDO> resultEyesightDOList = studentDao.getJInShiLv(startDate,endDate);
		 List<ResultDiopterDO> resultDiopterDOList = studentDao.getResultDiopterDO(startDate,endDate);
		 //近视视力统计
		 Map<String, List<ResultEyesightDO>> jinshitongjiMap = resultEyesightDOList.stream().collect(Collectors.groupingBy(ResultEyesightDO::getSchool));
		 List<ShiliJinShi> shiliJinShiList = new ArrayList<ShiliJinShi>();
		 for(Entry<String,List<ResultEyesightDO>> entry :jinshitongjiMap.entrySet()){
			 String school = entry.getKey();
			 List<ResultEyesightDO> reiList = entry.getValue();
			
				 Long jslcqianqiNumber=0L,//近视临床前期人数
				 	  jxjsNumber=0L,//假性近视人数;
				 	  didujinshiNumber=0L,//低度近视人数
				 	  zhongdujinshiNumber=0L,//中度近视人数
				 	  gaodujinshiNumber=0L,//高度近视人数
				 	  jinshizongjiNumber=0L;//近视总人数
				String xuebu= "";
			 for(ResultEyesightDO r:reiList){
				 xuebu=r.getXueBu();
				 Double luoyanshilii=0.0;
				 String nakedFarvisionOd=r.getNakedFarvisionOd();
				 String nakedFarvisionOs=r.getNakedFarvisionOs();
				 nakedFarvisionOd=nakedFarvisionOd.compareTo(nakedFarvisionOs)>0?nakedFarvisionOs:nakedFarvisionOd;
				 if(!StringUtils.isBlank(nakedFarvisionOd))
					 luoyanshilii=Double.parseDouble(nakedFarvisionOd);
				 
				 if(!StringUtils.isBlank(r.getNakedFarvisionOd()) && Double.parseDouble(r.getNakedFarvisionOd())>=5.0){
					Double d=     resultDiopterDOList.stream().filter(i ->i.getIdentityCard().equals(r.getIdentityCard()) && i.getIfrl().equals("R")).mapToDouble(ResultDiopterDO::getDengxiaoqiujing).sum();
					if(d>0.5 &&d<=0.75){//近视临床前期（判断右眼）
						jslcqianqiNumber++;
					}
					if(d<0.5){//假性近视（判断右眼）
						jxjsNumber++;
					}
				 }
				else if(!StringUtils.isBlank(r.getNakedFarvisionOs()) &&  Double.parseDouble(r.getNakedFarvisionOs())>=5.0){
					 Double d=     resultDiopterDOList.stream().filter(i ->i.getIdentityCard().equals(r.getIdentityCard()) && i.getIfrl().equals("L")).mapToDouble(ResultDiopterDO::getDengxiaoqiujing).sum();
					if(d>0.5 &&d<=0.75){//近视临床前期（判断左眼）
						jslcqianqiNumber++;
					}
					if(d<0.5){//假性近视（判断左眼）
						jxjsNumber++;
					}
				 }
				if(!StringUtils.isBlank(r.getNakedFarvisionOd()) && Double.parseDouble(r.getNakedFarvisionOd())<5.0){
					 Double d=     resultDiopterDOList.stream().filter(i ->i.getIdentityCard().equals(r.getIdentityCard()) && i.getIfrl().equals("R")).mapToDouble(ResultDiopterDO::getDengxiaoqiujing).sum();
					 if(d>6){//右眼高度近视
						 gaodujinshiNumber++;
					 }
					 if(d>=3.25 && d<=6.0){//右眼中度近视
						 zhongdujinshiNumber++;
					 }
					 if(d>0.5 &&d<=3.0){//右眼低度近视
						 didujinshiNumber++;
					 }
				}
				else if(!StringUtils.isBlank(r.getNakedFarvisionOs()) && Double.parseDouble(r.getNakedFarvisionOs())<5.0){
					 Double d=  resultDiopterDOList.stream().filter(i ->i.getIdentityCard().equals(r.getIdentityCard()) && i.getIfrl().equals("L")).mapToDouble(ResultDiopterDO::getDengxiaoqiujing).sum();
					 if(d>6){//左眼高度近视
						 gaodujinshiNumber++;
					 }
					 if(d>=3.25 && d<=6.0){//左眼中度近视
						 zhongdujinshiNumber++;
					 }
					 if(d>0.5 &&d<=3.0){//左眼低度近视
						 didujinshiNumber++;
					 }
				}
				
				
			 }
				jinshizongjiNumber=didujinshiNumber+zhongdujinshiNumber+gaodujinshiNumber;//高度近视人数

			 ShiliJinShi shiliJinShi = new ShiliJinShi();
			 shiliJinShi.setSchoole(school);
			 shiliJinShi.setXuebu(xuebu);
			 shiliJinShi.setCheckNumbers((long)reiList.size());
			 shiliJinShi.setJxjsNumber(jxjsNumber);
			 shiliJinShi.setJxjszhanbi((double)jxjsNumber/reiList.size());
			 shiliJinShi.setJslcqianqiNumber(jslcqianqiNumber);
			 shiliJinShi.setJslcgqianqizhanbi((double)jslcqianqiNumber/reiList.size());
			 shiliJinShi.setZhongdujinshiNumber(zhongdujinshiNumber);
			 shiliJinShi.setZhongdujinshizhanbi((double)zhongdujinshiNumber/reiList.size());
			 shiliJinShi.setGaodujinshiNumber(gaodujinshiNumber);
			 shiliJinShi.setGaodujinshizhanbi((double)gaodujinshiNumber/reiList.size());
			 shiliJinShi.setJinshizongjiNumber(jinshizongjiNumber);
			 shiliJinShi.setJinshizongzhanbi((double)jinshizongjiNumber/reiList.size());
			 shiliJinShi.setDidujinshiNumber(didujinshiNumber);
			 shiliJinShi.setDidujinshizhanbi((double)didujinshiNumber/reiList.size());
			 
			 shiliJinShi.setA(String.valueOf(shiliJinShi.getSchoole()));
			 shiliJinShi.setB(String.valueOf(shiliJinShi.getXuebu()));
			 shiliJinShi.setC(String.valueOf(shiliJinShi.getCheckNumbers()));
			 
			 shiliJinShi.setD(String.valueOf(shiliJinShi.getJslcqianqiNumber()));
			 shiliJinShi.setE(String.valueOf(shiliJinShi.getJslcgqianqizhanbi()));
			 
			 shiliJinShi.setF(String.valueOf(shiliJinShi.getJxjsNumber()));
			 shiliJinShi.setG(String.valueOf(shiliJinShi.getJxjszhanbi()));
			 
			 shiliJinShi.setH(String.valueOf(shiliJinShi.getDidujinshiNumber()));
			 shiliJinShi.setI(String.valueOf(shiliJinShi.getDidujinshizhanbi()));
			 
			 shiliJinShi.setJ(String.valueOf(shiliJinShi.getZhongdujinshiNumber()));
			 shiliJinShi.setK(String.valueOf(shiliJinShi.getZhongdujinshizhanbi()));
			 
			 shiliJinShi.setL(String.valueOf(shiliJinShi.getGaodujinshiNumber()));
			 shiliJinShi.setM(String.valueOf(shiliJinShi.getGaodujinshizhanbi()));
			 
			 
			 shiliJinShi.setN(String.valueOf(shiliJinShi.getJinshizongjiNumber()));
			 shiliJinShi.setO(String.valueOf(shiliJinShi.getJinshizongzhanbi()));
			 shiliJinShiList.add(shiliJinShi);
			 freeMap.put("shiliJinShi", shiliJinShiList);
			 //计算近视总计
			 Long totalcheckNumbers = shiliJinShiList.stream().map(ShiliJinShi::getCheckNumbers).count();//总的检查人数
			 Long totalJslcqiNumbers = shiliJinShiList.stream().map(ShiliJinShi::getJslcqianqiNumber).count();//总的近视临床前期人数
			 Long totalJxjsNumbers = shiliJinShiList.stream().map(ShiliJinShi::getJxjsNumber).count();//总的假性近视人数
			 Long totalDidujsNumbers = shiliJinShiList.stream().map(ShiliJinShi::getDidujinshiNumber).count();//总的低度近视人数
			 Long totalZhjsNumbers = shiliJinShiList.stream().map(ShiliJinShi::getZhongdujinshiNumber).count();//总的中度近视人数
			 Long totalGaodujsNumbers =  shiliJinShiList.stream().map(ShiliJinShi::getGaodujinshiNumber).count();//总的高度近视人数
			 Long totaljsNumbers = 	shiliJinShiList.stream().map(ShiliJinShi::getJinshizongjiNumber).count();//总的近视人数;	 
			 Double totallczb = (double)totalJslcqiNumbers/resultEyesightDOList.size();//总的近视临床占比
			 Double totaljxzb = (double)totalJxjsNumbers/resultEyesightDOList.size();//总的假性近视人数占比
			 Double totalDiduzb=  (double)totalDidujsNumbers/resultEyesightDOList.size();//总的低度近视占比
			 Double totalzdzb  = (double)totalZhjsNumbers/resultEyesightDOList.size();//总的中度近视占比
			 Double totalgaoduzb = (double)totalGaodujsNumbers/resultEyesightDOList.size();//总的高度近视占比
			 Double totaljszb=(double)totaljsNumbers/resultEyesightDOList.size();//总的近视占比
			 freeMap.put("C", totalcheckNumbers);
			 freeMap.put("D", totalJslcqiNumbers);
			 freeMap.put("E", totallczb);
			 freeMap.put("F", totalJxjsNumbers);
			 freeMap.put("G", totaljxzb);
			 freeMap.put("H", totalDidujsNumbers);
			 freeMap.put("I", totalDiduzb);
			 freeMap.put("J", totalZhjsNumbers);
			 freeMap.put("K", totalzdzb);
			 freeMap.put("L", totalGaodujsNumbers);
			 freeMap.put("M", totalgaoduzb);
			 freeMap.put("N",totaljsNumbers );
			 freeMap.put("O", totaljszb);
		 }
		//不良视力统计
		 List<BuLiangShili> buliangList = new ArrayList<BuLiangShili>();
		 for(Entry<String,List<ResultEyesightDO>> entry :jinshitongjiMap.entrySet()){
			 String school = entry.getKey();
			 List<ResultEyesightDO> reiList = entry.getValue();
			
				 Long qdshilibuliang=0L,//轻度视力不良
				 	  zdshilibuliang=0L,//中度视力不良
				 	  weishilibuliang=0L,//重度视力不良
				 	  buliangtotal=0L;//不良总计
				 	 
				String xuebu= "";
			 for(ResultEyesightDO r:reiList){
				 xuebu=r.getXueBu();
				 Double od = 0.0;//右眼视力
				 Double os = 0.0;//左眼视力
				 if(!StringUtils.isBlank(r.getNakedFarvisionOd())){
					 od =  Double.parseDouble(r.getNakedFarvisionOd());
				 }
				 if(!StringUtils.isBlank(r.getNakedFarvisionOs())){
					os = Double.parseDouble(r.getNakedFarvisionOs());
				 }
				od=od<os?od:os;
				if(od>=4.8&&od<4.9) qdshilibuliang++;
				if(od>=4.6&&od<=4.8) zdshilibuliang++;
				if(od<=4.5)weishilibuliang++;
			 }
			 buliangtotal=qdshilibuliang+zdshilibuliang+weishilibuliang;
			 BuLiangShili buLiangShili = new BuLiangShili();
			 buLiangShili.setQdshilibuliang(qdshilibuliang);
			 buLiangShili.setQingduzhanbi((double)qdshilibuliang/reiList.size());
			 buLiangShili.setZdshilibuliang(zdshilibuliang);
			 buLiangShili.setZhongduzhanbil((double)zdshilibuliang/reiList.size());
			 buLiangShili.setWeishilibuliang(weishilibuliang);
			 buLiangShili.setWeightzhanbi((double)weishilibuliang/reiList.size());
			 buLiangShili.setBuliangtotal(buliangtotal);
			 buLiangShili.setZongjizhanbi((double)buliangtotal/reiList.size());
			 
			 buLiangShili.setI0(school);
			 buLiangShili.setI1(xuebu);
			 buLiangShili.setI2(String.valueOf(reiList.size()));
			 buLiangShili.setI3(String.valueOf(qdshilibuliang));
			 buLiangShili.setI4(String.valueOf(buLiangShili.getQingduzhanbi()));
			 
			 buLiangShili.setI5(String.valueOf(zdshilibuliang));
			 buLiangShili.setI6(String.valueOf(buLiangShili.getZhongduzhanbil()));
			 
			 buLiangShili.setI7(String.valueOf(weishilibuliang));
			 buLiangShili.setI8(String.valueOf(buLiangShili.getWeightzhanbi()));
			 
			 buLiangShili.setI9(String.valueOf(buliangtotal));
			 buLiangShili.setI10(String.valueOf(buLiangShili.getZongjizhanbi()));
			 buliangList.add(buLiangShili);
			 freeMap.put("shilibuliang", buLiangShili);
			 Long totaoqingdu =  buliangList.stream().map(BuLiangShili::getQdshilibuliang).count();//轻度视力不良总计
			 Long totalzhongdu =  buliangList.stream().map(BuLiangShili::getZdshilibuliang).count();//中度视力不良总计
			 Long totalweight = buliangList.stream().map(BuLiangShili::getWeishilibuliang).count();//高度视力不良总计
			 Long totalbuliang = buliangList.stream().map(BuLiangShili::getBuliangtotal).count();//不良视力不良总计
			 double  totaoqingduzb = (double)totaoqingdu/resultEyesightDOList.size();
			 double totalzhongduzb=    (double)totalzhongdu/resultEyesightDOList.size();
			 double totalweightzb=(double)totalweight/resultEyesightDOList.size();
			 double totalbuliangzb=(double)totalbuliang/resultEyesightDOList.size();
			 freeMap.put("i2",String.valueOf(resultEyesightDOList.size()));
			 freeMap.put("i3",String.valueOf(totaoqingdu));
			 freeMap.put("i4", String.valueOf(totaoqingduzb));
			 freeMap.put("i5", String.valueOf(totalzhongdu));
			 freeMap.put("i6", String.valueOf(totalzhongduzb));
			 freeMap.put("i7", String.valueOf(totalweight));
			 freeMap.put("i8", String.valueOf(totalweightzb));
			 freeMap.put("i9", String.valueOf(totalbuliang));
			 freeMap.put("i10",String.valueOf(totalbuliangzb));
		
		
	}
		 return freeMap;
	}
	
	
	
}



