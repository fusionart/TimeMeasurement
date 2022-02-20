package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.TimeMeasurementDetail;

public class CalculateReport {

	private static HashMap<Integer, PhaseDetails> sortedTmDetails = new HashMap<Integer, PhaseDetails>();

	public static Double CalculateReportData(List<TimeMeasurementDetail> tmDetailList) {

		for (int i = 0; i < tmDetailList.size() - 1; i++) {
			if (sortedTmDetails.containsKey(tmDetailList.get(i).getZaCode())) {
				PhaseDetails phaseDetails = sortedTmDetails.get(tmDetailList.get(i).getZaCode());

				phaseDetails.ez.add(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());

				sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);

			} else {
				PhaseDetails phaseDetails = new PhaseDetails();
				phaseDetails.lg = tmDetailList.get(i).getLg();

				if (i == 0) {
					phaseDetails.ez.add(tmDetailList.get(i).getFz());
				} else {
					phaseDetails.ez.add(tmDetailList.get(i).getFz() - tmDetailList.get(i - 1).getFz());
				}
				sortedTmDetails.put(tmDetailList.get(i).getZaCode(), phaseDetails);
			}
		}

		return CalculateMainTime();
	}

	private static Double CalculateMainTime() {
		Double mainTime = 0.0;
		for (Map.Entry<Integer, PhaseDetails> set : sortedTmDetails.entrySet()) {

			mainTime += CalculateSzBzm(set.getValue());

			// System.out.println(set.getKey() + " = " + set.getValue());
		}

		System.out.println(mainTime);
		return mainTime;
	}

	private static Double CalculateSzBzm(PhaseDetails phaseDetails) {

		List<Integer> ez = phaseDetails.ez;
		int count = 0;
		int sumEz = 0;
		Double szBzm = 0.0;

		for (Integer integer : ez) {
			sumEz += integer;
			count++;
		}

		szBzm = (double) sumEz * phaseDetails.lg / 100 / count;

		// round to third decimal
		szBzm = szBzm * 1000;
		szBzm = (double) Math.round(szBzm);
		szBzm = szBzm / 1000;
		
		return szBzm;
	}
}

class PhaseDetails {
	int lg;
	List<Integer> ez = new ArrayList<>();
}
