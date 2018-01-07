package li.joker.smsforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by super on 2018/1/6.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(TAG, "incoming " + intent);
            SmsMessage[] smss = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            SmsMessage sms = smss[0];
            StringBuilder stringBuilder = new StringBuilder();
            for (SmsMessage s : smss) {
                stringBuilder.append(s.getMessageBody());
            }
            String body = stringBuilder.toString();
            Log.i(TAG, "onReceive: " + body);
            String displayName = findContactNameByNumber(sms.getOriginatingAddress(), context);
            String target = SmsUtil.getTargetPhone();
            if (!TextUtils.isEmpty(target)) {
                SmsUtil.sendSms(target, body + "\nfrom " + sms.getOriginatingAddress() + " " + displayName);
            } else {
                Log.e(TAG, "onReceive:未设置号码");
                FirebaseAnalytics.getInstance(context).logEvent("no_target_settled", null);
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
            Crashlytics.logException(ignore);
        }
    }

    String findContactNameByNumber(String number, Context context) {
        String displayName = "";
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor c = context.getContentResolver().query(lookupUri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        try {
            if (c != null) {
                c.moveToFirst();
                displayName = c.getString(0);
                Log.i(TAG, "onReceive: from name:" + displayName);
            }
        } catch (Exception ignore) {
            Crashlytics.logException(ignore);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return displayName;
    }
}
