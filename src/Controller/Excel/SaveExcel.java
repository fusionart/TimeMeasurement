package Controller.Excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public class SaveExcel {
	public static void SaveExcelFile(Workbook workbook, String fileAddress, String filename) {
		FileOutputStream out;

		File directory = new File(fileAddress);
		if (!directory.exists()) {
			directory.mkdir();
			// If you require it to make the entire directory path including parents,
			// use directory.mkdirs(); here instead.
		}

		try {
			out = new FileOutputStream(new File(fileAddress + filename + ".xlsx"));
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
