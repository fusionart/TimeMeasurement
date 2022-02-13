package Controller.Services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import Controller.Base;
import Model.TimeMeasurementDetail;
import Model.TimeMeasurementHeader;

public class CreateTables {
	public static void CreateDbTables() throws Exception {

		// create a connection source to our database
		ConnectionSource connectionSource = new JdbcConnectionSource(Base.DATABASE_URL);

		TableUtils.createTableIfNotExists(connectionSource, TimeMeasurementHeader.class);

		TableUtils.createTableIfNotExists(connectionSource, TimeMeasurementDetail.class);

		// close the connection source
		connectionSource.close();
	}

	public static void createNewDatabase() {

		try (Connection conn = DriverManager.getConnection(Base.DATABASE_URL)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
