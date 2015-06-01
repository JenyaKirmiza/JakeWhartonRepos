package com.github.jakewarthongithub.data.api.model;

import java.util.List;

public class RepositoriesResponse {
    public List<Repository> items;

    public RepositoriesResponse(List<Repository> items) {
        this.items = items;
    }
}
