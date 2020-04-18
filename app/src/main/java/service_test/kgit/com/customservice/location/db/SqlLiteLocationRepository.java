package service_test.kgit.com.customservice.location.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import service_test.kgit.com.customservice.location.LocationRepositoryI;

public class SqlLiteLocationRepository implements LocationRepositoryI {

    private String DB_NAME = "running_pace_db_v2";
    private AppDatabase db;

    public SqlLiteLocationRepository(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }


    public void insertLocation(final LocationEntity location) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.locationDao().inserLocation(location);
                return null;
            }
        }.execute();
    }

    @Override
    public List<LocationEntity> getAllLocations() {
        return null;
    }

    @Override
    public List<LocationEntity> getAllLocationWhereTimeBiggerThan(long time) {
        return null;
    }

    public LiveData<List<LocationEntity>> getLocations() {
        return db.locationDao().getAll();
    }

}
