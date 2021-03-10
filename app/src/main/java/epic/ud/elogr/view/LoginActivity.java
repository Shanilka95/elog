package epic.ud.elogr.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import java.util.List;
import java.util.Locale;

import epic.ud.elogr.R;
import epic.ud.elogr.api.Res.AuthResponse;
import epic.ud.elogr.api.dto.AuthDto;
import epic.ud.elogr.api.mng.CallManager;
import epic.ud.elogr.api.mng.NetworkHelper;
import epic.ud.elogr.config.StateCodes;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.controller.SettingsController;
import epic.ud.elogr.db.entity.Login;
import epic.ud.elogr.util.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Configuration conf;

    EditText unTxt;
    EditText pwTxt;
    Button logBtn;
    TextView setLangBtn, signinTxt;

    AnimationDrawable animationDrawable;
    ConstraintLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {






        unTxt = (EditText) findViewById(R.id.unTxt);
        pwTxt = (EditText) findViewById(R.id.pwTxt);
        logBtn = (Button) findViewById(R.id.logBtn);

        signinTxt = (TextView) findViewById(R.id.signinTxt);
        setLangBtn = (TextView) findViewById(R.id.setLangBtn);
        initSetting();

        signinTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog();
                return false;
            }
        });
        setLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setLangBtn.getText().toString().equals("SINHALA")) {
                    setLangBtn.setText("TAMIL");
                    initLocal("ta");
                } else if (setLangBtn.getText().toString().equals("TAMIL")) {
                    setLangBtn.setText("ENGLISH");
                    initLocal("en");
                } else if (setLangBtn.getText().toString().equals("ENGLISH")) {
                    setLangBtn.setText("SINHALA");
                    initLocal("si");
                }
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });


        }
        catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Login Activity"));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }

    public void initSetting() {
        String lan = SettingsController.lang(getApplicationContext(), null);
        if (lan != null) {
            setLangBtn.setText(lan);
            if (setLangBtn.getText().toString().equals("SINHALA")) {
                initLocal("si");
            } else if (setLangBtn.getText().toString().equals("TAMIL")) {
                initLocal("ta");
            } else if (setLangBtn.getText().toString().equals("ENGLISH")) {
                initLocal("en");
            }
        } else {
            setLangBtn.setText("ENGLISH");
            initLocal("en");
        }
    }

    public void initLocal(String lan) {
        conf = getResources().getConfiguration();
        conf.locale = new Locale(lan);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Resources resources = new Resources(getAssets(), metrics, conf);
        Store.INSTANCE.setResources(resources);
        if(lan.equals("en")){Store.INSTANCE.setFace(Typeface.createFromAsset(getAssets(), "font/DroidSans.ttf")); Store.INSTANCE.setLang("en");}
        if(lan.equals("si")){Store.INSTANCE.setFace(Typeface.createFromAsset(getAssets(), "font/DLSarala.ttf")); Store.INSTANCE.setLang("si");}
        if(lan.equals("ta")){Store.INSTANCE.setFace(Typeface.createFromAsset(getAssets(), "font/Bamini.ttf")); Store.INSTANCE.setLang("ta");}
        SettingsController.lang(getApplicationContext(), setLangBtn.getText().toString());
    }

    public void getLogin() {
        Store.INSTANCE.setLog();
        if (unTxt.getText().toString().trim().length() != 0 && pwTxt.getText().toString().trim().length() != 0) {
            AuthDto model = new AuthDto(unTxt.getText().toString().trim(), pwTxt.getText().toString().trim());

            if (NetworkHelper.isNetworkAvailable(getApplicationContext())) {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Authenticating");
                progressDialog.setMessage("please wait");
                progressDialog.show();


                Call<AuthResponse> call = CallManager.INSTANCE.getAuthCaller().authenticate(model);
                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<AuthResponse> call, Response<AuthResponse> response) {
                        Log.i("Log Res: ", "---"+response.toString());

                        if (!response.body().equals(null) && response.code() == StateCodes.OK && response.body().getStatus().equals("success")) {

                            if(!response.body().getStatus().equals("success")){
                                Toast.makeText(getApplicationContext(), "Server not responding...", Toast.LENGTH_LONG)
                                        .show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Login Activity Success !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Store.INSTANCE.getAppDatabase().loginDao().saveLogin(new Login(unTxt.getText().toString(), pwTxt.getText().toString(),"", 0));
                            if(Store.INSTANCE.getAppDatabase().tripDao().getAllDraf().size() == 0){
                                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(home);
                            }else{
                                Intent trip = new Intent(LoginActivity.this, TripMenuActivity.class);
                                startActivity(trip);
                            }
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "LoginActivity failed, please enter valid credentials", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<AuthResponse> call, Throwable t) {
                        Log.i("Log Res: ", "---"+t.getMessage().toString());
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG);
                    }
                });


            } else {
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(getApplicationContext(), "Please fill Username / Password fields", Toast.LENGTH_SHORT).show();
        }


    }

    public static String sensorTypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "Ambient Temperature";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagnetic Rotation Vector";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";
            case Sensor.TYPE_HEART_RATE:
                return "Heart Rate Monitor";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
                return "Orientation";
            case Sensor.TYPE_PRESSURE:
                return "Orientation";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            default:
                return "Unknown";
        }
    }


    public void showDialog(){
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sensor_log);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String sns = "";
        if(sensors != null){
            for (Sensor sensor: sensors){
                sns = sns+"* Name : " + sensor.getName()
                        + " | Type : "
                        + sensorTypeToString(sensor.getType())
                        + " | Vendor : " + sensor.getVendor()
                        + " | Version : "+ sensor.getVersion() +" \n ";
            }

        }


        TextView text = (TextView) dialog.findViewById(R.id.sensLogTxt);
        text.setText(""+sns);



        dialog.show();

    }


}
