package com.apaza.moises.practices.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.apaza.moises.practices.Utils;

public class ActionNotificationReceiver extends BroadcastReceiver{

    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(action.equals(Utils.YES_ACTION)){
            Toast.makeText(context, "You trip continues, it come a car for you", Toast.LENGTH_LONG).show();
            notificationManager.cancel(Utils.NOTIFICATION_ID);
        }else if(action.equals(Utils.NO_ACTION)){
            Toast.makeText(context, "You trip was canceled", Toast.LENGTH_LONG).show();
            notificationManager.cancel(Utils.NOTIFICATION_ID);
        }

    }
}
