package com.example.githubchallengemvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubchallengemvp.R;
import com.example.githubchallengemvp.data.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Repository> data = new ArrayList<>();
    private boolean isLoadingAdded = false;

    private static final int ITEM_REPO = 0;
    private static final int ITEM_LOADING = 1;

    public void setData(List<Repository> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<Repository> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        data.add(new Repository()); // item vide juste pour loader
        notifyItemInserted(data.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded && data.size() > 0) {
            int position = data.size() - 1;
            data.remove(position);
            notifyItemRemoved(position);
            isLoadingAdded = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (isLoadingAdded && position == data.size() - 1) ? ITEM_LOADING : ITEM_REPO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_REPO) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_repository, parent, false);
            return new RepoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_REPO) {
            RepoViewHolder repoHolder = (RepoViewHolder) holder;
            Repository repo = data.get(position);
            repoHolder.name.setText(repo.getName());
            repoHolder.description.setText(repo.getDescription());
            repoHolder.author.setText(repo.getOwner() != null ? repo.getOwner().getLogin() : "Unknown");
            repoHolder.stars.setText("⭐ " + repo.getStars());

            if (repo.getOwner() != null) {
                Glide.with(repoHolder.avatar.getContext())
                        .load(repo.getOwner().getAvatarUrl())
                        .into(repoHolder.avatar);
            }
        }
        // LoadingViewHolder n'a rien à binder
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class  RepoViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, author, stars;
        ImageView avatar;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            author = itemView.findViewById(R.id.author);
            stars = itemView.findViewById(R.id.stars);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}