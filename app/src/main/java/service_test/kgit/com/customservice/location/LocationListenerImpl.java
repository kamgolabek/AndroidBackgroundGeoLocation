package service_test.kgit.com.customservice.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Date;

import service_test.kgit.com.customservice.location.db.LocationEntity;
import service_test.kgit.com.customservice.location.db.SqlLiteLocationRepository;

public class LocationListenerImpl implements LocationListener {

    private final int LOCATION_INTERVAL = 500;
    private final int LOCATION_DISTANCE = 2;
    LocationManager locationManager;
    Context ctx;
    LocationRepositoryI locRepo;

    public LocationListenerImpl(Context ctx, LocationRepositoryI locRepo) {
        this.ctx = ctx;
        this.locRepo = locRepo;

        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

    }

    public void startListening() {
        if (ctx.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(ctx, "Permission not granted!", Toast.LENGTH_SHORT).show();

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, this);
    }

    public void stopListening(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("new location !");

        LocationEntity le = new LocationEntity("default", location.getLatitude(),
                location.getLongitude(), (new Date()).getTime());
       locRepo.insertLocation(le);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
