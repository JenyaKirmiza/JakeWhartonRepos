package com.github.jakewarthongithub.data.api.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.apache.http.auth.AUTH;
import org.joda.time.DateTime;

import static com.github.jakewarthongithub.util.Preconditions.checkNotNull;

public final class Commit {

    @NonNull
    public final CommitInner commit;

    private Commit(Builder builder) {

        this.commit = checkNotNull(builder.commit, "author == null");
    }

    @Override
    public String toString() {
        return "Commit{" +
                "message='";
    }

    public static final class Builder {

        private CommitInner commit;


        public Builder author(CommitInner commit) {
            this.commit = commit;
            return this;
        }

        public Commit build() {
            return new Commit(this);
        }
    }
}
