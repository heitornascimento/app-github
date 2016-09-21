package cl.com.br.github.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import cl.com.br.github.endpoint.EndpointResult;
import cl.com.br.github.endpoint.GithubEndpoint;
import cl.com.br.github.model.PullRequest;
import cl.com.br.github.model.PullRequestResponse;

import static cl.com.br.github.endpoint.Constants.*;

/**
 * Created by heitornascimento on 8/20/16.
 */
public class PullRequestPresenter extends BasePresenter {

    private PresentableImpl mView;

    public PullRequestPresenter(Context ctx) {
        super(ctx);
    }

    public void setPresentable(PresentableImpl view) {
        this.mView = view;
    }

    public void loadPullRequests(String ownerName, String repoName) {
        Intent intent = new Intent(getContext(), GithubEndpoint.class);
        EndpointResult receiver = new EndpointResult(new Handler());
        intent.putExtra("receiver", receiver);
        intent.putExtra("action", ACTION_PULL_REQUESTS);
        intent.putExtra("owner_name", ownerName);
        intent.putExtra("repo_name", repoName);
        receiver.setReceiver(this);
        getContext().startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == RESULT_OK && resultData != null) {
            PullRequestResponse response = resultData.getParcelable("data");
            if (response == null) {
                mView.onFailure("Error");
                return;
            }

            List<PullRequest> pullRequestList = response.getPullRequest();
            if (pullRequestList != null && pullRequestList.size() > 0) {
                mView.onReceiveData(pullRequestList);
                calculateStatistics(pullRequestList);
            } else {
                mView.onFailure("Data Set in Null");
            }

        } else {
            mView.onFailure("Error");
        }
    }

    /**
     * Calculates the opened and closed pull requests
     *
     * @param list
     */
    private void calculateStatistics(List<PullRequest> list) {

        String openTemplate = "__OPEN__";
        String closeTemplate = "__END__";

        String template = openTemplate + " opened/ " + closeTemplate + " closed";

        StringBuilder sb = new StringBuilder(template);
        String result = "";
        int amountOpen = 0;
        int amountClose = 0;

        for (PullRequest pq : list) {
            if (pq.getState().equals("open")) {
                amountOpen++;
            } else {
                amountClose++;
            }
        }

        int indexOpen = sb.indexOf(openTemplate);
        int indexClose = sb.indexOf(closeTemplate);

        sb.replace(indexClose, indexClose + closeTemplate.length(), String.valueOf(amountClose));
        sb.replace(indexOpen, indexOpen + openTemplate.length(), String.valueOf(amountOpen));

        result = sb.toString();
        mView.displayStateStatistics(result);
    }


    public static interface PresentableImpl<T> {
        public void onReceiveData(List<T> data);

        public void onFailure(String msg);

        public void displayStateStatistics(String result);
    }
}
