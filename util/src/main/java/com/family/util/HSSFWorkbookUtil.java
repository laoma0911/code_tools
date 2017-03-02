package com.family.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


public class HSSFWorkbookUtil {
	/**
	 * 生成普通样式
	 * @param workbook
	 * @return
	 * @date Jul 25, 2013 4:17:51 PM
	 * @author wuzl
	 * @comment
	 */
	public static  HSSFCellStyle createCommonStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();    
		HSSFFont font = workbook.createFont();    
	    font.setFontName("宋体");    
	    font.setFontHeightInPoints((short) 10);
	    style.setFont(font);    
	    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中    
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中    
	    style.setWrapText(true);    
	    style.setLeftBorderColor(HSSFColor.BLACK.index);    
	    style.setBorderLeft((short) 1);    
	    style.setRightBorderColor(HSSFColor.BLACK.index);    
	    style.setBorderRight((short) 1);    
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
	    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色
	    return style;
	}
	/**
	 * 表头样式
	 * @param workbook
	 * @return
	 * @date Jul 25, 2013 4:19:00 PM
	 * @author wuzl
	 * @comment
	 */
	public static  HSSFCellStyle createColumnHeadStyle(HSSFWorkbook workbook){
		HSSFCellStyle columnHeadStyle = workbook.createCellStyle();    
		HSSFFont columnHeadFont = workbook.createFont();    
	    columnHeadFont.setFontName("宋体");    
	    columnHeadFont.setFontHeightInPoints((short) 10);    
	    columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
		columnHeadStyle.setFont(columnHeadFont);    
	    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
	    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中    
	    columnHeadStyle.setLocked(true);    
	    columnHeadStyle.setWrapText(true);    
	    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色    
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小    
	    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色    
	    columnHeadStyle.setBorderRight((short) 1);// 边框的大小    
	    columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色    
	    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）    
	    columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);  
	    return columnHeadStyle;
	} 
}
