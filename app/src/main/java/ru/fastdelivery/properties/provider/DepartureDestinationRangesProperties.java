package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.usecase.DepartureDestinationRangesProvider;

import java.math.BigDecimal;

@ConfigurationProperties("departure-destination-ranges")
@Setter
public class DepartureDestinationRangesProperties implements DepartureDestinationRangesProvider {
    private BigDecimal minLatitude;
    private BigDecimal maxLatitude;
    private BigDecimal minLongitude;
    private BigDecimal maxLongitude;

    @Override
    public BigDecimal minLatitude() {
        return minLatitude;
    }

    @Override
    public BigDecimal maxLatitude() {
        return maxLatitude;
    }

    @Override
    public BigDecimal minLongitude() {
        return minLongitude;
    }

    @Override
    public BigDecimal maxLongitude() {
        return maxLongitude;
    }
}
