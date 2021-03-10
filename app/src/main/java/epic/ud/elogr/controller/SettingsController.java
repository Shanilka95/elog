package epic.ud.elogr.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



import static android.content.Context.MODE_PRIVATE;

public class SettingsController {
    public static String getSettings(Context c, String userId, String name, String value) {
        String returnval = null;
        try{
            SharedPreferences sharedpreferences;

            if(userId != null){
                sharedpreferences = c.getSharedPreferences(userId, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(name != null && value != null){
                    editor.putString(name, value);
                    editor.commit();
                }
                else if(name != null && value == null){
                    editor.putString(name, "N/A");
                    editor.commit();

                }
                else {
                    if (sharedpreferences.contains(name)) {
                        returnval = sharedpreferences.getString(name, "N/A");
                    }
                }
            }
        }catch (Exception e){
            Log.d("Setting Controller Ex: ", e.getMessage());
        }
        return returnval;
    }

    public static String lang(Context c, String value){
        SharedPreferences sharedpreferences;
        sharedpreferences = c.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(value != null){
            editor.putString("lang", value);
            editor.commit();
            return null;
        }else {
            if (sharedpreferences.contains("lang")) {
                return sharedpreferences.getString("lang", "EN");
            }else {
                return null;
            }
        }
    }

    public static String tripId(Context c, String value){
        SharedPreferences sharedpreferences;
        sharedpreferences = c.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(value != null){
            editor.putString("tripid", value);
            editor.commit();
            return null;
        }else {
            if (sharedpreferences.contains("tripid")) {
                return sharedpreferences.getString("tripid", "EMT");
            }else {
                return null;
            }
        }
    }

}
