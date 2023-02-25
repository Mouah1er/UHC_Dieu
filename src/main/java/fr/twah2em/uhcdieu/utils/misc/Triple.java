package fr.twah2em.uhcdieu.utils.misc;

public interface Triple <L, M, R> {
    static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
        return new Triple<>() {
            @Override
            public L left() {
                return left;
            }

            @Override
            public M middle() {
                return middle;
            }

            @Override
            public R right() {
                return right;
            }
        };
    }

    L left();
    M middle();
    R right();
}
