package com.github.jakewarthongithub.data.api.model;

import android.widget.Spinner;

import com.github.jakewarthongithub.data.api.model.Repository;
import com.github.jakewarthongithub.ui.adapters.SpinnerEnum;
import com.github.jakewarthongithub.ui.adapters.SpinnerEnumAdapter;

import java.util.Comparator;

public class RepoComparator implements Comparator<Repository> {
    private SpinnerEnum spinnerEnum;

    public RepoComparator(SpinnerEnum spinnerEnum) {
        this.spinnerEnum = spinnerEnum;
    }

    @Override
    public int compare(Repository o1, Repository o2) {
        switch (spinnerEnum) {
            case STAR:
                return (int) (o2.stars - o1.stars);
            case FORK:
                return (int) (o2.forks - o1.forks);
            case UPDATE:
                return (int) (o2.updatedAt.toDate().getTime() - o1.updatedAt.toDate().getTime());
            case CREATED:
                return (int) (o2.createdAt.toDate().getTime() - o1.createdAt.toDate().getTime());
            default:
                return (int) (o2.stars - o1.stars);
        }
    }
}