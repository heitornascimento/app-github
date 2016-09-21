package cl.com.br.github.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.com.br.github.R;
import cl.com.br.github.model.PullRequest;
import cl.com.br.github.utils.DateUtils;

/**
 * Created by heitornascimento on 8/20/16.
 */
public class PullRequestAdapter extends RecyclerView.Adapter<PullRequestAdapter.ViewHolder> {

    private Context mContext;
    private List<PullRequest> mData;
    private ViewHolder mViewHolder;

    public PullRequestAdapter(Context context, List<PullRequest> list) {
        this.mContext = context;
        this.mData = list;
    }


    public void setData(List<PullRequest> list) {
        this.mData = list;
    }

    @Override
    public PullRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pull_request_item, parent, false);
        mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(PullRequestAdapter.ViewHolder holder, int position) {

        if (mData != null) {

            PullRequest pullRequest = mData.get(position);

            final String userName = pullRequest.getOwner().getAuthor();
            mViewHolder.userName.setText(userName);

            final String title = pullRequest.getTitle();
            mViewHolder.title.setText(title);

            final String body = pullRequest.getBody();
            mViewHolder.body.setText(body);

            final String state = pullRequest.getState();
            mViewHolder.state.setText(state);

            final String createAt = pullRequest.getCreatedAt();
            mViewHolder.createAt.setText(DateUtils.formatDate(createAt));

            Picasso.with(mContext).load(pullRequest.getOwner().getAvatar())
                    .resize(100, 100).into(mViewHolder.icProfile);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView title;
        private TextView body;
        private TextView state;
        private TextView createAt;
        private ImageView icProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            title = (TextView) itemView.findViewById(R.id.title);
            body = (TextView) itemView.findViewById(R.id.body);
            state = (TextView) itemView.findViewById(R.id.state);
            createAt = (TextView) itemView.findViewById(R.id.created_at);
            icProfile = (ImageView) itemView.findViewById(R.id.ic_user);
        }
    }
}
