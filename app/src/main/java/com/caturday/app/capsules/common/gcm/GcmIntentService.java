package com.caturday.app.capsules.common.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.caturday.app.R;
import com.caturday.app.capsules.detail.view.CatDetailActivity;
import com.caturday.app.capsules.detail.view.CatDetailPresenter;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private final String TAG = this.getClass().getCanonicalName();

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
             sendNotification(
                     extras.getString("message"),
                     extras.getString("post_id"),
                     extras.getString("type"));
        }
    }

    private void sendNotification(String msg, String id, String type) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent;
        intent = new Intent(this, CatDetailActivity.class);
        intent.putExtra(CatDetailPresenter.EXTRA_SERVER_ID, id);
        intent.putExtra(CatDetailPresenter.EXTRA_FROM_NETWORK, true);
        if (type.equals("post_voted")) {
            intent.putExtra(CatDetailPresenter.EXTRA_SHOW_COMMENTS, false);
        } else {
            intent.putExtra(CatDetailPresenter.EXTRA_SHOW_COMMENTS, true);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.cat_small_icon)
                        .setAutoCancel(true)
                        .setContentTitle("Caturday")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setColor(getResources().getColor(R.color.primary))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
