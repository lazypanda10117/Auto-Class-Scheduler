import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Interpretation {
	File f = new File(System.getProperty("user.home") + "/Desktop/Data.xlsx");
	Data dL = new Data(f);
	ArrayList<List> sheet;
	public Interpretation(){
		sheet = dL.getListFromExcel();		
	}
	public List getModuleList(){
		return sheet.get(0);
	}
	public List getTeacherList(){
		return sheet.get(1);
	}
	public List getStudentList(){
		return sheet.get(2);
	}
	public List getRoomList(){
		return sheet.get(3);
	}
	public List getModule(int id){
		return (List) getModuleList().get(id+1); //not including the first row of description
	}
	public List getTeacher(int id){
		return (List) getTeacherList().get(id+1);
	}
	public List getStudent(int id){
		return (List) getStudentList().get(id+1);
	}
	public List getRoom(int id){
		return (List) getRoomList().get(id+1);
	}
	
	public void writeStrToSheet(int sheetId, int sheetRow, int sheetColumn, String context) throws IOException{
		FileInputStream file = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet worksheet = wb.getSheetAt(sheetId); 
		Cell cell = null;
		if(worksheet.getRow(sheetRow) == null){
			 cell = worksheet.createRow(sheetRow).createCell(sheetColumn);
		}else{
			Row row = worksheet.getRow(sheetRow);
			if(worksheet.getRow(sheetRow).getCell(sheetColumn) == null){
				cell = row.createCell(sheetColumn);
			}else{
				cell = row.getCell(sheetColumn);
			}
		}		cell.setCellValue(context);
		file.close();
		FileOutputStream output_file = new FileOutputStream(f);  
		wb.write(output_file);
		output_file.close();
	}
	
	public void writeIntToSheet(int sheetId, int sheetRow, int sheetColumn, int context) throws IOException{
		FileInputStream file = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet worksheet = wb.getSheetAt(sheetId); 
		Cell cell = null;
		if(worksheet.getRow(sheetRow) == null){
			 cell = worksheet.createRow(sheetRow).createCell(sheetColumn);
		}else{
			Row row = worksheet.getRow(sheetRow);
			if(worksheet.getRow(sheetRow).getCell(sheetColumn) == null){
				cell = row.createCell(sheetColumn);
			}else{
				cell = row.getCell(sheetColumn);
			}
		}		cell.setCellValue(context);
		file.close();		
		FileOutputStream output_file = new FileOutputStream(f);  
		wb.write(output_file);
		output_file.close();
	}
	
	public void writeDouToSheet(int sheetId, int sheetRow, int sheetColumn, Double context) throws IOException{
        FileInputStream file = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet worksheet = wb.getSheetAt(sheetId); 
		Cell cell = null;
		if(worksheet.getRow(sheetRow) == null){
			 cell = worksheet.createRow(sheetRow).createCell(sheetColumn);
		}else{
			Row row = worksheet.getRow(sheetRow);
			if(worksheet.getRow(sheetRow).getCell(sheetColumn) == null){
				cell = row.createCell(sheetColumn);
			}else{
				cell = row.getCell(sheetColumn);
			}
		}
		cell.setCellValue(context);
		file.close();
		FileOutputStream output_file = new FileOutputStream(f);  
		wb.write(output_file);
		output_file.close();
	}
	
	public void writeBolToSheet(int sheetId, int sheetRow, int sheetColumn, boolean context) throws IOException{
        FileInputStream file = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet worksheet = wb.getSheetAt(sheetId); 
		Cell cell = null;
		if(worksheet.getRow(sheetRow) == null){
			 cell = worksheet.createRow(sheetRow).createCell(sheetColumn);
		}else{
			Row row = worksheet.getRow(sheetRow);
			if(worksheet.getRow(sheetRow).getCell(sheetColumn) == null){
				cell = row.createCell(sheetColumn);
			}else{
				cell = row.getCell(sheetColumn);
			}
		}
		cell.setCellValue(context);
		file.close();
		FileOutputStream output_file = new FileOutputStream(f);  
		wb.write(output_file);
		output_file.close();
	}
	
	public void clearExcel(int[] clearArr) throws IOException{
		FileInputStream file = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		for(int i=0; i<clearArr.length; i++){
			XSSFSheet sheet = wb.getSheetAt(clearArr[i]);
			//System.out.println("Row Num: " + sheet.getLastRowNum());
			for (int j=0; i<sheet.getLastRowNum();i++) {
				if(j != 0){
					Row row = sheet.getRow(j);	
				}
			}
		}
		file.close();
	}
	
	public String getFilePath(){
		String filePath = "";
		try{
		    File temp = File.createTempFile("tempFile", ".tmp" );
		    String absolutePath = temp.getAbsolutePath();
		    filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		}catch(IOException e){
		    e.printStackTrace();
		}
		return filePath;
	
	}
}
