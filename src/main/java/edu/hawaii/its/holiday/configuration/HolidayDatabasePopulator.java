package edu.hawaii.its.holiday.configuration;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.web.reactive.function.client.WebClient;

import edu.hawaii.its.holiday.repository.HolidayRepository;
import edu.hawaii.its.holiday.type.Holiday;
import edu.hawaii.its.holiday.type.HolidayDto;
import edu.hawaii.its.holiday.util.Strings;

public class HolidayDatabasePopulator extends ResourceDatabasePopulator {

    private static final String holidayV2Url
            = "https://www.test.hawaii.edu/its/cloud/holiday/main/holidayapi/api/v2/holidays";

    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    public void populate(Connection connection) throws ScriptException {
        System.out.println(">>>>> populate; holidayRepository: " + holidayRepository);
        super.populate(connection);
        try {
            System.out.println(Strings.fill('a', 22));
            WebClient client = WebClient.create();
            System.out.println(Strings.fill('b', 22));
            Mono<List<HolidayDto>> jsonDataHoliday = client.get()
                    .uri(holidayV2Url)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<HolidayDto>>() {
                    });

            System.out.println(Strings.fill('c', 22));
            List<HolidayDto> holidays2 = jsonDataHoliday.block()
                    .stream()
                    .collect(Collectors.toList());

            System.out.println(Strings.fill('d', 22));
            int i = 1;
            for (HolidayDto d : holidays2) {
                System.out.println(String.format("%3s", i++) + ":TTTT: " + d);
                Holiday h = new Holiday();
                h.setId(d.getId());
                h.setOfficialYear(d.getOfficialYear());
                h.setObservedDate(d.getObservedDate());
                h.setOfficialDate(d.getOfficialDate());
                h.setDescription(d.getDescription());
                h.setTypes(d.getTypes());

                holidayRepository.save(h);
            }
            System.out.println(Strings.fill('e', 22));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
