package edu.hawaii.its.holiday.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

import edu.hawaii.its.holiday.controller.JsonData;

public class StringToJsonDataConverter implements Converter<String, JsonData> {

    private static final Log logger = LogFactory.getLog(StringToJsonDataConverter.class);

    @Override
    public JsonData convert(String source) {
        System.out.println(" ### INSIDE THE CONVERTER; source: " + source);
        return null;
    }

}
