package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Route;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("routes")
@RequiredArgsConstructor
public class RouteController {

    private final GtfsService service;

    @GetMapping
    public List<Route> getAllRoutes(
            @RequestParam(value = "routeId", required = false) String routeId,
            @RequestParam(value = "stopId", required = false) String stopId) {

        return this.service.getGtfsDao().getAllRoutes().stream()
                .filter(route -> routeId == null || route.getId().getId().equals(routeId))
                .filter(route -> stopId == null || this.service.getGtfsDao().getAllStopTimes().stream()
                        .anyMatch(stopTime -> stopTime.getStop().getId().getId().equals(stopId) &&
                                stopTime.getTrip().getRoute().equals(route)))
                .sorted((a, b) -> a.getId().getId().compareToIgnoreCase(b.getId().getId()))
                .toList();
    }


}
