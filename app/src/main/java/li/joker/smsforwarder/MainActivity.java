package li.joker.smsforwarder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
        });

        ((EditText) findViewById(R.id.another_phone)).setText(SmsUtil.getTargetPhone());
    }
}
