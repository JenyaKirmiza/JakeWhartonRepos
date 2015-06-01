package com.github.jakewarthongithub.ui.adapters;

import android.app.Application;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.jakewarthongithub.R;
import com.github.jakewarthongithub.data.ApiProvider;
import com.github.jakewarthongithub.data.api.model.RepoComparator;
import com.github.jakewarthongithub.data.api.model.Repository;
import com.github.jakewarthongithub.ui.views.RepoItemView;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    private final Picasso picasso;
    private final RepositoryClickListener repositoryClickListener;
    private List<Repository> repositories = Collections.emptyList();

    public RepoAdapter(Application app, RepositoryClickListener repositoryClickListener, List<Repository> repositories) {
        this.picasso = new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(ApiProvider.createOkHttpClient(app)))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                        Log.e("Failed to load image:", uri.toString());
                    }
                })
                .build();
        ;
        this.repositories = repositories;
        this.repositoryClickListener = repositoryClickListener;
    }

    public Repository getItem(int position) {
        return repositories.get(position);
    }

    public void sort(SpinnerEnum spinnerEnum) {
        Collections.sort(repositories, new RepoComparator(spinnerEnum));
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RepoItemView view = (RepoItemView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_repository, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bindTo(repositories.get(i));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public interface RepositoryClickListener {
        void onRepositoryClick(Repository repository);
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final RepoItemView itemView;
        private Repository repository;

        public ViewHolder(RepoItemView itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repositoryClickListener.onRepositoryClick(repository);
                }
            });
        }

        public void bindTo(Repository repository) {
            this.repository = repository;
            itemView.bindTo(repository, picasso);
        }
    }
}
