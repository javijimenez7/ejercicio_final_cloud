package com.concurso.backEmpresa.Trip;

import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TripTest{
    @Autowired
    TripServiceImpl tripService;

    @MockBean
    TripRepository tripRepository;


    TripOutputDto tripOutputDto =
        new TripOutputDto(
                "TRIP0001",
                null,
                "JAEN",
                "GRANADA",
                37,
                false,
                new ArrayList<>()
        );

        @Test
        void findTripById() throws Exception {
            Optional<Trip> optTrip = Optional.of(tripService.tripOutDtoToEntity(tripOutputDto));
            System.out.println("TEST1");
            when(tripRepository.findById("TRIP0001")).thenReturn(optTrip);
            assertEquals(tripOutputDto, tripService.filterTripById("TRIP0001"));
            System.out.println(tripOutputDto);
        }

}
