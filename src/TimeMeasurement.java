import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import Controller.Base;
import Controller.SqliteConnect;
import Model.TimeMeasurementHeader;
import Service.CreateTables;
import Service.TimeMeasurementHeaderDaoImpl;
import View.Header;

public class TimeMeasurement {

	private JFrame frame;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		//CreateTables.createNewDatabase();
		//CreateTables.CreateDbTables();
		Base.LoadSettings();
		new Header();

	}
}
