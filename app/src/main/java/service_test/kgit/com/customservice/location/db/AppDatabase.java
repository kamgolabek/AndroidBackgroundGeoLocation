package service_test.kgit.com.customservice.location.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LocationEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();

}
