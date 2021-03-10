package epic.ud.elogr.view.SetData;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
//import android.icu.util.Calendar;
import java.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import epic.ud.elogr.R;
import epic.ud.elogr.config.AppConsts;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.ShowTripGearModel;
import epic.ud.elogr.util.LocationTrack;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.GearData.GearListSelectedActivity;
import epic.ud.elogr.view.TripMenuActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class SetDataActivity extends AppCompatActivity {

    private Double LONGITUDE = 0.0;
    private Double LATITUDE = 0.0;
    TextView gearSetDataTitle, gearSetNumberLable, gearStartDateLableLable, gearSetStartGpsLable, gearSetEndDateLable, gearSetEndGpsLable;
    Button gearSetDataSaveButton, gearSetStartGpsButton, gearSetEndGpsButton;
    EditText gearSetStartGpsLatInput, gearSetStartGpsLonInput, gearSetEndGpsLatInput, gearSetEndGpsLonInput, gearStartDateInput, gearSetEndDateInput, gearSetNumberInput;
    String lineId;

//    LocationTrack locationTrack;

    /**
        //for permission parameters - start
        private ArrayList<String> permissionsToRequest;
        private ArrayList<String> permissionsRejected = new ArrayList<>();
        private ArrayList<String> permissions = new ArrayList<>();
        public static final int REQUEST_WRITE_STORAGE = 112;
        private final static int ALL_PERMISSIONS_RESULT = 101;
        //for permission parameters - end
     */


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);
        try {

            Store.INSTANCE.reInitStore(SetDataActivity.this);
        Intent intent = getIntent();
        if(intent.hasExtra("lineId")){
            lineId = intent.getStringExtra("lineId");
        }else {
            Intent departure = new Intent(SetDataActivity.this, GearListSelectedActivity.class);
            startActivity(departure);
        }

/**
        // for permission request call - start
//        permissions.add(ACCESS_FINE_LOCATION);
//        permissions.add(ACCESS_COARSE_LOCATION);
//        permissions.add(WRITE_EXTERNAL_STORAGE);
//        permissionsToRequest = findUnAskedPermissions(permissions);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (permissionsToRequest.size() > 0)
//                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//        }
        // for permission request - end
*/
        initUi();
        openDraf();


        gearSetStartGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gearSetStartGpsLatInput.setText(AppConsts.LATITUDE);
                gearSetStartGpsLonInput.setText(AppConsts.LONGITUDE);
            }
        });

        gearSetEndGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gearSetEndGpsLatInput.setText(AppConsts.LATITUDE);
                gearSetEndGpsLonInput.setText(AppConsts.LONGITUDE);

            }
        });


        gearStartDateInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int inType = gearStartDateInput.getInputType(); // backup the input type
                gearStartDateInput.setInputType(InputType.TYPE_NULL); // disable soft input
                gearStartDateInput.onTouchEvent(event); // call native handler
                //startGpsTxt.setInputType(inType); // restore input type

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (gearStartDateInput.getRight() - gearStartDateInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        initDate(gearStartDateInput);
                        return true;
                    }
                }
                return false;
            }
        });

        gearSetEndDateInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int inType = gearSetEndDateInput.getInputType(); // backup the input type
                gearSetEndDateInput.setInputType(InputType.TYPE_NULL); // disable soft input
                gearSetEndDateInput.onTouchEvent(event); // call native handler
                //startGpsTxt.setInputType(inType); // restore input type

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (gearSetEndDateInput.getRight() - gearSetEndDateInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        initDate(gearSetEndDateInput);
                        return true;
                    }
                }
                return false;
            }
        });

        gearSetDataSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formValidate();

            }
        });

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SetData Activity"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        locationTrack.stopListener();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initUi() {

        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        if (word.size() < 67) {
            Toast.makeText(getApplicationContext(), "Data Loading Incorrect...", Toast.LENGTH_SHORT).show();
            finish();
            Intent departure = new Intent(SetDataActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }

        List<ShowTripGearModel> tgList = Store.INSTANCE.getAppDatabase().tripGearDao().getAll_(Store.INSTANCE.getOnTripId());
        Log.d("TAG_L", "-" + tgList.size());

        gearSetDataTitle = (TextView) findViewById(R.id.gearSetDataTitle);
        gearSetDataTitle.setTypeface(tf);
        gearSetNumberLable = (TextView) findViewById(R.id.gearSetNumberLable);
        gearSetNumberLable.setTypeface(tf);
        gearStartDateLableLable = (TextView) findViewById(R.id.gearStartDateLableLable);
        gearStartDateLableLable.setTypeface(tf);
        gearSetStartGpsLable = (TextView) findViewById(R.id.gearSetStartGpsLable);
        gearSetStartGpsLable.setTypeface(tf);
        gearSetEndDateLable = (TextView) findViewById(R.id.gearSetEndDateLable);
        gearSetEndDateLable.setTypeface(tf);
        gearSetEndGpsLable = (TextView) findViewById(R.id.gearSetEndGpsLable);
        gearSetEndGpsLable.setTypeface(tf);
        gearSetDataSaveButton = (Button) findViewById(R.id.gearSetDataSaveButton);
        gearSetDataSaveButton.setTypeface(tf);

        gearSetNumberInput = (EditText) findViewById(R.id.gearSetNumberInput);gearSetNumberInput.setText(""+lineId);

        gearSetStartGpsLatInput = (EditText) findViewById(R.id.gearSetStartGpsLatInput);
        gearSetStartGpsLonInput = (EditText) findViewById(R.id.gearSetStartGpsLonInput);
        gearSetStartGpsButton = (Button) findViewById(R.id.gearSetStartGpsButton);

        gearStartDateInput = (EditText) findViewById(R.id.gearStartDateInput);
        gearStartDateInput.setText("" + setDate(null));
        gearSetEndDateInput = (EditText) findViewById(R.id.gearSetEndDateInput);
        gearSetEndDateInput.setText("" + setDate(null));

        gearSetEndGpsLatInput = (EditText) findViewById(R.id.gearSetEndGpsLatInput);
        gearSetEndGpsLonInput = (EditText) findViewById(R.id.gearSetEndGpsLonInput);
        gearSetEndGpsButton = (Button) findViewById(R.id.gearSetEndGpsButton);


        if (lang.equals("si")) {
            gearSetDataTitle.setText(Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("7"));
            gearSetNumberLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("43"));
            gearStartDateLableLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("42"));
            gearSetEndDateLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("46"));
            gearSetStartGpsLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("44"));
            gearSetEndGpsLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getSinhalaWord("45"));
            gearSetDataSaveButton.setText(" B<.  ");
        }

        if (lang.equals("ta")) {
            gearSetDataTitle.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("7"));
            gearSetNumberLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("43"));
            gearStartDateLableLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("42"));
            gearSetEndDateLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("46"));
            gearSetStartGpsLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("44"));
            gearSetEndGpsLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("45"));
            gearSetDataSaveButton.setText(Store.INSTANCE.getAppDatabase().wordDao().getTamilWord("10"));
        }

        if (lang.equals("en")) {
            gearSetDataTitle.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("7"));
            gearSetNumberLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("43"));
            gearStartDateLableLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("42"));
            gearSetEndDateLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("46"));
            gearSetStartGpsLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("44"));
            gearSetEndGpsLable.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("45"));
            gearSetDataSaveButton.setText(Store.INSTANCE.getAppDatabase().wordDao().getEnglishWord("10"));

        }
    }

