package epic.ud.elogr.view.CatchData;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.model.CatchDataFishListModel;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.TripMenuActivity;
import epic.ud.elogr.view.adaptor.CatchDataListAdaptor;

public class CatchDataListActivity extends AppCompatActivity {

    TextView setCatchDataListTitleLable;
    RecyclerView setCatchDataList;
    Button backViewCatchDataBtn;
    CatchDataListAdaptor catchDataListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_data_list);
        try {
            Store.INSTANCE.reInitStore(CatchDataListActivity.this);

            initUi();

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Catch Data List Activity"));
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
            Intent departure = new Intent(CatchDataListActivity.this, TripMenuActivity.class);
            startActivity(departure);
        }

        setCatchDataList = (RecyclerView) findViewById(R.id.setCatchDataList);
        backViewCatchDataBtn = (Button) findViewById(R.id.backViewCatchDataBtn);
        backViewCatchDataBtn.setTypeface(tf);
        setCatchDataListTitleLable = (TextView) findViewById(R.id.setCatchDataListTitleLable);
        setCatchDataListTitleLable.setTypeface(tf);


        if (lang.equals("si")) {
            backViewCatchDataBtn.setText(" B<.  ");
            setCatchDataListTitleLable.setText("" + word.get(5).getSi_name());
        }
        if (lang.equals("en")) {
            backViewCatchDataBtn.setText("" + word.get(49).getEn_name());
            setCatchDataListTitleLable.setText("" + word.get(5).getEn_name());
        }
        if (lang.equals("ta")) {
            backViewCatchDataBtn.setText("" + word.get(49).getTm_name());
            setCatchDataListTitleLable.setText("" + word.get(5).getTm_name());
        }

        List<CatchDataFishListModel> catchlist = new ArrayList<>();

        List<Catch> clist = Store.INSTANCE.getAppDatabase().catchDao().getAllByTripId(Store.INSTANCE.getOnTripId());
        for (Catch c : clist) {
            CatchDataFishListModel cdflm = new CatchDataFishListModel();
            cdflm.setCatchid(c.getId());
            cdflm.setGearId(c.getGearId());
            cdflm.setCatchType(c.getCatchType());
            cdflm.setFishType(c.getFishType());
            List<CatchFish> flist = new ArrayList<>();
            flist = Store.INSTANCE.getAppDatabase().catchfishDao().getAllByCatchId(c.getId());
            cdflm.setFishList(flist);
            catchlist.add(cdflm);
        }

       /* String json = new Gson().toJson(clist);
        Log.d("TAG_FLIST_ZISE",""+json);*/
        catchDataListAdaptor = new CatchDataListAdaptor(getApplicationContext(), catchlist);
        setCatchDataList.setLayoutManager(new LinearLayoutManager(this));
        setCatchDataList.setAdapter(catchDataListAdaptor);


        backViewCatchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(CatchDataListActivity.this, TripMenuActivity.class);
                startActivity(intent);
            }
        });


    }

}
