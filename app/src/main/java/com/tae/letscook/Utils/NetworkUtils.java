package com.tae.letscook.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import retrofit.RetrofitError;

/**
 * Created by Eduardo on 20/01/2016.
 */
public class NetworkUtils {

    public static final String NO_NETWORK_AVAILABLE = "It looks that the network is not working.";
    public static final String CONNECTED_VIA = "Connected via:";

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(context, CONNECTED_VIA + activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(context, CONNECTED_VIA + activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            Toast.makeText(context, NO_NETWORK_AVAILABLE, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void handleRestAdapterFailure(Context context, RetrofitError error) {
        if (error.getKind().equals(RetrofitError.Kind.HTTP)) {
            ToastUtils.showToastErrorInRetrofit(context, "Http error: ", error);
        } else if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            ToastUtils.showToastErrorInRetrofit(context, "Network error: ", error);
        } else if (error.getKind().equals(RetrofitError.Kind.CONVERSION)) {
            ToastUtils.showToastErrorInRetrofit(context, "Conversion error: ", error);
        } else {
            ToastUtils.showToastErrorInRetrofit(context, "Unknown error: ", error);
        }
    }
}
