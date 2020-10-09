package com.shaicha.common.utils;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuxueli.poi.excel.ExcelExportUtil;

public class ExcelExportUtil4DIY extends ExcelExportUtil {
	private static Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);
	
	/**
	* 导出Excel文件到磁盘
	* @param dataList
	* @param outputStream
	*/
	public static void exportToFile(List<Map<String, Object>> dataList, OutputStream outputStream){
		// workbook
		Workbook workbook = exportWorkbooks(dataList);
		try {
			// workbook 2 FileOutputStream
			workbook.write(outputStream);
			outputStream.flush();
			// flush
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (outputStream!=null) {
					outputStream.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	* 导出Excel对象
	*
	* @param dataList  Excel数据
	* @return
	*/
	public static Workbook exportWorkbooks(List<Map<String, Object>> dataList){

		// data
		if (dataList==null || dataList.size()==0) {
			throw new RuntimeException(">>>>>>>>>>> xxl-excel error, data can not be empty.");
		}
		Map<String, Object> mapTitle = dataList.get(0);
		if (mapTitle==null || mapTitle.size()==0) {
			throw new RuntimeException(">>>>>>>>>>> xxl-excel error, data field can not be empty.");
		}

		// book
		Workbook workbook = new HSSFWorkbook();     // HSSFWorkbook=2003/xls、XSSFWorkbook=2007/xlsx
		Sheet sheet = workbook.createSheet("sheet0");
		HSSFColor.HSSFColorPredefined headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN;
		// sheet header row
		CellStyle headStyle = null;
		if (headColor != null) {
			headStyle = workbook.createCellStyle();
           /*Font headFont = book.createFont();
           headFont.setColor(headColor);
           headStyle.setFont(headFont);*/

			headStyle.setFillForegroundColor(headColor.getIndex());
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headStyle.setFillBackgroundColor(headColor.getIndex());
		}

		Row headRow = sheet.createRow(0);
		int i = 0;
		for (Map.Entry<String, Object> entry : mapTitle.entrySet()) {  
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			String fieldName = entry.getKey();
			Cell cellX = headRow.createCell(i, Cell.CELL_TYPE_STRING);
			if (headStyle != null) {
				cellX.setCellStyle(headStyle);
			}
			cellX.setCellValue(String.valueOf(fieldName));
			i++;
		}
		/*
       for (int i = 0; i < fields.size(); i++) {
           Field field = fields.get(i);
           ExcelField excelField = field.getAnnotation(ExcelField.class);
           String fieldName = (excelField!=null && excelField.name()!=null && excelField.name().trim().length()>0)?excelField.name():field.getName();

           Cell cellX = headRow.createCell(i, Cell.CELL_TYPE_STRING);
           if (headStyle != null) {
               cellX.setCellStyle(headStyle);
           }
           cellX.setCellValue(String.valueOf(fieldName));
       }
       */
       // sheet data rows
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			int rowIndex = dataIndex+1;
			Map<String, Object> map = dataList.get(dataIndex);

			Row rowX = sheet.createRow(rowIndex);
			int y = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {  
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
				Cell cellX = rowX.createCell(y, Cell.CELL_TYPE_STRING);
				cellX.setCellValue(String.valueOf(entry.getValue()));
				y++;
			}
			/*
           for (int i = 0; i < fields.size(); i++) {
               Field field = fields.get(i);
               try {
                   field.setAccessible(true);
                   Object fieldValue = field.get(rowData);

                   Cell cellX = rowX.createCell(i, Cell.CELL_TYPE_STRING);
                   cellX.setCellValue(String.valueOf(fieldValue));
               } catch (IllegalAccessException e) {
                   logger.error(e.getMessage(), e);
                   throw new RuntimeException(e);
               }
           }
           */
		}

		return workbook;
	}
}
