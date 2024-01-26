package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimensions.Dimensions;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        assertThatThrownBy(() -> new Pack(weight,new Dimensions(100,100,100)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWeightLessThanMaxWeight_thenObjectCreated() {
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)),new Dimensions(100,100,100));
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
    @Test
    @DisplayName("Нормализация габаритов -> успешно")
    void whenNormalizeDimensions_thenSuccess() {
        var notNormalizedDimensions = new Dimensions(
                452,
                653,
                1001
        );
        var expected = new Dimensions(
                450,
                650,
                1000
        );
        assertThat(Pack.normalizeDimensions(notNormalizedDimensions)).isEqualTo(expected);
    }
}