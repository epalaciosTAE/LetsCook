package com.tae.letscook.Utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Eduardo on 27/01/2016.
 */
public class DetectTabletUtils {

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
