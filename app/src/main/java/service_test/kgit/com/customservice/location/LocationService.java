package service_test.kgit.com.customservice.location;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import service_test.kgit.com.customservice.R;

public class LocationService extends Service {

    LocationListenerImpl locationListener;
    private final LocationServiceBinder binder = new LocationServiceBinder();

    private static final int FOREGROUND_ID = 19930122;
    LocationRepositoryI locRepo = new InMemoryLocationRepository();

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(FOREGROUND_ID, getNotification());
        locRepo = new InMemoryLocationRepository();
        locationListener = new LocationListenerImpl(this, locRepo);
        startLocationTracking();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        locationListener.stopListening();
    }

    public void pauseLocationTracking(){
        locationListener.stopListening();
    }

    public void startLocationTracking(){
        locationListener.startListening();
    }

    public LocationRepositoryI getLocationRepo(){
        return locRepo;
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
        public LocationService getService() {
            return LocationService.this;
        }
    }
}


