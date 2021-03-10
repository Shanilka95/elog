package epic.ud.elogr.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.util.ArrayList;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.config.AppConsts;
import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Trip;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.CatchData.CatchDataActivity;
import epic.ud.elogr.view.GearData.GearListActivity;
import epic.ud.elogr.view.GearData.GearListSelectedActivity;
import epic.ud.elogr.view.SetData.SetDataActivity;
import epic.ud.elogr.view.TripData.ArrivalActivity;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class TripMenuActivity extends AppCompatActivity {

    LinearLayout btnGear, btnSetData, btnCatchData;
    Button btnEndTrip;
    private String[] locationPermission;

    TextView gearDataTxt,
    setDataText,
    catchDataTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_menu);
        Store.INSTANCE.reInitStore(TripMenuActivity.this);
        locationPermission = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };

        checkPermissions();

        btnGear = findViewById(R.id.gearDataBtn);
        btnGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripMenuActivity.this, GearListActivity.class);
                startActivity(intent);
            }
        });

        btnSetData = findViewById(R.id.setDataBtn);
        btnSetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationUpdates();
                Intent intent = new Intent(TripMenuActivity.this, GearListSelectedActivity.class);
                startActivity(intent);
            }
        });

        btnCatchData = findViewById(R.id.catchDataBtn);
        btnCatchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripMenuActivity.this, CatchDataActivity.class);
                startActivity(intent);
            }
        });

        btnEndTrip = findViewById(R.id.btnEndTrip);
        btnEndTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(isDataEmpty() == false){
                Intent intent = new Intent(TripMenuActivity.this, ArrivalActivity.class);
                startActivity(intent);
                // }else {
                Toast.makeText(getApplicationContext(), "Please Enter Data", Toast.LENGTH_LONG);
                // }
            }
        });


        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        if(word.size()<67){
            Toast.makeText(getApplicationContext(), "Data Loading Incorrect...", Toast.LENGTH_SHORT).show();
            finish();
            Intent departure = new Intent(TripMenuActivity.this, HomeActivity.class);
            startActivity(departure);
        }else {
            gearDataTxt = findViewById(R.id.gearDataTxt);gearDataTxt.setTypeface(tf);
            setDataText= findViewById(R.id.setDataText);setDataText.setTypeface(tf);
            catchDataTxt= findViewById(R.id.catchDataTxt);catchDataTxt.setTypeface(tf);
            btnEndTrip.setTypeface(tf);


            if (lang.equals("si")) {
                gearDataTxt.setText("" + word.get(3).getSi_name());
                setDataText.setText("" + word.get(4).getSi_name());
                catchDataTxt.setText("" + word.get(5).getSi_name());
                btnEndTrip.setText(" B<.  ");
            }
            if (lang.equals("en")) {
                gearDataTxt.setText("" + word.get(3).getEn_name());
                setDataText.setText("" + word.get(4).getEn_name());
                catchDataTxt.setText("" + word.get(5).getEn_name());
                btnEndTrip.setText("" + word.get(6).getEn_name());
            }
            if (lang.equals("ta")) {
                gearDataTxt.setText("" + word.get(3).getTm_name());
                setDataText.setText("" + word.get(4).getTm_name());
                catchDataTxt.setText("" + word.get(5).getTm_name());
                btnEndTrip.setText("" + word.get(6).getTm_name());
            }

        }


    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : locationPermission) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        locationUpdatesEnabled();

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(TripMenuActivity.this, listPermissionsNeeded.toArray(
                    new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    public void locationUpdatesEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TripMenuActivity.this.startActivity
                                (new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        AppConsts.LATITUDE = "" + locationResult.getLastLocation().getLatitude();
                        AppConsts.LONGITUDE = "" + locationResult.getLastLocation().getLongitude();

//                        Log.e("LAT", "" + locationResult.getLastLocation().getLatitude());
//                        Log.e("LON", "" + locationResult.getLastLocation().getLongitude());

                    }
                },
                Looper.myLooper());
    }


//    public boolean isDataEmpty(){
//        int lineId = Store.INSTANCE.getOnTripId();
//        List<TripGear> tripGears = Store.INSTANCE.getAppDatabase().tripGearDao().getAllByTripId(lineId);
//        if(tripGears != null  && tripGears.size()>0){
//            List<Catch> catches = Store.INSTANCE.getAppDatabase().catchDao().getAllByGearId(tripGears.get(0).getId());
//            if(catches != null  && catches.size()>0){
//                List<CatchFish> catchFish = Store.INSTANCE.getAppDatabase().catchfishDao().getAllByCatchId(catches.get(0).getId());
//                if(catchFish != null  && catchFish.size()>0){
//                    return false;
//                }else {
//                    return true;
//                }
//                //return false;
//            }else {
//                return true;
//            }
//            //return false;
//        }else {
//            return true;
//        }
//
//
//    }
}
