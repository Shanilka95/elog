package epic.ud.elogr.controller;

/**
 * Created by Udith Perera on 6/9/2020.
 */
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SystemDateFunctions {

    public static String getSystemDate() {

        Calendar c 	= Calendar.getInstance();

        int day 	= c.get(Calendar.DATE);
        int month 	= c.get(Calendar.MONTH) + 1;
        int year 	= c.get(Calendar.YEAR);
        String s 	= month + "/" + day + "/" + year;

        return s;
    }

    public static String getMobileDate() {

        Calendar c 	= Calendar.getInstance();

        int day 	= c.get(Calendar.DAY_OF_MONTH);
        int month 	= c.get(Calendar.MONTH) + 1;
        String dayVal = "";

        if(String.valueOf(day).length() == 1){
            dayVal = "0" + String.valueOf(day);
        }
        else{
            dayVal = String.valueOf(day);
        }

        int year 	= c.get(Calendar.YEAR);
        String s 	= month + "/" + dayVal + "/" + year;

        return s;
    }

    public static String getNowSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s    = sdf.format(day);
        return s;
    }

    public static int CompareDate(String serverDate, String getDate) {
        int ret = -5;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date1 = sdf.parse(serverDate);
            Date date2 = sdf.parse(getDate);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.compareTo(date2) > 0) {
                ret = 1;
            }
            else if (date1.compareTo(date2) < 0) {
                ret = -1;
            }
            else if (date1.compareTo(date2) == 0) {
                ret = 0;
            }
            else {
                ret = -5;
            }
        }
        catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SYSTEM DATE FUNCTIONS | CompareDate"));
        }
        return ret;

    }

    public static int compareDates(String serverDate, String getDate) {
        int ret = -5;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = sdf.parse(serverDate);
            Date date2 = sdf.parse(getDate);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.compareTo(date2) > 0) {
                ret = 1;
            } else if (date1.compareTo(date2) < 0) {
                ret = -1;
            } else if (date1.compareTo(date2) == 0) {
                ret = 0;
            } else {
                ret = -5;
            }
        }
        catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SYSTEM DATE FUNCTIONS | CompareDate"));
        }
        return ret;

    }

    public static String convertedDate(String oldFormat, String newFormat, String dateTo){
        SimpleDateFormat oFormat = new SimpleDateFormat(oldFormat, Locale.US);
        SimpleDateFormat nFormat = new SimpleDateFormat(newFormat, Locale.US);
        try {
            Date date = oFormat.parse(dateTo);
            String formattedDate = nFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            return dateTo;
        }
    }

    public static String getNowDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        Calendar c 	  = Calendar.getInstance();
        Date day 	  = c.getTime();
        String s2 	  = df.format(day);

        return s2;
    }

    public static String getNow() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar c 	  = Calendar.getInstance();
        Date day 	  = c.getTime();
        String s2 	  = df.format(day);

        return s2;
    }

    public static String getNowNew() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'HH'HH:mm:ss");
        Calendar c 	  = Calendar.getInstance();
        Date day 	  = c.getTime();
        String s2 	  = df.format(day);

        return s2;
    }

    public static String getNowNewView() {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar c 	  = Calendar.getInstance();
        Date day 	  = c.getTime();
        String s2 	  = df.format(day);

        return s2;
    }

    public static String getFormattedCurrentDate() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss a");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s2 	= dateFormatter.format(day);

        return s2;
    }

    public static String getFormattedCurrentDateFORINS() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s2 	= dateFormatter.format(day);

        return s2;
    }

    public static String getCurrentDateForSchedule() {
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s2 	= dateFormatter.format(day);

        return s2;
    }

    public static String getFormattedNewCurrentDate() {
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s2 	= dateFormatter.format(day);
        return s2;
    }

    public static String getFormattedCurrentTime() {
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm:ss");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s2 	= dateFormatter.format(day);

        return s2;
    }

    public static String getCurrentTime() {
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm");
        Calendar c 	= Calendar.getInstance();
        Date day 	= c.getTime();
        String s2 	= dateFormatter.format(day);

        return s2;
    }

    public static String dateLongFormatDOB(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
            String fDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null)
            return "" + date.getTime();
        else
            return "";
    }
    public static String longtoNormalDateFormat(String date1)
    {
        long millisecond = Long.parseLong(date1);
        String dateString = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(millisecond)).toString();
        String aab 			= dateString.replaceAll(" ", "T");
        return aab;
    }

    public static String getFormattedServerDate(String formatDate) {
        Log.i("LOG_OOO-",""+formatDate);
        String aab 			= formatDate.replaceAll("\\+", "T");
        String[] splitT 	= aab.split("T");

        String formatedDate = splitT[0] + " " + splitT[1];

        return formatedDate;
    }

    public static String GetTimeDifference(String REPORTEDDATETIME, String NOW) {
        String totalTimeDaysHoursMinuts = "";

        try {
            Date _day 	= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(REPORTEDDATETIME);
            Date day 	= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(NOW);

            double diff = day.getTime() - _day.getTime() ;

            double totalMinuts 			= diff / (60 * 1000);
            double leftMinutsAfterHours = totalMinuts % 60;
            double totalHours 			= (totalMinuts - leftMinutsAfterHours) / 60;

            double leftHoursAfterDays 	= totalHours % 24;
            double totalDays 			= (totalHours - leftHoursAfterDays) / 24;

            totalTimeDaysHoursMinuts 	= "Days " + totalDays + " , Hours "
                    + leftHoursAfterDays + " , Minutes " + leftMinutsAfterHours;

        }
        catch (ParseException e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SYSTEM DATE FUNCTIONS | GetTimeDifference"));
        }

        return totalTimeDaysHoursMinuts;
    }

    public static double GetDateDifference(String fromTime, String toTime) {
        double diff = 0;
        try {
            Date _day 	 = new SimpleDateFormat("yyyy-MM-dd").parse(fromTime);
            Date day 	 = new SimpleDateFormat("yyyy-MM-dd").parse(toTime);

            double diffe = day.getTime() - _day.getTime();

            diff = diffe / (60*60*1000*24);


        }
        catch (ParseException e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SYSTEM DATE FUNCTIONS | GetDateDifference"));
        }
        return diff;
    }

    public static double getTimeDiffInMins(String startTime, String CurrentTime) {
        double totalMinuts 		  	= 0;
        double totalHours  		  	= 0;
        double leftMinutsAfterHours	= 0;

        try {
            Date _day 	= new SimpleDateFormat("kk:mm:ss").parse(startTime);
            Date day  	= new SimpleDateFormat("kk:mm:ss").parse(CurrentTime);

            double diff = day.getTime() - _day.getTime() ;

            totalMinuts = diff / (60 * 1000);
            leftMinutsAfterHours = totalMinuts % 60;
            totalHours 	= (totalMinuts - leftMinutsAfterHours) / 60;

        }
        catch (ParseException e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SYSTEM DATE FUNCTIONS | getTimeDiffInMins"));
        }

        return totalHours + (leftMinutsAfterHours)/  60;
    }

    public static String convertDate(String dateToConvert){
        String convertedDate =  "";
        try {
            DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            DateFormat targetFormat	  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");

            Date date				  = originalFormat.parse(dateToConvert);

            convertedDate			  = targetFormat.format(date);
        }
        catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SYSTEM DATE FUNCTIONS | convertDate"));
        }

        return convertedDate;
    }
}
