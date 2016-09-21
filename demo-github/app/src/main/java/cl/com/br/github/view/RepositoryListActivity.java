package cl.com.br.github.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import cl.com.br.github.R;
import cl.com.br.github.core.InfiniteScrollListener;
import cl.com.br.github.core.RecyclerClickListener;
import cl.com.br.github.core.RepositoryAdapter;
import cl.com.br.github.model.Repository;
import cl.com.br.github.model.RepositoryResponse;
import cl.com.br.github.presenter.RepositoryPresenter;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class RepositoryListActivity extends BaseActivity implements RepositoryPresenter.Presentable, RecyclerClickListener.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RepositoryAdapter mAdapter;
    private ArrayList<Repository> mRepositories = new ArrayList<>();
    private RepositoryPresenter mPresenter;
    private ProgressBar mProgressBar;
    private int mPage = 1;
    private ProgressBar mLoading;
    private RepositoryResponse mResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_layout);
        loadView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRepositories == null || mRepositories.size() == 0) {
            //Init load.
            loadData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("data", mRepositories);
        outState.putInt("page", mPage);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            List<Repository> list = savedInstanceState.getParcelableArrayList("data");
            if (list == null) {
                mRepositories = new ArrayList<>();
            } else {
                loadRecyclerView(list);
                makeListVisible();
            }
            mPage = savedInstanceState.getInt("page", 1);
        }
    }

    /**
     * Load View
     */
    private void loadView() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = (RecyclerView) findViewById(R.id.repositories);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(this, this));
        mAdapter = new RepositoryAdapter(this, mRepositories);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView.addOnScrollListener(new InfiniteScrollListener(llManager) {
            @Override
            public void onLoadMore(int page, int totalRepositories) {
                mPage = page + 1;
                mProgressBar.setVisibility(View.VISIBLE);
                loadData();
            }
        });
        mLoading = (ProgressBar) findViewById(R.id.loading);
        mPresenter = new RepositoryPresenter(this, this);
    }


    /**
     * Load the github repositories.
     */
    private void loadData() {
        mPresenter.loadRepositories(mPage);
    }

    @Override
    public void onReceiveData(Parcelable data) {

        RepositoryResponse response = (RepositoryResponse) data;
        if (response != null) {
            List<Repository> listOfRepositories = response.getRepositories();
            if (listOfRepositories != null && listOfRepositories.size() > 0) {
                loadRecyclerView(listOfRepositories);
            }
        }
        makeListVisible();
    }

    private void loadRecyclerView(List<Repository> list) {
        mRepositories.addAll(list);
        mAdapter.setData(mRepositories);
        mAdapter.notifyDataSetChanged();
    }

    private void makeListVisible() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }


    @Override
    public void onFailure(String msg) {
        Snackbar snackbar = Snackbar.
                make(findViewById(R.id.cl),
                        "Something went wrong.", Snackbar.LENGTH_SHORT);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View v, int position) {
        if (checkInternetConnection()) {
            Repository repo = mRepositories.get(position);
            String projectName = repo.getName();
            String author = repo.getOwner().getAuthor();
            Intent intent = new Intent("PULL_REQUEST");
            intent.putExtra("project", projectName);
            intent.putExtra("author", author);
            startActivity(intent);
        }
    }
}
