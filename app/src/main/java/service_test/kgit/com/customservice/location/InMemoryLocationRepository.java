package service_test.kgit.com.customservice.location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import service_test.kgit.com.customservice.location.db.LocationEntity;

public class InMemoryLocationRepository implements LocationRepositoryI {

    private List<LocationEntity> locations = new ArrayList<>();

    @Override
    public void insertLocation(LocationEntity location) {
        locations.add(location);
    }

    @Override
    public List<LocationEntity> getAllLocations() {
        return locations;
    }

    @Override
    public List<LocationEntity> getAllLocationWhereTimeBiggerThan(long time) {
        return locations.stream().filter(l -> l.time > time).collect(Collectors.toList());
    }
}
