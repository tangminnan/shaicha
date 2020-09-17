package com.shaicha.common.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.shaicha.informationNEW.domain.SchoolNewDO;
import com.shaicha.informationNEW.domain.StudentNewDO;
import com.shaicha.system.config.ExcelUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExcel {
	
	
	private static Logger logger  = Logger.getLogger(ImportExcel.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private final static String[] xiaoxue = {"一年级","二年级","三年级","四年级","五年级","六年级"};
    private final static String[] chuzhong = {"初一","初二","初三","初四"};
    private final static String[] gaozhong = {"高一","高二","高三"};
 
    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static Map<String,Object> readExcel(MultipartFile file,Integer activityId,SchoolNewDO schoolDO,String checkType) throws IOException {
    	Map<String,Object> map = new HashMap<String,Object>();
    
    	//检查文件
        Map<String, Object> checkFile = checkFile(file);
        if(checkFile.get("code").equals("-1")){
        	map.put("code", -1);
        	map.put("msg","文件不存在！");
        	map.put("data", null);
        }
        if(checkFile.get("code").equals("-2")){
        	map.put("code", -1);
        	map.put("msg","不是excel文件");
        	map.put("data", null);
        }
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<StudentNewDO> list = new ArrayList<StudentNewDO>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第二行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
					String identityCard = ExcelUtils.getCellFormatValue(row.getCell((short)1));	//身份证号
                    if(identityCard == null || identityCard == ""){
                    	map.put("code", -1);
                    	map.put("msg","第"+rowNum+"行数据扫描失败，原因：身份证号可能为空");
                    	map.put("data", null);
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                        String cellValue = getCellValue(cell);
                        if(cellValue.equals("非法字符") || cellValue.equals("未知类型")){
                        	map.put("code", -1);
                        	map.put("msg","数据参数有问题！！！");
                        	map.put("data", null);
                        }
                    }
                    StudentNewDO student = excelToObject(cells, activityId, schoolDO, checkType);
                    if(schoolDO.getXuebu().equals("幼儿园")){
                    	if(student.getGrade().equals("幼儿园")){
                    		list.add(student);
                    	}
                    }
                    if(schoolDO.getXuebu().equals("小学")){
                    	if(Arrays.asList(xiaoxue).contains(student.getGrade())){
                    		list.add(student);
                    	}
                    }
                    if(schoolDO.getXuebu().equals("初中")){
                    	if(Arrays.asList(chuzhong).contains(student.getGrade())){
                    		list.add(student);
                    	}
                    }
                    if(schoolDO.getXuebu().equals("高中")){
                    	if(Arrays.asList(gaozhong).contains(student.getGrade())){
                    		list.add(student);
                    	}
                    }
                    //list.add(student);
                }
            }
            workbook.close();
            if(!map.get("code").equals("-1")){
            	map.put("code", 0);
            	map.put("msg", "上传成功");
            	map.put("data", list);
            }
        }else{
        	map.put("code", -1);
        	map.put("msg","请选择导入的文件!");
        	map.put("data", null);
		}
			
		return map;
		
    }
 
    public static Map<String,Object> checkFile(MultipartFile file) throws IOException{
    	Map<String,Object> map = new HashMap<String,Object>();
        //判断文件是否存在
        if(null == file){
            logger.error("文件不存在！");
            map.put("code", "-1");
            //throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            logger.error(fileName + "不是excel文件");
            map.put("code", "-2");
            //throw new IOException(fileName + "不是excel文件");
        }
        return map;
    }
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }
    @SuppressWarnings("deprecation")
	public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            //数字
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            //字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            //空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            //故障
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static StudentNewDO excelToObject(String[] cells,Integer activityId,SchoolNewDO schoolDO,String checkType){
		
    	StudentNewDO student = new StudentNewDO();
		student.setSchoolId(schoolDO.getId());
		student.setSchool(schoolDO.getOrgname());
		student.setSchoolCode(schoolDO.getOrgcode());
		student.setAddress(schoolDO.getAreaname());
		student.setActivityId(activityId);
		student.setCheckType(checkType);
		//student.setSchool(school);
		student.setStatus(0);
		student.setXueBu(schoolDO.getXuebu());
		student.setSysId(ShiroUtils.getUserId());
		//student.setSchoolCode(schoolCode);
		student.setModelType("学校");
		student.setAddTime(new Date());
		String ideentityType="";
        for(int i = 0 ; i<cells.length; i++){
            switch (i){
                case 0:
                	student.setIdeentityType(cells[i]);
                	ideentityType=cells[i];
                    break;
                case 1:
                	if(cells[i]!= null && cells[i] != ""){
                		if(ideentityType.equals("身份证") && cells[i].length() == 18){
							 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String year = cells[i].substring(6, 10);
								String month = cells[i].substring(10, 12);
								String day = cells[i].substring(12, 14);
								String bir = year+"-"+month+"-"+day;
								try {
									student.setBirthday(sdf.parse(bir));
								} catch (ParseException e) {
									e.printStackTrace();
								}
						}
                		student.setIdentityCard(cells[i]);
                	}
                    break;
                case 2:
                	student.setStudentName(cells[i]);
                    break;
                case 3:
                	if(cells[i] != null && cells[i] != ""){
						if(cells[i].equals("男")){
							student.setStudentSex(1);
						}
						if(cells[i].equals("女")){
							student.setStudentSex(2);
						}
					}
                    break;
                case 4:
                	student.setGrade(cells[i]);
                    break;
                case 5:
                	student.setStudentClass(cells[i]);
                    break;
                case 6:
                	student.setPhone(cells[i]);
                    break;
                case 7:
                	student.setNation(cells[i]);
                    break;
                default:
                    System.out.println("参数存在问题");
                    break;
            }
        }
        return student;
    }
 
    public static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }


}
