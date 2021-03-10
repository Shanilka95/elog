package epic.ud.elogr.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import epic.ud.elogr.api.Res.FishResponse;
import epic.ud.elogr.api.Res.WordResponse;
import epic.ud.elogr.api.mng.CallManager;
import epic.ud.elogr.api.mng.NetworkHelper;
import epic.ud.elogr.config.StateCodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Udith Perera on 4/8/2020.
 */
public class PrimaryDataController {
    Context c;

    ProgressDialog progressDialog;

    public PrimaryDataController(Context c) {
        this.c = c;

    }

    public void loadFishList(){
        progressDialog = new ProgressDialog(c);
        progressDialog.setTitle("Fish list");
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();

        if (NetworkHelper.isNetworkAvailable(c)) {
        Call<FishResponse> call = CallManager.INSTANCE.getFishCaller().fishList();
        call.enqueue(new Callback<FishResponse>() {
            @Override
            public void onResponse(retrofit2.Call<FishResponse> call, Response<FishResponse> response) {

                assert response.body() != null;
                Log.e("RES", response.body().getStatus() );

                if (!response.body().equals(null) && response.code() == StateCodes.OK && response.body().getStatus().equals("success")) {

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(c, "1- Faild to load, \n Process terminated.", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FishResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(c, "2- Faild to load", Toast.LENGTH_SHORT).show();
            }
        });
        } else {
            progressDialog.dismiss();
            Toast.makeText(c, "Network error", Toast.LENGTH_SHORT).show();

        }


    }

    public  void loadWordList(){
        progressDialog = new ProgressDialog(c);
        progressDialog.setTitle("Word list");
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();

        if (NetworkHelper.isNetworkAvailable(c)) {
            Call<WordResponse> call = CallManager.INSTANCE.getHWordCaller().wordList();
            call.enqueue(new Callback<WordResponse>() {
                @Override
                public void onResponse(retrofit2.Call<WordResponse> call, Response<WordResponse> response) {


                    if (!response.body().equals(null) && response.code() == StateCodes.OK && response.body().getStatus().equals("success")) {

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(c, "3- Faild to load, \n Process terminated.", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<WordResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(c, "4- Faild to load", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(c, "Network error", Toast.LENGTH_SHORT).show();

        }


    }


}
