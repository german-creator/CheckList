package com.ivanilov.checklist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ivanilov.checklist.Model.Preferences;
import com.ivanilov.checklist.Presenter.Adapter.AdapterCheckList;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CheckListActivity extends AppCompatActivity {

    ListView listView;
    AdapterCheckList adapter;
    ArrayList<String> itemCheckList = new ArrayList<>();
    Preferences preferences = new Preferences();
    CheckList checkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        checkList = getIntent().getParcelableExtra("name");

        String name = checkList.getName();

        setTitle(name);


        listView = findViewById(R.id.checklist_listview);

        itemCheckList= preferences.getArrayStringPreferences(this, name);
    }


    @Override
    protected void onResume () {
        super.onResume();

        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_ok, null, false);

        adapter = new AdapterCheckList(this, itemCheckList, footerView);

        listView.setAdapter(adapter);

        listView.addFooterView(footerView);


    }

    public void upgradeCheckList (){
        checkList.setTimeCheck(Calendar.getInstance());
        ArrayList<CheckList> checkLists = preferences.getArrayCheckListPreferences(this);
        int position = getIntent().getExtras().getInt("ItemCheck");
        checkLists.set(position, checkList);
        preferences.setArrayCheckListPreferences(this, checkLists);
    }
}
