package ru.fastdelivery.domain.common.price;

import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.currency.Currency;

import java.math.BigDecimal;

/**
 * @param amount   значение цены
 * @param currency валюта цены
 */
@Slf4j
public record Price(
        BigDecimal amount,
        Currency currency) {
    public Price {
        if (isLessThanZero(amount)) {
            log.warn("USER_ERROR: Price Amount cannot be below Zero!: " + amount);
            throw new IllegalArgumentException("Price Amount cannot be below Zero!");
        }
    }

    private static boolean isLessThanZero(BigDecimal price) {
        return BigDecimal.ZERO.compareTo(price) > 0;
    }

    public Price multiply(BigDecimal amount) {
        return new Price(this.amount.multiply(amount), this.currency);
    }

    public Price max(Price price) {
        if (!currency.equals(price.currency)) {
            log.warn("USER_ERROR: Cannot compare Prices in difference Currency!:" + price.currency + ", " + currency);
            throw new IllegalArgumentException("Cannot compare Prices in difference Currency!");
        }
        return new Price(this.amount.max(price.amount), this.currency);
    }
}
