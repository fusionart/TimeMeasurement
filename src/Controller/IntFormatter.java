package Controller;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntFormatter extends DocumentFilter {
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (isInt(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			// warn the user and don't allow the insert
		}
	}

	private boolean isInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		int startChar = 0;
		String test;

		// System.out.println("doc " + doc.getText(0, doc.getLength()) + " doc length "
		// + doc.getLength());

//		if ("0".equals(doc.getText(0, 1)) && doc.getLength() > 1) {
//			System.out.println("has 0");
//			test = doc.getText(0, doc.getLength()).substring(1);
//			offset -= 1;
//			startChar = 1;
//		} else {
//			test = doc.getText(0, doc.getLength());
//			startChar = 0;
//		}
		
		//System.out.println("0: " + doc.getText(0, doc.getLength()) + " / 1: " + doc.getText(1, doc.getLength())  + " / length: " + doc.getLength());
		
		//System.out.println("offset: " + offset + " / length: " + length);

		sb.append(doc.getText(0, doc.getLength()));

		sb.replace(offset, offset + length, text);

		if (isInt(sb.toString())) {
			System.out.println(text);
			super.replace(fb, offset, length, text, attrs);
		} else {
			// warn the user and don't allow the insert
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (sb.toString().length() == 0) {
			super.replace(fb, offset, length, "0", null);
		} else {
			if (isInt(sb.toString())) {
				super.remove(fb, offset, length);
			} else {
				// warn the user and don't allow the insert
			}
		}
	}
}