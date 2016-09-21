package cl.com.br.github.endpoint;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

import java.util.List;

import cl.com.br.github.model.PullRequest;
import cl.com.br.github.model.RepositoryResponse;
import cl.com.br.github.model.PullRequestResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class GithubEndpoint extends IntentService {

    private ResultReceiver mReceiver;
    private RepositoryResponse mResponse;
    private PullRequestResponse mPullRequestResponse;
    private final GithubService service = Injector.provideGithubService();
    private int mPage;

    public GithubEndpoint() {
        super("GithubEndpoint");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mReceiver = intent.getParcelableExtra("receiver");
            String action = intent.getStringExtra("action");

            switch (action) {
                case Constants.ACTION_REPOSITORIES:
                    loadRepositories(intent);
                    break;
                case Constants.ACTION_PULL_REQUESTS:
                    loadPullRequest(intent);
            }
        }
    }

    /**
     * Retrieve java repositories according to the start and page
     */
    private void loadRepositories(Intent intent) {
        mPage = intent.getIntExtra("page", 1);
        service.getRepositories("language:Java", "stars", mPage).enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                mResponse = response.body();
                sendToReceiverSuccess(mResponse);
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                sendToReceiverError(t.getMessage());
            }
        });

    }

    /**
     * Load Pull Request
     *
     * @param intent
     */
    private void loadPullRequest(Intent intent) {
        String ownerName = intent.getStringExtra("owner_name");
        final String repoName = intent.getStringExtra("repo_name");

        service.getPullRequest(ownerName, repoName).enqueue(new Callback<List<PullRequest>>() {
            @Override
            public void onResponse(Call<List<PullRequest>> call,
                                   Response<List<PullRequest>> response) {
                mPullRequestResponse = new PullRequestResponse();
                mPullRequestResponse.setPullRequest(response.body());
                sendToReceiverSuccess(mPullRequestResponse);
            }

            @Override
            public void onFailure(Call<List<PullRequest>> call, Throwable t) {
                sendToReceiverError(t.getMessage());
            }
        });

    }


    /**
     * @param response
     */
    public void sendToReceiverSuccess(Parcelable response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", response);
        if (mReceiver != null) {
            mReceiver.send(Constants.RESULT_OK, bundle);
        }

    }

    /**
     *
     */
    public void sendToReceiverError(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("error", message);
        if (mReceiver != null) {
            mReceiver.send(Constants.RESULT_ERROR, bundle);
        }
    }
}
