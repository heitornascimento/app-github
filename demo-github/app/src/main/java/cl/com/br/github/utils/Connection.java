package cl.com.br.github.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by heitornascimento on 8/20/16.
 */
public class Connection {

    /**
     * Must be called for every network communication.
     *
     * @param ctx
     * @return
     */
    public static boolean hasValidaConnection(Context ctx) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null) {
                return netInfo.isConnectedOrConnecting();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
