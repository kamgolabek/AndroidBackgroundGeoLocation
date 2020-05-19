package service_test.kgit.com.customservice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import service_test.kgit.com.customservice.location.BackgroundLocationService;
import service_test.kgit.com.customservice.location.MeasureManager;
import service_test.kgit.com.customservice.location.MeasureState;

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


        Map<String, Object> metadata = new HashMap<>();
        metadata.put("startDate", new Date());
        try {
            measureService.setMeasureData(metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("============ SERVICE RUNNING: " + measureService.getMeasureState());


    }


    // FLUTTER IMPORTANT
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            measureService.onRequestPermissionResult(requestCode,permissions,grantResults);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception: " + e);
        }
    }


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
                if(!measureService.getMeasureState().isBinded()){
                    measureService.startMeasure();
                }else if(!measureService.getMeasureState().isSaveToRepoEnabled()){
                    measureService.continueMeasure();
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("exception: " + e);
            }
        });
        btnPause.setOnClickListener(v -> {

            try {
                measureService.requestPermissions();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            try {
//                measureService.pauseMeasure();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
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

            MeasureState measureState = measureService.getMeasureState();
            tvStatus.setText(measureState.toString());
            try {
                int size = measureService.getMeasureLocationsRepository().getAllLocations().size();
                Toast.makeText(this,"Locations stored count: " + size, Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                System.out.println(ex);
            }

        });
    }
}
