package com.jaydenxiao.androidfire.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yyh on 2017-05-25.
 */

public class BsLSumm implements Parcelable {
    private String State;
    private String Message;
    private List<BsLData> SList;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<BsLData> getSList() {
        return SList;
    }

    public void setSList(List<BsLData> SList) {
        this.SList = SList;
    }

    protected BsLSumm(Parcel in) {
        State = in.readString();
        Message = in.readString();
        SList = in.createTypedArrayList(BsLData.CREATOR);
    }

    public static final Creator<BsLSumm> CREATOR = new Creator<BsLSumm>() {
        @Override
        public BsLSumm createFromParcel(Parcel in) {
            return new BsLSumm(in);
        }

        @Override
        public BsLSumm[] newArray(int size) {
            return new BsLSumm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(State);
        parcel.writeString(Message);
        parcel.writeTypedList(SList);
    }

    public static class BsLData implements Parcelable {
        private String No;
        private String Name;

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

        protected BsLData(Parcel in) {
            No = in.readString();
            Name = in.readString();
        }

        public static final Creator<BsLData> CREATOR = new Creator<BsLData>() {
            @Override
            public BsLData createFromParcel(Parcel in) {
                return new BsLData(in);
            }

            @Override
            public BsLData[] newArray(int size) {
                return new BsLData[size];
            }
        };

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
}
