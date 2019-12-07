package com.ivanilov.checklist.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.checklist.Model.Preferences;
import com.ivanilov.checklist.Presenter.Adapter.AdapterCreateCheck;
import com.ivanilov.checklist.Presenter.Entity.CheckList;
import com.ivanilov.checklist.Presenter.CreateAlarmList;
import com.ivanilov.checklist.Presenter.Entity.NotificationCheck;
import com.ivanilov.checklist.R;

import java.util.ArrayList;
import java.util.Calendar;


public class DialogCreateCheckList extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    TextView textView;
    DialogCreateCheckList dialogCreateCheckList;
    ArrayList<CheckList> checkLists;
    Intent intent;
    View footerView;
    ListView listView;
    EditText editText;
    AdapterCreateCheck adapter;
    ImageView imageView;
    Calendar date;
    Preferences preferences = new Preferences();



    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_create_check_list, null);
        final Preferences preferences = new Preferences();
        editText = view.findViewById(R.id.Dialog_Create_EditText);
        listView = view.findViewById(R.id.Dialog_Create_ListView);
        Button button = view.findViewById(R.id.Dialog_Create_Button);
        Button buttonDel = view.findViewById(R.id.Dialog_Create_Button_Del);
        textView = view.findViewById(R.id.Dialog_Create_TextView);
        imageView = view.findViewById(R.id.Dialog_Create_ImageView);
        intent = getActivity().getIntent();
        dialogCreateCheckList = this;
        builder.setView(view);

        checkLists = preferences.getArrayCheckListPreferences(getActivity());

        footerView = ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_plus, null, false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Нет");
            }
        });

        if (intent.getExtras() != null && intent.getExtras().containsKey("Position") == true) {
            editAdapter(intent.getIntExtra("Position", 0));
        }
        else {
            adapter = new AdapterCreateCheck((HomeActivity) getActivity(), checkLists, null, null, footerView);

        }

            listView.setAdapter(adapter);
            listView.addFooterView(footerView);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (editText.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Введите название", Toast.LENGTH_SHORT).show();

                    }
                    if (adapter.chekItemValue() == false){
                        Toast.makeText(getActivity(), "Заполните все пукты", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        ArrayList<String> s = adapter.getItemCheck();

                    ArrayList<CheckList> checkListsAll = preferences.getArrayCheckListPreferences(view.getContext());

                    Calendar calendar = null;


                    if (!textView.getText().equals("Нет")) {

                        String textviewDate = textView.getText().toString();

                        String[] resultDate = textviewDate.split(":");

                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(resultDate[0]));
                        calendar.set(Calendar.MINUTE, Integer.valueOf(resultDate[1]));

                    }

                    date = calendar;

                    Calendar timeCheck = Calendar.getInstance();

                    timeCheck.add(Calendar.DAY_OF_YEAR,-1);

                    CheckList checkListNew = new CheckList(
                            editText.getText().toString(),
                            calendar,
                            timeCheck
                    );

                    if (checkListsAll == null) {
                        checkListsAll = new ArrayList<>();
                    }

                    int position = intent.getIntExtra("Position", 10000);

                    if (position == 10000) {
                        checkListsAll.add(checkListNew);
                    } else checkListsAll.set(position, checkListNew);


                    preferences.setArrayStringPreferences(view.getContext(), checkListNew.getName(), s);
                    preferences.setArrayCheckListPreferences(view.getContext(), checkListsAll);

                    if (!textView.getText().equals("Нет")){
                        createAlarm();
                    }

                    ((HomeActivity) getActivity()).refreshAdapter();
                    dismiss();
                    }
                }

            });


            buttonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<CheckList> checkListsAll = preferences.getArrayCheckListPreferences(view.getContext());

                    if (intent.getExtras() != null && intent.getExtras().containsKey("Position") == true) {
                        CheckList checkList = checkListsAll.get(intent.getIntExtra("Position", 0));
                        preferences.deletPrefferences(view.getContext(), checkList.getName());
                        checkListsAll.remove(checkList);
                        preferences.setArrayCheckListPreferences(view.getContext(), checkListsAll);

                    }

                    ((HomeActivity) getActivity()).refreshAdapter();
                    dismiss();
                }
            });

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogCreateTime addDialogFragment = new DialogCreateTime(dialogCreateCheckList);

                    addDialogFragment.show(getFragmentManager(), "Тэг?");
                }
            });

        return builder.create();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textView.setText(hourOfDay +":"+ minute);
    }

    public void editAdapter (int position){

        editText.setText(checkLists.get(position).getName());


        ArrayList<String > item = preferences.getArrayStringPreferences(getContext(), checkLists.get(position).getName());
        date = checkLists.get(position).getTime();

        if (date != null){
            textView.setText(date.get(Calendar.HOUR_OF_DAY)+":" + date.get(Calendar.MINUTE));
        }

        adapter = new AdapterCreateCheck((HomeActivity) getActivity(), checkLists, item, date, footerView);

    }


    public void createAlarm (){

        final String name = editText.getText().toString();

        Context context = getContext();
        ArrayList<NotificationCheck> notificationChecks;

        if (preferences.contains(context, "NotificationCheckList") == true){
            notificationChecks = deletOldAlarm(preferences.getArrayNotificationCheckPreferences(context), name);

        }
        else notificationChecks = new ArrayList<>();


        NotificationCheck notificationCheck = new NotificationCheck(
                name,
                date.getTimeInMillis()
        );

        notificationChecks.add(notificationCheck);
        preferences.setArrayNotificationCheckPreferences(context, notificationChecks);

        CreateAlarmList createAlarmList = new CreateAlarmList();
        createAlarmList.createAllAlarm(context, notificationChecks);

    }


    public ArrayList<NotificationCheck> deletOldAlarm (ArrayList<NotificationCheck> notificationChecks, String name){
        ArrayList<String> names = new ArrayList<>();
        for(NotificationCheck i:notificationChecks){
            names.add(i.getName());
        }

        for (int i = 0; i<notificationChecks.size(); i++){
            if (names.get(i).equals(name)){
                notificationChecks.remove(i);
                i--;
            }
        }

        return notificationChecks;

    }


}
