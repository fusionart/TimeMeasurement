package Controller.Excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Controller.Base;

public class SaveReport {
	public static void SaveReportFile() {
		Workbook workbook = ReadExcelFile.LoadExcelFile(Base.reportTemplate);
		Sheet sheet = workbook.getSheetAt(0);
	}

}
