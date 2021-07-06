package com.example.djgeteamproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class SaveScore implements Parcelable {
    public double score;
    public String id;

    public SaveScore(String id, double score) {
        this.id = id;
        this.score = score;
    }

    public SaveScore(Parcel in) {
        id = in.readString();
        score = in.readDouble();
    }

    public String getid() { return id; }
    public String getscore() { return Double.toString(score); }
    public void setid(String stid) { id = stid; }
    public void setscore(Double stsc) { score = stsc; }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof SaveScore)
            return id.equals(((SaveScore) o).getid());
        return false;
    }

    @Override
    public String toString(){
        return this.id;
    }


    public static final Creator<SaveScore> CREATOR = new Creator<SaveScore>() {
        @Override
        public SaveScore createFromParcel(Parcel in) {
            return new SaveScore(in);
        }

        @Override
        public SaveScore[] newArray(int size) {
            return new SaveScore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(score);
        dest.writeString(id);
    }
}
