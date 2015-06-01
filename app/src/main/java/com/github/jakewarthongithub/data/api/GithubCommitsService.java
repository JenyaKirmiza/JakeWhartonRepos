package com.github.jakewarthongithub.data.api;


import com.github.jakewarthongithub.data.api.model.Commit;
import com.github.jakewarthongithub.data.api.model.Repository;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface GithubCommitsService {

    @GET("/repos/JakeWharton/{repo}/commits")
    void commits(
                @Path("repo") String repo,
                Callback<List<Commit>> callback);
}
