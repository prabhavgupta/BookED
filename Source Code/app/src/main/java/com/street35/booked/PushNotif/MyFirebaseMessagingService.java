package com.street35.booked.PushNotif;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.street35.booked.Main.BottomNavigation;
import com.street35.booked.R;

/**
 * Created by Weirdo on 18-12-2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent i = new Intent(this, BottomNavigation.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 , i ,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder nb= new NotificationCompat.Builder(this);
        nb.setContentTitle("FCM Notification");
        nb.setContentText(remoteMessage.getNotification().getBody());
        nb.setAutoCancel(true);
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,nb.build());
    }
}
