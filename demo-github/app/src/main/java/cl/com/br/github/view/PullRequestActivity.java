package cl.com.br.github.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.com.br.github.R;
import cl.com.br.github.core.PullRequestAdapter;
import cl.com.br.github.core.RecyclerClickListener;
import cl.com.br.github.model.PullRequest;
import cl.com.br.github.model.Repository;
import cl.com.br.github.presenter.PullRequestPresenter;

public class PullRequestActivity extends BaseActivity implements PullRequestPresenter.PresentableImpl,
        RecyclerClickListener.OnItemClickListener {


    private PullRequestPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private PullRequestAdapter mAdapter;
    private ArrayList<PullRequest> mData = new ArrayList<>();
    private TextView mStateStatistics;
    private TextView mTitle;
    private String mRepoName;
    private ProgressBar mLoading;
    private String mStatstics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_request);
        loadView();
        displayToolbarTitle(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRepoInfo(getIntent());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("data", mData);
        outState.putString("statistic", mStatstics);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String statistic = savedInstanceState.getString("statistic");
            if (statistic != null && !statistic.isEmpty()) {
                displayStateStatistics(statistic);
            }

            ArrayList<PullRequest> listPR = savedInstanceState.getParcelableArrayList("data");
            if (listPR == null) {
                mData = new ArrayList<>();
            } else {
                makeRecyclerViewVisible();
                loadRecyclerView(listPR);
            }
        }
    }

    /**
     * Load views from the layout.
     */
    private void loadView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mStateStatistics = (TextView) findViewById(R.id.statistic);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mPresenter = new PullRequestPresenter(this);
        mPresenter.setPresentable(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.pull_request);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(this, this));
        mAdapter = new PullRequestAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mLoading = (ProgressBar) findViewById(R.id.loading);
    }

    /**
     * Render project name as title
     *
     * @param intent
     */
    private void displayToolbarTitle(Intent intent) {
        if (intent != null) {
            mRepoName = intent.getStringExtra("project");
            mTitle.setText(mRepoName);
        }
    }

    /**
     * Load Pull Request from endpoints.
     *
     * @param intent
     */
    private void loadRepoInfo(Intent intent) {
        if (intent != null && mData.size() == 0) {
            String author = intent.getStringExtra("author");
            mPresenter.loadPullRequests(author, mRepoName);
        }
    }

    @Override
    public void onReceiveData(List data) {
        if (data != null && data.size() > 0) {
            makeRecyclerViewVisible();
            loadRecyclerView((ArrayList<PullRequest>) data);
        }
        mLoading.setVisibility(View.GONE);
    }

    private void makeRecyclerViewVisible() {
        if (mRecyclerView.getVisibility() == View.GONE) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mStateStatistics.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.GONE);
        }
    }

    private void loadRecyclerView(ArrayList<PullRequest> data) {
        mData = (ArrayList<PullRequest>) data;
        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String msg) {
        Snackbar snackbar = Snackbar.
                make(findViewById(R.id.cl),
                        "Something went wrong.", Snackbar.LENGTH_SHORT);

    }

    @Override
    public void displayStateStatistics(String result) {
        if (mStateStatistics != null) {
            mStatstics = result;
            mStateStatistics.setText(mStatstics);
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        if (checkInternetConnection()) {
            PullRequest pullRequest = mData.get(position);
            String url = pullRequest.getUrlPullRequest();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }


}
