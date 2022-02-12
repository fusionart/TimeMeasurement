package TableParameters;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.TimeMeasurementDetail;
import Model.ZA;

public class DetailTableItemModel extends AbstractTableModel {

    private List<TimeMeasurementDetail> tmDetails;
    private List<ZA> zaList;
    
    private static String header[] = { "FZ", "Код", "Описание", "Тип", "TG", "LG" };

    public DetailTableItemModel(List<TimeMeasurementDetail> tmDetails, List<ZA> zaList) {

        this.tmDetails = new ArrayList<TimeMeasurementDetail>(tmDetails);
        this.zaList = new ArrayList<ZA>(zaList);
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
                value = tmDetail.getFz();
                break;
            case 1:
                value = tmDetail.getZaCode();
                break;
            case 2:
                value = zaList.get(tmDetail.getZaCode()).getType();
                break;
            case 3:
                value = zaList.get(tmDetail.getZaCode()).getDesc_bg();
                break;
            case 4:
                value = tmDetail.getTg();
                break;
            case 5:
                value = tmDetail.getLg();
                break;
        }

        return value;
    }
    
    public void addRow(TimeMeasurementDetail tmDetail) {
    	tmDetails.add(tmDetail);
        fireTableRowsInserted(tmDetails.size() -1, tmDetails.size() -1);
    }

    /**
     * This will return the user at the specified row...
     * @param row
     * @return 
     */
    public TimeMeasurementDetail getDetailAt(int row) {
        return tmDetails.get(row);
    }
}
