package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        Departure departure,
        Destination destination
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public BigDecimal volumeAllPackages() {
        var volumeAllPackagesM3 = 0.0D;
        for (var pack : packages) {
            var dimensions = pack.dimensions();
            dimensions = Pack.normalizeDimensions(dimensions);
            var length = dimensions.length();
            var width = dimensions.width();
            var height = dimensions.height();
            volumeAllPackagesM3 += (double) (length * width * height) / 1_000_000_000;
        }
        return BigDecimal.valueOf(ceilVolume(volumeAllPackagesM3));
    }

    public Double ceilVolume(Double volume) {
        if (String.valueOf(volume.longValue()).length() >= 6) {
            var decimalFormat = new DecimalFormat("#.####");
            return Double.valueOf(decimalFormat.format(volume));
        }
        return volume;
    }
}
