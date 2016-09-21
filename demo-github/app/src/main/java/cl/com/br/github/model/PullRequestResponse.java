package cl.com.br.github.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by heitornascimento on 8/20/16.
 */
public class PullRequestResponse implements Parcelable {

    private List<PullRequest> mPullRequest;

    public List<PullRequest> getPullRequest() {
        return mPullRequest;
    }

    public void setPullRequest(List<PullRequest> mPullRequest) {
        this.mPullRequest = mPullRequest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mPullRequest);
    }


    public static final Parcelable.Creator<PullRequestResponse> CREATOR =
            new Creator<PullRequestResponse>() {

                @Override
                public PullRequestResponse createFromParcel(Parcel source) {
                    PullRequestResponse pullRequestResponse = new PullRequestResponse();
                    source.readList(pullRequestResponse.mPullRequest,
                            PullRequestResponse.class.getClassLoader());
                    return pullRequestResponse;
                }

                @Override
                public PullRequestResponse[] newArray(int size) {
                    return new PullRequestResponse[size];
                }
            };
}
