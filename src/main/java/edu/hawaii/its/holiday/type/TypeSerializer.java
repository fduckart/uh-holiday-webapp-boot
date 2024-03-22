package edu.hawaii.its.holiday.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.hawaii.its.holiday.util.JsonUtil;

public class TypeSerializer extends StdSerializer<Type> {

    private static final long serialVersionUID = 149L;

    public TypeSerializer() {
        this(null);
    }

    public TypeSerializer(Class<Type> t) {
        super(t);
    }

    @Override
    public void serialize(Type dto, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        System.out.println(">>>>>><><><>  serialize: " + JsonUtil.asJson(dto));
        gen.writeString(JsonUtil.asJson(dto));
    }
}
