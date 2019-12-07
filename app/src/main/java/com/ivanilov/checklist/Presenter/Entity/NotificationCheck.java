package com.ivanilov.checklist.Presenter.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationCheck implements Parcelable {

    private String name;
    private Long time;

    public NotificationCheck(String name, Long time) {
        this.name = name;
        this.time = time;

    }

    public  String getName (){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public  Long getTime (){
        return time;
    }

    public void setTime (Long time){
        this.time = time;
    }


    protected NotificationCheck(Parcel in) {
        name = in.readString();
        time = in.readByte() == 0x00 ? null : in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (time == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(time);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NotificationCheck> CREATOR = new Parcelable.Creator<NotificationCheck>() {
        @Override
        public NotificationCheck createFromParcel(Parcel in) {
            return new NotificationCheck(in);
        }

        @Override
        public NotificationCheck[] newArray(int size) {
            return new NotificationCheck[size];
        }
    };
}