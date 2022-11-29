package net.serrago.ntc.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilterSelectorTest {

    private FilterSelector filterSelector;

    @BeforeEach
    void setUp() {
        filterSelector = new FilterSelector();
    }

    @Test
    void doesNotAllowNullValues() {
        assertThatThrownBy(() -> filterSelector.select(null))
                .hasMessage("algorithm cannot be null")
                .isInstanceOf(NullPointerException.class);
    }

    @EnumSource
    @ParameterizedTest
    void givenFilterAlgorithm_expectFilterInstance(FilterAlgorithm algorithm) {
        assertThat(filterSelector.select(algorithm))
                .isInstanceOf(PrefixFilter.class);
    }
}