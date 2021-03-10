package epic.ud.elogr.api.mng;


import java.io.IOException;

import epic.ud.elogr.config.AppConsts;
import epic.ud.elogr.util.Store;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Udith on 24/5/2018.
 */

public class AuthTokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader(AppConsts.API_KEY, "TOKEN FROM STORE");
        return chain.proceed(builder.build());
    }
}
