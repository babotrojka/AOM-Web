package apachepoi;

import hr.aomatica.constant.Constants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ApachePoiTest {

    @Test
    public void reading() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new File("C:\\Users\\Ivan\\Desktop\\One Ring to rule them all\\ao_stari_web\\Inventura - 2021.xlsx"));
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        Sheet inventura = workbook.getSheet(Constants.ORUZARSTVO_INVENTURA_SHEET);
        for(Row row : inventura) {
            System.out.print("Row: ");
            for(Cell cell : row)
                System.out.print(cell + ", ");
            System.out.println();
        }
    }

    @Test
    public void reading2() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new File("C:\\Users\\Ivan\\Desktop\\One Ring to rule them all\\ao_stari_web\\Inventura - 2021.xlsx"));
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        Sheet inventura = workbook.getSheet(Constants.ORUZARSTVO_INVENTURA_SHEET);
        for(Row row : inventura) {
            if(row.getRowNum() == 0) continue;
            System.out.print("Row: ");
            System.out.println(row.getCell(7) == null ? 0 : row.getCell(7).getNumericCellValue());
        }
    }
}
