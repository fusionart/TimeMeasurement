package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

public class ReadIni {
	public static Preferences ParseIni(String filename) throws IOException {
		Ini ini;
		Preferences prefs = null;
		try {
			ini = new Ini(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			prefs = new IniPreferences(ini);
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prefs;
	}
}
