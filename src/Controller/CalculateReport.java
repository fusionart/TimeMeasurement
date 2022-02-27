package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.TimeMeasurementDetail;
import Model.PhaseDetails;

public class CalculateReport {

	public static HashMap<Integer, PhaseDetails> calculateReportData(List<TimeMeasurementDetail> tmDetailList) {
		HashMap<Integer, PhaseDetails> sortedTmDetails = new HashMap<Integer, PhaseDetails>();

		for (int i = 0; i < tmDetailList.size(); i++) {
			if (sortedTmDetails.containsKey(tmDetailList.get(i).getZaCode())) {
				updateDetails(tmDetailList, sortedTmDetails, i);

			} else {
				createNewDetails(tmDetailList, sortedTmDetails, i);
			}
		}

		return sortedTmDetails;
	}

	private static void createNewDetails(List<TimeMeasurementDetail> tmDetailList,
			HashMap<Integer, PhaseDetails> sortedTmDetails, int i) {
		PhaseDetails phaseDetails = new PhaseDetails();
		phaseDetails.setLg(tmDetailList.get(i).getLg());
		phaseDetails.setBzm(tmDetailList.get(i).getBzm());

		if (i == 0) {
			phaseDetails.addToEz(tmDetailList.get(i).getFz());
		} else {
			phaseDetails.addToEz(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());
		}
		sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);
	}

	private static void updateDetails(List<TimeMeasurementDetail> tmDetailList,
			HashMap<Integer, PhaseDetails> sortedTmDetails, int i) {
		PhaseDetails phaseDetails = sortedTmDetails.get(tmDetailList.get(i).getZaCode());

		phaseDetails.addToEz(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());

		sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);
	}

	public static double calculateMainTime(HashMap<Integer, PhaseDetails> sortedTmDetails) {
		double mainTime = 0.0;
		for (Map.Entry<Integer, PhaseDetails> set : sortedTmDetails.entrySet()) {

			mainTime += calculateSzBzm(set.getValue());
		}

		return mainTime / 100;
	}

	private static double calculateSzBzm(PhaseDetails phaseDetails) {

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
}
