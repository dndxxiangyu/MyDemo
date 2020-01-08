package com.example;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间：2020-01-08
 * author: wuxiangyu.lc
 * describe:
 */
public class RouteBean implements Parcelable {

    public String name;
    public String keyword;
    public String pageName;
    public RouteBean() {

    }
    protected RouteBean(Parcel in) {
        this.name = in.readString();
        this.keyword = in.readString();
        this.pageName = in.readString();
    }

    public static final Creator<RouteBean> CREATOR = new Creator<RouteBean>() {
        @Override
        public RouteBean createFromParcel(Parcel in) {
            return new RouteBean(in);
        }

        @Override
        public RouteBean[] newArray(int size) {
            return new RouteBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.keyword);
        dest.writeString(this.pageName);
    }
}
