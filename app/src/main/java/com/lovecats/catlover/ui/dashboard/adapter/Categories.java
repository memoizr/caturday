package com.lovecats.catlover.ui.dashboard.adapter;

/**
 * Created by user on 02/04/15.
 */
public enum Categories {
    CATEGORY_SPACE("space"),
    CATEGORY_HATS("hats");

    private final String category;

    private Categories(final String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

    public static CharSequence getCategory(int position) {
        switch (position) {
            case 0:
                return "Space";
            case 1:
                return "Hats";
            default:
                return "Space";
        }
    }
}
