package cl.com.br.github.core;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by heitornascimento on 8/18/16.
 */
public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private boolean isLoading = true;
    private int startingPageIndex = 0;
    private int previousTotalItemCount = 0;


    RecyclerView.LayoutManager mLayoutManager;

    public InfiniteScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public void setLoading(Boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        if (dy > 0) {
            int lastVisibleItemPosition = 0;
            final int totalItemCount = mLayoutManager.getItemCount();

            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager)
                    .findLastVisibleItemPosition();

            //reset the list.
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.isLoading = true;
                }
            }

            if (isLoading && (totalItemCount > previousTotalItemCount)) {
                isLoading = false;
                previousTotalItemCount = totalItemCount;
            }

            if (!isLoading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
                currentPage++;
                onLoadMore(currentPage, totalItemCount);
                isLoading = true;
            }
        }
    }

    public abstract void onLoadMore(int page, int totalRepositories);
}
