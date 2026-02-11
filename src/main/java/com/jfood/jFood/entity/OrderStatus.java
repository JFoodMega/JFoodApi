package com.jfood.jFood.entity;

public enum OrderStatus {
    CONFIRMATION("Подтверждение", 1, "brown"),
    CONFIRMED("Подтверждено", 1, "orange"),
    DELIVERY("Доставка", 1, "yellow"),
    DELIVERED("Доставлено", 0, "green"),
    CANCELED("Отменено", 0, "red");

    private final String displayName;
    private final Integer priority;
    private final String color;

    OrderStatus(String displayName, Integer priority, String color) {
        this.displayName = displayName;
        this.priority = priority;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getColor() {
        return color;
    }
}

