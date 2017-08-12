import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data {
    private String path = "";
    private String extension = "";
    public Data(File file){
    	path = file.getPath();
	}    
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public ArrayList<List> getListFromExcel() {
        ArrayList<List> dataTotal = new ArrayList();
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(path);
            @SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(fileInput);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            if(extension.equals("xlsx")){
            }else if(extension.equals("xls")){
            	workbook = new XSSFWorkbook(fileInput);
            }else{
            	//System.out.println("File is not in excel format");
            }
            int numberOfSheets = workbook.getNumberOfSheets();
            //looping over each workbook sheet
            for (int i = 0; i < numberOfSheets; i++) {
                List dataTable = new ArrayList();
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                //iterating over each row
                while (rowIterator.hasNext()) {
                    //Student student = new Student();
                    Row row = rowIterator.next();
                    Iterator cellIterator = row.cellIterator();
                    //Iterating over each cell (column wise)  in a particular row.
                    List dataCol = new ArrayList();
                    int colLength = 0;
                                        
                    while (cellIterator.hasNext()) {
                        Cell cell = (Cell) cellIterator.next();
                        //Logger.addLog("Getting Cell");
	                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
	                        	//Logger.addLog("Getting String");
	                            String s = cell.getStringCellValue();
	                            //Logger.addLog("Got String");
	                            dataCol.add(s);
	                        }else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
	                        	//Logger.addLog("Getting Number");
	                        	if(DateUtil.isCellDateFormatted(cell)){
	                            	//Logger.addLog("Getting Date");
	                            	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	                                Date d = cell.getDateCellValue();
	                                String s = df.format(d);
	                                dataCol.add(s);
	                        	}else{
		                        	double d = cell.getNumericCellValue();
		                        	//Logger.addLog("Got Number");
		                            String s = Double.toString(d);
		                            dataCol.add(s);
	                        	}
	                        }else if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
	                        	String s = "";
	                        	//Logger.addLog("Getting Formula");
	                        	CellValue cellValue = evaluator.evaluate(cell);
	                        	//Logger.addLog("Got Formula");
	                        	if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()){
	                            	double d = cellValue.getNumberValue();
	                        		if(DateUtil.isValidExcelDate(d)){
	                        			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		                                Date da = DateUtil.getJavaDate(d);
		                                s = df.format(da);
	                        		}else{
	                        			s = Double.toString(d);
	                        		}
	                        	}else if (Cell.CELL_TYPE_STRING == cellValue.getCellType()){
	                            	s = cellValue.getStringValue();
	                        	}else if (Cell.CELL_TYPE_BOOLEAN == cellValue.getCellType()){
	                        		s = Boolean.toString(cellValue.getBooleanValue());
	                        	}else{
	                        		s = "Null";
	                            	//Logger.addLog("Retrieve Data encountered error");	
	                        	}
	                            dataCol.add(s);
	                        }else if(Cell.CELL_TYPE_BLANK == cell.getCellType()){
	                            //String s = "NULL";
	                            //Logger.addLog("Getting blank");
	                            //dataCol.add(s);
	                        }else if(Cell.CELL_TYPE_BOOLEAN == cell.getCellType()){
	                        	boolean b = cell.getBooleanCellValue();
	                            String s = Boolean.toString(b);
	                            //Logger.addLog("Getting boolean");
	                            dataCol.add(s);
	                        }else{
	                        	//Logger.addLog("Retrieve Data encountered error");
	                        }
                    }
                    if(dataCol.size() <= 0){
                    	break;
                    }
                    dataTable.add(dataCol);
                }
                dataTotal.add(dataTable);
            }
            fileInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataTotal;
    }
}
