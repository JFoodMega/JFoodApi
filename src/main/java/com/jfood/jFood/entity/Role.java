package com.jfood.jFood.entity;

public enum Role {
    CLIENT("Клиент", "👕"),
    COURIER("Курьер", "🚚"),
    MODERATOR("Модератор", "🧑‍💻"),
    ADMIN("Администратор", "🧑‍💼");

    private final String displayName;
    private final String emoji;

    Role(String displayName, String emoji) {
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

