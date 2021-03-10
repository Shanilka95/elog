package epic.ud.elogr.api.mng;


import epic.ud.elogr.api.caller.AuthCaller;
import epic.ud.elogr.api.caller.FishCaller;
import epic.ud.elogr.api.caller.HarbourCaller;
import epic.ud.elogr.api.caller.SubmitCaller;
import epic.ud.elogr.api.caller.WordCaller;
import epic.ud.elogr.config.AppConsts;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Udith on 24/5/2018.
 */
public enum CallManager {

    INSTANCE;
    private Retrofit retrofit;

    private CallManager() {

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        OkHttpClient.Builder httpClient = okHttpClient.newBuilder();
        // httpClient.addNetworkInterceptor(new AuthTokenInterceptor());


        retrofit = new Retrofit.Builder()
                .baseUrl(AppConsts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public AuthCaller getAuthCaller() {
        return retrofit.create(AuthCaller.class);
    }

    public FishCaller getFishCaller() {
        return retrofit.create(FishCaller.class);
    }

    public HarbourCaller getHarbourCaller() {
        return retrofit.create(HarbourCaller.class);
    }

    public WordCaller getHWordCaller() {
        return retrofit.create(WordCaller.class);
    }

    public SubmitCaller getSubmitCaller() {
        return retrofit.create(SubmitCaller.class);
    }

}
