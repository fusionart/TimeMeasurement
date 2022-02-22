package TableParameters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.TimeMeasurementDetail;
import Model.ZA;

public class DetailTableItemModel extends AbstractTableModel {

	private List<TimeMeasurementDetail> tmDetails;
	private List<ZA> zaList;
	int fz;

	private static String header[] = { "Код", "Описание", "Тип", "FZ", "EZ", "LG", "TG"};

	public DetailTableItemModel(List<TimeMeasurementDetail> tmDetails, List<ZA> zaList) {

		this.tmDetails = new ArrayList<TimeMeasurementDetail>(tmDetails);
		this.zaList = new ArrayList<ZA>(zaList);
		this.fz = 0;
	}

	@Override
	public int getRowCount() {
		return tmDetails.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	public String getColumnName(int col) {
		return header[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object value = "??";
		TimeMeasurementDetail tmDetail = tmDetails.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = tmDetail.getZaCode();
			break;
		case 1:
			value = FindZa(tmDetail.getZaCode()).getDesc_bg();
			break;
		case 2:
			value = FindZa(tmDetail.getZaCode()).getType();
			break;
		case 3:
			value = tmDetail.getFz();
			break;
		case 4:
			value = CalculateEz(tmDetail.getFz());
			break;
		case 5:
			value = tmDetail.getLg();
			break;
		case 6:
			value = tmDetail.getTg();
			break;
		}

		return value;
	}
	
	private ZA FindZa(int zaCode) {
		ZA za = zaList.stream().filter(zaItem -> zaCode == zaItem.getCode()).findAny().orElse(null);
		return za;
	}

	private int CalculateEz(int detailFz) {
		int ez = detailFz - fz;

		if (ez < 0) {
			ez = detailFz;
		}

		fz = detailFz;

		return ez;
	}

	public void addRow(TimeMeasurementDetail tmDetail) {
		tmDetails.add(tmDetail);
		tmDetails.sort(Comparator.comparing(TimeMeasurementDetail::getFz));
		fireTableRowsInserted(tmDetails.size() - 1, tmDetails.size() - 1);
	}

	/**
	 * This will return the user at the specified row...
	 * 
	 * @param row
	 * @return
	 */
	public TimeMeasurementDetail getDetailAt(int row) {
		return tmDetails.get(row);
	}
}
