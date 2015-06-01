package com.github.jakewarthongithub.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.jakewarthongithub.R;
import com.github.jakewarthongithub.data.ApiProvider;
import com.github.jakewarthongithub.data.api.callbacks.CallbackCommitsListener;
import com.github.jakewarthongithub.data.api.model.Commit;
import com.github.jakewarthongithub.ui.adapters.CommitAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommitsDialog extends Dialog implements
        android.view.View.OnClickListener {

    @InjectView((R.id.listView))
    ListView listView;
    @InjectView((R.id.btnOk))
    Button yes;
    @InjectView((R.id.progressBar))
    ProgressBar progressBar;

    private Activity c;


    private CommitAdapter commitAdapter;
    private String repoName;
    private ApiProvider apiProvider;

    public CommitsDialog(Activity a, ApiProvider apiProvider, String repo) {
        super(a, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.apiProvider = apiProvider;
        this.repoName = repo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.commit_dialog);
        ButterKnife.inject(this);

        yes.setOnClickListener(this);
        progressBar.setVisibility(View.VISIBLE);
        apiProvider.getJakesCommits(repoName, new CallbackCommitsListener() {
            @Override
            public void dataLoaded(ArrayList<Commit> commitsList, boolean status) {
                if (status) {
                    progressBar.setVisibility(View.GONE);
                    commitAdapter = new CommitAdapter(c, commitsList);
                    listView.setAdapter(commitAdapter);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(c, "Failed to load commits", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
} 