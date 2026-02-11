package com.jfood.jFood.entity;

public enum DishType {
    BREAKFASTS("Завтраки", "🥞"),
    SNACKS("Закуски", "🍿"),
    SALADS("Салаты", "🥗"),
    SOUPS("Супы", "🥣"),
    HOT_DISHES("Горячие блюда", "🍲"),
    SIDE_DISHES("Гарниры", "🥘"),
    DESSERTS("Десерты", "🍰");

    private final String displayName;
    private final String emoji;

    DishType(String displayName, String emoji) {
        this.displayName = displayName;
        this.emoji = emoji;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmoji() {
        return emoji;
    }
}

