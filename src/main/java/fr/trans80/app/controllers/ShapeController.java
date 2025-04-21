package fr.trans80.app.controllers;

import fr.trans80.app.services.GtfsService;
import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.ShapePoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shapes")
@RequiredArgsConstructor
public class ShapeController {

    private final GtfsService service;

    @GetMapping
    public List<ShapePoint> getAllShapes(
            @RequestParam(value = "shapeId", required = false) String shapeId) {
        return this.service.getGtfsDao().getAllShapePoints().stream()
                .filter(shapePoint -> shapeId == null || shapePoint.getShapeId().getId().equals(shapeId))
                .toList();
    }

    @GetMapping("/{shapeId}")
    public List<ShapePoint> getShapeByServiceId(@PathVariable String shapeId) {
        return this.service.getGtfsDao().getAllShapePoints().stream()
                .filter(shape -> shape.getShapeId().getId().equals(shapeId))
                .toList();
    }
}