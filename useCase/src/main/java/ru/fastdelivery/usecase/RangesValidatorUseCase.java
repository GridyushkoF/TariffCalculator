package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;

import javax.inject.Named;
import java.util.Arrays;

@Named
@RequiredArgsConstructor
@Slf4j
public class RangesValidatorUseCase {

    private final DepartureDestinationRangesProvider rangesProvider;

    public void checkCoordinatesForCorrectness(Departure departure, Destination destination) {
        var departureLatitude = departure.latitude().doubleValue();
        var departureLongitude = departure.longitude().doubleValue();
        var destinationLatitude = destination.latitude().doubleValue();
        var destinationLongitude = destination.longitude().doubleValue();
        if (
                !isLatitudesCorrect(departureLatitude, destinationLatitude)
                        ||
                        !isLongitudesCorrect(departureLongitude, destinationLongitude)) {
            String errorText = "Latitude or longitude is out of range";
            log.warn(errorText);
            throw new IllegalArgumentException(errorText);
        }
    }

    public boolean isLatitudesCorrect(Double... latitudes) {
        return Arrays.stream(latitudes)
                .allMatch(latitude ->
                        isInRange(latitude,
                                rangesProvider.getMinLatitude().doubleValue(),
                                rangesProvider.getMaxLatitude().doubleValue()));
    }

    public boolean isLongitudesCorrect(Double... longitudes) {
        return Arrays.stream(longitudes)
                .allMatch(longitude ->
                        isInRange(longitude,
                                rangesProvider.getMinLongitude().doubleValue(),
                                rangesProvider.getMaxLongitude().doubleValue()));
    }

    public boolean isInRange(double number, double min, double max) {
        return number < max && number >= min;
    }
}
