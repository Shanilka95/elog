package epic.ud.elogr.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.room.Room;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import epic.ud.elogr.controller.SettingsController;
import epic.ud.elogr.db.AppDatabase;
import epic.ud.elogr.view.HomeActivity;

/**
 * Created by Udith on 24/5/2018.
 */

public enum Store {
    INSTANCE;
    Activity nowAct;
    Context c;
    Configuration conf;
    Resources resources;
    Typeface face;
    String lang;
    StringBuilder log = new StringBuilder();
    AppDatabase appDatabase;
    int onTripId = -1;




    private Store() {
    }

    public void reInitStore(Activity act) {
        if(nowAct == null){ nowAct = act; }
        if(appDatabase == null){ initDb(nowAct.getApplicationContext());}
        if(resources == null || face == null || lang == null){initSetting();}
        if(onTripId == -1){initTripId();}
    }


    public int getOnTripId() {
        return onTripId;
    }

    public void setOnTripId(int onTripId) {
        this.onTripId = onTripId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }




    public Typeface getFace() {
        return face;
    }

    public void setFace(Typeface face) {
        this.face = face;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }


    public StringBuilder getLog() {
        return log;
    }

    public void setLog(StringBuilder log) {
        this.log = log;
    }

    public void setLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec("logcat");
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        CharSequence cs = "APPLOG";
                        if (line.contains(cs)) {
                            Store.INSTANCE.getLog().append(line.substring(line.indexOf("APPLOG"),line.length())); // Cuts out the junk at the beginning of the log
                            Store.INSTANCE.getLog().append("\n");
                        }
                    }

                } catch (Exception e) {
                    Log.d("log-append", e.getMessage());
                }
            }
        }).start();
    }



    public void initDb(Context c){
        appDatabase = Room.databaseBuilder(
                c,
                AppDatabase.class, "eLog.db").
                allowMainThreadQueries().
                fallbackToDestructiveMigration().
                build();
        Store.INSTANCE.setAppDatabase(appDatabase);
    }

    public void initLocal(String lan) {
        conf = getResources().getConfiguration();
        conf.locale = new Locale(lan);
        DisplayMetrics metrics = new DisplayMetrics();
        nowAct.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Resources resources = new Resources(nowAct.getAssets(), metrics, conf);
        Store.INSTANCE.setResources(resources);
        if (lan.equals("en")) {
            setFace(Typeface.createFromAsset(nowAct.getAssets(), "font/DroidSans.ttf"));
            setLang("en");
        }
        if (lan.equals("si")) {
            setFace(Typeface.createFromAsset(nowAct.getAssets(), "font/DLSarala.ttf"));
            setLang("si");
        }
        if (lan.equals("ta")) {
            INSTANCE.setFace(Typeface.createFromAsset(nowAct.getAssets(), "font/Bamini.ttf"));
            INSTANCE.setLang("ta");
        }

    }

    public void initSetting() {
        String lan = SettingsController.lang(nowAct.getApplicationContext(), null);
        if (lan != null) {
            if (lan.equals("SI")) {
                initLocal("si");
            } else if (lan.equals("TA")) {
                initLocal("ta");
            } else if (lan.equals("EN")) {
                initLocal("en");
            }

        } else {
            initLocal("en");
        }
    }

    public void initTripId(){
        if(getAppDatabase().tripDao().getAllDraf().size() == 0){
            //setOnTripId(Store.INSTANCE.getAppDatabase().tripDao().getMaxId());
        }else{
            setOnTripId(getAppDatabase().tripDao().getAllDrafMaxId());
        }
    }


}
