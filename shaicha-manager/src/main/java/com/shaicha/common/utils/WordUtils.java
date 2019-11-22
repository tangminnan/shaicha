package com.shaicha.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;

import com.shaicha.common.config.BootdoConfig;
import com.shaicha.information.service.impl.StudentServiceImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtils {
	
	
	

	/** 
     * 替换段落里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {  
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
        XWPFParagraph para;  
        while (iterator.hasNext()) {  
            para = iterator.next();  
            replaceInPara(para, params);  
        }  
    }  
    XWPFDocument  temp = new XWPFDocument();
    /** 
     * 替换段落里面的变量 
     * 
     * @param para   要替换的段落 
     * @param params 参数 
     */  
    public static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {  
   //学校:${school}         年级:${grade}         班级:${studentClass} 

    	
      String text = para.getParagraphText();
      System.out.println(text);
    	List<XWPFRun> runs;  
        while (matcher(para.getParagraphText()).find()) {  
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
//                    para.createRun().setText((String) params.get(key));  
                    para.insertNewRun(start).setText((String) params.get(key)); 
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
    
    public static int getPicFormat(String imgFile){
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
    private static Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    }
    
    /** 
     * 替换表格里面的变量 
     * 
     * @param doc    要替换的文档 
     * @param params 参数 
     */  
    public static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {  
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
	            		replaceInPara(para, params);  
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
    public static void close(InputStream is) {  
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
    public static void close(OutputStream os) {  
        if (os != null) {  
            try {  
                os.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    
    
}
