package Controller.Excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public class SaveExcel {
	public static void SaveExcelFile(Workbook workbook, String fileAddress) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(fileAddress + ".xlsx"));
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
