package net.serrago.ntc.filter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An implementation of {@link PrefixFilter} that naively iterates over each prefix to determine a match. When matching
 * against a filterset, the complexity of the match is O(n^2), as it will iterate over each individual character in each
 * individual prefix to determine whether the input is matched.
 */
public class SimplePrefixFilter implements PrefixFilter {

    private final Set<String> prefixes;

    public SimplePrefixFilter() {
        prefixes = new LinkedHashSet<>();
    }

    @Override
    public void initialize(Collection<String> prefixes) {
        // Adding all the prefixes to the set ensures that the given collection is de-duplicated.
        if (prefixes != null) {
            prefixes.stream()
                    .map(s -> s == null ? "" : s)
                    .forEach(this.prefixes::add);
        }
    }

    @Override
    public boolean matches(String input) {
        if (input == null) {
            input = "";
        }

        return prefixes.stream().anyMatch(input::startsWith);
    }
}
