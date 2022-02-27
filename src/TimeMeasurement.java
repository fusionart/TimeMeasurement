import Controller.Base;
import Controller.Services.CreateTables;
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
		Base.loadSettings();
		new Header();

	}
}
