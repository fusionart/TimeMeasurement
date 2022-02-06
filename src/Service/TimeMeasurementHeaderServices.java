package Service;

import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import Controller.Base;
import Model.TimeMeasurementHeader;

public class TimeMeasurementHeaderServices{

	private static Dao<TimeMeasurementHeader, Integer> tmDao;
	static ConnectionSource connectionSource = null;

	public static List<TimeMeasurementHeader> GetRecords() throws Exception {
		List<TimeMeasurementHeader> tmHeaderList;
		
		try {
			connectionSource = new JdbcConnectionSource(Base.DATABASE_URL);
			setupDatabase(connectionSource);
			
			tmHeaderList = tmDao.queryForAll();

			//tmDao.create(new TimeMeasurementHeader("test name the fuck 2222223333fdvfd32"));
			//System.out.println(tmDao.queryForId(6).getName());
		} finally {
			// destroy the data source which should close underlying connections
			if (connectionSource != null) {
				connectionSource.close();
			}
		}
		return tmHeaderList;
	}

	/**
	 * Setup our database and DAOs
	 */
	private static void setupDatabase(ConnectionSource connectionSource) throws Exception {

		tmDao = DaoManager.createDao(connectionSource, TimeMeasurementHeader.class);
	}
}
