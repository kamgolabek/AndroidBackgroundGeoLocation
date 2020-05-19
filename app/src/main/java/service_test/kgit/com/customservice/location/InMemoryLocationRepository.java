package service_test.kgit.com.customservice.location;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryLocationRepository implements LocationRepositoryI {

    private List<Location> locations = new ArrayList<>();

    @Override
    public void insertLocation(Location location) {
        locations.add(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return locations;
    }

    @Override
    public List<Location> getAllLocationWhereTimeBiggerThan(long time) {
        return locations.stream().filter(l -> l.getTime() > time).collect(Collectors.toList());
    }

    @Override
    public void clearAll(){
        locations.clear();
    }


}
