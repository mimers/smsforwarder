package li.joker.smsforwarder;

import android.content.Context;
import android.telephony.SmsManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by super on 2018/1/7.
 */

public class SmsUtil {
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
        manager.sendTextMessage(dest, null, body, null, null);
    }
}
