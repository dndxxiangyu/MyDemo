package com.example;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间：2020-01-08
 * author: wuxiangyu.lc
 * describe:
 */
public class RouteBeanCopy implements Parcelable {

    public String name;
    public String keyword;
    public String pageName;
    public RouteBeanCopy() {

    }
    protected RouteBeanCopy(Parcel in) {
        this.name = in.readString();
//        this.keyword = in.readString();
//        this.pageName = in.readString();
    }

    public static final Creator<RouteBeanCopy> CREATOR = new Creator<RouteBeanCopy>() {
        @Override
        public RouteBeanCopy createFromParcel(Parcel in) {
            return new RouteBeanCopy(in);
        }

        @Override
        public RouteBeanCopy[] newArray(int size) {
            return new RouteBeanCopy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
//        dest.writeString(this.keyword);
//        dest.writeString(this.pageName);
    }
}
