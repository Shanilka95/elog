package epic.ud.elogr.view.GearData;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.TripMenuActivity;

public class GearListActivity extends AppCompatActivity {

    TextView gearDataTitle,
            gearTypeLable,
            mainLineLable,
            branchLineLable,
            noOfHookLable,
            hookTypeLable,
            depthLable,
            baitLable,
            heightOfNetLable,
            lenghtOfNetLable,
            meshSizeLable,
            netMaterialLable,
            noOfNetPiecesLable,
            plyOfNetLable,
            heightOfTheRingNetLable,
            lengthOfTheRingNetLable;
    EditText mainLineInput,
            branchLineInput,
            noOfHookInput,
            depthInput,
            heightOfNetInput,
            heightOfTheRingNetInput,
            lengthOfNetInput,
            meshSizeInput,
            noOfNetPiecesInput,
            plyOfNetInput,
            lengthOfTheRingNetInput;
    Spinner gearTypeInput, hookTypeInput, baitInput, netMaterialInput;
    Button gearDataSaveButton;

    LinearLayout layout_longLine, layout_gillLine, layout_ringLine;

    HashMap<Integer, String> baitListMap, matrialListMap, gearTypeMap, hookTypesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_list);
        try {
            Store.INSTANCE.reInitStore(GearListActivity.this);

            initUi();

            gearTypeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    layoutSwitch();
                    formReset();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            gearDataSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    formVaidate();
                }
            });

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "GearData Activity"));
        }

    }

    public void initUi() {
        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        if (word.size() < 67) {
            Toast.makeText(getApplicationContext(), "Data Loading Incorrect...", Toast.LENGTH_SHORT).show();
            finish();
            Intent departure = new Intent(GearListActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }


        //layouts
        layout_longLine = (LinearLayout) findViewById(R.id.layout_longLine);
        layout_gillLine = (LinearLayout) findViewById(R.id.layout_gillLine);
        layout_ringLine = (LinearLayout) findViewById(R.id.layout_ringLine);


        //init lables
        gearDataTitle = (TextView) findViewById(R.id.gearDataTitle);
        gearDataTitle.setTypeface(tf);
        gearTypeLable = (TextView) findViewById(R.id.gearTypeLable);
        gearTypeLable.setTypeface(tf);
        mainLineLable = (TextView) findViewById(R.id.mainLineLable);
        mainLineLable.setTypeface(tf);
        branchLineLable = (TextView) findViewById(R.id.branchLineLable);
        branchLineLable.setTypeface(tf);
        noOfHookLable = (TextView) findViewById(R.id.noOfHookLable);
        noOfHookLable.setTypeface(tf);
        hookTypeLable = (TextView) findViewById(R.id.hookTypeLable);
        hookTypeLable.setTypeface(tf);
        depthLable = (TextView) findViewById(R.id.depthLable);
        depthLable.setTypeface(tf);
        baitLable = (TextView) findViewById(R.id.baitLable);
        baitLable.setTypeface(tf);

        heightOfNetLable = (TextView) findViewById(R.id.heightOfNetLable);
        heightOfNetLable.setTypeface(tf);
        lenghtOfNetLable = (TextView) findViewById(R.id.lenghtOfNetLable);
        lenghtOfNetLable.setTypeface(tf);
        meshSizeLable = (TextView) findViewById(R.id.meshSizeLable);
        meshSizeLable.setTypeface(tf);
        netMaterialLable = (TextView) findViewById(R.id.netMaterialLable);
        netMaterialLable.setTypeface(tf);
        noOfNetPiecesLable = (TextView) findViewById(R.id.noOfNetPiecesLable);
        noOfNetPiecesLable.setTypeface(tf);
        plyOfNetLable = (TextView) findViewById(R.id.plyOfNetLable);
        plyOfNetLable.setTypeface(tf);

        heightOfTheRingNetLable = (TextView) findViewById(R.id.heightOfTheRingNetLable);
        heightOfTheRingNetLable.setTypeface(tf);
        lengthOfTheRingNetLable = (TextView) findViewById(R.id.lengthOfTheRingNetLable);
        lengthOfTheRingNetLable.setTypeface(tf);


        //init inputs
        gearTypeInput = (Spinner) findViewById(R.id.gearTypeInput);

        mainLineInput = (EditText) findViewById(R.id.mainLineInput);
        branchLineInput = (EditText) findViewById(R.id.branchLineInput);
        noOfHookInput = (EditText) findViewById(R.id.noOfHookInput);
        hookTypeInput = (Spinner) findViewById(R.id.hookTypeInput);
        depthInput = (EditText) findViewById(R.id.depthInput);
        baitInput = (Spinner) findViewById(R.id.baitInput);

        heightOfNetInput = (EditText) findViewById(R.id.heightOfNetInput);
        lengthOfNetInput = (EditText) findViewById(R.id.lengthOfNetInput);
        meshSizeInput = (EditText) findViewById(R.id.meshSizeInput);
        netMaterialInput = (Spinner) findViewById(R.id.netMaterialInput);
        noOfNetPiecesInput = (EditText) findViewById(R.id.noOfNetPiecesInput);
        plyOfNetInput = (EditText) findViewById(R.id.plyOfNetInput);


        heightOfTheRingNetInput = (EditText) findViewById(R.id.heightOfTheRingNetInput);
        lengthOfTheRingNetInput = (EditText) findViewById(R.id.lengthOfTheRingNetInput);

        //init buttons
        gearDataSaveButton = (Button) findViewById(R.id.gearDataSaveButton);
        gearDataSaveButton.setTypeface(tf);


        //init language
        if (lang.equals("si")) {
            gearDataTitle.setText("" + word.get(3).getSi_name());
            gearTypeLable.setText("" + word.get(14).getSi_name());
            mainLineLable.setText("" + word.get(15).getSi_name());
            branchLineLable.setText("" + word.get(16).getSi_name());
            noOfHookLable.setText("" + word.get(17).getSi_name());
            hookTypeLable.setText("" + word.get(18).getSi_name());
            depthLable.setText("" + word.get(19).getSi_name());
            baitLable.setText("" + word.get(20).getSi_name());
            gearDataSaveButton.setText(" B<.  ");

            heightOfNetLable.setText("" + word.get(34).getSi_name());
            lenghtOfNetLable.setText("" + word.get(35).getSi_name());
            meshSizeLable.setText("" + word.get(32).getSi_name());
            netMaterialLable.setText("" + word.get(63).getSi_name());
            noOfNetPiecesLable.setText("" + word.get(36).getSi_name());
            plyOfNetLable.setText("" + word.get(33).getSi_name());

            heightOfTheRingNetLable.setText("" + word.get(38).getSi_name());
            lengthOfTheRingNetLable.setText("" + word.get(37).getSi_name());
        }
        if (lang.equals("ta")) {
            gearDataTitle.setText("" + word.get(3).getTm_name());
            gearTypeLable.setText("" + word.get(14).getTm_name());
            mainLineLable.setText("" + word.get(15).getTm_name());
            branchLineLable.setText("" + word.get(16).getTm_name());
            noOfHookLable.setText("" + word.get(17).getTm_name());
            hookTypeLable.setText("" + word.get(18).getTm_name());
            depthLable.setText("" + word.get(19).getTm_name());
            baitLable.setText("" + word.get(20).getTm_name());
            gearDataSaveButton.setText("" + word.get(7).getTm_name());

            heightOfNetLable.setText("" + word.get(34).getTm_name());
            lenghtOfNetLable.setText("" + word.get(35).getTm_name());
            meshSizeLable.setText("" + word.get(32).getTm_name());
            netMaterialLable.setText("" + word.get(63).getTm_name());
            noOfNetPiecesLable.setText("" + word.get(36).getTm_name());
            plyOfNetLable.setText("" + word.get(33).getTm_name());

            heightOfTheRingNetLable.setText("" + word.get(38).getTm_name());
            lengthOfTheRingNetLable.setText("" + word.get(37).getTm_name());
        }
        if (lang.equals("en")) {
            gearDataTitle.setText("" + word.get(3).getEn_name());
            gearTypeLable.setText("" + word.get(14).getEn_name());
            mainLineLable.setText("" + word.get(15).getEn_name());
            branchLineLable.setText("" + word.get(16).getEn_name());
            noOfHookLable.setText("" + word.get(17).getEn_name());
            hookTypeLable.setText("" + word.get(18).getEn_name());
            depthLable.setText("" + word.get(19).getEn_name());
            baitLable.setText("" + word.get(20).getEn_name());
            gearDataSaveButton.setText("" + word.get(7).getEn_name());

            heightOfNetLable.setText("" + word.get(34).getEn_name());
            lenghtOfNetLable.setText("" + word.get(35).getEn_name());
            meshSizeLable.setText("" + word.get(32).getEn_name());
            netMaterialLable.setText("" + word.get(63).getEn_name());
            noOfNetPiecesLable.setText("" + word.get(36).getEn_name());
            plyOfNetLable.setText("" + word.get(33).getEn_name());

            heightOfTheRingNetLable.setText("" + word.get(38).getEn_name());
            lengthOfTheRingNetLable.setText("" + word.get(37).getEn_name());
        }


        //init spinners

        String[] gearTypeArray = new String[3];
        int cycle = 0;
        gearTypeMap = new HashMap<Integer, String>();
        for (int i = 65; i < 68; i++) {
            String str = "";
            if (Store.INSTANCE.getLang().equals("si")) {
                str = word.get(i).getSi_name();
            }
            if (Store.INSTANCE.getLang().equals("en")) {
                str = word.get(i).getEn_name();
            }
            if (Store.INSTANCE.getLang().equals("ta")) {
                str = word.get(i).getTm_name();
            }
            gearTypeMap.put(Integer.parseInt(word.get(i).getId()), "" + str);
            gearTypeArray[cycle] = str;
            cycle++;
        }

        ArrayAdapter<String> gearTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, gearTypeArray) {
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

        gearTypeInput.setAdapter(gearTypeAdapter);

        String[] baitList = Store.INSTANCE.getResources().getStringArray(R.array.baitTypes);
        baitListMap = new HashMap<Integer, String>();
        for (int i = 0; i < baitList.length; i++) {
            baitListMap.put(i, baitList[i]);
        }

        ArrayAdapter<String> baitListAdapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, baitList) {
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
        baitInput.setAdapter(baitListAdapter);


        String[] matrialList = Store.INSTANCE.getResources().getStringArray(R.array.matrialList);
        matrialListMap = new HashMap<Integer, String>();
        for (int i = 0; i < matrialList.length; i++) {
            baitListMap.put(i, matrialList[i]);
        }

        ArrayAdapter<String> matrialListAdapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, matrialList) {
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
        netMaterialInput.setAdapter(matrialListAdapter);


        String[] hookTypes = Store.INSTANCE.getResources().getStringArray(R.array.hookTypes);
        hookTypesMap = new HashMap<Integer, String>();
        for (int i = 0; i < hookTypes.length; i++) {
            hookTypesMap.put(i, hookTypes[i]);
        }

        ArrayAdapter<String> hookTypesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, hookTypes) {
            Typeface font = Store.INSTANCE.getFace();

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
//                view.setTypeface(font);
                return view;
            }


            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setGravity(Gravity.CENTER);
                view.setPadding(0, 8, 0, 8);
//                view.setTypeface(font);
                return view;
            }
        };
        hookTypeInput.setAdapter(hookTypesAdapter);

        layoutSwitch();
    }


    public void layoutSwitch() {
        String itemTxt = gearTypeInput.getSelectedItem().toString();
        if (gearTypeInput.getSelectedItemPosition() == 0) {
            layout_longLine.setVisibility(View.VISIBLE);
            layout_gillLine.setVisibility(View.GONE);
            layout_ringLine.setVisibility(View.GONE);
        }
        if (gearTypeInput.getSelectedItemPosition() == 1) {
            layout_longLine.setVisibility(View.GONE);
            layout_gillLine.setVisibility(View.VISIBLE);
            layout_ringLine.setVisibility(View.GONE);
        }
        if (gearTypeInput.getSelectedItemPosition() == 2) {
            layout_longLine.setVisibility(View.GONE);
            layout_gillLine.setVisibility(View.GONE);
            layout_ringLine.setVisibility(View.VISIBLE);
        }
    }

    public void formReset() {
        mainLineInput.setText("");
        branchLineInput.setText("");
        noOfHookInput.setText("");
        hookTypeInput.setSelection(0);
        depthInput.setText("");
        baitInput.setSelection(0);

        heightOfNetInput.setText("");
        lengthOfNetInput.setText("");
        meshSizeInput.setText("");
        netMaterialInput.setSelection(0);
        noOfNetPiecesInput.setText("");
        plyOfNetInput.setText("");


        heightOfTheRingNetInput.setText("");
        lengthOfTheRingNetInput.setText("");

    }


    public void formVaidate() {
        String itemTxt = gearTypeInput.getSelectedItem().toString();
        if (gearTypeInput.getSelectedItemPosition() == 0) {
            if (mainLineInput.getText().toString().trim().length() == 0) {
                mainLineInput.setError("Please Fill This Field");
                return;
            } else if (branchLineInput.getText().toString().trim().length() == 0) {
                branchLineInput.setError("Please Fill This Field");
                return;
            } else if (noOfHookInput.getText().toString().trim().length() == 0) {
                noOfHookInput.setError("Please Fill This Field");
                return;
            } else if (hookTypeInput.getSelectedItemPosition() == 0) {
                Toast.makeText(getApplicationContext(), "Please Select Hook", Toast.LENGTH_SHORT).show();
                return;
            } else if (depthInput.getText().toString().trim().length() == 0) {
                mainLineInput.setError("Please Fill This Field");
                return;
            } else if (baitInput.getSelectedItemPosition() == 0) {
                Toast.makeText(getApplicationContext(), "Please Select Hook", Toast.LENGTH_SHORT).show();
                return;
            } else {
                formSave();
            }
        }
        if (gearTypeInput.getSelectedItemPosition() == 1) {


            if (heightOfNetInput.getText().toString().trim().length() == 0) {
                heightOfNetInput.setError("Please Fill This Field");
                return;
            } else if (lengthOfNetInput.getText().toString().trim().length() == 0) {
                lengthOfNetInput.setError("Please Fill This Field");
                return;
            } else if (meshSizeInput.getText().toString().trim().length() == 0) {
                meshSizeInput.setError("Please Fill This Field");
                return;
            } else if (netMaterialInput.getSelectedItemPosition() == 0) {
                Toast.makeText(getApplicationContext(), "Please Select Material", Toast.LENGTH_SHORT).show();
                return;
            } else if (noOfNetPiecesInput.getText().toString().trim().length() == 0) {
                noOfNetPiecesInput.setError("Please Fill This Field");
                return;
            } else if (plyOfNetInput.getText().toString().trim().length() == 0) {
                plyOfNetInput.setError("Please Fill This Field");
                return;
            } else {
                formSave();
            }

        }
        if (gearTypeInput.getSelectedItemPosition() == 2) {

            if (heightOfTheRingNetInput.getText().toString().trim().length() == 0) {
                heightOfTheRingNetInput.setError("Please Fill This Field");
                return;
            } else if (lengthOfTheRingNetInput.getText().toString().trim().length() == 0) {
                lengthOfTheRingNetInput.setError("Please Fill This Field");
                return;
            } else {
                formSave();
            }


        }
    }

    public Integer getKeyX(HashMap<Integer, String> map, String value) {
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


    private void formSave() {
        int lastTripId = Store.INSTANCE.getOnTripId();
        Log.i("TAG_GL_TID", "" + lastTripId);
        Log.i("POS_X", "" + gearTypeInput.getSelectedItemPosition());
        String gearNameTxt = "LONGLINE";
        if (gearTypeInput.getSelectedItemPosition() == 0) {
            gearNameTxt = "LONGLINE";
        }
        if (gearTypeInput.getSelectedItemPosition() == 1) {
            gearNameTxt = "GILLNET";
        }
        if (gearTypeInput.getSelectedItemPosition() == 2) {
            gearNameTxt = "RING NET";
        }
        String netMat = "0";
        if (netMaterialInput.getSelectedItemPosition() == 1) {
            netMat = "1";
        }
        if (netMaterialInput.getSelectedItemPosition() == 2) {
            netMat = "2";
        }
        if (netMaterialInput.getSelectedItemPosition() == 3) {
            netMat = "3";
        }
        TripGear tripGear = new TripGear(
                lastTripId,
                "" + getKeyX(gearTypeMap, gearTypeInput.getSelectedItem().toString()),
                gearNameTxt,
                mainLineInput.getText().toString(),
                branchLineInput.getText().toString(),
                noOfHookInput.getText().toString(),
                hookTypeInput.getSelectedItemPosition() == 0 ? "" : "" + getKeyX(hookTypesMap, hookTypeInput.getSelectedItem().toString()),
                depthInput.getText().toString(),
                baitInput.getSelectedItemPosition() == 0 ? "" : "" + getKeyX(baitListMap, baitInput.getSelectedItem().toString()),
//                netMaterialInput.getSelectedItemPosition() == 0 ? "" : ""+getKeyX(matrialListMap, netMaterialInput.getSelectedItem().toString()),
                netMat,
                meshSizeInput.getText().toString(),
                plyOfNetInput.getText().toString(),
                heightOfNetInput.getText().toString(),
                lengthOfNetInput.getText().toString(),
                noOfNetPiecesInput.getText().toString(),
                lengthOfTheRingNetInput.getText().toString(),
                heightOfTheRingNetInput.getText().toString());
        Store.INSTANCE.getAppDatabase().tripGearDao().insert(tripGear);
        formReset();
        finish();
        Intent departure = new Intent(GearListActivity.this, TripMenuActivity.class);
        startActivity(departure);
    }

}
