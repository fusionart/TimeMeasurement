package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Model.ZA;
import View.LoadingScreen;

public class ExcelFile {
	private static Workbook workbook;
	private static List<ZA> allRows;
	
	public static List<ZA> LoadZA() {
		ReadExcelFile();
		ReadAllRows();
		//System.out.println(allRows.size());
		return allRows;
	}

	private static void ReadExcelFile() {
		// File file = new File(Base.mainDbFile);
		File file = new File(Base.zaFile);
		FileInputStream streamFile = null;
		try {
			streamFile = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook = new XSSFWorkbook(streamFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			streamFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ReadAllRows() {

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
					for (int cn = 0; cn <= Base.LAST_COLUMN; cn++) {
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

					// PalletModel pm = new PalletModel(temp);
					//ZA za = new ZA(temp);
					allRows.add(new ZA(temp));
					i++;

					percentage = i * 100 / rowCount;
					publish((int) percentage);
					//ls.UpdateProgress(percentage);
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

	private static Sheet GetSheet() {
		Sheet sheet = workbook.getSheetAt(0);

		return sheet;
	}
}
