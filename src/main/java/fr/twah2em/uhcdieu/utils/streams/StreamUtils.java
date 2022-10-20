package fr.twah2em.uhcdieu.utils.streams;

import java.util.List;
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
}