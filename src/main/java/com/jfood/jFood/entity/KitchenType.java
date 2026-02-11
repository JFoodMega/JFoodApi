package com.jfood.jFood.entity;

public enum KitchenType {
    RUSSIAN("Русская", "🇷🇺"),
    FRENCH("Французская", "🇫🇷"),
    ASIAN("Азиатская", "🇯🇵"),
    CAUCASIAN("Кавказская", "🇦🇲"),
    ITALIAN("Итальянская", "🇮🇹"),
    MEDITERRANEAN("Средиземноморская", "🇬🇷"),
    FUSION("Фьюжн", "🌍"),
    MOLECULAR("Молекулярная", "🔬");

    private final String displayName;
    private final String emoji;

    KitchenType(String displayName, String emoji) {
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

