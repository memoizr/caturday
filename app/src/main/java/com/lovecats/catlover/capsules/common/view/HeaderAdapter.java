package com.lovecats.catlover.capsules.common.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class HeaderAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_ITEM = 1;

    protected boolean isPositionHeader(int position) {
        return position == 0;
    }

    protected class EmptyHeader extends RecyclerView.ViewHolder {

        public EmptyHeader(View itemView) {
            super(itemView);
        }
    }
}
