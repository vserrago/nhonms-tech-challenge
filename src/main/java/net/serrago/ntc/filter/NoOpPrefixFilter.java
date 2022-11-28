package net.serrago.ntc.filter;

import java.util.Collection;

public class NoOpPrefixFilter implements PrefixFilter {
    @Override
    public void initialize(Collection<String> prefixes) {
    }

    @Override
    public boolean matches(String input) {
        return false;
    }
}
