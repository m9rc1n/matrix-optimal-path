package io.github.marcinn.matrixoptimalpath.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MatrixCell implements Parcelable {

    public static final Creator CREATOR = new Creator() {
        public MatrixCell createFromParcel(Parcel in) {
            return new MatrixCell(in);
        }

        public MatrixCell[] newArray(int size) {
            return new MatrixCell[size];
        }
    };
    private final String text;
    private final int cost;

    public MatrixCell(String text, int cost) {
        this.text = text;
        this.cost = cost;
    }

    private MatrixCell(Parcel in) {
        this.cost = in.readInt();
        this.text = in.readString();
    }

    @Override
    public String toString() {
        if (cost == Integer.MAX_VALUE) {
            return String.format("%s\n(\u221E)", text);
        } else {
            return String.format("%s\n(%d)", text, cost);
        }
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