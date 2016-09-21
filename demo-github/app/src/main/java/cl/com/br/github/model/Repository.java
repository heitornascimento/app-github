package cl.com.br.github.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class Repository implements Parcelable {

    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("stargazers_count")
    private long mStars;
    @SerializedName("forks_count")
    private long mForks;
    @SerializedName("owner")
    private Owner mOwner;


    public Repository() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    public long getStars() {
        return mStars;
    }

    public void setStars(long mStars) {
        this.mStars = mStars;
    }

    public long getForks() {
        return mForks;
    }

    public void setForks(long mForks) {
        this.mForks = mForks;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public void setOwner(Owner mOwner) {
        this.mOwner = mOwner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeLong(mStars);
        dest.writeLong(mForks);
        dest.writeParcelable(mOwner, flags);
    }

    public static final Parcelable.Creator<Repository> CREATOR =
            new Creator<Repository>() {

                @Override
                public Repository createFromParcel(Parcel source) {
                    Repository repository = new Repository();
                    repository.mName = source.readString();
                    repository.mDescription = source.readString();
                    repository.mStars = source.readLong();
                    repository.mForks = source.readLong();
                    repository.mOwner = source.readParcelable(Owner.class.getClassLoader());
                    return repository;
                }

                @Override
                public Repository[] newArray(int size) {
                    return new Repository[size];
                }
            };
}
