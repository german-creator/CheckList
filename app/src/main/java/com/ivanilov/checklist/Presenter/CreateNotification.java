package com.ivanilov.checklist.Presenter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ivanilov.checklist.R;


import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class CreateNotification extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String checkListName = intent.getStringExtra("NameCheckList");
        create(context, checkListName);
    }

    public void create(Context context, String nameCheckList){

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationChannel channel = notificationManager.getNotificationChannel("1111");
        if (channel == null){
            createNotificationChannel(context);
            notificationManager = NotificationManagerCompat.from(context);
        }

        Intent snoozeIntent = new Intent(context, AlertReceiver.class);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast( context, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1111")
                .setSmallIcon(R.drawable.ic_check_white_24dp)
                .setContentTitle("Пора заполнить Чек-лист")
                .setContentText(nameCheckList)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(snoozePendingIntent)
                .setAutoCancel(true)
                .setColor(context.getResources().getColor(R.color.colorPrimary));

        soundSignal(context);
        notificationManager.notify(1111, builder.build());
    }

    private void createNotificationChannel(Context context) {
        CharSequence name = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            name = "chanelCheck";
            String description = "chanelCheckDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1111", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void soundSignal (Context context) {

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
