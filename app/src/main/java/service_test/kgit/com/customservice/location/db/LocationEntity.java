package service_test.kgit.com.customservice.location.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location")
public class LocationEntity {

    @PrimaryKey
    @ColumnInfo(name = "time")
    public long time;

    @ColumnInfo(name = "measure_id")
    public String measureId;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;



    public LocationEntity(){}

    public LocationEntity(String measureId, double lat, double lon, long time){
        this.measureId = measureId;
        this.latitude = lat;
        this.longitude = lon;
        this.time = time;
    }

}