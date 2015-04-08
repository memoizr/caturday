package com.lovecats.catlover.capsules.common.view.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.lovecats.catlover.R;

public class MovingImageSliderView extends BaseSliderView {

    public MovingImageSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.v_slide_show_page, null);
        ImageView target = (ImageView) v.findViewById(R.id.slider_image);
        bindEventAndShow(v, target);
        return v;
    }
}
