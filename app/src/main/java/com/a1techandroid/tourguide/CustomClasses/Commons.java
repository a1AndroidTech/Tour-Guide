package com.a1techandroid.tourguide.CustomClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.a1techandroid.tourguide.LoginActivityNew;
import com.a1techandroid.tourguide.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Commons {

    public static String SimpleGMTTimeFormat(String date){
        String timeFormat= date;
        String newDate = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
        Date d = null;
        try {
            d = inputFormat.parse(timeFormat);
            Log.i("dateGMT", "" + d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat = null;
        outputFormat = new java.text.SimpleDateFormat("EE, dd MMM yyyy");
        newDate = outputFormat.format(d);
        Log.i("dateGMT22", "" + newDate);
        return newDate;
    }

    public static void testMessage (Context context,String message){

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , new Intent(context, LoginActivityNew.class),
                PendingIntent.FLAG_ONE_SHOT);


        String channelId = "some_channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.tg_logo)
                        .setContentTitle("Booking")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
