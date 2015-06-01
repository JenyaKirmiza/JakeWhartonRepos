package com.github.jakewarthongithub.data.api;


import com.github.jakewarthongithub.data.api.model.Repository;
import com.github.jakewarthongithub.data.api.params.Order;
import com.github.jakewarthongithub.data.api.params.Sort;
import com.github.jakewarthongithub.data.api.params.Type;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GithubRepoService {

  @GET("/users/JakeWharton/repos") //
  void  repositories( //
                      @Query("type") Type type, //
                      @Query("sort") Sort sort, //
                      @Query("direction") Order order,
                      Callback<List<Repository>> callback);
}
