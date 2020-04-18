package service_test.kgit.com.customservice.location;

import java.util.List;

import service_test.kgit.com.customservice.location.db.LocationEntity;

public interface LocationRepositoryI {

    public void insertLocation(final LocationEntity location);

    public List<LocationEntity> getAllLocations();

    public List<LocationEntity> getAllLocationWhereTimeBiggerThan(long time);
}
