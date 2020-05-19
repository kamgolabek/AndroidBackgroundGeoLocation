package service_test.kgit.com.customservice.location;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Map;

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

    public static boolean isServiceRunning = false;
    public static boolean isSavingToRepoEnabled = true;
    public static Map<String, Object> metadata;


    LocationListenerImpl locationListener;
    private final LocationServiceBinder binder = new LocationServiceBinder();


    private static final int FOREGROUND_ID = 19930122;
    private InMemoryLocationRepository locationRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        BackgroundLocationService.isServiceRunning = true;
        startForeground(FOREGROUND_ID, getNotification());
        locationRepository = new InMemoryLocationRepository();
        System.out.println("Creating bg service..");
        locationListener = new LocationListenerImpl(this, location -> {
            if(BackgroundLocationService.isSavingToRepoEnabled){
                locationRepository.insertLocation(location);
            }else{
                System.out.println("not saving to repo..");
            }
        });
    }

    public void startLocationTracking(int intervalMillis, int minDistanceMeters){
        locationListener.startListening(intervalMillis,minDistanceMeters);
    }

    public void stopLocationTracking(){
        locationListener.stopListening();
    }

    public void setSaveToRepo(boolean saveToRepo) {
        BackgroundLocationService.isSavingToRepoEnabled = saveToRepo;
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


