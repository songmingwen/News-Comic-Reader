package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Song on 2016/12/5.
 */
public class User implements Parcelable ,Cloneable{

    private String userName;

    private String address;

    private String phone;

    public User(String userName, String address, String phone) {
        this.userName = userName;
        this.address = address;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User clone() {
        try {
            User user = (User) super.clone();
            user.userName = this.userName;
            user.address = this.address;
            user.phone = this.phone;
            return user;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.address);
        dest.writeString(this.phone);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
