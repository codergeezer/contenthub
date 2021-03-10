package com.codergeezer.contenthub.event;

import com.codergeezer.core.base.event.CoreEvent;

/**
 * @author haidv
 * @version 1.0
 */
public enum Event implements CoreEvent {
    UPDATE_TOTAL_LIKE("handleEventUpdateTotalLike"),
    UPDATE_TOTAL_READ("handleEventUpdateTotalRead"),
    UPDATE_TOTAL_COMMENT("handleEventUpdateTotalComment"),
    UPGRADE_TIER("handleEventUpgradeTier"),
    DONATE("handleEventDonate"),
    UNLOCK_POST("handleEventUnlockPost");

    private final String handleEventFunctionName;

    private String handleEventClassName;

    Event(String handleEventFunctionName) {
        this.handleEventFunctionName = handleEventFunctionName;
    }

    Event(String handleEventFunctionName, String handleEventClassName) {
        this.handleEventFunctionName = handleEventFunctionName;
        this.handleEventClassName = handleEventClassName;
    }

    public String getHandleEventClassName() {
        return handleEventClassName;
    }

    public String getHandleEventFunctionName() {
        return handleEventFunctionName;
    }

    public String getEventName() {
        return this.name();
    }
}
