package com.lovecats.catlover.capsules.common.view.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.util.helper.ColorHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by user on 14/03/15.
 */
public class ExpandingView extends RelativeLayout{
    @InjectView(R.id.caption_container_V) ViewGroup caption_container_V;
    @InjectView(R.id.user_image_IV) View user_image;
    @InjectView(R.id.user_name_TV) TextView username;
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
    private int activeColor;
    private int neutralColor;

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

        neutralColor = resources.getColor(R.color.black);
        activeColor = resources.getColor(R.color.white);
    }

    public void setTextcolors(int neutralColor, int activeColor) {
        this.neutralColor = neutralColor;
        this.activeColor = activeColor;
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void setExpandedLevel(float level){
        if (level != expandedLevel) {
            expandedLevel = level;
            collapseScale = (float) (0.5*Math.tanh( 6 * expandedLevel - 3) + 0.5);
            username.setTextColor(ColorHelper.interpolateColor(neutralColor, activeColor, 1 - collapseScale));
            updateLayoutParams();
            updateUserImageMargins();
        }
    }

    private void updateUserImageMargins() {
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) user_image.getLayoutParams();
        params.topMargin =
                (int) interpolate(logoMinTopMargin, logoMaxTopMargin, expandedLevel);
        params.leftMargin =
                (int) interpolate(logoMinLeftMargin, logoMaxLeftMargin, collapseScale);
        user_image.setLayoutParams(params);
    }

    private void updateLayoutParams() {
        updateContainerHeight();
        requestLayout();
    }

    private void updateContainerHeight() {
        ViewGroup.LayoutParams params = caption_container_V.getLayoutParams();
        params.height = (int) interpolate(minHeight, maxHeight, collapseScale);
        caption_container_V.setLayoutParams(params);
    }

    private static float interpolate(float min, float max, float level) {
        return (max - min) * level + min;
    }
}
