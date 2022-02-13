package Model;

import java.time.LocalDateTime;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tmheader")
public class TimeMeasurementHeader {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String name;
    
    @DatabaseField(canBeNull = false)
    private String createDate;
    
    @DatabaseField(canBeNull = false)
    private String startTime;
    
    @DatabaseField(canBeNull = false)
    private String endTime;
    
    @ForeignCollectionField(eager = false)
    private ForeignCollection<TimeMeasurementDetail> tmDetails;
    
    public TimeMeasurementHeader() {
    	
    }

	public TimeMeasurementHeader(String name, String startTime, String endTime) {
		super();
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createDate = LocalDateTime.now().toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ForeignCollection<TimeMeasurementDetail> getTmDetails() {
		return tmDetails;
	}

	public void setTmDetails(ForeignCollection<TimeMeasurementDetail> tmDetails) {
		this.tmDetails = tmDetails;
	}
}