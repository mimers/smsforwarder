package li.joker.smsforwarder;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by super on 2018/1/7.
 */

public class SmsUtil {
    private static final String TAG = "SmsUtil";
    static Context context;
    private static final String SP_NAME = "phone";
    private static final String SP_KEY = "number";

    public static void updateTargetPhone(String number) {
        context.getSharedPreferences(SP_NAME, MODE_PRIVATE).edit().putString(SP_KEY, number).apply();
    }

    public static String getTargetPhone() {
        return context.getSharedPreferences(SP_NAME, MODE_PRIVATE).getString(SP_KEY, "");
    }

    public static void sendSms(String dest, String body) {
        SmsManager manager = SmsManager.getDefault();
        try {
            manager.sendMultipartTextMessage(dest, null, manager.divideMessage(body), null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        Log.i(TAG, "sendSms to " + dest + " :" + body);
        FirebaseAnalytics.getInstance(context).logEvent("send_sms", null);
    }
}
