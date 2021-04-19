package com.sprint.challenge2.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.utils.FilesUtils;
import com.sprint.challenge2.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    private final String filePath;

    public HotelRepositoryImpl(@Value("${path_file_hotel}") String path) {
        this.filePath = path;
    }

    /**
     * Get all the hotels from the database
     * @return List<HotelDto> List of all hotels
     * @throws ApiException
     */
    @Override
    public List<HotelDTO> getAllHotels() throws ApiException {
        List<HotelDTO> list = FilesUtils.<HotelDTO>getAllObjects(filePath);
        return new ObjectMapper().convertValue(list, new TypeReference<List<HotelDTO>>() { });
    }

    /**
     * Get one hotel by code
     * @param code code of hotel to search
     * @return HotelDTO The hotel found. Null if not found
     */
    @Override
    public HotelDTO getHotelByCode(String code) throws ApiException {
        List<HotelDTO> list = getAllHotels();
        return list.stream()
            .filter(hotel -> hotel.getCode().equals(code))
            .findAny()
            .orElse(null);
    }

    /**
     * Set the booked attribute to the hotel indicated
     * @param hotelToBook Hotel to book
     * @throws ApiException when read or write file fails
     */
    @Override
    public void bookHotel(HotelDTO hotelToBook) throws ApiException {
        List<HotelDTO> list = getAllHotels();
        int index = list.indexOf(hotelToBook);
        list.get(index).setBooked(true);
        updateDB(list);
    }

    /**
     * Find all cities of the database hotels
     * @return The list of the cities
     * @throws ApiException when the filereader fails
     */
    @Override
    public List<String> getAllDestinations () throws ApiException {
        List<String> destinations = new ArrayList<>();
        for ( HotelDTO hotel : getAllHotels()) {
            String hotelLocation = StringUtils.normalize(hotel.getCity());
            if (!destinations.contains(hotelLocation)) {
                destinations.add(hotelLocation);
            }
        }
        return destinations;
    }

    private void updateDB(List<HotelDTO> listUpdated) throws ApiException {
        FilesUtils.<HotelDTO>writeFile(filePath, listUpdated);
    }
}
