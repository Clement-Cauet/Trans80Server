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
    public List<StopTime> getAllStopTimes() {
        return this.service.getGtfsDao().getAllStopTimes().stream().toList();
    }

    @GetMapping("/{tripId}")
    public List<StopTime> getStopTimesByTripId(@PathVariable String tripId) {

        return this.getAllStopTimes().stream()
                .filter(stopTime -> stopTime.getTrip().getId().getId().equals(tripId))
                .collect(Collectors.toList());
    }
}
