import Controller.Base;
import Service.CreateTables;
import View.Header;

public class TimeMeasurement {

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		CreateTables.createNewDatabase();
		CreateTables.CreateDbTables();
		Base.LoadSettings();
		new Header();

	}
}
