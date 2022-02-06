package TableParameters;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.TimeMeasurementDetail;

public class DetailTableItemModel extends AbstractTableModel {

    private List<TimeMeasurementDetail> tmDetails;
    
    private static String header[] = { "FZ", "Код", "Тип", "Описание", "LG", "TG" };

    public DetailTableItemModel(List<TimeMeasurementDetail> tmDetails) {

        this.tmDetails = new ArrayList<TimeMeasurementDetail>(tmDetails);
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
                value = "";
                break;
            case 3:
                value = "";
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

    /**
     * This will return the user at the specified row...
     * @param row
     * @return 
     */
    public TimeMeasurementDetail getDetailAt(int row) {
        return tmDetails.get(row);
    }
}
