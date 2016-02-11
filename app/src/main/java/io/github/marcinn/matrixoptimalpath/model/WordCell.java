package io.github.marcinn.matrixoptimalpath.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordCell implements Parcelable {

    public static final Creator CREATOR = new Creator() {
        public WordCell createFromParcel(Parcel in) {
            return new WordCell(in);
        }

        public WordCell[] newArray(int size) {
            return new WordCell[size];
        }
    };
    private final String text;
    private final int cost;

    public WordCell(String text, int cost) {
        this.text = text;
        this.cost = cost;
    }

    private WordCell(Parcel in) {
        this.cost = in.readInt();
        this.text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cost);
        dest.writeString(text);
    }

    public String getText() {
        return text;
    }

    public int getCost() {
        return cost;
    }
}