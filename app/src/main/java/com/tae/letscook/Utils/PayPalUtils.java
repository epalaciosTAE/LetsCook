package com.tae.letscook.Utils;

import android.net.Uri;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.tae.letscook.constants.PaypalConstants;

/**
 * Created by Eduardo on 27/01/2016.
 */
public class PayPalUtils {

    public  static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PaypalConstants.CONFIG_ENVIRONMENT)
            .clientId(PaypalConstants.CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Lets Cook!")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
}
