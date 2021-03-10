package epic.ud.elogr.api.caller;


import epic.ud.elogr.api.Res.AuthResponse;
import epic.ud.elogr.api.dto.submitdata.SubmitData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Udith on 24/5/2018.
 */
public interface SubmitCaller {

    @POST("submitdata")
    Call<AuthResponse> submitData(@Body SubmitData submitData);
}
