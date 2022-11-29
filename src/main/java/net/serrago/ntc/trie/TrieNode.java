package net.serrago.ntc.trie;

/**
 * A node in a trie, or prefix tree. This prefix tree is geared towards SNMP OIDs, only being able to hold the
 * 0,1,2,3,4,5,6,7,8,9, and '.' characters.
 */
public class TrieNode {
    private static final int DOT_CHAR_VALUE = '.' - '0';
    private static final int DOT_CHAR_CHILD_INDEX = 10;

    private final TrieNode[] children;

    /** Represents whether this node is the end of a prefix, and therefore a terminal node in the trie. */
    private boolean end;

    public TrieNode() {
        children = new TrieNode[11];
    }

    /**
     * Inserts the given value into the trie. An empty string is treated as a match against all values. Null values are
     * treated the same as empty strings.
     *
     * @param value The value to insert. Must be null, empty, or a string consisting of only the 0,1,2,3,4,5,6,7,8,9,
     * and '.' characters.
     * @throws IllegalArgumentException If a string with illegal characters is used as a parameter.
     */
    public void insert(String value) {
        if (value == null || value.isEmpty()) {
            end = true;
            return;
        }
        insert(value, 0);
    }

    private void insert(String value, int index) {
        if (value.length() <= index) {
            end = true;
            return;
        }

        int childIndex = childIndexOf(value.charAt(index));

        if (children[childIndex] == null) {
            children[childIndex] = new TrieNode();
        }
        children[childIndex].insert(value, index + 1);
    }

    /**
     * Checks the given value against the trie. If the value is prefixed by a value in this trie, then the trie is
     * considered to contain this value.
     *
     * @param value The value to insert. Must be null, empty, or a string consisting of only the 0,1,2,3,4,5,6,7,8,9,
     * and '.' characters.
     * @return Whether the given value has a prefix matching in this trie.
     * @throws IllegalArgumentException If a string with illegal characters is used as a parameter.
     */
    public boolean contains(String value) {
        if (value == null || value.isEmpty()) {
            return end;
        }
        return contains(value, 0);
    }

    private boolean contains(String value, int index) {
        if (value.length() <= index) {
            return end;
        }

        int childIndex = childIndexOf(value.charAt(index));

        if (children[childIndex] == null) {
            return end;
        }

        return children[childIndex].contains(value, index + 1);
    }

    private int childIndexOf(char c) {
        int charValue = c - '0';

        int childIndex = charValue == DOT_CHAR_VALUE
                ? DOT_CHAR_CHILD_INDEX
                : charValue;
        if (childIndex < 0 || childIndex > children.length) {
            throw new IllegalArgumentException(String.format(
                    "Illegal character '%c' in trie", c
            ));
        }

        return childIndex;
    }
}
