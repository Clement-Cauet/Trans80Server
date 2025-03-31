package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final GtfsService service;

    @GetMapping
    public List<ServiceCalendar> getAllCalendars() {
        return this.service.calendarCache;
    }
}
