package com.github.jakewarthongithub.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


import com.github.jakewarthongithub.R;
import com.github.jakewarthongithub.data.ApiProvider;
import com.github.jakewarthongithub.data.api.callbacks.CallbackListener;
import com.github.jakewarthongithub.data.api.model.Repository;
import com.github.jakewarthongithub.ui.adapters.RepoAdapter;
import com.github.jakewarthongithub.ui.adapters.SpinnerEnum;
import com.github.jakewarthongithub.ui.adapters.SpinnerEnumAdapter;
import com.github.jakewarthongithub.ui.dialogs.CommitsDialog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.recycleView)
    RecyclerView recyclerView;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.spinner)
    Spinner spinner;
    @InjectView(R.id.my_awesome_toolbar)
    Toolbar toolbar;
    RepoAdapter repoAdatapter;
    private ApiProvider apiProvider;
    private SpinnerEnum selectedSpinner = SpinnerEnum.STAR;
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

            switch (position) {
                case 0:
                    selectedSpinner = SpinnerEnum.CREATED;
                    break;
                case 1:
                    selectedSpinner = SpinnerEnum.UPDATE;
                    break;
                case 2:
                    selectedSpinner = SpinnerEnum.STAR;
                    break;
                case 3:
                    selectedSpinner = SpinnerEnum.FORK;
                    break;
            }
            repoAdatapter.sort(selectedSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    };
    private CallbackListener callbackListener = new CallbackListener() {
        @Override
        public void dataLoaded(ArrayList<Repository> repositoryList, boolean status) {
            if (status) {
                repoAdatapter = new RepoAdapter(getApplication(), new RepoAdapter.RepositoryClickListener() {
                    @Override
                    public void onRepositoryClick(Repository repository) {
                        CommitsDialog commitsDialog = new CommitsDialog(MainActivity.this, apiProvider, repository.name);
                        commitsDialog.show();
                    }
                }, repositoryList);
                recyclerView.setAdapter(repoAdatapter);
                repoAdatapter.sort(selectedSpinner);
                swipeRefreshLayout.setRefreshing(false);
                SpinnerEnumAdapter spinnerAdapter = new SpinnerEnumAdapter(
                        new ContextThemeWrapper(getApplicationContext(), R.style.Theme_U2020_TrendingTimespan));
                spinner.setAdapter(spinnerAdapter);
                switch (selectedSpinner) {
                    case CREATED:
                        spinner.setSelection(0);
                        break;
                    case UPDATE:
                        spinner.setSelection(1);
                        break;
                    case STAR:
                        spinner.setSelection(2);
                        break;
                    case FORK:
                        spinner.setSelection(3);
                        break;
                }
                spinner.setOnItemSelectedListener(itemSelectedListener);
            }
        }
    };
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            apiProvider.getJakesRepos(callbackListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        apiProvider = ApiProvider.getInstance(getApplication());
        apiProvider.getJakesRepos(callbackListener);

    }

    public void initView() {
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        swipeRefreshLayout.setColorSchemeResources(R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
