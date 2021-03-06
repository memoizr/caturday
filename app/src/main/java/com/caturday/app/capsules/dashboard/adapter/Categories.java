package com.caturday.app.capsules.dashboard.adapter;

public enum Categories {
    CATEGORY_CATURDAY("caturday"),
    CATEGORY_SUNGLASSES("sunglasses"),
    CATEGORY_HATS("hats");

    private final String category;

    Categories(final String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

    public static CharSequence getCategory(int position) {
        switch (position) {
            case 0:
                return "Caturday";
            case 1:
                return "Sunglasses";
            default:
                return "Hats";
        }
    }
}
