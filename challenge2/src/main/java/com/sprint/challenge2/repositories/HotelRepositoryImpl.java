package com.sprint.challenge2.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.utils.FilesUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    private final String filePath;

    public HotelRepositoryImpl(@Value("${path_file_hotel}") String path) {
        this.filePath = path;
    }

    @Override
    public List<HotelDTO> getAllHotels() throws ApiException {
        List<HotelDTO> list = FilesUtils.<HotelDTO>getAllObjects(filePath);
        return new ObjectMapper().convertValue(list, new TypeReference<List<HotelDTO>>() { });
    }
}
