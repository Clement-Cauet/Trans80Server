package fr.trans80.app.services;

import fr.trans80.app.controllers.CalendarController;
import fr.trans80.app.controllers.CalendarDateController;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
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
    private final CalendarDateController calendarDateController;
    private final List<ServiceCalendarDate> calendarDates;


    @Autowired
    public DateService(CalendarController calendarController, CalendarDateController calendarDateController) {
        this.calendarController = calendarController;
        this.calendars = calendarController.getAllCalendars();
        this.calendarDateController = calendarDateController;
        this.calendarDates = calendarDateController.getAllCalendarDates();
        if (this.calendars == null) {
            throw new IllegalStateException("Calendars list cannot be null");
        }
        if (this.calendarDates == null) {
            throw new IllegalStateException("Calendar dates list cannot be null");
        }
    }

    public boolean isDateTrip(LocalDate date, String serviceId) {
        ServiceCalendar calendar = calendars.stream()
                .filter(cal -> cal.getServiceId().getId().equals(serviceId))
                .findFirst()
                .orElse(null);

        if (calendar == null || !isDateTripRange(date, calendar)) {
            return false;
        }

        boolean isTripDay = isDateTripDay(date, calendar);
        Boolean exception = hasException(date, serviceId);
        //Boolean exception = null;

        if (isTripDay) {
            return exception == null || exception;
        } else {
            return Boolean.TRUE.equals(exception);
        }
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

    private Boolean hasException(LocalDate date, String serviceId) {
        return calendarDates.stream()
                .filter(cd -> cd.getServiceId().getId().equals(serviceId) &&
                        cd.getDate().getYear() == date.getYear() &&
                        cd.getDate().getMonth() == date.getMonthValue() &&
                        cd.getDate().getDay() == date.getDayOfMonth())
                .map(ServiceCalendarDate::getExceptionType)
                .sorted()
                .reduce((first, second) -> second)
                .map(type -> type == 1)
                .orElse(null);
    }


}
