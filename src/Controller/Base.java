package Controller;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.prefs.Preferences;

public class Base {
	public final static String DOT = ".";
	public final static String BACKSLASH = "\\";
	public final static String DELIMITER = ";";
	public final static String FORWARDSLASH = "/";
	private static String slash;

	// Paths
	private final static String MAIN_PATH_LINUX = "/home/nick/eclipse-workspace/TimeMeasurement/sys/settings.ini";
	private final static String MAIN_PATH_WIN = "C:\\TimeMeasurement\\sys\\settings.ini";

	// database
	public static String DATABASE_URL = "jdbc:sqlite:db/timemeasurement.db";

	// Labels
	public final static String FRAME_CAPTION = "Time Measurement";

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

	// Locale
	public final static Locale LOCALE = new Locale("bg");

	public final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public final static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	public final static DateTimeFormatter fileNameTimeFormat = DateTimeFormatter.ofPattern("HH-mm-ss");

	// default fonts
	public final static Font DEFAULT_FONT = new Font("Century Gothic", Font.BOLD, 18);
	public final static Font RADIO_BUTTON_FONT = new Font("Century Gothic", Font.BOLD, 14);

	// default colors
	public final static Color TEXT_FIELD_COLOR = new Color(51, 51, 204);
	public final static Color BUTTON_COLOR = new Color(0, 153, 255);

	public static Preferences settings;
	public static String backgroundPic;
	public static String icon;
	public static String zaFile;
	public static String reportTemplate;

	public static void LoadSettings() {
		GetCorrectSlash();
		LoadPaths();
		AssignVariables();
	}

	private static void LoadPaths() {
		String os = GetOperatingSystem();
		try {
			if (os.equals("Linux")) {
				settings = ReadIni.ParseIni(MAIN_PATH_LINUX);
			} else {
				settings = ReadIni.ParseIni(MAIN_PATH_WIN);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GetCorrectSlash() {
		String os = GetOperatingSystem();
		if (os.equals("Linux")) {
			slash = FORWARDSLASH;
		} else {
			slash = BACKSLASH;
		}
	}

	private static String GetOperatingSystem() {
		String os = System.getProperty("os.name");
		// System.out.println("Using System Property: " + os);
		return os;
	}

	private static void AssignVariables() {
		StringBuilder sb = new StringBuilder();

		// background
		sb.append(settings.node("system").get("mainaddress", null));
		sb.append(slash);
		sb.append(settings.node("background").get("address", null));
		sb.append(slash);
		sb.append(settings.node("background").get("name", null));
		sb.append(DOT);
		sb.append(settings.node("background").get("extension", null));

		backgroundPic = sb.toString();

		// icon
		sb = new StringBuilder();
		sb.append(settings.node("system").get("mainaddress", null));
		sb.append(slash);
		sb.append(settings.node("icon").get("address", null));
		sb.append(slash);
		sb.append(settings.node("icon").get("name", null));
		sb.append(DOT);
		sb.append(settings.node("icon").get("extension", null));

		icon = sb.toString();

		// ZA file
		sb = new StringBuilder();
		sb.append(settings.node("system").get("mainaddress", null));
		sb.append(slash);
		sb.append(settings.node("zafile").get("address", null));
		sb.append(slash);
		sb.append(settings.node("zafile").get("name", null));
		sb.append(DOT);
		sb.append(settings.node("zafile").get("extension", null));

		zaFile = sb.toString();

		// report template
		sb = new StringBuilder();
		sb.append(settings.node("system").get("mainaddress", null));
		sb.append(slash);
		sb.append(settings.node("report_template").get("address", null));
		sb.append(slash);
		sb.append(settings.node("report_template").get("name", null));
		sb.append(DOT);
		sb.append(settings.node("report_template").get("extension", null));

		reportTemplate = sb.toString();
	}
}
