package fr.trans80.app.services;

import fr.trans80.app.controllers.CalendarController;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class DateService {

    private final CalendarController calendarController;
    private final List<ServiceCalendar> calendars;

    @Autowired
    public DateService(CalendarController calendarController) {
        this.calendarController = calendarController;
        this.calendars = calendarController.getAllCalendars();
        if (this.calendars == null) {
            throw new IllegalStateException("Calendars list cannot be null");
        }
    }

    public boolean isDateTrip(LocalDate date, String serviceId) {
        ServiceCalendar calendar = calendars.stream()
                .filter(cal -> cal.getServiceId().getId().equals(serviceId))
                .findFirst()
                .orElse(null);

        return calendar != null && isDateTripRange(date, calendar) && isDateTripDay(date, calendar);
    }

    private boolean isDateTripRange(LocalDate date, ServiceCalendar calendar) {

        LocalDate startDate = LocalDate.of(calendar.getStartDate().getYear(), calendar.getStartDate().getMonth(), calendar.getStartDate().getDay());
        LocalDate endDate = LocalDate.of(calendar.getEndDate().getYear(), calendar.getEndDate().getMonth(), calendar.getEndDate().getDay());

        return (date.isAfter(startDate) || date.isEqual(startDate)) &&
               (date.isBefore(endDate) || date.isEqual(endDate));
    }

    private boolean isDateTripDay(LocalDate date, ServiceCalendar calendar) {
        DayOfWeek day = date.getDayOfWeek();

        switch (day) {
            case MONDAY -> {
                return calendar.getMonday() == 1;
            }
            case TUESDAY -> {
                return calendar.getTuesday() == 1;
            }
            case WEDNESDAY -> {
                return calendar.getWednesday() == 1;
            }
            case THURSDAY -> {
                return calendar.getThursday() == 1;
            }
            case FRIDAY -> {
                return calendar.getFriday() == 1;
            }
            case SATURDAY -> {
                return calendar.getSaturday() == 1;
            }
            case SUNDAY -> {
                return calendar.getSunday() == 1;
            }
        }

        return false;
    }

}
