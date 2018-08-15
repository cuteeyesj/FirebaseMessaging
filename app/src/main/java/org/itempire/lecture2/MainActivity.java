package org.itempire.lecture2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
EditText title,msg,time;
Button setNotification , stopNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.txt_title);
        msg = (EditText) findViewById(R.id.txt_msg);
        time = (EditText) findViewById(R.id.txt_time);
        setNotification = (Button) findViewById(R.id.setNotif);
        stopNotification = (Button) findViewById(R.id.stopNotif);
        SharedPreferences sharedPreferences = getSharedPreferences("notfication",MODE_PRIVATE);
       final SharedPreferences.Editor editor = sharedPreferences.edit();
       final Intent intent = new Intent(getApplicationContext(),MyService.class);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FROM FIREBASE MAIN", "Refreshed token: " + refreshedToken);

        setNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notiTitle = title.getText().toString();
                String notiMsg = msg.getText().toString();
                Long notitime = Long.parseLong(time.getText().toString());

                editor.putString("title",notiTitle);
                editor.putString("msg",notiMsg);
                editor.putLong("time",notitime);
                editor.apply();
                startService(intent);

            }
        });
        stopNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
            }
        });
    }
}
