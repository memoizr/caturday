package com.lovecats.catlover.capsules.dashboard.adapter;

public enum Categories {
    CATEGORY_SPACE("space"),
    CATEGORY_CATURDAY("caturday"),
    CATEGORY_SUNGLASSES("sunglasses"),
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
                return "Caturday";
            case 2:
                return "Sunglasses";
            default:
                return "Hats";
        }
    }
}
