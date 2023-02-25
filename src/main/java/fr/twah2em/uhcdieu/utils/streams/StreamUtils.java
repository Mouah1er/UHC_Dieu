package fr.twah2em.uhcdieu.utils.streams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class StreamUtils {
    public static <T> void forEachIndexed(List<T> list, BiConsumer<Integer, T> biConsumer) {
        for (int i = 0; i < list.size(); i++) {
            biConsumer.accept(i, list.get(i));
        }
    }

    public static <T> void fillEmptyElements(List<T> list, T element) {
        forEachIndexed(list, (index, e) -> {
            if (e == null) {
                list.set(index, element);
            }
        });
    }

    @SafeVarargs
    public static <T> Stream<T> appendToStream(Stream<? extends T> stream, T... elements) {
        return Stream.concat(stream, Stream.of(elements));
    }

    public static List<String> concatPlayersNameWithColor(List<UUID> list, ChatColor color) {
        return list
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> color + player.getName())
                .toList();
    }

    public static <K, V> K getKeyByValue(HashMap<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public static <T> List<T> halfList(List<T> list) {
        return list.subList(0, list.size() / 2);
    }

    public static <T> List<T> removeDuplicates(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>(list1);
        list.addAll(list2);
        return new ArrayList<>(new HashSet<>(list));
    }
}