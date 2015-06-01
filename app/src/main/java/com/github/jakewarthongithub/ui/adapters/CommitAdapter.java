package com.github.jakewarthongithub.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.jakewarthongithub.R;
import com.github.jakewarthongithub.data.api.model.Commit;

import java.util.ArrayList;

public class CommitAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private ViewHolder viewHolder;
    private Context context;
    private ArrayList<Commit> commits = null;

    public CommitAdapter(Context context, ArrayList<Commit> commits) {
        this.commits = commits;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return commits.size();
    }

    @Override
    public Commit getItem(int position) {
        return commits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Commit commit = commits.get(position);

        if (convertView == null) {
        /*--- no View is available. Inflate our list item layout and init the Views we need ---*/
            convertView = inflater.inflate(R.layout.list_item_commits, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewMessage = (TextView) convertView
                    .findViewById(R.id.textViewMessage);
            viewHolder.textViewAuthorEmail = (TextView) convertView
                    .findViewById(R.id.textViewEmail);
            viewHolder.textViewAuthorName = (TextView) convertView
                    .findViewById(R.id.textViewName);
            viewHolder.textViewDate = (TextView) convertView
                    .findViewById(R.id.textViewDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.textViewAuthorName.setText("@" + commit.commit.author.name);
        viewHolder.textViewAuthorEmail.setText(commit.commit.author.email);
        viewHolder.textViewMessage.setText(commit.commit.message);
        viewHolder.textViewDate.setText(commit.commit.author.date.toString());

        return convertView;
    }

    static class ViewHolder {
        public TextView textViewMessage;
        public TextView textViewAuthorName;
        public TextView textViewAuthorEmail;
        public TextView textViewDate;
    }
}
