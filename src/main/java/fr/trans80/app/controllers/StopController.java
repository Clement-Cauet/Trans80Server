package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Stop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stops")
@RequiredArgsConstructor
public class StopController {

    private final GtfsService service;

    @GetMapping
    public List<Stop> getAllStops() {
        return this.service.getGtfsDao().getAllStops().stream().toList();
    }

    @GetMapping("/{id}")
    public Stop getStopById(String id) {
        return this.getAllStops().stream()
                .filter(stop -> stop.getId().getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
