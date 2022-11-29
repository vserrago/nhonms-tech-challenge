package net.serrago.ntc.filter;

import net.serrago.ntc.trie.TrieNode;

import java.util.Collection;

/**
 * A filter using a trie, or prefix tree. When matching against a filterset, the complexity of the match is O(n), as it
 * will take at most n node traversals in the trie to determine whether the input is matched against all prefixes.
 */
public class TriePrefixFilter implements PrefixFilter {

    private final TrieNode trie;

    public TriePrefixFilter() {
        trie = new TrieNode();
    }

    @Override
    public void initialize(Collection<String> prefixes) {
        if (prefixes == null) {
            return;
        }

        for (String prefix : prefixes) {
            trie.insert(prefix);
        }
    }

    @Override
    public boolean matches(String input) {
        return trie.contains(input);
    }
}
