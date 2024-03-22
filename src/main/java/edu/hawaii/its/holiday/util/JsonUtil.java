package edu.hawaii.its.holiday.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class JsonUtil {

    private static final Log logger = LogFactory.getLog(JsonUtil.class);
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    static {
        jsonMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        jsonMapper.setVisibility(VisibilityChecker.Std.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));

    }

    // Private contructor to prevent instantiation.
    private JsonUtil() {
        // Empty.
    }

    public static String asJson(final Object object) {
        String result = null;
        try {
            result = jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("asJson; Error: " + e);
            // Maybe we should throw something?
        }
        return result;
    }

    public static <T> T asObject(final String json, Class<T> type) {
        T result = null;
        try {
            result = jsonMapper.readValue(json, type);
        } catch (Exception e) {
            logger.error("Error: " + e);
            // Maybe we should throw something?
        }
        return result;
    }

    // Important: the map entries are not JSON compliant after it filled.
    public static Map<String, Object> toMap(final String json) throws Exception {
        return new ObjectMapper()
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .readValue(json,
                        new TypeReference<Map<String, Object>>() {
                        });
    }

}
