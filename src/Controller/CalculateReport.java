package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.TimeMeasurementDetail;
import Model.PhaseDetails;

public class CalculateReport {

	public static HashMap<Integer, PhaseDetails> CalculateReportData(List<TimeMeasurementDetail> tmDetailList) {
		HashMap<Integer, PhaseDetails> sortedTmDetails = new HashMap<Integer, PhaseDetails>();
		
		for (int i = 0; i < tmDetailList.size() - 1; i++) {
			if (sortedTmDetails.containsKey(tmDetailList.get(i).getZaCode())) {
				PhaseDetails phaseDetails = sortedTmDetails.get(tmDetailList.get(i).getZaCode());

				phaseDetails.addToEz(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());

				sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);

			} else {
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
		}

		return sortedTmDetails;
	}

	public static Double CalculateMainTime(HashMap<Integer, PhaseDetails> sortedTmDetails) {
		Double mainTime = 0.0;
		for (Map.Entry<Integer, PhaseDetails> set : sortedTmDetails.entrySet()) {

			mainTime += CalculateSzBzm(set.getValue());

			// System.out.println(set.getKey() + " = " + set.getValue());
		}

		System.out.println(mainTime);
		return mainTime;
	}

	private static Double CalculateSzBzm(PhaseDetails phaseDetails) {

		List<Integer> ez = phaseDetails.getEz();
		int sumEz = 0;
		Double szBzm = 0.0;

		for (Integer integer : ez) {
			sumEz += integer;
		}

		szBzm = (double) sumEz * phaseDetails.getLg() / 100 / phaseDetails.getBzm();

		// round to third decimal
		szBzm = szBzm * 1000;
		szBzm = (double) Math.round(szBzm);
		szBzm = szBzm / 1000;
		
		return szBzm;
	}
}
