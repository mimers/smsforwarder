package li.joker.smsforwarder;

import android.app.Application;

/**
 * Created by super on 2018/1/6.
 */

public class ForwarderApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmsUtil.context = this;
    }
}
