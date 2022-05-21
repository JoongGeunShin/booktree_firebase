package org.techtown.booktree_firebase;

import android.os.Parcel;
import android.os.Parcelable;

public class PostItem implements Parcelable {
    String postTitle;
    String postWhat;
    String bookInfo;
    int postImg;
    int likescount;



    int postIdx;


    public PostItem(String postTitle, String postWhat, String bookInfo, int postIdx){
        this.postTitle = postTitle;
        this.postWhat = postWhat;
        this.bookInfo = bookInfo;
        this.postIdx = postIdx;

    }

    protected PostItem(Parcel in) {
        postTitle = in.readString();
        postWhat = in.readString();
        bookInfo = in.readString();
        postIdx = in.readInt();
    }

    public static final Creator<PostItem> CREATOR = new Creator<PostItem>() {
        @Override
        public PostItem createFromParcel(Parcel in) {
            return new PostItem(in);
        }

        @Override
        public PostItem[] newArray(int size) {
            return new PostItem[size];
        }
    };

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostWhat(String postWhat) {
        this.postWhat = postWhat;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    public void setPostIdx(int postIdx) {
        this.postIdx = postIdx;
    }

    public void setPostImg(int postImg) {
        this.postImg = postImg;
    }

    public int getPostImg() {
        return postImg;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostWhat() {
        return postWhat;
    }

    public String getBookInfo() {
        return bookInfo;
    }


    public int getPostIdx() {
        return postIdx;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(postTitle);
        parcel.writeString(postWhat);
        parcel.writeString(bookInfo);
        parcel.writeInt(postIdx);
    }
}