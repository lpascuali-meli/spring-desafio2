package com.sprint.challenge2.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.utils.FilesUtils;
import com.sprint.challenge2.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepositoryImpl implements FlightRepository{
    private final String filePath;

    public FlightRepositoryImpl(@Value("${path_file_flight}") String path) {
        this.filePath = path;
    }

    /**
     * Get all the flights from the database
     * @return List<FlightDTO> List of all flights
     * @throws ApiException
     */
    @Override
    public List<FlightDTO> getAllFlights() throws ApiException {
        List<FlightDTO> list = FilesUtils.<FlightDTO>getAllObjects(filePath);
        return new ObjectMapper().convertValue(list, new TypeReference<List<FlightDTO>>() { });
    }

    /**
     * Find all cities of origin of the database flights
     * @return The list of the cities
     * @throws ApiException when the filereader fails
     */
    @Override
    public List<String> getAllOrigins() throws ApiException {
        List<String> destinations = new ArrayList<>();
        for ( FlightDTO flight : getAllFlights()) {
            String flightLocation = StringUtils.normalize(flight.getOrigin());
            if (!destinations.contains(flightLocation)) {
                destinations.add(flightLocation);
            }
        }
        return destinations;
    }

    /**
     * Find all cities of destination of the database flights
     * @return The list of the cities
     * @throws ApiException when the filereader fails
     */
    @Override
    public List<String> getAllDestinations() throws ApiException {
        List<String> destinations = new ArrayList<>();
        for ( FlightDTO flight : getAllFlights()) {
            String flightLocation = StringUtils.normalize(flight.getDestination());
            if (!destinations.contains(flightLocation)) {
                destinations.add(flightLocation);
            }
        }
        return destinations;
    }

    @Override
    public FlightDTO getFlightByCodeAndSeatType(String flightNumber, String seatType) throws ApiException {
        List<FlightDTO> list = getAllFlights();
        return list.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber)
                        && StringUtils.normalize(flight.getSeatType()).equals(StringUtils.normalize(seatType)))
                .findAny()
                .orElse(null);
    }

    /**
     * Set the booked attribute to the flight indicated
     * @param flightToReserve Flight to reserve
     * @throws ApiException when read or write file fails
     */
    @Override
    public void reserveFlight(FlightDTO flightToReserve) throws ApiException {
        List<FlightDTO> list = getAllFlights();
        int index = list.indexOf(flightToReserve);
        list.get(index).setBooked(true);
        updateDB(list);
    }

    private void updateDB(List<FlightDTO> listUpdated) throws ApiException {
        FilesUtils.<FlightDTO>writeFileFlight(filePath, listUpdated);
    }
}
