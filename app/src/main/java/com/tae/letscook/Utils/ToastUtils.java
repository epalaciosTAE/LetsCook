package com.tae.letscook.Utils;

import android.content.Context;
import android.widget.Toast;

import retrofit.RetrofitError;

/**
 * Created by Eduardo on 17/01/2016.
 */
public class ToastUtils {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastErrorInRetrofit(Context context, String msg, RetrofitError error) {
        Toast.makeText(context, msg + error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
