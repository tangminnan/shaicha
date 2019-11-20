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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;




import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.utils.QRCodeUtil;

import com.shaicha.common.utils.R;

import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.StudentService;
import com.shaicha.system.config.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;


import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
					return R.ok();
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
				this.replaceInPara(doc, params);  
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
     * 替换段落里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
        XWPFParagraph para;  
        while (iterator.hasNext()) {  
            para = iterator.next();  
            this.replaceInPara(para, params);  
        }  
    }  
    XWPFDocument  temp = new XWPFDocument();
    /** 
     * 替换段落里面的变量 
     * 
     * @param para   要替换的段落 
     * @param params 参数 
     */  
    public void replaceInPara(XWPFParagraph para, Map<String, Object> params) {  
      String text = para.getParagraphText();
      System.out.println(text);
    	List<XWPFRun> runs;  
        if (this.matcher(para.getParagraphText()).find()) {  
            runs = para.getRuns();  
            int start = -1;  
            int end = -1;  
            String str = "";  
            for (int i = 0; i < runs.size(); i++) {  
                XWPFRun run = runs.get(i);  
                String runText = run.toString().trim();  
               if ('$' == runText.charAt(0)&&'{' == runText.charAt(1)) {  
                    start = i;  
                }  
                if ((start != -1)) {  
                    str += runText;  
               }  
                if ('}' == runText.charAt(runText.length() - 1)) {  
                    if (start != -1) {  
                        end = i;  
                        break;  
                    }  
                }
            } 
           for (int i = start; i <= end; i++) {  
               para.removeRun(i);  
               i--;  
                end--;  
            }  
           String s="";
            for (String key : params.keySet()) {  
                if (str.trim().equals(key) && !(params.get(key) instanceof InputStream)) {  
                    para.createRun().setText((String) params.get(key));  
                    s=key;
                    break; 
                }
                else if(str.equals(key) && params.get(key) instanceof InputStream){//插入图片
                	try {
						XWPFRun imageCellRunn = para.createRun();
						imageCellRunn.addPicture((InputStream)params.get(key), getPicFormat("browser.png"), "browser.png", Units.toEMU(200), Units.toEMU(200));
						s=key;
						System.out.println(key+  "  dssssssssssssssssssssssss");
						break;
                	} catch (InvalidFormatException e) {
						e.printStackTrace();
					}catch (IOException e) {
						e.printStackTrace();
					} 
                }
            } 
            if(!"".equals(s))
            	params.remove(s);
       }  
    }  
    
    public int getPicFormat(String imgFile){
    	int format = 0;
    	 if(imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
         else if(imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
         else if(imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
         else if(imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
         else if(imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
         else if(imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
         else if(imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
         else if(imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
         else if(imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
         else if(imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
         else if(imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
        return format;
    }
    
    /** 
     * 正则匹配字符串 
     * 
     * @param str 
     * @return 
     */  
    private Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    }

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
	@Override
	public void shaichajieguodaochu(Integer[] ids, HttpServletResponse response) {
		for(int i=0;i<ids.length;i++){
			Map<String, Object> params = createPeramsMap(ids[i]);
			if(params==null) continue;
			XWPFDocument doc;  
		    String fileNameInResource = "普通筛查导出模板.docx";  
		    InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);       
			OutputStream os=null;
		    try {
				doc = new XWPFDocument(is);
	//			this.replaceInPara(doc, params);  
				this.replaceInTable(doc, params);
				response.setContentType("application/msword");
		        response.setHeader("Content-disposition","attachment;filename="+new String(ids[i].toString().getBytes(),"iso-8859-1")+".docx");
		        os = response.getOutputStream(); 
		        doc.write(os);  
		        os.flush();
		    } catch (IOException e) {
				e.printStackTrace();
			}finally{
				 this.close(os);  
			     this.close(is);  
			}
		}		
		
		  
	          
	       
	       
	}
	
	/**
	 * 拼装数据
	 */
	private Map<String,Object> createPeramsMap(Integer id){
		Map<String, Object> params = new HashMap<String, Object>(); 
		//基本信息获取
		StudentDO studentDO = studentDao.get(id);
		if(studentDO==null || studentDO.getLastCheckTime()==null) return null;
		params.put("${school}", studentDO.getSchool());
		params.put("${grade}",studentDO.getGrade());
		params.put("${studentClass} ",studentDO.getStudentClass());
		params.put("${studentName}",studentDO.getStudentName());
		params.put("${studentSex}", studentDO.getStudentSex());
		params.put("${lastCheckTime}", new SimpleDateFormat("yyyy-MM-dd").format(studentDO.getLastCheckTime()));
		
		//视力检查结果获取
		List<ResultEyesightDO> resultEyesightDOList = studentDao.getLatestResultEyesightDO(studentDO.getId(),studentDO.getLastCheckTime());
		ResultEyesightDO resultEyesightDO = new ResultEyesightDO();
		if(resultEyesightDOList.size()>0)
			resultEyesightDO=resultEyesightDOList.get(0);
		params.put("${nakedFarvisionOd}",resultEyesightDO.getNakedFarvisionOd());
		params.put("${nakedFarvisionOs}",resultEyesightDO.getNakedFarvisionOs());
		params.put("${correctionFarvisionOd}",resultEyesightDO.getCorrectionFarvisionOd());
		params.put("${correctionFarvisionOs}",resultEyesightDO.getCorrectionFarvisionOs());
		
		//自动电脑验光结果(左眼) 
		List<ResultDiopterDO> resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),studentDO.getLastCheckTime(),"L");
		ResultDiopterDO resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("${diopterSL}",resultDiopterDO.getDiopterS());
		params.put("${diopterCL}",resultDiopterDO.getDiopterC());
		params.put("${diopterAL}",resultDiopterDO.getDiopterA());;
		
		
		
		//自动电脑验光结果(右眼) 
		 resultDiopterDOList = studentDao.getLatestResultDiopterDOListL(studentDO.getId(),studentDO.getLastCheckTime(),"R");
		 resultDiopterDO = new ResultDiopterDO();
		if(resultDiopterDOList.size()>0)
			resultDiopterDO=resultDiopterDOList.get(0);
		params.put("${diopterSR}",resultDiopterDO.getDiopterS());
		params.put("${diopterCR}",resultDiopterDO.getDiopterC());
		params.put("${diopterAR}",resultDiopterDO.getDiopterA());;
		System.out.println("===========================");
		System.out.println("===========================");
		return params;
	}
	
	/** 
     * 替换表格里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFTable> iterator = doc.getTablesIterator();  
        XWPFTable table;  
        List<XWPFTableRow> rows;  
        List<XWPFTableCell> cells;  
        List<XWPFParagraph> paras;  
        while (iterator.hasNext()) {  
            table = iterator.next();  
            rows =  table.getRows();
            for (int i=0;i<rows.size();i++) {
            	cells = rows.get(i).getTableCells();  
	            for (XWPFTableCell cell : cells) {  
	            	paras = cell.getParagraphs(); 
	            	for (XWPFParagraph para : paras) {  
	            		this.replaceInPara(para, params);  
	            	}  
	            }
            
            }  
        }  
    } 
    
    
    /** 
     * 关闭输入流 
     * 
     * @param is 
     */  
    public void close(InputStream is) {  
        if (is != null) {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  

    /** 
     * 关闭输出流 
     * 
     * @param os 
     */  
    public void close(OutputStream os) {  
        if (os != null) {  
            try {  
                os.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}



