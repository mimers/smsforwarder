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
            for (SmsMessage sms : smss) {
                String body = sms.getDisplayMessageBody();
                Log.i(TAG, "onReceive: " + body);
                Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(sms.getDisplayOriginatingAddress()));
                Log.i(TAG, "onReceive: from  number:" + sms.getDisplayOriginatingAddress());
                String displayName = "";
                Cursor c = context.getContentResolver().query(lookupUri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
                try {
                    if (c != null) {
                        c.moveToFirst();
                        displayName = c.getString(0);
                        Log.i(TAG, "onReceive: from name:" + displayName);
                    }

                } catch (Exception ignore) {

                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                String target = SmsUtil.getTargetPhone();
                if (!TextUtils.isEmpty(target)) {
                    SmsUtil.sendSms(target, body + "\nfrom " + sms.getDisplayOriginatingAddress() + " " + displayName);
                } else {
                    Log.e(TAG, "onReceive:未设置号码");
                }
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }
}
