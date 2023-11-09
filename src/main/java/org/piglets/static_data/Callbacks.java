package org.piglets.static_data;

public enum Callbacks {
    CANCEL_PARTNERSHIP_CALLBACK,
    APPROVE_PARTNERSHIP_CALLBACK,
    REJECT_PARTNERSHIP_CALLBACK,
    CANCEL_BREAK_CALLBACK,
    APPROVE_BREAK_CALLBACK,
    REJECT_BREAK_CALLBACK,
    BREAK_PIGLET_CALLBACK,
    ADD_NOTE_CALLBACK;

    public String setValue(String data) {
        return "%s_%s".formatted(this, data);
    }

    public static String getValue(String callback) {
        var split =  callback.split("_");
        return split[split.length - 1];
    }
}
