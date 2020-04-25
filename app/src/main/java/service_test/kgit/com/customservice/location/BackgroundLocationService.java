package service_test.kgit.com.customservice.location;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import androidx.core.app.NotificationCompat;
import service_test.kgit.com.customservice.R;


/**
 *
 * This is a background service instance, living also with activity died.
 * When Activity died, it should be able to reopen by clicking in notification -
 * then all stored location are able to reload from internalLocationRepository.
 *
 * Require status information - for activity that bind to already running service
 *
 */
public class BackgroundLocationService extends Service {

    LocationListenerImpl locationListener;
    private final LocationServiceBinder binder = new LocationServiceBinder();
    public static boolean isServiceRunning = false;

    private static final int FOREGROUND_ID = 19930122;
    private boolean isLocationTrackignEnabled = false;
    private InMemoryLocationRepository locationRepository;
    private boolean saveToRepo = true;

    @Override
    public void onCreate() {
        super.onCreate();
        BackgroundLocationService.isServiceRunning = true;
        startForeground(FOREGROUND_ID, getNotification());
        locationRepository = new InMemoryLocationRepository();
        locationListener = new LocationListenerImpl(this, location -> {
            if(saveToRepo){
                System.out.println("save location to repo: " + location);
                locationRepository.insertLocation(location);
            }
        });
    }

    public boolean isLocationTrackignEnabled() {
        return isLocationTrackignEnabled;
    }

    public void startLocationTracking(int intervalMillis, int minDistanceMeters){
        locationListener.startListening(intervalMillis,minDistanceMeters);
        isLocationTrackignEnabled = true;
    }

    public void stopLocationTracking(){
        locationListener.stopListening();
        isLocationTrackignEnabled = false;
    }

    public void setSaveToRepo(boolean saveToRepo) {
        this.saveToRepo = saveToRepo;
    }

    public boolean isSaveToRepo() {
        return saveToRepo;
    }

    public InMemoryLocationRepository getLocationRepository() {
        return locationRepository;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        locationListener.stopListening();
        BackgroundLocationService.isServiceRunning = false;
    }



    private Notification getNotification() {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_01")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("RunningPace")
                .setContentText("GPS tracking is active")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocationServiceBinder extends Binder {
        public BackgroundLocationService getService() {
            return BackgroundLocationService.this;
        }
    }
}


