package com.edu.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 17:55
 */
public class ExcelUtil {
    /**
     * 写Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @return workbook HSSFWorkbook对象
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return workbook;
    }

    /**
     * 读Excel
     * @param inputStream
     * @param sheetName
     * @return
     * @throws Exception
     */
    public static String[][] getValuesFromExcel(InputStream inputStream, String sheetName) throws Exception{
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        Workbook workbook = WorkbookFactory.create(inputStream);

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = workbook.getSheet(sheetName);

        int rowNum = sheet.getLastRowNum();

        //读取excel表中的内容存到二维数组中
        String[][] values = new String[rowNum+1][sheet.getRow(0).getLastCellNum()+1];
        for(int i=0;i<=rowNum;i++){
            Row row = sheet.getRow(i);
            int cellNum = row.getLastCellNum();
            for(int j=0;j<cellNum;j++){
                Cell cell=row.getCell(j);
                cell.setCellType(CellType.STRING);
                String value = null;
                if(!(cell.getStringCellValue().equals(""))) {
                    value=cell.getStringCellValue();
                }

                System.out.println(value);
                values[i][j] = value;
            }
        }
        return values;
    }
}
