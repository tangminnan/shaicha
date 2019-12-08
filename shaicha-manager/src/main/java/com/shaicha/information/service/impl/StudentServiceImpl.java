package com.shaicha.information.service.impl;

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
import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyeaxisDO;
import com.shaicha.information.domain.ResultEyepressureDO;
import com.shaicha.information.domain.ResultEyesightDO;
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


import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
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
						student.setStudentClass(studentClass);
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
							
							student.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
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
				doc.write(new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(studentDO.getIdentityCard().getBytes(),"iso-8859-1")+".docx")));
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
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		params.put("nakedFarvisionOd",resultEyesightDO.getNakedFarvisionOd()==null?"":resultEyesightDO.getNakedFarvisionOd().toString());
		params.put("nakedFarvisionOs",resultEyesightDO.getNakedFarvisionOs()==null?"":resultEyesightDO.getNakedFarvisionOs().toString());
		params.put("glassvisionOd", 11);
		params.put("glassvisionOs", 12);
//		params.put("correctionFarvisionOd",resultEyesightDO.getCorrectionFarvisionOd()==null?"":resultEyesightDO.getCorrectionFarvisionOd().toString());
//		params.put("correctionFarvisionOs",resultEyesightDO.getCorrectionFarvisionOs()==null?"":resultEyesightDO.getCorrectionFarvisionOs().toString());
		
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
		params.put("diopterAR",resultDiopterDO.getDiopterA()==null?"":resultDiopterDO.getDiopterA().toString());;
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
		params.put("secondCheckOd",resultEyeaxisDO.getSecondCheckOd()==null?"":resultEyeaxisDO.getSecondCheckOd().toString());
		params.put("secondCheckOs", resultEyeaxisDO.getSecondCheckOs()==null?"":resultEyeaxisDO.getSecondCheckOs().toString());
		
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
				params.put("doctorchubu","注意用眼卫生");
				
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
	public List<ResultCornealDO> getResultCornealDOList(Integer studentId, String ifrl, String type) {
		return studentDao.getResultCornealDOList(studentId, ifrl, type);
	}

	@Override
	public Map<String, Object> getJInShiLv(Date startDate, Date endDate) {
		//裸眼视力小于5.0 = 近视
		List<ResultEyesightDO> resultDiopterDOList = studentDao.getJInShiLv(startDate,endDate);
		Map<Date,List<ResultEyesightDO>> map = resultDiopterDOList.stream().filter(resultDiopterDO -> Double.parseDouble(resultDiopterDO.getNakedFarvisionOd())<5.0 ||
																	                                  Double.parseDouble(resultDiopterDO.getNakedFarvisionOs())<5.0)
																		  
																				.collect(Collectors.groupingBy(ResultEyesightDO::getCheckDate));
		
		return null;
	}
	
}



