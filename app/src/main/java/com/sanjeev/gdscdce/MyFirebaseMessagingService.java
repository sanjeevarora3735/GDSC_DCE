package com.sanjeev.gdscdce;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        final String ChannelID = "FAST_NOTIFICATION";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(ChannelID, "FAST_NOTIFICATION", NotificationManager.IMPORTANCE_HIGH);

        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

        if(message.getNotification().getImageUrl() != null) {
            Notification.Builder notification = new Notification.Builder(this, ChannelID)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setSmallIcon(Icon.createWithContentUri(message.getNotification().getImageUrl()))
                    .setContentText(text);
            NotificationManagerCompat.from(this).notify(1, notification.build());
        }
        else {
            Notification.Builder notification = new Notification.Builder(this, ChannelID)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                        .setContentText(text);
            NotificationManagerCompat.from(this).notify(1, notification.build());

        }
        super.onMessageReceived(message);
        }

    }
}
