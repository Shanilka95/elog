package epic.ud.elogr.view.TripData;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Harbour;
import epic.ud.elogr.db.entity.Trip;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.HomeActivity;
import epic.ud.elogr.view.TripMenuActivity;

//import android.icu.util.Calendar;

public class DepartureActivity extends AppCompatActivity {
    View view;
    TextView depatureTitleLable, depatureDateTimeLable, depatureHarbourLable, depatureRemarkLable;
    EditText depatureDateTimeInput, depatureRemarkInput;
    Spinner depatureHarbourInput;
    Button btnDepNext;

    List<Harbour> harbourList;
    HashMap<Integer, String> harbourMap;
    String[] harbourArray;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog dp;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);
        try {

            view = this.getWindow().getDecorView().findViewById(R.id.departure_layout);
            Store.INSTANCE.reInitStore(DepartureActivity.this);
            initUi();
            setSpinnerData();
            openDraf();
        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Departure Activity"));
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
            Intent departure = new Intent(DepartureActivity.this, HomeActivity.class);
            startActivity(departure);
        }


        depatureTitleLable = (TextView) findViewById(R.id.depatureTitleLable);
        depatureTitleLable.setTypeface(tf);
        depatureDateTimeLable = (TextView) findViewById(R.id.depatureDateTimeLable);
        depatureDateTimeLable.setTypeface(tf);
        depatureHarbourLable = (TextView) findViewById(R.id.depatureHarbourLable);
        depatureHarbourLable.setTypeface(tf);
        depatureRemarkLable = (TextView) findViewById(R.id.depatureRemarkLable);
        depatureRemarkLable.setTypeface(tf);

        depatureDateTimeInput = (EditText) findViewById(R.id.depatureDateTimeInput);
        depatureDateTimeInput.setText("" + setDate(null));
        depatureRemarkInput = (EditText) findViewById(R.id.depatureRemarkInput);
        depatureHarbourInput = (Spinner) findViewById(R.id.depatureHarbourInput);

        btnDepNext = findViewById(R.id.btnDepNext);
        btnDepNext.setTypeface(tf);


        if (lang.equals("si")) {
            depatureTitleLable.setText("msg;ajk");
            depatureDateTimeLable.setText("" + word.get(1).getSi_name());
            depatureHarbourLable.setText("" + word.get(2).getSi_name());
            depatureRemarkLable.setText("" + word.get(53).getSi_name());
            btnDepNext.setText(" B<.  ");
        }
        if (lang.equals("en")) {
            depatureTitleLable.setText("Departure ");
            depatureDateTimeLable.setText("" + word.get(1).getEn_name());
            depatureHarbourLable.setText("" + word.get(2).getEn_name());
            depatureRemarkLable.setText("" + word.get(53).getEn_name());
            btnDepNext.setText("" + word.get(54).getEn_name());
        }
        if (lang.equals("ta")) {
            depatureTitleLable.setText("Gwg;gLk; ");
            depatureDateTimeLable.setText("" + word.get(1).getTm_name());
            depatureHarbourLable.setText("" + word.get(2).getTm_name());
            depatureRemarkLable.setText("" + word.get(53).getTm_name());
            btnDepNext.setText("" + word.get(54).getTm_name());
        }

        depatureDateTimeInput.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int inType = depatureDateTimeInput.getInputType(); // backup the input type
                depatureDateTimeInput.setInputType(InputType.TYPE_NULL); // disable soft input
                depatureDateTimeInput.onTouchEvent(event); // call native handler
                //startGpsTxt.setInputType(inType); // restore input type

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (depatureDateTimeInput.getRight() - depatureDateTimeInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        initDate(depatureDateTimeInput);
                        return true;
                    }
                }
                return false;
            }
        });
        btnDepNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                view.setPadding(0, 8, 0, 8);
                view.setTypeface(font);
                return view;
            }
        };
        depatureHarbourInput.setAdapter(adapter);
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
                } else {
                    e.setText("" + setDate(myCalendar));
                    timePicker(e);
                }

            }

        };

        new DatePickerDialog(DepartureActivity.this, date, myCalendar
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
        e.setText("" + etxt + " , " + formattedDate);
        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DepartureActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker timePicker, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, hourOfDay, minute, 0);
                long timeInMillis = calendar.getTimeInMillis();
                java.text.DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss a");
                Date date = new Date();
                date.setTime(timeInMillis);
                e.setText("" + etxt + " , " + dateFormatter.format(date));

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
            String formattedDate = dateF.format(date);
            return formattedDate;
        }
    }

    public void openDraf() {
        List<Trip> trip = Store.INSTANCE.getAppDatabase().tripDao().getAllDraf();
        if (trip != null) {
            if (trip.get(0).getDepartureDate() != null) {
                depatureDateTimeInput.setText("" + trip.get(0).getDepartureDate());
            }
            if (trip.get(0).getDepartureRemarks() != null) {
                depatureRemarkInput.setText("" + trip.get(0).getDepartureRemarks());
            }

            if (trip.get(0).getDepartureHarbour() != null) {
                String harb = harbourMap.get(Integer.parseInt(trip.get(0).getDepartureHarbour()));
                Log.i("TAG_OPEN", "+" + harb);
                int p = getIndex(harbourArray, harb);
                if (p != -1) depatureHarbourInput.setSelection(p);
            }
        }

    }


    public void formValidate() {
        if (depatureDateTimeInput.getText().toString().trim().length() == 0) {
            snackMessage("Please select a Departure Date");
            return;
        } else if (depatureHarbourInput.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please select harbour", Toast.LENGTH_SHORT).show();
            return;
        } else if (depatureRemarkInput.getText().toString().trim().length() == 0) {
            snackMessage("Please enter a Remark");
            return;
        } else formSave();

    }

    void snackMessage(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void formSave() {
        int lineId = Store.INSTANCE.getOnTripId();
        Store.INSTANCE.getAppDatabase().tripDao().updateDeparture(
                "" + lineId,
                "" + depatureDateTimeInput.getText().toString(),
                depatureHarbourInput.getSelectedItemPosition() == 0 ? "" : "" + getKeyX(harbourMap, depatureHarbourInput.getSelectedItem().toString()),
                "" + depatureRemarkInput.getText().toString());
        Intent departure = new Intent(DepartureActivity.this, TripMenuActivity.class);
        startActivity(departure);


    }
}
