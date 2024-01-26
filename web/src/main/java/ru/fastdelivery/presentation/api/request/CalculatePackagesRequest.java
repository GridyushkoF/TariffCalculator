package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
@Slf4j
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = "[{\"weight\": 4056.45}]")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,
        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,
        @Schema(description = "Координаты пункта отправки", example = "34.345625467")
        @NotNull
        Departure departure,
        @Schema(description = "Координаты пункта доставки", example = "56.678456")
        @NotNull
        Destination destination

) {
        public CalculatePackagesRequest {
                if(packages != null) {
                        for (CargoPackage cargoPackage : packages) {
                                var length = cargoPackage.length().longValue();
                                var width = cargoPackage.width().longValue();
                                var height = cargoPackage.height().longValue();
                                if(length <= 0 || width <= 0 || height <= 0) {
                                        String warnText = "One of size values equals or lower than 0!: " + length + ", " + width + ", " + height;
                                        log.warn(warnText);
                                        throw new IllegalArgumentException(warnText);
                                }
                                if(length >= 1500 || width >= 1500 || height >= 1500) {
                                        String warnText = "One of size values equals or higher than 1500!: " + length + ", " + width + ", " + height;
                                        log.warn(warnText);
                                        throw new IllegalArgumentException(warnText);
                                }
                        }
                }
                if(departure == null) {
                        String warnText = "departure can`t be null";
                        log.warn(warnText);
                        throw new IllegalArgumentException(warnText);
                }
                if(destination == null) {
                        String warnText = "destination can`t be null";
                        log.warn(warnText);
                        throw new IllegalArgumentException(warnText);
                }

        }
}
