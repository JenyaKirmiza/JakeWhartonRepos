package com.github.jakewarthongithub.data.api.model;

import android.support.annotation.NonNull;

import static com.github.jakewarthongithub.util.Preconditions.checkNotNull;

public final class CommitInner {

    @NonNull
    public final Author author;

    @NonNull
    public final String message;


    private CommitInner(Builder builder) {
        this.message = checkNotNull(builder.message, "message == null");
        this.author = checkNotNull(builder.author, "author == null");
    }

    @Override
    public String toString() {
        return "Commit{" +
                '}';
    }

    public static final class Builder {
        private Author author;
        private String message;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public CommitInner build() {
            return new CommitInner(this);
        }
    }
}
