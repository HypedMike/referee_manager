package com.hypedmike.referee_manager.packages.match.models;

public enum MatchEventType {
    GOAL,
    FOUL;

    public static boolean isValid(String value) {
        for (MatchEventType type : MatchEventType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}