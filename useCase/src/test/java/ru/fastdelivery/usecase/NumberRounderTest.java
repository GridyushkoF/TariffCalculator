package ru.fastdelivery.usecase;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.usecase.calculators.NumberRounder;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberRounderTest {
    NumberRounder numberRounder = new NumberRounder();

    @Test
    void whenRoundNumber_thenSuccess() {
        var expected = 8.77;
        var actual = numberRounder.roundNumber(8.7644444);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenRoundNumber2_thenSuccess() {
        var expected = 9;
        var actual = numberRounder.roundNumber(8.999999);
        assertThat(actual).isEqualTo(expected);
    }
}
