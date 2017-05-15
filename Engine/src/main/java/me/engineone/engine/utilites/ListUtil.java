package me.engineone.engine.utilites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class ListUtil {
    private ListUtil() {
    }

    public static <T> T getRandom(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static <T> List<T> concat(List<T> one, List<T> two) {
        List<T> list = new ArrayList<>(one);
        list.addAll(two);
        return list;
    }

    public static boolean containsIgnoreCase(Collection<String> collection, String search) {
        for (String string : collection) {
            if (string.equalsIgnoreCase(search))
                return true;
        }
        return false;
    }

}
