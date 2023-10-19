package edu.hawaii.its.holiday.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import reactor.core.publisher.Mono;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hawaii.its.holiday.controller.JsonData;
import edu.hawaii.its.holiday.repository.DesignationRepository;
import edu.hawaii.its.holiday.repository.HolidayRepository;
import edu.hawaii.its.holiday.repository.TypeRepository;
import edu.hawaii.its.holiday.repository.UserRoleRepository;
import edu.hawaii.its.holiday.type.Designation;
import edu.hawaii.its.holiday.type.Holiday;
import edu.hawaii.its.holiday.type.Type;
import edu.hawaii.its.holiday.type.UserRole;
import edu.hawaii.its.holiday.util.Dates;
import edu.hawaii.its.holiday.util.Strings;

@Service
public class HolidayService {

    private static final Log logger = LogFactory.getLog(HolidayService.class);

    private static final String holidayV2Url = "https://www.test.hawaii.edu/its/cloud/holiday/main/holidayapi/api/v2/holidays";
    private static final String holidayV1UrlItem = "https://www.test.hawaii.edu/its/cloud/holiday/main/holidayapi/api/holidays/1001/";
    private static final String holidayV2UrlItem = "https://www.test.hawaii.edu/its/cloud/holiday/main/holidayapi/api/v2/holidays/1001/";

    private RestTemplate restTemplate;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    // Constructor.
    public void xHolidayService(@Autowired RestTemplate restTemplate) throws JsonProcessingException {
        this.restTemplate = restTemplate;
        System.out.println(">>>>> restTemplate: " + restTemplate);

        // final String holidayV2Url = "https://www.hawaii.edu/its/cloud/holiday/holidayapi/api/holidays";
        // final String url = "https://www.hawaii.edu/its/ws/holiday/api/holidays";
        final String typesV1Url = "https://www.test.hawaii.edu/its/ws/holiday/api/types";
        final String typesV2Url = "https://www.test.hawaii.edu/its/cloud/holiday/main/holidayapi/api/v2/types";
        // final String githubUrl = "https://api.github.com/users/duckart";

        System.out.println(Strings.fill('A', 99));
        ResponseEntity<String> responses
                = restTemplate.getForEntity(typesV2Url, String.class);
        assertThat(responses.getStatusCode(), equalTo(HttpStatus.OK));
        System.out.println("TYPESa  : " + responses);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responses.getBody());
        JsonNode name = root.path("data");
        System.out.println("NAMEaa: " + name);

        ResponseEntity<List<Type>> response = restTemplate
                .exchange(typesV2Url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Type>>() {
                });

        System.out.println(Strings.fill('s', 44));
        System.out.println("class type data: " + response);
        System.out.println(".........................");
        System.out.println("class type data: " + response.getBody().getClass());
        System.out.println(Strings.fill('t', 44));
        List<Type> tts = response.getBody().stream().collect(Collectors.toList());
        for (Type t : tts) {
            System.out.println("### TYPE (v2): " + t);
        }

        System.out.println(Strings.fill('v', 44));

        ResponseEntity<JsonData<List<Type>>> hResponse = restTemplate
                .exchange(typesV1Url, HttpMethod.GET, null, new ParameterizedTypeReference<JsonData<List<Type>>>() {
                });

        System.out.println("### SUPER WOW: " + hResponse);
        tts = hResponse.getBody().getData().stream().collect(Collectors.toList());
        for (Type t : tts) {
            System.out.println("### TYPE (v1): " + t);
        }

        System.out.println(Strings.fill('w', 44));

        System.out.println("### GODDAMN  : " + hResponse);
        tts = hResponse.getBody().getData();
        for (Type t : tts) {
            System.out.println("### TYPE (v1): " + t);
        }

        System.out.println(Strings.fill('B', 99));

        WebClient client = WebClient.create();
        Mono<List<Type>> jsonData = client.get()
                .uri(typesV2Url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Type>>() {
                });

        System.out.println("### HELL YA  : " + hResponse);
        tts = jsonData.block().stream().collect(Collectors.toList());
        for (Type t : tts) {
            System.out.println("### TYPE (v1): " + t);
        }

        System.out.println(Strings.fill('D', 99));

        System.out.println(Strings.fill('x', 44));
        System.out.println(Strings.fill('x', 44));

