package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.Agency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("agencies")
@RequiredArgsConstructor
public class AgencyController {

    private final GtfsService service;

    @GetMapping
    public List<Agency> getAllAgencies() {
        return this.service.agencyCache;
    }
}
