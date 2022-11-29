package net.serrago.ntc.filter;

import java.util.Arrays;

/**
 * An enum representing what filter algorithms are available to be filtered by.
 */
public enum FilterAlgorithm {
    /**
     * @see NoOpPrefixFilter
     */
    NO_OP("no-op"),
    /**
     * @see SimplePrefixFilter
     */
    SIMPLE("simple"),
    /**
     * @see TriePrefixFilter
     */
    TRIE("trie");

    private final String label;

    public static FilterAlgorithm fromLabel(String label) {
        return Arrays.stream(FilterAlgorithm.values())
                .filter(algorithm -> algorithm.getLabel().equalsIgnoreCase(label))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    FilterAlgorithm(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
