package ru.fastdelivery.usecase;

import java.math.BigDecimal;

public interface DepartureDestinationRangesProvider {
    BigDecimal getMinLatitude();

    BigDecimal getMaxLatitude();

    BigDecimal getMinLongitude();

    BigDecimal getMaxLongitude();
}
