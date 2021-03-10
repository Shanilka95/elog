package epic.ud.elogr.api.mng;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Udith on 24/5/2018.
 */
public class NetworkHelper {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        return result;
    }

}
