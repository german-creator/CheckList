package com.ivanilov.checklist.Presenter.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanilov.checklist.Activity.CheckListActivity;
import com.ivanilov.checklist.Activity.HomeActivity;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.R;

import java.util.ArrayList;

public class AdapterCheckList extends BaseAdapter {

    private CheckListActivity view;
    private ArrayList<String> itemCheck;
    private ArrayList<Boolean> itemValue = new ArrayList<>();
    private View footerView;

    public AdapterCheckList(CheckListActivity view, ArrayList<String> itemCheck, View footerView ){
        this.view = view;
        this.itemCheck = itemCheck;
        for (int i=0; i<itemCheck.size(); i++){
            itemValue.add(false);
        }
        this.footerView = footerView;
    }

    @Override
    public int getCount() {
        return itemCheck.size();
    }

    @Override
    public Object getItem(int position) {
        return itemCheck.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Boolean cheсkItemValue (){
        for (Boolean i:itemValue){
            if (i == false) return false;
        }
        return true;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewitem;
        if (convertView == null) {
            viewitem = LayoutInflater.from(view).inflate(R.layout.item_check, parent, false);
        }
        else {
            viewitem=convertView;
        }

        final CheckBox checkBox = viewitem.findViewById(R.id.item_check_checkbox);

        final TextView textView = viewitem.findViewById(R.id.item_check_textView);

        textView.setText(itemCheck.get(position));

        checkBox.setChecked(itemValue.get(position));

        footerView.findViewById(R.id.checklist_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cheсkItemValue() == true){
                    view.upgradeCheckList();
                    Intent intent = new Intent(view, HomeActivity.class);
                    view.startActivity(intent);
                }
                else Toast.makeText(view, "Выполните все пункты из списка", Toast.LENGTH_SHORT).show();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked() == true) itemValue.set(position, true);
                else itemValue.set(position, false);

            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    itemValue.set(position, true);
                }
                else {
                    checkBox.setChecked(false);
                    itemValue.set(position, false);

                }

            }
        });




        return viewitem;
    }
}
