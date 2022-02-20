package Controller.Excel;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import Controller.Base;
import Controller.BaseMethods;
import Model.TimeMeasurementHeader;

public class SaveReport {
	public static void SaveReportFile(Double mainTime, TimeMeasurementHeader tmHeader) {
		Workbook workbook = ReadExcelFile.LoadExcelFile(Base.reportTemplate);
		XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

		XSSFCell cellToUpdate;

		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		// set main time
		cellToUpdate = sheet.getRow(14).getCell(29, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(mainTime);

		// set name
		cellToUpdate = sheet.getRow(3).getCell(9, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getName());

		// set date
		cellToUpdate = sheet.getRow(6).getCell(7, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(BaseMethods.ExtractDate(tmHeader.getCreateDate()));

		// set begin time
		cellToUpdate = sheet.getRow(6).getCell(16, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getStartTime());

		// set end time
		cellToUpdate = sheet.getRow(6).getCell(25, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getEndTime());

		// set duration
		cellToUpdate = sheet.getRow(6).getCell(34, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getDuration());

		evaluator.clearAllCachedResultValues();
		evaluator.evaluateAll();

		SaveExcel.SaveExcelFile(workbook, Base.reportSaveAddress + tmHeader.getName());
	}
}