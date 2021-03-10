package epic.ud.elogr.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.util.List;

import epic.ud.elogr.R;
import epic.ud.elogr.api.Res.FishResponse;
import epic.ud.elogr.api.Res.HarbourResponse;
import epic.ud.elogr.api.Res.WordResponse;
import epic.ud.elogr.api.mng.CallManager;
import epic.ud.elogr.api.mng.NetworkHelper;
import epic.ud.elogr.controller.LogFileCreator;
import epic.ud.elogr.db.AppDatabase;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Harbour;
import epic.ud.elogr.db.entity.Word;
import epic.ud.elogr.util.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    LinearLayout tripsBtn, fishBtn;
    TextView startTxt;
    public static AppDatabase appDatabase;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Store.INSTANCE.reInitStore(HomeActivity.this);
//        TextView text = (TextView) findViewById(R.id.textText);
//        text.setTypeface(Store.INSTANCE.getFace());
//        String TSCIIString = APTamil.convertToTamil(APTamil.BAMINI2UNI, "R+iu");
//        text.setText("R+iu".toString());

            appDatabase = Store.INSTANCE.getAppDatabase();
            progressDialog = new ProgressDialog(this);
            getFish();
            String lang = Store.INSTANCE.getLang();
            Typeface tf = Store.INSTANCE.getFace();

            startTxt = findViewById(R.id.startTxt);
            startTxt.setTypeface(tf);

            if (lang.equals("si")) {
                startTxt.setText("pdßldj wdrïN lrkak");
            }
            if (lang.equals("en")) {
                startTxt.setText("Start Trip");
            }
            if (lang.equals("ta")) {
                startTxt.setText("gazj;ij njhlq;Fq;fs;");
            }
            tripsBtn = findViewById(R.id.tripsBtn);
            tripsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent trips = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(trips);

                }
            });

            fishBtn = findViewById(R.id.fishBtn);
            fishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settings = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(settings);
                }
            });


        } catch (Exception e) {
            LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Home Activity"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.logout:
                Store.INSTANCE.getAppDatabase().loginDao().loginOutAll();
//                finish();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                } else {
                    ActivityCompat.finishAffinity(HomeActivity.this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getFish() {
        progressDialog.setTitle("Syncing Data!");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (NetworkHelper.isNetworkAvailable(getApplicationContext())) {
            Call<FishResponse> call = CallManager.INSTANCE.getFishCaller().fishList();
            call.enqueue(new Callback<FishResponse>() {
                @Override
                public void onResponse(Call<FishResponse> call, Response<FishResponse> response) {
                    assert response.body() != null;
                    try {
                        List<Fish> fishList = response.body().getFishlist();
                        appDatabase.fishDao().deleteAll();
                        for (Fish fish : fishList) {
                            appDatabase.fishDao().saveFish(fish);
                        }
                        getWords();

                    } catch (Exception e) {
                        LogFileCreator.Makelog(LogFileCreator.MakeErrorText(e, "Arrival Activity"));
                    }
                }

                @Override
                public void onFailure(Call<FishResponse> call, Throwable t) {
                    Log.e("ERROR", t.getMessage());

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "LoginActivity failed, please enter valid credentials", Toast.LENGTH_LONG).show();
        }


    }

    public void getWords() {

        Call<WordResponse> call = CallManager.INSTANCE.getHWordCaller().wordList();
        call.enqueue(new Callback<WordResponse>() {
            @Override
            public void onResponse(Call<WordResponse> call, Response<WordResponse> response) {
                assert response.body() != null;
                List<Word> wordList = response.body().getWordList();
                appDatabase.wordDao().deleteAll();
                for (Word word : wordList) {
                    appDatabase.wordDao().saveWords(word);
                }
                getHarbours();
            }

            @Override
            public void onFailure(Call<WordResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });

    }

    public void getHarbours() {

        Call<HarbourResponse> call = CallManager.INSTANCE.getHarbourCaller().harbourList();
        call.enqueue(new Callback<HarbourResponse>() {
            @Override
            public void onResponse(Call<HarbourResponse> call, Response<HarbourResponse> response) {
                assert response.body() != null;
                List<Harbour> harbourList = response.body().getHarbourList();
                Log.e("ERROR", response.body().getHarbourList().size() + "");

                appDatabase.harbourDao().deleteAll();
                appDatabase.harbourDao().saveHarbour(new Harbour("-1", "---", "---", "---", "---"));
                for (Harbour harbour : harbourList) {
                    appDatabase.harbourDao().saveHarbour(harbour);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<HarbourResponse> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });

    }


}
