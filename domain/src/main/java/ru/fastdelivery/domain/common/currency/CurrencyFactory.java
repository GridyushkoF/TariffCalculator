package ru.fastdelivery.domain.common.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Создание валюты с проверками
 */

@RequiredArgsConstructor
@Slf4j
public class CurrencyFactory {


    private final CurrencyPropertiesProvider propertiesProvider;

    public Currency create(String code) {
        if (code == null || !propertiesProvider.isAvailable(code)) {
            log.warn("USER_ERROR: Currency code contains not available value: " + code);
            throw new IllegalArgumentException("Currency code contains not available value");
        }

        return new Currency(code);
    }
}
