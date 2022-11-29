package net.serrago.ntc.filter;

import net.serrago.ntc.trie.TrieNode;

import java.util.Collection;

public class TriePrefixFilter implements PrefixFilter {

    private TrieNode trie;

    @Override
    public void initialize(Collection<String> prefixes) {
        trie = new TrieNode();
        for (String prefix : prefixes) {
            trie.insert(prefix);
        }
    }

    @Override
    public boolean matches(String input) {
        return trie.contains(input);
    }
}
