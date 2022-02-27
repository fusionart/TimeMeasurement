package Controller.Excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import Controller.Base;
import Controller.BaseMethods;
import Controller.CalculateReport;
import Model.PhaseDetails;
import Model.TimeMeasurementDetail;
import Model.TimeMeasurementHeader;
import Model.ZA;

public class SaveReport {
	private TimeMeasurementHeader tmHeader; 
	private List<TimeMeasurementDetail> detailList;
	private List<ZA> zaList;
	
	public SaveReport(TimeMeasurementHeader tmHeader, List<TimeMeasurementDetail> detailList) {
		this.tmHeader = tmHeader;
		this.detailList = detailList;
		LoadZaFile loadZaFile = LoadZaFile.getInstance();
		this.zaList = loadZaFile.getAllRows();
	}

	public void saveReportFile() {
		Workbook workbook = ReadExcelFile.LoadExcelFile(Base.reportTemplate);
		XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		HashMap<Integer, PhaseDetails> sortedTmDetails = CalculateReport.calculateReportData(detailList);
		double mainTime = CalculateReport.calculateMainTime(sortedTmDetails);

		fillFirstPage(sheet, mainTime, tmHeader);
		fillSecondPage(sheet, sortedTmDetails);

		evaluator.clearAllCachedResultValues();
		evaluator.evaluateAll();

		SaveExcel.SaveExcelFile(workbook, Base.reportSaveAddress, tmHeader.getName());
	}

	private void fillSecondPage(XSSFSheet sheet, HashMap<Integer, PhaseDetails> sortedTmDetails) {
		XSSFCell cellToUpdate;
		PhaseDetails phaseDetails = new PhaseDetails();
		int row = 76;

		for (Map.Entry<Integer, PhaseDetails> set : sortedTmDetails.entrySet()) {

			phaseDetails = set.getValue();
			ZA za = zaList.stream().filter(zaItem -> set.getKey() == zaItem.getCode()).findAny().orElse(null);

			if (za == null) {
				continue;
			}

			int calculatedEz = calculateEz(phaseDetails.getEz());
			double calculatedSz = calculateSz(phaseDetails, calculatedEz);
			double calculatedSzBzm = calculatedSz / phaseDetails.getBzm();
			double calculatedTeTr;

			if (za.getType().substring(0, 1).equals("t")) {
				calculatedTeTr = calculatedSzBzm + calculatedSzBzm * 0.1;
			} else {
				calculatedTeTr = 0.0;
			}

			// set AA Nr
			cellToUpdate = sheet.getRow(row).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(set.getKey());

			// set ZA Description
			cellToUpdate = sheet.getRow(row).getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(za.getDesc_bg());

			// set ZA Code
			cellToUpdate = sheet.getRow(row).getCell(19, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(za.getType());

			// set n
			cellToUpdate = sheet.getRow(row).getCell(21, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(phaseDetails.getEz().size());

			// set LG
			cellToUpdate = sheet.getRow(row).getCell(23, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(phaseDetails.getLg());

			// set EZ[HM]
			cellToUpdate = sheet.getRow(row).getCell(25, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(calculatedEz);

			// set SZ[HM]
			cellToUpdate = sheet.getRow(row).getCell(28, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(calculatedSz);

			// set BZM
			cellToUpdate = sheet.getRow(row).getCell(31, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(phaseDetails.getBzm());

			// set SZ/BZM[HM]
			cellToUpdate = sheet.getRow(row).getCell(33, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(calculatedSzBzm);

			// set te,tr[HM]
			cellToUpdate = sheet.getRow(row).getCell(35, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToUpdate.setCellValue(calculatedTeTr);

			// ainTime += CalculateSzBzm(set.getValue());

			row += 3;
		}
	}

	private static void fillFirstPage(XSSFSheet sheet, double mainTime, TimeMeasurementHeader tmHeader) {
		XSSFCell cellToUpdate;
		// set main time
		cellToUpdate = sheet.getRow(14).getCell(29, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(mainTime);

		// set name
		cellToUpdate = sheet.getRow(3).getCell(9, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getName());

		// set date
		cellToUpdate = sheet.getRow(6).getCell(7, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(BaseMethods.extractDate(tmHeader.getCreateDate()));

		// set begin time
		cellToUpdate = sheet.getRow(6).getCell(16, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getStartTime());

		// set end time
		cellToUpdate = sheet.getRow(6).getCell(25, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getEndTime());

		// set duration
		cellToUpdate = sheet.getRow(6).getCell(34, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(tmHeader.getDuration());
	}

	private static double calculateSz(PhaseDetails phaseDetails, int calculatedEz) {

		double szBzm = 0.0;

		szBzm = (double) calculatedEz * phaseDetails.getLg() / 100;

		// round to second decimal
		szBzm = szBzm * 100;
		szBzm = (double) Math.round(szBzm);
		szBzm = szBzm / 100;

		return szBzm;
	}

	private static int calculateEz(List<Integer> ez) {
		int sumEz = 0;

		for (Integer time : ez) {
			sumEz += time;
		}

		return sumEz;
	}

}