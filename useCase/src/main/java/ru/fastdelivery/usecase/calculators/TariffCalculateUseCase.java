package ru.fastdelivery.usecase.calculators;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.usecase.WeightPriceProvider;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final DistanceCalculator distanceCalculator = new DistanceCalculator();
    private final NumberRounder numberRounder = new NumberRounder();
    private static final int TARIFF_PER_KILOMETER = 450;
    public Price calc(Shipment shipment) {
        //TODO: дописать и проверить тесты, добавить логгирование
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPrice = weightPriceProvider.minimalPrice();
        var basePrice = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPrice)
                .max(calcPriceByVolume(shipment))
                .amount()
                .doubleValue();
        var distance = distanceCalculator.calcDistance(shipment.departure(),shipment.destination());
        if(distance >= TARIFF_PER_KILOMETER) {
            var priceWithDistance = (distance / TARIFF_PER_KILOMETER) * basePrice;
            var roundedPrice = numberRounder.roundNumber(priceWithDistance);
            return new Price(new BigDecimal(String.valueOf(roundedPrice)),new CurrencyFactory(code -> true).create("RUB"));
        }
        return new Price(BigDecimal.valueOf(basePrice),new CurrencyFactory(code -> true).create("RUB"));
    }
    public Price calcPriceByVolume(Shipment shipment) {
        var volumeAllPackagesM3 = shipment.volumeAllPackages();
        return weightPriceProvider.costPerM3().multiply(volumeAllPackagesM3);
    }
    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
