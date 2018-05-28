package com.jaydenxiao.androidfire.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 2017-05-24.
 */

public class BsListSummary implements Parcelable {

    private String No;
    private String Name;

    protected BsListSummary(Parcel in) {
        No = in.readString();
        Name = in.readString();
    }

    public static final Creator<BsListSummary> CREATOR = new Creator<BsListSummary>() {
        @Override
        public BsListSummary createFromParcel(Parcel in) {
            return new BsListSummary(in);
        }

        @Override
        public BsListSummary[] newArray(int size) {
            return new BsListSummary[size];
        }
    };

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(No);
        parcel.writeString(Name);
    }
}
