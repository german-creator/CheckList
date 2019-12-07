package com.ivanilov.checklist.Presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ivanilov.checklist.Model.Preferences;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.Presenter.Entity.NotificationCheck;

import java.util.ArrayList;

import static android.app.AlarmManager.RTC;
import static android.content.Context.ALARM_SERVICE;

public class CreateAlarmList {

    public void createAllAlarm (Context context, ArrayList<NotificationCheck> notificationChecks1){

            ArrayList<NotificationCheck> notificationChecks = existenceСheck(context, notificationChecks1);

            Intent[] intents = new Intent[notificationChecks.size()];
            PendingIntent[] alarmIntentRTC = new PendingIntent[notificationChecks.size()];
            AlarmManager[] alarmManagerRTC = new AlarmManager[notificationChecks.size()];

            for (int i=0; i<notificationChecks.size(); i++){

                intents[i] = new Intent(context, CreateNotification.class);
                intents[i].putExtra("NameCheckList", notificationChecks.get(i).getName());
                intents[i].setAction(String.valueOf(notificationChecks.get(i).getTime()));

                alarmIntentRTC[i] = PendingIntent.getBroadcast(context, RTC, intents[i], 0);

                alarmManagerRTC[i] = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                alarmManagerRTC[i].setRepeating(AlarmManager.RTC_WAKEUP, notificationChecks.get(i).getTime(), AlarmManager.INTERVAL_DAY, alarmIntentRTC[i]);

            }

    }

    public ArrayList<NotificationCheck> existenceСheck (Context context, ArrayList<NotificationCheck> notificationChecks){
        Preferences preferences = new Preferences();
        ArrayList<CheckList> checkLists = preferences.getArrayCheckListPreferences(context);

        ArrayList<String> checkListsName = new ArrayList<>();
        for (CheckList i: checkLists){
            checkListsName.add(i.getName());
        }

        for (int i=0; i<notificationChecks.size(); i++){
            if (!checkListsName.contains(notificationChecks.get(i).getName())){
                notificationChecks.remove(i);
            }
        }

        preferences.setArrayNotificationCheckPreferences(context, notificationChecks);

        return notificationChecks;
    }
}
