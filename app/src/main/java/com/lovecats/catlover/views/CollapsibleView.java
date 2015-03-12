package com.lovecats.catlover.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lovecats.catlover.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by user on 24/02/15.
 */
public class CollapsibleView extends FrameLayout {
    @InjectView(R.id.titleContainer_RL) ViewGroup profileContainer_RL;
    @InjectView(R.id.logo_IV) ImageView logo_IV;

    // From 0 to 1, how much should it be open, 1 = fully open

    @Getter
    private float collapseLevel = 1f;

    @Getter private final int minHeight;
    @Getter private final int maxHeight;
    private int logoMaxLeftMargin;
    private int logoMinLeftMargin;
    private int logoMaxTopMargin;
    private int logoMinTopMargin;
    private int logoMaxHeight;
    private int logoMinHeight;
    private int offset;

    public CollapsibleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v_collapsable_profile, this, true);

        ButterKnife.inject(this);

        // Get the size of the action bar as specified by the library, it
        // will change depending on screen orientation and screen size
        Resources resources = getResources();
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
        minHeight = getResources().getDimensionPixelSize(tv.resourceId);


        logoMinLeftMargin = resources.getDimensionPixelSize(R.dimen.logo_min_left_margin);
        logoMinTopMargin = resources.getDimensionPixelSize(R.dimen.logo_min_top_margin);

        logoMaxTopMargin = resources.getDimensionPixelSize(R.dimen.logo_max_top_margin);
        logoMinTopMargin = resources.getDimensionPixelSize(R.dimen.logo_min_top_margin);

        logoMinHeight = resources.getDimensionPixelSize(R.dimen.logo_min_height);
        logoMaxHeight = resources.getDimensionPixelSize(R.dimen.logo_max_height);

        offset = resources.getDimensionPixelSize(R.dimen.size_xlarge);

        int logoWidth = resources.getDimensionPixelSize(R.dimen.logo_max_width);

        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        MarginLayoutParams params =
                (MarginLayoutParams) logo_IV.getLayoutParams();
        logo_IV.setLayoutParams(params);
        logoMaxLeftMargin = ((displayWidth - logoWidth ) / 2) - offset;

        maxHeight = resources.getDimensionPixelSize(R.dimen.title_height);
        updateLogo();

        updateLayoutParams();
    }

    public CollapsibleView(Context context) {
        this(context, null);
    }

    public void setCollapseLevel(float level){
        if (level != collapseLevel) {
            collapseLevel = level;
            updateLayoutParams();
            updateLogo();
        }
    }

    private void updateLogo() {
        MarginLayoutParams params =
                (MarginLayoutParams) logo_IV.getLayoutParams();
        float collapseScale = (float) (0.5*Math.tanh( 6 * collapseLevel - 3) + 0.5);
        params.topMargin =
                (int) interpolate(logoMinTopMargin, logoMaxTopMargin, collapseLevel);
        params.leftMargin =
                (int) interpolate(logoMinLeftMargin, logoMaxLeftMargin, collapseScale);
        logo_IV.setLayoutParams(params);

        logo_IV.setPivotX(0);
        logo_IV.setPivotY(0);
        logo_IV.setScaleX(interpolate(logoMinHeight, logoMaxHeight, collapseScale)/logoMaxHeight);
        logo_IV.setScaleY(interpolate(logoMinHeight, logoMaxHeight, collapseScale)/logoMaxHeight);
    }

    private void updateLayoutParams() {
        updateContainer();
        requestLayout();
    }

    private void updateContainer() {
        ViewGroup.LayoutParams params = profileContainer_RL.getLayoutParams();
        params.height = (int) interpolate(minHeight, maxHeight, collapseLevel);
        profileContainer_RL.setLayoutParams(params);
    }

    private static float interpolate(float min, float max, float level) {
        return (max - min) * level + min;
    }
}
