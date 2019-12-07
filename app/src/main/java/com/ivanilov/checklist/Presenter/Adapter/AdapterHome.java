package com.ivanilov.checklist.Presenter.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanilov.checklist.Activity.CheckListActivity;
import com.ivanilov.checklist.Activity.DialogCreateCheckList;
import com.ivanilov.checklist.Activity.HomeActivity;
import com.ivanilov.checklist.Model.Preferences;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterHome extends BaseAdapter {

    private HomeActivity view;
    private ArrayList<CheckList> checkLists;

    public AdapterHome (HomeActivity view, ArrayList<CheckList> checkLists){
        this.view = view;
        this.checkLists = checkLists;
    }

    @Override
    public int getCount() {
        return checkLists.size();
    }

    @Override
    public CheckList getItem(int position) {
        return checkLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(view).inflate(R.layout.item_home, parent, false);
        }
        final TextView textView = convertView.findViewById(R.id.item_home_textView);

        final ImageView imageView = convertView.findViewById(R.id.item_home_imageView);

        int color = (fillingСheck(checkLists.get(position)));

        if (color == 1) {
            convertView.setBackgroundColor(view.getResources().getColor(R.color.colorPrimaryVeryLight));
        }

        if (color == 2) {
            convertView.setBackgroundColor(view.getResources().getColor(R.color.colorAccentLight));
        }


        textView.setText(checkLists.get(position).getName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.editAdapter(position);

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view, CheckListActivity.class);
                intent.putExtra("name", checkLists.get(position));
                intent.putExtra("ItemCheck", position);
                view.startActivity(intent);
            }
        });

        return convertView;
    }

    public int fillingСheck (CheckList checkList){

        Calendar currintCalendar = Calendar.getInstance();

        if (checkList.getTime() == null) return 0;

        if (checkList.getTimeCheck() == null) return 2;

        if (checkList.getTimeCheck().get(Calendar.DAY_OF_YEAR) == currintCalendar.get(Calendar.DAY_OF_YEAR)) return 1;

        if (checkList.getTimeCheck().get(Calendar.DAY_OF_YEAR) != (currintCalendar.get(Calendar.DAY_OF_YEAR))
                && (checkList.getTime().get(Calendar.HOUR_OF_DAY)<=currintCalendar.get(Calendar.HOUR_OF_DAY))
        ) return 2;

        else return 0;
    }
}