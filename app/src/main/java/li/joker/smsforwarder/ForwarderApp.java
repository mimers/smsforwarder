package li.joker.smsforwarder;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by super on 2018/1/6.
 */

public class ForwarderApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmsUtil.context = this;
        Fabric.with(this, new Crashlytics());
        FirebaseAnalytics.getInstance(this).logEvent("app_started", null);
    }
}
