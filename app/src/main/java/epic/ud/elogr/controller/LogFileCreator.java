package epic.ud.elogr.controller;

/**
 * Created by Udith Perera on 6/9/2020.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import android.os.Environment;

import epic.ud.elogr.config.AppConsts;

public class LogFileCreator {

    static String folderSD = Environment.getExternalStorageDirectory() + "/"+ AppConsts.BACKUP_DIR;
    public static void Makelog(String text){

        File folder 	= new File(folderSD);
        boolean success = false;

        if (!folder.exists()) {
            success = folder.mkdir();
        }
        else{
            success = true;
        }

        if(success){

            File logFile = new File(folderSD+"/ELOGErrorlog" + SystemDateFunctions.getFormattedCurrentDateFORINS() + ".txt");

            if (!logFile.exists()){

                try{
                    logFile.createNewFile();
                }
                catch (IOException e){
                    Makelog(LogFileCreator.MakeErrorText(e, "LOGFILE CREATOR | Makelog - createNewFile"));
                }
            }
            try{
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(text);
                buf.close();

            }
            catch (IOException e){
                Makelog(LogFileCreator.MakeErrorText(e, "LOGFILE CREATOR | Makelog"));
            }
        }
    }



    public static String MakeErrorText(Exception e, String classMethod){
        String errorText = "";
        try {
            LogFileCreator.Makelog("\n===================================\n");
            LogFileCreator.Makelog(classMethod + "\n");
            LogFileCreator.Makelog(getStackTrace(e) + "\n");
            LogFileCreator.Makelog(new Date() + "\n");
            LogFileCreator.Makelog("===================================\n");
        }
        catch (Exception ex) {
            LogFileCreator.Makelog("\n===================================\n");
            LogFileCreator.Makelog("LOGFILE CREATOR | MakeErrorText\n");
            LogFileCreator.Makelog(getStackTrace(ex) + "\n");
            LogFileCreator.Makelog(new Date() + "\n");
            LogFileCreator.Makelog("===================================\n");
        }

        return errorText;
    }



    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
