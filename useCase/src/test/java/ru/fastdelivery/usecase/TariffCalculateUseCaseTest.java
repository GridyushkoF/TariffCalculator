package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimensions.Dimensions;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.usecase.calculators.DistanceCalculator;
import ru.fastdelivery.usecase.calculators.NumberRounder;
import ru.fastdelivery.usecase.calculators.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider);
    final NumberRounder numberRounder = new NumberRounder();
    final Departure departureTestValue = new Departure(
            BigDecimal.valueOf(40.714268),
            BigDecimal.valueOf(-74.005974)
    );
    final Destination destinationTestValue = new Destination(
            BigDecimal.valueOf(34.0522),
            BigDecimal.valueOf(-118.2437)
    );
    final DistanceCalculator distanceCalculator = new DistanceCalculator();

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(1), currency);
        var pricePerM3 = new Price(BigDecimal.valueOf(200), currency);

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(weightPriceProvider.costPerM3()).thenReturn(pricePerM3);

        var dimensions = new Dimensions(1000, 1000, 1000);
        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1)), dimensions)),
                new CurrencyFactory(code -> true).create("RUB"), departureTestValue, destinationTestValue);
        var distance = distanceCalculator.calcDistance(shipment.departure(), shipment.destination());
        var expectedPrice = new Price(BigDecimal.valueOf(numberRounder.roundNumber(200 * (distance / 450))), currency);

        var actualPrice = tariffCalculateUseCase.calculateTotalPrice(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }

    @Test
    @DisplayName("Получение общего объёма -> успешно")
    void whenCalcPriceByVolume_thenSuccess() {
        var pricePerM3 = new Price(new BigDecimal(200), currency);

        when(weightPriceProvider.costPerM3()).thenReturn(pricePerM3);
        var packList = List.of(
                new Pack(
                        new Weight(BigInteger.valueOf(100)),
                        new Dimensions(1000, 1000, 1000)
                )
        );

        var shipment = new Shipment(packList, currency, departureTestValue, destinationTestValue);
        var actual = tariffCalculateUseCase.calculatePriceByVolume(shipment);
        var expected = new Price(BigDecimal.valueOf(200), currency);
        assertThat(actual).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expected);

    }

}