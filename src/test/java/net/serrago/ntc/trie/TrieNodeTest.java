package net.serrago.ntc.trie;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TrieNodeTest {

    static Stream<Arguments> expectedMatches() {
        return Stream.of(
                arguments("", ""),
                arguments("", "."),
                arguments("", ".1"),
                arguments("", ".1.3"),
                arguments("", ".1.3.6.1.4.1.9.9.117.2"),
                arguments(null, ""),
                arguments(null, "."),
                arguments(null, ".1"),
                arguments(null, ".1.3"),
                arguments(null, ".1.3.6.1.4.1.9.9.117.2"),
                arguments(".", "."),
                arguments(".", ".1"),
                arguments(".", ".1.3"),
                arguments(".", ".1.3.6.1.4.1.9.9.117.2"),
                arguments(".1", ".1"),
                arguments(".1", ".1.3"),
                arguments(".1.3", ".1.3"),
                arguments(".1.3", ".1.3.6.1.4.1.9.9.117.2"),
                arguments(".1.3.6.1.4.1.9.9.117.2.0.1", ".1.3.6.1.4.1.9.9.117.2.0.1")
        );
    }

    @MethodSource("expectedMatches")
    @ParameterizedTest
    void givenInsertedValue_expectToContainExpectedValue(String insertValue, String expectedMatch) {
        var trieNode = new TrieNode();
        trieNode.insert(insertValue);
        assertThat(trieNode.contains(expectedMatch))
                .isTrue();
    }

    static Stream<Arguments> expectedMisses() {
        return Stream.of(
                arguments(".", ""),
                arguments(".", null),
                arguments(".2", ".1.3.6.1.4.1.9.9.117.2"),
                arguments(".1.3.6.1.4.1.9.9.117.2", ".1.3.6.1.4.1.9.9.117")
        );
    }

    @MethodSource("expectedMisses")
    @ParameterizedTest
    void givenInsertedValue_expectToNotContainExpectedValue(String insertValue, String expectedMatch) {
        var trieNode = new TrieNode();
        trieNode.insert(insertValue);
        assertThat(trieNode.contains(expectedMatch))
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "aword",
            "a word",
            "    ",
            "?"
    })
    void givenIllegalInsertParameter_expectIllegalArgumentException(String illegalInsertValue) {
        var trieNode = new TrieNode();
        assertThatThrownBy(() -> trieNode.insert(illegalInsertValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "aword",
            "a word",
            "    ",
            "?"
    })
    void givenIllegalContainsParameter_expectFalse(String illegalContainsValue) {
        var trieNode = new TrieNode();
        trieNode.insert("1.1.1.1.1.1.1.1.1.1.1.1.1.1");
        assertThat(trieNode.contains(illegalContainsValue))
                .isFalse();
    }
}