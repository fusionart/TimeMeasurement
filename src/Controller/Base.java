package Controller;

import java.io.IOException;
import java.util.prefs.Preferences;

public class Base {
	private final static String DOT = ".";
	private final static String BACKSLASH = "\\";
	private final static String DELIMITER = ";";
	private final static String FORWARDSLASH = "/";
	private static String slash;

	// Paths
	private final static String MAIN_PATH_LINUX = "/home/nick/eclipse-workspace/TimeMeasurement/sys/settings.ini";
	private final static String MAIN_PATH_WIN = "C:\\TimeMeasurement\\sys\\settings.ini";

	// database
	public static String DATABASE_URL = "jdbc:sqlite:db/timemeasurement.db";

	private static Preferences settings;
	public static String backgroundPic;
	public static String icon;
	public static String zaFile;
	public static String reportTemplate;
	public static String reportSaveAddress;

	public static void loadSettings() {
		getCorrectSlash();
		loadPaths();
		assignVariables();
	}

	private static void loadPaths() {
		String os = getOperatingSystem();
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

	private static void getCorrectSlash() {
		String os = getOperatingSystem();
		if (os.equals("Linux")) {
			slash = FORWARDSLASH;
		} else {
			slash = BACKSLASH;
		}
	}

	private static String getOperatingSystem() {
		String os = System.getProperty("os.name");
		// System.out.println("Using System Property: " + os);
		return os;
	}

	private static void assignVariables() {
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

		// report save
		sb = new StringBuilder();
		sb.append(settings.node("report_save").get("address", null));
		sb.append(slash);

		reportSaveAddress = sb.toString();
	}
}
