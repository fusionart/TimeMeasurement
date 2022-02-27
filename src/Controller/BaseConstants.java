package Controller;

import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BaseConstants {
	//texts
	public final static String ERROR = "Грешка";
	public final static String REPORT = "Репорт";
	
	public final static String DEFAULT_BZM = "1";
	public final static String DEFAULT_LG = "100";
	
	// Labels
	public final static String FRAME_CAPTION = "Time Measurement";

	// Locale
	public final static Locale LOCALE = new Locale("bg");
	
	// Size
	public final static int WIDTH = 1366;
	public final static int HEIGHT = 768;
	public final static int ELEMENT_HEIGHT = 30;
	public final static int ELEMENT_OFFSET = 37;
	public final static int PANEL_HEIGHT = 74;
	public final static int PANEL_WIDTH = 250;
	public final static int BUTTON_HEIGHT = 50;
	public final static int BUTTON_WIDTH = 225;
	public final static int LAST_COLUMN = 4;
	
	// default fonts
	public final static Font DEFAULT_FONT = new Font("Century Gothic", Font.BOLD, 18);
	public final static Font RADIO_BUTTON_FONT = new Font("Century Gothic", Font.BOLD, 14);
	
	// default colors
	public final static Color TEXT_FIELD_COLOR = new Color(51, 51, 204);
	public final static Color BUTTON_COLOR = new Color(0, 153, 255);
	
	//date formats
	public final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public final static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	public final static DateTimeFormatter fileNameTimeFormat = DateTimeFormatter.ofPattern("HH-mm-ss");
}
