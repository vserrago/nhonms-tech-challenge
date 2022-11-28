package net.serrago.ntc.trie;

public class Node {
    private static final int DOT_CHAR_VALUE = '.' - '0';
    private static final int DOT_CHAR_INDEX = 10;

    private final Node[] children;
    private boolean end;

    public Node() {
        children = new Node[11];
    }

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
            children[childIndex] = new Node();
        }
        children[childIndex].insert(value, index + 1);
    }

    public boolean contains(String value) {
        if (end && (value == null || value.isEmpty())) {
            return true;
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
                ? DOT_CHAR_INDEX
                : charValue;
        if (childIndex > children.length) {
            throw new IllegalArgumentException(String.format(
                    "Illegal character '%c' in trie", c
            ));
        }

        return childIndex;
    }
}
