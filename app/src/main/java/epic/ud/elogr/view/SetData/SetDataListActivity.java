package epic.ud.elogr.view.SetData;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.ShowTripGearModel;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.TripMenuActivity;
import epic.ud.elogr.view.adaptor.SetDataListAdaptor;

public class SetDataListActivity extends AppCompatActivity {

    TextView setSetDataListTitleLable;
    RecyclerView setSetDataList;

    SetDataListAdaptor setDataListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data_list);

        try {

            Store.INSTANCE.reInitStore(SetDataListActivity.this);

            initUi();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "SetDataList Activity"));
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
            Intent departure = new Intent(SetDataListActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }

        List<ShowTripGearModel> tgList = Store.INSTANCE.getAppDatabase().tripGearDao().getSetDataAll_(Store.INSTANCE.getOnTripId());
        Log.d("TAG_L", "-" + tgList.size());

        setSetDataListTitleLable = (TextView) findViewById(R.id.setSetDataListTitleLable);
        setSetDataListTitleLable.setTypeface(tf);
        setSetDataList = (RecyclerView) findViewById(R.id.setSetDataList);


        setDataListAdaptor = new SetDataListAdaptor(getApplicationContext(), tgList);
        setSetDataList.setLayoutManager(new LinearLayoutManager(this));
        setSetDataList.setAdapter(setDataListAdaptor);


        if (lang.equals("si")) {
            setSetDataListTitleLable.setText("" + word.get(13).getSi_name());
        }

        if (lang.equals("ta")) {
            setSetDataListTitleLable.setText("" + word.get(13).getTm_name());
        }

        if (lang.equals("en")) {
            setSetDataListTitleLable.setText("" + word.get(13).getEn_name());
        }

    }
}
