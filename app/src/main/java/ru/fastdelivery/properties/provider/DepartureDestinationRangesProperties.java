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
    public BigDecimal getMinLatitude() {
        return minLatitude;
    }

    @Override
    public BigDecimal getMaxLatitude() {
        return maxLatitude;
    }

    @Override
    public BigDecimal getMinLongitude() {
        return minLongitude;
    }

    @Override
    public BigDecimal getMaxLongitude() {
        return maxLongitude;
    }
}
