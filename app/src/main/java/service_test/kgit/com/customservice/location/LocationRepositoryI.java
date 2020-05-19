package service_test.kgit.com.customservice.location;

import android.location.Location;

import java.util.List;

public interface LocationRepositoryI {

    public void insertLocation(final Location location);

    public List<Location> getAllLocations();

    public List<Location> getAllLocationWhereTimeBiggerThan(long time);

    public void clearAll();

}
