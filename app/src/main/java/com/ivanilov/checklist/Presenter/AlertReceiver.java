package com.ivanilov.checklist.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ivanilov.checklist.Activity.HomeActivity;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, HomeActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
