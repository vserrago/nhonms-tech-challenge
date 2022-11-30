package net.serrago.ntc.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilterAlgorithmTest {

    @EnumSource
    @ParameterizedTest
    void fromLabel_givenName_expectEnum(FilterAlgorithm algorithm) {
        assertThat(FilterAlgorithm.fromLabel(algorithm.getLabel()))
                .isEqualTo(algorithm);
    }

    @NullAndEmptySource
    @ValueSource(strings = {
            "   ",
            "something"
    })
    @ParameterizedTest
    void fromLabel_givenBadName_expectIllegalArgumentException(String value) {
        assertThatThrownBy(() -> FilterAlgorithm.fromLabel(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void algorithmLabels_expectAllValueLabelsProvided() {
        var expectedLabels = Arrays.stream(FilterAlgorithm.values())
                .map(FilterAlgorithm::getLabel)
                .toList();
        var actualLabels = new ArrayList<String>();
        new FilterAlgorithm.AlgorithmLabels().iterator()
                .forEachRemaining(actualLabels::add);

        assertThat(actualLabels).isEqualTo(expectedLabels);
    }
}