package epic.ud.elogr.view.TripData;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
//import android.icu.util.Calendar;
import java.io.File;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import epic.ud.elogr.Backup.BackupData;
import epic.ud.elogr.R;
import epic.ud.elogr.api.Res.AuthResponse;
import epic.ud.elogr.api.dto.submitdata.SubmitCatchData;
import epic.ud.elogr.api.dto.submitdata.SubmitData;
import epic.ud.elogr.api.dto.submitdata.SubmitDataContent;
import epic.ud.elogr.api.dto.submitdata.SubmitGearDataGillNet;
import epic.ud.elogr.api.dto.submitdata.SubmitGearDataLongLine;
import epic.ud.elogr.api.dto.submitdata.SubmitGearDataRingNet;
import epic.ud.elogr.api.dto.submitdata.SubmitGillNet;
import epic.ud.elogr.api.dto.submitdata.SubmitRingNet;
import epic.ud.elogr.api.dto.submitdata.SubmitSelectedFish;
import epic.ud.elogr.api.dto.submitdata.SubmitSetData;
import epic.ud.elogr.api.dto.submitdata.SubmitTripData;
import epic.ud.elogr.api.mng.CallManager;
import epic.ud.elogr.api.mng.NetworkHelper;
import epic.ud.elogr.config.AppConsts;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Harbour;
import epic.ud.elogr.db.entity.Trip;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.HomeActivity;
import epic.ud.elogr.view.TripMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArrivalActivity extends AppCompatActivity implements BackupData.OnBackupListener {
    View view;
    TextView arrivalTitleLabel, arrivalDateTimeLabel, arrivalHarbourLabel;
    EditText arrivalDateTimeInput;
    Spinner arrivalHarbourInput;
    Button btnSubmit;

    List<Harbour> harbourList;
    HashMap<Integer, String> harbourMap;
    String[] harbourArray;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    ProgressDialog progressDialog;

    private long mLastClickTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        try {
            view = this.getWindow().getDecorView().findViewById(R.id.arrival_layout);
            Store.INSTANCE.reInitStore(ArrivalActivity.this);
        //view = this.getWindow().getDecorView().findViewById(R.id.arrival_layout);
        initUi();
        progressDialog = new ProgressDialog(ArrivalActivity.this);
        setSpinnerData();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Arrival Activity"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initUi() {
        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        if (word.size() < 67) {
            Toast.makeText(getApplicationContext(), "Data Loading Incorrect...", Toast.LENGTH_SHORT).show();
            finish();
            Intent departure = new Intent(ArrivalActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }


        arrivalTitleLabel = findViewById(R.id.arrivalTitleLabel);arrivalTitleLabel.setTypeface(tf);
        arrivalDateTimeLabel = findViewById(R.id.arrivalDateTimeLabel);arrivalDateTimeLabel.setTypeface(tf);
        arrivalHarbourLabel = findViewById(R.id.arrivalHarbourLabel);arrivalHarbourLabel.setTypeface(tf);

        arrivalDateTimeInput = findViewById(R.id.arrivalDateTimeInput);
        arrivalHarbourInput = findViewById(R.id.arrivalHarbourInput);
        btnSubmit = findViewById(R.id.btnSubmit);btnSubmit.setTypeface(tf);

        if (lang.equals("si")) {
            arrivalTitleLabel.setText("meñfKk");
            arrivalDateTimeLabel.setText("" + word.get(11).getSi_name());
            arrivalHarbourLabel.setText("" + word.get(12).getSi_name());
            btnSubmit.setText(" B<.  ");
        }
        if (lang.equals("en")) {
            arrivalTitleLabel.setText("Arrival ");
            arrivalDateTimeLabel.setText("" + word.get(11).getEn_name());
            arrivalHarbourLabel.setText("" + word.get(12).getEn_name());
            btnSubmit.setText("" + word.get(52).getEn_name());
        }
        if (lang.equals("ta")) {
            arrivalTitleLabel.setText("tUif ");
            arrivalDateTimeLabel.setText("" + word.get(11).getTm_name());
            arrivalHarbourLabel.setText("" + word.get(12).getTm_name());
            btnSubmit.setText("" + word.get(52).getTm_name());
        }



        arrivalDateTimeInput.setText("" + setDate(null));

        arrivalDateTimeInput.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int inType = arrivalDateTimeInput.getInputType(); // backup the input type
                arrivalDateTimeInput.setInputType(InputType.TYPE_NULL); // disable soft input
                arrivalDateTimeInput.onTouchEvent(event); // call native handler
                //startGpsTxt.setInputType(inType); // restore input type

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (arrivalDateTimeInput.getRight() - arrivalDateTimeInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        initDate(arrivalDateTimeInput);
                        return true;
                    }
                }
                return false;
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                formValidate();

            }
        });


    }

    void setSpinnerData() {
        List<Harbour> harbourList = Store.INSTANCE.getAppDatabase().harbourDao().selectAll();
        harbourArray = new String[harbourList.size()];
        harbourMap = new HashMap<Integer, String>();
        for (int i = 0; i < harbourList.size(); i++) {
            String str = "";
            if (Store.INSTANCE.getLang().equals("si")) {
                str = harbourList.get(i).getSi_name();
            }
            if (Store.INSTANCE.getLang().equals("en")) {
                str = harbourList.get(i).getEn_name();
            }
            if (Store.INSTANCE.getLang().equals("ta")) {
                str = harbourList.get(i).getTm_name();
            }
            harbourMap.put(Integer.parseInt(harbourList.get(i).getId()), str);
            harbourArray[i] = str;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, harbourArray) {
            Typeface font = Store.INSTANCE.getFace();

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setTypeface(font);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setGravity(Gravity.CENTER);
                view.setPadding(0,8,0,8);
                view.setTypeface(font);
                return view;
            }
        };
        arrivalHarbourInput.setAdapter(adapter);
    }

    public Integer getKeyX(HashMap<Integer, String> map, String value) {
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static int getIndex(String arr[], String value) {
        // if array is Null
        if (arr == null) {
            return -1;
        }
        // find length of array
        int len = arr.length;
        int i = 0;
        // traverse in the array
        while (i < len) {
            // if the i-th element is t
            // then return the index
            if (arr[i] == value) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
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
            String formattedDate = dateF.format(date);
            return formattedDate;
        }
    }

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
        new DatePickerDialog(ArrivalActivity.this, date, myCalendar
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
        mTimePicker = new TimePickerDialog(ArrivalActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    public void formValidate() {
        if (arrivalDateTimeInput.getText().toString().trim().length() == 0) {
            arrivalDateTimeInput.setError("Please fill this");
            return;
        }
        else if (arrivalHarbourInput.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please select harbour", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            btnSubmit.setEnabled(false);
            formSave();
        }
    }

    public void formSave() {
        int lineId = Store.INSTANCE.getOnTripId();
        Store.INSTANCE.getAppDatabase().tripDao().updateArrival(
                "" + lineId,
                "" + arrivalDateTimeInput.getText().toString(),
                arrivalHarbourInput.getSelectedItemPosition() == 0 ? "" : "" + getKeyX(harbourMap, arrivalHarbourInput.getSelectedItem().toString()));
        initData();

    }


    public void setTripStatus() {
        int lineId = Store.INSTANCE.getOnTripId();
        Store.INSTANCE.getAppDatabase().tripDao().updateTripStatus(""+lineId);
        //initData();

    }


    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public void initData() {
        SubmitDataContent submitDataContent = new SubmitDataContent();
        List<Trip> trips = Store.INSTANCE.getAppDatabase().tripDao().getAllDrafList();
        if(trips.size()>0){
        for (Trip trip : trips) {
            submitDataContent.setTripdata(new SubmitTripData(
                    "" + trip.getTripId(),
                    "" + trip.getVesselId(),
                    "" + trip.getSkipperId(),
                    "" + trip.getContact(),
                    "" + trip.getLicenseNo(),
                    "" + trip.getDepartureHarbour(),
                    "" + trip.getDepartureDate(),
                    "" + trip.getDepartureRemarks(),
                    "" + trip.getSkipperId(),
                    "" + trip.getArrivalHarbour(),
                    "" + trip.getArrivalDate()
            ));


            List<TripGear> tripGears = Store.INSTANCE.getAppDatabase().tripGearDao().getAllByTripId(trip.getTripId());
            List<Object> submitGearDataList = new ArrayList<>();
            List<SubmitSetData> submitSetDataList = new ArrayList<>();
            List<SubmitCatchData> submitCatchDataList = new ArrayList<>();
            for (TripGear tripGear : tripGears) {

                if(tripGear.getStartDate() == null){continue;}

                if(tripGear.getGearTypeName().toString().trim().equals("LONGLINE")){
                    SubmitGillNet gillNet = new SubmitGillNet(
                            "" + tripGear.getNetMaterial(),
                            "" + tripGear.getMeshSize(),
                            "" + tripGear.getPlyOfTheNet(),
                            "" + tripGear.getHeightOfNet(),
                            "" + tripGear.getLengthOfNetPiece(),
                            "" + tripGear.getNumberOfNetPieces()
                    );
                    SubmitRingNet ringNet = new SubmitRingNet(
                            "" + tripGear.getLengthOfTheRingNet(),
                            "" + tripGear.getHeightOfTheRingNet()
                    );

                    SubmitGearDataLongLine gearData = new SubmitGearDataLongLine(
                            "" + tripGear.getId(),
                            "" + tripGear.getGearTypeName(),
                            "" + tripGear.getMainLine(),
                            "" + tripGear.getBranchLine(),
                            "" + tripGear.getNoHooks(),
                            "" + tripGear.getHooksTypes(),
                            "" + tripGear.getDepth(),
                            "" + tripGear.getBaitType(),
                            gillNet,
                            ringNet
                    );

                    submitGearDataList.add(gearData);

                }
                if(tripGear.getGearTypeName().toString().trim().equals("GILLNET")){
                    SubmitGearDataGillNet gn = new SubmitGearDataGillNet(
                            "" + tripGear.getId(),
                            "" + tripGear.getGearTypeName(),
                            "" + tripGear.getNetMaterial(),
                            "" + tripGear.getMeshSize(),
                            "" + tripGear.getPlyOfTheNet(),
                            "" + tripGear.getHeightOfNet(),
                            "" + tripGear.getLengthOfNetPiece(),
                            "" + tripGear.getNumberOfNetPieces()

                    );
                    submitGearDataList.add(gn);

                }
                if(tripGear.getGearTypeName().toString().trim().equals("RING NET")){
                    SubmitGearDataRingNet rn = new SubmitGearDataRingNet(
                            "" + tripGear.getId(),
                            "" + tripGear.getGearTypeName(),
                            "" + tripGear.getLengthOfTheRingNet(),
                            "" + tripGear.getHeightOfTheRingNet()
                    );
                    submitGearDataList.add(rn);
                }

                SubmitSetData submitSetData = new SubmitSetData(
                        "" + tripGear.getId(),
                        "" + tripGear.getId(),
                        "" + tripGear.getStartDate(),
                        "" + tripGear.getNumber(),
                        "",
                        "" + tripGear.getStartGpsLon(),
                        "" + tripGear.getStartGpsLat(),
                        "",
                        "" + tripGear.getEndGpsLon(),
                        "" + tripGear.getEndGpsLat(),
                        "" + tripGear.getEndDate()
                );

                submitSetDataList.add(submitSetData);

                List<Catch> catches = Store.INSTANCE.getAppDatabase().catchDao().getAllByGearId(tripGear.getId());

                if(catches != null || catches.size() > 0){

                    List<SubmitSelectedFish> submitSelectedFishList = new ArrayList<>();
                    for (Catch cat : catches) {

                        List<CatchFish> catchFish = Store.INSTANCE.getAppDatabase().catchfishDao().getAllByCatchId(cat.getId());
                        if(catches != null || catchFish.size() > 0){

                        for (CatchFish cf : catchFish) {
                            SubmitSelectedFish submitSelectedFish = new SubmitSelectedFish(
                                    "" + cf.getFishid(),
                                    "" + cf.getFishQty(),
                                    "" + cf.getFishWeight()
                            );
                            submitSelectedFishList.add(submitSelectedFish);
                        }

                        }

                        SubmitCatchData catchData = new SubmitCatchData(
                                "" + cat.getId(),
                                "" + cat.getGearId(),
                                "" + cat.getCatchType(),
                                "" + cat.getFishType(),
                                submitSelectedFishList
                        );
                        submitCatchDataList.add(catchData);
                    }
                }


            }


            submitDataContent.setGeardata(submitGearDataList);
            submitDataContent.setSetdata(submitSetDataList);
            submitDataContent.setCatchdata(submitCatchDataList);
            submitDataContent.setLicenseNumber("" + trip.getLicenseNo());

        }
        SubmitData submitData = new SubmitData(submitDataContent);
        String json = new Gson().toJson(submitData);
        //writeToFile("" + json, getApplicationContext());
        sendData(submitData);
        }{
            Toast.makeText(getApplicationContext(), "Previous Data Submited, Start New Trip", Toast.LENGTH_LONG)
                    .show();
        }
    }


    public void sendData(SubmitData submitData) {
        Store.INSTANCE.setLog();
        if(submitData.getSubmitData().getTripdata().getPhone_id() != null){
        if (NetworkHelper.isNetworkAvailable(getApplicationContext())) {


            progressDialog.setTitle("Uploading");
            progressDialog.setMessage("please wait");
            progressDialog.show();


            Call<AuthResponse> call = CallManager.INSTANCE.getSubmitCaller().submitData(submitData);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(retrofit2.Call<AuthResponse> call, Response<AuthResponse> response) {
                    Log.i("Log Res: ", "---" + response.toString());

                       /* if (!response.body().equals(null) && response.code() == StateCodes.OK && response.body().getStatus().equals("success")) {

                            Toast.makeText(getApplicationContext(), "Login Activity Success !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "LoginActivity failed, please enter valid credentials", Toast.LENGTH_LONG)
                                    .show();
                        }*/

                    progressDialog.dismiss();
                    if(response.code() == 200){
                        setTripStatus();
                        dbClear();
                        finish();
                        Intent departure = new Intent(ArrivalActivity.this, HomeActivity.class);
                        startActivity(departure);
                    }
                    else {
                        btnSubmit.setEnabled(true);
                        /*finish();
                        Intent departure = new Intent(ArrivalActivity.this, HomeActivity.class);
                        startActivity(departure);*/
                        Toast.makeText(getApplicationContext(), "Please Check Your \n GEAR DATA | SET DATA | CATCH DATA \n ERROR: "+response.code()+" \n"+response.message(), Toast.LENGTH_LONG)
                                .show();
                    }


                }

                @Override
                public void onFailure(retrofit2.Call<AuthResponse> call, Throwable t) {
                    btnSubmit.setEnabled(true);
                    Log.i("Log Res: ", "---" + t.getMessage().toString());
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG);
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

        }

        }


    }

    public void dbClear(){
        //Backup process
//        BackupData backupData = new BackupData(ArrivalActivity.this);
//        backupData.setOnBackupListener(this);
//        backupData.exportToSD();
        //backupData.importFromSD();

//for backup
        try{

            SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss a");
            String dateTimeString = formatTime.format(new Date());
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission == PackageManager.PERMISSION_GRANTED) {


                File db = getDatabasePath(""+AppConsts.DB_NAME);
                File dbShm = new File(db.getParent(), ""+AppConsts.DB_NAME+"-shm");
                File dbWal = new File(db.getParent(), ""+AppConsts.DB_NAME+"-wal");

                File db2 = new File("/sdcard/ELOG_Backup/" + dateTimeString, ""+ AppConsts.DB_NAME);
                File dbShm2 = new File(db2.getParent(), ""+AppConsts.DB_NAME+"-shm");
                File dbWal2 = new File(db2.getParent(), ""+AppConsts.DB_NAME+"-wal");

                try {
                    FileUtils.copyFile(db, db2);
                    FileUtils.copyFile(dbShm, dbShm2);
                    FileUtils.copyFile(dbWal, dbWal2);
                    Toast.makeText(getApplicationContext(),"Backup successfully complete", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"BACKUPDB : "+e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("SAVEDB", e.toString());
                }
            } else {


                Snackbar.make(view, "Please allow access to your storage", Snackbar.LENGTH_LONG)
                        .setAction("Allow", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(ArrivalActivity.this, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, 0);
                            }
                        }).show();
                return;
            }



        }catch (Exception e){
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Arrival Activity"));
        }


/*
        //for Restore
        File forder = new File("/sdcard/DFAR_Backup");
        File[] listFile = forder.listFiles();

        if (listFile != null) {
            final String[] listFileName = new String[listFile.length];
            for (int i = 0, j = listFile.length - 1; i < listFile.length; i++, j--) {
                listFileName[j] = listFile[i].getName();
            }


            if (listFileName.length > 0) {

                // get layout for list
                LayoutInflater inflater = ((FragmentActivity) ArrivalActivity.this).getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.list_backup_file, null);

                final AlertDialog.Builder builder = new AlertDialog.Builder(ArrivalActivity.this);


                // set view for dialog
                builder.setView(convertView);
                builder.setTitle("Data Backup / Restore").setIcon(R.drawable.trip_history);
                builder.setMessage("Select, what do you want to restore...");

                final AlertDialog alert = builder.create();

                ListView lv = (ListView) convertView.findViewById(R.id.lv_backup);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ArrivalActivity.this,
                        android.R.layout.simple_list_item_1, listFileName);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view_, int position, long id) {
                        alert.dismiss();
                        Log.e("RESTOREDB", ">>>" + listFileName[position]);

                        int permission2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (permission2 == PackageManager.PERMISSION_GRANTED) {

                            File db = new File("/sdcard/DFAR_Backup/" + listFileName[position], ""+AppConsts.DB_NAME);
                            File dbShm = new File(db.getParent(), ""+AppConsts.DB_NAME+"-shm");
                            File dbWal = new File(db.getParent(), ""+AppConsts.DB_NAME+"-wal");

                            File db2 = getDatabasePath(""+AppConsts.DB_NAME);
                            File dbShm2 = new File(db2.getParent(), ""+AppConsts.DB_NAME+"-shm");
                            File dbWal2 = new File(db2.getParent(), ""+AppConsts.DB_NAME+"-wal");

                            try {
                                FileUtils.copyFile(db, db2);
                                FileUtils.copyFile(dbShm, dbShm2);
                                FileUtils.copyFile(dbWal, dbWal2);
                                Toast.makeText(getApplicationContext(),"Restore successfully complete", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),"RESTOREDB : "+e.toString(), Toast.LENGTH_LONG).show();
                                Log.e("RESTOREDB", e.toString());
                            }
                        } else {
                            Snackbar.make(view, "Please allow access to your storage", Snackbar.LENGTH_LONG)
                                    .setAction("Allow", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ActivityCompat.requestPermissions(ArrivalActivity.this, new String[]{
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            }, 0);
                                        }
                                    }).show();
                            return;
                        }

                    }
                });
                alert.show();
            }
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ArrivalActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                    .setMessage("Backup file is empty");
            builder.show();
        }

*/


        Store.INSTANCE.getAppDatabase().tripDao().deleteAll();
        Store.INSTANCE.getAppDatabase().tripGearDao().deleteAll();
        Store.INSTANCE.getAppDatabase().catchDao().deleteAll();
        Store.INSTANCE.getAppDatabase().catchfishDao().deleteAll();
    }


    //start db backup process
    @Override
    public void onFinishExport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Export success";
        }
        Toast.makeText(getApplicationContext(), notify, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Import success";
        }
        Toast.makeText(getApplicationContext(), notify, Toast.LENGTH_SHORT).show();
    }


    void snackMessage(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


}
