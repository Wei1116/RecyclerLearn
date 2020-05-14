package com.example.recylerviewlearn.demo1.date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "mType")
    public String mType;
    @ColumnInfo(name = "mContent")
    public String mContent;

    public User (String type, String content){
        this.mType=type;
        this.mContent=content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMType() {
        return mType;
    }

    public void setMType(String mType) {
        this.mType = mType;
    }

    public String getMContent() {
        return mContent;
    }

    public void setMContent(String mContent) {
        this.mContent = mContent;
    }
}
