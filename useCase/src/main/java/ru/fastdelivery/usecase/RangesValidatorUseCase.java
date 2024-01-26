package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.coords.departure.Departure;
import ru.fastdelivery.domain.common.coords.destination.Destination;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
@Slf4j
public class RangesValidatorUseCase {
    private final DepartureDestinationRangesProvider rangesProvider;;

    public void throwExceptionIfNotValid(Departure departure, Destination destination) {
        var maxLatitude = rangesProvider.maxLatitude().longValue();
        var minLatitude = rangesProvider.minLatitude().longValue();
        var maxLongitude = rangesProvider.maxLongitude().longValue();
        var minLongitude = rangesProvider.minLongitude().longValue();

        var departureLatitude = departure.latitude().longValue();
        var departureLongitude = departure.longitude().longValue();
        var destinationLatitude = destination.latitude().longValue();
        var destinationLongitude = destination.longitude().longValue();
        if(
                !(isInRange(departureLatitude,minLatitude,maxLatitude)
                        &&
                        isInRange(departureLongitude,minLongitude,maxLongitude)
                        &&
                        isInRange(destinationLatitude,minLatitude,maxLatitude)
                        &&
                        isInRange(destinationLongitude,minLongitude,maxLongitude))
        ) {
            String errorText = "Latitude or longitude is out of range";
            log.warn(errorText);
            throw new IllegalArgumentException(errorText);
        }
    }
    public boolean isInRange(long number, long min, long max) {
        return number < max && number >= min;
    }
}
