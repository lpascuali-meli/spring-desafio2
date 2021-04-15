package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.dtos.HotelFilterDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.HotelRepository;
import com.sprint.challenge2.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelDTO> getAllHotels() throws ApiException {
        return hotelRepository.getAllHotels();
    }

    @Override
    public List<HotelDTO> getHotelsByFilters(HotelFilterDTO filters) throws ApiException {
        if (filters != null) {
            validateFilters(filters);
        }
        List<HotelDTO> allHotels = getAllHotels();
        allHotels = allHotels.stream()
            .filter(hotel -> {
                boolean matches = true;
                matches = !hotel.getBooked();
                if (filters.getCity() != null) {
                    matches = StringUtils.normalize(hotel.getCity()).equals(StringUtils.normalize(filters.getCity()));
                }
                if (filters.getDateFrom() != null) {
                    matches = matches && filters.getDateFrom().compareTo(hotel.getDateFrom()) >= 0;
                }
                if (filters.getDateTo() != null) {
                    matches = matches && filters.getDateTo().compareTo(hotel.getDateTo()) <= 0;
                }
                return matches;
            }).collect(Collectors.toList());
        if (!allHotels.isEmpty()) {
            return allHotels;
        }
        throw new ApiException(HttpStatus.NOT_FOUND, "Not hotels found.");
    }

    public void validateFilters(HotelFilterDTO filters) throws ApiException {
        if (filters.getDateFrom() != null
                && filters.getDateTo() != null
                && filters.getDateFrom().compareTo(filters.getDateTo()) > 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "AvailableUntilDate must be after than AvailableFromDate.");
        }
        if (filters.getCity() != null) {
            boolean cityNameMatched = false;
            for (HotelDTO hotel: hotelRepository.getAllHotels()) {
                if (StringUtils.normalize(hotel.getCity()).equals(StringUtils.normalize(filters.getCity()))) {
                    cityNameMatched = true;
                    break;
                }
            }
            if (!cityNameMatched) {
                throw new ApiException(HttpStatus.NOT_FOUND, "City not found.");
            }
        }

    }
}
