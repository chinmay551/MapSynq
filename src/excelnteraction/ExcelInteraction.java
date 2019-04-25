package excelnteraction;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelInteraction {

	Workbook workbook;

	public String getCellData(String filePath,String fileName,String sheetName,int rowno,int colno) throws IOException{
		File file = new File(filePath+"\\"+fileName);
		FileInputStream stream = new FileInputStream(file);
		String fileExtension = fileName.substring(fileName.indexOf("."));
		
		if(fileExtension.equals(".xls")){
			workbook = new HSSFWorkbook(stream);
		}else if(fileExtension.equals(".xlsx")){
			workbook = new XSSFWorkbook(stream);
			
		}
			
		Sheet sheet = workbook.getSheet(sheetName);
		
		Row row = sheet.getRow(rowno);
		// format cell due to error " Cannot get a STRING value from a NUMERIC cell"
		DataFormatter formatter = new DataFormatter();
		Cell cell=row.getCell(colno);
		 String stringValue = formatter.formatCellValue(cell);		
		return stringValue;
		
		 //return row.getCell(colno).getStringCellValue();
	}

	public void generateReport(String filePath, String fileName) throws IOException{
		File file = new File(filePath+"\\"+fileName);
		FileOutputStream stream = new FileOutputStream(file);
		workbook.write(stream);
	}

	public CellStyle customizeCell(String status){
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		if(status.equals("Pass")){
			style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}else if(status.equals("Fail")){
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		
		return style;
	}

	public void closeExcel() throws IOException{
		workbook.close();
	}

}
