package com.ivanilov.checklist.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DialogCreateTime extends DialogFragment {

    DialogCreateCheckList dialogCreateCheckList;

    public DialogCreateTime (DialogCreateCheckList dialogCreateCheckList){
        this.dialogCreateCheckList = dialogCreateCheckList;
    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();

            int hour  = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), dialogCreateCheckList, hour, minute, true);

        }


}
