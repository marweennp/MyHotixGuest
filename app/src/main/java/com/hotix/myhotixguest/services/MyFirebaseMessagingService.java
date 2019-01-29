package com.hotix.myhotixguest.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activites.SplashScreenActivity;
import com.hotix.myhotixguest.helpers.Session;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import static com.hotix.myhotixguest.helpers.ConstantConfig.RECEIVE_NOTIFICATION;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String channelId = "Default";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (RECEIVE_NOTIFICATION) {

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Map<String, String> data = remoteMessage.getData();
                String body = data.get("body");
                String title = data.get("title");
                String type = data.get("type");
                sendNotification(title, body, channelId, type);
            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                String body = remoteMessage.getNotification().getBody();
                String title = remoteMessage.getNotification().getTitle();
                sendNotification(title, body, channelId, "");
            }
        }

    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
        Session session = new Session(getApplicationContext());
        session.setNewToken(true);
        session.setFCMToken(token);
    }

    //Create and show a simple notification
    private void sendNotification(String messageTitle, String messageBody, String channelId, String type) {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.putExtra(type, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int icon;
        String notifTitele;
        String notifBody;
        switch (type) {
            case "message":
                icon = R.drawable.svg_notifications_blue_512;
                notifTitele = getString(R.string.message_from_) + " " + messageTitle;
                notifBody = messageBody;
                break;
            case "complaint":
                icon = R.drawable.svg_warning_blue_512;
                notifTitele = getString(R.string.complaint_treated);
                notifBody = messageBody;
                break;
            default:
                icon = R.drawable.svg_information_blue_512;
                notifTitele = messageTitle;
                notifBody = messageBody;
                break;
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(icon)
                        .setContentTitle(notifTitele)
                        .setShowWhen(true)
                        .setContentText(notifBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Random r = new Random();
        int i = r.nextInt(100);
        notificationManager.notify(createID(), notificationBuilder.build());
    }

    public int createID() {
        int id = 0;
        try {
            //Date now = new Date();
            //id = Integer.parseInt(new SimpleDateFormat("ddHHmmssSS", Locale.US).format(now));
            id = (int) (new Date().getTime() / 1000);
            Log.e("Exception", "" + id);
        } catch (Exception ex) {
            Log.e("Exception", "" + ex.toString());
        }
        return id;
    }

}
