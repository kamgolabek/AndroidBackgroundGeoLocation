package service_test.kgit.com.customservice.location.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    LiveData<List<LocationEntity>> getAll();

    @Query("SELECT * FROM location WHERE measure_id = :measureId and time > :timeEdge")
    LiveData<List<LocationEntity>> findByMeasureAndTime(String measureId, long timeEdge);

    @Insert
    void inserLocation(LocationEntity location);

}
