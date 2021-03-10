package epic.ud.elogr.view.CatchData;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.CatchDataFishModel;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.TripMenuActivity;
import epic.ud.elogr.view.adaptor.CatchDataFishListUpdateAdaptor;

public class UpdateCatchDataActivity extends AppCompatActivity {


    TextView fishTypeLable, catchDataLable;
    Spinner fishTypeInput, catchDataTypeInput;
    RecyclerView fishList;
    Button catchDataSaveBtn;
    CatchDataFishListUpdateAdaptor catchDataFishListAdaptor;
    String lineId;
    HashMap fishTypeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_catch_data);

        try {
            Store.INSTANCE.reInitStore(UpdateCatchDataActivity.this);

            Intent intent = getIntent();
            if (intent.hasExtra("lineId")) {
                lineId = intent.getStringExtra("lineId");
            } else {
                Intent departure = new Intent(UpdateCatchDataActivity.this, CatchDataListActivity.class);
                startActivity(departure);
            }
            initUi();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "UpdateCatch Data Activity"));
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
            Intent departure = new Intent(UpdateCatchDataActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }


        fishTypeLable = (TextView) findViewById(R.id.fishTypeLable);
        catchDataLable = (TextView) findViewById(R.id.catchDataLable);
        catchDataLable.setTypeface(tf);


        fishTypeInput = (Spinner) findViewById(R.id.fishTypeInput);
        fishTypeInput.setEnabled(false);
        catchDataTypeInput = (Spinner) findViewById(R.id.catchDataTypeInput);
        catchDataTypeInput.setEnabled(false);

        fishList = (RecyclerView) findViewById(R.id.fishList);

        List<CatchFish> fishListData = Store.INSTANCE.getAppDatabase().catchfishDao().getAllByCatchId(Integer.parseInt(lineId));

        catchDataFishListAdaptor = new CatchDataFishListUpdateAdaptor(UpdateCatchDataActivity.this, fishListData);
        fishList.setLayoutManager(new LinearLayoutManager(this));
        fishList.setAdapter(catchDataFishListAdaptor);


        catchDataSaveBtn = (Button) findViewById(R.id.catchDataSaveBtn);
        catchDataSaveBtn.setTypeface(tf);

        if (lang.equals("si")) {
            catchDataSaveBtn.setText(" B<.  ");
            catchDataLable.setText("" + word.get(5).getSi_name());
        }
        if (lang.equals("en")) {
            catchDataSaveBtn.setText("" + word.get(7).getEn_name());
            catchDataLable.setText("" + word.get(5).getEn_name());
        }
        if (lang.equals("ta")) {
            catchDataSaveBtn.setText("" + word.get(7).getTm_name());
            catchDataLable.setText("" + word.get(5).getTm_name());
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


    public void formSave() {
        List<CatchDataFishModel> models = catchDataFishListAdaptor.getCatchDataFishModels();


        for (CatchDataFishModel mod : models) {
            Log.d("TAG_TLEN", "" + mod.getFishQty().toString().length() + "-" + mod.getFishWeight().toString().length());

            Store.INSTANCE.getAppDatabase().catchfishDao().updateById(mod.getId(), mod.getFishQty(), mod.getFishWeight());

        }

        finish();
        Intent intent = new Intent(UpdateCatchDataActivity.this, CatchDataListActivity.class);
        startActivity(intent);


    }


}
