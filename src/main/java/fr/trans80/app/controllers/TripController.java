package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Trip;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trips")
@RequiredArgsConstructor
public class TripController {

    private final GtfsService service;

    @GetMapping
    public List<Trip> getAllTrips() {
        return this.service.getGtfsDao().getAllTrips().stream().toList();
    }

    @GetMapping("/{routeId}")
    public List<Trip> getTripsByRouteId(@RequestParam String routeId) {
        return this.getAllTrips().stream()
                .filter(trip -> trip.getRoute().getId().getId().equals(routeId))
                .toList();
    }


}
