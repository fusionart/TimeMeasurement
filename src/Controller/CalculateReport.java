package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.Excel.LoadZaFile;
import Model.TimeMeasurementDetail;
import Model.ZA;
import Model.PhaseDetails;

public class CalculateReport {

	private HashMap<Integer, PhaseDetails> sortedTmDetails = new HashMap<Integer, PhaseDetails>();
	private HashMap<Integer, PhaseDetails> detailsWithTg = new HashMap<Integer, PhaseDetails>();
	private HashMap<Integer, PhaseDetails> detailsWithoutTg = new HashMap<Integer, PhaseDetails>();
	private HashMap<Integer, PhaseDetails> thbDetails = new HashMap<Integer, PhaseDetails>();
	private HashMap<Integer, PhaseDetails> tnbDetails = new HashMap<Integer, PhaseDetails>();
	private HashMap<Integer, PhaseDetails> otherDetails = new HashMap<Integer, PhaseDetails>();
	private double mainTime;
	private LoadZaFile loadZaFile;
	private List<ZA> zaList;

	public CalculateReport(List<TimeMeasurementDetail> tmDetailList) {
		this.loadZaFile = LoadZaFile.getInstance();
		this.zaList = this.loadZaFile.getAllRows();
		this.sortedTmDetails = calculateReportData(tmDetailList);
		separateDetails();
		this.mainTime = calculateMainTime();
	}

	private void separateDetails() {
		for (Map.Entry<Integer, PhaseDetails> set : sortedTmDetails.entrySet()) {

			PhaseDetails phaseDetails = set.getValue();

			if (phaseDetails.getTg()) {
				detailsWithTg.put(set.getKey(), phaseDetails);
			} else {
				detailsWithoutTg.put(set.getKey(), phaseDetails);
			}

			if (phaseDetails.getEzType().equals("thb")) {
				thbDetails.put(set.getKey(), phaseDetails);
			} else if (phaseDetails.getEzType().equals("tnb")) {
				tnbDetails.put(set.getKey(), phaseDetails);
			} else {
				otherDetails.put(set.getKey(), phaseDetails);
			}
		}
	}

	private HashMap<Integer, PhaseDetails> calculateReportData(List<TimeMeasurementDetail> tmDetailList) {

		for (int i = 0; i < tmDetailList.size(); i++) {
			if (sortedTmDetails.containsKey(tmDetailList.get(i).getZaCode())) {
				updateDetails(tmDetailList, i);

			} else {
				createNewDetails(tmDetailList, i);
			}
		}

		return sortedTmDetails;
	}

	private void createNewDetails(List<TimeMeasurementDetail> tmDetailList, int i) {
		PhaseDetails phaseDetails = new PhaseDetails();
		phaseDetails.setLg(tmDetailList.get(i).getLg());
		phaseDetails.setBzm(tmDetailList.get(i).getBzm());
		phaseDetails.setTg(tmDetailList.get(i).getTg());

		ZA za = zaList.stream().filter(zaItem -> tmDetailList.get(i).getZaCode() == zaItem.getCode()).findAny()
				.orElse(null);

		phaseDetails.setEzType(za.getType());

		if (i == 0) {
			phaseDetails.addToEz(tmDetailList.get(i).getFz());
		} else {
			phaseDetails.addToEz(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());
		}
		sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);
	}

	private void updateDetails(List<TimeMeasurementDetail> tmDetailList, int i) {
		PhaseDetails phaseDetails = sortedTmDetails.get(tmDetailList.get(i).getZaCode());

		phaseDetails.addToEz(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());

		sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);
	}

	private double calculateMainTime() {
		double mainTime = 0.0;
		for (Map.Entry<Integer, PhaseDetails> record : sortedTmDetails.entrySet()) {
			if (record.getValue().getTg()) {
				mainTime += calculateSzBzm(record.getValue());
			}
		}

		return mainTime / 100;
	}

	private double calculateSzBzm(PhaseDetails phaseDetails) {

		List<Integer> ez = phaseDetails.getEz();
		int sumEz = 0;
		double szBzm = 0.0;

		for (Integer time : ez) {
			sumEz += time;
		}

		szBzm = (double) sumEz * phaseDetails.getLg() / 100 / phaseDetails.getBzm();

		// round to third decimal
		szBzm = szBzm * 1000;
		szBzm = (double) Math.round(szBzm);
		szBzm = szBzm / 1000;

		return szBzm;
	}

	public HashMap<Integer, PhaseDetails> getDetailsWithTg() {
		return detailsWithTg;
	}

	public void setDetailsWithTg(HashMap<Integer, PhaseDetails> detailsWithTg) {
		this.detailsWithTg = detailsWithTg;
	}

	public HashMap<Integer, PhaseDetails> getDetailsWithoutTg() {
		return detailsWithoutTg;
	}

	public void setDetailsWithoutTg(HashMap<Integer, PhaseDetails> detailsWithoutTg) {
		this.detailsWithoutTg = detailsWithoutTg;
	}

	public double getMainTime() {
		return mainTime;
	}

	public void setMainTime(double mainTime) {
		this.mainTime = mainTime;
	}

	public HashMap<Integer, PhaseDetails> getThbDetails() {
		return thbDetails;
	}

	public void setThbDetails(HashMap<Integer, PhaseDetails> thbDetails) {
		this.thbDetails = thbDetails;
	}

	public HashMap<Integer, PhaseDetails> getTnbDetails() {
		return tnbDetails;
	}

	public void setTnbDetails(HashMap<Integer, PhaseDetails> tnbDetails) {
		this.tnbDetails = tnbDetails;
	}

	public HashMap<Integer, PhaseDetails> getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(HashMap<Integer, PhaseDetails> otherDetails) {
		this.otherDetails = otherDetails;
	}
}
