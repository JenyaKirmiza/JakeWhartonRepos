package com.github.jakewarthongithub.data.api.model;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import static com.github.jakewarthongithub.util.Preconditions.checkNotNull;

public final class Author {

    @NonNull
    public final String name;

    @NonNull
    public final String email;

    @NonNull
    public final DateTime date;


    public Author(String name, String email, DateTime date) {
        this.date = checkNotNull(date, "date == null");
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
