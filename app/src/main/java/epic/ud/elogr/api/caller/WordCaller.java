package epic.ud.elogr.api.caller;

import epic.ud.elogr.api.Res.WordResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WordCaller {

    @GET("appwords_api")
    Call<WordResponse> wordList();
}
