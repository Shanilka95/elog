package epic.ud.elogr.view.CatchData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.util.Store;
import epic.ud.elogr.view.HomeActivity;
import epic.ud.elogr.view.SetData.SetDataListActivity;
import epic.ud.elogr.view.TripMenuActivity;

public class CatchDataActivity extends AppCompatActivity {

    LinearLayout btnEnterData, btnViewData;
    Button backCatchDataBtn;
    TextView enterTxt, updateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_data);

        try {
        Store.INSTANCE.reInitStore(CatchDataActivity.this);

        btnEnterData = findViewById(R.id.enterCatchDataBtn);
        btnViewData = findViewById(R.id.viewCatchDataBtn);
        backCatchDataBtn = findViewById(R.id.backCatchDataBtn);

        btnEnterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatchDataActivity.this, SetDataListActivity.class);
                startActivity(intent);
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatchDataActivity.this, CatchDataListActivity.class);
                startActivity(intent);
            }
        });

        backCatchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatchDataActivity.this, TripMenuActivity.class);
                startActivity(intent);
            }
        });

        enterTxt = findViewById(R.id.enterTxt);
        updateTxt = findViewById(R.id.updateTxt);

        List<Word> word = Store.INSTANCE.getAppDatabase().wordDao().getAll();
        String lang = Store.INSTANCE.getLang();
        Typeface tf = Store.INSTANCE.getFace();
        Log.i("TAX_LANG", "" + lang);
        if(word.size()<67){
            Toast.makeText(getApplicationContext(), "Data Loading Incorrect...", Toast.LENGTH_SHORT).show();
            finish();
            Intent departure = new Intent(CatchDataActivity.this, HomeActivity.class);
            startActivity(departure);
        }else {
            enterTxt = findViewById(R.id.enterTxt);enterTxt.setTypeface(tf);
            updateTxt= findViewById(R.id.updateTxt);updateTxt.setTypeface(tf);
            backCatchDataBtn.setTypeface(tf);


            if (lang.equals("si")) {
                enterTxt.setText("" + word.get(44).getSi_name());
                updateTxt.setText("" + word.get(45).getSi_name());
                backCatchDataBtn.setText(" B<.  ");
            }
            if (lang.equals("en")) {
                enterTxt.setText("" + word.get(44).getEn_name());
                updateTxt.setText("" + word.get(45).getEn_name());
                backCatchDataBtn.setText("" + word.get(49).getEn_name());
            }
            if (lang.equals("ta")) {
                enterTxt.setText("" + word.get(44).getTm_name());
                updateTxt.setText("" + word.get(45).getTm_name());
                backCatchDataBtn.setText("" + word.get(49).getTm_name());
            }

        }

        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Catch Data Activity"));
        }

    }
}
