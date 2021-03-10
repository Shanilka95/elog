package epic.ud.elogr.api.caller;

import epic.ud.elogr.api.Res.FishResponse;
import retrofit2.Call;
import retrofit2.http.GET;


public interface FishCaller {

    @GET("fishlist_api")
    Call<FishResponse> fishList();

}
