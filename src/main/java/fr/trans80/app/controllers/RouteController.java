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

        var gtfsDao = service.getGtfsDao();

        Stream<Route> routeStream;

        if (stopId != null) {
            routeStream = gtfsDao.getAllStopTimes().stream()
                    .filter(stopTime -> stopTime.getStop().getId().getId().equals(stopId))
                    .map(stopTime -> stopTime.getTrip().getRoute());
        } else {
            routeStream = gtfsDao.getAllRoutes().stream();
        }

        if (routeId != null) {
            routeStream = routeStream.filter(route -> route.getId().getId().equals(routeId));
        }

        return routeStream.distinct().toList();
    }


}
