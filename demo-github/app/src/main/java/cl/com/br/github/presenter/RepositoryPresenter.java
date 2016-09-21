package cl.com.br.github.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import cl.com.br.github.endpoint.EndpointResult;
import cl.com.br.github.endpoint.GithubEndpoint;
import cl.com.br.github.model.RepositoryResponse;
import cl.com.br.github.model.Repository;


import static cl.com.br.github.endpoint.Constants.*;

/**
 * Created by heitornascimento on 8/18/16.
 */
public class RepositoryPresenter extends BasePresenter {

    public RepositoryPresenter(Presentable presentable, Context ctx) {
        super(presentable, ctx);
    }

    /**
     * Load repositories from endpoint.
     */
    public void loadRepositories(int page) {
        Intent intent = new Intent(getContext(), GithubEndpoint.class);
        EndpointResult receiver = new EndpointResult(new Handler());
        intent.putExtra("receiver", receiver);
        intent.putExtra("action", ACTION_REPOSITORIES);
        intent.putExtra("page", page);
        receiver.setReceiver(this);
        getContext().startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        if (resultCode == RESULT_OK && resultData != null) {
            RepositoryResponse response = resultData.getParcelable("data");
            if (response == null) {
                mPresentable.onFailure("Error");
                return;
            }
            if (response != null) {
                mPresentable.onReceiveData(response);
            } else {
                mPresentable.onFailure("Data Set in Null");
            }

        } else {
            mPresentable.onFailure("Error");
        }
    }


}
