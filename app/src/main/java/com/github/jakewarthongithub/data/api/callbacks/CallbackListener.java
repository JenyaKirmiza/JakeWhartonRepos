package com.github.jakewarthongithub.data.api.callbacks;

import com.github.jakewarthongithub.data.api.model.Repository;

import java.util.ArrayList;

/**
 * Created by Jenya on 31.05.2015.
 */
public interface CallbackListener {
    void dataLoaded(ArrayList<Repository> repositoryList,boolean status);
}
