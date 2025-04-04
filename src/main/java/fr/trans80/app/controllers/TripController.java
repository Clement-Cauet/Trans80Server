package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Trip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Trip> getTripsByRouteId(
            @PathVariable String routeId,
            @RequestParam(value = "date", required = false) String dateStr,
            @RequestParam(value = "directionId", required = false) String directionId) {

        LocalDate date = (dateStr != null)
                ? LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : LocalDate.now();

        return this.service.getGtfsDao().getAllTrips().stream()
                .filter(trip -> trip.getRoute().getId().getId().equals(routeId))
                .filter(trip -> directionId == null || trip.getDirectionId().equals(directionId))
                .collect(Collectors.toList());
    }

}