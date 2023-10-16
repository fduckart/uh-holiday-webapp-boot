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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import edu.hawaii.its.holiday.controller.JsonData2;
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

    private final RestTemplate restTemplate;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    // Constructor.
    public HolidayService(@Autowired RestTemplate restTemplate) throws JsonProcessingException {
        this.restTemplate = restTemplate;
        System.out.println(">>>>> restTemplate: " + restTemplate);

        ///final String url = "https://www.hawaii.edu/its/cloud/holiday/holidayapi/api/holidays";
        // final String url = "https://www.hawaii.edu/its/ws/holiday/api/holidays";
        final String typesUrl = "https://www.test.hawaii.edu/its/ws/holiday/api/types";
        // final String githubUrl = "https://api.github.com/users/duckart";

        System.out.println(Strings.fill('A', 99));
        // Holiday[] response0 = restTemplate.getForObject(url, Holiday[].class);
        //
        // for (Holiday h : response0) {
        //     System.out.println("  -a-> " + h.toString());
        //     // System.out.println("  -b-> " + asObject(h.toString(), Holiday.class));
        // }

        // ResponseEntity<Object> response
        //         = restTemplate.getForEntity(url, Object.class);

        // ResponseEntity<Object> response
        //         = restTemplate.getForEntity(url, Object.class);

        // JsonData response
        //         = restTemplate.getForObject(url, JsonData.class);

        ResponseEntity<String> responses
                = restTemplate.getForEntity(typesUrl, String.class);
        assertThat(responses.getStatusCode(), equalTo(HttpStatus.OK));
        System.out.println("TYPESa  : " + responses);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responses.getBody());
        JsonNode name = root.path("data");
        System.out.println("NAMEaa: " + name);

        // ResponseEntity<JsonData> response

        ResponseEntity<JsonData> response
                = restTemplate.getForEntity(typesUrl, JsonData.class);

        List<Type> types = (List<Type>) response.getBody().getData();
        System.out.println(Strings.fill('s', 44));
        System.out.println("class type data: " + ((List<?>) response.getBody().getData()).get(0));
        System.out.println(">>>>> types: " + types);
        System.out.println(Strings.fill('t', 44));

        // JsonData<List<Holiday>> data = new JsonData<>(holidays);
        //
        ///JsonData<List<Holiday>> data = response.getBody();
        // System.out.println(response.getBody().getData().getClass());
        System.out.println("RESPONSE: " + response.getBody().getData());
        List<Object> objects = (List<Object>) response.getBody().getData();

        for (Object h : objects) {
            System.out.println("  -a-> " + h.toString());
            // System.out.println("  -b-> " + asObject(h.toString(), Object.class));
        }

        if ("off".equals("")) {
            Type[] typesx = restTemplate.getForObject(typesUrl, Type[].class);
            System.out.println("TYPES   : " + typesx);
        }

        // String s = "{\"description\":Christmas, \"officialYear\":2028, \"types\":[{id:2, version:1, description:Federal}, {id:3, version:1, description:UH}, {id:4, version:1, description:State}], closest:false, holidayTypes:[Federal, UH, State], year:2028, observedDateFull:December 25, 2028, Monday, officialDateFull:December 25, 2028, Monday, observedDate:2028-12-25, \"officialDate:2028-12-25}";
        String s = "{\"description\":\"Christmas\", \"officialYear\":2028}"; // works
        System.out.println("  -b-> " + asObject(s, Holiday.class));

        // System.out.println(" >>> " + response.getBody().getData().getClass());
        System.out.println(Strings.fill('B', 99));

        // String userJson = restTemplate.getForObject(url, String.class);
        // System.out.println("  <><>   userJson: " + userJson);
        // ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        System.out.println(Strings.fill('B', 99));
        // mapper = new ObjectMapper();
        // JsonNode root = mapper.readTree(responseEntity.getBody());
        // System.out.println("  <><>     root: " + root);
        // List<JsonNode> nodes = root.findValues("data");
        // System.out.println("  <><>    nodes: " + nodes.size());
        // System.out.println("  <><>    nodes: " + nodes);

        System.out.println(Strings.fill('C', 99));

        if ("off".equals("")) {
            String url = null;

            WebClient client = WebClient.create();

            WebClient.ResponseSpec responseSpec = client.get()
                    .uri(url)
                    .retrieve();
            System.out.println("  <><><> responseSpec: " + responseSpec);

            String responseBody = responseSpec.bodyToMono(String.class).block();
            System.out.println("  <><><> responseBody: " + responseBody.length());
            System.out.println(Strings.fill('C', 99));

            Mono<JsonData> jsonData = client.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(JsonData.class);

            List<Object> holidays = (List<Object>) jsonData.block().getData();
            for (Object h : holidays) {
                System.out.println("  -a-> " + h.toString());
                System.out.println("  -b-> " + asObject(h.toString(), Holiday.class));
            }

            System.out.println(Strings.fill('D', 99));

            Mono<JsonData2> jsonData2 = client.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(JsonData2.class);

            // System.out.println(" >>>>>>> " + jsonData2.block());

            // Mono<JsonData> mono = responseSpec.bodyToMono(JsonData.class);

            // System.out.println("  <><><>    JsonData: " + jsonData.block().getData().getClass());
            List<Holiday> holidays2 = jsonData2.block().getData();
            for (Object h : holidays2) {
                System.out.println("  ---> " + h);
            }

            System.out.println(Strings.fill('E', 99));
        }
    }

    private <T> T asObject(final String json, Class<T> type) {
        T result = null;
        try {
            result = new ObjectMapper().readValue(json, type);
        } catch (Exception e) {
            logger.error("Error: " + e);
            // Maybe we should throw something?
        }
        return result;
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
