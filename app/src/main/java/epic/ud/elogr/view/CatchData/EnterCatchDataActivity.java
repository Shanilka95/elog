package epic.ud.elogr.view.CatchData;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.CatchDataFishModel;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.SetData.SetDataListActivity;
import epic.ud.elogr.view.TripMenuActivity;
import epic.ud.elogr.view.adaptor.CatchDataFishListAdaptor;

public class EnterCatchDataActivity extends AppCompatActivity {


    TextView fishTypeLable, catchDataLable;
    Spinner fishTypeInput, catchDataTypeInput;
    RecyclerView fishList;
    ListView fistListView;
    Button catchDataSaveBtn;
    CatchDataFishListAdaptor catchDataFishListAdaptor;
    String lineId;
    HashMap fishTypeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_catch_data);

        try {
        Store.INSTANCE.reInitStore(EnterCatchDataActivity.this);

        Intent intent = getIntent();
        if(intent.hasExtra("lineId")){
            lineId = intent.getStringExtra("lineId");
        }else {
            Intent departure = new Intent(EnterCatchDataActivity.this, SetDataListActivity.class);
            startActivity(departure);
        }

        initUi();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "EnterCatch Data Activity"));
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
            Intent departure = new Intent(EnterCatchDataActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }


        fishTypeLable = (TextView) findViewById(R.id.fishTypeLable);fishTypeLable.setTypeface(tf);
        catchDataLable = (TextView) findViewById(R.id.catchDataLable);catchDataLable.setTypeface(tf);




        fishTypeInput = (Spinner) findViewById(R.id.fishTypeInput);
        catchDataTypeInput = (Spinner) findViewById(R.id.catchDataTypeInput);

        fishList = (RecyclerView) findViewById(R.id.fishList);

        List<Fish> fishListData = Store.INSTANCE.getAppDatabase().fishDao().getAll();

        catchDataFishListAdaptor = new CatchDataFishListAdaptor(EnterCatchDataActivity.this, fishListData);
        fishList.setLayoutManager(new LinearLayoutManager(this));
        fishList.setAdapter(catchDataFishListAdaptor);



        catchDataSaveBtn = (Button) findViewById(R.id.catchDataSaveBtn);catchDataSaveBtn.setTypeface(tf);

        fishTypeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String perentId = "" + getKeyX(fishTypeMap, fishTypeInput.getSelectedItem().toString());


                catchDataFishListAdaptor.getFilter().filter(perentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (lang.equals("si")) {
            fishTypeLable.setText("" + word.get(27).getSi_name());
            catchDataLable.setText("" + word.get(5).getSi_name());
            catchDataSaveBtn.setText(" B<.  ");
        }
        if (lang.equals("en")) {
            fishTypeLable.setText("" + word.get(27).getEn_name());
            catchDataLable.setText("" + word.get(5).getEn_name());
            catchDataSaveBtn.setText("" + word.get(7).getEn_name());
        }
        if (lang.equals("ta")) {
            fishTypeLable.setText("" + word.get(27).getTm_name());
            catchDataLable.setText("" + word.get(5).getTm_name());
            catchDataSaveBtn.setText("" + word.get(7).getTm_name());
        }


        List<Fish> flishCat = Store.INSTANCE.getAppDatabase().fishDao().getAllFishCat();
        String[] flishCatArray = new String[flishCat.size()];
        int cycle = 0;
        fishTypeMap = new HashMap<Integer, String>();
        for (Fish f : flishCat) {
            String str = "";
            if (Store.INSTANCE.getLang().equals("si")) {
                str = f.getSi_name();
            }
            if (Store.INSTANCE.getLang().equals("en")) {
                str = f.getEn_name();
            }
            if (Store.INSTANCE.getLang().equals("ta")) {
                str = f.getTm_name();
            }
            fishTypeMap.put(Integer.parseInt(f.getId()), "" + str);
            flishCatArray[cycle] = str;
            cycle++;
        }

        String[] baitList = Store.INSTANCE.getResources().getStringArray(R.array.fishDataEntryTypes);
        ArrayAdapter<String> catchTypesCatAdapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, baitList) {
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
        catchDataTypeInput.setAdapter(catchTypesCatAdapter);

        ArrayAdapter<String> flishCatAdapter = new ArrayAdapter<String>(this, R.layout.spinner_itemx, flishCatArray) {
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

        fishTypeInput.setAdapter(flishCatAdapter);


        catchDataSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formSave();
            }
        });

    }

    public Integer getKeyX(HashMap<Integer, String> map, String value) {
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean formValidate(){
        List<CatchDataFishModel> models = catchDataFishListAdaptor.getCatchDataFishModels();
        if(models != null){
            int emptyCount = 0;
            for (CatchDataFishModel mod : models) {

                if (mod.getFishQty().toString().length()>0 && mod.getFishWeight().toString().length()>0) {

                }else {
                    emptyCount++;
                }
            }

            if(emptyCount == models.size()){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }


    }


    public void formSave() {
        if(formValidate()){

            String catchType = "";
            if (catchDataTypeInput.getSelectedItemPosition() == 0) {
                catchType = "RETAINED";
            }
            if (catchDataTypeInput.getSelectedItemPosition() == 1) {
                catchType = "DISCARDED_DEAD";
            }
            if (catchDataTypeInput.getSelectedItemPosition() == 2) {
                catchType = "DISCARDED_ALIVE'";
            }
            String fishType = "" + getKeyX(fishTypeMap, fishTypeInput.getSelectedItem().toString());
            List<CatchDataFishModel> models = catchDataFishListAdaptor.getCatchDataFishModels();

            Store.INSTANCE.getAppDatabase().catchDao().insertAll(new Catch(Integer.parseInt(lineId), "" + catchType, "" + fishType));
            int lastId = Store.INSTANCE.getAppDatabase().catchDao().getMaxId();

            for (CatchDataFishModel mod : models) {
                Log.d("TAG_TLEN",""+mod.getFishQty().toString().length()+"-"+mod.getFishWeight().toString().length());
                if (mod.getFishQty().toString().length()>0 && mod.getFishWeight().toString().length()>0) {
                    Store.INSTANCE.getAppDatabase().catchfishDao().insertAll(
                            new CatchFish(lastId,
                                    "" + mod.getFishId(),
                                    "" + mod.getFishQty(),
                                    "" + mod.getFishWeight()));
                }else {

                }
            }


            finish();
            Intent intent = new Intent(EnterCatchDataActivity.this, CatchDataActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Please Check the fields..", Toast.LENGTH_SHORT).show();
        }



    }




}
