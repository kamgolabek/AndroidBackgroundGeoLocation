package service_test.kgit.com.customservice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import service_test.kgit.com.customservice.location.BackgroundLocationService;
import service_test.kgit.com.customservice.location.MeasureManager;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 200;
    private MeasureManager measureService;

    Button btnStart;
    Button btnPause;
    Button btnStop;
    TextView tvStatus;
    Button btnGetState;

    // FLUTTER IMPORTANT
    BackgroundLocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWidgets(); // DEMO
        measureService = new MeasureManager(this, 500, 5);
        System.out.println("============ SERVICE RUNNING: " + measureService.getMeasureState());
    }


//    // FLUTTER IMPORTANT
//
//    // get all Stored locations...
//    public void getAllStoredLocations(){
//        locationService.getLocationRepo().getAllLocations();
//    }
//
//    // get stored locations where time bigger than x
//    public void getAllStoredLocationsWithTimeBiggerThan(long time){
//        locationService.getLocationRepo().getAllLocationWhereTimeBiggerThan(time);
//    }
//
//    //
//
//    // FLUTTER IMPORTANT
//    private void startServ(){
//        if (this.checkSelfPermission( ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            final Intent intent = new Intent(this.getApplication(), BackgroundLocationService.class);
//            startService(intent);
//            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//        } else {
//            this.requestPermissions( new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
//        }
//    }
//
//
//    // FLUTTER IMPORTANT
//    private void pauseServ(){
//        this.locationService.pauseLocationTracking();
//    }
//
//    // FLUTTER IMPORTANT
//    private void startLocationTracking(){
//        this.locationService.startLocationTracking();
//    }
//
//
//    // FLUTTER IMPORTANT
//    void stopServ(){
//        try {
//            unbindService(serviceConnection);
//        }catch (IllegalArgumentException ex){
//            System.out.println("service already unbinded");
//        }
//
//        stopService(new Intent(this.getApplication(), BackgroundLocationService.class));
//    }


    // FLUTTER IMPORTANT
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("on ");

//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startServ();
//            }
//        }
        try {
            measureService.onRequestPermissionResult(requestCode,permissions,grantResults);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception: " + e);
        }
    }



//    // FLUTTER IMPORTANT
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            locationService = ((BackgroundLocationService.LocationServiceBinder) service).getService();
//        }
//
//        public void onServiceDisconnected(ComponentName className) {
//            locationService = null;
//        }
//    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            System.out.println("unbinding service..");
            measureService.onActivityDestroy();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // DEMO
    private void setWidgets() {
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        tvStatus = findViewById(R.id.tvStatus);
        btnGetState = findViewById(R.id.btnGetState);

        btnStart.setOnClickListener(v -> {
            try {
                if(measureService.getMeasureState() == MeasureManager.MeasureState.PAUSED){
                    measureService.continueMeasure();
                }else{
                    measureService.startMeasure();
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("exception: " + e);
            }
        });
        btnPause.setOnClickListener(v -> {
//            if(measureService != null){
//                System.out.println("============ SERVICE RUNNING: " + measureService.isServiceRunning());
//                System.out.println("============ SERVICE BINDED: " + measureService.isServiceBinded());
//            }
            measureService.pauseMeasure();
        });
        btnStop.setOnClickListener(v -> {
            try {
                measureService.stopMeasure();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("exception: " + e);
            }
        });

        btnGetState.setOnClickListener(v -> {
            MeasureManager.MeasureState measureState = measureService.getMeasureState();
            tvStatus.setText(measureState.toString());
        });
    }
}
