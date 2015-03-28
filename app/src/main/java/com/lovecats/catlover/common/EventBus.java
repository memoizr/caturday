package com.lovecats.catlover.common;

import com.squareup.otto.Bus;

/**
 * Created by user on 17/03/15.
 */
public class EventBus {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
