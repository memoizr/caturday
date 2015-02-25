package com.lovecats.catlover.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecats.catlover.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by user on 24/02/15.
 */
public class CollapsibleView extends FrameLayout {
    @InjectView(R.id.profileContainer_RL) ViewGroup profileContainer_RL;
//    @InjectView(R.id.title_TV) TextView title_TV;

    // From 0 to 1, how much should it be open, 1 = fully open

    @Getter
    private float collapseLevel = 1f;

    @Getter private final int minHeight;
    @Getter private final int maxHeight;
    private final int displayNameMaxMargin;
    private final int displayNameMinMargin;

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

        maxHeight = resources.getDimensionPixelSize(R.dimen.title_height);

        displayNameMaxMargin = resources.getDimensionPixelSize(R.dimen.display_name_max_margin);
        displayNameMinMargin = resources.getDimensionPixelSize(R.dimen.display_name_min_margin);

        updateLayoutParams();
    }

    public CollapsibleView(Context context) {
        this(context, null);
    }

    public void setCollapseLevel(float level){
        if (level != collapseLevel) {
            collapseLevel = level;
            updateLayoutParams();
        }
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
