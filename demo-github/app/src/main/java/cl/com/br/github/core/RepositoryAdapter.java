package cl.com.br.github.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import cl.com.br.github.R;
import cl.com.br.github.cache.BitmapManager;
import cl.com.br.github.model.Repository;

/**
 * Created by heitornascimento on 8/18/16.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Repository> mData;
    private ViewHolder mViewHolder;
    private WeakReference<Context> weakContext;

    public RepositoryAdapter(Context context, List<Repository> data) {
        mData = data;
        weakContext = new WeakReference<Context>(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_item, parent, false);
        mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Repository repository = mData.get(position);

        final String name = repository.getName();
        mViewHolder.nameTxt.setText(name);

        final String description = repository.getDescription();
        mViewHolder.descriptionTxt.setText(description);

        final String forkCount = String.valueOf(repository.getForks());
        mViewHolder.forkCount.setText(forkCount);

        final String starCount = String.valueOf(repository.getStars());
        mViewHolder.starCount.setText(starCount);

        final String userName = String.valueOf(repository.getOwner().getAuthor());
        mViewHolder.userName.setText(userName);

        //Image Profile - Cache system.
        BitmapManager manager = BitmapManager.getInstance(weakContext.get());
        String profile = repository.getOwner().getAuthor();
        String url = repository.getOwner().getAvatar();
        Bitmap bitmap = manager.loadImageFromCache(profile, url, mViewHolder.icProfile);
        mViewHolder.icProfile.setImageBitmap(bitmap);
    }

    public void setData(List<Repository> list) {
        mData = list;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTxt;
        private TextView descriptionTxt;
        private TextView forkCount;
        private TextView starCount;
        private TextView userName;
        private TextView userLastName;
        private ImageView icProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.name);
            descriptionTxt = (TextView) itemView.findViewById(R.id.description);
            forkCount = (TextView) itemView.findViewById(R.id.fork_count);
            starCount = (TextView) itemView.findViewById(R.id.star_count);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userLastName = (TextView) itemView.findViewById(R.id.user_last_name);
            icProfile = (ImageView) itemView.findViewById(R.id.ic_user);
        }
    }
}