/**
    // for permission request - start
//    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
//        ArrayList<String> result = new ArrayList<String>();
//
//        for (String perm : wanted) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//
//        return result;
//    }

//    private boolean hasPermission(String permission) {
//        if (canMakeSmores()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
//            }
//        }
//        return true;
//    }

//    private boolean canMakeSmores() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }

//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        switch (requestCode) {
//
//            case ALL_PERMISSIONS_RESULT:
//                for (String perms : permissionsToRequest) {
//                    if (!hasPermission(perms)) {
//                        permissionsRejected.add(perms);
//                    }
//                }
//
//                if (permissionsRejected.size() > 0) {
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    });
//                            return;
//                        }
//                    }
//
//                }
//
//                break;
//        }
//
//    }

//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(SetDataActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

    // for permission request - end

//    public String[] setGps() {
//
//        String retVal[] = new String[2];
//
//        if (locationTrack.canGetLocation()) {
//            double longitude = locationTrack.getLongitude();
//            double latitude = locationTrack.getLatitude();
//
//            retVal[0] = "" + latitude;
//            retVal[1] = "" + longitude;
//            return retVal;
//
//        } else {
//            locationTrack.showSettingsAlert();
//            retVal[0] = "";
//            retVal[1] = "";
//            return retVal;
//        }
//
//
//    }
*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initDate(final EditText e) {
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //for today calender obj
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date dateToday = c.getTime();


                //for seleted date calender obj
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date dateSelected = myCalendar.getTime();

                if (dateSelected.before(dateToday)) {
                    Toast.makeText(getApplicationContext(), "Previous Dates Not Allowed", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    e.setText("" + setDate(myCalendar));
                    timePicker(e);
                }

            }

        };

        new DatePickerDialog(SetDataActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void timePicker(final EditText e) {
        final String etxt = e.getText().toString();
        Calendar mcurrentTime = myCalendar;
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        Date date = myCalendar.getTime();
        SimpleDateFormat timeF = new SimpleDateFormat("hh:mm:ss aa");
        final String formattedDate = "" + timeF.format(date);
        e.setText("" +etxt+ " , "+formattedDate);
        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SetDataActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker timePicker, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, hourOfDay, minute, 0);
                long timeInMillis = calendar.getTimeInMillis();
                java.text.DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss a");
                Date date = new Date();
                date.setTime(timeInMillis);
                e.setText("" +etxt+ " , "+dateFormatter.format(date));

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String setDate(Calendar calender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (calender == null) {
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MMM-dd");
            SimpleDateFormat timeF = new SimpleDateFormat("hh:mm:ss aa");
            String formattedDate = dateF.format(date) + " " + timeF.format(date);
            return formattedDate;
        } else {
            Date date = calender.getTime();
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MMM-dd");
            SimpleDateFormat timeF = new SimpleDateFormat("hh:mm:ss");
            String formattedDate = "" + dateF.format(date)/* + " " + timeF.format(date)*/;
            return formattedDate;
        }
    }


    public void openDraf() {
        List<TripGear> trip = Store.INSTANCE.getAppDatabase().tripGearDao().loadAllByIds(Integer.parseInt(lineId));
        if (trip.get(0).getNumber() != null) {
            gearSetNumberInput.setText("" + trip.get(0).getNumber());
        }
        if (trip.get(0).getStartDate() != null) {
            gearStartDateInput.setText("" + trip.get(0).getStartDate());
        }
        if (trip.get(0).getStartGpsLat() != null) {
            gearSetStartGpsLatInput.setText("" + trip.get(0).getStartGpsLat());
        }
        if (trip.get(0).getStartGpsLon() != null) {
            gearSetStartGpsLonInput.setText("" + trip.get(0).getStartGpsLon());
        }
        if (trip.get(0).getEndGpsLat() != null) {
            gearSetEndGpsLatInput.setText("" + trip.get(0).getEndGpsLat());
        }
        if (trip.get(0).getEndGpsLon() != null) {
            gearSetEndGpsLonInput.setText("" + trip.get(0).getEndGpsLon());
        }
        if (trip.get(0).getEndDate() != null) {
            gearSetEndDateInput.setText("" + trip.get(0).getEndDate());
        }
    }

    public void formValidate() {
        if (gearSetNumberInput.getText().toString().trim().length() == 0) {
            gearSetNumberInput.setError("Please fill this");
            return;
        } else if (gearStartDateInput.getText().toString().trim().length() == 0) {
            gearStartDateInput.setError("Please pick a date");
            return;
        } else if (gearSetStartGpsLatInput.getText().toString().trim().length() == 0) {
            gearSetStartGpsLatInput.setError("Please pick a Location");
            return;
        } else if (gearSetStartGpsLonInput.getText().toString().trim().length() == 0) {
            gearSetStartGpsLonInput.setError("Please pick a Location");
            return;
        } else if (gearSetEndDateInput.getText().toString().trim().length() == 0) {
            gearSetEndDateInput.setError("Please pick a date");
            return;
        } else if (gearSetEndGpsLatInput.getText().toString().trim().length() == 0) {
            gearSetEndGpsLatInput.setError("Please pick a Location");
            return;
        } else if (gearSetEndGpsLonInput.getText().toString().trim().length() == 0) {
            gearSetEndGpsLonInput.setError("Please pick a Location");
            return;
        } else {
            formSave();
        }
    }

    public void formSave() {

        Store.INSTANCE.getAppDatabase().tripGearDao().updateSetData(
                Integer.parseInt(lineId),
                "" + gearSetNumberInput.getText().toString(),
                "" + gearStartDateInput.getText().toString(),
                "" + gearSetStartGpsLatInput.getText().toString(),
                "" + gearSetStartGpsLonInput.getText().toString(),
                "" + gearSetEndGpsLatInput.getText().toString(),
                "" + gearSetEndGpsLonInput.getText().toString(),
                "" + gearSetEndDateInput.getText().toString()

        );
        Intent intent = new Intent(SetDataActivity.this, TripMenuActivity.class);
        startActivity(intent);

    }

/**
//    void getLastLocation() {
//        Log.e("PRESS", "" + "LOC");
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mSettingsClient = LocationServices.getSettingsClient(this);
//
//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                mCurrentLocation = locationResult.getLastLocation();
//                Log.e("LAT", "" + mCurrentLocation.getLatitude());
//                Log.e("LON", "" + mCurrentLocation.getLongitude());
//
//            }
//        };
//    }
*/
}
