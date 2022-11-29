package net.serrago.ntc.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

interface PrefixFilterTest {

    List<String> BASIC_FILTER_PREFIXES = List.of(
            ".1.3.6.1.6.3.1.1.5",
            ".1.3.6.1.2.1.10.166.3",
            ".1.3.6.1.4.1.9.9.117.2",
            ".1.3.6.1.2.1.10.32.0.1",
            ".1.3.6.1.2.1.14.16.2.2",
            ".1.3.6.1.4.1.9.10.137.0.1"
    );

    PrefixFilter getInstance();

    static Stream<Arguments> expectedMatches() {
        return Stream.of(
                arguments(BASIC_FILTER_PREFIXES, ".1.3.6.1.4.1.9.9.117.2.0.1"),
                arguments(BASIC_FILTER_PREFIXES, ".1.3.6.1.4.1.9.9.117.2"),
                arguments(List.of(""), ".1.3.6.1.4.1.9.9.117.2"),
                arguments(List.of(""), ""),
                arguments(List.of(""), null),
                arguments(Collections.singletonList(null), ".1.3.6.1.4.1.9.9.117.2"),
                arguments(Collections.singletonList(null), ""),
                arguments(Collections.singletonList(null), null)
        );
    }

    @MethodSource("expectedMatches")
    @ParameterizedTest(name = "{index}: {1}")
    default void expectMatch(List<String> prefixes, String value) {
        PrefixFilter filter = getInstance();
        filter.initialize(prefixes);

        Assertions.assertThat(filter.matches(value))
                .withFailMessage("Expecting '%s' to be matched in %s", value, prefixes)
                .isTrue();
    }

    static Stream<Arguments> expectedMisses() {
        return Stream.of(
                arguments(BASIC_FILTER_PREFIXES, ".1.3.6.1.4.1.9.9.118.2.0.1"),
                arguments(BASIC_FILTER_PREFIXES, ".1.3.6.1.4.1.9.9.117"),
                arguments(BASIC_FILTER_PREFIXES, null),
                arguments(BASIC_FILTER_PREFIXES, ""),
                arguments(Collections.emptyList(), ".1.3.6.1.4.1.9.9.117.2"),
                arguments(Collections.emptyList(), ""),
                arguments(Collections.emptyList(), null),
                arguments(null, ".1.3.6.1.4.1.9.9.117.2"),
                arguments(null, ""),
                arguments(null, null)
        );
    }

    @MethodSource("expectedMisses")
    @ParameterizedTest(name = "{index}: {1}")
    default void expectNoMatch(List<String> prefixes, String value) {
        PrefixFilter filter = getInstance();
        filter.initialize(prefixes);

        Assertions.assertThat(filter.matches(value))
                .withFailMessage("Expecting '%s' to not be matched in %s", value, prefixes)
                .isFalse();
    }
}