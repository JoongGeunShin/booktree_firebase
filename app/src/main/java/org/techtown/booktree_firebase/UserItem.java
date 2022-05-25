package org.techtown.booktree_firebase;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItem implements Parcelable {
    String user_name;
    String user_email;
    int userImg;
    int userIdx;


    public UserItem(String user_name, String user_email, int postIdx){
        this.user_name = user_name;
        this.user_email = user_email;
        this.userIdx = postIdx;

    }

    protected UserItem(Parcel in) {
        user_name = in.readString();
        user_email = in.readString();
        userIdx = in.readInt();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel in) {
            return new UserItem(in);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUserIdx(int postIdx) {
        this.userIdx = postIdx;
    }

    public void setUserImg(int postImg) {
        this.userImg = postImg;
    }


    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public int getUserImg() {
        return userImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_name);
        parcel.writeString(user_email);
        parcel.writeInt(userIdx);
    }
}