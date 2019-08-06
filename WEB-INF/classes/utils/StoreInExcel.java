package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StoreInExcel {
	public static void store(String filePath,String jsonStr) throws IOException {
			JSONArray jsonArray = JSONArray.fromObject(jsonStr); 
		 
			/*************************前一天的日期*****************************/
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 	Calendar c = Calendar.getInstance();
		 	c.setTime(new Date());
		 	c.add(Calendar.DATE, -1);
		 	Date start = c.getTime();
		 	String qyt= format.format(start);//前一天
		 	/******************************************************************/
		 	// TODO Auto-generated method stub
		 	//创建HSSFWorkbook对象(excel的表单)
		 	HSSFWorkbook wb = new HSSFWorkbook();
		 	//创建HSSFSheet对象//建立新的sheet对象（excel的表单）
		 	HSSFSheet sheet = wb.createSheet("来自aldrinLake.top");
		 	//创建HSSFRow对象 //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		 	HSSFRow row = sheet.createRow(0);
			//创建HSSFCell对象//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cell=row.createCell(0);
			//设置单元格的值
			cell.setCellValue(qyt+"提交情况");
			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
			
			/*********************把json数据的key属性写入第一行******************************/
			int hang = 1; // 行数计数  
			JSONObject first = jsonArray.getJSONObject(0);  
	        Iterator<String> iterator = first.keys(); // 得到第一项的key集合  
	        int lie=0;
	        row=sheet.createRow(hang++);//在sheet里创建第创建行为1（从0开始算）
	        while (iterator.hasNext()) { // 遍历key集合  ，给表格设置tab属性
	        	 String key = (String) iterator.next(); // 得到key  
	        	 //创建单元格并设置单元格内容 
	 			 row.createCell(lie++).setCellValue(key); //列数自增     
	        }
	        /*********************************************************************/
	      
	        /*********************循环写入key属性对应的value值****************************/
	        for (int i = 0; i < jsonArray.size(); i++) {
	        	 JSONObject item = jsonArray.getJSONObject(i); // 得到数组的每项  
	        	 iterator = item.keys(); // 得到key集合  
	        	 lie = 0;// 从第0列开始放  
	        	 row=sheet.createRow(hang++);
	        	 /**设置两个变量来存放差值*/
	        	 int last_grades=0;//上次提交成绩
	        	 int grades=0;//这次提交成绩
	        	 while (iterator.hasNext()) {
	        		 String key = iterator.next(); // 得到key  
	        		 String value = item.getString(key); // 得到key对应的value 
	        		 if("last_grades".equals(key)) {
	        			 last_grades =Integer.parseInt(value);
	        		 }
	        		 if("grades".equals(key)) {
	        			 grades = Integer.parseInt(value);
	        		 }
	        		 row.createCell(lie++).setCellValue(value);
	        	 } 
	        	 row.createCell(lie++).setCellValue(grades-last_grades);//分差存起来
	         }
	        /*******************************************************************/

			//创建HSSFCellStyle
			HSSFCellStyle cellStyle=wb.createCellStyle();
			// 设置单元格的横向和纵向对齐方式，具体参数就不列了，参考HSSFCellStyle
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			row.setRowStyle(cellStyle);
			//创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
			HSSFFont  fontStyle=wb.createFont();
			//设置字体样式
			fontStyle.setFontName("宋体");
			//设置字体高度
			fontStyle.setFontHeightInPoints((short)20);
			//设置字体颜色
			fontStyle.setColor(HSSFColor.BLACK.index);
			//设置粗体
			fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//设置斜体
			fontStyle.setItalic(true);
			//设置下划线
			fontStyle.setUnderline(HSSFFont.U_SINGLE);
			// 将单元格样式应用于单元格
			//创建HSSFCellStyle
			HSSFCellStyle cellStyle2=wb.createCellStyle();
			cellStyle2.setFont(fontStyle);
			//row4.createCell(3).setCellStyle(cellStyle2);
			//输出Excel文件
			FileOutputStream output=new FileOutputStream(filePath);//写入目的文件
			wb.write(output);
			output.flush();

	}
}
