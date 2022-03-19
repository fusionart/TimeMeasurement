package Controller.Excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
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
	private List<ZA> zaList;
	private Workbook workbook;
	private XSSFSheet sheet;
	private XSSFCell cellToUpdate;
	private CalculateReport calculateReport;
	private final int SECOND_PAGE_START_ROW = 76;
	private int secondPageRow = 0;
	DataFormat format;

	public SaveReport(TimeMeasurementHeader tmHeader, List<TimeMeasurementDetail> detailList) {
		this.tmHeader = tmHeader;
		LoadZaFile loadZaFile = LoadZaFile.getInstance();
		this.zaList = loadZaFile.getAllRows();
		this.workbook = ReadExcelFile.LoadExcelFile(Base.reportTemplate);
		this.sheet = (XSSFSheet) workbook.getSheetAt(0);
		this.calculateReport = new CalculateReport(detailList);
		this.format = workbook.createDataFormat();
	}

	public void saveReportFile() {
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		HashMap<Integer, PhaseDetails> detailsWithTg = calculateReport.getDetailsWithTg();
		HashMap<Integer, PhaseDetails> detailsWithoutTg = calculateReport.getDetailsWithoutTg();

		fillFirstPage(calculateReport.getMainTime(), tmHeader);
		fillSecondPage(detailsWithTg);

		if (detailsWithoutTg.size() > 0) {
			fillSecondPage(detailsWithoutTg);
		}

		fillThirdPage();

		evaluator.clearAllCachedResultValues();
		evaluator.evaluateAll();

		SaveExcel.SaveExcelFile(workbook, Base.reportSaveAddress, tmHeader.getName());
	}

	private void fillThirdPage() {
		int thbTotal = 0;
		int tnbTotal = 0;
		int otherTotal = 0;
		int total;
		int nextRow = 162;
		HashMap<Integer, PhaseDetails> thbDetails = calculateReport.getThbDetails();
		HashMap<Integer, PhaseDetails> tnbDetails = calculateReport.getTnbDetails();
		HashMap<Integer, PhaseDetails> otherDetails = calculateReport.getOtherDetails();

		thbTotal = calculateTTotal(thbDetails);
		tnbTotal = calculateTTotal(tnbDetails);
		otherTotal = calculateTTotal(otherDetails);

		total = thbTotal + tnbTotal + otherTotal;
		// set the total
		writeInCell(total, 231, 21);

		if (thbTotal > 0) {
			double thbPercent = (double) thbTotal / total * 100;
			thbPercent = roundNumber(thbPercent, 2);
			// set thb details
			writeInCellNumber(thbTotal, 156, 18);
			writeInCellNumber(thbPercent, 156, 21);
		}

		if (tnbTotal > 0) {
			double tnbPercent = (double) tnbTotal / total * 100;
			tnbPercent = roundNumber(tnbPercent, 2);
			// set thb details
			writeInCellNumber(tnbTotal, 159, 18);
			writeInCellNumber(tnbPercent, 159, 21);
		}

		if (otherDetails.size() > 0) {
			PhaseDetails phaseDetails = new PhaseDetails();
			for (Map.Entry<Integer, PhaseDetails> record : otherDetails.entrySet()) {

				phaseDetails = record.getValue();
				ZA za = zaList.stream().filter(zaItem -> record.getKey() == zaItem.getCode()).findAny().orElse(null);

				int calculatedEz = calculateEz(phaseDetails.getEz());
				double detailPercent;
				if (!za.getType().equals("P")) {
					detailPercent = (double) calculatedEz / total * 100;
					detailPercent = roundNumber(detailPercent, 2);
				} else {
					detailPercent = (double) 0.00;
				}

				writeInCell(za.getType(), nextRow, 0);
				writeInCell(za.getDesc_bg(), nextRow, 2);
				writeInCellNumber(calculatedEz, nextRow, 18);
				writeInCellNumber(detailPercent, nextRow, 21);

				nextRow += 3;
			}
		}
	}

	private int calculateTTotal(HashMap<Integer, PhaseDetails> tDetails) {
		PhaseDetails phaseDetails = new PhaseDetails();
		int tTotal = 0;
		for (Map.Entry<Integer, PhaseDetails> record : tDetails.entrySet()) {
			phaseDetails = record.getValue();
			ZA za = zaList.stream().filter(zaItem -> record.getKey() == zaItem.getCode()).findAny().orElse(null);

			// do not include time for breaks
			if (!za.getType().equals("P")) {
				tTotal += calculateEz(phaseDetails.getEz());
			}
		}

		return tTotal;
	}

	private void fillSecondPage(HashMap<Integer, PhaseDetails> tmDetails) {
		PhaseDetails phaseDetails = new PhaseDetails();
		int row;

		if (secondPageRow == 0) {
			row = SECOND_PAGE_START_ROW;
		} else {
			row = secondPageRow;
		}

		for (Map.Entry<Integer, PhaseDetails> record : tmDetails.entrySet()) {

			phaseDetails = record.getValue();
			ZA za = zaList.stream().filter(zaItem -> record.getKey() == zaItem.getCode()).findAny().orElse(null);

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
			writeInCell(record.getKey(), row, 0);

			// set ZA Description
			writeInCell(za.getDesc_bg(), row, 2);

			// set ZA Code
			writeInCell(za.getType(), row, 19);

			// set n
			writeInCell(phaseDetails.getEz().size(), row, 21);

			// set LG
			writeInCell(phaseDetails.getLg(), row, 23);

			// set EZ[HM]
			writeInCellNumber(calculatedEz, row, 25);

			// set SZ[HM]
			writeInCellNumber(calculatedSz, row, 28);

			// set BZM
			writeInCell(phaseDetails.getBzm(), row, 31);

			// set SZ/BZM[HM]
			writeInCellNumber(calculatedSzBzm, row, 33);

			// set te,tr[HM]
			writeInCellNumber(calculatedTeTr, row, 35);

			// ainTime += CalculateSzBzm(set.getValue());

			row += 3;
		}
		secondPageRow = row;
	}

	private void fillFirstPage(double mainTime, TimeMeasurementHeader tmHeader) {
		// set main time
		writeInCellNumber(mainTime, 14, 29);

		// set name
		writeInCell(tmHeader.getName(), 3, 9);

		// set date
		writeInCell(BaseMethods.extractDate(tmHeader.getCreateDate()), 6, 7);

		// set begin time
		writeInCell(tmHeader.getStartTime(), 6, 16);

		// set end time
		writeInCell(tmHeader.getEndTime(), 6, 25);

		// set duration
		writeInCell(tmHeader.getDuration(), 6, 34);
	}

	private <T> void writeInCell(T value, int row, int column) {
		cellToUpdate = this.sheet.getRow(row).getCell(column, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(value.toString());
	}

	private void writeInCellNumber(double value, int row, int column) {
		cellToUpdate = this.sheet.getRow(row).getCell(column, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cellToUpdate.setCellValue(value);
	}

	private double calculateSz(PhaseDetails phaseDetails, int calculatedEz) {
		double szBzm = 0.0;

		szBzm = (double) calculatedEz * phaseDetails.getLg() / 100;
		szBzm = roundNumber(szBzm, 2);

		return szBzm;
	}

	private double roundNumber(double value, int digits) {
		int rounding = (int) Math.pow(10, digits);

		value = value * rounding;
		value = (double) Math.round(value);
		value = value / rounding;

		return value;
	}

	private int calculateEz(List<Integer> ez) {
		int sumEz = 0;

		for (Integer time : ez) {
			sumEz += time;
		}

		return sumEz;
	}

}