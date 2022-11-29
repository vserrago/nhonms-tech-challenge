package net.serrago.ntc.filter;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class FilterSelector {

    private final EnumMap<FilterAlgorithm, Supplier<PrefixFilter>> algorithms;

    public FilterSelector() {
        algorithms = new EnumMap<>(FilterAlgorithm.class);
        algorithms.putAll(Map.of(
                FilterAlgorithm.NO_OP, NoOpPrefixFilter::new,
                FilterAlgorithm.SIMPLE, SimplePrefixFilter::new,
                FilterAlgorithm.TRIE, TriePrefixFilter::new
        ));

    }

    public PrefixFilter select(FilterAlgorithm algorithm) {
        Objects.requireNonNull(algorithm, "algorithm cannot be null");
        return algorithms.get(algorithm).get();
    }
}
