package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("calendar_dates")
@RequiredArgsConstructor
public class CalendarDateController {

    private final GtfsService service;

    @GetMapping
    public List<ServiceCalendarDate> getAllCalendarDates() {
        return this.service.getGtfsDao().getAllCalendarDates().stream().toList();
    }

    @GetMapping("/{serviceId}")
    public List<ServiceCalendarDate> getCalendarDatesByServiceId(
            @PathVariable String serviceId,
            @RequestParam(value = "date", required = false) String dateStr) {

        LocalDate date = (dateStr != null)
                ? LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : LocalDate.now();

        return this.getAllCalendarDates().stream()
                .filter(calendar -> calendar.getServiceId().getId().equals(serviceId))
                .filter(cd -> cd.getDate().getYear() == date.getYear() &&
                        cd.getDate().getMonth() == date.getMonthValue() &&
                        cd.getDate().getDay() == date.getDayOfMonth())
                .toList();
    }
}