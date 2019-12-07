package com.ivanilov.checklist.Presenter.Entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class CheckList implements Parcelable {

    private String name;
    private Calendar time;
    private Calendar timeCheck;

    public CheckList(String name, Calendar time, Calendar timeCheck) {
        this.name = name;
        this.time = time;
        this.timeCheck = timeCheck;

    }

    public  String getName (){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public  Calendar getTime (){
        return time;
    }

    public void setTime (Calendar time){
        this.time = time;
    }

    public  Calendar getTimeCheck (){
        return timeCheck;
    }

    public void setTimeCheck (Calendar timeCheck){
        this.timeCheck = timeCheck;
    }


    protected CheckList(Parcel in) {
        name = in.readString();
        time = (Calendar) in.readValue(Calendar.class.getClassLoader());
        timeCheck = (Calendar) in.readValue(Calendar.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeValue(time);
        dest.writeValue(timeCheck);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CheckList> CREATOR = new Parcelable.Creator<CheckList>() {
        @Override
        public CheckList createFromParcel(Parcel in) {
            return new CheckList(in);
        }

        @Override
        public CheckList[] newArray(int size) {
            return new CheckList[size];
        }
    };
}