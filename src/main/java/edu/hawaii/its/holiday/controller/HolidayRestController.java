package edu.hawaii.its.holiday.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.hawaii.its.holiday.service.HolidayService;
import edu.hawaii.its.holiday.type.Holiday;
import edu.hawaii.its.holiday.type.Type;

@RestController
public class HolidayRestController {

    private static final Logger logger = LoggerFactory.getLogger(HolidayRestController.class);

    @Autowired
    private HolidayService holidayService;

    @GetMapping(value = "/api/holidays",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData<List<Holiday>>> holidays() {
        logger.info("Entered REST holidays...");
        List<Holiday> holidays = holidayService.findHolidays();
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "/api/holidays/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData<Holiday>> holiday(@PathVariable Integer id) {
        logger.info("Entered REST holiday(" + id + ") ...");
        Holiday holiday = holidayService.findHoliday(id);
        JsonData<Holiday> data = new JsonData<>(holiday);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "/api/types",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData<List<Type>>> types() {
        logger.info("Entered REST types...");
        List<Type> types = holidayService.findTypes();
        JsonData<List<Type>> data = new JsonData<>(types);
        return ResponseEntity
                .ok()
                .body(data);
    }

}
