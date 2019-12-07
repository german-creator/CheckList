package com.ivanilov.checklist.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivanilov.checklist.Activity.HomeActivity;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterCreateCheck extends BaseAdapter{

    private HomeActivity view;
    private ArrayList<CheckList> checkLists;
    private View footerView;
    private ArrayList<String> itemCheck = new ArrayList<>();
    private Calendar time;


    public AdapterCreateCheck(HomeActivity view, ArrayList<CheckList> checkLists, ArrayList<String> itemCheck, Calendar time, View footerView) {
        this.view = view;
        this.checkLists = checkLists;
        this.footerView = footerView;
        this.time = time;

        if (itemCheck == null) {
            this.itemCheck.add("");
        } else {
            for (String s : itemCheck) {
                this.itemCheck.add(s);
            }
        }
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

    public ArrayList<String> getItemCheck() {
        return itemCheck;
    }

    public Boolean chekItemValue() {
        for (String i : itemCheck) {
            if (i.equals("")) return false;
        }
        return true;
    }

    @Override

    public View getView(final int position, View convertView, final ViewGroup parent) {
        View viewitem;
        if (convertView == null) {
            viewitem = LayoutInflater.from(view).inflate(R.layout.item_create_check, parent, false);
        }
        else {
            viewitem=convertView;
        }

        final EditText editText = viewitem.findViewById(R.id.item_create_check_EditText);
        editText.setHint("Пункт " + (position + 1));

        editText.setText(itemCheck.get(position));

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (itemCheck.size() > position){
                    itemCheck.set(position, editText.getText().toString());

                }
            }
        });

        final ImageView imageView = viewitem.findViewById(R.id.item_delete_check_imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editText.requestFocus();
                itemCheck.remove(position);
                notifyDataSetChanged();

            }
        });

        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheck.add("");
                notifyDataSetChanged();
            }
        });

        return viewitem;
    }


}

