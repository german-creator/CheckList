package com.ivanilov.checklist.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ivanilov.checklist.Model.Preferences;

public class SampleBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            CreateAlarmList createAlarmList = new CreateAlarmList();
            Preferences preferences = new Preferences();
            createAlarmList.createAllAlarm(context, preferences.getArrayNotificationCheckPreferences(context));
        }
    }
}
