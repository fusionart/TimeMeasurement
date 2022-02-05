package Service;

import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import Controller.Base;
import Model.TimeMeasurementDetail;
import Model.TimeMeasurementHeader;

public class SaveData {
	private static Dao<TimeMeasurementHeader, Integer> tmHeaderDao;
	private static Dao<TimeMeasurementDetail, Integer> tmDetailDao;
	
	public static void SaveAll(TimeMeasurementHeader tmHeader, List<TimeMeasurementDetail> tmDetails) throws Exception {
		ConnectionSource connectionSource = new JdbcConnectionSource(Base.DATABASE_URL);
		
		tmHeaderDao = DaoManager.createDao(connectionSource, TimeMeasurementHeader.class);
		tmDetailDao = DaoManager.createDao(connectionSource, TimeMeasurementDetail.class);
		
		tmHeaderDao.create(tmHeader);
		
		for (TimeMeasurementDetail entry: tmDetails) {
			entry.setTmHeader(tmHeader);
			tmDetailDao.create(entry);
		}

		//accountDao = DaoManager.createDao(connectionSource, Account.class);
	}
}
