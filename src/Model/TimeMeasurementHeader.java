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
    
    @ForeignCollectionField(eager = false)
    private ForeignCollection<TimeMeasurementDetail> tmDetails;
    
    public TimeMeasurementHeader() {
    	
    }

	public TimeMeasurementHeader(String name) {
		super();
		this.name = name;
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

	public ForeignCollection<TimeMeasurementDetail> getTmDetails() {
		return tmDetails;
	}

	public void setTmDetails(ForeignCollection<TimeMeasurementDetail> tmDetails) {
		this.tmDetails = tmDetails;
	}
}