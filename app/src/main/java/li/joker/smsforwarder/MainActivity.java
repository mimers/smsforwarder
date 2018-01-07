package li.joker.smsforwarder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.ok).setOnClickListener((view) -> {
            String phone = ((EditText) findViewById(R.id.another_phone)).getText().toString();
            SmsUtil.updateTargetPhone(phone);
            SmsUtil.sendSms(phone, this.toString());
            FirebaseAnalytics.getInstance(getApplicationContext()).logEvent("setting_target_number", null);
        });

        ((EditText) findViewById(R.id.another_phone)).setText(SmsUtil.getTargetPhone());
    }
}
