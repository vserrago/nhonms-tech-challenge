package net.serrago.ntc.filter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An implementation of {@link PrefixFilter} that naively iterates over each prefix to determine a match.
 */
public class SimplePrefixFilter implements PrefixFilter {

    private final Set<String> prefixes;

    public SimplePrefixFilter() {
        prefixes = new LinkedHashSet<>();
    }

    @Override
    public void initialize(Collection<String> prefixes) {
        // Adding all the prefixes to the set ensures that the given collection is de-duplicated.
        this.prefixes.addAll(prefixes);
    }

    @Override
    public boolean matches(String input) {
        // If null or empty, return false
        if (input == null || input.isBlank()) {
            return false;
            //TODO: May not be necessary
        } else if (prefixes.isEmpty()) {
            return true;
        }
        return prefixes.stream().anyMatch(input::startsWith);
    }
}
