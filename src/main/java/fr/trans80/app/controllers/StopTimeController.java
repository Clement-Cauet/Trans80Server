package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.StopTime;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("stop_times")
@RequiredArgsConstructor
public class StopTimeController {

    private final GtfsService service;

    @GetMapping
    public List<StopTime> getStopTimes(
            @RequestParam(value = "tripId", required = false) String tripId,
            @RequestParam(value = "stopId", required = false) String stopId) {

        return this.service.getGtfsDao().getAllStopTimes().stream()
                .filter(stopTime -> tripId == null || stopTime.getTrip().getId().getId().equals(tripId))
                .filter(stopTime -> stopId == null || stopTime.getStop().getId().getId().equals(stopId))
                .collect(Collectors.toList());
    }
}