        // ResponseEntity<JsonData<Holiday>> res = restTemplate
        //         .exchange(holidayV1UrlItem,
        //                 HttpMethod.GET,
        //                 null,
        //                 new ParameterizedTypeReference<JsonData<Holiday>>() {
        //                 });
        // client = WebClient.create();
        Mono<JsonData<Holiday>> jsonData2 = client.get()
                .uri(holidayV1UrlItem)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<JsonData<Holiday>>() {
                });

        System.out.println("### HITM (v2): " + jsonData2.block().getData());

        System.out.println(Strings.fill('E', 99));
        System.out.println(Strings.fill('E', 99));

        if ("".equals("")) {

            client = WebClient.create();
            Mono<List<Holiday>> jsonDataHoliday = client.get()
                    .uri(holidayV2Url)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Holiday>>() {
                    });

            List<Holiday> holidays2 = jsonDataHoliday.block().stream().collect(Collectors.toList());
            for (Object h : holidays2) {
                System.out.println("  ---> " + h);
            }
        }

        System.out.println(Strings.fill('Z', 99));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "holidaysById", key = "#id")
    public Holiday findHoliday(Integer id) {
        return holidayRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "holidays")
    public List<Holiday> findHolidays() {
        return holidayRepository.findAllByOrderByObservedDateDesc();
    }

    @Transactional(readOnly = true)
    public List<UserRole> findUserRoles() {
        return userRoleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "holidayTypesById", key = "#id")
    public Type findType(Integer id) {
        return typeRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "holidayTypes")
    public List<Type> findTypes() {
        return typeRepository.findAll();
    }

    public List<Holiday> findHolidaysByYear(Integer year) {
        LocalDate start = Dates.firstDateOfYear(year);
        LocalDate end = Dates.lastDateOfMonth(Month.DECEMBER, year);
        return holidayRepository.findAllByOfficialDateBetween(start, end);
    }

    public List<Holiday> findHolidaysByType(List<Holiday> holidays, String type) {
        return holidays.stream().filter(holiday -> holiday.getTypes().stream()
                        .anyMatch(types -> types.getDescription().equalsIgnoreCase(type)))
                .collect(Collectors.toList());
    }

    public List<Holiday> findHolidaysByMonth(Integer month, Integer year) {
        Month realMonth = Month.of(month);
        LocalDate start = Dates.firstDateOfMonth(realMonth, year);
        LocalDate end = Dates.lastDateOfMonth(realMonth, year);
        return holidayRepository.findAllByOfficialDateBetween(start, end);
    }

    public List<Holiday> findHolidaysByRange(String beginDate, String endDate, boolean include) {
        LocalDate start = Dates.toLocalDate(beginDate, "yyyy-MM-dd");
        LocalDate end = Dates.toLocalDate(endDate, "yyyy-MM-dd");
        if (!include) {
            start = Dates.fromOffset(start, 1);
            end = Dates.fromOffset(end, -1);
        }
        return holidayRepository.findAllByOfficialDateBetween(start, end);
    }

    public Holiday findClosestHolidayByDate(String date, boolean forward) {
        List<Holiday> holidays = holidayRepository.findAllByOrderByObservedDateDesc();
        LocalDate curDate = Dates.toLocalDate(date, "yyyy-MM-dd");

        int closestIndex;
        long daysBetween;
        int i = 0;

        do {
            daysBetween = Dates.compareDates(curDate, holidays.get(i).getObservedDate());
            i++;
        } while (daysBetween > -1);

        closestIndex = forward ? i - 2 : i - 1;

        holidays.get(closestIndex + 1).setClosest(false);
        holidays.get(closestIndex).setClosest(true);

        return holidays.get(closestIndex);
    }

    public Holiday findClosestHolidayByDate(String date, boolean forward, String type) {
        List<Holiday> holidays = holidayRepository.findAllByOrderByObservedDateDesc();
        LocalDate curDate = Dates.toLocalDate(date, "yyyy-MM-dd");

        int closestIndex;
        long daysBetween;
        int i = 0;
        boolean found = false;

        do {
            daysBetween = Dates.compareDates(curDate, holidays.get(i).getObservedDate());
            i++;
        } while (daysBetween > -1);

        closestIndex = forward ? i - 2 : i - 1;

        while (!found) {
            for (int j = 0; j < holidays.get(closestIndex).getHolidayTypes().size(); j++) {
                if (holidays.get(closestIndex).getHolidayTypes().get(j).equalsIgnoreCase(type)) {
                    found = true;
                }
            }

            if (!found) {
                closestIndex = forward ? closestIndex - 1 : closestIndex + 1;
            }
        }

        holidays.get(closestIndex + 1).setClosest(false);
        holidays.get(closestIndex).setClosest(true);

        return holidays.get(closestIndex);
    }

    @Caching(evict = {
            @CacheEvict(value = "holidays", allEntries = true),
            @CacheEvict(value = "holidaysById", allEntries = true) })
    public void evictHolidaysCache() {
        // Empty.
    }

    private List<String> findAllDescriptions() {
        return holidayRepository.findAllDistinctDescription();
    }

    public List<Designation> findDesignations() {
        return designationRepository.findAll();
    }

    public Designation findDesignation(Integer id) {
        return designationRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Page<Holiday> findPaginatedHdays(final int page, final int size) {
        return holidayRepository.findAllByOrderByObservedDateAsc(PageRequest.of(page, size));

    }
}
