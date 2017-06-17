package ch03Cleaning;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 17.06.2017.
 */
public class Spreadsheets {
    public static void main(String[] args) {
        try(FileInputStream fis = new FileInputStream(new File(RESOURCE_FOLDER + "sample.xlsx"))){
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for(Row row: sheet){
                for(Cell cell: row){
                    switch(cell.getCellTypeEnum()){
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                System.out.println();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
