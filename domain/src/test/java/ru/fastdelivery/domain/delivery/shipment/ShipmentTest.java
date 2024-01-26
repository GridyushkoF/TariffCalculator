package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimensions.Dimensions;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {
    final Departure departureTestValue = new Departure(
            BigDecimal.valueOf(40.714268),
            BigDecimal.valueOf(-74.005974)
    );
    final Destination destinationTestValue = new Destination(
            BigDecimal.valueOf(34.0522),
            BigDecimal.valueOf(-118.2437)
    );
    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);

        var packages = List.of(new Pack(weight1,null), new Pack(weight2,null));
        var shipment = new Shipment(packages,
                new CurrencyFactory(code -> true).create("RUB"),departureTestValue,destinationTestValue);

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }
    @Test
    void whenSummarizingVolumeOfAllPackages_thenReturnSum() {
        var weight = new Weight(BigInteger.valueOf(100));
        var dimensions1 = new Dimensions(100,200,300);
        var dimensions2 = new Dimensions(120,230,340);
        List<Pack> packList = List.of(
                new Pack(weight,dimensions1),
                new Pack(weight,dimensions2)
        );

        var shipment = new Shipment(packList,new CurrencyFactory(code -> true).create("RUB"),departureTestValue,destinationTestValue);
        assertThat(shipment.volumeAllPackages().doubleValue()).isEqualTo(0.012);


    }
}