package TableParameters;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Controller.BaseMethods;
import Model.TimeMeasurementHeader;

public class HeaderTableItemModel extends AbstractTableModel {

    private List<TimeMeasurementHeader> tmHeaders;
    
    private static String header[] = { "#", "Описание", "Начало, час","Край, час", "Дата на създаване" };

    public HeaderTableItemModel(List<TimeMeasurementHeader> tmHeaders) {

        this.tmHeaders = new ArrayList<TimeMeasurementHeader>(tmHeaders);
    }

    @Override
    public int getRowCount() {
        return tmHeaders.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return header[col];
      }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = "??";
        TimeMeasurementHeader tmHeader = tmHeaders.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = tmHeader.getId();
                break;
            case 1:
                value = tmHeader.getName();
                break;
            case 2:
                value = tmHeader.getStartTime();
                break;
            case 3:
                value = tmHeader.getEndTime();
                break;
            case 4:
                value = BaseMethods.extractDate(tmHeader.getCreateDate());
                break;
        }

        return value;
    }

    public void deleteRow(TimeMeasurementHeader tmHeader) {
    	tmHeaders.remove(tmHeader);
    	//fireTableRowsInserted(tmDetails.size() - 1, tmDetails.size() - 1);
    	fireTableDataChanged();
    }
    
    /**
     * This will return the user at the specified row...
     * @param row
     * @return 
     */
    public TimeMeasurementHeader getHeaderAt(int row) {
        return tmHeaders.get(row);
    }
}