package epic.ud.elogr.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.facebook.stetho.Stetho;

import java.util.Locale;

import epic.ud.elogr.R;
import epic.ud.elogr.config.AppConsts;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.controller.SettingsController;
import epic.ud.elogr.db.AppDatabase;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.TripData.DepartureActivity;

public class SplashActivity extends AppCompatActivity {
    Configuration conf;
    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {



//        TextView versionTxt = (TextView)findViewById(R.id.versionTxt);
//        versionTxt.setText(""+BuildConfig.VERSION_CODE);
        Stetho.initializeWithDefaults(this);
        initDb();
        initSetting();
        Thread timer = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(3000);
                    Intent loginIntent;
                    if (Store.INSTANCE.getAppDatabase().loginDao().loginCount().size()>0) {
                        if(Store.INSTANCE.getAppDatabase().tripDao().getAllDraf().size() == 0){
                            loginIntent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(loginIntent);
                        }else{
                            Store.INSTANCE.setOnTripId(Store.INSTANCE.getAppDatabase().tripDao().getAllDrafMaxId());
                            if(Store.INSTANCE.getAppDatabase().tripDao().getSettingFormIsValid(Store.INSTANCE.getOnTripId()) == 1){
                                loginIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                                startActivity(loginIntent);
//                                return;
                            }else if(Store.INSTANCE.getAppDatabase().tripDao().getDepatureFormIsValid(Store.INSTANCE.getOnTripId()) == 1){
                                loginIntent = new Intent(getApplicationContext(), DepartureActivity.class);
                                startActivity(loginIntent);
                            }else {
                                loginIntent = new Intent(getApplicationContext(), TripMenuActivity.class);
                                startActivity(loginIntent);
                            }


                        }
                    } else {
                        loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginIntent);
                    }

                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };

        timer.start();

        }
        catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Splash Activity"));
        }



    }


    public void initSetting() {
        String lan = SettingsController.lang(getApplicationContext(), null);
        if (lan != null) {
            if (lan.equals("SINHALA")) {
                initLocal("si");
            } else if (lan.equals("TAMIL")) {
                initLocal("ta");
            } else if (lan.equals("ENGLISH")) {
                initLocal("en");
            }

        } else {
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
        if (lan.equals("en")) {
            Store.INSTANCE.setFace(Typeface.createFromAsset(getAssets(), "font/DroidSans.ttf"));
            Store.INSTANCE.setLang("en");
        }
        if (lan.equals("si")) {
            Store.INSTANCE.setFace(Typeface.createFromAsset(getAssets(), "font/DLSarala.ttf"));
            Store.INSTANCE.setLang("si");
        }
        if (lan.equals("ta")) {
            Store.INSTANCE.setFace(Typeface.createFromAsset(getAssets(), "font/Bamini.ttf"));
            Store.INSTANCE.setLang("ta");
        }

    }



    public void initDb(){
        appDatabase = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class, ""+ AppConsts.DB_NAME).
                allowMainThreadQueries().
                fallbackToDestructiveMigration().
                build();
        Store.INSTANCE.setAppDatabase(appDatabase);
    }

}
