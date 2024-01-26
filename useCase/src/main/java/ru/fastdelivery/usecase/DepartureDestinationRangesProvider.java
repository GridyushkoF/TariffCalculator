package ru.fastdelivery.usecase;

import java.math.BigDecimal;

public interface DepartureDestinationRangesProvider {
    BigDecimal minLatitude();

    BigDecimal maxLatitude();

    BigDecimal minLongitude();

    BigDecimal maxLongitude();
}
