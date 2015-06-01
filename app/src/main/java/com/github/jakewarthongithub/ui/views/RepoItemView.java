package com.github.jakewarthongithub.ui.views;

import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jakewarthongithub.R;
import com.github.jakewarthongithub.data.api.model.Repository;
import com.github.jakewarthongithub.ui.animation.CircleStrokeTransformation;
import com.github.jakewarthongithub.ui.animation.Truss;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class RepoItemView extends RelativeLayout {
    private final CircleStrokeTransformation avatarTransformation;
    private final int descriptionColor;
    @InjectView(R.id.repository_avatar)
    ImageView avatarView;
    @InjectView(R.id.repository_name)
    TextView nameView;
    @InjectView(R.id.repository_description)
    TextView descriptionView;
    @InjectView(R.id.repository_stars)
    TextView starsView;
    @InjectView(R.id.repository_forks)
    TextView forksView;

    public RepoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColorSecondary, outValue, true);
        descriptionColor = outValue.data;

        // TODO: Make this a singleton.
        avatarTransformation =
                new CircleStrokeTransformation(context, getResources().getColor(R.color.avatar_stroke), 1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void bindTo(Repository repository, Picasso picasso) {
        picasso.load(repository.owner.avatarUrl)
                .placeholder(R.drawable.avatar)
                .fit()
                .transform(avatarTransformation)
                .into(avatarView);
        nameView.setText(repository.name);
        starsView.setText(String.valueOf(repository.stars));
        forksView.setText(String.valueOf(repository.forks));

        Truss description = new Truss();
        description.append(repository.owner.login);

        if (repository.description != null) {
            description.pushSpan(new ForegroundColorSpan(descriptionColor));
            description.append(" — ");
            description.append(repository.description);
            description.popSpan();
        }

        descriptionView.setText(description.build());
    }
}
