package net.serrago.ntc.filter;

import net.serrago.ntc.trie.Node;

import java.util.Collection;

public class TriePrefixFilter implements PrefixFilter {

    private Node node;

    @Override
    public void initialize(Collection<String> prefixes) {
        node = new Node();
        for (String prefix : prefixes) {
            node.insert(prefix);
        }
    }

    @Override
    public boolean matches(String input) {
        return node.contains(input);
    }
}
