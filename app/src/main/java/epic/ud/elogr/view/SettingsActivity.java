package epic.ud.elogr.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Trip;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.TripData.DepartureActivity;

public class SettingsActivity extends AppCompatActivity {
    View view;
    Button btnSubmit;
    EditText contactNo, vesselId, skipperId, licenseNo;
    RadioGroup rgLanguage;
    RadioButton langRadio;
    String regExMobile = "^[0-9]{10}$";
    String vesselIdPattern = "^(IMULA)([0-9]{4})(?:BCO|CBO|CHW|GLE|JFN|KCH|KLT|KMN|MNR|MLT|MTR|NBO|PTM|TCO|TLE)";
    String skipperIdPattern = "^(?:SL|SK)([0-9]{4})(?:BCO|CBO|CHW|GLE|JFN|KCH|KLT|KMN|MNR|MLT|MTR|NBO|PTM|TCO|TLE)";


    TextView setupTitleLable, contNoInputLable, edtVesselLable, edtSkipperLable, edtLicenseLable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        try {


            view = this.getWindow().getDecorView().findViewById(R.id.settings_layout);
            Store.INSTANCE.reInitStore(SettingsActivity.this);
            init();
            createDraft();
            //saveSettings();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Setting Activity"));
        }

    }

    void init() {
        contactNo = findViewById(R.id.edtContact);
        vesselId = findViewById(R.id.edtVessel);
        skipperId = findViewById(R.id.edtSkipper);
        licenseNo = findViewById(R.id.edtLicense);
        rgLanguage = findViewById(R.id.rgLanguage);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });


        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();

        setupTitleLable = findViewById(R.id.setupTitleLable);
        setupTitleLable.setTypeface(tf);
        contNoInputLable = findViewById(R.id.contNoInputLable);
        contNoInputLable.setTypeface(tf);
        edtVesselLable = findViewById(R.id.edtVesselLable);
        edtVesselLable.setTypeface(tf);
        edtSkipperLable = findViewById(R.id.edtSkipperLable);
        edtSkipperLable.setTypeface(tf);
        edtLicenseLable = findViewById(R.id.edtLicenseLable);
        edtLicenseLable.setTypeface(tf);
        btnSubmit.setTypeface(tf);

        if (lang.equals("si")) {
            setupTitleLable.setText("mßYS,l ieliqu");
            contNoInputLable.setText("ÿrl:k wxlh");
            edtVesselLable.setText("hd;ardj");
            edtSkipperLable.setText("kdhlhd");
            edtLicenseLable.setText("n,m;% wxlh");
            btnSubmit.setText(" B<.  ");
        }
        if (lang.equals("en")) {
            setupTitleLable.setText("User Setting");
            contNoInputLable.setText("Telephone no");
            edtVesselLable.setText("Vessel");
            edtSkipperLable.setText("Skipper");
            edtLicenseLable.setText("License No");
            btnSubmit.setText("SUBMIT");
        }
        if (lang.equals("ta")) {
            setupTitleLable.setText("gadu; mikg;G");
            contNoInputLable.setText("njாiyNgrp vz;");
            edtVesselLable.setText("fg;gy;");
            edtSkipperLable.setText("Nfg;ld;");
            edtLicenseLable.setText("cupkk; ,y;iy");
            btnSubmit.setText("rku;g;gpf;fTk;");

        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please Complete User Settings", Toast.LENGTH_SHORT).show();
    }


    public void saveSettings() {
        if (formValidated()) {
            Intent trips = new Intent(SettingsActivity.this, DepartureActivity.class);
            if (Store.INSTANCE.getAppDatabase().tripDao().getAllDraf().size() == 0) {
                Store.INSTANCE.getAppDatabase().tripDao().insert(
                        new Trip(
                                "" + contactNo.getText().toString(),
                                "",
                                "" + vesselId.getText().toString(),
                                "" + skipperId.getText().toString(),
                                "" + licenseNo.getText().toString(),
                                "DRAF"
                        ));
                Toast.makeText(getApplicationContext(), "Trip Draft Created", Toast.LENGTH_SHORT).show();
                startActivity(trips);
            } else {
                Store.INSTANCE.getAppDatabase().tripDao().updateDraft(
                        "" + Store.INSTANCE.getAppDatabase().tripDao().getAllDraf().get(0).getTripId(),
                        "" + contactNo.getText().toString(),
                        "",
                        "" + vesselId.getText().toString(),
                        "" + skipperId.getText().toString(),
                        "" + licenseNo.getText().toString()
                );
                Toast.makeText(getApplicationContext(), "Trip Draft Updated", Toast.LENGTH_SHORT).show();
                startActivity(trips);
            }
        }


    }

    public boolean formValidated() {
        if (contactNo.getText().toString().isEmpty() || !contactNo.getText().toString().matches(regExMobile)) {
            snackMessage("Please enter a valid Phone Number");
            return false;
        }
//        else if (rgLanguage.getCheckedRadioButtonId() == -1) {
//            snackMessage("Please select a Language");
//            return false;
//        }
        else if (vesselId.getText().toString().isEmpty() || !vesselId.getText().toString().matches(vesselIdPattern)) {
            snackMessage("Please enter a Valid Boat Number");
            vesselId.setHint("eg:-IMULA0000CBO");
            return false;
        } else if (skipperId.getText().toString().isEmpty() || !skipperId.getText().toString().matches(skipperIdPattern)) {
            snackMessage("Please enter a valid Skipper Id");
            skipperId.setHint("eg:-SL0000CBO");
            return false;
        } else if (licenseNo.getText().toString().isEmpty()) {
            snackMessage("Please enter a valid License Number");
            return false;
        } else return true;
    }

    void snackMessage(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    public void createDraft() {
        if (Store.INSTANCE.getAppDatabase().tripDao().getAllDraf().size() == 0) {
            Store.INSTANCE.getAppDatabase().tripDao().insert(new Trip("DRAF"));
            Store.INSTANCE.setOnTripId(Store.INSTANCE.getAppDatabase().tripDao().getMaxId());
            Toast.makeText(getApplicationContext(), "Trip Draft Created", Toast.LENGTH_SHORT).show();
        } else {
            openDraf();
            Toast.makeText(getApplicationContext(), "Trip Draft Continue", Toast.LENGTH_SHORT).show();
            Store.INSTANCE.setOnTripId(Store.INSTANCE.getAppDatabase().tripDao().getAllDrafMaxId());
        }

    }

    public void openDraf() {
        List<Trip> trip = Store.INSTANCE.getAppDatabase().tripDao().getAllDraf();
        if (trip != null) {
            Log.i("TAG_OPEN", "" + trip.get(0).getTripId());
            contactNo.setText("" + (trip.get(0).getContact() == null ? "" : trip.get(0).getContact()));
            vesselId.setText("" + (trip.get(0).getVesselId() == null ? "" : trip.get(0).getVesselId()));
            skipperId.setText("" + (trip.get(0).getSkipperId() == null ? "" : trip.get(0).getSkipperId()));
            licenseNo.setText("" + (trip.get(0).getLicenseNo() == null ? "" : trip.get(0).getLicenseNo()));
        }

    }
}
