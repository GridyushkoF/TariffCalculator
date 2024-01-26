package ru.fastdelivery.usecase;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;
import ru.fastdelivery.usecase.calculators.DistanceCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

import java.math.BigDecimal;

public class DistanceCalculatorTest {
    final DistanceCalculator distanceCalculator = new DistanceCalculator();

    @Test
    void whenCalculateDistance_thenSuccess() {
        var departure = new Departure(
                BigDecimal.valueOf(40.714268),
                BigDecimal.valueOf(-74.005974)
        );
        var destination = new Destination(
                BigDecimal.valueOf(34.0522),
                BigDecimal.valueOf(-118.2437)
        );

        var actual = distanceCalculator.calcDistance(departure, destination);
        var expected = 3944;

        assertThat(actual).isCloseTo(expected, within(0.5));
    }
}