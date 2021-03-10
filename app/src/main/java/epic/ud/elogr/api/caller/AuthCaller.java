package epic.ud.elogr.api.caller;


import epic.ud.elogr.api.Res.AuthResponse;
import epic.ud.elogr.api.dto.AuthDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Udith on 24/5/2018.
 */
public interface AuthCaller {

    @POST("appligin")
    Call<AuthResponse> authenticate(@Body AuthDto model);
}
