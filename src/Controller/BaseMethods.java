package Controller;

import java.awt.Component;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class BaseMethods {
	public static Boolean CheckIsNumber(String s) {
		if ((s == null)) {
			return false;
		}

		try {
			int d = Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Моля, въведете число!", "Грешка!", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	public static Boolean CheckIfNegative(String s) {
		if (Integer.parseInt(s) <= 0) {
			JOptionPane.showMessageDialog(null, "Моля, въведете число по-голямо от 0!", "Грешка!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	public static String ExtractDate(String dateTime) {
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

		return FormatDate(localDateTime.toLocalDate());
	}

	public static String FormatDate(LocalDate date) {
		String formattedDate = null;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		if (date != null) {
			formattedDate = date.format(dateFormat);
		}

		return formattedDate;
	}

	public static String FormatTime(LocalTime time) {
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		String formattedTime = time.format(timeFormat);

		return formattedTime;
	}

	public static void ResizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + (comp.getPreferredSize().width / 4), width);
			}

			TableColumn col = columnModel.getColumn(column);
			TableCellRenderer headerRenderer = col.getHeaderRenderer();
			if (headerRenderer == null) {
				headerRenderer = table.getTableHeader().getDefaultRenderer();
			}
			Object headerValue = col.getHeaderValue();
			Component headerComp = headerRenderer.getTableCellRendererComponent(table, headerValue, false, false, 0,
					column);
			width = Math.max(width, headerComp.getPreferredSize().width + (headerComp.getPreferredSize().width / 5));

			if (width > 400)
				width = 400;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}
}
