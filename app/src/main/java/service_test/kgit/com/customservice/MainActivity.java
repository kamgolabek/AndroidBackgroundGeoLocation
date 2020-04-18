package service_test.kgit.com.customservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import service_test.kgit.com.customservice.location.LocationService;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 200;

    Button btnStart;
    Button btnPause;
    Button btnStop;
    TextView tvStatus;

    // FLUTTER IMPORTANT
    LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWidgets(); // DEMO
    }


    // FLUTTER IMPORTANT

    // get all Stored locations...
    public void getAllStoredLocations(){
        locationService.getLocationRepo().getAllLocations();
    }

    // get stored locations where time bigger than x
    public void getAllStoredLocationsWithTimeBiggerThan(long time){
        locationService.getLocationRepo().getAllLocationWhereTimeBiggerThan(time);
    }

    //

    // FLUTTER IMPORTANT
    private void startServ(){
        if (this.checkSelfPermission( ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            final Intent intent = new Intent(this.getApplication(), LocationService.class);
            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            this.requestPermissions( new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }


    // FLUTTER IMPORTANT
    private void pauseServ(){
        this.locationService.pauseLocationTracking();
    }

    // FLUTTER IMPORTANT
    private void startLocationTracking(){
        this.locationService.startLocationTracking();
    }


    // FLUTTER IMPORTANT
    void stopServ(){
        try {
            unbindService(serviceConnection);
        }catch (IllegalArgumentException ex){
            System.out.println("service already unbinded");
        }

        stopService(new Intent(this.getApplication(), LocationService.class));
    }


    // FLUTTER IMPORTANT
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startServ();
            }
        }
    }



    // FLUTTER IMPORTANT
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            locationService = ((LocationService.LocationServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            locationService = null;
        }
    };


    // DEMO
    private void setWidgets() {
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        tvStatus = findViewById(R.id.tvStatus);

        btnStart.setOnClickListener(v -> startServ());
        btnPause.setOnClickListener(v -> pauseServ());
        btnStop.setOnClickListener(v -> stopServ());
    }
}
