package cl.com.br.github.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 8/20/16.
 */
public class PullRequest implements Parcelable {

    @SerializedName("state")
    private String mState;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("body")
    private String mBody;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("user")
    private Owner mOwner;
    @SerializedName("html_url")
    private String urlPullRequest;


    public String getState() {
        return mState;
    }

    public void setState(String mState) {
        this.mState = mState;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public void setOwner(Owner mOwner) {
        this.mOwner = mOwner;
    }

    public String getUrlPullRequest() {
        return urlPullRequest;
    }

    public void setUrlPullRequest(String urlPullRequest) {
        this.urlPullRequest = urlPullRequest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mState);
        dest.writeString(this.mTitle);
        dest.writeString(this.mBody);
        dest.writeString(this.mCreatedAt);
        dest.writeParcelable(this.mOwner, flags);
        dest.writeString(this.urlPullRequest);
    }

    public static final Parcelable.Creator<PullRequest> CREATOR = new Creator<PullRequest>() {

        @Override
        public PullRequest createFromParcel(Parcel source) {

            PullRequest pullRequest = new PullRequest();
            pullRequest.mState = source.readString();
            pullRequest.mTitle = source.readString();
            pullRequest.mBody = source.readString();
            pullRequest.mCreatedAt = source.readString();
            pullRequest.mOwner = source.readParcelable(Owner.class.getClassLoader());
            pullRequest.urlPullRequest = source.readString();
            return pullRequest;
        }

        @Override
        public PullRequest[] newArray(int size) {
            return new PullRequest[size];
        }
    };
}
