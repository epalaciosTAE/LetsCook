package com.tae.letscook.Utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Eduardo on 02/01/2016.
 */
public class KeyboardUtils {

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
