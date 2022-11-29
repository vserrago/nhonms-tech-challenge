package net.serrago.ntc.filter;

import java.util.Collection;

/**
 * A prefix filter initializes itself against a list of prefixes, which can then match inputs against those prefixes.
 */
public interface PrefixFilter {
    /**
     * Initializes this filter with a collection of prefix strings. Null values should be treated as if they are empty
     * collections. Due to their nature, collections have the possibility of duplicates. Therefore, any implementing
     * filter should be able to accommodate duplicates.
     * <p>
     * A null or empty prefix implies a wildcard match, matching everything.
     *
     * @param prefixes A {@link Collection} instance.
     */
    void initialize(Collection<String> prefixes);

    /**
     * Checks whether the given input matches against this filter. Null values are treated as empty strings.
     *
     * @param input The value to check against this filter.
     * @return Whether one of the values in the filter is a prefix of this value.
     */
    boolean matches(String input);
}
