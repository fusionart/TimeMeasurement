package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tmdetail")
public class TimeMeasurementDetail {
    @DatabaseField(generatedId = true)
    private long id;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private TimeMeasurementHeader tmHeader;
    
    @DatabaseField(canBeNull = false)
    private int fz;
    
    @DatabaseField(canBeNull = false)
    private int lg;
    
    @DatabaseField(canBeNull = false)
    private Boolean tg;
    
    @DatabaseField(canBeNull = false)
    private int zaCode;
    
	public TimeMeasurementDetail() {

	}

	public TimeMeasurementDetail(TimeMeasurementHeader tmHeader, int fz, int lg, Boolean tg, int zaCode) {
		super();
		this.tmHeader = tmHeader;
		this.fz = fz;
		this.lg = lg;
		this.tg = tg;
		this.zaCode = zaCode;
	}

	public TimeMeasurementHeader getTmHeader() {
		return tmHeader;
	}

	public void setTmHeader(TimeMeasurementHeader tmHeader) {
		this.tmHeader = tmHeader;
	}

	public int getFz() {
		return fz;
	}

	public void setFz(int fz) {
		this.fz = fz;
	}

	public int getLg() {
		return lg;
	}

	public void setLg(int lg) {
		this.lg = lg;
	}

	public Boolean getTg() {
		return tg;
	}

	public void setTg(Boolean tg) {
		this.tg = tg;
	}

	public int getZaCode() {
		return zaCode;
	}

	public void setZaCode(int zaCode) {
		this.zaCode = zaCode;
	}

	public long getId() {
		return id;
	}
	
}
