package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.StopTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("stop_times")
@RequiredArgsConstructor
public class StopTimeController {

    private final GtfsService service;

    @GetMapping
    public List<StopTime> getAllStopTimes() {
        return this.service.getGtfsDao().getAllStopTimes().stream().limit(300).toList();
    }

    @GetMapping("/{stopId}")
    public List<StopTime> getStopTimesByStopId(@PathVariable String stopId) {
        return this.getAllStopTimes().stream()
                .filter(stopTime -> stopTime.getStop().getId().getId().equals(stopId))
                .collect(Collectors.toList());
    }
}
