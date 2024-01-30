package ru.fastdelivery.domain.delivery.pack;

import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.dimensions.Dimensions;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
@Slf4j
public record Pack
        (Weight weight,
         Dimensions dimensions) {
    private static final Weight MAX_WEIGHT = new Weight(BigInteger.valueOf(150_000));

    public Pack {
        if (weight.greaterThan(MAX_WEIGHT)) {
            log.warn("USER_ERROR: " + "Package can't be more than " + MAX_WEIGHT);
            throw new IllegalArgumentException("Package can't be more than " + MAX_WEIGHT);
        }
    }

    public static Dimensions normalizeDimensions(Dimensions dimensions) {
        var length = dimensions.length();
        var width = dimensions.width();
        var height = dimensions.height();
        length -= length % 50;
        width -= width % 50;
        height -= height % 50;
        return new Dimensions(length, width, height);
    }
}
