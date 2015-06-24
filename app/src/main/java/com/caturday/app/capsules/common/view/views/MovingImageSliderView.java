package com.caturday.app.capsules.common.view.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.caturday.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Sets a custom layout for SliderView
 */
public class MovingImageSliderView extends BaseSliderView {

    @InjectView(R.id.slider_image) ImageView target;

    public MovingImageSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.v_slide_show_page, null);

        ButterKnife.inject(this, v);

        bindEventAndShow(v, target);

        return v;
    }
}
