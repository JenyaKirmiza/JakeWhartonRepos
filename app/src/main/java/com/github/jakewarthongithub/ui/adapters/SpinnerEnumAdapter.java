package com.github.jakewarthongithub.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jakewarthongithub.R;


public class SpinnerEnumAdapter extends EnumAdapter<SpinnerEnum> {
  public SpinnerEnumAdapter(Context context) {
    super(context, SpinnerEnum.class);
  }

  @Override
  public View newView(LayoutInflater inflater, int position, ViewGroup container) {
    return inflater.inflate(R.layout.spinner_view, container, false);
  }
}
