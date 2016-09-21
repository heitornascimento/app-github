package cl.com.br.github.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class RepositoryResponse implements Parcelable {

    @SerializedName("total_count")
    private long count;
    @SerializedName("incomplete_results")
    private Boolean results;
    @SerializedName("items")
    private List<Repository> repositories;

    public RepositoryResponse() {

    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Boolean getResults() {
        return results;
    }

    public void setResults(Boolean results) {
        this.results = results;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(count);
        boolean[] booleanArray = new boolean[1];
        dest.writeBooleanArray(booleanArray);
        results = booleanArray[0];
        dest.writeList(repositories);
    }

    public static final Parcelable.Creator<RepositoryResponse> CREATOR = new Creator<RepositoryResponse>() {
        @Override
        public RepositoryResponse createFromParcel(Parcel source) {

            RepositoryResponse response = new RepositoryResponse();
            response.count = source.readLong();

            boolean[] booleanArray = new boolean[1];
            source.readBooleanArray(booleanArray);
            response.results = booleanArray[0];
            source.readList(response.repositories,
                    Repository.class.getClassLoader());
            return response;
        }

        @Override
        public RepositoryResponse[] newArray(int size) {
            return new RepositoryResponse[size];
        }
    };
}
