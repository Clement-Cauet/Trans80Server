package fr.trans80.app.controllers;

import fr.trans80.app.services.DateService;
import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.StopTime;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("stop_times")
@RequiredArgsConstructor
public class StopTimeController {

    private final GtfsService service;
    private final CalendarController calendarController;

    @GetMapping
    public List<StopTime> getStopTimes(
            @RequestParam(value = "tripId", required = false) String tripId,
            @RequestParam(value = "routeId", required = false) String routeId,
            @RequestParam(value = "stopId", required = false) String stopId,
            @RequestParam(value = "date", required = false) String dateStr,
            @RequestParam(value = "directionId", required = false) String directionId) {

        LocalDate date = (dateStr != null)
                ? LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : LocalDate.now();

        return this.service.getGtfsDao().getAllStopTimes().stream()
                .filter(stopTime -> tripId == null || stopTime.getTrip().getId().getId().equals(tripId))
                .filter(stopTime -> routeId == null || stopTime.getTrip().getRoute().getId().getId().equals(routeId))
                .filter(stopTime -> stopId == null || stopTime.getStop().getId().getId().equals(stopId))
                .filter(stopTime -> new DateService(calendarController).isDateTrip(date, stopTime.getTrip().getServiceId().getId()))
                .filter(stopTime -> directionId == null || stopTime.getTrip().getDirectionId().equals(directionId))
                .collect(Collectors.toList());
    }
}
