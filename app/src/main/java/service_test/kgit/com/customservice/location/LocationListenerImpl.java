package service_test.kgit.com.customservice.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.function.Consumer;

public class LocationListenerImpl implements LocationListener {

    private LocationManager locationManager;
    private Context ctx;
    private Consumer<Location> onLocationChangedCallback;

    public LocationListenerImpl(Context ctx, Consumer<Location> onLocationChangedCallback) {
        this.ctx = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        this.onLocationChangedCallback = onLocationChangedCallback;
    }


    @SuppressLint("MissingPermission")
    public void startListening(int intervalMillis, int minDistanceMeter) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, intervalMillis, minDistanceMeter, this);
    }

    public void stopListening(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChangedCallback.accept(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // deprecated in latest versions
    }

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}
