package com.caturday.app.capsules.common.view.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.caturday.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class EmptyView extends FrameLayout {

    protected final Context context;
    @InjectView(R.id.image) ImageView imageIV;
    @InjectView(R.id.title) TextView titleTV;
    @InjectView(R.id.subtitle) TextView subTitleTV;
    @InjectView(R.id.empty_CV) CardView container;

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v_empty_view, this, true);

        this.context = context;

        ButterKnife.inject(this);

        setContent();
        setAction();
    }

    public EmptyView(Context context) {
        this(context, null);
    }

    protected abstract void setContent();

    protected abstract void setAction();
}
