package Controller.Excel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Controller.Base;
import Controller.BaseConstants;
import Model.ZA;
import View.LoadingScreen;

public class LoadZaFile {
	private Workbook workbook;
	private List<ZA> allRows;
	
	private LoadZaFile() {
		this.workbook = ReadExcelFile.LoadExcelFile(Base.zaFile);
		ReadAllRows();
	}
	
    private static class SingletonHelper{
        private static final LoadZaFile INSTANCE = new LoadZaFile();
    }
    
    public static LoadZaFile getInstance(){
        return SingletonHelper.INSTANCE;
    }
	
	public List<ZA> getAllRows() {
		return allRows;
	}
    
	private void ReadAllRows() {

		allRows = new ArrayList<ZA>();

		final LoadingScreen ls = new LoadingScreen();
		final Sheet sheet = GetSheet();

		new SwingWorker<Void, Integer>() {
			int i = 0;
			double percentage = 0;

			@Override
			public Void doInBackground() {

				int rowCount = sheet.getPhysicalNumberOfRows();

				for (Row row : sheet) {
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(String.valueOf(i));

					// Reading cells in this way because For loop and Iterator can handle with blank
					// cells
					for (int cn = 0; cn <= BaseConstants.LAST_COLUMN; cn++) {
						Cell cell = row.getCell(cn, MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if (cell == null) {
							temp.add(" ");
						} else {
							switch (cell.getCellType()) {
							case STRING:
								temp.add(cell.getStringCellValue());
								break;
							case NUMERIC:
								temp.add(String.valueOf(cell.getNumericCellValue()));
								break;
							case BOOLEAN:
								temp.add(String.valueOf(cell.getBooleanCellValue()));
								break;
							case BLANK:
								temp.add(" ");
								break;
							case _NONE:
								temp.add(" ");
								break;
							// case FORMULA: ... break;
							default:
								temp.add(" ");
							}
						}
					}

					allRows.add(new ZA(temp));
					i++;

					percentage = i * 100 / rowCount;
					publish((int) percentage);
					// ls.UpdateProgress(percentage);
				}
				return null;
			}

			@Override
			public void done() {
				ls.setVisible(false);
				ls.dispose();
			}

			@Override
			protected void process(List<Integer> ints) {
				ls.setProgress(ints.get(0));
			}
		}.execute();
	}

	private Sheet GetSheet() {
		Sheet sheet = workbook.getSheetAt(0);

		return sheet;
	}
}
