package com.github.jakewarthongithub.data;

import android.app.Application;
import android.util.Log;

import com.github.jakewarthongithub.data.api.GithubCommitsService;
import com.github.jakewarthongithub.data.api.GithubRepoService;
import com.github.jakewarthongithub.data.api.model.RepoComparator;
import com.github.jakewarthongithub.ui.adapters.SpinnerEnum;
import com.github.jakewarthongithub.util.DateTimeConverter;
import com.github.jakewarthongithub.data.api.callbacks.CallbackCommitsListener;
import com.github.jakewarthongithub.data.api.callbacks.CallbackListener;
import com.github.jakewarthongithub.data.api.model.Commit;
import com.github.jakewarthongithub.data.api.model.Repository;
import com.github.jakewarthongithub.data.api.params.Order;
import com.github.jakewarthongithub.data.api.params.Sort;
import com.github.jakewarthongithub.data.api.params.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import static java.util.concurrent.TimeUnit.SECONDS;
import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;

/**
 * Created by Jenya on 31.05.2015.
 */
public class ApiProvider {
    public static final String PRODUCTION_API_URL = "https://api.github.com";
    static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);
    private static ApiProvider instance;
    private Application application;

    private ApiProvider(Application app) {
        this.application = app;
    }

    public static ApiProvider getInstance(Application app) {
        if (instance != null)
            return instance;
        else {
            instance = new ApiProvider(app);
            return instance;
        }

    }

    public static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, SECONDS);
        client.setReadTimeout(10, SECONDS);
        client.setWriteTimeout(10, SECONDS);

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);

        return client;
    }

    private RestAdapter getRestAdapter() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeConverter())
                .create();
        OkHttpClient client = createOkHttpClient(application);

        Endpoint endpoint = Endpoints.newFixedEndpoint(PRODUCTION_API_URL);

        return new RestAdapter.Builder() //
                .setClient(new OkClient(client)) //
                .setEndpoint(endpoint) //
                .setConverter(new GsonConverter(gson)) //
                .build();
    }

    private GithubRepoService getGithubSevice() {
        return getRestAdapter().create(GithubRepoService.class);
    }

    private GithubCommitsService getGithubCommitsSevice() {
        return getRestAdapter().create(GithubCommitsService.class);
    }

    public void getJakesCommits(String repo, final CallbackCommitsListener listener) {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                try {
                    List<Commit> commits = (List<Commit>) o;
                    ArrayList<Commit> commitss = new ArrayList<Commit>(commits);
                    listener.dataLoaded(commitss, true);
                    Log.i("Success", response.toString());
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.dataLoaded(new ArrayList<Commit>(), false);
                Log.e("Error", retrofitError.toString());
            }
        };

        getGithubCommitsSevice().commits(repo, callback);
    }

    public void getJakesRepos(final CallbackListener listener) {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                try {
                    List<Repository> repos = (List<Repository>) o;
                    ArrayList<Repository> repositories = new ArrayList<Repository>(repos);
                    listener.dataLoaded(repositories, true);
                    Log.i("Success", response.toString());
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.dataLoaded(new ArrayList<Repository>(), false);
                Log.e("Error", retrofitError.toString());
            }
        };

        getGithubSevice().repositories(Type.OWNER, Sort.FULLNAME, Order.DESC, callback);
    }
}
