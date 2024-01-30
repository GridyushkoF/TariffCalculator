package ru.fastdelivery.usecase.calculators;

public class NumberRounder {

    public double roundNumber(double number) {
        var integerPart = (int) number;
        var numberFractionalPart = number % 1;
        var numberFractionalString = String.valueOf(numberFractionalPart);
        if (numberFractionalString.length() > 2) {
            var numberFractionalPartSliced = numberFractionalString.substring(2, 4);
            var numberFractionalPartSlicedDouble = Integer.parseInt(numberFractionalPartSliced.replaceAll(",", ""));
            if (numberFractionalPartSlicedDouble != 99) {
                numberFractionalPartSlicedDouble += 1;
                return Double.parseDouble(integerPart + "." + numberFractionalPartSlicedDouble);
            }
            return integerPart + 1;
        }
        return 0;
    }
}
