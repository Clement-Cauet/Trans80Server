package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Trip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("trips")
@RequiredArgsConstructor
public class TripController {

    private final GtfsService service;

    @GetMapping
    public List<Trip> getAllTrips() {
        return this.service.tripCache;
    }
}
