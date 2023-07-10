package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;
//constructor for reading excel path
public ExcelFileUtil(String Excelpath)throws Throwable
{
	FileInputStream fi =  new FileInputStream(Excelpath);
	wb = WorkbookFactory.create(fi);
}
//method for counting no of rows
public int rowCount(String SheetName)
{
	return wb.getSheet(SheetName).getLastRowNum();
}
//method reading cell data
public String getCellData(String SheetName,int row,int column)
{
	String data ="";
	if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
{
	int celldata = (int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
	data = String.valueOf(celldata);
}
	else
	{
	data= wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
//method for set cell data
public void setCellData(String SheetName,int row,int column,String status,String WriteExcel)throws Throwable
{
	//getsheet from wb
	Sheet ws = wb.getSheet(SheetName);
	//get row from sheet
	Row rowNum = ws.getRow(row);
	//create cell
	Cell cell =rowNum.createCell(column);
	//write status into cell
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		//colour text
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(row).setCellStyle(style);
	}
	else  if(status.equalsIgnoreCase("Fail"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		//colour text
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(row).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Blocked"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		//colour text
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(row).setCellStyle(style);
	}
	FileOutputStream fo = new FileOutputStream(WriteExcel);
	wb.write(fo);
}
public static void main(String[] args) throws Throwable {
	ExcelFileUtil xl = new ExcelFileUtil("D:/sample.xlsx");
	int rc = xl.rowCount("Emp");
	System.out.println(rc);
	for(int i=1; i<=rc; i++)
	{
		String fname = xl.getCellData("Emp", i, 0);
		String mname = xl.getCellData("Emp", i, 1);
		String lname = xl.getCellData("Emp", i, 2);
		String eid = xl.getCellData("Emp", i, 3);
		System.out.println(fname+"    "+mname+"      "+lname+"      "+eid);
		xl.setCellData("Emp", i, 4, "Pass", "d:/Results.xlsx");
		//xl.setCellData("Emp", i, 4, "Fail", "d:/Results.xlsx");
		//xl.setCellData("Emp", i, 4, "Blocked", "d:/Results.xlsx");
	}
}
}
