package epic.ud.elogr.api.caller;

import epic.ud.elogr.api.Res.HarbourResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HarbourCaller {

    @GET("gethaborlist_api")
    Call<HarbourResponse> harbourList();

}
