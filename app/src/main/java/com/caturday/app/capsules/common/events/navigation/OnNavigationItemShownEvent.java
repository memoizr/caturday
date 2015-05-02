package com.caturday.app.capsules.common.events.navigation;

import lombok.Getter;

public class OnNavigationItemShownEvent {
    public static final int ITEM_DASHBOARD  = 0;
    public static final int ITEM_FAVORITES  = 1;

    @Getter private int currentItem;

    public OnNavigationItemShownEvent(int item){
        currentItem = item;
    }
}
