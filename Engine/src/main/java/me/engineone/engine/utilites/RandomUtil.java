package me.engineone.engine.utilites;

import com.google.common.collect.ImmutableMap;
import me.engineone.engine.utilites.color.Palette;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import static me.engineone.core.Extensions.range;

public final class RandomUtil {
    private static final Random RANDOM = new Random();

    private RandomUtil() {

    }

    private static final Map<ChatColor, Color> CHAT_COLOR_MAP = ImmutableMap.<ChatColor, Color>builder()
            .put(ChatColor.BLACK, Color.fromRGB(0, 0, 0))
            .put(ChatColor.DARK_BLUE, Color.fromRGB(0, 0, 170))
            .put(ChatColor.DARK_GREEN, Color.fromRGB(0, 170, 0))
            .put(ChatColor.DARK_AQUA, Color.fromRGB(0, 170, 170))
            .put(ChatColor.DARK_RED, Color.fromRGB(170, 0, 0))
            .put(ChatColor.DARK_PURPLE, Color.fromRGB(170, 0, 170))
            .put(ChatColor.GOLD, Color.fromRGB(255, 170, 0))
            .put(ChatColor.GRAY, Color.fromRGB(170, 170, 170))
            .put(ChatColor.DARK_GRAY, Color.fromRGB(85, 85, 85))
            .put(ChatColor.BLUE, Color.fromRGB(85, 85, 255))
            .put(ChatColor.GREEN, Color.fromRGB(85, 255, 85))
            .put(ChatColor.AQUA, Color.fromRGB(85, 255, 255))
            .put(ChatColor.RED, Color.fromRGB(255, 85, 85))
            .put(ChatColor.LIGHT_PURPLE, Color.fromRGB(255, 85, 255))
            .put(ChatColor.YELLOW, Color.fromRGB(255, 255, 85))
            .put(ChatColor.WHITE, Color.fromRGB(255, 255, 255)).build();

    public static Color toColor(ChatColor chatColor) {
        Color color = CHAT_COLOR_MAP.get(chatColor);
        if (color != null) {
            return color;
        } else {
            return Color.WHITE;
        }
    }

    public static boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }

    public static Color randomColor() {
        return random(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY,
                Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW);
    }

    @SafeVarargs
    public static <T> T random(T... items) {
        return items[random(items.length - 1)];
    }

    public static ChatColor[] random(Palette palette, int amount) {
        ChatColor[] result = new ChatColor[amount];
        range(amount - 1, i -> result[i] = random(palette));
        return result;
    }

    public static ChatColor random(Palette palette) {
        switch (random(4)) {
            case 0:
                return palette.getSecondary();
            case 1:
                return palette.getAccent();
            case 2:
                return palette.getDarkShader();
            case 3:
                return palette.getLightShader();
            default:
                return palette.getPrimary();
        }
    }

    public static <T extends Enum<?>> T random(Class<T> clazz) {
        return random(clazz.getEnumConstants());
    }

    public static <T extends Enum<?>> T random(Class<T> type, Predicate<T> filter) {
        T result;
        do result = random(type.getEnumConstants());
        while (!filter.test(result));
        return result;
    }

    public static int random(int max) {
        return RANDOM.nextInt(max + 1);
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
