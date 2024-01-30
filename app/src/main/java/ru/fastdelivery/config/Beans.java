package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.usecase.DepartureDestinationRangesProvider;
import ru.fastdelivery.usecase.RangesValidatorUseCase;
import ru.fastdelivery.usecase.WeightPriceProvider;
import ru.fastdelivery.usecase.calculators.TariffCalculateUseCase;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider) {
        return new TariffCalculateUseCase(weightPriceProvider);
    }

    @Bean
    public RangesValidatorUseCase rangesValidator(DepartureDestinationRangesProvider rangesProvider) {
        return new RangesValidatorUseCase(rangesProvider);
    }
}
