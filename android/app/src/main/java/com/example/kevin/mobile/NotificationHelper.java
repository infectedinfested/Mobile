package com.example.kevin.mobile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

/**
 * Created by kevin on 15/03/2018.
 */

public class NotificationHelper extends ContextWrapper{

    private static final String id= "sasd";
    private static final String name = "dsfsdf";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }
    private void createChannels(){
        NotificationChannel channel = new NotificationChannel(id,name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getChannelNotification(String title, String body){
        return new Notification.Builder(getApplicationContext(),id)
                .setContentText(body)
                .setContentText(title).setSmallIcon(R.mipmap.ic_launcher_round).setAutoCancel(true);
    }
}
