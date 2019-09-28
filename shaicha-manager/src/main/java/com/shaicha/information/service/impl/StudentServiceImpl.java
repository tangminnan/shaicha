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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.shaicha.common.config.BootdoConfig;
import com.shaicha.common.utils.QRCodeUtil;
import com.shaicha.common.utils.Query;
import com.shaicha.common.utils.R;
import com.shaicha.common.utils.ShiroUtils;
import com.shaicha.information.dao.StudentDao;
import com.shaicha.information.domain.AnswerResultDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.StudentService;
import com.shaicha.system.config.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	public R importMember(MultipartFile file) {
		System.out.println("==============file================"+file);
		int num = 0;
		InputStream in=null;
		Workbook book=null;
		List<Integer> errnum = new ArrayList<>();
		try {
			if(file != null){
				in = file.getInputStream();
				book =ExcelUtils.getBook(in);
				Sheet sheet = book.getSheetAt(0);
				Row row=null;
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					try {
						row = sheet.getRow(rowNum);
						String name = ExcelUtils.getCellFormatValue(row.getCell((short)0));			//姓名
						String phone = ExcelUtils.getCellFormatValue(row.getCell((short)1));		//手机号
						String nation = ExcelUtils.getCellFormatValue(row.getCell((short)2));		//民族
						String sex = ExcelUtils.getCellFormatValue(row.getCell((short)3));			//性别
						String birthday = ExcelUtils.getCellFormatValue(row.getCell((short)4));		//生日
						String identityCard = ExcelUtils.getCellFormatValue(row.getCell((short)5));	//身份证号
						String school = ExcelUtils.getCellFormatValue(row.getCell((short)6));		//学校
						String grade = ExcelUtils.getCellFormatValue(row.getCell((short)7));		//年级
						String studentClass = ExcelUtils.getCellFormatValue(row.getCell((short)8));	//班级
						String address = ExcelUtils.getCellFormatValue(row.getCell((short)9));		//联系地址
						String height = ExcelUtils.getCellFormatValue(row.getCell((short)10));		//身高
						String weight = ExcelUtils.getCellFormatValue(row.getCell((short)11));		//体重
					//	String addTime = ExcelUtils.getCellFormatValue(row.getCell((short)12));		//添加时间
						StudentDO student = new StudentDO();
				    	
						if(name != null && name !=""){
							student.setStudentName(name);
						}else{
							errnum.add(rowNum);
							continue;
						}
						if(phone != null && phone != ""){
							student.setPhone(phone);
						}else{
							errnum.add(rowNum);
							continue;
						}
						if(identityCard != null && identityCard != ""){
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("identityCard", identityCard);
							List<StudentDO> list = studentDao.list(map);
							if(list.size()>0){
								errnum.add(rowNum);
								continue;
							}else{
								student.setIdentityCard(identityCard);
								//String imgSrc = "https://cli.im/api/qrcode/code?text="+identityCard;
								String destPath = bootdoConfig.getUploadPath();
								String rand = new Random().nextInt(99999999)+".jpg";
								//生成二维码
								QRCodeUtil.encode(identityCard, null, destPath+"/"+rand, true);		
								student.setQRCode("/files/"+rand);
							}
						}else{
							errnum.add(rowNum);
							continue;
						}
						if(nation != null && nation != ""){
							student.setNation(nation);
						}
						if(sex != null && sex != ""){
							if(sex.equals("男")){
								student.setStudentSex(1);
							}
							if(sex.equals("女")){
								student.setStudentSex(2);
							}
						}
						if(birthday != null && birthday != ""){
							Calendar c = new GregorianCalendar(1900,0,-1);
							Date d = c.getTime();
							Date _d = DateUtils.addDays(d, Integer.parseInt(birthday));
							student.setBirthday(_d);
						}
						student.setAddTime(new Date());
//						if(addTime != null && addTime != ""){
//							student.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(addTime));
//						}
						if(height != null && height != ""){
							student.setHeight(Double.parseDouble(height));
						}
						if(weight != null && weight != ""){
							student.setWeight(Double.parseDouble(weight));
						}
						if(school != null && school != ""){
							student.setSchool(school);
						}
						if(grade != null && grade != ""){
							student.setGrade(grade);
						}
						if(studentClass != null && studentClass != ""){
							student.setStudentClass(studentClass);
						}
						if(address != null && address != ""){
							student.setAddress(address);
						}
				    	
						student.setStatus(0);
				    	
					    studentDao.save(student);
				    	num++;
					} catch (Exception e) {
						return R.error("导入失败！第"+rowNum+"条");
					}
				}
				if(errnum.size()>0){
					return R.ok("上传成功,共增加["+num+"]条,第"+errnum+"条上传失败，原因姓名,手机号或者身份证号为空/身份证号重复");
				}else{
					return R.ok("上传成功,共增加["+num+"]条");
				}
				
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
				if(book!=null)
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
}
