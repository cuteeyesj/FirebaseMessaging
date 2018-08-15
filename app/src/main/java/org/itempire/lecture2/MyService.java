package org.itempire.lecture2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    SharedPreferences sharedPreferences;
    String notiTitle, notiMessage;
    Long delayTime;
    public Timer timer;
    public TimerTask timerTask;
    String CHANNEL_ID = "first notification";
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences("notfication",MODE_PRIVATE);
       notiTitle =  sharedPreferences.getString("title","");
       notiMessage =  sharedPreferences.getString("msg","");
       delayTime =  sharedPreferences.getLong("time",10000);

        startTimer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
                notification.setContentTitle(notiTitle);
                notification.setContentText(notiMessage);
                notification.setSmallIcon(R.mipmap.ic_launcher);
                notification.setPriority(1);
                int NOTIFICATION_ID = (int) System.currentTimeMillis();
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(NOTIFICATION_ID,notification.build());
            }
        };
        timer.schedule(timerTask,delayTime,delayTime);
    }
}
