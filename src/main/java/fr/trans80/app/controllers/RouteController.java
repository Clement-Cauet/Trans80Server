package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Route;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("routes")
@RequiredArgsConstructor
public class RouteController {

    private final GtfsService service;

    @GetMapping
    public List<Route> getAllRoutes() {
        return this.service.routeCache;
    }
}
