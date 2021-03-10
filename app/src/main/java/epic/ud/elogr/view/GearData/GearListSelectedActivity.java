package epic.ud.elogr.view.GearData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.ShowTripGearModel;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.SetData.SetDataActivity;
import epic.ud.elogr.view.TripMenuActivity;
import epic.ud.elogr.view.adaptor.GearListSelectedAdaptor;
import epic.ud.elogr.view.adaptor.SetDataRecycleAdaptor;

public class GearListSelectedActivity extends AppCompatActivity {

    TextView setGearListTitleLable;
    RecyclerView setGearList;

    GearListSelectedAdaptor gearListSelectedAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_gear_list_selected);
        Store.INSTANCE.reInitStore(GearListSelectedActivity.this);

        initUi();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SelectedGearList Activity"));
        }

    }


    public void initUi(){
        //load word list and lang
        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        if(word.size()<67){
            Toast.makeText(getApplicationContext(), "Data Loading Incorrect...", Toast.LENGTH_SHORT).show();
            finish();
            Intent departure = new Intent(GearListSelectedActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }

        List<ShowTripGearModel> tgList = Store.INSTANCE.getAppDatabase().tripGearDao().getAll_(Store.INSTANCE.getOnTripId());
      /*  Log.d("TAG_L", "-"+tgList.size());
        Log.d("TAG_FACE", "-"+tf.equals(null));*/

        setGearListTitleLable = (TextView)findViewById(R.id.setGearListTitleLable);
        setGearListTitleLable.setTypeface(tf);
        setGearList = (RecyclerView)findViewById(R.id.setGearList);


        gearListSelectedAdaptor = new GearListSelectedAdaptor(getApplicationContext(), tgList);
        setGearList.setLayoutManager(new LinearLayoutManager(this));
        setGearList.setAdapter(gearListSelectedAdaptor);


        if (lang.equals("si")) {
            setGearListTitleLable.setText(""+word.get(13).getSi_name());
        }

        if (lang.equals("ta")) {
            setGearListTitleLable.setText(""+word.get(13).getTm_name());
        }

        if (lang.equals("en")) {
            setGearListTitleLable.setText(""+word.get(13).getEn_name());
        }

    }
}
