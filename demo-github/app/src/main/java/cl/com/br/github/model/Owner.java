package cl.com.br.github.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class Owner implements Parcelable {

    @SerializedName("login")
    private String mAuthor;
    @SerializedName("avatar_url")
    private String mAvatar;

    public Owner() {

    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAuthor);
        dest.writeString(this.mAvatar);
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Creator<Owner>() {

        @Override
        public Owner createFromParcel(Parcel source) {
            Owner owner = new Owner();
            owner.mAuthor = source.readString();
            owner.mAvatar = source.readString();
            return owner;
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
