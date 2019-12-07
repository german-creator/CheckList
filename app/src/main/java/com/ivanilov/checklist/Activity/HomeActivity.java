package com.ivanilov.checklist.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivanilov.checklist.Presenter.Adapter.AdapterHome;
import com.ivanilov.checklist.Presenter.AlertReceiver;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.Presenter.HomePrecenter;
import com.ivanilov.checklist.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    FloatingActionButton fab;
    HomePrecenter homePrecenter;
    AdapterHome adapter;
    Intent intent;
    NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.home_list_view);
        fab = findViewById(R.id.fab);
        homePrecenter = new HomePrecenter();
        setSupportActionBar(toolbar);
        intent = getIntent();

        homePrecenter.attachView(this);



    }

    @Override
    protected void onResume () {
        super.onResume();


        ArrayList<CheckList> checkLists = homePrecenter.getCheckList();
        if (checkLists!=null){
            adapter = new AdapterHome(this, checkLists);

            listView.setAdapter(adapter);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getIntent().removeExtra("Position");

                DialogCreateCheckList addDialogFragment = new DialogCreateCheckList();
                addDialogFragment.show(getSupportFragmentManager(), "Тэг?");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    public void refreshAdapter (){
        ArrayList<CheckList> checkLists = homePrecenter.getCheckList();
        adapter = new AdapterHome(this, checkLists);
        listView.setAdapter(adapter);

    }

    public void editAdapter (int position){

        intent.putExtra("Position", position);
        DialogCreateCheckList addDialogFragment = new DialogCreateCheckList();
        addDialogFragment.show(getSupportFragmentManager(), "Тэг?");

    }

}
