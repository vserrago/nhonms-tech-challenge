package net.serrago.ntc.filter;

import java.util.Collection;

public interface PrefixFilter {
    void initialize(Collection<String> prefixes);

    // TODO: specify null values in contract
    boolean matches(String input);
}
