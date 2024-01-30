package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.fastdelivery.domain.common.dimensions.Dimensions;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,
        @Schema(description = "Длина упаковки, миллиметры", example = "490")
        BigInteger length,
        @Schema(description = "Ширина упаковки, миллиметры", example = "589")
        BigInteger width,
        @Schema(description = "Высота упаковки, миллиметры", example = "100")
        BigInteger height
) {
    public Dimensions getDimensions() {
        return new Dimensions(length.longValue(), width.longValue(), height.longValue());
    }
}
