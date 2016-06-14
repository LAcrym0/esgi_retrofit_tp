package fr.esgi.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoViewHolder> {
    List<Repo> repos;
    static Context context;

    public ReposAdapter(List<Repo> repos, Context context) {
        this.repos = repos;
        ReposAdapter.context = context;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_repo, parent, false);
        return new RepoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, final int position) {
        holder.name.setText(repos.get(position).getName());
        holder.id.setText(repos.get(position).getId());
        animate(holder);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;

        public RepoViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.repo_name);
            this.id = (TextView) itemView.findViewById(R.id.repo_id);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation insertFromBottom = AnimationUtils.loadAnimation(context, R.anim.decelerate_animator);
        viewHolder.itemView.setAnimation(insertFromBottom);
    }


}
