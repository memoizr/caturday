package com.lovecats.catlover.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.helpers.AnimationHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by user on 14/03/15.
 */
public class ExpandingView extends RelativeLayout{
    @InjectView(R.id.caption_container_V) ViewGroup caption_container_V;
    @InjectView(R.id.user_image_IV) View user_image;
    @InjectView(R.id.user_name_TV) TextView user_name;
    @InjectView(R.id.date_TV) View date;

    // From 0 to 1, how much should it be open, 1 = fully open

    @Getter private float expandedLevel = 1f;
    private int logoMaxLeftMargin;
    private int logoMinLeftMargin;
    private int logoMaxTopMargin;
    private int logoMinTopMargin;
    private float collapseScale;
    @Getter private int minHeight;
    @Getter private int maxHeight;


    public ExpandingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v_expandable_caption, this, true);

        ButterKnife.inject(this);

        Resources resources = getResources();
        logoMinLeftMargin = resources.getDimensionPixelSize(R.dimen.size_small);
        logoMaxLeftMargin = resources.getDimensionPixelSize(R.dimen.size_xxlarge);

        logoMaxTopMargin = resources.getDimensionPixelSize(R.dimen.caption_max_top_margin);
        logoMinTopMargin = resources.getDimensionPixelSize(R.dimen.size_small);

        maxHeight = resources.getDimensionPixelSize(R.dimen.max_caption_height);
        minHeight = resources.getDimensionPixelSize(R.dimen.caption_height);

    }

    public void setExpandedLevel(float level){
        if (level != expandedLevel) {
            expandedLevel = level;
            collapseScale = (float) (0.5*Math.tanh( 6 * expandedLevel - 3) + 0.5);
            updateLayoutParams();
            updateLogo();
        }
    }

    private void updateLogo() {
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) user_image.getLayoutParams();
        params.topMargin =
                (int) interpolate(logoMinTopMargin, logoMaxTopMargin, expandedLevel);
        params.leftMargin =
                (int) interpolate(logoMinLeftMargin, logoMaxLeftMargin, collapseScale);
        user_image.setLayoutParams(params);
    }

    public void animateBackgroundAccent() {
        AnimationHelper.animateColor((View) caption_container_V.getParent(),
                getResources().getColor(R.color.white_translucent),
                getResources().getColor(R.color.accent));

        AnimationHelper.animateTextColor(user_name,
                getResources().getColor(R.color.deepblack),
                getResources().getColor(R.color.white));
    }

    public void animateBackgroundNeutral() {
        AnimationHelper.animateColor((View)caption_container_V.getParent(),
                getResources().getColor(R.color.accent),
                getResources().getColor(R.color.white_translucent));
        AnimationHelper.animateTextColor(user_name,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.deepblack));
    }

    private void updateLayoutParams() {
        updateContainer();
        requestLayout();
    }

    private void updateContainer() {
        ViewGroup.LayoutParams params = caption_container_V.getLayoutParams();
        params.height = (int) interpolate(minHeight, maxHeight, collapseScale);
        System.out.println(params.height);
        caption_container_V.setLayoutParams(params);
    }

    private static float interpolate(float min, float max, float level) {
        return (max - min) * level + min;
    }
}
